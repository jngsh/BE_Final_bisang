package com.exam.config;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.exam.dto.DiscountProductDTO;
import com.exam.dto.DiscountsDTO;

@Mapper
public interface DiscountsMapper {

    public List<DiscountProductDTO> findDiscountProduct();
    public List<DiscountProductDTO> findProductsPetType();
    public List<DiscountProductDTO> SortProductsBySalesPrice();
    
    public List<DiscountsDTO> DiscountProductJoin();
    
    
}
