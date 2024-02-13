package com.wisehr.wisehr.organization.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "position")
public class TreePos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pos_code")
    private int posCode;
    @Column(name = "pos_name", nullable = false)
    private String posName;
}
