package com.exam.dto;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.exam.entity.CartItems;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Alias("CartsDTO")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CartsDTO {

	int cartId;
    int userId;
    
    @JsonIgnore
    List<CartItemsDTO> items;
}
