package com.wisehr.wisehr.setting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wisehr.wisehr.common.Criteria;
import com.wisehr.wisehr.common.PageDTO;
import com.wisehr.wisehr.common.PagingResponseDTO;
import com.wisehr.wisehr.common.ResponseDTO;
import com.wisehr.wisehr.setting.dto.SettingMemDepPosDTO;
import com.wisehr.wisehr.setting.dto.SettingMemberDTO;
import com.wisehr.wisehr.setting.dto.SettingResourcesDTO;
import com.wisehr.wisehr.setting.entity.SettingResources;
import com.wisehr.wisehr.setting.service.SettingMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("member")
public class SettingMemberController {

    private final SettingMemberService settingMemberService;

    public SettingMemberController(SettingMemberService settingMemberService) {
        this.settingMemberService = settingMemberService;
    }

    /**
     * 직원 정보 등록
     */
    @PostMapping("/member")
    public ResponseEntity<ResponseDTO> insertMember(@ModelAttribute SettingMemberDTO settingMemberDTO, MultipartFile profile){

        System.out.println("settingMemberDTO = " + settingMemberDTO);
        System.out.println("profile = " + profile);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "사원등록 성공", settingMemberService.insertMember(settingMemberDTO, profile)));
    }


    /**
     * 직원 인사 등록 (학위, 자격, 경력)
     */
    @PostMapping("/insertResourcesInformation")
    public ResponseEntity<ResponseDTO> insertResourcesInformation(@ModelAttribute SettingResourcesDTO settingResourcesDTO){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "인사등록 성공", settingMemberService.insertResourcesInformation(settingResourcesDTO)));
    }

    /**
     * 직원 인사 조회 (학위, 자격, 경력)
     */
    @GetMapping("/searchResourcesInformation")
    public ResponseEntity<ResponseDTO> searchResourcesInformation(
            @RequestParam(name = "mem", defaultValue = "all") int memCode){

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", settingMemberService.searchResourcesInformation(memCode)));
    }


    /**
     * 직원 전체 조회, 페이징처리
     */
    @GetMapping("/allMemberSearch")
    public ResponseEntity<ResponseDTO> allMemberSearchWithPaging(
            @RequestParam(name = "offset", defaultValue = "1") String offset){
        log.info("전체 사원 조회 시작~~~~~~~~~~~");
        log.info("페이지 번호~~~~~~~~~", offset);

        Criteria cri = new Criteria(Integer.valueOf(offset), 10);

        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();

//        1. offset의 번호에 맞는 페이지의 뿌려줄 사원정보
        Page<SettingMemDepPosDTO> memberList = settingMemberService.allMemberSearchWithPaging(cri);
        pagingResponseDTO.setData(memberList);
//        2. PageDTO : 화면에서 페이징 처리에 필요한 정보들
        pagingResponseDTO.setPageInfo(new PageDTO(cri, (int) memberList.getTotalElements()));

        log.info("allMemberSearchWithPaging 끗~~~~~~~~~~~~");
        log.info(pagingResponseDTO.getData().toString());
//        조회가 성공했을 때, HTTP 상태코드 200이랑 "조회 성공" 메시지 반환
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", pagingResponseDTO));
    }

    /**
     * 사원 이름으로 검색
     * @param search 이름
     * @return memberList
     */
    @GetMapping("/search")
    public ResponseEntity<ResponseDTO> searchMemberList(
            @RequestParam(name = "s", defaultValue = "all") String search){

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", settingMemberService.searchMemberList(search)));
    }

    /**
     * 부서 조회
     * @return 전체 부서 리스트
     */
    @GetMapping("/depSearch")
    public ResponseEntity<ResponseDTO> searchDepName(){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", settingMemberService.searchDepName()));
    }

    /**
     * 직급 조회
     * @return 전체 직급 리스트
     */
    @GetMapping("/position")
    public ResponseEntity<ResponseDTO> searchPosition(){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", settingMemberService.searchPosition()));
    }



}
