package com.exam.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class PayController {
	
	Logger logger = LoggerFactory.getLogger(getClass());

 KakaoPayService kakaoPayService;
 PayService payService;
//   @Autowired
//    private HttpSession httpSession;
    
    public PayController(KakaoPayService kakaoPayService, PayService payService) {
	this.kakaoPayService = kakaoPayService;
	this.payService = payService;
}
	
  
    
//    @GetMapping("/hello")
//    public String hello()
//    {
//    	log.info("KakaoPayService:>>>>>>>>>>>>>> " + kakaoPayService);
//    	log.info("PayService:>>>>>>>>>>>>>> " + payService);
//    	log.info("HttpSession:>>>>>>>>>>>>>> " + httpSession);
//    	return "Hello World";
//    }
    
    
    
//  @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/ready")
    public @ResponseBody ReadyResponse payReady(@RequestBody CartItemsDTO cartItemsDTO) {
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
         log.info("readyResponse:"+readyResponse);
       if(readyResponse !=null) {
         // 세션에 결제 고유번호(tid) 저장
    	   SessionUtils.addAttribute("tid", readyResponse.getTid());
           log.info("결제 고유번호1: " + readyResponse.getTid());
        log.info("sessionUtils에tid저장됨?"+SessionUtils.getStringAttributeValue("tid"));
       }else {
    	   log.error("결제 준비 실패");
       }
    }catch(Exception e) {
    	e.printStackTrace();
    }
        return readyResponse;
    }
    
    

    @GetMapping("/completed")
    public String payCompleted(@RequestParam("pg_token") String pgToken) {
        log.info("httpSessiontidtidtidtidtidtidit:{}",SessionUtils.getStringAttributeValue("tid"));       
    	String tid = SessionUtils.getStringAttributeValue("tid");
        log.info("결제승인 요청을 인증하는 토큰: " + pgToken);
        log.info("결제 고유번호2: " + tid);

        if (tid == null || tid.isEmpty()) {	
            log.error("tid 값이 유효하지 않습니다.");
            return "결제 고유번호(tid)가 유효하지 않습니다.";
        }
        
        // 카카오 결제 요청하기
        ApproveResponse approveResponse = kakaoPayService.payApprove(tid, pgToken);
        if(approveResponse == null) {
        	log.error("결제 승인 실패");
        	return "redirect:/about";
        }
        
        log.info("결제 승인 완료, 성공 페이지로 리다이렉트합니다.");
        return "redirect:/about";  //수정하기
        
    }
    
    
    @GetMapping("/success")
    public String paySuccess() {
    	return "결제가 성공적으로 완료되었습니다.";
    }
    


}
