package com.wisehr.wisehr.payment.repository;

import com.wisehr.wisehr.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, String> {

}
