package com.exam.service;

import java.util.List;
import com.exam.dto.DeliveryAddressDTO;
import com.exam.entity.DeliveryAddress;

public interface DeliveryAddressService {

	public List<DeliveryAddressDTO> findByUserId(Integer userId);
	public DeliveryAddress modifyDeliveryAddress(Integer userId, DeliveryAddressDTO modifyDTO);
}
