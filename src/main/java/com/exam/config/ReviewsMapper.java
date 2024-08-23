package com.exam.config;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.exam.dto.ReviewsDTO;

@Mapper
public interface ReviewsMapper {

	public List<Integer> findOrderDetailIdByUserId(Map<String, Integer> params);
	public List<Integer> findOrderDetailIdsByUserId(int userId);
	public List<ReviewsDTO> findReviews(int productId);
	public int findReviewCounts(int productId);
    
}
