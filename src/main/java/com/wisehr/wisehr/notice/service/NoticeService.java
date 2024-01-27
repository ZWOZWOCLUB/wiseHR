package com.wisehr.wisehr.notice.service;
import com.wisehr.wisehr.notice.dto.NoticeDTO;
import com.wisehr.wisehr.notice.entity.Notice;
import com.wisehr.wisehr.notice.repository.NoticeRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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


        }
        return (result > 0)? "공지등록 성공": "공지등록 실패";
    }
}
