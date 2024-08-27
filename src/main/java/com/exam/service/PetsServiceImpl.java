package com.exam.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.exam.dto.PetsDTO;
import com.exam.dto.PetsImageDTO;
import com.exam.config.PetsImageMapper;
import com.exam.config.PetsMapper;

@Service
public class PetsServiceImpl implements PetsService {

    Logger logger = LoggerFactory.getLogger(PetsService.class);
    private final PetsMapper petsMapper;
    private final PetsImageMapper petsImageMapper;
    private final AmazonS3 s3Client;
    private final String bucketName;
    
    private final Converter<MultipartFile, PetsImageDTO> converter;

    @Autowired
    public PetsServiceImpl(AmazonS3 s3Client, @Value("${cloud.aws.s3.bucket}") String bucketName, 
                           PetsMapper petsMapper, PetsImageMapper petsImageMapper, 
                           Converter<MultipartFile, PetsImageDTO> converter) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
        this.petsMapper = petsMapper;
        this.petsImageMapper = petsImageMapper;
        this.converter = converter;
    }

    @Override
    public List<PetsDTO> getAllPets() {
        return petsMapper.getAllPets();
    }

    @Override
    public List<PetsDTO> getPetsByUserId(int userId) {
        return petsMapper.getPetsByUserId(userId);
    }

    @Override
    public PetsDTO getPetById(int petId) {
        return petsMapper.getPetById(petId);
    }

    @Override
    public Map<String, String> addPet(PetsDTO pet, MultipartFile petImage) {
        String imageFileName = null;

        // 이미지 파일 처리
        if (petImage != null && !petImage.isEmpty()) {
            // 변환기를 사용하여 MultipartFile을 PetsImageDTO로 변환
            PetsImageDTO petsImageDTO = converter.convert(petImage);

            if (petsImageDTO != null) {
                imageFileName = petsImageDTO.getPetImage();  // 변환된 파일 이름 사용

                // AWS S3에 이미지 업로드
                try (InputStream inputStream = petImage.getInputStream()) {
                    String s3Key = "images/" + imageFileName;

                    // 파일의 Content-Type을 가져옵니다.
                    String contentType = petImage.getContentType();
                    if (contentType == null) {
                        contentType = "application/octet-stream"; // 기본값 설정
                    }

                    ObjectMetadata metadata = new ObjectMetadata();
                    metadata.setContentType(contentType); // Content-Type 설정
                    s3Client.putObject(new PutObjectRequest(bucketName, s3Key, inputStream, metadata));

                    logger.info("Image uploaded to S3 with filename: {}", imageFileName);

                    // 이미지 정보를 데이터베이스에 저장
                    petsImageMapper.addPetImage(petsImageDTO); // 매퍼를 통해 이미지 추가

                    // petsImageDTO가 자동으로 생성된 petImageId를 가져온다고 가정
                    int generatedPetImageId = petsImageDTO.getPetImageId();
                    logger.info("generatedPetImageId: {}", generatedPetImageId);
                    if (generatedPetImageId != 0) {
                        pet.setPetImageId(generatedPetImageId); // 이미지 ID를 PetsDTO에 설정

                    } else {
                        logger.error("Failed to get generated petImageId");
                        throw new IllegalStateException("Failed to get generated petImageId");
                    }
                } catch (IOException e) {
                    logger.error("Failed to upload image to S3", e);
                    throw new IllegalArgumentException("Failed to upload image to S3", e);
                }
            }
        }

        // 데이터베이스에 삽입할 데이터를 Map으로 준비
        Map<String, Object> petData = new HashMap<>();
        petData.put("userId", pet.getUserId());
        petData.put("petName", pet.getPetName());
        petData.put("petBirthdate", pet.getPetBirthdate());
        petData.put("petType", pet.getPetType());
        if (pet.getPetImageId() != 0) {
            petData.put("petImageId", pet.getPetImageId()); // 이미지 ID를 Map에 추가
        }

        // 데이터베이스에 반영
        logger.info("Adding pet to the database: {}", petData);
        petsMapper.addPet(petData);

        // 응답으로 보낼 데이터 구성
        Map<String, String> response = new HashMap<>();
        response.put("petImage", imageFileName);
        return response;
    }



    @Override
    public void updatePet(PetsDTO pet) {
        petsMapper.updatePet(pet);
    }

    @Override
    public void deletePet(int petId) {
        petsMapper.deletePet(petId);
    }
}
