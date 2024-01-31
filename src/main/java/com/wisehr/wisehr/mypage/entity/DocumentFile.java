package com.wisehr.wisehr.mypage.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;

@Entity
@Table(name = "document_file")
@AllArgsConstructor
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
    @Column(name = "doc_atc_kind")
    private String docAtcKind;

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

    public DocumentFile() {
    }
    public DocumentFile docAtcCode(int docAtcCode) {
        this.docAtcCode = docAtcCode;
        return this;
    }
    public DocumentFile docAtcExtends(String docAtcExtends) {
        this.docAtcExtends = docAtcExtends;
        return this;
    }
    public DocumentFile docAtcConvertName(String docAtcConvertName) {
        this.docAtcConvertName = docAtcConvertName;
        return this;
    }
    public DocumentFile docAtcRegistDate(String docAtcRegistDate) {
        this.docAtcRegistDate = docAtcRegistDate;
        return this;
    }
    public DocumentFile docAtcStorage(String docAtcStorage) {
        this.docAtcStorage = docAtcStorage;
        return this;
    }

    public DocumentFile docAtcDeleteStatus(String docAtcDeleteStatus) {
        this.docAtcDeleteStatus = docAtcDeleteStatus;
        return this;
    }
    public DocumentFile docAtcPath(String docAtcPath) {
        this.docAtcPath = docAtcPath;
        return this;
    }
    public DocumentFile memCode(int memCode) {
        this.memCode = memCode;
        return this;
    }
    public DocumentFile docAtcOriginName(String docAtcOriginName) {
        this.docAtcOriginName = docAtcOriginName;
        return this;
    }
    public DocumentFile docAtcKind(String docAtcKind) {
        this.docAtcKind = docAtcKind;
        return this;
    }


    public DocumentFile build(){
        return new DocumentFile(docAtcCode,
                docAtcExtends, docAtcConvertName, docAtcRegistDate,docAtcStorage,
                docAtcDeleteStatus, docAtcPath, memCode, docAtcOriginName, docAtcKind);
    }

}
