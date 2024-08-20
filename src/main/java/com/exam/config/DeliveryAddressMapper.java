package com.exam.config;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.exam.dto.DeliveryAddressDTO;
import com.exam.dto.UsersDTO;


@Mapper
public interface DeliveryAddressMapper {

	// 저장된 배송지 불러오기
	List<DeliveryAddressDTO> findByUserId(int userId);
	
	
}
