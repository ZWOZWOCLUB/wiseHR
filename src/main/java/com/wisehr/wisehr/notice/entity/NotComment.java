package com.wisehr.wisehr.notice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comment")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class NotComment {
    @Id
    @Column(name = "com_code", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comCode;
    @Column(name = "com_contents", nullable = false)
    private String  comContents;
    @Column(name = "com_date", nullable = false)
    private String  comDate;
    @Column(name = "mem_code", nullable = false)
    private Long memCode;
    @Column(name = "com_delete_state", nullable = false)
    private String comDeleteState;
    @Column(name = "not_code", nullable = false)
    private String  notCode;
}
