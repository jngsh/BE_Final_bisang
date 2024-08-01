package com.exam.controller;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.exam.dto.ProductsDTO;
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
				response.sendRedirect("http://10.10.10.151:5173/shop_order_complete");
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

//	 @GetMapping("/ordered-items")
//	    public ProductsDTO getOrderedItems(@RequestParam("CartId") int CartId) {
//	        return payService.getOrderDetails(orderId);
//	    }
}

/////////////////////////////////안볼코드////////////////////////////////////

//
//package com.exam.controller;
//
//import java.util.Enumeration;
//
//import javax.servlet.ServletContext;
//import javax.servlet.http.HttpSession;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.bind.annotation.SessionAttributes;
//
//import com.exam.dto.ApproveResponse;
//import com.exam.dto.CartItemsDTO;
//import com.exam.dto.ReadyResponse;
//import com.exam.dto.SendToPayDTO;
//import com.exam.service.PayService;
//import com.exam.service.payment.KakaoPayService;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@RestController // Controller이면 뷰인줄알고 에러띄움
//@RequestMapping("/pay")
////@SessionAttributes({ "tid2", "tid", "x" })
//public class PayController {
//
//	Logger logger = LoggerFactory.getLogger(getClass());
//
//	@Autowired
//	ServletContext ctx;
//
//	KakaoPayService kakaoPayService;
//	PayService payService;
////   @Autowired
////    private HttpSession httpSession;
//
//	public PayController(KakaoPayService kakaoPayService, PayService payService) {
//		this.kakaoPayService = kakaoPayService;
//		this.payService = payService;
//	}
//
////    @GetMapping("/hello")
////    public String hello()
////    {
////    	log.info("KakaoPayService:>>>>>>>>>>>>>> " + kakaoPayService);
////    	log.info("PayService:>>>>>>>>>>>>>> " + payService);
////    	log.info("HttpSession:>>>>>>>>>>>>>> " + httpSession);
////    	return "Hello World";
////    }
//
////	@PostMapping("/ready2")
////	public @ResponseBody ReadyResponse cccc(Model m) {
////		SessionUtils.addAttribute("tid", "tid값");
////		m.addAttribute("tid2", "hello");
////		return null;
////	}
////
////	@GetMapping("/completed2")
////	public String yyy(Model m) {
////		String tid = SessionUtils.getStringAttributeValue("tid");
////		String tid2 = (String) m.getAttribute("tid2");
////
////		log.info("결제 고유번호2: " + tid);
////		log.info("결제 고유번호22222: " + tid2);
////		return "redirect:/about"; // 수정하기
////
////	}
//
////  @CrossOrigin(origins = "http://localhost:5173")
//	@PostMapping("/ready")
//	public @ResponseBody ReadyResponse payReady(@RequestBody CartItemsDTO cartItemsDTO, Model m, HttpSession session) {
//		ReadyResponse readyResponse = null;
//		try {
//			// PayService를 통해 이름과 금액 정보를 가져옴
//			log.info("payService:>>>>>>>>>>>>>> " + payService);
//			log.info("CartItemsDTO:>>>>>>>>>>>>>> " + cartItemsDTO);
//			SendToPayDTO sendToPayInfo = payService.sendToPayInfo(cartItemsDTO);
//
//			String combinedName = sendToPayInfo.getCombinedName();
//			int totalPrice = sendToPayInfo.getTotalPrice();
//
//			log.info("주문 상품 이름: " + combinedName);
//			log.info("주문 금액: " + totalPrice + " 원");
//
//			// 카카오 결제 준비하기
//			readyResponse = kakaoPayService.payReady(combinedName, totalPrice);
//			log.info("readyResponse:" + readyResponse);
//			if (readyResponse != null) {
//				// 세션에 결제 고유번호(tid) 저장
//				SessionUtils.addAttribute("tid3", readyResponse.getTid());
//				m.addAttribute("tid2", "hello"); // 리퀘스트스코프에 기본적으로 저장 >> @sessionAttributes에 저장함으로써 session스코프가됨
//				ctx.setAttribute("tid", readyResponse.getTid());
//
//				log.info("결제 고유번호1: " + readyResponse.getTid());
//				log.info("sessionUtils에tid저장됨?" + SessionUtils.getStringAttributeValue("tid3"));
//				log.info("model에tid저장됨?" + m.getAttribute("tid2"));
//
//				Enumeration<String> enu = session.getAttributeNames();
//				while (enu.hasMoreElements()) {
//					String key = enu.nextElement();
//					log.info("session {}={} ", key, session.getAttribute(key));
//				}
//
//			} else {
//				log.error("결제 준비 실패");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return readyResponse;
//	}
//
//	@GetMapping("/completed")
//	public String kakaoPayCompleted(@RequestParam("pg_token") String pgToken, Model m) {
//		String tid3 = SessionUtils.getStringAttributeValue("tid3");
//		String tid2 = (String) m.getAttribute("tid2");
//
//		log.info("결제승인 요청을 인증하는 토큰: " + pgToken);
//		log.info("SessionUtilstid:{}", SessionUtils.getStringAttributeValue("tid3"));
//		log.info("model에담은tid: " + tid2);
//		String x = (String) m.getAttribute("x");
//		log.info("다른집에서온친구: " + x);
//
//		String tid = (String) ctx.getAttribute("tid");
//		logger.info("컨텍스트에담자>>>>>>>>>>>:{}", tid);
//
//		if (tid == null || tid.isEmpty()) {
//			log.error("tid 값이 유효하지 않습니다.");
//			return "결제 고유번호(tid)가 유효하지 않습니다.";
//		}
//
//		// 카카오 결제 요청하기
//		ApproveResponse approveResponse = kakaoPayService.payApprove(tid, pgToken);
//		if (approveResponse == null) {
//			log.error("결제 승인 실패");
//			return "redirect:/about";
//		}
//
//		log.info("결제 승인 완료, 성공 페이지로 리다이렉트합니다.");
//		return "redirect:success"; // 수정하기
//
//	}
//
//	@GetMapping("/success")
//	public String paySuccess() {
//		return "결제가 성공적으로 완료되었습니다.";
//	}
//
//}
