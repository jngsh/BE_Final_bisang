package com.exam.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.exam.dto.ApproveResponse;
import com.exam.dto.CartItemsDTO;
import com.exam.dto.OrderDetailsDTO;
import com.exam.dto.ReadyResponse;
import com.exam.dto.SendToPayDTO;
import com.exam.entity.OrderDetails;
import com.exam.entity.Orders;
import com.exam.entity.Products;
import com.exam.entity.Reviews;
import com.exam.entity.Sales;
import com.exam.entity.Users;
import com.exam.service.CartItemsService;
import com.exam.service.OrderDetailsService;
import com.exam.service.PayService;
import com.exam.service.payment.KakaoPayService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController // Controller이면 뷰인줄알고 에러띄움
@RequestMapping("/pay")
//@SessionAttributes({ "tid2", "tid", "x" })
public class PayController {

	@Autowired
	ServletContext ctx;

	KakaoPayService kakaoPayService;
	PayService payService;
	OrderDetailsService orderDetailsService;
	CartItemsService cartItemsService;

	public PayController(KakaoPayService kakaoPayService, PayService payService,
			OrderDetailsService orderDetailsService, CartItemsService cartItemsService) {
		this.kakaoPayService = kakaoPayService;
		this.payService = payService;
		this.orderDetailsService = orderDetailsService;
		this.cartItemsService = cartItemsService;
	}

	@PostMapping("/ready")
	public @ResponseBody ReadyResponse payReady(@RequestBody CartItemsDTO cartItemsDTO, Model m, HttpSession session,HttpServletRequest request) {
		ReadyResponse readyResponse = null;
//			HttpServletRequest request = null;
		try {
			// PayService를 통해 이름과 금액 정보를 가져옴
			log.info("payService:>>>>>>>>>>>>>> " + payService);
			log.info("CartItemsDTO:>>>>>>>>>>>>>> " + cartItemsDTO);
			SendToPayDTO sendToPayInfo = payService.sendToPayInfo(cartItemsDTO);

			String combinedName = sendToPayInfo.getCombinedName();
			int totalPrice = sendToPayInfo.getTotalPrice();
			
			log.info("주문 상품 이름: " + combinedName);
			log.info("주문 금액: " + totalPrice + " 원");

			// 카카오 결제 준비하기
			readyResponse = kakaoPayService.payReady(combinedName, totalPrice, request);
			log.info("readyResponse:" + readyResponse);
			if (readyResponse != null) {
				// 세션에 결제 고유번호(tid) 저장해야하는데 안돼서 일단 ctx에 저장
				ctx.setAttribute("tid", readyResponse.getTid());

				Enumeration<String> enu = session.getAttributeNames();
				while (enu.hasMoreElements()) {
					String key = enu.nextElement();
					log.info("session {}={} ", key, session.getAttribute(key));
				}

			} else {
				log.error("결제 준비 실패");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return readyResponse;
	}

	@RequestMapping(value = "/completed")
	public RedirectView kakaoPayCompleted(@RequestParam("pg_token") String pgToken, HttpServletResponse response,
			HttpServletRequest request) {
		RedirectView redirectView = new RedirectView();

		String tid = (String) ctx.getAttribute("tid");
		log.info("결제승인 요청을 인증하는 토큰: " + pgToken);
		log.info("컨텍스트에담자>>>>>>>>>>>:{}", tid);

		if (tid == null || tid.isEmpty()) {
			log.error("tid 값이 유효하지 않습니다.");
//			redirectView.setUrl("http://localhost:5173/about");
			redirectView.setUrl("http://10.10.10.133:5173/about");
			/////////////////////////////////////
//			redirectView.setUrl("https://peterpet.store/page-not-found");
//			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return redirectView;
		}

		// 카카오 결제 요청하기
		ApproveResponse approveResponse = kakaoPayService.payApprove(tid, pgToken);
		if (approveResponse == null) {
			log.error("결제 승인 실패");
//			redirectView.setUrl("http://localhost:5173/about");
			redirectView.setUrl("http://10.10.10.133:5173/about");
			return redirectView;
		}

		// User-Agent로 모바일과 데스크탑 구분
		String userAgent = request.getHeader("User-Agent");
		boolean isMobile = userAgent != null && (userAgent.contains("iPhone") || userAgent.contains("Android"));

		// 모바일 또는 데스크탑에 따라 리다이렉트 URL 설정
		if (isMobile) {
			log.info("모바일에서 결제 승인 완료, 모바일 페이지로 리다이렉트합니다.");
			redirectView.setUrl("http://10.10.10.133:5173/orderCompleted"); // ip주소 변경될 때마다 변경
//			redirectView.setUrl("https://peterpet.store/orderCompleted"); // ip주소 변경될 때마다 변경
			log.info("모바일페이지:{}", redirectView);
			return redirectView; // 모바일 페이지
		} else {
			log.info("데스크탑에서 결제 승인 완료, 데스크탑 페이지로 리다이렉트합니다.");
			redirectView.setUrl("http://localhost:5173/orderCompleted");
//			redirectView.setUrl("https://peterpet.store/orderCompleted"); // ip주소 변경될 때마다 변경
			log.info("데스크탑페이지:{}", redirectView);
			log.info("check point");
			return redirectView; // 데스크탑 페이지
		}
	}



	// get방식
	// orders와 orderDetails에 db 옮기고, 옮겨진거 확인 되면 cartId로 cartitems 테이블 항목 삭제
	@GetMapping("/details/{cartId}") // api body에 데이터를 실어보낼거면 post , get은 쿼리스트링으로 보낼때
	public ResponseEntity<Map<String, Object>> getOrderDetails(@PathVariable Integer cartId) {

		Orders orders = orderDetailsService.findOrder(cartId);
		if (orders == null) {
			log.error("주문 정보를 생성하는 데 실패했습니다. cartId: {}", cartId);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("error", "주문 정보를 생성하는 데 실패했습니다."));
		}
		log.info("orders 확인: {}", orders);

		// 주문 상세내역 가져오기
		List<OrderDetailsDTO> orderDetailsList = orderDetailsService.findOrderDetails(cartId);
		if (orderDetailsList == null || orderDetailsList.isEmpty()) {
			log.error("주문 상세내역을 가져오는 데 실패했습니다. cartId: {}", cartId);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("error", "주문 상세내역을 가져오는 데 실패했습니다."));
		}

		// 주문상세내역 저장 및 orderId사용
		List<OrderDetails> orderDetails = orderDetailsService.saveAllOrderDetails(orders, orderDetailsList);
		if (orderDetails == null || orderDetails.isEmpty()) {
			log.error("주문 상세내역을 저장하는 데 실패했습니다. cartId: {}", cartId);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("error", "주문 상세내역을 저장하는 데 실패했습니다."));
		}
		
