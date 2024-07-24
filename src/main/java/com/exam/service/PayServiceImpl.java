package com.exam.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.dto.CartItemsDTO;
import com.exam.dto.ProductsDTO;
import com.exam.dto.SendToPayDTO;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
//@RequiredArgsConstructor
@NoArgsConstructor
@Slf4j
public class PayServiceImpl implements PayService {

	@Autowired
    private CartItemsService cartItemsService;
	@Autowired
    private ProductsService productsService;

//    public PayServiceImpl(CartItemsService cartItemsService, ProductsService productsService) {
//    	log.info(">>>>>>>>>>>>>>>>>>>>>>>.PayServiceImpl 생성자");
//    	this.cartItemsService = cartItemsService;
//    	this.productsService = productsService;
//    }
//    
    
    
    @Override
    public SendToPayDTO getSendToPayInfo(CartItemsDTO cartItemsDTO) {
    	log.info("CartItemsDTO:{}" , cartItemsDTO );
        List<CartItemsDTO> cartItems = cartItemsService.findByCartId(cartItemsDTO.getCartId());
        log.info("cartItems:{}" , cartItems );

        List<ProductsDTO> products = cartItems.stream()
            .map(item -> productsService.getProductById(item.getProductId()))
            .collect(Collectors.toList());

//        String combinedName2 = products.stream()
//            .map(ProductsDTO::getProductName)
//            .collect(Collectors.joining(", "));
        
        log.info("products:{}" , products );
        Stream<ProductsDTO> xxx = products.stream();
        log.info("xxx::{}" , xxx );
        Stream<String>  xxx2 = xxx.map(ProductsDTO::getProductName);
        log.info("xxx2::{}" , xxx2 );
        String xxx3 = xxx2.collect(Collectors.joining(", "));
        log.info("xxx3::{}" , xxx3 );
        
        
        String combinedName = "";

        
        
        if (products.size() > 1) {
            combinedName = products.get(0).getProductName() + " 외 " + (products.size() - 1) + "건";
        }

        int totalPrice = cartItems.stream()
            .mapToInt(item -> item.getAmount() * products.stream()
            .filter(p -> p.getProductId() == item.getProductId())
            .findFirst().get().getProductPrice())
            .sum();

        return new SendToPayDTO(combinedName, totalPrice);
    }




}
