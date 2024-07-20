package com.exam.dto;

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
public class NonMemberDTO {
	
	@Id
	int non_member_id;
	
	int payment_id;
	String username;
	String phone1;
	String phone2;
	String phone3;
	String post;
	String addr1;
	String addr2;
	String email1;
	String email2;
}
