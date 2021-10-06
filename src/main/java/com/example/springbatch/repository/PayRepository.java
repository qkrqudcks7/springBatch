package com.example.springbatch.repository;

import com.example.springbatch.entity.Pay;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Id;

public interface PayRepository extends JpaRepository<Pay, Id> {
}
