package com.wisehr.wisehr.setting.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "degree")
@AllArgsConstructor
@Getter
public class SettingDegree {
    @Id
    @GeneratedValue(generator = "eegenerator")
    @GenericGenerator(name = "eegenerator",
            parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "deg"),
            strategy = "com.wisehr.wisehr.common.MyGenerator")
    @Column(name = "deg_code")
    private String degCode;
    @Column(name = "deg_kind")
    private String degKind;
    @Column(name = "deg_major")
    private String degMajor;
    @Column(name = "deg_name")
    private String degName;
    @Column(name = "deg_graduation")
    private String degGraduation;
    @Column(name = "deg_state")
    private String degState;
    @Column(name = "deg_admissions")
    private String degAdmissions;
    @Column(name = "mem_code")
    private int memCode;
    @OneToOne(mappedBy = "degree", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "deg_code", insertable = false, updatable = false)
    private SettingDegreeFile degreeFile;

    public SettingDegree() {
    }

    public SettingDegree(String degCode, String degKind, String degMajor, String degName, String degGraduation, String degState, String degAdmissions, int memCode) {
    }

    @Override
    public String toString() {
        return "SettingDegree{" +
                "degCode='" + degCode + '\'' +
                ", degKind='" + degKind + '\'' +
                ", degMajor='" + degMajor + '\'' +
                ", degName='" + degName + '\'' +
                ", degGraduation='" + degGraduation + '\'' +
                ", degState='" + degState + '\'' +
                ", degAdmissions='" + degAdmissions + '\'' +
                '}';
    }



        public SettingDegree degCode(String degCode) {
            this.degCode = degCode;
            return this;
        }

        public SettingDegree degKind(String degKind) {
            this.degKind = degKind;
            return this;
        }

        public SettingDegree degMajor(String degMajor) {
            this.degMajor = degMajor;
            return this;
        }

        public SettingDegree degName(String degName) {
            this.degName = degName;
            return this;
        }

        public SettingDegree degGraduation(String degGraduation) {
            this.degGraduation = degGraduation;
            return this;
        }

        public SettingDegree degState(String degState) {
            this.degState = degState;
            return this;
        }

        public SettingDegree degAdmissions(String degAdmissions) {
            this.degAdmissions = degAdmissions;
            return this;
        }

        public SettingDegree memCode(int memCode) {
            this.memCode = memCode;
            return this;
        }

        public SettingDegree build() {
            return new SettingDegree(degCode, degKind, degMajor, degName, degGraduation, degState, degAdmissions, memCode);
        }

}
