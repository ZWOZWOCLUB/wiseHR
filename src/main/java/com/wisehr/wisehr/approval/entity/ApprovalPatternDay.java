package com.wisehr.wisehr.approval.entity;

import com.wisehr.wisehr.approval.dto.ApprovalWeekDayDTO;
import com.wisehr.wisehr.approval.dto.ApprovalWorkPatternDTO;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "pattern_day")
public class ApprovalPatternDay {

    @EmbeddedId
    private ApprovalPatternDayPK approvalPatternDayPK;

    @Override
    public String toString() {
        return "ApprovalPatternDay{" +
                "approvalPatternDayPK=" + approvalPatternDayPK +
                '}';
    }

    public ApprovalPatternDay() {
    }

    public ApprovalPatternDay(ApprovalPatternDayPK approvalPatternDayPK) {
        this.approvalPatternDayPK = approvalPatternDayPK;
    }

    public ApprovalPatternDayPK getApprovalPatternDayPK() {
        return approvalPatternDayPK;
    }

    public void setApprovalPatternDayPK(ApprovalPatternDayPK approvalPatternDayPK) {
        this.approvalPatternDayPK = approvalPatternDayPK;
    }
}
