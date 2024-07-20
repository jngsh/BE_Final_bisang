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
public class PetsDTO {
   
   int pet_id;
   int customer_id;
   String pet_name;
   LocalDate pet_birthdate;
   String pet_type;

}