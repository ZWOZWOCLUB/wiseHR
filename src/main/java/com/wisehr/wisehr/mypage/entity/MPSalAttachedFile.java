package com.wisehr.wisehr.mypage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sal_attached_file")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MPSalAttachedFile {

    @Id
    @Column(name = "sal_atc_code")
    private int salAtcCode;
    @Column(name = "sal_atc_name")
    private String salAtcName;
    @Column(name = "sal_atc_regist_date")
    private String salAtcRegistDate;
    @Column(name = "sal_atc_delete_status")
    private String salAtcDeleteStatus;
    @Column(name = "sal_atc_path")
    private String salAtcPath;
    @Column(name = "sal_code")
    private String salCode;
    @Column(name = "sal_atc_convert_name")
    private String salAtcConvertName;
}
