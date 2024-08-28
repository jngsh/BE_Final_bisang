package com.exam.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.CartItemsDTO;
import com.exam.dto.CartsDTO;
import com.exam.dto.OrderDetailsDTO;
import com.exam.dto.OrdersAccountDTO;
import com.exam.dto.ProductsDTO;
import com.exam.dto.ReviewStatsDTO;
import com.exam.dto.ReviewsDTO;
import com.exam.entity.OrderDetails;
import com.exam.entity.Orders;
import com.exam.entity.Products;
import com.exam.entity.Reviews;
import com.exam.entity.Users;
import com.exam.service.CartService;
import com.exam.service.OrderDetailsService;
import com.exam.service.ProductsService;
import com.exam.service.ReviewsService;
import com.exam.service.UsersService;

@RestController
@RequestMapping("/review")
public class ReviewsController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
    ReviewsService reviewsService;
    OrderDetailsService orderDetailsService;
    ProductsService productsService;
    UsersService usersService;
    
    public ReviewsController(ReviewsService reviewsService, OrderDetailsService orderDetailsService,
			ProductsService productsService, UsersService usersService) {
		this.reviewsService = reviewsService;
		this.orderDetailsService = orderDetailsService;
		this.productsService = productsService;
		this.usersService = usersService;
	}

    //작성 가능한 리뷰 조회
    @GetMapping("/{userId}")
    public ResponseEntity<List<OrdersAccountDTO>> findReview(@PathVariable int userId){
    	try {
    		List<Integer> reviewedOrderDetailIds = reviewsService.findReviewedOrderDetailIds(userId);
    		
    		List<OrdersAccountDTO> orders = orderDetailsService.FindOrdersAndDetails(userId);
    		
    		
    		for (OrdersAccountDTO orderDTO : orders) {
    			List<OrderDetailsDTO> filteredOrderDetailsList = orderDetailsService.findOrderDetailsProducts(orderDTO.getOrderId()).stream()
    					.filter(detailDTO -> !reviewedOrderDetailIds.contains(detailDTO.getOrderDetailId()))
    					.collect(Collectors.toList());
    			
    			orderDTO.setOrderDetails(filteredOrderDetailsList);
    		}
    		
    		List<OrdersAccountDTO> filteredOrders = orders.stream()
    				.filter(OrderDTO -> !OrderDTO.getOrderDetails().isEmpty())
    				.collect(Collectors.toList());
    		
    		if (filteredOrders.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
    		return ResponseEntity.ok(filteredOrders);
    	} catch(Exception e) {
    		logger.error("Error get review:",e);
    		return ResponseEntity.status(500).body(null);
    	}
    }

    //리뷰 작성
	@PostMapping("/{orderDetailId}/{productId}/{userId}")
    public ResponseEntity<Reviews> createReviews(@PathVariable int orderDetailId,@PathVariable int productId,@PathVariable int userId, @RequestBody Reviews reviews){
    	
    	
    	try {
    		OrderDetails order = orderDetailsService.findByOrderDetailId(orderDetailId);
    		Products product = productsService.findByProductsId(productId);
    		Users user = usersService.findByUserId(userId);
    		Orders orders = orderDetailsService.getOrdersByOrderDetailId(orderDetailId);
    				
    		if (order == null || product == null || user == null) {
                return ResponseEntity.badRequest().body(null);
            }
    		
    		reviews.setOrderDetails(order);
    		reviews.setProducts(product);
    		reviews.setUsers(user);
    		reviews.setOrders(orders);
    		
    		logger.info("review:{}",reviews);
    		Reviews savedReviews = reviewsService.saveReviews(reviews);
    		logger.info("saveReview:{}",savedReviews);
    		return ResponseEntity.ok(savedReviews);
    	} catch (Exception e) {
    		logger.error("Error creating review: ", e);
            return ResponseEntity.status(500).body(null);
    	}
    }
	
	//상세페이지 리뷰 조회
	@GetMapping("/product-review/{productId}")
	public ResponseEntity<List<ReviewsDTO>> findReviews(@PathVariable int productId){
		try {
			List<ReviewsDTO> reviews = reviewsService.findReviews(productId);
			
			return ResponseEntity.ok(reviews);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	//리뷰 작성할 상품 조회
	@GetMapping("/productDetail/{productId}")
	public ResponseEntity<ProductsDTO> findProductDetails(@PathVariable int productId){
		try {
			ProductsDTO productDetails = reviewsService.findProductDetails(productId);
			return ResponseEntity.ok(productDetails);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	//각 상품의 리뷰수
	@GetMapping("/review-count/{productId}")
	public ResponseEntity<Integer> getReviewCounts(@PathVariable int productId){
		try {
			int counts = reviewsService.findReviewsCounts(productId);
			return ResponseEntity.ok(counts);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	//작성한 리뷰 조회
	@GetMapping("/reviewed/{userId}")
	public ResponseEntity<List<ReviewsDTO>> getReviewed(@PathVariable int userId){
		try {
			List<ReviewsDTO> reviews = reviewsService.findReivewsByUserId(userId);
			logger.info("logger: {}", reviews);
			return ResponseEntity.ok(reviews);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
	//주문 내역에서 작성 가능한 리뷰 조회
	@GetMapping("/exist/{orderDetailId}")
	public ResponseEntity<Boolean> checkReviewExistence(@PathVariable int orderDetailId) {
		try {
		boolean exists = reviewsService.checkReview(orderDetailId);
	    return ResponseEntity.ok(exists);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	//메인페이지 리뷰 평균 별점, 총 리뷰수 조회
	@GetMapping("/main-product-review/{productId}")
	public ResponseEntity<ReviewStatsDTO> getReviewStats(@PathVariable int productId){
		try {
			logger.info("{}",productId);
			ReviewStatsDTO reviewStats = reviewsService.getReviewStatsByProductId(productId);
			logger.info("reviews{}",reviewStats);
		    return ResponseEntity.ok(reviewStats);
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
	}
	
	
}