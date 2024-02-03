package com.wisehr.wisehr.mypage.repository;

import com.wisehr.wisehr.mypage.entity.MPDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<MPDocument, Integer> {


    MPDocument findByMemCode(int memCode);
}
