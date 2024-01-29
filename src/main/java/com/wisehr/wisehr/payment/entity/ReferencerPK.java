package com.wisehr.wisehr.payment.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class ReferencerPK implements Serializable {

    @Column(name = "mem_code")
    private Long memCode;

    @Column(name = "app_code")
    private String appCode;

    @Override
    public int hashCode() {
        return Objects.hash(memCode, appCode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReferencerPK referencerPK = (ReferencerPK) o;
        return Objects.equals(memCode, referencerPK.memCode) && Objects.equals(appCode, referencerPK.appCode);
    }

    public ReferencerPK() {
    }

    public ReferencerPK(Long memCode, String appCode) {
        this.memCode = memCode;
        this.appCode = appCode;
    }

    public Long getMemCode() {
        return memCode;
    }

    public void setMemCode(Long memCode) {
        this.memCode = memCode;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    @Override
    public String toString() {
        return "ReferencerPK{" +
                "memCode=" + memCode +
                ", appCode='" + appCode + '\'' +
                '}';
    }
}
