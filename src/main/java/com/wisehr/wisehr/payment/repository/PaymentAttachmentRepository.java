package com.wisehr.wisehr.payment.repository;

import com.wisehr.wisehr.payment.entity.PaymentAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentAttachmentRepository extends JpaRepository<PaymentAttachment,String> {
}
