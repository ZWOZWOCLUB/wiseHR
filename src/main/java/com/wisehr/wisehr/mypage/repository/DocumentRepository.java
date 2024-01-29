package com.wisehr.wisehr.mypage.repository;

import com.wisehr.wisehr.mypage.entity.Attendance;
import com.wisehr.wisehr.mypage.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Integer> {


    Document findByMemCode(int memCode);
}
