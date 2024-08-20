package com.exam.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.exam.entity.OrderDetails;
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



	@PostMapping("/{orderDetailId}/{productId}/{userId}")
    public ResponseEntity<Reviews> createReviews(@PathVariable int orderDetailId,@PathVariable int productId,@PathVariable int userId, @RequestBody Reviews reviews){
    	
    	
    	try {
    		OrderDetails order = orderDetailsService.findByOrderDetailId(orderDetailId);
    		Products product = productsService.findByProductsId(productId);
    		Users user = usersService.findByUserId(userId);
    		
    		if (order == null || product == null || user == null) {
                return ResponseEntity.badRequest().body(null);
            }
    		
    		reviews.setOrderDetails(order);
    		reviews.setProducts(product);
    		reviews.setUsers(user);
    		
    		Reviews savedReviews = reviewsService.saveReviews(reviews);
    		logger.info("saveReview:{}",savedReviews);
    		return ResponseEntity.ok(savedReviews);
    	} catch (Exception e) {
    		logger.error("Error creating review: ", e);
            return ResponseEntity.status(500).body(null);
    	}
    }
	
//	@GetMapping("/")
}