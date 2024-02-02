package com.wisehr.wisehr.payment.repository;

import com.wisehr.wisehr.payment.entity.Approval;
import com.wisehr.wisehr.payment.entity.ApprovalMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApprovalRepository extends JpaRepository<Approval, String> {

}
