package com.exam.dto;

import java.time.LocalDate;
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
	
	int reviewId;
	
	int productId;
	int userId;
	int orderDetailId;
	int orderId;
	String contents;
	String reviewImage;
	Byte rating;
	LocalDate reviewDate;
	
	String id;
	String productName;
	String productImage;
	
}
