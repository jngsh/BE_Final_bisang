package com.exam.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
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

   int user_id;
   String username;
   String id; 
   String pw; 
   String address1; 
   String address2; 
   String post; 
   String email1; 
   String email2;
   String phone1;
   String phone2;
   String phone3; 
   Boolean is_customer;
}
