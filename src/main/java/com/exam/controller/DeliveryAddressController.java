package com.exam.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.CartItemsDTO;
import com.exam.dto.CartsDTO;
import com.exam.dto.DeliveryAddressDTO;
import com.exam.entity.DeliveryAddress;
import com.exam.service.CartService;
import com.exam.service.DeliveryAddressService;

@RestController
@RequestMapping("/deliveryAddr")
public class DeliveryAddressController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	DeliveryAddressService deliveryAddressService;
	
	public DeliveryAddressController(DeliveryAddressService deliveryAddressService) {
		this.deliveryAddressService = deliveryAddressService;
	}



	@GetMapping("/{userId}")
	public List<DeliveryAddressDTO> getDeliveryAddresses(@PathVariable Integer userId){
		if (deliveryAddressService == null) {
            throw new IllegalStateException("DeliveryAddressService is not initialized.");
        }
		return deliveryAddressService.findByUserId(userId);
	}
    
}