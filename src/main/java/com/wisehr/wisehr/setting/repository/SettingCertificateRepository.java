package com.wisehr.wisehr.setting.repository;

import com.wisehr.wisehr.setting.entity.SettingCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SettingCertificateRepository extends JpaRepository<SettingCertificate, String > {

    List<SettingCertificate> findByMemCode(int memCode);
}
