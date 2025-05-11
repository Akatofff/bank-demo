package com.example.bankdemo.repository;

import com.example.bankdemo.entity.EmailData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailDataRepository extends JpaRepository<EmailData, Long> {}
