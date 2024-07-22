package com.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entity.Categories;

public interface CategoriesRepository extends JpaRepository<Categories, Integer> {

}
