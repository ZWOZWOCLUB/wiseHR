package com.wisehr.wisehr.notice.repository;

import com.wisehr.wisehr.notice.entity.NotMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotMemberRepository extends JpaRepository<NotMember, String> {
    List<NotMember> findAll();
}
