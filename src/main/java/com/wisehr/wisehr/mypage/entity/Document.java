package com.wisehr.wisehr.mypage.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Table(name = "member")
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Document {
    @Id
    @Column(name = "mem_code")
    private int memCode;
    @JoinColumn(name = "mem_code")
    @OneToMany
    private List<DocumentFile> documentFileDTOList;
    @JoinColumn(name = "mem_code")
    @OneToOne
    private Salary salaryFileDTO;


    @Override
    public String toString() {
        return "Document{" +
                ", documentFileDTOList=" + documentFileDTOList +
                ", salAttachedFileDTO=" + salaryFileDTO +
                '}';
    }
}
