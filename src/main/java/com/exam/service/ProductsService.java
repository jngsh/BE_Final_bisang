package com.exam.service;

import com.exam.dto.ProductsDTO;

public interface ProductsService {
    ProductsDTO findByProductId(int productId);

}
