package com.wisehr.wisehr.approval.repository;

import com.wisehr.wisehr.approval.entity.Referencer;
import com.wisehr.wisehr.approval.entity.ReferencerPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReferencerRepository extends JpaRepository<Referencer, ReferencerPK> {

    List<Referencer> findByReferencerPK_MemCode(long l);

}
