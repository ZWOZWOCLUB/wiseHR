package com.wisehr.wisehr.payment.entity;


import jakarta.persistence.*;
import lombok.*;

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
    @Column(name="esh_code")
    private String eshCode;
    @Column(name="esh_name")
    private String eshName;
    @Column(name="esh_start_date")
    private Date eshStartDate;
    @Column(name="esh_end_date")
    private Date eshEndDate;
    @Column(name="esh_contents")
    private String eshContents;
    @OneToOne
    @JoinColumn(name = "pay_code")
    private Payment payCode;
}
