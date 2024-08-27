package com.exam.controller;

import java.io.InputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.exam.dto.PetsDTO;
import com.exam.service.PetsService;

@RestController
@RequestMapping("/pets")
public class PetsController {
	
	Logger logger = LoggerFactory.getLogger(PetsController.class);

    private final PetsService petsService;
    private final AmazonS3 s3Client;
    private final String bucketName;

    @Autowired
    public PetsController(PetsService petsService, AmazonS3 s3Client, @Value("${cloud.aws.s3.bucket}") String bucketName) {
        this.petsService = petsService;
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    // 모든 펫 조회
    @GetMapping
    public ResponseEntity<List<PetsDTO>> getAllPets() {
        List<PetsDTO> petsList = petsService.getAllPets();
        return new ResponseEntity<>(petsList, HttpStatus.OK);
    }
    
    // 특정 유저의 펫 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PetsDTO>> getPetsByUserId(@PathVariable int userId) {
        List<PetsDTO> petsList = petsService.getPetsByUserId(userId);
        return new ResponseEntity<>(petsList, HttpStatus.OK);
    }


    // 특정 펫 조회
    @GetMapping("/{petId}")
    public ResponseEntity<PetsDTO> getPetById(@PathVariable int petId) {
        PetsDTO pet = petsService.getPetById(petId);
        if (pet != null) {
            return new ResponseEntity<>(pet, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 펫 추가
    @PostMapping
    public ResponseEntity<Map<String, String>> addPet(
            @ModelAttribute PetsDTO pet, // DTO 객체
            @RequestParam(value = "petImage", required = false) MultipartFile petImage) { // MultipartFile 객체
        Map<String, String> response = new HashMap<>();

        // 요청 데이터 로그 추가
        logger.info("Received request to add pet: {}", pet.toString());
        if (petImage != null) {
            logger.info("Received image: {}", petImage.getOriginalFilename());
        }

        try {
            // 파일 검증
            if (petImage != null && !petImage.isEmpty()) {
                // 파일 Content-Type 검증
                String contentType = petImage.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    throw new IllegalArgumentException("Invalid file type. Only image files are allowed.");
                }
            }

            // 서비스 메서드 호출
            Map<String, String> serviceResponse = petsService.addPet(pet, petImage);
            return new ResponseEntity<>(serviceResponse, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.error("Error adding pet: {}", e.getMessage());
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while adding pet: ", e);
            response.put("error", "An unexpected error occurred.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    // S3에서 이미지 반환
    @CrossOrigin(origins = "*")
    @GetMapping("/images")
    public ResponseEntity<InputStreamResource> returnImage(@RequestParam String petImage) throws IOException {
        try {
            // S3 객체 가져오기
            S3Object s3Object = s3Client.getObject(new GetObjectRequest(bucketName, "images/" + petImage));
            if (s3Object == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // S3 객체의 메타데이터 가져오기
            ObjectMetadata metadata = s3Object.getObjectMetadata();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(metadata.getContentType()));
            headers.setContentLength(metadata.getContentLength());

            // InputStreamResource를 사용해 이미지 반환
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(new InputStreamResource(s3Object.getObjectContent()));
        } catch (AmazonS3Exception e) {
            // S3 관련 예외 처리
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // 기타 예외 처리
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 펫 정보 업데이트
    @PutMapping("/{petId}")
    public ResponseEntity<Void> updatePet(@PathVariable int petId, @RequestBody PetsDTO pet) {
        pet.setPetId(petId);
        petsService.updatePet(pet);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 펫 삭제
    @DeleteMapping("/{petId}")
    public ResponseEntity<Void> deletePet(@PathVariable int petId) {
        petsService.deletePet(petId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
