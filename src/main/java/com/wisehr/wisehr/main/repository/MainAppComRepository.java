package com.wisehr.wisehr.main.repository;

import com.wisehr.wisehr.main.entity.MainAppCom;
import com.wisehr.wisehr.main.entity.MainMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MainAppComRepository extends JpaRepository<MainAppCom, Long> {

    List<MainAppCom> findByMainMemberAndAppStateAndAppDateBetween(MainMember mainMember, String 승인, LocalDate currentDate, LocalDate sevenDaysBefore);
}
