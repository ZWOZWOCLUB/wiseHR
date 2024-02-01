package com.wisehr.wisehr.setting.repository;

import com.wisehr.wisehr.setting.entity.SettingDocumentFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;

public interface SettingDocumentFileRepository extends JpaRepository<SettingDocumentFile, Integer> {
    SettingDocumentFile findByMemCodeAndDocAtcKind(int memCode, String kind);

    SettingDocumentFile findByDocAtcCode(int docAtcCode);
}
