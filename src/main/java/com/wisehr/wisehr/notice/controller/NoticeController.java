package com.wisehr.wisehr.notice.controller;

import com.wisehr.wisehr.common.Criteria;
import com.wisehr.wisehr.common.PagingResponseDTO;
import com.wisehr.wisehr.common.ResponseDTO;
import com.wisehr.wisehr.notice.dto.NotMemberDTO;
import com.wisehr.wisehr.notice.dto.NoticeDTO;
import com.wisehr.wisehr.notice.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }
    /*
    * 공지 등록
    * */
    @PostMapping("/notice")
    public ResponseEntity<ResponseDTO> insertNotice(@ModelAttribute NoticeDTO noticeDTO){

        System.out.println("noticeDTO = " + noticeDTO);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "공지등록 성공", noticeService.insertNotice(noticeDTO)));
    }

    /*
    * 공지 전체 조회
    * */

//    @GetMapping("/allNoticeSearch")
//    public ResponseEntity<ResponseDTO> allNoticeSearchWithPaging(
//            @RequestParam(name = "offset", defaultValue = "1")String offset){
//        log.info("전체 공지 조회");
//        log.info("페이지 번호", offset);
//
//        Criteria criteria = new Criteria(Integer.valueOf(offset), 10);
//
//        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
//
//        Page<NoticeDTO> noticeList = noticeService.allNoticeSearchWithPaging(criteria);
//    }

    /***
     * 공지제목으로 검색
     * @param search 제목
     * @return
     */
    @GetMapping("/titleSearch")
    public ResponseEntity<ResponseDTO> searchTitleList(
            @RequestParam(value = "t", defaultValue = "all")String search){
        return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK, "제목조회성공", noticeService.searchTitleList(search)));
    }

    @GetMapping("/commentSearch")
    public ResponseEntity<ResponseDTO> searchCommentList(
            @RequestParam(value = "c", defaultValue = "all")String search){
        return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK, "내용조회성공", noticeService.searchCommentList(search)));
    }

}
