package com.wisehr.wisehr.mypage.controller;

import com.wisehr.wisehr.common.ResponseDTO;
import com.wisehr.wisehr.mypage.service.MyPageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@Slf4j
@RestController
@RequestMapping("myPage")
public class MyPageController {

    private final MyPageService myPageService;

    public MyPageController(MyPageService myPageService) {
        this.myPageService = myPageService;
    }


    @GetMapping("/searchMem/{memCode}")
    public ResponseEntity<ResponseDTO> selectMemDetail(@PathVariable int memCode){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "회원 상세조회 성공" , myPageService.selectMem(memCode)));
    }

    @GetMapping("/searchDegree/{memCode}")
    public ResponseEntity<ResponseDTO> selectDegreeDetail(@PathVariable int memCode){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "학위 상세조회 성공" , myPageService.selectDegree(memCode)));
    }

    @GetMapping("/searchCer/{memCode}")
    public ResponseEntity<ResponseDTO> selectCerDetail(@PathVariable int memCode){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "자격 상세조회 성공" , myPageService.selectCer(memCode)));
    }

    @GetMapping("/searchCareer/{memCode}")
    public ResponseEntity<ResponseDTO> selectCareerDetail(@PathVariable int memCode){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "경력 상세조회 성공" , myPageService.selectCareer(memCode)));
    }


//    출퇴근 정보 조회
    @GetMapping("/attendance/{memCode}/{attWorkDate}")
    public ResponseEntity<ResponseDTO> selectAttendanceDetail(
            @PathVariable int memCode,
            @PathVariable Date attWorkDate
            ){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "출퇴근 상세조회 성공" , myPageService.selectAttendance(attWorkDate,memCode)));
    }

    @GetMapping("/document/{memCode}")
    public ResponseEntity<ResponseDTO> selectDocumentDetail(@PathVariable int memCode){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "출퇴근 상세조회 성공" , myPageService.selectDocument(memCode)));
    }

}
