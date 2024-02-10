package com.wisehr.wisehr.notice.repository;

import com.wisehr.wisehr.notice.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface NoticeRepository extends JpaRepository<Notice, String> {

    //공지제목조회
    List<Notice> findByNotNameContaining(String search);
    //공지내용조회
    List<Notice> findByNotCommentContaining(String search);
    //공지작성자조회
    List<Notice> findByNotMemberMemNameContaining(String search);

    @Query(value = "SELECT * FROM notice ORDER BY CAST(SUBSTRING(notCode, 4) AS UNSIGNED) ASC",
            countQuery = "SELECT count(*) FROM notice",
            nativeQuery = true)
    Page<Notice> findAllWithCustomOrder(Pageable pageable);
}
