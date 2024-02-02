package com.wisehr.wisehr.mypage.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "hold_vacation")
@AllArgsConstructor
@Getter
@ToString
public class HoldVacation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mem_code")
    private int memCode;
    @Column(name = "vct_count")
    private int vctCount;
    @Column(name = "vct_deadline")
    private int vctDeadline;


    public HoldVacation() {
    }

    public HoldVacation memCode(int memCode) {
            this.memCode = memCode;
            return this;
        }

        public HoldVacation vctCount(int vctCount) {
            this.vctCount = vctCount;
            return this;
        }

        public HoldVacation vctDeadline(int vctDeadline) {
            this.vctDeadline = vctDeadline;
            return this;
        }

        public HoldVacation build() {
            return new HoldVacation(memCode, vctCount, vctDeadline);
        }

}
