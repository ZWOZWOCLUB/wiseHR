package com.wisehr.wisehr.organization.controller;

import com.wisehr.wisehr.common.ResponseDTO;
import com.wisehr.wisehr.organization.dto.OrgDepartmentDTO;
import com.wisehr.wisehr.organization.entity.OrgDepartment;
import com.wisehr.wisehr.organization.service.OrgService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("org")
@Slf4j
public class OrgController {

    private final OrgService orgService;

    public OrgController(OrgService orgService) {
        this.orgService = orgService;
    }


    /**
     * 부서 전체 목록 조회
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<ResponseDTO> selectAllOrgList(){

//        OrgDepartmentDTO orgDepartmentList = new OrgDepartmentDTO();
//
//        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", orgDepartmentList));

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", orgService.selectAllOrgList()));
    }

    /**
     *  상위부서 목록 조회
     * @return
     */
    @GetMapping("/repList")
    public ResponseEntity<ResponseDTO> selectRefOrgList(){

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", orgService.selectRefOrgList()));
    }

    /**
     *  신규 부서 생성
     *
     * 파일이 아닌 폼 데이터만 전송할 때는 리퀘스트바디 사용해도 된다.
     * 리퀘스트바디 어노테이션은 클라이언트로부터 받은 json 데이터를 자바 객체로 반환해줌
     * @param orgDepartmentDTO
     * @return
     */
    @PostMapping("/insertOrgDep")
    public ResponseEntity<ResponseDTO> insertOrgDep(@RequestBody OrgDepartmentDTO orgDepartmentDTO){


        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "부서 등록 성공", orgService.insertOrgDepartment(orgDepartmentDTO)));
    }

    /**
     * 부서명 수정
     * @param orgDepartmentDTO
     * @return
     */
    @PutMapping("/modifyOrgDep")
    public ResponseEntity<ResponseDTO> modifyOrgDep(@RequestBody OrgDepartmentDTO orgDepartmentDTO){

        orgService.modifyOrgDep(orgDepartmentDTO);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "부서명 수정 성공", orgService.modifyOrgDep(orgDepartmentDTO)));
    }


}
