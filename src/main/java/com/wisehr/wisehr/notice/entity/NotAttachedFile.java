package com.wisehr.wisehr.notice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name ="not_attached_file")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class NotAttachedFile {
    @Id
    @Column(name = "not_atc_code", nullable = false)
    private Long notAtcCode;          //첨부파일코드
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "not_atc_extens", nullable = false)
    private String notAtcExtens;      //확장자
    @Column(name = "not_atc_name", nullable = false)
    private String notAtcName;        //파일이름
    @Column(name = "not_atc_delete_status", nullable = false)
    private String notAtcDeleteStatus;//삭제여부
    @Column(name = "not_atc_path", nullable = false)
    private String notAtcPath;        //파일경로
    @OneToOne
    @JoinColumn(name = "not_code", nullable = false)
    private Notice notCode;           //글코드
}
