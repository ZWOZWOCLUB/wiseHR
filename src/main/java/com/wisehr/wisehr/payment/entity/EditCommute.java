package com.wisehr.wisehr.payment.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name="edit_commute")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class EditCommute {
    @Id
    @Column(name = "edi_code")
    private String ediCode;
    @Column(name = "edi_kind")
    private String ediKind;
    @Column(name = "edi_name")
    private String ediName;
    @Column(name = "edi_contents")
    private String ediContents;
    @Column(name = "edi_date")
    private Date ediDate;
    @Column(name = "edi_time")
    private Time ediTime;
    @OneToOne
    @JoinColumn(name = "pay_code")
    private Payment payCode;
}
