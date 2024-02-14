package com.wisehr.wisehr.mypage.service;

import com.wisehr.wisehr.exception.PasswordNotEqualException;
import com.wisehr.wisehr.mypage.dto.*;
import com.wisehr.wisehr.mypage.entity.*;
import com.wisehr.wisehr.mypage.repository.*;
import com.wisehr.wisehr.util.FileUploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private final MPMyPageRepository myPageRepository;
    private final MPDegreeRepository degreeRepository;
    private final MPCertificateRepository certificateRepository;
    private final MPCareerRepository careerRepository;
    private final MPAttendanceRepository attendanceRepository;
    private final MPDocumentRepository documentRepository;
    private final MPVacationHistoryRepository vacationHistoryRepository;
    private final MPHoldVacationRepository holdVacationRepository;
    private final MPMyPageAnnualRepository myPageAnnualRepository;
    private final MPDocumentFileRepository documentFileRepository;
    private final MPDepartmentRepository mpDepartmentRepository;
    private final MPPositionRepository mpPositionRepository;
    private final MPVacationHistoryAndApprovalPaymentRepository vacationHistoryAndApprovalPaymentRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    /* 이미지 저장 할 위치 및 응답 할 이미지 주소 */
    @Value("${image.image-dir}")
    private String IMAGE_DIR;

    @Value("${image.image-url}")
    private String IMAGE_URL;

    public MyPageService(MPMyPageRepository myPageRepository, MPDegreeRepository degreeRepository, MPCertificateRepository certificateRepository, MPCareerRepository careerRepository, MPAttendanceRepository attendanceRepository,
                         MPDocumentFileRepository documentFileRepository, MPDocumentRepository documentRepository,
                         MPVacationHistoryRepository vacationHistoryRepository, MPHoldVacationRepository holdVacationRepository,
                         MPMyPageAnnualRepository myPageAnnualRepository, MPDocumentFileRepository documentFileRepository1, MPDepartmentRepository mpDepartmentRepository, MPPositionRepository mpPositionRepository, MPVacationHistoryAndApprovalPaymentRepository vacationHistoryAndApprovalPaymentRepository,
                         ModelMapper modelMapper, BCryptPasswordEncoder passwordEncoder, BCryptPasswordEncoder passwordEncoder1) {
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
        this.mpDepartmentRepository = mpDepartmentRepository;
        this.mpPositionRepository = mpPositionRepository;
        this.vacationHistoryAndApprovalPaymentRepository = vacationHistoryAndApprovalPaymentRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder1;
    }


    public MPMyPageDTO selectMem(int memCode) {

        MPMyPageMember myPageMember = myPageRepository.findById(memCode).get();
        MPMyPageDTO settingMemberDTO = modelMapper.map(myPageMember, MPMyPageDTO.class);
        System.out.println("settingMemberDTO = " + settingMemberDTO);

        if(settingMemberDTO.getDepCode() != 0 ){
            MPDepartment mpDepartment = mpDepartmentRepository.findByDepCode(settingMemberDTO.getDepCode());
            MPDepartmentDTO departmentDTO = modelMapper.map(mpDepartment, MPDepartmentDTO.class);
            System.out.println("departmentDTO = " + departmentDTO);

            settingMemberDTO.setDepName(departmentDTO.getDepName());
        }

        if(settingMemberDTO.getPosCode() != 0) {
            MPPosition mpPosition = mpPositionRepository.findByPosCode(settingMemberDTO.getPosCode());
            MPPositionDTO mpPositionDTO = modelMapper.map(mpPosition, MPPositionDTO.class);
            System.out.println("mpPositionDTO = " + mpPositionDTO);

            settingMemberDTO.setPosName(mpPositionDTO.getPosName());
        }

        return settingMemberDTO;

    }


    public List<MPDegreeDTO> selectDegree(int memCode) {

        List<MPDegree> degree = degreeRepository.findByMemCode(memCode);
        List<MPDegreeDTO> degreeDTO = degree.stream()
                .map(exam -> modelMapper.map(exam, MPDegreeDTO.class))
                .collect(Collectors.toList());

        return degreeDTO;
    }

    public List<MPCertificateDTO> selectCer(int memCode) {
        List<MPCertificate> certificates = certificateRepository.findByMemCode(memCode);
        List<MPCertificateDTO> degreeDTO = certificates.stream()
                .map(exam -> modelMapper.map(exam, MPCertificateDTO.class))
                .collect(Collectors.toList());

        return degreeDTO;
    }

    public List<MPCareerDTO> selectCareer(int memCode) {
        List<MPCareer> certificates = careerRepository.findByMemCode(memCode);
        List<MPCareerDTO> degreeDTO = certificates.stream()
                .map(exam -> modelMapper.map(exam, MPCareerDTO.class))
                .collect(Collectors.toList());

        return degreeDTO;
    }

    public MPAttendanceDTO selectAttendance(Date attWorkDate, int memCode) {


        MPAttendance attendance = attendanceRepository.findByAttWorkDateAndMemCode(attWorkDate,memCode);
        MPAttendanceDTO settingMemberDTO = modelMapper.map(attendance, MPAttendanceDTO.class);

        Time start = settingMemberDTO.getAttStartTime();
        Time end = settingMemberDTO.getAttEndTime();

        // Time을 LocalTime으로 변환
        LocalTime startLocalTime = start.toLocalTime();
        LocalTime endLocalTime = end.toLocalTime();

        // 두 LocalTime 객체 간의 차이 계산
        long hoursDiff = ChronoUnit.HOURS.between(startLocalTime, endLocalTime);
        long minutesDiff = ChronoUnit.MINUTES.between(startLocalTime, endLocalTime) % 60;
        String totalWork = hoursDiff + " 시간 " + minutesDiff + " 분";

        // 결과 출력
        System.out.println("시간 차이: " + hoursDiff + " 시간 " + minutesDiff + " 분");
        settingMemberDTO.setTotalWork(totalWork);

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
    public MPDocumentDTO selectDocument(int memCode) {

        MPDocument myPageMember = documentRepository.findByMemCode(memCode);
        System.out.println("myPageMember = " + myPageMember);
        MPDocumentDTO settingMemberDTO = modelMapper.map(myPageMember, MPDocumentDTO.class);
        System.out.println("settingMemberDTO = " + settingMemberDTO);

        return settingMemberDTO;

    }

    public MPHoldVacationDTO selectVacationHistory(int memCode) {

        MPHoldVacation myPageMember = holdVacationRepository.findByMemCode(memCode);
        MPHoldVacationDTO settingMemberDTO = modelMapper.map(myPageMember, MPHoldVacationDTO.class);

        return settingMemberDTO;
    }


    public List<MPAnnualDTO> selectAnnualHistory(int memCode,String year) {
        System.out.println("year = " + year);

        List<MPVacationHistoryAndApprovalPayment> degree = vacationHistoryAndApprovalPaymentRepository.findByMemCodeOrderByVhiCodeDesc(memCode);
        List<MPVacationHistoryAndApprovalPaymentDTO> degreeDTO = degree.stream()
                .map(exam -> modelMapper.map(exam, MPVacationHistoryAndApprovalPaymentDTO.class))
                .collect(Collectors.toList());

        System.out.println("왜 안나옴?????/");
//       고유 결재 코드만 가져오기
        List<String> code = new ArrayList<>();
        for (MPVacationHistoryAndApprovalPaymentDTO exam : degreeDTO){
            code.add(exam.getAppCode().getPayCode());
        }
        System.out.println("code = " + code);

//        고유 결재 코드를 사용해서 연차 테이블의 이력을 가져오기
        List<MPAnnualDTO> annualDTOS = new ArrayList<>();
        for (String exam : code){
            System.out.println("exam = " + exam);
            MPAnnual myPageMember = myPageAnnualRepository.findByPayCodeAndVacStartDateLike(exam,"%2024%");
            MPAnnualDTO settingMemberDTO = modelMapper.map(myPageMember, MPAnnualDTO.class);
            annualDTOS.add(settingMemberDTO);
        }

        System.out.println("annualDTOS = " + annualDTOS);

        return annualDTOS;
    }

    @Transactional
    public String updateMem(MPMyPageDTO productDTO) {

        log.info("[ProductService] updateProduct Start ===================================");
        log.info("[ProductService] productDTO : " + productDTO);

        String replaceFileName = null;
        int result = 0;

        /* update 할 엔티티 조회 */
        MPMyPageMember product = myPageRepository.findById(productDTO.getMemCode()).get();

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
    public String insertSign(MPDocumentFileDTO productDTO, MultipartFile productImage) {
        log.info("[ProductService] insertProduct Start ===================");
        log.info("[ProductService] productDTO : " + productDTO);
        log.info("[ProductService] productImage : " + productImage);

        String imageName = UUID.randomUUID().toString().replace("-", "");
        String replaceFileName = null;
        int result = 0; // 결과에 따른 값을 구분하기위한 용도의 변수
        System.out.println(productImage.getContentType());
        System.out.println(productImage.getOriginalFilename());
        System.out.println(productImage.getName());
//        String[] extend = productImage.getOriginalFilename().split("\\.");
//        System.out.println("Arrays.toString(extend) = " + Arrays.toString(extend));
//        String realExtend = extend[1];
        System.out.println(LocalDate.now().toString());
        try{
            replaceFileName = FileUploadUtils.saveFile(IMAGE_DIR+"/sign/", imageName, productImage);

            productDTO.setDocAtcExtends("blob"); // 확장자 내가 임의로 씀
            productDTO.setDocAtcConvertName(replaceFileName);
            productDTO.setDocAtcRegistDate(LocalDate.now().toString());
            productDTO.setDocAtcStorage(IMAGE_DIR+"/sign/");
            productDTO.setDocAtcDeleteStatus("N");
            productDTO.setDocAtcPath(IMAGE_DIR+"/sign/");
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
            MPDocumentFile insertProduct = modelMapper.map(productDTO, MPDocumentFile.class);

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
    public String updateSign(MPDocumentFileDTO productDTO, MultipartFile productImage) {

        log.info("[ProductService] updateProduct Start ===================================");
        log.info("[ProductService] productDTO : " + productDTO);

        String replaceFileName = null;
        int result = 0;

        try {

            /* update 할 엔티티 조회 */
            String kind = "서명";
            MPDocumentFile product = documentFileRepository.findByMemCodeAndDocAtcKind(productDTO.getMemCode(),kind);
            String oriImage = product.getDocAtcConvertName();
            log.info("[updateProduct] oriImage : " + oriImage);

//            String[] extend = productImage.getOriginalFilename().split("\\.");
//            System.out.println("Arrays.toString(extend) = " + Arrays.toString(extend));
//            String realExtend = extend[1];

//            /* update를 위한 엔티티 값 수정 */
//            product = product.docAtcRegistDate(LocalDate.now().toString())
//                    .docAtcOriginName(productImage.getOriginalFilename()).build();

            if(productImage != null){
                String imageName = UUID.randomUUID().toString().replace("-", "");
                replaceFileName = FileUploadUtils.saveFile(IMAGE_DIR+"/sign/", imageName, productImage);
                log.info("[updateProduct] InsertFileName : " + replaceFileName);

                System.out.println("replaceFileName = " + replaceFileName);
                product = product.docAtcRegistDate(LocalDate.now().toString())
                        .docAtcOriginName(productImage.getOriginalFilename())// 새로운 파일 이름으로 update
                        .docAtcConvertName(replaceFileName).build();
//                product = product.docAtcConvertName(replaceFileName).build();
                log.info("[updateProduct] deleteImage : " + oriImage);

                boolean isDelete = FileUploadUtils.deleteFile(IMAGE_DIR+"/sign/", oriImage); // 기존 파일을 삭제
                log.info("[update] isDelete : " + isDelete);

            } else {

                /* 이미지 변경 없을 시 */
                product = product.docAtcConvertName(oriImage).build();
            }

            result = 1;

        } catch (IOException e) {
            log.info("[updateProduct] Exception!!");
            FileUploadUtils.deleteFile(IMAGE_DIR+"/sign/", replaceFileName);
            throw new RuntimeException(e);
        }
        log.info("[ProductService] updateProduct End ===================================");
        return (result > 0) ? "서명 수정 성공" : "서명 수정 실패";
    }


    @Transactional
    public String updatePass(MPPassDTO productDTO) {

//        1. 기존 비밀번호가 맞는지 확인
        MPMyPageMember product = myPageRepository.findById(productDTO.getMemCode()).get();

        if(!passwordEncoder.matches(productDTO.getOriginMemPassword(), product.getMemPassword())){ // 둘이 비교
            throw new BadCredentialsException(productDTO.getOriginMemPassword() + "는 비밀번호가 아닙니다.");
        }

        log.info("[ProductService] updateProduct Start ===================================");
        log.info("[ProductService] productDTO : " + productDTO);

        String replaceFileName = null;
        int result = 0;

        System.out.println("product = " + product);

//        변경할 비밀번호가 확실한지 비교 확인
        System.out.println("productDTO = " + productDTO.getNewMemPassword1());
        System.out.println("productDTO = " + productDTO.getNewMemPassword2());

        if(!productDTO.getNewMemPassword1().equals(productDTO.getNewMemPassword2())){
            throw new PasswordNotEqualException(productDTO.getOriginMemPassword() + " 변경될 비밀번호가 동일하지 않습니다.");
        }

        /* update를 위한 엔티티 값 수정 */

        product = product.memPassword(passwordEncoder.encode(productDTO.getNewMemPassword1())).build();

        System.out.println("product = " + product);

        result = 1;

        log.info("[ProductService] updateProduct End ===================================");
        return (result > 0) ? "비밀번호 업데이트 성공" : "비밀번호 업데이트 실패";
    }

    public MPDocumentFileDTO selectSign(int memCode) {

        String kind = "서명";
        MPDocumentFile product = documentFileRepository.findByMemCodeAndDocAtcKind(memCode,kind);
        MPDocumentFileDTO settingMemberDTO = modelMapper.map(product, MPDocumentFileDTO.class);

        settingMemberDTO.setDocAtcPath(IMAGE_URL+"sign/"+settingMemberDTO.getDocAtcConvertName());

        return settingMemberDTO;

    }


    public MPDocumentFileDTO selectProfile(int memCode) {

        String kind = "프로필";
        MPDocumentFile product = documentFileRepository.findByMemCodeAndDocAtcKind(memCode,kind);
        MPDocumentFileDTO settingMemberDTO = modelMapper.map(product, MPDocumentFileDTO.class);

        settingMemberDTO.setDocAtcPath(IMAGE_URL+"profile/"+settingMemberDTO.getDocAtcConvertName());

        return settingMemberDTO;

    }

    public Object selectDoc(int memCode) {

        List<MPDocumentFile> certificates = documentFileRepository.findByMemCodeAndDocAtcKindNotAndDocAtcKindNot(memCode,"프로필","서명");
        List<MPDocumentFileDTO> degreeDTO = certificates.stream()
                .map(exam -> modelMapper.map(exam, MPDocumentFileDTO.class))
                .collect(Collectors.toList());

//        파일 저장 경로 지정해줘야할듯

        return degreeDTO;

//        String kind = "서명";
//        MPDocumentFile product = documentFileRepository.findByMemCodeAndDocAtcKind(memCode,kind);
//        MPDocumentFileDTO settingMemberDTO = modelMapper.map(product, MPDocumentFileDTO.class);
//
//        settingMemberDTO.setDocAtcPath(IMAGE_URL+"sign/"+settingMemberDTO.getDocAtcConvertName());
//
//        return settingMemberDTO;
    }
}
