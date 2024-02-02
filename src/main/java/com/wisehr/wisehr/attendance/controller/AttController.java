package com.wisehr.wisehr.attendance.controller;

import com.wisehr.wisehr.attendance.service.AttService;
import com.wisehr.wisehr.common.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("att")
@Slf4j
public class AttController {

    private final AttService attService;

    public AttController(AttService attService) {
        this.attService = attService;
    }

    @PostMapping("/insertAtt")
    public ResponseEntity<ResponseDTO> insertAtt(){

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "출근 기록", attService.insertAtt()));
    }
}
