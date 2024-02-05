package com.wisehr.wisehr.dataFormat.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "data_format")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class DataFormat {
    @Id
    @Column(name="data_code", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dataCode;
    @Column(name="data_name", nullable = false)
    private String dataName;

    @Column(name="mem_code", nullable = false)
    private Long memCode;
    @Column(name="regist_date", nullable = false)
    private Date registDate;
    @Column(name="data_size", nullable = false)
    private Long dataSize;
    @Column(name="data_path", nullable = false)
    private String dataPath;
    @Column(name="data_delete_status", nullable = false)
    private String dataDeleteStatus;

}
