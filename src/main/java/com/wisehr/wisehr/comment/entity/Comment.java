package com.wisehr.wisehr.comment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

public class Comment {
    @Id
    @Column(name = "com_code", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comCode;
    @Column(name = "com_contents", nullable = false)
    private String  comContents;
    @Column(name = "com_date", nullable = false)
    private Date comDate;
    @Column(name = "mem_code", nullable = false)
    private Long memCode;
    @Column(name = "com_delete_state", nullable = false)
    private String comDeleteState;
    @Column(name = "not_code", nullable = false)
    private String  notCode;
}
