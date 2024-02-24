package com.wisehr.wisehr.notice.service;

import com.wisehr.wisehr.common.Criteria;
import com.wisehr.wisehr.notice.dto.NotAttachedFileDTO;
import com.wisehr.wisehr.notice.dto.NoticeDTO;
import com.wisehr.wisehr.notice.dto.NoticeResponseDTO;
import com.wisehr.wisehr.notice.entity.NotAllAlarm;
import com.wisehr.wisehr.notice.entity.NotAttachedFile;
import com.wisehr.wisehr.notice.entity.Notice;
import com.wisehr.wisehr.notice.entity.NoticeResponse;
import com.wisehr.wisehr.notice.repository.*;
import com.wisehr.wisehr.util.FileUploadUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NoticeService {

    private final NoticeRepository noticeRepository;

    private final NoticeResRepository noticeResRepository;
    private final NotAttachedFileRepository notAttachedFileRepository;

    private final NotMemberRepository notMemberRepository;
    private final ModelMapper modelMapper;
    private final NotAllAlarmRepository notAllAlarmRepository;


    @Value("src/main/resources/static/")
    private String IMAGE_DIR;

    @Value("http://localhost:8001/")
    private String IMAGE_URL;


    public NoticeService(NoticeRepository noticeRepository, NoticeResRepository noticeResRepository, NotAttachedFileRepository notAttachedFileRepository, NotMemberRepository notMemberRepository, ModelMapper modelMapper, NotAllAlarmRepository notAllAlarmRepository) {
        this.noticeRepository = noticeRepository;
        this.noticeResRepository = noticeResRepository;
        this.notAttachedFileRepository = notAttachedFileRepository;
        this.notMemberRepository = notMemberRepository;
        this.modelMapper = modelMapper;

        this.notAllAlarmRepository = notAllAlarmRepository;
    }

    @Transactional
    // 공지 등록
    public String insertNotice(NoticeDTO noticeDTO, MultipartFile noticeFiles) {
        log.info("---insertNotice Start---");
        log.info(noticeDTO.toString());
        log.info("==========noticeFiles : " + noticeFiles);
        int result = 0;

        //공지
        Notice insertNotice = modelMapper.map(noticeDTO, Notice.class);

        System.out.println("여기");


        noticeRepository.save(insertNotice);
        System.out.println("noticeDTO.getNotCode()" + insertNotice);

        log.info("공지등록 성공");


        try {
            if (noticeFiles != null && !noticeFiles.isEmpty() && noticeFiles.getSize() > 0) {
                String path = IMAGE_DIR + "noticeFiles/" + noticeDTO.getNotCode(); //파일이름이 공지사항코드가 됨

                //파일
                // 파일 원본 이름
                String originalFileName = noticeFiles.getOriginalFilename();
                // 파일 확장자 추출
                String extension = FilenameUtils.getExtension(originalFileName);
                // 저장할 파일 이름 설정
                String storedFileName = originalFileName.endsWith("." + extension) ?
                        originalFileName : originalFileName + "." + extension;
                if (!storedFileName.endsWith("." + extension)) {
                    storedFileName += "." + extension;
                }

                log.info("=====noticeFile : " + storedFileName);

                //파일저장
                String story = FileUploadUtils.saveFile(path, storedFileName, noticeFiles);

                //DTO
                NotAttachedFileDTO noticeFileDTO = new NotAttachedFileDTO();
                noticeFileDTO.setNotAtcName(storedFileName);
                noticeFileDTO.setNotAtcDeleteStatus("N");
                noticeFileDTO.setNotAtcPath(path + "/" + storedFileName);
                noticeFileDTO.setCode(insertNotice.getNotCode()); // 여기에 실제 notCode 설정

                //전환
                NotAttachedFile notFile = modelMapper.map(noticeFileDTO, NotAttachedFile.class);
                notAttachedFileRepository.save(notFile);
            } else {

                log.info("첨부파일 없음", noticeFiles);
                System.out.println("첨부파일 없음");
                result = 1;
            }

            //알림
            if (noticeDTO.getNotAllArmCheck().equals("Y")) {  // 맴버 전체에게 알림을 보냅니다.
//            List<NotMember> notMemberList = notMemberRepository.findAll();
                LocalDateTime now = LocalDateTime.now();
                NotAllAlarm notAllAlarm = new NotAllAlarm();
                notAllAlarm.setAllArmCode(7);
                notAllAlarm.setAllArmDate(now);
                notAllAlarm.setAllArmCheck("N");
                notAllAlarm.setNotCode(insertNotice.getNotCode());
                notAllAlarm.setMemCode(insertNotice.getNotMember());
                System.out.println(notAllAlarm.getMemCode());
                notAllAlarmRepository.save(notAllAlarm);


                log.info("notAllAlarmDTO ==== " + notAllAlarm);
                log.info("알람전송 완료");


            }

        } catch (Exception e) {
            log.info("LLLLLLLLLL{}", noticeDTO.getNotCode());
            System.out.println("LLLLLLLLLLLL " + noticeDTO.getNotCode());
            log.info("---insertNotice 오류---");
            throw new RuntimeException(e);


        }

        return (result > 0) ? "공지등록 성공" : "공지등록 실패";
    }


    /***
     * 공지업데이트
     * @param noticeDTO
     * @param noticeFile
     * @return
     */

    @Transactional
    public String updateNotice(NoticeDTO noticeDTO, MultipartFile noticeFile) {
        log.info(" ==============updateService Start ===========");
        log.info("noticeDTO +++=== " + noticeDTO);
        log.info("noticeFiles ===+++ " + noticeFile);

        int result = 0;

        try {

            /* update할 notice 조회 */
            Notice notice = noticeRepository.findById(noticeDTO.getNotCode()).orElseThrow(() -> new EntityNotFoundException("공지사항을 찾을 수 없습니다."));
            log.info("notice ==== " + notice);

            Optional<NotAttachedFile> attFile = notAttachedFileRepository.findById(notice.getNotAttachedFile().get(0).getNotAtcCode());


            // 공지 내용 업데이트
            notice.setNotName(noticeDTO.getNotName());
            notice.setNotComment(noticeDTO.getNotComment());

            // 첨부파일이 있을 때
            if (noticeFile != null && !noticeFile.isEmpty()) {
                String path = IMAGE_DIR + "noticeFile/" + noticeDTO.getNotCode(); // 파일 이름이 공지사항 코드가 됨
                String savedFileName = FileUploadUtils.saveFile(path, noticeFile.getOriginalFilename(), noticeFile);
                log.info("Saved File Name: " + savedFileName);

                NotAttachedFileDTO noticeFileDTO = new NotAttachedFileDTO();
                noticeFileDTO.setNotAtcName(noticeFile.getOriginalFilename());
                noticeFileDTO.setNotAtcDeleteStatus("N");
                noticeFileDTO.setNotAtcPath(path);
                noticeFileDTO.setCode(notice.getNotCode());
                noticeFileDTO.setNotAtcCode(attFile.get().getNotAtcCode());

                NotAttachedFile notFile = modelMapper.map(noticeFileDTO, NotAttachedFile.class);

                log.info("노티스 파일 : " + notFile);

                noticeRepository.save(notice);
                notAttachedFileRepository.save(notFile);

            }
            log.info("Updated notice: " + notice);
            log.info("noticeFile = " + noticeFile);

            result = 1;
        } catch (IOException e) {
            log.error("File upload error", e);
            // 필요한 경우 사용자 정의 예외로 변경하여 처리할 수 있음
        } catch (EntityNotFoundException e) {
            log.error("Notice not found", e);
            // 적절한 예외 처리
        }

        log.info("updateNotice 끝");
        return (result > 0) ? "공지업뎃 성공" : "공지업뎃 실패";
    }



    //공지 상세 조회
    public List<NoticeResponseDTO> noticeDetail(String search) {
        log.info("titleSearchList시작");
        log.info("titleSearchList search : {}", search);

        List<NoticeResponse> searchNoticeList = noticeResRepository.findByNotCode(search);
        List<NoticeResponseDTO> SearchNoticeDTOList = searchNoticeList.stream()
                .map(noticeResponse -> modelMapper.map(noticeResponse, NoticeResponseDTO.class))
                .collect(Collectors.toList());

        log.info("searchNoticeList : " + searchNoticeList);
        System.out.println("SearchNoticeDTOList = " + SearchNoticeDTOList);
        return SearchNoticeDTOList;
    }


    //공지 제목으로 조회
    public List<NoticeResponseDTO> searchTitleList(String search) {
        log.info("titleSearchList시작");
        log.info("titleSearchList search : {}", search);

        List<NoticeResponse> noticeWithSearchValue = noticeResRepository.findByNotNameContaining(search);
        List<NoticeResponseDTO> noticeDTOList = noticeWithSearchValue.stream()
                .map(noticeResponse -> modelMapper.map(noticeResponse, NoticeResponseDTO.class))
                .collect(Collectors.toList());
        log.info("titleSearchList 서비스 끝" + noticeWithSearchValue);
        System.out.println("noticeWithSearchValue = " + noticeWithSearchValue);
        return noticeDTOList;
    }

    //공지 내용으로 조회
    public List<NoticeResponseDTO> searchCommentList(String search) {
        log.info("commentSearchList시작");
        log.info("commentSearchList search : {}", search);

        List<NoticeResponse> noticeWithSearchValue = noticeResRepository.findByNotCommentContaining(search);
        List<NoticeResponseDTO> noticeDTOList = noticeWithSearchValue.stream()
                .map(noticeResponse -> modelMapper.map(noticeResponse, NoticeResponseDTO.class))
                .collect(Collectors.toList());
        log.info("commentSearchList 서비스 끝" + noticeWithSearchValue);
        System.out.println("noticeWithSearchValue = " + noticeWithSearchValue);

        return noticeDTOList;
    }

    //공지 작성자로 조회
    public List<NoticeResponseDTO> searchMemberNameList(String search) {
        log.info("memberNameSearchList 시작");
        log.info("memberNameSearchList search : {}", search);

        List<NoticeResponse> noticeWithSearchValue = noticeResRepository.findByNotMemberMemNameContaining(search);
        List<NoticeResponseDTO> noticeDTOList = noticeWithSearchValue.stream()
                .map(noticeResponse -> modelMapper.map(noticeResponse, NoticeResponseDTO.class))
                .collect(Collectors.toList());
        log.info("memberNameSearchList 서비스 끝" + noticeWithSearchValue);
        System.out.println("noticeWithSearchValue = " + noticeWithSearchValue);
        return noticeDTOList;
    }


    //공지전체조회

    public Page<NoticeResponseDTO> allNoticeSearchWithPaging(Criteria criteria) {
        log.info("allNoticeSearchWithPaging 서비스 시작");
        int index = criteria.getPageNum() - 1;
        int count = criteria.getAmount();

        Pageable paging = PageRequest.of(index, count, Sort.by(Sort.Direction.DESC, "notCodeNumber"));
        // deleteStatus가 "N"인 공지사항만 조회
        Page<Notice> result = noticeRepository.findByNotDeleteStatus("N", paging);

        Page<NoticeResponseDTO> noticeList = result.map(notice -> modelMapper.map(notice, NoticeResponseDTO.class));
        log.info("allNoticeSearchWithPaging 끝");

        return noticeList;
    }

    @Transactional
    public String deleteNotice(List<NoticeDTO> noticeDTOs) {
        log.info("공지삭제 시작");
        log.info("noticeDTO" + noticeDTOs);

        int result = 0;

        try {
            for (NoticeDTO noticeDTO : noticeDTOs) {
                Notice notice = noticeRepository.findById(noticeDTO.getNotCode())
                        .orElseThrow(() -> new EntityNotFoundException("Notice not found with ID: " + noticeDTO.getNotCode()));
                notice.setNotDeleteStatus("Y");
                noticeRepository.save(notice);
            }
            result = 1;
        } catch (Exception e){
            log.error("공지삭제에러", e);
            return "공지 삭제 실패: " + e.getMessage();
        }
        log.info("공지삭제 끝" + noticeDTOs);
        return (result > 0)? "공지삭제성공":"공지삭제실패";


    }
}
