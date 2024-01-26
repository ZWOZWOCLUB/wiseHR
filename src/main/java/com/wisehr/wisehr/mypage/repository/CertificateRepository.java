package com.wisehr.wisehr.mypage.repository;

import com.wisehr.wisehr.mypage.entity.Certificate;
import com.wisehr.wisehr.mypage.entity.Degree;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificateRepository extends JpaRepository<Certificate, Integer> {

    List<Certificate> findByMemCode(int memCode);
}
