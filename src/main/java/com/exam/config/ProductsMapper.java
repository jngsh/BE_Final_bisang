package com.exam.config;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.exam.entity.Products;

import java.util.List;

@Mapper
public interface ProductsMapper {

    List<Products> findAll();
}