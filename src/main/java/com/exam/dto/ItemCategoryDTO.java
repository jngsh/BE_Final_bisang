package com.exam.dto;

import org.apache.ibatis.type.Alias;

import com.exam.enums.AgeType;
import com.exam.enums.ItemType;
import com.exam.enums.PetType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Alias("ItemCategoryDTO")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ItemCategoryDTO {
	
	String itemType;
	int itemCount;

}
