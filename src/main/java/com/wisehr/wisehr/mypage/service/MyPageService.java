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
//import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
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
    private final MPSalAttachedFileRepository mpSalAttachedFileRepository;

    private final MPSalaryRepository mpSalaryRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    /* 이미지 저장 할 위치 및 응답 할 이미지 주소 */
    @Value("${image.image-dir}")
    private String IMAGE_DIR;

    @Value("${image.image-url}")
    private String IMAGE_URL;

    @Value("C:/uploadSign/")
    private String ROOT_LOCATION;

    public MyPageService(MPMyPageRepository myPageRepository, MPDegreeRepository degreeRepository, MPCertificateRepository certificateRepository, MPCareerRepository careerRepository, MPAttendanceRepository attendanceRepository,
                         MPDocumentFileRepository documentFileRepository, MPDocumentRepository documentRepository,
                         MPVacationHistoryRepository vacationHistoryRepository, MPHoldVacationRepository holdVacationRepository,
                         MPMyPageAnnualRepository myPageAnnualRepository, MPDocumentFileRepository documentFileRepository1, MPDepartmentRepository mpDepartmentRepository, MPPositionRepository mpPositionRepository, MPVacationHistoryAndApprovalPaymentRepository vacationHistoryAndApprovalPaymentRepository,
                         MPSalAttachedFileRepository mpSalAttachedFileRepository, ModelMapper modelMapper, BCryptPasswordEncoder passwordEncoder, MPSalaryRepository mpSalaryRepository, BCryptPasswordEncoder passwordEncoder1) {
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
        this.mpSalAttachedFileRepository = mpSalAttachedFileRepository;
        this.modelMapper = modelMapper;
        this.mpSalaryRepository = mpSalaryRepository;
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

        if(hoursDiff < 0 ||  minutesDiff < 0){
            hoursDiff = 23 + hoursDiff;
            minutesDiff = 59 + minutesDiff;
        }

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
            MPAnnual myPageMember = myPageAnnualRepository.findByPayCodeAndVacStartDateLike(exam,"%"+year+"%");
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

        product = product
                .memEmail(productDTO.getMemEmail())
                .memPhone(productDTO.getMemPhone())
                .memAddress(productDTO.getMemAddress()).build();

        System.out.println("product = " + product);


        result = 1;

        log.info("[ProductService] updateProduct End ===================================");
        return (result > 0) ? "회원정보 업데이트 성공" : "회원정보 업데이트 실패";
    }

//    서명 등록
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
        String[] extend = productImage.getOriginalFilename().split("\\.");
        System.out.println("Arrays.toString(extend) = " + Arrays.toString(extend));
        String realExtend = extend[1];
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

