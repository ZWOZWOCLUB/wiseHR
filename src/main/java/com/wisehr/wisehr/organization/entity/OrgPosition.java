package com.wisehr.wisehr.organization.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Entity
@Table(name = "position")
@AllArgsConstructor
@Getter
@ToString
public class OrgPosition {

    @Id
    @Column(name = "pos_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int posCode;
    @Column(name = "pos_name", nullable = false)
    private String posName;


    public OrgPosition() {
    }
}
