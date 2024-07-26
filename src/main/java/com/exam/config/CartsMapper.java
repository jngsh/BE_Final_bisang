package com.exam.config;

import com.exam.dto.CartsDTO;
import com.exam.entity.Carts;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CartsMapper {

    // Cart ID로 장바구니 조회
    CartsDTO findCartById(int cartId);

    // 장바구니 생성
    void createCart(Carts cart);

    // 장바구니 삭제
    void deleteCart(int cartId);
}
