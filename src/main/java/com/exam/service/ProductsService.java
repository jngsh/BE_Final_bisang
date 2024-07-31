package com.exam.service;

import java.util.List;

import com.exam.dto.ProductsDTO;

public interface ProductsService {
    ProductsDTO findByProductId(int productId);
    List<ProductsDTO> SortProductsBySalesPrice();
    List<ProductsDTO> findProductsPetType();
}
