package com.exam.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
@Entity
public class Users {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   int userId;
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
