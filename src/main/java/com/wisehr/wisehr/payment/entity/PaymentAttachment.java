package com.wisehr.wisehr.payment.entity;

import com.wisehr.wisehr.payment.dto.PaymentDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "pay_attached_file")
public class PaymentAttachment {
    @Id
    @GeneratedValue(generator = "eegenerator")
    @GenericGenerator(name = "eegenerator",
            parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "payAtc"),
            strategy = "com.wisehr.wisehr.common.MyGenerator")
    private String payAtcCode;
    @Column(name = "pay_atc_name")
    private String payAtcName;
    @Column(name = "pay_atc_delete_status")
    private String payAtcDeleteStatus;
    @Column(name = "pay_atc_path")
    private String payAtcPath;
    @OneToOne
    @JoinColumn(name = "pay_code")
    private Payment payment;
}
