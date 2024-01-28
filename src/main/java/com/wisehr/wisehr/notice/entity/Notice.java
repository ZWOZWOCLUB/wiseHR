package com.wisehr.wisehr.notice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name ="notice")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Notice {
    @Id
    @Column(name = "not_code", nullable = false)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String notCode;
    @Column(name = "not_name", nullable = false)
    private String notName;
    @Column(name = "not_comment", nullable = false)
    private String notComment;
    @Column(name = "not_view", nullable = false)
    private String notView;
    @Column(name = "not_create_date", nullable = false)
    private String notCreateDate;
    @Column(name = "not_modify_date", nullable = true)
    private String notModifyDate;
//    @OneToOne
//    @JoinColumn(name = "mem_code")
    @Column(name = "mem_code", nullable = false)
    private int memCode;
    @Column(name = "not_delete_status", nullable = false)
    private String notDeleteStatus;

}
