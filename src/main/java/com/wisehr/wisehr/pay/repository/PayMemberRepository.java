package com.wisehr.wisehr.pay.repository;

import com.wisehr.wisehr.pay.entity.PayMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PayMemberRepository extends JpaRepository<PayMember, Integer> {
    PayMember findAllByMemCode(int memCode);
}