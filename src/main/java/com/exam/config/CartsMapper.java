package com.exam.config;

import org.apache.ibatis.annotations.Mapper;

import com.exam.entity.Carts;

@Mapper
public interface CartsMapper {
    void insertCart(Carts cart);
}
