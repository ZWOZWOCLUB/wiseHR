package com.wisehr.wisehr.mypage.repository;

import com.wisehr.wisehr.mypage.entity.MPDocumentFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentFileRepository extends JpaRepository<MPDocumentFile, Integer> {


    MPDocumentFile findByMemCodeAndDocAtcKind(long memCode, String kind);
}
