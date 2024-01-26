package com.wisehr.wisehr.pay.repository;

import com.wisehr.wisehr.pay.entity.PayDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PayDetailsRepository extends JpaRepository<PayDetails, String > {
    @Query("SELECT a FROM PayDetails a WHERE a.memCode = :memCode AND a.pdeYymm LIKE %:year% order by a.pdeYymm")
    List<PayDetails> findByMemCodeAndPdeYymmContaining(String year, String memCode);

    List<PayDetails> findByMemCodeOrderByPdeDate(int memCode);
}
