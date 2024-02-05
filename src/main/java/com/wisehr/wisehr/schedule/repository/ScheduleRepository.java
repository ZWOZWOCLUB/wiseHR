package com.wisehr.wisehr.schedule.repository;

import com.wisehr.wisehr.approval.entity.ApprovalSchedule;
import com.wisehr.wisehr.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, String > {
}
