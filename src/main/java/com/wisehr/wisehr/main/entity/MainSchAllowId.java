package com.wisehr.wisehr.main.entity;

import jakarta.persistence.Entity;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MainSchAllowId implements Serializable {

    private Long memCode;
    private String schCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MainSchAllowId that = (MainSchAllowId) o;
        return Objects.equals(memCode, that.memCode) && Objects.equals(schCode, that.schCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memCode, schCode);
    }
}
