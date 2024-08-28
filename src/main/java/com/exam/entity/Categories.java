package com.exam.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.exam.enums.AgeType;
import com.exam.enums.ItemType;
import com.exam.enums.PetType;

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
@Table(name = "categories")
public class Categories {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int category_id;
	
	@Enumerated(EnumType.STRING)
	PetType pet_type;
	
	@Enumerated(EnumType.STRING)
	ItemType item_type;
	
	@Enumerated(EnumType.STRING)
	AgeType age_type;

}
