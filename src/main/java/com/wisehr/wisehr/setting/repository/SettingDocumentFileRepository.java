package com.wisehr.wisehr.setting.repository;

import com.wisehr.wisehr.setting.entity.SettingDocumentFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface SettingDocumentFileRepository extends JpaRepository<SettingDocumentFile, Integer> {
    SettingDocumentFile findByMemCodeAndDocAtcKind(int memCode, String kind);

    SettingDocumentFile findByDocAtcCode(int docAtcCode);

    @Query(
            "select A " +
            "from SettingDocumentFile A " +
            "where A.docAtcKind <> '프로필' and A.docAtcKind <> '서명' and A.memCode = :memCode "
    )
    List<SettingDocumentFile> findByMemCode(int memCode);
}
