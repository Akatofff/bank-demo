package com.example.bankdemo.repository;

import com.example.bankdemo.entity.PhoneData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneDataRepository extends JpaRepository<PhoneData, Long> {}
