package com.wisehr.wisehr.mypage.service;

import com.wisehr.wisehr.mypage.dto.*;
import com.wisehr.wisehr.mypage.entity.*;
import com.wisehr.wisehr.mypage.repository.*;
import com.wisehr.wisehr.util.FileUploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Array;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MyPageService {
    private final MyPageRepository myPageRepository;
    private final DegreeRepository degreeRepository;
    private final CertificateRepository certificateRepository;
    private final CareerRepository careerRepository;
    private final AttendanceRepository attendanceRepository;
    private final DocumentRepository documentRepository;
    private final VacationHistoryRepository vacationHistoryRepository;
    private final HoldVacationRepository holdVacationRepository;
    private final MyPageAnnualRepository myPageAnnualRepository;
    private final DocumentFileRepository documentFileRepository;
    private final VacationHistoryAndApprovalPaymentRepository vacationHistoryAndApprovalPaymentRepository;
    private final ModelMapper modelMapper;

    /* 이미지 저장 할 위치 및 응답 할 이미지 주소 */
    @Value("${image.image-dir}")
    private String IMAGE_DIR;

    @Value("${image.image-url}")
    private String IMAGE_URL;

    public MyPageService(MyPageRepository myPageRepository, DegreeRepository degreeRepository, CertificateRepository certificateRepository, CareerRepository careerRepository, AttendanceRepository attendanceRepository,
                         DocumentFileRepository documentFileRepository, DocumentRepository documentRepository,
                         VacationHistoryRepository vacationHistoryRepository, HoldVacationRepository holdVacationRepository,
                         MyPageAnnualRepository myPageAnnualRepository, DocumentFileRepository documentFileRepository1, VacationHistoryAndApprovalPaymentRepository vacationHistoryAndApprovalPaymentRepository,
                         ModelMapper modelMapper) {
        this.myPageRepository = myPageRepository;
        this.degreeRepository = degreeRepository;
        this.certificateRepository = certificateRepository;
        this.careerRepository = careerRepository;
        this.attendanceRepository = attendanceRepository;
        this.documentRepository = documentRepository;
        this.vacationHistoryRepository = vacationHistoryRepository;
        this.holdVacationRepository = holdVacationRepository;
        this.myPageAnnualRepository = myPageAnnualRepository;
        this.documentFileRepository = documentFileRepository1;
        this.vacationHistoryAndApprovalPaymentRepository = vacationHistoryAndApprovalPaymentRepository;
        this.modelMapper = modelMapper;
    }


    public MyPageDTO selectMem(int memCode) {

        MyPageMember myPageMember = myPageRepository.findById(memCode).get();
        MyPageDTO settingMemberDTO = modelMapper.map(myPageMember, MyPageDTO.class);

        return settingMemberDTO;

    }


    public List<DegreeDTO> selectDegree(int memCode) {

        List<Degree> degree = degreeRepository.findByMemCode(memCode);
        List<DegreeDTO> degreeDTO = degree.stream()
                .map(exam -> modelMapper.map(exam, DegreeDTO.class))
                .collect(Collectors.toList());

        return degreeDTO;
    }

    public List<CertificateDTO> selectCer(int memCode) {
        List<Certificate> certificates = certificateRepository.findByMemCode(memCode);
        List<CertificateDTO> degreeDTO = certificates.stream()
                .map(exam -> modelMapper.map(exam, CertificateDTO.class))
                .collect(Collectors.toList());

        return degreeDTO;
    }

    public List<CareerDTO> selectCareer(int memCode) {
        List<Career> certificates = careerRepository.findByMemCode(memCode);
        List<CareerDTO> degreeDTO = certificates.stream()
                .map(exam -> modelMapper.map(exam, CareerDTO.class))
                .collect(Collectors.toList());

        return degreeDTO;
    }

    public AttendanceDTO selectAttendance(Date attWorkDate, int memCode) {


        Attendance attendance = attendanceRepository.findByAttWorkDateAndMemCode(attWorkDate,memCode);
        AttendanceDTO settingMemberDTO = modelMapper.map(attendance, AttendanceDTO.class);

        Time start = settingMemberDTO.getAttStartTime();
        Time end = settingMemberDTO.getAttEndTime();

        // Time을 LocalTime으로 변환
        LocalTime startLocalTime = start.toLocalTime();
        LocalTime endLocalTime = end.toLocalTime();

        // 두 LocalTime 객체 간의 차이 계산
        long hoursDiff = ChronoUnit.HOURS.between(startLocalTime, endLocalTime);
        long minutesDiff = ChronoUnit.MINUTES.between(startLocalTime, endLocalTime) % 60;

        // 결과 출력
        System.out.println("시간 차이: " + hoursDiff + " 시간 " + minutesDiff + " 분");

        return settingMemberDTO;
    }

//    public List<DocumentDTO> selectDocument(int memCode) {
//        List<Document> certificates = documentRepository.findByMemCode(memCode);
//        List<DocumentDTO> degreeDTO = certificates.stream()
//                .map(exam -> modelMapper.map(exam, DocumentDTO.class))
//                .collect(Collectors.toList());
//
//        return degreeDTO;
//    }
    public DocumentDTO selectDocument(int memCode) {

        Document myPageMember = documentRepository.findByMemCode(memCode);
        DocumentDTO settingMemberDTO = modelMapper.map(myPageMember, DocumentDTO.class);

        return settingMemberDTO;

    }

    public HoldVacationDTO selectVacationHistory(int memCode) {

        HoldVacation myPageMember = holdVacationRepository.findByMemCode(memCode);
        HoldVacationDTO settingMemberDTO = modelMapper.map(myPageMember, HoldVacationDTO.class);

        return settingMemberDTO;
    }


    public List<AnnualDTO> selectAnnualHistory(int memCode) {

        List<VacationHistoryAndApprovalPayment> degree = vacationHistoryAndApprovalPaymentRepository.findByMemCode(memCode);
        List<VacationHistoryAndApprovalPaymentDTO> degreeDTO = degree.stream()
                .map(exam -> modelMapper.map(exam, VacationHistoryAndApprovalPaymentDTO.class))
                .collect(Collectors.toList());

        System.out.println("왜 안나옴?????/");
//       고유 결재 코드만 가져오기
        List<String> code = new ArrayList<>();
        for (VacationHistoryAndApprovalPaymentDTO exam : degreeDTO){
            code.add(exam.getAppCode().getPayCode());
        }
        System.out.println("code = " + code);

//        고유 결재 코드를 사용해서 연차 테이블의 이력을 가져오기
        List<AnnualDTO> annualDTOS = new ArrayList<>();
        for (String exam : code){
            System.out.println("exam = " + exam);
            Annual myPageMember = myPageAnnualRepository.findByPayCode(exam);
            AnnualDTO settingMemberDTO = modelMapper.map(myPageMember, AnnualDTO.class);
            annualDTOS.add(settingMemberDTO);
        }

        System.out.println("annualDTOS = " + annualDTOS);

        return annualDTOS;
    }

    @Transactional
    public String updateMem(MyPageDTO productDTO) {

        log.info("[ProductService] updateProduct Start ===================================");
        log.info("[ProductService] productDTO : " + productDTO);

        String replaceFileName = null;
        int result = 0;

        /* update 할 엔티티 조회 */
        MyPageMember product = myPageRepository.findById(productDTO.getMemCode()).get();

        System.out.println("product = " + product);

        /* update를 위한 엔티티 값 수정 */

        product = product.memBirth(productDTO.getMemBirth())
                .memEmail(productDTO.getMemEmail())
                .memPhone(productDTO.getMemPhone())
                .memAddress(productDTO.getMemAddress()).build();

        System.out.println("product = " + product);


        result = 1;

        log.info("[ProductService] updateProduct End ===================================");
        return (result > 0) ? "회원정보 업데이트 성공" : "회원정보 업데이트 실패";
    }

    @Transactional
    public String insertSign(DocumentFileDTO productDTO, MultipartFile productImage) {
        log.info("[ProductService] insertProduct Start ===================");
        log.info("[ProductService] productDTO : " + productDTO);

        String imageName = UUID.randomUUID().toString().replace("-", "");
        String replaceFileName = null;
        int result = 0; // 결과에 따른 값을 구분하기위한 용도의 변수
        System.out.println(productImage.getContentType());
        System.out.println(productImage.getOriginalFilename());
        System.out.println(productImage.getName());
        String[] extend = productImage.getOriginalFilename().split("\\.");

        System.out.println("Arrays.toString(extend) = " + Arrays.toString(extend));
        String realExtend = extend[1];
        System.out.println(LocalDate.now().toString());
        try{
            replaceFileName = FileUploadUtils.saveFile(IMAGE_DIR, imageName, productImage);

            productDTO.setDocAtcExtends(realExtend);
            productDTO.setDocAtcConvertName(replaceFileName);
            productDTO.setDocAtcRegistDate(LocalDate.now().toString());
            productDTO.setDocAtcStorage(IMAGE_DIR);
            productDTO.setDocAtcDeleteStatus("N");
            productDTO.setDocAtcPath(IMAGE_DIR);
            productDTO.setDocAtcOriginName(productImage.getOriginalFilename());
            productDTO.setDocAtcKind("서명");
            /*
            *
            *
            * 이 부분에서 set으로 다 값 넘겨줘야 할듯(변경된 파일명, 저장위치, 사용용도 등등....)
            *
            *
            *
            * */

            log.info("[ProductService] insert Image Name : ", replaceFileName);

            // 저장을 위해서 일반 DTO객체를 Entity객체로 변경
            DocumentFile insertProduct = modelMapper.map(productDTO, DocumentFile.class);

            documentFileRepository.save(insertProduct);

            result = 1;
        } catch (Exception e){
            System.out.println("check");
            FileUploadUtils.deleteFile(IMAGE_DIR, replaceFileName);
            throw new RuntimeException(e);
        }


        log.info("[ProductService] insertProduct End ===================");
        return (result > 0)? "서명 입력 성공": "서명 입력 실패";
    }

    @Transactional
    public String updateSign(DocumentFileDTO productDTO, MultipartFile productImage) {

        log.info("[ProductService] updateProduct Start ===================================");
        log.info("[ProductService] productDTO : " + productDTO);

        String replaceFileName = null;
        int result = 0;

        try {

            /* update 할 엔티티 조회 */
            String kind = "서명";
            DocumentFile product = documentFileRepository.findByMemCodeAndDocAtcKind(productDTO.getMemCode(),kind);
            String oriImage = product.getDocAtcConvertName();
            log.info("[updateProduct] oriImage : " + oriImage);

            String[] extend = productImage.getOriginalFilename().split("\\.");
            System.out.println("Arrays.toString(extend) = " + Arrays.toString(extend));
            String realExtend = extend[1];

//            /* update를 위한 엔티티 값 수정 */
            product = product.docAtcExtends(realExtend)
                    .docAtcRegistDate(LocalDate.now().toString())
                    .docAtcOriginName(productImage.getOriginalFilename()).build();

            if(productImage != null){
                String imageName = UUID.randomUUID().toString().replace("-", "");
                replaceFileName = FileUploadUtils.saveFile(IMAGE_DIR, imageName, productImage);
                log.info("[updateProduct] InsertFileName : " + replaceFileName);

                product = product.docAtcConvertName(replaceFileName).build();	// 새로운 파일 이름으로 update
                log.info("[updateProduct] deleteImage : " + oriImage);

                boolean isDelete = FileUploadUtils.deleteFile(IMAGE_DIR, oriImage);
                log.info("[update] isDelete : " + isDelete);

            } else {

                /* 이미지 변경 없을 시 */
                product = product.docAtcConvertName(oriImage).build();
            }

            result = 1;

        } catch (IOException e) {
            log.info("[updateProduct] Exception!!");
            FileUploadUtils.deleteFile(IMAGE_DIR, replaceFileName);
            throw new RuntimeException(e);
        }
        log.info("[ProductService] updateProduct End ===================================");
        return (result > 0) ? "서명 수정 성공" : "서명 수정 실패";
    }
}
