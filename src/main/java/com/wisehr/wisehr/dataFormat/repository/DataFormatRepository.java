package com.wisehr.wisehr.dataFormat.repository;

import com.wisehr.wisehr.dataFormat.entity.DataFormat;
import com.wisehr.wisehr.notice.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataFormatRepository extends JpaRepository<DataFormat, Long> {

    Page<DataFormat> findByDataDeleteStatus(String n, Pageable paging);
}
