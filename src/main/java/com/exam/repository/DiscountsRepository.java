package com.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entity.Discounts;

public interface DiscountsRepository extends JpaRepository<Discounts, Integer> {

}
