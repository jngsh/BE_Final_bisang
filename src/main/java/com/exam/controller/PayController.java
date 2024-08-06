package com.exam.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.ApproveResponse;
import com.exam.dto.CartItemsDTO;
import com.exam.dto.ReadyResponse;
import com.exam.dto.SendToPayDTO;
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

	public PayController(KakaoPayService kakaoPayService, PayService payService) {
		this.kakaoPayService = kakaoPayService;
		this.payService = payService;
	}

	@PostMapping("/ready")
	public @ResponseBody ReadyResponse payReady(@RequestBody CartItemsDTO cartItemsDTO, Model m, HttpSession session) {
		ReadyResponse readyResponse = null;
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
			readyResponse = kakaoPayService.payReady(combinedName, totalPrice);
			log.info("갑자기안된다고?");
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

	@GetMapping("/completed")
	public void kakaoPayCompleted(@RequestParam("pg_token") String pgToken, HttpServletResponse response,
			HttpServletRequest request) {

		String tid = (String) ctx.getAttribute("tid");
		log.info("결제승인 요청을 인증하는 토큰: " + pgToken);
		log.info("컨텍스트에담자>>>>>>>>>>>:{}", tid);

		if (tid == null || tid.isEmpty()) {
			log.error("tid 값이 유효하지 않습니다.");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		// 카카오 결제 요청하기
		ApproveResponse approveResponse = kakaoPayService.payApprove(tid, pgToken);
		if (approveResponse == null) {
			log.error("결제 승인 실패");
			try {
				response.sendRedirect("/about");
			} catch (IOException e) {
				e.printStackTrace();
			} // 실패 시 /about으로 리디렉션
			return;
		}

		// User-Agent로 모바일과 데스크탑 구분
		String userAgent = request.getHeader("User-Agent");
		boolean isMobile = userAgent != null && (userAgent.contains("iPhone") || userAgent.contains("Android"));

		// 모바일 또는 데스크탑에 따라 리다이렉트 URL 설정
		if (isMobile) {
			log.info("모바일에서 결제 승인 완료, 모바일 페이지로 리다이렉트합니다.");
			try {
				response.sendRedirect("http://10.10.10.181:5173/shop_order_complete");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return; // 모바일 페이지
		} else {
			log.info("데스크탑에서 결제 승인 완료, 데스크탑 페이지로 리다이렉트합니다.");
			try {
				response.sendRedirect("http://localhost:5173/shop_order_complete");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return; // 데스크탑 페이지
		}
	}

//	@GetMapping("/details")	
//	public ResponseEntity<List<CartItemsDTO>> getCartDetails(@RequestParam int cartId) {
//	    List<CartItemsDTO> cartDetails = payService.getCartItems(cartId);
//	    return ResponseEntity.ok(cartDetails);
//	}
	
	@PostMapping("/details") //api body에 데이터를 실어보낼거면 post , get은 쿼리스트링으로 보낼때 
	public ResponseEntity<List<CartItemsDTO>> getCartDetails(@RequestBody Map<String, Integer> requestBody){
		int cartId = requestBody.get("cartId");
		List<CartItemsDTO> cartDetails = payService.getCartItems(cartId);
		return ResponseEntity.ok(cartDetails);
	}

}
