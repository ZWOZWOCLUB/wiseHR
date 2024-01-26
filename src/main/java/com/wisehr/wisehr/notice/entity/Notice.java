package com.wisehr.wisehr.notice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Entity
@Table(name="notice")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class Notice {
    @Id
    @Column(name="not_code",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String notCode;
    @Column(name="not_name")
    private String notName;
    @Column(name="not_comment")
    private String notComment;
    @Column(name="not_view")
    private int notView;
    @Column(name="not_create_date")
    private Date notCreateDate;
    @Column(name="not_modify_date")
    private Date notModifyDate;
    @Column(name="not_delete_state")
    private String notDeleteState;
    @OneToOne
    @JoinColumn(name="mem_code")
    private SettingMember memCode;
}
