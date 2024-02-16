package com.wisehr.wisehr.mypage.repository;

import com.wisehr.wisehr.mypage.entity.MPSalAttachedFile;
import com.wisehr.wisehr.mypage.entity.MPSalary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MPSalAttachedFileRepository extends JpaRepository<MPSalAttachedFile, Integer> {

    MPSalAttachedFile findBySalCode(String salCode);
}
