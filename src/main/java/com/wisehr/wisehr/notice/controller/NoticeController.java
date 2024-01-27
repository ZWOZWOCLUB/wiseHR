package com.wisehr.wisehr.notice.controller;

import com.wisehr.wisehr.common.ResponseDTO;
import com.wisehr.wisehr.notice.dto.NotMemberDTO;
import com.wisehr.wisehr.notice.dto.NoticeDTO;
import com.wisehr.wisehr.notice.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @PostMapping("/notice")
    public ResponseEntity<ResponseDTO> insertNotice(@ModelAttribute NoticeDTO noticeDTO){

        System.out.println("noticeDTO = " + noticeDTO);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "공지등록 성공", noticeService.insertNotice(noticeDTO)));
    }
}
