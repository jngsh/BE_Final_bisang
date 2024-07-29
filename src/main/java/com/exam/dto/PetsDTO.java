package com.exam.dto;



import java.time.LocalDate;

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
public class PetsDTO {
   
   int petId;
   int userId;
   String petName;
   LocalDate petBirthdate;
   PetType petType;

}