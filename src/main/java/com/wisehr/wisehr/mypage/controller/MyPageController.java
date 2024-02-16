package com.wisehr.wisehr.mypage.controller;

import com.wisehr.wisehr.common.ResponseDTO;
import com.wisehr.wisehr.mypage.dto.MPDocumentFileDTO;
import com.wisehr.wisehr.mypage.dto.MPMyPageDTO;
import com.wisehr.wisehr.mypage.dto.MPPassDTO;
import com.wisehr.wisehr.mypage.service.MyPageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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


//    서류함 조회
    @GetMapping("/document/{memCode}")
    public ResponseEntity<ResponseDTO> selectDocumentDetail(@PathVariable int memCode){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "서류함 상세조회 성공" , myPageService.selectDocument(memCode)));
    }

//    소유/ 소멸 예정 연차 조회
    @GetMapping("/ownAnnual/{memCode}")
    public ResponseEntity<ResponseDTO> selectVacationHistoryDetail(@PathVariable int memCode){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "소유/소멸연차 상세조회 성공" , myPageService.selectVacationHistory(memCode)));
    }

//    사용한 연차 기록을 알기 위한 고유 결재 코드를 가져오는 로직
    @GetMapping("/annualHistory/{memCode}/{year}")
    public ResponseEntity<ResponseDTO> selectAnnualHistoryDetail(@PathVariable int memCode,@PathVariable String year){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "사용 연차 상세조회 성공" , myPageService.selectAnnualHistory(memCode,year)));
    }

//    개인 정보 수정
    @PutMapping(value = "/updateMem")
    public ResponseEntity<ResponseDTO> updateProduct(@ModelAttribute MPMyPageDTO productDTO) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "수정 성공",  myPageService.updateMem(productDTO)));
    }

//    이미 입력한 서명이 있는지 조회
    @GetMapping("/selectSign/{memCode}")
    public ResponseEntity<ResponseDTO> selectSign(@PathVariable int memCode){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "서명 조회 성공" , myPageService.selectSign(memCode)));
    }

    @PostMapping("/insertSign")
    public ResponseEntity<ResponseDTO> insertSign(@ModelAttribute MPDocumentFileDTO myPageDTO, MultipartFile productImage){

        return ResponseEntity.ok()
                .body(new ResponseDTO(HttpStatus.OK, "서명 입력 성공"
                        , myPageService.insertSign(myPageDTO, productImage)));
    }

    @PutMapping("/updateSign")
    public ResponseEntity<ResponseDTO> updateSign(@ModelAttribute MPDocumentFileDTO myPageDTO, MultipartFile productImage){

        return ResponseEntity.ok()
                .body(new ResponseDTO(HttpStatus.OK, "서명 수정 성공"
                        , myPageService.updateSign(myPageDTO, productImage)));
    }

    @PutMapping("/updatePass")
    public ResponseEntity<ResponseDTO> updatePass(@ModelAttribute MPPassDTO productDTO) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "비밀번호 수정 성공",  myPageService.updatePass(productDTO)));
    }


//    프로필 사진 조회
    @GetMapping("/selectProfile/{memCode}")
    public ResponseEntity<ResponseDTO> selectProfile(@PathVariable int memCode){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "프로필 조회 성공" , myPageService.selectProfile(memCode)));
    }

//    서류함만!! 조회
    @GetMapping("/selectDoc/{memCode}")
    public ResponseEntity<ResponseDTO> selectDoc(@PathVariable int memCode){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "서류함만!! 조회 성공" , myPageService.selectDoc(memCode)));
    }

    @GetMapping("/selectSal/{memCode}")
    public ResponseEntity<ResponseDTO> selectSal(@PathVariable int memCode){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "통장만!! 조회 성공" , myPageService.selectSal(memCode)));
    }


}
