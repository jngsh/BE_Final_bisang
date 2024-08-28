package com.exam.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "user_activity_log")
public class UserActivityLog {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   int activity_id;
	int user_id;
	String activity_type;
	LocalDateTime activity_date;
}
