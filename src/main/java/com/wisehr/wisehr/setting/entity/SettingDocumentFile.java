package com.wisehr.wisehr.setting.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "document_file")
public class SettingDocumentFile {
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
    @Column(name = "doc_atc_origin_name")
    private String docAtcOriginName;
    @Column(name = "doc_atc_kind")
    private String docAtcKind;
    @Column(name = "mem_code")
    private int memCode;

    public SettingDocumentFile() {
    }



        public SettingDocumentFile docAtcCode(int docAtcCode) {
            this.docAtcCode = docAtcCode;
            return this;
        }

        public SettingDocumentFile docAtcExtends(String docAtcExtends) {
            this.docAtcExtends = docAtcExtends;
            return this;
        }

        public SettingDocumentFile docAtcConvertName(String docAtcConvertName) {
            this.docAtcConvertName = docAtcConvertName;
            return this;
        }

        public SettingDocumentFile docAtcRegistDate(String docAtcRegistDate) {
            this.docAtcRegistDate = docAtcRegistDate;
            return this;
        }

        public SettingDocumentFile docAtcStorage(String docAtcStorage) {
            this.docAtcStorage = docAtcStorage;
            return this;
        }

        public SettingDocumentFile docAtcDeleteStatus(String docAtcDeleteStatus) {
            this.docAtcDeleteStatus = docAtcDeleteStatus;
            return this;
        }

        public SettingDocumentFile docAtcPath(String docAtcPath) {
            this.docAtcPath = docAtcPath;
            return this;
        }

        public SettingDocumentFile docAtcOriginName(String docAtcOriginName) {
            this.docAtcOriginName = docAtcOriginName;
            return this;
        }

        public SettingDocumentFile docAtcKind(String docAtcKind) {
            this.docAtcKind = docAtcKind;
            return this;
        }

        public SettingDocumentFile memCode(int memCode) {
            this.memCode = memCode;
            return this;
        }

        public SettingDocumentFile build() {
            return new SettingDocumentFile(docAtcCode, docAtcExtends, docAtcConvertName, docAtcRegistDate, docAtcStorage, docAtcDeleteStatus, docAtcPath, docAtcOriginName, docAtcKind, memCode);
        }

}
