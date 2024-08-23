package com.exam.config;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.exam.dto.ProductsDTO;
import com.exam.entity.Products;

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
    
    public String findCategoryCode(int categoryId);
    public Integer findCategoryIdByCode(Map<String, Object> type);
    
}
