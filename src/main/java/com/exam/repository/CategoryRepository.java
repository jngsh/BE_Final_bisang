package com.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
