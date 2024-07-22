package com.exam.entity;

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
public class NonMembers {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int non_member_id;
	
	String username;
	String phone1;
	String phone2;
	String phone3;
	String post;
	String address1;
	String address2;
	String email1;
	String email2;
}
