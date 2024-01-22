package com.wisehr.wisehr.setting.controller;

import com.wisehr.wisehr.common.Criteria;
import com.wisehr.wisehr.common.PageDTO;
import com.wisehr.wisehr.common.PagingResponseDTO;
import com.wisehr.wisehr.common.ResponseDTO;
import com.wisehr.wisehr.setting.dto.SettingMemberDTO;
import com.wisehr.wisehr.setting.service.SettingMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("member")
public class SettingMemberController {

    private final SettingMemberService settingMemberService;

    public SettingMemberController(SettingMemberService settingMemberService) {
        this.settingMemberService = settingMemberService;
    }

    @PostMapping("/member")
    public ResponseEntity<ResponseDTO> insertMember(@ModelAttribute SettingMemberDTO settingMemberDTO){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "사원등록 성공", settingMemberService.insertMember(settingMemberDTO)));
    }

    @GetMapping("/allMemberSearch")
    public ResponseEntity<ResponseDTO> allMemberSearchWithPaging(
            @RequestParam(name = "offset", defaultValue = "1") String offset){
        log.info("전체 사원 조회 시작~~~~~~~~~~~");
        log.info("페이지 번호~~~~~~~~~", offset);

        Criteria cri = new Criteria(Integer.valueOf(offset), 10);

        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();

//        1. offset의 번호에 맞는 페이지의 뿌려줄 사원정보
        Page<SettingMemberDTO> memberList = settingMemberService.allMemberSearchWithPaging(cri);
        pagingResponseDTO.setData(memberList);
//        2. PageDTO : 화면에서 페이징 처리에 필요한 정보들
        pagingResponseDTO.setPageInfo(new PageDTO(cri, (int) memberList.getTotalElements()));

        log.info("allMemberSearchWithPaging 끗~~~~~~~~~~~~");
        log.info(pagingResponseDTO.getData().toString());
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", pagingResponseDTO));
    }
}
