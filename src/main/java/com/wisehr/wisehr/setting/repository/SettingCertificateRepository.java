package com.wisehr.wisehr.setting.repository;

import com.wisehr.wisehr.setting.entity.SettingCertificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SettingCertificateRepository extends JpaRepository<SettingCertificate, Integer> {
    List<SettingCertificate> findByMemCode(int memCode);
}
