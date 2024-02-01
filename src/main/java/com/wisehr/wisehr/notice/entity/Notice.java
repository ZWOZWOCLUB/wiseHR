package com.wisehr.wisehr.notice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;

@Entity
@Table(name ="notice")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Notice {
    @Id
    @GeneratedValue(generator = "eegenerator")
    @GenericGenerator(name = "eegenerator",
            parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "not"),
            strategy = "com.wisehr.wisehr.common.MyGenerator")
    private String notCode;
    @Column(name = "not_name", nullable = false)
    private String notName;
    @Column(name = "not_comment", nullable = true)
    private String notComment;
    @Column(name = "not_view", nullable = false)
    private Long notView;
    @Column(name = "not_create_date", nullable = false)
    private Date notCreateDate;
    @Column(name = "not_modify_date", nullable = true)
    private Date notModifyDate;
    @OneToOne
    @JoinColumn(name = "mem_code",nullable = false)
//    @Column(name = "mem_code", nullable = true)
    private NotMember notMember;
    @Column(name = "not_delete_status", nullable = false)
    private String notDeleteStatus;

}