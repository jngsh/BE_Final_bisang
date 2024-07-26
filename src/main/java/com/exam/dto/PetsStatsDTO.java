package com.exam.dto;



import java.time.LocalDate;

import org.apache.ibatis.type.Alias;

import com.exam.enums.PetType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Alias("PetsStatsDTO")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class PetsStatsDTO {
   
   int petId;
   String petType;
   String ageType;
   int petCount;
   double petRatio; 

}