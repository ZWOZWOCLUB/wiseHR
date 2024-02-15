package com.wisehr.wisehr.setting.controller;

import com.wisehr.wisehr.common.Criteria;
import com.wisehr.wisehr.common.PageDTO;
import com.wisehr.wisehr.common.PagingResponseDTO;
import com.wisehr.wisehr.common.ResponseDTO;
import com.wisehr.wisehr.mypage.dto.MPHoldVacationDTO;
import com.wisehr.wisehr.security.auth.model.dto.MemberDTO;
import com.wisehr.wisehr.setting.dto.*;
import com.wisehr.wisehr.setting.service.SettingMemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("setting")
@Tag(name = "설정 관련 스웨거 연동")

public class SettingMemberController {

    private final SettingMemberService settingMemberService;
    private final BCryptPasswordEncoder PasswordEncoder;
    public SettingMemberController(SettingMemberService settingMemberService, BCryptPasswordEncoder PasswordEncoder) {
        this.settingMemberService = settingMemberService;
        this.PasswordEncoder = PasswordEncoder;

    }

    /**
     * 직원 정보 등록
     */
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN')")
    @Tag(name = "설정 직원 정보 등록", description = "직원 정보 등록")
    @PostMapping("/member")
    public ResponseEntity<ResponseDTO> insertMember(@ModelAttribute SettingMemberDTO settingMemberDTO, MultipartFile profile){
        settingMemberDTO.setMemPassword(PasswordEncoder.encode(settingMemberDTO.getMemPassword()));
        System.out.println("settingMemberDTO = " + settingMemberDTO);
        System.out.println("profile = " + profile);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "사원등록 성공", settingMemberService.insertMember(settingMemberDTO, profile)));
    }

    /**
     * 직원 정보 수정
     */
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN')")
    @Tag(name = "설정 직원 정보 수정", description = "직원 정보 수정")
    @PutMapping(value = "/member")
    public ResponseEntity<ResponseDTO> UpdateMember(@ModelAttribute SettingMemberDTO settingMemberDTO, MultipartFile profile){

        System.out.println("settingMemberDTO = " + settingMemberDTO);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "사원정보 수정 성공", settingMemberService.updateMember(settingMemberDTO, profile)));
    }


    /**
     * 직원 학위 정보 등록
     */
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN')")
    @Tag(name = "설정 직원 학위 정보 등록", description = "직원 학위 정보 등록")
    @PostMapping("/degree")
    public ResponseEntity<ResponseDTO> insertDegree(@RequestBody List<SettingDegreeDTO> degreeDTO){
        System.out.println("degreeDTO = " + degreeDTO);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "학위 정보 등록 성공", settingMemberService.insertDegree(degreeDTO)));
    }

    /**
     * 직원 자격 정보 등록
     */
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN')")
    @Tag(name = "설정 직원 자격 정보 등록", description = "직원 자격 정보 등록")
    @PostMapping("/certificate")
    public ResponseEntity<ResponseDTO> insertCertificate(@RequestBody List<SettingCertificateDTO> certificateDTO){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "자격 정보 등록 성공", settingMemberService.insertCertificate(certificateDTO)));
    }

    /**
     * 직원 경력 정보 등록
     */
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN')")
    @Tag(name = "설정 직원 경력 정보 등록", description = "직원 경력 정보 등록")
    @PostMapping("/career")
    public ResponseEntity<ResponseDTO> insertCareer(@RequestBody List<SettingCareerDTO> careerDTO){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "경력 정보 등록 성공", settingMemberService.insertCareer(careerDTO)));
    }

    /**
     * 직원 학위 정보 수정
     */
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN')")
    @Tag(name = "설정 직원 학위 정보 수정", description = "직원 학위 정보 수정")
    @PutMapping(value = "/degree")
    public ResponseEntity<ResponseDTO> updateDegree(@RequestBody List<SettingDegreeDTO> degreeDTO){
        System.out.println("degreeDTO = " + degreeDTO);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "학위 정보 수정 성공", settingMemberService.updateDegree(degreeDTO)));
    }

    /**
     * 직원 자격 정보 수정
     */
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN')")
    @Tag(name = "설정 직원 자격 정보 수정", description = "직원 자격 정보 수정")
    @PutMapping(value = "/certificate")
    public ResponseEntity<ResponseDTO> updateCertificate(@RequestBody List<SettingCertificateDTO> certificateDTO){
        System.out.println("degreeDTO = " + certificateDTO);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "자격 정보 수정 성공", settingMemberService.updateCertificate(certificateDTO)));
    }

    /**
     * 직원 경력 정보 수정
     */
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN')")
    @Tag(name = "설정 직원 경력 정보 수정", description = "직원 경력 정보 수정")
    @PutMapping(value = "/career")
    public ResponseEntity<ResponseDTO> updateCareer(@RequestBody List<SettingCareerDTO> careerDTO){
        System.out.println("degreeDTO = " + careerDTO);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "경력 정보 수정 성공", settingMemberService.updateCareer(careerDTO)));
    }

    /**
     * 직원 학위 정보 파일 등록
     */
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN')")
    @Tag(name = "설정 직원 학위 정보 파일 등록", description = "직원 학위 정보 파일 등록")
    @PostMapping("/degreeFile")
    public ResponseEntity<ResponseDTO> insertDegreeFile(@ModelAttribute SettingDegreeDTO degreeDTO, MultipartFile degreeFile){
        System.out.println("degreeDTO = " + degreeDTO);
        System.out.println("degreeFile = " + degreeFile);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "학위 정보 등록 파일 성공", settingMemberService.insertDegreeFile(degreeDTO, degreeFile)));
    }


    /**
     * 직원 자격 정보 파일 등록
     */
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN')")
    @Tag(name = "설정 직원 자격 정보 파일 등록", description = "직원 자격 정보 파일 등록")
        @PostMapping("/certificateFile")
    public ResponseEntity<ResponseDTO> insertCertificateFile(@ModelAttribute SettingCertificateDTO certificateDTO, MultipartFile certificateFile){
        System.out.println("degreeDTO = " + certificateDTO);
        System.out.println("degreeFile = " + certificateFile);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "자격 파일 등록 성공", settingMemberService.insertCertificateFile(certificateDTO, certificateFile)));
    }

    /**
     * 직원 경력 정보 파일 등록
     */
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN')")
    @Tag(name = "설정 직원 경력 정보 파일 등록", description = "직원 경력 정보 파일 등록")
    @PostMapping("/careerFile")
    public ResponseEntity<ResponseDTO> insertCareerFile(@ModelAttribute SettingCareerDTO careerDTO, MultipartFile careerFile){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "경력 정보 등록 파일 성공", settingMemberService.insertCareerFile(careerDTO, careerFile)));
    }

    /**
     * 직원 인사 조회 (학위, 자격, 경력, 통장)
     */
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN')")
    @Tag(name = "설정 직원 인사 조회", description = "학위, 자격, 경력, 통장 정보 조회")
    @GetMapping("/searchResourcesInformation/{memCode}")
    public ResponseEntity<ResponseDTO> searchResourcesInformation(
            @PathVariable int memCode){

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", settingMemberService.searchResourcesInformation(memCode)));
    }


    /**
     * 직원 전체 조회, 페이징처리
     */
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN') or hasAuthority('USER')")
    @Tag(name = "설정 직원 전체 조회", description = "직원 전체 조회, 퇴사 직원 포함, 페이징 처리")
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
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN') or hasAuthority('USER')")
    @Tag(name = "설정 직원 이름으로 검색", description = "이름으로 검색")
    @GetMapping("/search")
    public ResponseEntity<ResponseDTO> searchMemberList(
            @RequestParam(name = "s", defaultValue = "all") String search){

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", settingMemberService.searchMemberList(search)));
    }

    /**
     * 부서 조회
     * @return 전체 부서 리스트
     */
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN') or hasAuthority('USER')")
    @Tag(name = "설정 부서 조회", description = "직원 등록시 부서 선택 창에 출력할 부서 조회")
    @GetMapping("/depSearch")
    public ResponseEntity<ResponseDTO> searchDepName(){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", settingMemberService.searchDepName()));
    }

    /**
     * 직급 조회
     * @return 전체 직급 리스트
     */
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN') or hasAuthority('USER')")
    @Tag(name = "설정 직급 조회", description = "직원 등록시 직급 선택 창에 출력할 직급 조회")
        @GetMapping("/position")
    public ResponseEntity<ResponseDTO> searchPosition(){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", settingMemberService.searchPosition()));
    }

    /**
     *급여 통장 정보 등록
     */
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN')")
    @Tag(name = "설정 통장 정보 등록", description = "직원 급여 통장 정보 등록")
    @PostMapping("/salary")
    public ResponseEntity<ResponseDTO> insertSalary(@RequestBody SettingSalaryDTO salaryDTO){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "급여 통장 정보 수정 성공", settingMemberService.insertSalary(salaryDTO)));
    }

    /**
     * 급여 통장 정보 수정
     */
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN')")
    @Tag(name = "설정 통장 정보 수정", description = "직원 급여 통장 정보 수정")
    @PutMapping(value = "/salary")
    public ResponseEntity<ResponseDTO> updateSalary(@RequestBody SettingSalaryDTO salaryDTO){
        System.out.println("salaryDTO = " + salaryDTO);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "통장 정보 수정 성공", settingMemberService.updateSalary(salaryDTO)));
    }

    /**
     * 급여 통장 파일 등록
     */
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN')")
    @Tag(name = "설정 통장 파일 등록", description = "직원 급여 통장 파일 등록")
    @PostMapping("/salaryFile")
    public ResponseEntity<ResponseDTO> insertSalaryFile(@ModelAttribute SettingSalaryDTO salaryDTO, MultipartFile salaryFile){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "통장 파일 등록 성공", settingMemberService.insertSalaryFile(salaryDTO, salaryFile)));
    }

    /**
     * 그외 파일 등록
     */
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN')")
    @Tag(name = "설정 그외 파일 등록", description = "직원 그외 통장 파일 등록")
    @PostMapping("/documentFile")
    public ResponseEntity<ResponseDTO> insertDocumentFile(@ModelAttribute SettingDocumentFileDTO etcfileDTO, MultipartFile etcFile){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "그외 인사 파일 등록 성공", settingMemberService.insertDocumentFile(etcfileDTO, etcFile)));
    }

    /**
     * 그외 파일 수정
     */
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN')")
    @Tag(name = "설정 그외 파일 수정", description = "직원 그외 통장 파일 수정")
        @PutMapping(value = "/documentFile")
    public ResponseEntity<ResponseDTO> updateDocumentFile(@ModelAttribute SettingDocumentFileDTO etcfileDTO, MultipartFile etcFile){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "그외 인사 파일 수정 성공", settingMemberService.updateDocumentFile(etcfileDTO, etcFile)));
    }


    /**
     * 연차 수정
     */
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN')")
    @Tag(name = "설정 연차 수정", description = "직원 연차 수정")
    @PutMapping("/updateVacation")
    public ResponseEntity<ResponseDTO> updateVacation(@RequestBody MPHoldVacationDTO holdVacationDTO){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "연차 수정 성공", settingMemberService.updateVacation(holdVacationDTO)));
    }

    /**
     * 통장 파일 수정
     */
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN')")
    @Tag(name = "설정 통장 파일 수정", description = "직원 통장 파일 수정")
    @PutMapping(value = "/salaryFile")
    public ResponseEntity<ResponseDTO> updateSalaryFile(@ModelAttribute SettingSalaryFileDTO salaryFileDTO, MultipartFile salaryFile){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "통장 파일 수정 성공", settingMemberService.updateSalaryFile(salaryFileDTO, salaryFile)));
    }

    /**
     * 자격 파일 수정
     */
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN')")
    @Tag(name = "설정 자격 파일 수정", description = "직원 자격 파일 수정")
    @PutMapping(value = "/certificateFile")
    public ResponseEntity<ResponseDTO> updateCertificateFile(@ModelAttribute SettingCertificateFileDTO certificateFileDTO, MultipartFile certificateFile){
        System.out.println("certificateFileDTO = " + certificateFileDTO);
        System.out.println("certificateFile = " + certificateFile);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "자격 파일 수정 성공", settingMemberService.updateCertificateFile(certificateFileDTO, certificateFile)));
    }

    /**
     * 학위 파일 수정
     */
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN')")
    @Tag(name = "설정 학위 파일 수정", description = "직원 학위 파일 수정")
    @PutMapping(value = "/degreeFile")
    public ResponseEntity<ResponseDTO> updateDegreeFile(@ModelAttribute SettingDegreeFileDTO degreeFileDTO, MultipartFile degreeFile){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "학위 파일 수정 성공", settingMemberService.updateDegreeFile(degreeFileDTO, degreeFile)));
    }

    /**
     * 경력 파일 수정
     */
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN')")
    @Tag(name = "설정 경력 파일 수정", description = "직원 경력 파일 수정")
    @PutMapping(value = "/careerFile")
    public ResponseEntity<ResponseDTO> updateCareerFile(@ModelAttribute SettingCareerFileDTO careerFileDTO, MultipartFile careerFile){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "경력 파일 수정 성공", settingMemberService.updateCareerFile(careerFileDTO, careerFile)));
    }

    /**
     * 전직원 근태 조회
     */
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN') or hasAuthority('USER')")
    @Tag(name = "설정 전직원 근태 조회", description = "전직원 근태 조회(년월)")
    @PostMapping("/attendance")
    public ResponseEntity<ResponseDTO> AllMemberAttendance(@RequestBody SettingSearchValueDTO valueDTO){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "전직원 근태 조회 성공", settingMemberService.AllMemberAttendance(valueDTO)));
    }

    /**
     * 통장 파일 삭제
     */
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN')")
    @Tag(name = "설정 통장 파일 삭제", description = "통장 파일 삭제")
    @DeleteMapping("/salaryFile")
    public ResponseEntity<ResponseDTO> deleteSalaryFile(@ModelAttribute SettingSalaryFileDTO salaryFileDTO){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "통장 파일 삭제 성공", settingMemberService.deleteSalaryFile(salaryFileDTO)));
    }
    /**
     * 그외 파일 삭제
     */
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN')")
    @Tag(name = "설정 그외 파일 삭제", description = "그외 파일 삭제")
    @DeleteMapping("/documentFile")
    public ResponseEntity<ResponseDTO> deleteDocumentFile(@ModelAttribute SettingDocumentFileDTO documentFileDTO){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "그외 파일 삭제 성공", settingMemberService.deleteDocumentFile(documentFileDTO)));
    }
    /**
     * 학위 파일 삭제
     */
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN')")
    @Tag(name = "설정 학위 파일 삭제", description = "학위 파일 삭제")
    @DeleteMapping("/degreeFile")
    public ResponseEntity<ResponseDTO> deleteDegreeFile(@ModelAttribute SettingDegreeFileDTO degreeFileDTO){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "학위 파일 삭제 성공", settingMemberService.deleteDegreeFile(degreeFileDTO)));
    }

    /**
     * 자격 파일 삭제
     */
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN')")
    @Tag(name = "설정 자격 파일 삭제", description = "자격 파일 삭제")
    @DeleteMapping("/certificateFile")
    public ResponseEntity<ResponseDTO> deleteCertificateFile(@ModelAttribute SettingCertificateFileDTO certificateFileDTO){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "자격 파일 삭제 성공", settingMemberService.deleteCertificateFile(certificateFileDTO)));
    }
    /**
     * 경력 파일 삭제
     */
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN')")
    @Tag(name = "설정 경력 파일 삭제", description = "경력 파일 삭제")
    @DeleteMapping("/careerFile")
    public ResponseEntity<ResponseDTO> deleteCareerFile(@ModelAttribute SettingCareerFileDTO careerFileDTO){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "경력 파일 삭제 성공", settingMemberService.deleteCareerFile(careerFileDTO)));
    }

    /**
     * 직원 자격 정보 삭제
     */
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN')")
    @Tag(name = "설정 자격 정보 삭제", description = "자격 정보 삭제")
    @DeleteMapping(value = "/certificate")
    public ResponseEntity<ResponseDTO> deleteCertificate(@RequestBody SettingCertificateDTO certificateDTO){
        System.out.println("certificateDTO = " + certificateDTO);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "자격 정보 삭제 성공", settingMemberService.deleteCertificate(certificateDTO)));
    }

    /**
     * 직원 경력 정보 삭제
     */
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN')")
    @Tag(name = "설정 경력 정보 삭제", description = "경력 정보 삭제")
    @DeleteMapping(value = "/career")
    public ResponseEntity<ResponseDTO> deleteCareer(@RequestBody SettingCareerDTO careerDTO){
        System.out.println("careerDTO = " + careerDTO);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "경력 정보 삭제 성공", settingMemberService.deleteCareer(careerDTO)));
    }

    /**
     * 직원 학위 정보 삭제
     */
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN')")
    @Tag(name = "설정 학위 정보 삭제", description = "학위 정보 삭제")
    @DeleteMapping(value = "/degree")
    public ResponseEntity<ResponseDTO> deleteDegree(@RequestBody SettingDegreeDTO degreeDTO){
        System.out.println("degreeDTO = " + degreeDTO);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "학위 정보 삭제 성공", settingMemberService.deleteDegree(degreeDTO)));
    }

    /**
     * 직원 통장 정보 삭제
     */
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN')")
    @Tag(name = "설정 통장 정보 삭제", description = "통장 정보 삭제")
    @DeleteMapping(value = "/salary")
    public ResponseEntity<ResponseDTO> deleteSalary(@RequestBody SettingSalaryDTO salaryDTO){
        System.out.println("salaryDTO = " + salaryDTO);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "통장 정보 삭제 성공", settingMemberService.deleteSalary(salaryDTO)));
    }

}
