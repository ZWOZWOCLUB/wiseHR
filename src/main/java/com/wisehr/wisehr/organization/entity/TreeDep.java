package com.wisehr.wisehr.organization.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "department")
public class TreeDep{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dep_code")
    private Integer depCode;
    @Column(name = "dep_name")
    private String depName;
    @Column(name = "dep_delete_status")
    private String depDeleteStatus;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "ref_dep_code")
    private TreeDep parentTreeDep;
    @OneToMany(mappedBy = "parentTreeDep")
    private List<TreeDep> subTreeDep;

    @OneToMany(mappedBy = "treeDep")
    private List<TreeMem> treeMemList;

    @Override
    public String toString() {
        return "TreeDep{" +
                "depCode=" + depCode +
                ", depName='" + depName + '\'' +
                ", depDeleteStatus='" + depDeleteStatus + '\'' +
//                ", parentTreeDep=" + parentTreeDep +
//                ", subTreeDep=" + subTreeDep +
//                ", treeMemList=" + treeMemList +
                '}';
    }
}
