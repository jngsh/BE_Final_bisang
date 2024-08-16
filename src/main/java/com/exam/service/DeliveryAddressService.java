package com.exam.service;

import java.util.List;
import java.util.Map;

import com.exam.dto.DeliveryAddressDTO;
import com.exam.dto.Member;
import com.exam.entity.DeliveryAddress;

public interface DeliveryAddressService {

//	public List<DeliveryAddress> findByUsersUserId(int userId);
	public List<DeliveryAddressDTO> findByUserId(Integer userId);
}
