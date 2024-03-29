package com.wisehr.wisehr.comment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Entity
@Table(name = "comment")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Comment {
    @Id
    @Column(name = "com_code", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comCode;
    @Column(name = "com_contents", nullable = false)
    private String  comContents;
    @Column(name = "com_date", nullable = false)
    private Date comDate;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "mem_code", nullable = false)
    private ComMember comMember;
    @Column(name = "com_delete_state", nullable = false)
    private String comDeleteState;
    @ManyToOne
    @JoinColumn(name = "not_code", nullable = false)
    private ComNotice notCode;
}
