package com.exam.config;

import com.exam.dto.ProductsDTO;
import com.exam.entity.Products;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductsMapper {

    // 상품 ID로 상품 조회
    ProductsDTO findProductById(int productId);

    // 모든 상품 조회
    List<ProductsDTO> findAllProducts();

    // 상품 추가
    void addProduct(Products product);

    // 상품 수정
    void updateProduct(Products product);

    // 상품 삭제
    void deleteProduct(int productId);
    
}
