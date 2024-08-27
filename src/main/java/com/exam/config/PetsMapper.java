package com.exam.config;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import com.exam.dto.PetsDTO;

@Mapper
public interface PetsMapper {

    // 모든 펫 조회
    List<PetsDTO> getAllPets();

    // 특정 유저의 펫 조회
    List<PetsDTO> getPetsByUserId(int userId);

    // 특정 펫 조회
    PetsDTO getPetById(int petId);

    // 펫 추가
    void addPet(Map<String, Object> petData);


    // 펫 정보 업데이트
    void updatePet(PetsDTO pet);

    // 펫 삭제
    void deletePet(int petId);
}
