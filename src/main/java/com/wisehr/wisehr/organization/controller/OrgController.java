package com.wisehr.wisehr.organization.controller;

import com.wisehr.wisehr.common.ResponseDTO;
import com.wisehr.wisehr.organization.dto.OrgDepartmentDTO;
import com.wisehr.wisehr.organization.service.OrgService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("org")
@Slf4j
public class OrgController {

    private final OrgService orgService;

    public OrgController(OrgService orgService) {
        this.orgService = orgService;
    }


    /**
     * 부서 목록 조회
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<ResponseDTO> selectAllOrgList(){

//        OrgDepartmentDTO orgDepartmentList = new OrgDepartmentDTO();
//
//        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", orgDepartmentList));

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", orgService.selectAllOrgList()));
    }

}
