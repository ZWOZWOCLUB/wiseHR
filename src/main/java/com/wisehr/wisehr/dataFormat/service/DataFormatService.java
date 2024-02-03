package com.wisehr.wisehr.dataFormat.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wisehr.wisehr.dataFormat.dto.DataFormatDTO;
import com.wisehr.wisehr.dataFormat.dto.DataMemberDTO;
import com.wisehr.wisehr.dataFormat.entity.DataFormat;
import com.wisehr.wisehr.dataFormat.repository.DataFormatRepository;
import com.wisehr.wisehr.util.FileUploadUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Stack;

@Service
@Slf4j
public class DataFormatService {

    private final DataFormatRepository dataFormatRepository;
    private final ModelMapper modelMapper;

    public DataFormatService(DataFormatRepository dataFormatRepository, ModelMapper modelMapper) {
        this.dataFormatRepository = dataFormatRepository;
        this.modelMapper = modelMapper;
    }

    @Value("src/main/resources/static/")
    private String DATA_DIR;

    @Value("http://localhost:8001/")
    private String DATA_URL;

    //서식자료 등록
    @Transactional
    public String insertDataFormat(MultipartFile dataFormatFile) {
        log.info("==--dataInsert start--==");
        log.info("dataFormatFile =====" + dataFormatFile);


        DataFormatDTO dataFormatDTO = new DataFormatDTO();
        DataMemberDTO dataMemberDTO = new DataMemberDTO();

        dataFormatDTO.setDataCode(1L);
        //파일 이름가져오기
        dataFormatDTO.setDataName(dataFormatFile.getOriginalFilename());
        //임의 작성자 가져오기
        dataFormatDTO.setMemCode(1L);
        //파일 사이즈 가져오기
        dataFormatDTO.setDataSize(dataFormatFile.getSize());

        //파일이름은 자료코드
        String path = DATA_DIR + "dataFomats/" + dataFormatDTO.getDataCode();

        dataFormatDTO.setDataPath(path);
        dataFormatDTO.setDataDeleteStatus("N");
        dataFormatDTO.setRegistDate(new Date());
        log.info("dataFormatDTO========" + dataFormatDTO);

        String story = null;
        log.info("dataFormatFile.getOriginalFilename()" +dataFormatFile.getOriginalFilename());
        log.info("dataFormatFile.getSize()" + dataFormatFile.getSize());
        log.info("path ======" + path);
        log.info("status ======== " + dataFormatDTO.getDataDeleteStatus());
        log.info("Date ===== " + dataFormatDTO.getRegistDate());
        log.info("dataFormatDTO========" + dataFormatDTO);
        int result = 0;
        try {
            story = FileUploadUtils.saveFile(path, dataFormatFile.getName(),dataFormatFile);

            DataFormat insertDataFormat = modelMapper.map(dataFormatDTO, DataFormat.class);
            log.info("==========dataFormat=========" + insertDataFormat);
            dataFormatRepository.save(insertDataFormat);

            log.info("자료 등록 성공");

            result = 1;



        } catch (IOException e) {
            log.info("자료 등록 실패");
            throw new RuntimeException(e);
        }

//        return "";
        return (result > 0)? "자료등록 성공": "자료등록 실패";
    }
}
