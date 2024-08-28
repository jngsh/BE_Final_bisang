package com.exam.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entity.Reviews;

public interface ReviewsRepository extends JpaRepository<Reviews, Integer> {
    
}
