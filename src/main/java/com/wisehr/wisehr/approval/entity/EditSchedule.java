package com.wisehr.wisehr.approval.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;

@Entity
@Table(name = "edit_schedule")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class EditSchedule {

    @Id
    @GeneratedValue(generator = "eegenerator")
    @GenericGenerator(name = "eegenerator",
            parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "esh"),
            strategy = "com.wisehr.wisehr.common.MyGenerator")
    @Column(name="esh_code")
    private String eshCode;
    @Column(name="esh_start_date")
    private String eshStartDate;
    @Column(name="esh_end_date")
    private String eshEndDate;
    @Column(name="esh_contents")
    private String eshContents;
    @Column(name ="esh_date_type")
    private String eshDateType;
    @OneToOne
    @JoinColumn(name = "pay_code")
    private Approval approval;
    @Column(name = "esh_off_start_date")
    private String eshOffStartDate;
    @Column(name = "esh_off_end_date")
    private String eshOffEndDate;
}
