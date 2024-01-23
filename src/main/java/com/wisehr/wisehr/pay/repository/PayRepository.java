package com.wisehr.wisehr.pay.repository;

import com.wisehr.wisehr.pay.entity.PayMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayRepository extends JpaRepository<PayMember, Integer> {
}
