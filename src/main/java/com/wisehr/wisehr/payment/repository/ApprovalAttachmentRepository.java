package com.wisehr.wisehr.payment.repository;

import com.wisehr.wisehr.payment.entity.ApprovalAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalAttachmentRepository extends JpaRepository<ApprovalAttachment,String> {
}
