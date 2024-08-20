package com.exam.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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
public class UsersDTO {

   int userId;
   
   @NotBlank(message = "이름은 필수로 입력해야 합니다.")
   String username;
   
   @NotBlank(message = "id는 필수로 입력해야 합니다.")
   String id;
   
   @Size(min = 8, max = 12, message = "password는 8~12자의 길이를 가져야 합니다.")
   String pw; 
   String address1; 
   String address2; 
   String post; 
   String email1; 
   String email2;
   String phone1;
   String phone2;
   String phone3; 
   Boolean isCustomer;
   
   CartsDTO carts;
   
   DeliveryAddressDTO deliveryAddress;
   
   public UsersDTO(String id, String pw) {
	   this.id= id;
	   this.pw=pw;
   }
   
   @Getter
   @Setter
   public static class IdRequest {
	    String id;
	}
   
   @Getter
   @Setter
   public static class PwRequest {
	   int userId;
	   String pw;
   }
   
   @Data
   public static class UsersModifyDTO{
	   int userId;
	   @Size(min = 8, max = 12, message = "password는 8~12자의 길이를 가져야 합니다.")
	   String pw;
	   String address1; 
	   String address2; 
	   String post; 
	   String email1; 
	   String email2;
	   String phone1;
	   String phone2;
	   String phone3;
   }
   
   @Data
   public static class EmailRequest{
	   String email1;
	   String email2;
   }
   
   
}
