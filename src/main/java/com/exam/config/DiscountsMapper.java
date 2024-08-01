package com.exam.config;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.exam.dto.DiscountProductDTO;

@Mapper
public interface DiscountsMapper {

    public List<DiscountProductDTO> findDiscountProduct(int discountId);
    public List<DiscountProductDTO> findProductsPetType();
    public List<DiscountProductDTO> SortProductsBySalesPrice();
    
    
}
