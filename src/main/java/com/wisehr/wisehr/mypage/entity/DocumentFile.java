package com.wisehr.wisehr.mypage.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;

@Entity
@Table(name = "document_file")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DocumentFile {

    @Id
    @Column(name = "doc_atc_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int docAtcCode;
    @Column(name = "doc_atc_extends")
    private String docAtcExtends;
    @Column(name = "doc_atc_convert_name")
    private String docAtcConvertName;
    @Column(name = "doc_atc_regist_date")
    private String docAtcRegistDate;
    @Column(name = "doc_atc_storage")
    private String docAtcStorage;
    @Column(name = "doc_atc_delete_status")
    private String docAtcDeleteStatus;
    @Column(name = "doc_atc_path")
    private String docAtcPath;
    @Column(name = "mem_code")
    private int memCode;
    @Column(name = "doc_atc_origin_name")
    private String docAtcOriginName;

    @Override
    public String toString() {
        return "DocumentFile{" +
                "docAtcCode=" + docAtcCode +
                ", docAtcExtends='" + docAtcExtends + '\'' +
                ", docAtcConvertName='" + docAtcConvertName + '\'' +
                ", docAtcRegistDate='" + docAtcRegistDate + '\'' +
                ", docAtcStorage='" + docAtcStorage + '\'' +
                ", docAtcDeleteStatus='" + docAtcDeleteStatus + '\'' +
                ", docAtcPath='" + docAtcPath + '\'' +
                ", docAtcOriginName='" + docAtcOriginName + '\'' +
                '}';
    }
}
