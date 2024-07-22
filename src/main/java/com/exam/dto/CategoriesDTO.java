package com.exam.dto;

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
public class CategoriesDTO {
	
	int category_id;
	PetType pet_type;
	ItemType item_type;
	AgeType age_type;

}
