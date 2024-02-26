package com.wisehr.wisehr.organization.controller;

import com.wisehr.wisehr.common.Criteria;
import com.wisehr.wisehr.common.PageDTO;
import com.wisehr.wisehr.common.PagingResponseDTO;
import com.wisehr.wisehr.common.ResponseDTO;
import com.wisehr.wisehr.organization.dto.*;
import com.wisehr.wisehr.organization.service.OrgService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN') or hasAuthority('USER')")
    @Tag(name = "부서 전체 조회", description = "전체 부서와 그 상하위로 조회 됩니다.")
    @GetMapping("/list")
    public ResponseEntity<ResponseDTO> selectAllOrgList(){

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", orgService.selectAllOrgList()));
    }

    /**
     *  상위부서 목록 조회
     * @return
     */
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN')")
    @Tag(name = "상위부서 목록 조회", description = "상위부서 목록 조회")
    @GetMapping("/repList")
    public ResponseEntity<ResponseDTO> selectRefOrgList(){

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", orgService.selectRefOrgList()));
    }

    /**
     *  신규 부서 생성
     * @param orgDepartmentDTO
     * @return
     */
    @PreAuthorize("hasAuthority('SUPERADMIN')")
    @Tag(name = "부서 생성", description = "부서 생성")
    @PostMapping("/insertOrgDep")
    public ResponseEntity<ResponseDTO> insertOrgDep(@RequestBody OrgDepartmentDTO orgDepartmentDTO){
        //@RequestBody 어노테이션은 클라이언트로부터 받은 json 데이터를 자바 객체로 반환해줌


        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "부서 등록 성공", orgService.insertOrgDepartment(orgDepartmentDTO)));
    }

    /**
     * 부서명 수정
     * @param orgDepartmentDTO
     * @return
     */
    @PreAuthorize("hasAuthority('SUPERADMIN')")
    @Tag(name = "부서명 수정", description = "부서명 수정")
    @PutMapping("/modifyOrgDep")
    public ResponseEntity<ResponseDTO> modifyOrgDep(@RequestBody OrgDepartmentDTO orgDepartmentDTO){

        Object result = orgService.modifyOrgDep(orgDepartmentDTO);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "부서명 수정 성공", result));
    }

    /**
     * 부서별 멤버 전체 조회
     * @return
     */
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN') or hasAuthority('USER')")
    @Tag(name = "부서별 멤버 전체 조회", description = "부서별 멤버 전체 조회")
    @GetMapping("/AllMemOfDep")
    public ResponseEntity<ResponseDTO> AllMemOfDep(){

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "부서별 멤버 조회 성공",orgService.AllMemOfDep()));

    }

    /**
     * 부서 삭제(delete 아닌 상태값 Y 업데이트 및 멤버 dep_code를 null로 업데이트)
     * @param orgDepartmentAndOrgMemberDTO
     * @return
     */
    @PreAuthorize("hasAuthority('SUPERADMIN')")
    @Tag(name = "부서삭제", description = "부서삭제")
    @PutMapping("/deleteOrgDep")
    public ResponseEntity<ResponseDTO> deleteOrgDep(@RequestBody OrgDepartmentAndOrgMemberDTO orgDepartmentAndOrgMemberDTO){



        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "부서 삭제 성공", orgService.deleteOrgDep(orgDepartmentAndOrgMemberDTO)));

    }

    /**
     * 부서 개별 상세 조회
     * @param depCode
     * @return
     */
    @PreAuthorize("hasAuthority('SUPERADMIN')")
    @Tag(name = "부서 개별 조회", description = "부서 개별 조회")
    @GetMapping("/list/{depCode}")
    public ResponseEntity<ResponseDTO> selectDepDetail(@PathVariable int depCode){
        //@PathVariable : url 경로를 매개변수로 전달

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "부서 개별 조회 성공", orgService.selectDepDetail(depCode)));
    }



    /**
     * 멤버 전체 조회(페이징)
     * @param offset
     * @return
     */
    @PreAuthorize("hasAuthority('SUPERADMIN')")
    @Tag(name = "멤버 전체 조회", description = "멤버 전체 조회")
    @GetMapping("/memberList")
    public ResponseEntity<ResponseDTO> selectAllOrgMemberListWithPaging(
            @RequestParam(name = "offset", defaultValue = "1") String offset){
        //offset : 현재 페이지 번호 - 클라이언트로부터 받음

        Criteria cri = new Criteria(Integer.valueOf(offset), 5); //pageNum, amount 값 저장. pageNum이 offset

        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
        Page<OrgMemAndOrgDepDTO> orgMemberList = orgService.selectAllOrgMemberListWithPaging(cri);

        //offset 번호에 맞는 페이지에 보여줄 멤버정보
        pagingResponseDTO.setData(orgMemberList);
        //페이징 처리에 필요한 정보
        pagingResponseDTO.setPageInfo(new PageDTO(cri, (int) orgMemberList.getTotalElements()));


        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", pagingResponseDTO));

    }

    /**
     * 부서에 멤버 추가(member 테이블의 dep_code를 업데이트 해주기 때문에 put)
     * @param depCode 선택한 부서
     * @param selectedMemberCodes 사용자가 추가하기로 선택한 멤버의 코드들
     * @return
     */
    @PreAuthorize("hasAuthority('SUPERADMIN')")
    @Tag(name = "부서에 멤버 추가", description = "부서에 멤버 추가")
    @PutMapping("/insertMember/{depCode}")
    public ResponseEntity<ResponseDTO> insertMember(@PathVariable int depCode, @RequestBody List<Integer> selectedMemberCodes){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "멤버 추가 성공", orgService.insertMember(depCode, selectedMemberCodes)));
    }


    /**
     * 중간관리자 지정
     * @param orgMemAndOrgDepDTO
     * @return
     */
