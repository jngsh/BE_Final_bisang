package com.exam.service;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.exam.dto.ProductsDTO;
import com.exam.dto.ReviewStatsDTO;
import com.exam.dto.ReviewsDTO;
import com.exam.entity.Reviews;

@Service
@Transactional
public interface ReviewsService {

	public Reviews saveReviews(Reviews reviews);
	public List<Integer> getReviewedOrderDetailIds(int userId, int orderId);
	public List<Integer> findReviewedOrderDetailIds(int userId);
	public List<ReviewsDTO> findReviews(int productId);
	public ProductsDTO findProductDetails(int productId);
	public int findReviewsCounts(int productId);
	public List<ReviewsDTO> findReivewsByUserId(int userId);
	public boolean checkReview(int orderDetailId);
	public ReviewStatsDTO getReviewStatsByProductId(int productId);
}
