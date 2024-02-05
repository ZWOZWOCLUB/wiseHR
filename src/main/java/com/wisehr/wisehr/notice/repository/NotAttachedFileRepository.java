package com.wisehr.wisehr.notice.repository;

import com.wisehr.wisehr.notice.entity.NotAttachedFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotAttachedFileRepository extends JpaRepository<NotAttachedFile, String> {

    List<NotAttachedFile> findByNotice_NotName(String search);

    List<NotAttachedFile> findByNotice_NotCode(String search);
}
