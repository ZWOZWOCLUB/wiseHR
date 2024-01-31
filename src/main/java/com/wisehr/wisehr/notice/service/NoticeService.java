package com.wisehr.wisehr.notice.service;
import com.wisehr.wisehr.common.Criteria;
import com.wisehr.wisehr.notice.dto.NotAttachedFileDTO;
import com.wisehr.wisehr.notice.dto.NoticeDTO;
import com.wisehr.wisehr.notice.entity.NotAttachedFile;
import com.wisehr.wisehr.notice.entity.Notice;
import com.wisehr.wisehr.notice.repository.NotAttachedFileRepository;
import com.wisehr.wisehr.notice.repository.NoticeRepository;
import com.wisehr.wisehr.util.FileUploadUtils;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NoticeService {

    private final NoticeRepository noticeRepository;

    private final NotAttachedFileRepository notAttachedFileRepository;


    private final ModelMapper modelMapper;



    @Value("src/main/resources/static/")
    private String IMAGE_DIR;

    @Value("http://localhost:8001/")
    private String IMAGE_URL;

    public NoticeService(NoticeRepository noticeRepository, NotAttachedFileRepository notAttachedFileRepository, ModelMapper modelMapper) {
        this.noticeRepository = noticeRepository;
        this.notAttachedFileRepository = notAttachedFileRepository;
        this.modelMapper = modelMapper;

    }

    @Transactional
    // 공지 등록
    public String insertNotice(NoticeDTO noticeDTO, MultipartFile noticeFile) {
        log.info("---insertNotice Start---");
        log.info(noticeDTO.toString());
        log.info("==========noticeFile : " + noticeFile);


        String path = IMAGE_DIR + "noticeFiles/" + noticeDTO.getNotCode();

        NotAttachedFileDTO noticeFileDTO = new NotAttachedFileDTO();

        noticeFileDTO.setNotAtcPath(path);
        noticeFileDTO.setNotAtcName(noticeFile.getName());
        noticeFileDTO.setNotice(noticeDTO);
        noticeFileDTO.setNotAtcDeleteStatus("N");



        String stroy = null;

        log.info("path : " + path);
        log.info("noticeDTO" + noticeDTO);
        int result = 0;

        try {
            stroy = FileUploadUtils.saveFile(path, noticeFile.getName(),noticeFile);


            Notice insertNotice = modelMapper.map(noticeDTO, Notice.class);
            log.info("=============================== inot : " + insertNotice);
            noticeRepository.save(insertNotice);

            NotAttachedFile notFile = modelMapper.map(noticeFileDTO, NotAttachedFile.class);

            log.info("notFile : " + notFile);

            notAttachedFileRepository.save(notFile);

            log.info("공지첨부파일 성공");


            result = 1;
        } catch (Exception e){
            log.info("---insertNotice 오류---");
            throw new RuntimeException(e);


        }
        return (result > 0)? "공지등록 성공": "공지등록 실패";
    }

    //공지 제목으로 조회
    public List<NoticeDTO> searchTitleList(String search) {
        log.info("titleSearchList시작");
        log.info("titleSearchList search : {}", search);

        List<Notice> noticeWithSearchValue = noticeRepository.findByNotNameContaining(search);
        List<NoticeDTO> noticeDTOList = noticeWithSearchValue.stream()
                .map(notice -> modelMapper.map(notice, NoticeDTO.class))
                .collect(Collectors.toList());
        log.info("titleSearchList 서비스 끝" + noticeWithSearchValue);
        System.out.println("noticeWithSearchValue = " + noticeWithSearchValue);
        return noticeDTOList;
    }

    //공지 내용으로 조회
    public List<NoticeDTO> searchCommentList(String search) {
        log.info("commentSearchList시작");
        log.info("commentSearchList search : {}", search);

        List<Notice> noticeWithSearchValue = noticeRepository.findByNotCommentContaining(search);
        List<NoticeDTO> noticeDTOList = noticeWithSearchValue.stream()
                .map(notice -> modelMapper.map(notice, NoticeDTO.class))
                .collect(Collectors.toList());
        log.info("commentSearchList 서비스 끝" + noticeWithSearchValue);
        System.out.println("noticeWithSearchValue = " + noticeWithSearchValue);

        return noticeDTOList;
    }

    //공지 작성자로 조회
    public List<NoticeDTO> searchMemberNameList(String search) {
        log.info("memberNameSearchList 시작");
        log.info("memberNameSearchList search : {}", search);

        List<Notice> noticeWithSearchValue = noticeRepository.findByNotMemberMemNameContaining(search);
        List<NoticeDTO> noticeDTOList = noticeWithSearchValue.stream()
                .map(notice -> modelMapper.map(notice, NoticeDTO.class))
                .collect(Collectors.toList());
        log.info("memberNameSearchList 서비스 끝" + noticeWithSearchValue);
        System.out.println("noticeWithSearchValue = " + noticeWithSearchValue);
        return noticeDTOList;
    }


    //공지전체조회

    public Page<NoticeDTO> allNoticeSearchWithPaging(Criteria criteria) {

        log.info("allNoticeSearchWithPaging 서비스 시작");
        int index = criteria.getPageNum() -1;
        int count = criteria.getAmount();

        Pageable paging = PageRequest.of(index, count, Sort.by(Sort.Direction.DESC,"notCode"));

        Page<Notice> result = noticeRepository.findAll(paging);

        Page<NoticeDTO> noticeList = result.map(notice -> modelMapper.map(notice, NoticeDTO.class));
        System.out.println("result = " + result);
        log.info("allNoticeSearchWithPaging 끝");

        return noticeList;
    }
}