//    @Tag(name = "부서의 관리자 지정", description = "부서의 관리자 지정")
//    @PutMapping("/updateRole/")
//    public ResponseEntity<ResponseDTO> updateRole(@RequestBody OrgMemAndOrgDepDTO orgMemAndOrgDepDTO){
//        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "memberRole 업데이트 성공", orgService.updateRole(orgMemAndOrgDepDTO)));
//    }

    /**
     * 멤버 권한 설정
     * @param orgMemAndOrgDepDTO
     * @return
     */
    @PreAuthorize("hasAuthority('SUPERADMIN')")
    @PutMapping("/updateRole/")
    public ResponseEntity<ResponseDTO> updateMemberRole(@RequestBody OrgMemAndOrgDepDTO orgMemAndOrgDepDTO){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "memberRole 업데이트 성공", orgService.updateMemberRole(orgMemAndOrgDepDTO)));
    }


    /**
     * 조직도 트리뷰 조회
     * @return
     */
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN') or hasAuthority('USER')")
    @Tag(name = "조직도 트리뷰 조회", description = "조직도 트리뷰 조회")
    @GetMapping("/treeView")
    public ResponseEntity<ResponseDTO> treeView(){
        TreeDepDTO tree = orgService.showTreeView();
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조직도 조회 성공", tree));
    }


    /**
     * 사원 이름으로 검색
     * @param search
     * @return
     */
    @PreAuthorize("hasAuthority('SUPERADMIN')")
    @GetMapping("/nameSearch")
    public ResponseEntity<ResponseDTO> searchMemberName(
            @RequestParam(value = "n", defaultValue = "all")String search){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "이름별 조회 성공", orgService.searchMemberName(search)));
    }

//    @GetMapping("/depSearch")
//    public ResponseEntity<ResponseDTO> searchDepName(
//            @RequestParam(value = "d", defaultValue = "all")String search){
//        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "부서별 조회 성공", orgService.searchDepName(search)));
//    }

//    @PreAuthorize("hasAuthority('SUPERADMIN')")
//    @GetMapping("/nameSearch")
//    public ResponseEntity<ResponseDTO> searchMemberName(
//            @RequestParam(value = "n", defaultValue = "all")String search){
//        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "이름별 조회 성공", orgService.searchMemberName(search)));
//    }

//    @PreAuthorize("hasAuthority('SUPERADMIN')")
//    @GetMapping("/nameSearch")
//    public ResponseEntity<ResponseDTO> searchMemberNameWithPaging(
//            @RequestParam(value = "n, value", defaultValue = "all, 1")String search, String offset){
//
//        Criteria cri = new Criteria(Integer.valueOf(offset), 5); //pageNum, amount 값 저장. pageNum이 offset
//
//        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
//        Page<OrgMemAndOrgDepDTO> orgMemberList = orgService.searchMemberNameWithPaging(search, cri);
//
//        //offset 번호에 맞는 페이지에 보여줄 멤버정보
//        pagingResponseDTO.setData(orgMemberList);
//        //페이징 처리에 필요한 정보
//        pagingResponseDTO.setPageInfo(new PageDTO(cri, (int) orgMemberList.getTotalElements()));
//
//        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "검색된 이름별 조회 성공", pagingResponseDTO));
//    }
}
