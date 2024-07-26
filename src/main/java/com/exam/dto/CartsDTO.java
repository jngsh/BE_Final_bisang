package com.exam.dto;

import java.util.List;

import com.exam.entity.CartItems;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
