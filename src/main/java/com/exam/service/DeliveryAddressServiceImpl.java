package com.exam.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.exam.config.DeliveryAddressMapper;
import com.exam.config.MemberMapper;
import com.exam.dto.DeliveryAddressDTO;
import com.exam.dto.Member;
import com.exam.entity.DeliveryAddress;
import com.exam.repository.DeliveryAddressRepository;

@Service
public class DeliveryAddressServiceImpl implements DeliveryAddressService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	DeliveryAddressRepository deliveryAddressRepository;
	DeliveryAddressMapper deliveryAddressMapper;

	
	
	public DeliveryAddressServiceImpl(DeliveryAddressRepository deliveryAddressRepository,
			DeliveryAddressMapper deliveryAddressMapper) {
		this.deliveryAddressRepository = deliveryAddressRepository;
		this.deliveryAddressMapper = deliveryAddressMapper;
	}



	@Override
	public List<DeliveryAddressDTO> findByUserId(Integer userId) {

		logger.info("delivery userId:{}",userId);
		
		List<DeliveryAddressDTO> address = deliveryAddressMapper.findByUserId(userId);
		
		return address;
	}
	
	@Override
	public DeliveryAddress modifyDeliveryAddress(Integer userId, DeliveryAddressDTO modifyDTO) {
		List<DeliveryAddress> deliveryAddresses = deliveryAddressRepository.findByUsers_UserId(userId);
		
//		if (deliveryAddress == null) {
//			logger.error("delivery with id {} not found",userId);
//			throw new RuntimeException("Delivery Address not found");
//		}
		
		DeliveryAddress deliveryAddress = deliveryAddresses.get(0);
		
		if(modifyDTO.getDeliveryName() != null) deliveryAddress.setDeliveryName(modifyDTO.getDeliveryName());
		if(modifyDTO.getAddress1() != null) deliveryAddress.setAddress1(modifyDTO.getAddress1());
		if(modifyDTO.getAddress2() != null) deliveryAddress.setAddress2(modifyDTO.getAddress2());
		if(modifyDTO.getPost() != null) deliveryAddress.setPost(modifyDTO.getPost());
		if(modifyDTO.getPhone1() != null) deliveryAddress.setPhone1(modifyDTO.getPhone1());
		if(modifyDTO.getPhone2() != null) deliveryAddress.setPhone2(modifyDTO.getPhone2());
		if(modifyDTO.getPhone3() != null) deliveryAddress.setPhone3(modifyDTO.getPhone3());
//		if(modifyDTO.isDefault() != true) deliveryAddress.setDefault(modifyDTO.isDefault());
		
		return deliveryAddressRepository.save(deliveryAddress);
	}

}
