package com.wisehr.wisehr.mypage.repository;

import com.wisehr.wisehr.mypage.entity.MPDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MPDocumentRepository extends JpaRepository<MPDocument, Integer> {


//    @Query(
//            "select A" +
//                    "from MPDocument A " +
//                    "where A.docAtcKind <> '프로필' and A.docAtcKind <> '서명' and A.memCode = :memCode "
//    )
    MPDocument findByMemCode(int memCode);
}
