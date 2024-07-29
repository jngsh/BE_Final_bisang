package com.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entity.Sales;

public interface SalesRepository extends JpaRepository<Sales, Integer> {

}
