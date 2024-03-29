package com.wisehr.wisehr.approval.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "pay_attached_file")
public class ApprovalAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pay_atc_code")
    private String payAtcCode;
    @Column(name = "pay_atc_name")
    private String payAtcName;
    @Column(name = "pay_atc_delete_status", columnDefinition = "VARCHAR(1) default 'N'")
    @ColumnDefault("N")
    private String payAtcDeleteStatus;
    @Column(name = "pay_atc_path")
    private String payAtcPath;
    @Column(name = "pay_atc_original_name")
    private String payAtcOriginalName;
    @OneToOne
    @JoinColumn(name = "pay_code")
    private Approval approval;
}
