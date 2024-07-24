package com.exam.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.exam.dto.ApproveResponse;
import com.exam.dto.CartItemsDTO;
import com.exam.dto.ReadyResponse;
import com.exam.dto.SendToPayDTO;
import com.exam.service.PayService;
import com.exam.service.payment.KakaoPayService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
//@RequiredArgsConstructor
@RequestMapping("/pay")
public class PayController {

//	@Autowired
    private KakaoPayService kakaoPayService;
//	@Autowired
    private PayService payService;
//	@Autowired
    private HttpSession httpSession;
    
    public PayController(KakaoPayService kakaoPayService, PayService payService, HttpSession httpSession) {
    	this.kakaoPayService = kakaoPayService;
    	this.payService = payService;
    	this.httpSession = httpSession;
    }
//    
    
    @GetMapping("/hello")
    public String hello()
    {
    	log.info("KakaoPayService:>>>>>>>>>>>>>> " + kakaoPayService);
    	log.info("PayService:>>>>>>>>>>>>>> " + payService);
    	log.info("HttpSession:>>>>>>>>>>>>>> " + httpSession);
    	return "Hello World";
    }
    @PostMapping("/ready")
    public @ResponseBody ReadyResponse payReady(@RequestBody CartItemsDTO cartItemsDTO) {
    	 ReadyResponse readyResponse = null;
    try {
        // PayService를 통해 이름과 금액 정보를 가져옴
    	log.info("payService:>>>>>>>>>>>>>> " + payService);
    	log.info("CartItemsDTO:>>>>>>>>>>>>>> " + cartItemsDTO);
        SendToPayDTO sendToPayInfo = payService.getSendToPayInfo(cartItemsDTO);

        String combinedName = sendToPayInfo.getCombinedName();
        int totalPrice = sendToPayInfo.getTotalPrice();
        
        log.info("주문 상품 이름: " + combinedName);
        log.info("주문 금액: " + totalPrice);

        // 카카오 결제 준비하기
         readyResponse = kakaoPayService.payReady(combinedName, totalPrice);
        // 세션에 결제 고유번호(tid) 저장
        httpSession.setAttribute("tid", readyResponse.getTid());
        log.info("결제 고유번호: " + readyResponse.getTid());

    }catch(Exception e) {
    	e.printStackTrace();
    }
        return readyResponse;
    }

    @GetMapping("/completed")
    public String payCompleted(@RequestParam("pg_token") String pgToken) {
    
        String tid = (String) httpSession.getAttribute("tid");
        log.info("결제승인 요청을 인증하는 토큰: " + pgToken);
        log.info("결제 고유번호: " + tid);

        // 카카오 결제 요청하기
        ApproveResponse approveResponse = kakaoPayService.payApprove(tid, pgToken);

        return "redirect:/pay/completed";
    }

}
