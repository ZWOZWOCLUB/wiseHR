package com.wisehr.wisehr.notice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.List;

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

    @Column(name = "not_code_number", nullable = false)
    private Long notCodeNumber;

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

    @Column(name = "mem_code")
    private String memCode;

    @OneToOne
    @JoinColumn(name = "mem_code",nullable = false, insertable = false, updatable = false)
    private NotMember notMember;

    @Column(name = "not_delete_status", nullable = false)
    private String notDeleteStatus;


    @Column(name = "not_all_arm_check", nullable = false)
    private String notAllArmCheck;

    @OneToMany
    @JoinColumn(name = "not_code")
    private List<NotAttachedFile> notAttachedFile;

    @PrePersist
    @PreUpdate
    private void updateNotCodeNumber() {
        if (this.notCode != null && !this.notCode.isEmpty()) {
            String numberPart = this.notCode.replaceAll("[^0-9]", "");
            this.notCodeNumber = Long.parseLong(numberPart);
        }
    }
}
