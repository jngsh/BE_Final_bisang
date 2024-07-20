package com.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entity.Discount;

public interface DiscountRepository extends JpaRepository<Discount, Integer> {

}
