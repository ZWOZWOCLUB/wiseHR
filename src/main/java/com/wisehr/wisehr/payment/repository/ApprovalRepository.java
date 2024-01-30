package com.wisehr.wisehr.payment.repository;

import com.wisehr.wisehr.payment.entity.Approval;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalRepository extends JpaRepository<Approval, String> {

}
