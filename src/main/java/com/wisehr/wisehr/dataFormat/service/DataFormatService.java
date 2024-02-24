package com.wisehr.wisehr.dataFormat.service;

import com.wisehr.wisehr.common.Criteria;
import com.wisehr.wisehr.dataFormat.dto.DataFormatDTO;
import com.wisehr.wisehr.dataFormat.dto.DataMemberDTO;
import com.wisehr.wisehr.dataFormat.entity.DataFormat;
import com.wisehr.wisehr.dataFormat.entity.DataMember;
import com.wisehr.wisehr.dataFormat.repository.DataFormatRepository;
import com.wisehr.wisehr.dataFormat.repository.DataMemberRepository;
import com.wisehr.wisehr.util.FileUploadUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

@Service
@Slf4j
public class DataFormatService {

    private final DataFormatRepository dataFormatRepository;
    private final DataMemberRepository dataMemberRepository;
    private final ModelMapper modelMapper;

    public DataFormatService(DataFormatRepository dataFormatRepository, DataMemberRepository dataMemberRepository, ModelMapper modelMapper) {
        this.dataFormatRepository = dataFormatRepository;
        this.dataMemberRepository = dataMemberRepository;
        this.modelMapper = modelMapper;
    }

    @Value("src/main/resources/static/")
    private String DATA_DIR;

    @Value("http://localhost:8001/")
    private String DATA_URL;

    //서식자료 등록
    @Transactional
    public String insertDataFormat(DataFormatDTO dataFormatDTO,MultipartFile dataFormatFile) {
        log.info("==--dataInsert start--==");
        log.info("dataFormatFile =====" + dataFormatFile);
        DataMemberDTO dataMemberDTO = new DataMemberDTO();
        dataMemberDTO.setMemCode(1L);
        dataMemberDTO.setMemName("이동락");
        dataMemberDTO.setMemPhone("010-8888-8888");
        dataMemberDTO.setMemEmail("sujung@gma2il.com2233");
        dataMemberDTO.setMemAddress("수정된 주소22233123123");
        dataMemberDTO.setMemBirth("1800-09-08");
        dataMemberDTO.setMemPassword("0000");
        dataMemberDTO.setMemStatus("N");
        dataMemberDTO.setMemRole("ADMIN");
        dataMemberDTO.setDepCode(5L);
//        dataMemberDTO.setPosCode();
        dataFormatDTO.setDataMember(dataMemberDTO);



        //임의 작성자 가져오기
//        dataFormatDTO.setDataMember(dataFormatDTO.getDataMember());
        //파일 이름가져오기
        dataFormatDTO.setDataName(dataFormatFile.getOriginalFilename());
        //파일 사이즈 가져오기
        dataFormatDTO.setDataSize(dataFormatFile.getSize());
        //삭제 상태
        dataFormatDTO.setDataDeleteStatus("N");

        dataFormatDTO.setRegistDate(new Date());
//        dataFormatDTO.setDataMember(insertMember);


//        dataFormatRepository.save(insertData);
//        DataFormatDTO dataFormatDTO = new DataFormatDTO();
//        System.out.println("insertData" + insertData);
//        DataMember insertResult = dataMemberRepository.save(insertMember);
//        System.out.println("insertResult" + insertResult);


        System.out.println("dataFormatFile" + dataFormatFile);
        //파일이름은 자료코드
        String path = DATA_DIR + "dataFomats/" + dataFormatDTO.getDataCode();
        dataFormatDTO.setDataPath(path);

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
            FileUploadUtils.saveFile(path, dataFormatFile.getOriginalFilename(), dataFormatFile);

            // DataFormat 객체 매핑 및 저장
            DataFormat insertDataFormat = modelMapper.map(dataFormatDTO, DataFormat.class);
            insertDataFormat = dataFormatRepository.save(insertDataFormat); // 저장 후, insertDataFormat에는 DB에서 생성된 dataCode가 채워짐
            // 저장 후 dataCode를 기반으로 실제 파일 경로 설정
            String newPath = DATA_DIR + "dataFomats/" + insertDataFormat.getDataCode();
            insertDataFormat.setDataPath(newPath);
            FileUploadUtils.saveFile(newPath, dataFormatFile.getOriginalFilename(), dataFormatFile);

            dataFormatRepository.save(insertDataFormat);


            log.info("자료 등록 성공");
            log.info("newPath {}",newPath);
            result = 1;



        } catch (IOException e) {
            log.info("자료 등록 실패");

            throw new RuntimeException(e);
        }

//        return "";
        return (result > 0)? "자료등록 성공": "자료등록 실패";
    }

    //서식자료 조회
    public Page<DataFormatDTO> allDataFormatWithPaging(Criteria criteria) {

        log.info("서식자료 전체조회 시작");
        int index = criteria.getPageNum() -1;
        int count = criteria.getAmount();

        Pageable paging = PageRequest.of(index, count, Sort.by(Sort.Direction.DESC, "dataCode"));

        Page<DataFormat> result = dataFormatRepository.findAll(paging);

        Page<DataFormatDTO> dataList = result.map(dataFormat -> modelMapper.map(dataFormat, DataFormatDTO.class));
        System.out.println("result = " + result);

        System.out.println("dataList  "+dataList);
        return dataList;
    }

    public String updateDataFormat(DataFormatDTO dataFormatDTO) {
        log.info("첨부파일 삭제 시작");
        log.info("dataFormatDTO" + dataFormatDTO);

        int result = 0;

        try {
        DataFormat dataFormat = dataFormatRepository.findById(dataFormatDTO.getDataCode()).get();

        System.out.println("dataFormat : " + dataFormat);

        dataFormat.setDataDeleteStatus("Y");

        log.info("dataFormat : " + dataFormat);

            dataFormatRepository.save(dataFormat);
            result = 1;
        } catch (Exception e){
            log.info("에러.");
        }
        log.info("첨부파일 삭제 끝" + dataFormatDTO);

        return (result> 0)? "파일삭제 성공" : "파일삭제 실패";
    }
}
