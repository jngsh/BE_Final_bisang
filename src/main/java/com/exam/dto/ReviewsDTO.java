package com.exam.dto;

import javax.persistence.Id;

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
public class ReviewsDTO {
	
	int review_id;
	
	int product_id;
	int user_id;
	int order_id;
	String contents;
	String review_image;
	Byte rating;
}