		try {
			if (orders == null || orderDetails == null) {
				return ResponseEntity.badRequest().body(null);
			}
			
			for (OrderDetails orderDetail : orderDetails) {
				Sales sales = new Sales();
				sales.setOrderId(orderDetail.getOrderId());
				sales.setProductId(orderDetail.getProductId());
				sales.setSaleAmount(orderDetail.getAmount());
				sales.setSalePrice(orderDetail.getTotalPrice());
				sales.setSaleDate(orders.getOrderDate());
				orderDetailsService.saveSales(sales);
			}
			
		}catch (Exception e) {
			log.error("sales 데이터 저장 실패: {}", e);
		}

		
		int orderId = orderDetails.get(0).getOrderId();
		
		log.info("입력될orderId:{}",orderId);
	    List<OrderDetailsDTO> orderDetailsProducts = orderDetailsService.findOrderDetailsProducts(orderId);
	    log.info("orderDetailsProducts:{}",orderDetailsProducts);
	    if (orderDetailsProducts == null || orderDetailsProducts.isEmpty()) {
	        log.error("주문 상세내역과 제품 정보를 가져오는 데 실패했습니다. orderId: {}", orderId);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(Map.of("error", "주문 상세내역과 제품 정보를 가져오는 데 실패했습니다."));
	    }

		// 위 메서드 다 실행되면 cartItems 테이블 삭제 실행
		try {
			cartItemsService.removeAllItems(cartId);
			log.info("cartId {}의 모든 장바구니 항목이 삭제되었습니다.", cartId);
		} catch (Exception e) {
			log.error("장바구니 항목을 삭제하는 데 실패했습니다. cartId: {}", cartId, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("error", "장바구니 항목을 삭제하는 데 실패했습니다."));
		}

		
		
		
		
		log.info("orderDetailsProducts 확인: {}", orderDetailsProducts);

		Map<String, Object> response = new HashMap<>();
		response.put("orderDetails", orderDetailsProducts);
		response.put("orderId", orderId);

		log.info("이렇게도 확인 가능?:{}", ResponseEntity.ok(response));
		return ResponseEntity.ok(response);// >>여기서 하나(id) 뽑아오기 ','

	}
}

