package com.wisehr.wisehr.setting.entity;

import com.wisehr.wisehr.mypage.entity.MyPageMember;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "career")
@AllArgsConstructor
@Getter
public class SettingCareer {
    @Id
    @GeneratedValue(generator = "eegenerator")
    @GenericGenerator(name = "eegenerator",
            parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "crr"),
            strategy = "com.wisehr.wisehr.common.MyGenerator")
    @Column(name = "crr_code")
    private String crrCode;
    @Column(name = "crr_name")
    private String crrName;
    @Column(name = "crr_position")
    private String crrPosition;
    @Column(name = "crr_start_date")
    private String crrStartDate;
    @Column(name = "crr_end_date")
    private String crrEndDate;
    @Column(name = "crr_state")
    private String crrState;
    @Column(name = "crr_description")
    private String crrDescription;
    @Column(name = "mem_code")
    private int memCode;


    public SettingCareer() {
    }



        public SettingCareer crrCode(String crrCode) {
            this.crrCode = crrCode;
            return this;
        }

        public SettingCareer crrName(String crrName) {
            this.crrName = crrName;
            return this;
        }

        public SettingCareer crrPosition(String crrPosition) {
            this.crrPosition = crrPosition;
            return this;
        }

        public SettingCareer crrStartDate(String crrStartDate) {
            this.crrStartDate = crrStartDate;
            return this;
        }

        public SettingCareer crrEndDate(String crrEndDate) {
            this.crrEndDate = crrEndDate;
            return this;
        }

        public SettingCareer crrState(String crrState) {
            this.crrState = crrState;
            return this;
        }

        public SettingCareer crrDescription(String crrDescription) {
            this.crrDescription = crrDescription;
            return this;
        }

        public SettingCareer memCode(int memCode) {
            this.memCode = memCode;
            return this;
        }

    @Override
    public String toString() {
        return "SettingCareer{" +
                "crrCode='" + crrCode + '\'' +
                ", crrName='" + crrName + '\'' +
                ", crrPosition='" + crrPosition + '\'' +
                ", crrStartDate='" + crrStartDate + '\'' +
                ", crrEndDate='" + crrEndDate + '\'' +
                ", crrState='" + crrState + '\'' +
                ", crrDescription='" + crrDescription + '\'' +
                '}';
    }

        public SettingCareer build() {
            return new SettingCareer(crrCode, crrName, crrPosition, crrStartDate, crrEndDate, crrState, crrDescription, memCode);
        }

}
