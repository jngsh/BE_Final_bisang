package com.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
