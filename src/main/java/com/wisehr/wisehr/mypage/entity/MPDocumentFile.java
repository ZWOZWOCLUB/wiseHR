package com.wisehr.wisehr.mypage.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "document_file")
@AllArgsConstructor
@Getter
@Setter
public class MPDocumentFile {

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

    public MPDocumentFile() {
    }
    public MPDocumentFile docAtcCode(int docAtcCode) {
        this.docAtcCode = docAtcCode;
        return this;
    }
    public MPDocumentFile docAtcExtends(String docAtcExtends) {
        this.docAtcExtends = docAtcExtends;
        return this;
    }
    public MPDocumentFile docAtcConvertName(String docAtcConvertName) {
        this.docAtcConvertName = docAtcConvertName;
        return this;
    }
    public MPDocumentFile docAtcRegistDate(String docAtcRegistDate) {
        this.docAtcRegistDate = docAtcRegistDate;
        return this;
    }
    public MPDocumentFile docAtcStorage(String docAtcStorage) {
        this.docAtcStorage = docAtcStorage;
        return this;
    }

    public MPDocumentFile docAtcDeleteStatus(String docAtcDeleteStatus) {
        this.docAtcDeleteStatus = docAtcDeleteStatus;
        return this;
    }
    public MPDocumentFile docAtcPath(String docAtcPath) {
        this.docAtcPath = docAtcPath;
        return this;
    }
    public MPDocumentFile memCode(int memCode) {
        this.memCode = memCode;
        return this;
    }
    public MPDocumentFile docAtcOriginName(String docAtcOriginName) {
        this.docAtcOriginName = docAtcOriginName;
        return this;
    }
    public MPDocumentFile docAtcKind(String docAtcKind) {
        this.docAtcKind = docAtcKind;
        return this;
    }


    public MPDocumentFile build(){
        return new MPDocumentFile(docAtcCode,
                docAtcExtends, docAtcConvertName, docAtcRegistDate,docAtcStorage,
                docAtcDeleteStatus, docAtcPath, memCode, docAtcOriginName, docAtcKind);
    }

}
