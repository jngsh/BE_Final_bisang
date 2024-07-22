package com.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entity.Products;

public interface ProductsRepository extends JpaRepository<Products, Integer> {

}
