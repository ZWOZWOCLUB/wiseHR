package com.wisehr.wisehr.dataFormat.controller;


import com.wisehr.wisehr.common.Criteria;
import com.wisehr.wisehr.common.PageDTO;
import com.wisehr.wisehr.common.PagingResponseDTO;
import com.wisehr.wisehr.common.ResponseDTO;
import com.wisehr.wisehr.dataFormat.dto.DataFormatDTO;
import com.wisehr.wisehr.dataFormat.service.DataFormatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN')")
    @PostMapping("/data")
    public ResponseEntity<ResponseDTO> insertDataFormat(
            @ModelAttribute DataFormatDTO dataFormatDTO,
            MultipartFile dataFormatFile
    ){
        System.out.println("dataFormatFile = " + dataFormatFile);
        System.out.println("dataFormatFile ====== " + dataFormatFile.getOriginalFilename() + "withSize" + dataFormatFile.getSize());


        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "서식자료 등록성공", dataFormatService.insertDataFormat(dataFormatDTO,dataFormatFile)));

    }
    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("/allData")
    public ResponseEntity<ResponseDTO> allDataSearchWithPaging(
            @RequestParam(name = "offset", defaultValue = "1")String offset){
        log.info("전제 자료 조회");
        log.info("페이지 번호 ", offset);

        Criteria criteria = new Criteria(Integer.valueOf(offset), 10);

        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();

        //offset의 번호에 맞는 페이지에 뿌려줄 공지사항정보
        Page<DataFormatDTO> dataFormatList = dataFormatService.allDataFormatWithPaging(criteria);

        pagingResponseDTO.setData(dataFormatList);
        pagingResponseDTO.setPageInfo(new PageDTO(criteria, (int) dataFormatList.getTotalElements()));

        log.info("allDataSearchWithPaging 끝");
        log.info(pagingResponseDTO.getData().toString());

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "자료전체조회성공", pagingResponseDTO));
    }

    @PreAuthorize("hasAuthority('SUPERADMIN') or hasAuthority('ADMIN')")
    //자료삭제
    @PutMapping("/deleteData")
    public ResponseEntity<ResponseDTO> deleteData(@RequestBody DataFormatDTO dataCode){

        log.info("dataFormat디티오", dataCode);
        System.out.println("dataFormatDTO = " + dataCode);

         return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "첨부파일 삭제 성공", dataFormatService.updateDataFormat(dataCode)));
    }


}
