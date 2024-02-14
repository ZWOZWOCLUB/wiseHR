package com.wisehr.wisehr.mypage.repository;

import com.wisehr.wisehr.mypage.entity.MPDocumentFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MPDocumentFileRepository extends JpaRepository<MPDocumentFile, Integer> {


    MPDocumentFile findByMemCodeAndDocAtcKind(long memCode, String kind);

    List<MPDocumentFile> findByMemCodeAndDocAtcKindNotAndDocAtcKindNot(int memCode, String 프로필, String 서명);
}
