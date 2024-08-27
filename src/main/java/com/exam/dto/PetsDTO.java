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

@Alias("PetsDTO")
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Getter
//@Setter
@ToString
public class PetsDTO {
	
	int petId;
	int userId;
	String petName;
	LocalDate petBirthdate;
	PetType petType;
	int petImageId;
	PetsImageDTO petImage;
   
   public int getPetId() {
		return petId;
	}

	public void setPetId(int petId) {
		this.petId = petId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public LocalDate getPetBirthdate() {
		return petBirthdate;
	}

	public void setPetBirthdate(LocalDate petBirthdate) {
		this.petBirthdate = petBirthdate;
	}

	public PetType getPetType() {
		return petType;
	}

	public void setPetType(PetType petType) {
		this.petType = petType;
	}

	public PetsImageDTO getPetImage() {
		return petImage;
	}

	public void setPetImage(PetsImageDTO petImage) {
		this.petImage = petImage;
	}

	public int getPetImageId() {
		return petImageId;
	}

	public void setPetImageId(int petImageId) {
		this.petImageId = petImageId;
	}

	
	
}