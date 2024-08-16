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
	

}
