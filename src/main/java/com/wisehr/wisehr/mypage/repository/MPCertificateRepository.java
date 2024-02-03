package com.wisehr.wisehr.mypage.repository;

import com.wisehr.wisehr.mypage.entity.MPCertificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MPCertificateRepository extends JpaRepository<MPCertificate, Integer> {

    List<MPCertificate> findByMemCode(int memCode);
}
