package com.wisehr.wisehr.dataFormat.controller;


import com.wisehr.wisehr.common.ResponseDTO;
import com.wisehr.wisehr.dataFormat.service.DataFormatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/dataFormat")
public class DataFormatController {
    private final DataFormatService dataFormatService;


    public DataFormatController(DataFormatService dataFormatService) {
        this.dataFormatService = dataFormatService;
    }

    /***
     * 서식자료 등록
     */
    @PostMapping("/data")
    public ResponseEntity<ResponseDTO> insertDataFormat(MultipartFile dataFormatFile){

        log.info("dataFormatFile ========= " + dataFormatFile);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "서식자료 등록성공", dataFormatService.insertDataFormat(dataFormatFile)));

    }

//    @GetMapping("/data")
//    public ResponseEntity<ResponseDTO> allDataFormatPaging(
//            @RequestParam(name = "offset", defaultValue = "1")String offset){
//        log.info("서식자료실전체 조회");
//        log.info("페이지 번호 = =" + offset);
//    }


}
