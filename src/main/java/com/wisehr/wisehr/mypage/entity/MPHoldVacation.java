package com.wisehr.wisehr.mypage.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "hold_vacation")
@AllArgsConstructor
@Getter
@ToString
public class MPHoldVacation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mem_code")
    private int memCode;
    @Column(name = "vct_count")
    private int vctCount;
    @Column(name = "vct_deadline")
    private int vctDeadline;
    @Column(name = "vct_amount_spend_vacation")
    private int vctAmountSpendVacation;


    public MPHoldVacation() {
    }

    public MPHoldVacation memCode(int memCode) {
            this.memCode = memCode;
            return this;
        }

        public MPHoldVacation vctCount(int vctCount) {
            this.vctCount = vctCount;
            return this;
        }

        public MPHoldVacation vctDeadline(int vctDeadline) {
            this.vctDeadline = vctDeadline;
            return this;
        }

    public MPHoldVacation vctAmountSpendVacation(int vctAmountSpendVacation) {
        this.vctAmountSpendVacation = vctAmountSpendVacation;
        return this;
    }

        public MPHoldVacation build() {
            return new MPHoldVacation(memCode, vctCount, vctDeadline, vctAmountSpendVacation);
        }

}
