package com.wisehr.wisehr.mypage.controller;

import com.wisehr.wisehr.common.ResponseDTO;
import com.wisehr.wisehr.mypage.service.MyPageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("myPage")
public class MyPageController {

    private final MyPageService myPageService;

    public MyPageController(MyPageService myPageService) {
        this.myPageService = myPageService;
    }


    @GetMapping("/{memCode}")
    public ResponseEntity<ResponseDTO> selectMemDetail(@PathVariable int memCode){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "회원 상세조회 성공" , myPageService.selectMem(memCode)));
    }

}
