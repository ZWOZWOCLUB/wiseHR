package com.wisehr.wisehr.notice.service;
import com.wisehr.wisehr.common.Criteria;
import com.wisehr.wisehr.notice.dto.NoticeDTO;
import com.wisehr.wisehr.notice.entity.Notice;
import com.wisehr.wisehr.notice.repository.NoticeRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NoticeService {

    private final NoticeRepository noticeRepository;

    private final ModelMapper modelMapper;


    public NoticeService(NoticeRepository noticeRepository, ModelMapper modelMapper) {
        this.noticeRepository = noticeRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public String insertNotice(NoticeDTO noticeDTO) {
        log.info("---insertNotice Start---");
        log.info(noticeDTO.toString());

        int result = 0;

        try {
            Notice insertNotice = modelMapper.map(noticeDTO, Notice.class);
            noticeRepository.save(insertNotice);

            result = 1;
        } catch (Exception e){
            log.info("---insertNotice 오류---");
            throw new RuntimeException(e);


        }
        return (result > 0)? "공지등록 성공": "공지등록 실패";
    }


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

    public Object searchCommentList(String search) {
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

//    public Page<NoticeDTO> allNoticeSearchWithPaging(Criteria criteria) {
//
//        log.info("allNoticeSearchWithPaging 서비스 시작");
//        int index = criteria.getPageNum() -1;
//        int count = criteria.getAmount();
//
//        Pageable paging = PageRequest.of(index, count, Sort.by(Sort.Direction.DESC,"notCode"));
//    }
}
