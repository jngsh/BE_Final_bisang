package com.exam.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "pets")
public class Pets {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   int pet_id;
   int user_id;
   String pet_name;
   LocalDate pet_birthdate;
   @Enumerated(EnumType.STRING)
   PetType pet_type;
}