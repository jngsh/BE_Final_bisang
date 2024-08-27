package com.exam.config;

import org.apache.ibatis.annotations.Mapper;

import com.exam.dto.PetsImageDTO;

@Mapper
public interface PetsImageMapper {

	// 펫 이미지 추가
    void addPetImage(PetsImageDTO petsImageDTO);
}
