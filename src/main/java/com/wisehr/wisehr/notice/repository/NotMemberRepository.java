package com.wisehr.wisehr.notice.repository;

import com.wisehr.wisehr.notice.entity.NotMember;

import java.util.List;

public interface NotMemberRepository {
    List<NotMember> findAll();
}