//        String rootLocation = ROOT_LOCATION;
//
//        String fileUploadDirectory = rootLocation + "origin/sign";
//        String thumbnailDirectory = rootLocation + "sign";
//
//        File directory = new File(fileUploadDirectory);
//        File directory2 = new File(thumbnailDirectory);
//
//        /* 파일 저장경로가 존재하지 않는 경우 디렉토리를 생성한다. */
//        if (!directory.exists() || !directory2.exists()) {
//
//            /* 폴더를 한 개만 생성할거면 mkdir, 상위 폴더도 존재하지 않으면 한 번에 생성하란 의미로 mkdirs를 이용한다. */
//            log.info("[ThumbnailController] 폴더 생성 : " + directory.mkdirs());
//            log.info("[ThumbnailController] 폴더 생성 : " + directory2.mkdirs());
//        }
//
//        /* 업르도 파일 하나하나에 대한 정보를 담을 리스트 */
//        List<Map<String, String>> fileList = new ArrayList<>();
//
//        List<MultipartFile> paramFileList = new ArrayList<>();
//        paramFileList.add(productImage);
//
//        try {
//            for (MultipartFile paramFile : paramFileList) {
//                if (paramFile.getSize() > 0) {
//                    String originFileName = paramFile.getOriginalFilename(); // origin 파일 이름을 가져온다.
//                    String ext = originFileName.substring(originFileName.lastIndexOf(".")); // 확장자 분리
//                    String savedFileName = UUID.randomUUID().toString().replace("-", "") + ext; // 이름 변환
//                    paramFile.transferTo(new File(fileUploadDirectory + "/" + savedFileName)); // 저장할 장소 + 변환한 이름으로 이름 변경
//
//                    /* DB에 업로드한 파일의 정보를 저장하는 비지니스 로직 수행 */
//                    /* 필요한 정보를 Map에 담는다. */
//                    Map<String, String> fileMap = new HashMap<>();
//                    fileMap.put("originFileName", originFileName);
//                    fileMap.put("savedFileName", savedFileName);
//                    fileMap.put("savePath", thumbnailDirectory);
//
//                    /* 제목 사진과 나머지 사진을 구분하고 썸네일도 생성한다. */
//                    int width = 0;
//                    int height = 0;
//
//                    String fieldName = paramFile.getName();
//
//                    fileMap.put("fileType", "sign");
//
//                    /* 썸네일로 변환 할 사이즈를 지정한다. */
//                    width = 300;
//                    height = 150;
//
//                    /* 썸네일로 변환 후 저장한다. */
////                    Thumbnails.of(fileUploadDirectory + "/" + savedFileName).size(width, height)
////                            .toFile(thumbnailDirectory + "/businessLicense_" + savedFileName);
////
////                    /* 나중에 웹서버에서 접근 가능한 경로 형태로 썸네일의 저장 경로도 함께 저장한다. */
////                    fileMap.put("thumbnailPath", "/businessLicense_" + savedFileName);
////
////                    fileList.add(fileMap);
//                }
//            }
//
//            log.info("[ThumbnailController] fileList =============================: " + fileList);
//
//            /* 서비스를 요청할 수 있도록 BoardDTO에 담는다. */
//
////            projectMakeDTO.setAttachmentList(new ArrayList<ProjectMakeFileDTO>());
////            List<ProjectMakeFileDTO> list = projectMakeDTO.getAttachmentList();
////            for (int i = 0; i < fileList.size(); i++) {
////                Map<String, String> file = fileList.get(i);
////
////                ProjectMakeFileDTO tempFileInfo = new ProjectMakeFileDTO();
////                tempFileInfo.setOriginFileName(file.get("originFileName"));
////                tempFileInfo.setChangeFileName(file.get("thumbnailPath"));
////                tempFileInfo.setFilePath(file.get("savePath"));
////                tempFileInfo.setType(file.get("fileType"));
////
////                list.add(tempFileInfo);
////            }
////
////            log.info("[ThumbnailController] projectMakeDTO : " + projectMakeDTO);
////
////            projectMakeService.registBusinessLicense(projectMakeDTO);
////
////            rttr.addFlashAttribute("message", "사진 게시판 등록에 성공하셨습니다.");
//
//        } catch (IllegalStateException | IOException e) {
//            e.printStackTrace();
//
//            /* 어떤 종류의 Exception이 발생 하더라도실패 시 파일을 삭제해야 한다. */
//            int cnt = 0;
//            for (int i = 0; i < fileList.size(); i++) {
//                Map<String, String> file = fileList.get(i);
//
//                File deleteFile = new File(fileUploadDirectory + "/" + file.get("savedFileName"));
//                boolean isDeleted1 = deleteFile.delete();
//
//                File deleteThumbnail = new File(thumbnailDirectory + "/businessLicense_" + file.get("savedFileName"));
//                boolean isDeleted2 = deleteThumbnail.delete();
//
//                if (isDeleted1 && isDeleted2) {
//                    cnt++;
//                }
//            }
//
//            if (cnt == fileList.size()) {
//                log.info("[ThumbnailController] 업로드에 실패한 모든 사진 삭제 완료!");
//                e.printStackTrace();
//            } else {
//                e.printStackTrace();
//            }
//        }


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
    public String updatePass(MPPassDTO mpPassDTO) {

//        1. 기존 비밀번호가 맞는지 확인
        MPMyPageMember myPageMember = myPageRepository.findById(mpPassDTO.getMemCode()).get();

        if(!passwordEncoder.matches(mpPassDTO.getOriginMemPassword(), myPageMember.getMemPassword())){ // 둘이 비교
            throw new BadCredentialsException(mpPassDTO.getOriginMemPassword() + "는 비밀번호가 아닙니다.");
        }

        log.info("[ProductService] updateProduct Start ===================================");
        log.info("[ProductService] productDTO : " + mpPassDTO);

        String replaceFileName = null;
        int result = 0;

        System.out.println("product = " + myPageMember);

//        변경할 비밀번호가 확실한지 비교 확인
        System.out.println("productDTO = " + mpPassDTO.getNewMemPassword1());
        System.out.println("productDTO = " + mpPassDTO.getNewMemPassword2());

        if(!mpPassDTO.getNewMemPassword1().equals(mpPassDTO.getNewMemPassword2())){
            throw new PasswordNotEqualException(mpPassDTO.getOriginMemPassword() + " 변경될 비밀번호가 동일하지 않습니다.");
        }

        /* update를 위한 엔티티 값 수정 */

        myPageMember = myPageMember.memPassword(passwordEncoder.encode(mpPassDTO.getNewMemPassword1())).build();

        System.out.println("product = " + myPageMember);

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

    public Object selectSal(int memCode) {

        MPSalary product = mpSalaryRepository.findByMemCode(memCode);

        MPSalAttachedFile mpSalAttachedFile = mpSalAttachedFileRepository.findBySalCode(product.getSalCode());

        MPSalAttachedFileDTO settingMemberDTO = modelMapper.map(mpSalAttachedFile, MPSalAttachedFileDTO.class);

//        settingMemberDTO.setDocAtcPath(IMAGE_URL+"profile/"+settingMemberDTO.getDocAtcConvertName());

        return settingMemberDTO;

    }

    public Object attList(int memCode) {

        // 현재 날짜를 가져옵니다.
        LocalDate currentDate = LocalDate.now();

        // 2024-01-01 형식으로 만듭니다.
        LocalDate customDate = LocalDate.of(2024, 1, 1);

        // 3달 전의 날짜를 계산합니다.
        LocalDate threeMonthsAgo = currentDate.minusMonths(3).withDayOfMonth(1);

        // 날짜를 원하는 형식으로 포맷팅합니다.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedCustomDate = customDate.format(formatter);
        String formattedThreeMonthsAgo = threeMonthsAgo.format(formatter);

        // 결과 출력
        System.out.println("현재 날짜: " + currentDate);
        System.out.println("2024-01-01 형식으로 만든 날짜: " + formattedCustomDate);
        System.out.println("3달 전의 날짜: " + formattedThreeMonthsAgo);

        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonthValue();

        // 현재 날짜에서 1달을 빼서 그 전 달의 날짜를 가져옵니다.
        LocalDate previousMonthDate = currentDate.minusMonths(1);
        int previousMonthYear = previousMonthDate.getYear();
        int previousMonth = previousMonthDate.getMonthValue();

        System.out.println("현재 년도: " + currentYear);
        System.out.println("현재 월: " + currentMonth);
        System.out.println("이전 년도: " + previousMonthYear);
        System.out.println("이전 월: " + previousMonth);

        Date startDate = java.sql.Date.valueOf("2024-01-01");
        Date endDate = java.sql.Date.valueOf("2024-02-01");

        Date sqlDate = Date.valueOf(currentDate);
        Date sqlDate2 = Date.valueOf(formattedThreeMonthsAgo);

        List<MPAttendance> attendance = attendanceRepository.findByMemCodeAndAttWorkDateBetween(memCode,sqlDate2,sqlDate);
        System.out.println("attendance = " + attendance);

        List<MPAttendanceDTO> degreeDTO = attendance.stream()
                .map(exam -> modelMapper.map(exam, MPAttendanceDTO.class))
                .collect(Collectors.toList());
        System.out.println("degreeDTO = " + degreeDTO);

        for(MPAttendanceDTO settingMemberDTO: degreeDTO){
            if(settingMemberDTO.getAttStartTime() != null && settingMemberDTO.getAttEndTime() != null){

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
        }
        }



        return degreeDTO;
    }
}
