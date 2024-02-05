package com.wisehr.wisehr.schedule.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class SearchCountDepCode {
    @Id
    @Column(name = "dep_code")
    private int depCode;
    @Column(name = "dep_name")
    private String depName;
    @Column(name = "count")
    private int count;
}
