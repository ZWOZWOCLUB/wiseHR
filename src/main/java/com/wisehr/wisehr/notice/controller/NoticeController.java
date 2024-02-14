package com.wisehr.wisehr.notice.controller;

import com.wisehr.wisehr.common.Criteria;
import com.wisehr.wisehr.common.PageDTO;
import com.wisehr.wisehr.common.PagingResponseDTO;
import com.wisehr.wisehr.common.ResponseDTO;
import com.wisehr.wisehr.notice.dto.NoticeDTO;
import com.wisehr.wisehr.notice.dto.NoticeResponseDTO;
import com.wisehr.wisehr.notice.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import java.util.List;

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
    public ResponseEntity<ResponseDTO> insertNotice(
            @ModelAttribute NoticeDTO noticeDTO,
//            List<MultipartFile> noticeFiles
            @RequestParam("noticeFiles") List<MultipartFile> noticeFiles
    ){

//        System.out.println("noticeDTO = " + noticeDTO);
//        log.info("noticeDTO============"+noticeDTO);
//        System.out.println("noticeFile = " + noticeFiles);
//        log.info("noticeFile ==========" + noticeFiles);

        System.out.println("noticeFile = " + noticeFiles);
        if (noticeFiles != null) {
            noticeFiles.forEach(file -> {
                System.out.println("Received file: " + file.getOriginalFilename() + " with size " + file.getSize());
            });
        } else {
            System.out.println("No files received.");
        }

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "공지등록 성공", noticeService.insertNotice(noticeDTO,noticeFiles)));
    }

/*
* 공지 수정
* */
    @PutMapping("/updateNotice")
    public ResponseEntity<ResponseDTO> updateNotice(
            @ModelAttribute NoticeDTO noticeDTO, MultipartFile noticeFile){
        log.info("공지사항수정시작");
        log.info("NoticeDTO ====== " + noticeDTO);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "공지수정성공", noticeService.updateNotice(noticeDTO, noticeFile)));

    }

    /*
    * 공지 전체 조회, 페이징처리
    * */

    @GetMapping("/allNoticeSearch")
    public ResponseEntity<ResponseDTO> allNoticeSearchWithPaging(
            @RequestParam(name = "offset", defaultValue = "1")String offset){
        log.info("전체 공지 조회");
        log.info("페이지 번호", offset);

        Criteria criteria = new Criteria(Integer.valueOf(offset), 10);

        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();

        //offset의 번호에 맞는 페이지에 뿌려줄 공지사항정보
        Page<NoticeResponseDTO> noticeList = noticeService.allNoticeSearchWithPaging(criteria);
        pagingResponseDTO.setData(noticeList);
        pagingResponseDTO.setPageInfo(new PageDTO(criteria, (int) noticeList.getTotalElements()));

        log.info("allNoticeSearchWithPaging 끝");
        log.info(pagingResponseDTO.getData().toString());

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "공지전체조회성공", pagingResponseDTO));
    }

    /***
     * 공지 상세 조회
     * @param search notCode
     * @return
     */
    @GetMapping("/detail")
    public ResponseEntity<ResponseDTO> noticeDetail(
            @RequestParam(value = "nc", defaultValue = "not")String search){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "공지상세조회성공", noticeService.noticeDetail(search)));
    }


    /***
     * 공지제목으로 조회
     * @param search
     * @return
     */
    @GetMapping("/titleSearch")
    public ResponseEntity<ResponseDTO> searchTitleList(
            @RequestParam(value = "t", defaultValue = "all")String search){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "제목조회성공", noticeService.searchTitleList(search)));
    }

    /***
     * 공지내용으로 검색
     * @param search 내용
     * @return
     */
    @GetMapping("/commentSearch")
    public ResponseEntity<ResponseDTO> searchCommentList(
            @RequestParam(value = "c", defaultValue = "all")String search){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "내용조회성공", noticeService.searchCommentList(search)));
    }

    /***
     * 공지작성자로 검색
     * @param search 작성자
     * @return
     */
    @GetMapping("/memberNameSearch")
    public ResponseEntity<ResponseDTO> searchMemberNameList(
            @RequestParam(value = "m",defaultValue = "all")String search){

        return  ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "작성자조회성공", noticeService.searchMemberNameList(search)));

    }


}
