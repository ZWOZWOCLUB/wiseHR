package com.wisehr.wisehr.setting.service;

import com.wisehr.wisehr.common.Criteria;
import com.wisehr.wisehr.mypage.dto.MPHoldVacationDTO;
import com.wisehr.wisehr.mypage.entity.MPHoldVacation;
import com.wisehr.wisehr.mypage.repository.MPHoldVacationRepository;
import com.wisehr.wisehr.setting.dto.*;
import com.wisehr.wisehr.setting.entity.*;
import com.wisehr.wisehr.setting.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import com.wisehr.wisehr.util.FileUploadUtils;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SettingMemberService {
    private final SettingMemberRepository settingMemberRepository;
    private final SettingMemDepPosRepository settingMemDepPosRepository;
    private final SettingDepartmentRepository settingDepartmentRepository;
    private final SettingPositionRepository settingPositionRepository;
    private final SettingDegreeRepository settingDegreeRepository;
    private final SettingDegreeFileRepository settingDegreeFileRepository;
    private final SettingCareerRepository settingCareerRepository;
    private final SettingCareerFileRepository settingCareerFileRepository;
    private final SettingCertificateRepository settingCertificateRepository;
    private final SettingCertificateFileRepository settingCertificateFileRepository;
    private final SettingDocumentFileRepository settingDocumentFileRepository;
    private final SettingSalaryFileRepository settingSalaryFileRepository;
    private final SettingSalaryRepository settingSalaryRepository;
    private final MPHoldVacationRepository vacationRepository;
    private final SettingMemDepAttSchRepository settingMemDepAttSchRepository;



    private final ModelMapper modelMapper;

    @Value("src/main/resources/static/")
    private String IMAGE_DIR;

    @Value("http://localhost:8001/")
    private String IMAGE_URL;


    public SettingMemberService(SettingMemberRepository settingMemberRepository, SettingMemDepPosRepository settingMemDepPosRepository, SettingDepartmentRepository settingDepartmentRepository, SettingPositionRepository settingPositionRepository, SettingDegreeRepository settingDegreeRepository, SettingDegreeFileRepository settingDegreeFileRepository, SettingCareerRepository settingCareerRepository, SettingCareerFileRepository settingCareerFileRepository, SettingCertificateRepository settingCertificateRepository, SettingCertificateFileRepository settingCertificateFileRepository, SettingDocumentFileRepository settingDocumentFileRepository, SettingSalaryFileRepository settingSalaryFileRepository, SettingSalaryRepository settingSalaryRepository, MPHoldVacationRepository vacationRepository, SettingMemDepAttSchRepository settingMemDepAttSchRepository, ModelMapper modelMapper) {
        this.settingMemberRepository = settingMemberRepository;
        this.settingMemDepPosRepository = settingMemDepPosRepository;
        this.settingDepartmentRepository = settingDepartmentRepository;
        this.settingPositionRepository = settingPositionRepository;
        this.settingDegreeRepository = settingDegreeRepository;
        this.settingDegreeFileRepository = settingDegreeFileRepository;
        this.settingCareerRepository = settingCareerRepository;
        this.settingCareerFileRepository = settingCareerFileRepository;
        this.settingCertificateRepository = settingCertificateRepository;
        this.settingCertificateFileRepository = settingCertificateFileRepository;
        this.settingDocumentFileRepository = settingDocumentFileRepository;
        this.settingSalaryFileRepository = settingSalaryFileRepository;
        this.settingSalaryRepository = settingSalaryRepository;
        this.vacationRepository = vacationRepository;
        this.settingMemDepAttSchRepository = settingMemDepAttSchRepository;
        this.modelMapper = modelMapper;
    }


    @Transactional
    public SettingMemberDTO insertMember(SettingMemberDTO settingMemberDTO, MultipartFile profile) {
        log.info("insertMember Start~~~~~~~~~~~~");
        log.info(settingMemberDTO.toString());
        if(!"일반사원".equals(settingMemberDTO.getMemRole())) {
            settingMemberDTO.setMemRole("중간관리자");
        }

        SettingDocumentFileDTO fileDTO = new SettingDocumentFileDTO();

        String fileName = UUID.randomUUID().toString().replace("-", "");
        String replaceFileName = null;

        String path = IMAGE_DIR + "profile/" ;

        int result = 0;
        System.out.println(profile.getContentType());
        System.out.println(profile.getOriginalFilename());
        System.out.println(profile.getName());
        String[] extend = profile.getOriginalFilename().split("\\.");

        System.out.println("Arrays.toString(extend) = " + Arrays.toString(extend));
        String realExtend = extend[1];
        System.out.println(LocalDate.now().toString());
        try{
            if (profile != null) {

                SettingMember insertMember = modelMapper.map(settingMemberDTO, SettingMember.class);

                SettingMember insertResult = settingMemberRepository.save(insertMember);
                System.out.println("insertResult = " + insertResult);



                replaceFileName = FileUploadUtils.saveFile(path, fileName, profile);

                fileDTO.setMemCode(settingMemberDTO.getMemCode());
                fileDTO.setDocAtcExtends(realExtend);
                fileDTO.setDocAtcConvertName(replaceFileName);
                fileDTO.setDocAtcRegistDate(LocalDate.now().toString());
                fileDTO.setDocAtcStorage(path);
                fileDTO.setDocAtcDeleteStatus("N");
                fileDTO.setDocAtcPath(path);
                fileDTO.setDocAtcOriginName(profile.getOriginalFilename());
                fileDTO.setDocAtcKind("프로필");
                fileDTO.setMemCode(insertResult.getMemCode());

                SettingDocumentFile insertFile = modelMapper.map(fileDTO, SettingDocumentFile.class);
                settingDocumentFileRepository.save(insertFile);

                SettingMemberDTO memberDTO = modelMapper.map(insertResult, SettingMemberDTO.class);

                SettingDocumentFileDTO resultFileDTO = modelMapper.map(insertFile, SettingDocumentFileDTO.class);

                String url = "profile/" + resultFileDTO.getDocAtcConvertName();
                memberDTO.setProfileURL(url);
                System.out.println("memberDTO = " + memberDTO);
                return memberDTO;



            }else {
                SettingMember insertMember = modelMapper.map(settingMemberDTO, SettingMember.class);

                SettingMember insertResult = settingMemberRepository.save(insertMember);
                System.out.println("insertResult = " + insertResult);

                SettingMemberDTO memberDTO = modelMapper.map(insertResult, SettingMemberDTO.class);
                return memberDTO;

            }




        }catch(Exception e) {
            log.info("오류~~~~~~~");
            throw new RuntimeException(e);
        }


    }

    @Transactional
    public SettingMemberDTO updateMember(SettingMemberDTO settingMemberDTO, MultipartFile profile) {
        log.info("updateMember Start~~~~~~~~~~~~");
        log.info("settingMemberDTO : " + settingMemberDTO);
        String path = IMAGE_DIR + "profile/";

        String replaceFileName = null;
        String kind = "프로필";
        int result = 0;



        try {
            SettingMember member = settingMemberRepository.findById(settingMemberDTO.getMemCode()).get();
            System.out.println("member = " + member);
            SettingDocumentFile file = settingDocumentFileRepository.findByMemCodeAndDocAtcKind(settingMemberDTO.getMemCode(), kind);
            System.out.println("file = " + file);
            String oriImage = file.getDocAtcConvertName();


            member = member.memName(settingMemberDTO.getMemName())
                    .memPhone(settingMemberDTO.getMemPhone())
                    .memEmail(settingMemberDTO.getMemEmail())
                    .memAddress(settingMemberDTO.getMemAddress())
                    .memBirth(settingMemberDTO.getMemBirth())
                    .memHireDate(settingMemberDTO.getMemHireDate())
                    .memEndDate(settingMemberDTO.getMemEndDate())
                    .memStatus(settingMemberDTO.getMemStatus())
                    .memRole(settingMemberDTO.getMemRole())
                    .depCode(settingMemberDTO.getDepCode())
                    .posCode(settingMemberDTO.getPosCode()).build();

            SettingMemberDTO memberDTO = modelMapper.map(member, SettingMemberDTO.class);



            if (profile != null) {


                String fileName = UUID.randomUUID().toString().replace("-", "");

                String[] extend = profile.getOriginalFilename().split("\\.");

                String realExtend = extend[1];

                replaceFileName = FileUploadUtils.saveFile(path, fileName, profile);

                file = file.memCode(member.getMemCode())
                        .docAtcCode(file.getDocAtcCode())
                        .docAtcExtends(realExtend)
                        .docAtcConvertName(replaceFileName)
                        .docAtcRegistDate(LocalDate.now().toString())
                        .docAtcStorage(path)
                        .docAtcOriginName(profile.getOriginalFilename())
                        .docAtcKind("프로필")
                        .docAtcPath(path).build();

                boolean isDelete = FileUploadUtils.deleteFile(path, oriImage);


                SettingDocumentFileDTO resultFileDTO = modelMapper.map(file, SettingDocumentFileDTO.class);

                String url = "profile/" + resultFileDTO.getDocAtcConvertName();
                memberDTO.setProfileURL(url);
                System.out.println("memberDTO = " + memberDTO);
                return memberDTO;


            } else {
                file = file.docAtcOriginName(oriImage).build();


                SettingDocumentFileDTO resultFileDTO = modelMapper.map(file, SettingDocumentFileDTO.class);

                String url = "profile/" + resultFileDTO.getDocAtcConvertName();
                memberDTO.setProfileURL(url);
                System.out.println("memberDTO = " + memberDTO);
                return memberDTO;

            }

        } catch (Exception e) {
            FileUploadUtils.deleteFile(path, replaceFileName);
            throw new RuntimeException(e);
        }
    }



    public Page<SettingMemDepPosDTO> allMemberSearchWithPaging(Criteria cri) {

        log.info("allMemberSearchWithPaging 서비스 시작~~~~~~~~~~~~");
        int index = cri.getPageNum() -1;

        int count = cri.getAmount();

        Pageable paging = PageRequest.of(index, count, Sort.by("memCode"));

        Page<SettingMemDepPos> result = settingMemDepPosRepository.findAll(paging);

        Page<SettingMemDepPosDTO> memberList = result.map(member -> modelMapper.map(member, SettingMemDepPosDTO.class));
        System.out.println("result = " + result);
        log.info("allMemberSearchWithPaging 서비스 끗~~~~~~~~~~~~");

        return memberList;
    }

    public List<SettingMemDepPosDTO> searchMemberList(String search) {
        log.info("searchMemberList 서비스 시작~~~~~~~~~~~~");
        log.info("searchMemberList search : {}", search);

        List<SettingMemDepPos> memberListWithSearchValue = settingMemDepPosRepository.findByMemNameContaining(search);

        List<SettingMemDepPosDTO> memberDTOList = memberListWithSearchValue.stream()
                .map(member -> modelMapper.map(member, SettingMemDepPosDTO.class))
                .collect(Collectors.toList());
        log.info("searchMemberList 서비스 끗~~~~~~~~~~~~", memberListWithSearchValue);
        System.out.println("memberListWithSearchValue = " + memberListWithSearchValue);

        return memberDTOList;
    }

    public List<SettingDepartmentDTO> searchDepName() {
        log.info("searchDepName 서비스 시작~~~~~~~~~~~~");

        List<SettingDepartment> depList = settingDepartmentRepository.findAll();

        List<SettingDepartmentDTO> depDTOList = depList.stream()
                .map(dep -> modelMapper.map(dep, SettingDepartmentDTO.class))
                .collect(Collectors.toList());

        log.info("searchDepName 서비스 끗~~~~~~~~~~~~");
        System.out.println("depList = " + depList);
        return depDTOList;
    }

    public List<SettingPositionDTO> searchPosition() {
        log.info("searchDepName 서비스 시작~~~~~~~~~~~~");

        List<SettingPosition> positionList = settingPositionRepository.findAll();

        List<SettingPositionDTO> positionDTOList = positionList.stream()
                .map(position -> modelMapper.map(position, SettingPositionDTO.class))
                .collect(Collectors.toList());
        log.info("searchDepName 서비스 끗~~~~~~~~~~~~");
        System.out.println("positionList = " + positionList);
        return positionDTOList;
    }

    public SettingResourcesDTO searchResourcesInformation(int memCode) {
        log.info("searchResourcesInformation 서비스 시작~~~~~~~~~~");

        List<SettingCertificate> certificateList = settingCertificateRepository.findByMemCode(memCode);
        List<SettingCareer> careerList = settingCareerRepository.findByMemCode(memCode);
        List<SettingDegree> degreeList = settingDegreeRepository.findByMemCode(memCode);
        SettingSalary salary = settingSalaryRepository.findByMemCode(memCode);
        System.out.println("degreeList = " + degreeList);
        System.out.println("careerList = " + careerList);
        System.out.println("certificateList = " + certificateList);
        System.out.println("salary = " + salary);

        List<SettingCertificateDTO> certificateDTOList = certificateList.stream()
                .map(certificate -> modelMapper.map(certificate, SettingCertificateDTO.class))
                .collect(Collectors.toList());
        List<SettingCareerDTO> careerDTOList = careerList.stream()
                .map(career -> modelMapper.map(career, SettingCareerDTO.class))
                .collect(Collectors.toList());
        List<SettingDegreeDTO> degreeDTOList = degreeList.stream()
                .map(degree -> modelMapper.map(degree, SettingDegreeDTO.class))
                .collect(Collectors.toList());
        SettingSalary settingSalary = modelMapper.map(salary, SettingSalary.class);

        SettingResourcesDTO resourcesDTO = new SettingResourcesDTO();
        resourcesDTO.setMemCode(memCode);
        resourcesDTO.setCareerDTO(careerDTOList);
        resourcesDTO.setDegreeDTO(degreeDTOList);
        resourcesDTO.setCertificateDTO(certificateDTOList);
        resourcesDTO.setSalary(settingSalary);

        log.info("searchResourcesInformation 서비스 끗~~~~~~~~~~");

        return resourcesDTO;
    }


    @Transactional
    public String insertDegree(List<SettingDegreeDTO> degreeDTO) {
        log.info("insertDegree Start~~~~~~~~~~~~");
        log.info(degreeDTO.toString());

        int result = 0;

        try {
            List<SettingDegree> insertDegrees = degreeDTO.stream()
                    .map(degree -> modelMapper.map(degree, SettingDegree.class))
                    .collect(Collectors.toList());


            List<SettingDegree> insertResults = settingDegreeRepository.saveAll(insertDegrees);
            System.out.println("insertResults = " + insertResults);

            result = 1;

        } catch (Exception e) {
            log.error("오류 발생", e);
            throw new RuntimeException(e);
        }

        return (result > 0)? "등록 성공": "등록 실패";
    }


    @Transactional
    public String insertCertificate(List<SettingCertificateDTO> certificateDTO) {
        log.info("insertCertificate Start~~~~~~~~~~~~");
        log.info(certificateDTO.toString());

        int result = 0;

        try{


            List<SettingCertificate> insertCertificate = certificateDTO.stream()
                    .map(certificate -> modelMapper.map(certificate, SettingCertificate.class))
                    .collect(Collectors.toList());

            List<SettingCertificate> insertResult = settingCertificateRepository.saveAll(insertCertificate);
            System.out.println("insertResult = " + insertResult);

            result = 1;

        }catch(Exception e) {
            log.info("오류~~~~~~~");
            throw new RuntimeException(e);
        }

        return (result > 0)? "등록 성공": "등록 실패";
    }

    @Transactional
    public String insertCareer(List<SettingCareerDTO> careerDTO) {
        log.info("insertCareer Start~~~~~~~~~~~~");
        log.info(careerDTO.toString());

        int result = 0;

        try{
            List<SettingCareer> insertCareer = careerDTO.stream()
                    .map(career -> modelMapper.map(career, SettingCareer.class))
                    .collect(Collectors.toList());

            List<SettingCareer> insertResult = settingCareerRepository.saveAll(insertCareer);
            System.out.println("insertResult = " + insertResult);

            result = 1;

        }catch(Exception e) {
            log.info("오류~~~~~~~");
            throw new RuntimeException(e);
        }

        return (result > 0)? "등록 성공": "등록 실패";
    }


    @Transactional
    public String insertDegreeFile(SettingDegreeDTO degreeDTO, MultipartFile degreeFile) {
        log.info("insertDegreeFile Start~~~~~~~~~~~~");
        log.info(degreeDTO.toString());

        SettingDegreeFileDTO fileDTO = new SettingDegreeFileDTO();

        String fileName = UUID.randomUUID().toString().replace("-", "");
        String replaceFileName = null;

        String path = IMAGE_DIR + "degree/" ;

        int result = 0;
        System.out.println(degreeFile.getContentType());
        System.out.println(degreeFile.getOriginalFilename());
        System.out.println(degreeFile.getName());
        String[] extend = degreeFile.getOriginalFilename().split("\\.");

        System.out.println("Arrays.toString(extend) = " + Arrays.toString(extend));
        String realExtend = extend[1];
        System.out.println(LocalDate.now().toString());
        try{

            replaceFileName = FileUploadUtils.saveFile(path, fileName, degreeFile);

            fileDTO.setDegCode(degreeDTO.getDegCode());
            fileDTO.setDegAtcName(degreeFile.getOriginalFilename());
            fileDTO.setDegAtcExtends(realExtend);
            fileDTO.setDegAtcConvertName(replaceFileName);
            fileDTO.setDegAtcRegistDate(LocalDate.now().toString());
            fileDTO.setDegAtcDeleteStatus("N");
            fileDTO.setDegAtcPath(path);

            SettingDegreeFile insertFile = modelMapper.map(fileDTO, SettingDegreeFile.class);
            settingDegreeFileRepository.save(insertFile);


            result = 1;


        }catch(Exception e) {
            log.info("오류~~~~~~~");
            throw new RuntimeException(e);
        }

        return (result > 0)? "등록 성공": "등록 실패";
    }

    @Transactional
    public String insertCertificateFile(SettingCertificateDTO certificateDTO, MultipartFile certificateFile) {
        log.info("insertCertificateFile Start~~~~~~~~~~~~");
        log.info(certificateDTO.toString());

        SettingCertificateFileDTO fileDTO = new SettingCertificateFileDTO();

        String fileName = UUID.randomUUID().toString().replace("-", "");
        String replaceFileName = null;

        String path = IMAGE_DIR + "certificate/" ;

        int result = 0;
        System.out.println(certificateFile.getContentType());
        System.out.println(certificateFile.getOriginalFilename());
        System.out.println(certificateFile.getName());
        String[] extend = certificateFile.getOriginalFilename().split("\\.");

        System.out.println("Arrays.toString(extend) = " + Arrays.toString(extend));
        String realExtend = extend[1];
        System.out.println(LocalDate.now().toString());
        try{

            replaceFileName = FileUploadUtils.saveFile(path, fileName, certificateFile);

            fileDTO.setCerCode(certificateDTO.getCerCode());
            fileDTO.setCerAtcName(certificateFile.getOriginalFilename());
            fileDTO.setCerAtcExtends(realExtend);
            fileDTO.setCerAtcConvertName(replaceFileName);
            fileDTO.setCerAtcRegistDate(LocalDate.now().toString());
            fileDTO.setCerAtcDeleteStatus("N");
            fileDTO.setCerAtcPath(path);

            SettingCertificateFile insertFile = modelMapper.map(fileDTO, SettingCertificateFile.class);
            settingCertificateFileRepository.save(insertFile);


            result = 1;


        }catch(Exception e) {
            log.info("오류~~~~~~~");
            throw new RuntimeException(e);
        }

        return (result > 0)? "등록 성공": "등록 실패";
    }

    @Transactional
    public String insertCareerFile(SettingCareerDTO careerDTO, MultipartFile careerFile) {
        log.info("insertCareerFile Start~~~~~~~~~~~~");
        log.info(careerDTO.toString());

        SettingCareerFileDTO fileDTO = new SettingCareerFileDTO();

        String fileName = UUID.randomUUID().toString().replace("-", "");
        String replaceFileName = null;

        String path = IMAGE_DIR + "career/" ;

        int result = 0;
        System.out.println(careerFile.getContentType());
        System.out.println(careerFile.getOriginalFilename());
        System.out.println(careerFile.getName());
        String[] extend = careerFile.getOriginalFilename().split("\\.");

        System.out.println("Arrays.toString(extend) = " + Arrays.toString(extend));
        String realExtend = extend[1];
        System.out.println(LocalDate.now().toString());
        try{

            replaceFileName = FileUploadUtils.saveFile(path, fileName, careerFile);

            fileDTO.setCrrCode(careerDTO.getCrrCode());
            fileDTO.setCrrAtcName(careerFile.getOriginalFilename());
            fileDTO.setCrrAtcExtends(realExtend);
            fileDTO.setCrrAtcConvertName(replaceFileName);
            fileDTO.setCrrAtcRegistDate(LocalDate.now().toString());
            fileDTO.setCrrAtcDeleteStatus("N");
            fileDTO.setCrrAtcPath(path);

            SettingCareerFile insertFile = modelMapper.map(fileDTO, SettingCareerFile.class);
            settingCareerFileRepository.save(insertFile);


            result = 1;


        }catch(Exception e) {
            log.info("오류~~~~~~~");
            throw new RuntimeException(e);
        }

        return (result > 0)? "등록 성공": "등록 실패";
    }

    @Transactional
    public String insertSalaryFile(SettingSalaryDTO salaryDTO, MultipartFile salaryFile) {
        log.info("insertSalaryFile Start~~~~~~~~~~~~");
        log.info(salaryDTO.toString());

        SettingSalaryFileDTO fileDTO = new SettingSalaryFileDTO();

        String fileName = UUID.randomUUID().toString().replace("-", "");
        String replaceFileName = null;

        String path = IMAGE_DIR + "salary/" ;

        int result = 0;
        System.out.println(salaryFile.getContentType());
        System.out.println(salaryFile.getOriginalFilename());
        System.out.println(salaryFile.getName());
        String[] extend = salaryFile.getOriginalFilename().split("\\.");

        System.out.println("Arrays.toString(extend) = " + Arrays.toString(extend));
        String realExtend = extend[1];
        System.out.println(LocalDate.now().toString());
        try{

            replaceFileName = FileUploadUtils.saveFile(path, fileName, salaryFile);

            fileDTO.setSalCode(salaryDTO.getSalCode());
            fileDTO.setSalAtcName(salaryFile.getOriginalFilename());
            fileDTO.setSalAtcConvertName(replaceFileName);
            fileDTO.setSalAtcRegistDate(LocalDate.now().toString());
            fileDTO.setSalAtcDeleteStatus("N");
            fileDTO.setSalAtcPath(path);

            SettingSalaryFile insertFile = modelMapper.map(fileDTO, SettingSalaryFile.class);
            settingSalaryFileRepository.save(insertFile);


            result = 1;


        }catch(Exception e) {
            log.info("오류~~~~~~~");
            throw new RuntimeException(e);
        }

        return (result > 0)? "등록 성공": "등록 실패";
    }

    @Transactional
    public String insertDocumentFile(SettingDocumentFileDTO etcfileDTO, MultipartFile etcFile) {
        log.info("insertDocumentFile Start~~~~~~~~~~~~");
        log.info(etcfileDTO.toString());


        String fileName = UUID.randomUUID().toString().replace("-", "");
        String replaceFileName = null;

        String path = IMAGE_DIR + "etcDocumentFile/" ;

        int result = 0;
        System.out.println(etcFile.getContentType());
        System.out.println(etcFile.getOriginalFilename());
        System.out.println(etcFile.getName());
        String[] extend = etcFile.getOriginalFilename().split("\\.");

        System.out.println("Arrays.toString(extend) = " + Arrays.toString(extend));
        String realExtend = extend[1];
        System.out.println(LocalDate.now().toString());
        try{

            replaceFileName = FileUploadUtils.saveFile(path, fileName, etcFile);

            etcfileDTO.setDocAtcExtends(realExtend);
            etcfileDTO.setDocAtcConvertName(replaceFileName);
            etcfileDTO.setDocAtcRegistDate(LocalDate.now().toString());
            etcfileDTO.setDocAtcStorage(IMAGE_DIR);
            etcfileDTO.setDocAtcDeleteStatus("N");
            etcfileDTO.setDocAtcPath(IMAGE_DIR);
            etcfileDTO.setDocAtcOriginName(etcFile.getOriginalFilename());

            SettingDocumentFile insertFile = modelMapper.map(etcfileDTO, SettingDocumentFile.class);
            settingDocumentFileRepository.save(insertFile);


            result = 1;


        }catch(Exception e) {
            log.info("오류~~~~~~~");
            throw new RuntimeException(e);
        }

        return (result > 0)? "등록 성공": "등록 실패";
    }

    @Transactional
    public String updateDegree(SettingDegreeDTO degreeDTO) {
        log.info("updateDegree Start~~~~~~~~~~~~");
        log.info(degreeDTO.toString());

        int result = 0;

        try{
            SettingDegree degree = settingDegreeRepository.findById(degreeDTO.getDegCode()).get();

            degree = degree.degKind(degreeDTO.getDegKind())
                    .degMajor(degreeDTO.getDegMajor())
                    .degName(degreeDTO.getDegName())
                    .degGraduation(degreeDTO.getDegGraduation())
                    .degState(degreeDTO.getDegState())
                    .degAdmissions(degreeDTO.getDegAdmissions()).build();

            result = 1;

        }catch(Exception e) {
            log.info("오류~~~~~~~");
            throw new RuntimeException(e);
        }

        return (result > 0)? "수정 성공": "수정 실패";
    }

    @Transactional
    public String updateCertificate(SettingCertificateDTO certificateDTO) {
        log.info("updateCertificate Start~~~~~~~~~~~~");
        log.info(certificateDTO.toString());

        int result = 0;

        try{
            SettingCertificate certificate = settingCertificateRepository.findById(certificateDTO.getCerCode()).get();

            certificate = certificate.cerName(certificateDTO.getCerName())
                    .cerKind(certificateDTO.getCerKind())
                    .cerDay(certificateDTO.getCerDay())
                    .cerEndDate(certificateDTO.getCerEndDate())
                    .cerDescription(certificateDTO.getCerDescription())
                    .cerInstitution(certificateDTO.getCerInstitution())
                    .build();

            result = 1;

        }catch(Exception e) {
            log.info("오류~~~~~~~");
            throw new RuntimeException(e);
        }

        return (result > 0)? "수정 성공": "수정 실패";
    }

    @Transactional
    public String updateCareer(SettingCareerDTO careerDTO) {
        log.info("updateCareer Start~~~~~~~~~~~~");
        log.info(careerDTO.toString());

        int result = 0;

        try{
            SettingCareer career = settingCareerRepository.findById(careerDTO.getCrrCode()).get();

            career = career.crrName(careerDTO.getCrrName())
                    .crrPosition(careerDTO.getCrrPosition())
                    .crrStartDate(careerDTO.getCrrStartDate())
                    .crrEndDate(careerDTO.getCrrEndDate())
                    .crrState(careerDTO.getCrrState())
                    .crrDescription(careerDTO.getCrrDescription())
                    .build();

            result = 1;

        }catch(Exception e) {
            log.info("오류~~~~~~~");
            throw new RuntimeException(e);
        }

        return (result > 0)? "수정 성공": "수정 실패";
    }

    @Transactional
    public String insertSalary(SettingSalaryDTO salaryDTO) {
        log.info("insertSalary Start~~~~~~~~~~~~");
        log.info(salaryDTO.toString());

        int result = 0;

        try{
            SettingSalary insertSalary = modelMapper.map(salaryDTO, SettingSalary.class);

            SettingSalary insertResult = settingSalaryRepository.save(insertSalary);
            System.out.println("insertResult = " + insertResult);

            result = 1;

        }catch(Exception e) {
            log.info("오류~~~~~~~");
            throw new RuntimeException(e);
        }

        return (result > 0)? "등록 성공": "등록 실패";
    }

    @Transactional
    public String updateSalary(SettingSalaryDTO salaryDTO) {
        log.info("updateSalary Start~~~~~~~~~~~~");
        log.info(salaryDTO.toString());

        int result = 0;

        try{
            SettingSalary salary = settingSalaryRepository.findById(salaryDTO.getSalCode()).get();

            salary = salary.salNumber(salaryDTO.getSalNumber())
                    .salBankName(salaryDTO.getSalBankName()).build();

            result = 1;

        }catch(Exception e) {
            log.info("오류~~~~~~~");
            throw new RuntimeException(e);
        }

        return (result > 0)? "수정 성공": "수정 실패";
    }


    @Transactional
    public String updateDocumentFile(SettingDocumentFileDTO etcfileDTO, MultipartFile etcFile) {
        log.info("updateDocumentFile Start~~~~~~~~~~~~");
        log.info("etcfileDTO : " + etcfileDTO);

        String path = IMAGE_DIR + "etcDocumentFile/";

        String replaceFileName = null;


        int result = 0;



        try {

                SettingDocumentFile file = settingDocumentFileRepository.findByDocAtcCode(etcfileDTO.getDocAtcCode());

            String[] extend = etcFile.getOriginalFilename().split("\\.");
            String realExtend = extend[1];
            String fileName = UUID.randomUUID().toString().replace("-", "");

            replaceFileName = FileUploadUtils.saveFile(path, fileName, etcFile);

            file = file.docAtcExtends(realExtend)
                    .docAtcConvertName(replaceFileName)
                    .docAtcRegistDate(LocalDate.now().toString())
                    .docAtcOriginName(etcFile.getOriginalFilename())
                    .docAtcPath(path)
                    .docAtcPath(etcfileDTO.getDocAtcKind())
                    .docAtcKind(etcfileDTO.getDocAtcKind()).build();


                boolean isDelete = FileUploadUtils.deleteFile(path, file.getDocAtcConvertName());


            result = 1;

        } catch (Exception e) {
            FileUploadUtils.deleteFile(path, replaceFileName);
            throw new RuntimeException(e);
        }
        return (result > 0) ? "그외 파일 업데이트 성공" : "그외 파일 업데이트 실패";
    }


    @Transactional
    public String updateVacation(MPHoldVacationDTO holdVacationDTO) {
        log.info("updateVacation Start~~~~~~~~~~~~");
        log.info("holdVacationDTO : " + holdVacationDTO);

        int result = 0;

        try{
            MPHoldVacation vacation = vacationRepository.findByMemCode(holdVacationDTO.getMemCode());
            System.out.println("vacation = " + vacation);
            int vct = vacation.getVctCount();
            log.info("vct"+ vct);
            int vctResult = vct + holdVacationDTO.getVctCount();
            log.info("vctResutle "+ vctResult);
            holdVacationDTO.setVctCount(vctResult);
            log.info(holdVacationDTO.toString());

            vacation = vacation.vctCount(holdVacationDTO.getVctCount()).build();

            result = 1;

        }catch(Exception e) {
            log.info("오류~~~~~~~");
            throw new RuntimeException(e);
        }

        return (result > 0)? "수정 성공": "수정 실패";
    }

    @Transactional
    public String updateSalaryFile(SettingSalaryFileDTO salaryFileDTO, MultipartFile salaryFile) {
        log.info("updateSalaryFile Start~~~~~~~~~~~~");
        log.info("salaryFileDTO : " + salaryFileDTO);

        String path = IMAGE_DIR + "salary/";

        String replaceFileName = null;


        int result = 0;

        try {

            SettingSalaryFile file = settingSalaryFileRepository.findById(salaryFileDTO.getSalAtcCode()).get();

            String fileName = UUID.randomUUID().toString().replace("-", "");

            replaceFileName = FileUploadUtils.saveFile(path, fileName, salaryFile);

            file = file.salAtcName(salaryFile.getOriginalFilename())
                    .salAtcRegistDate(LocalDate.now().toString())
                    .salAtcPath(path)
                    .salAtcConvertName(replaceFileName)
                    .build();


            boolean isDelete = FileUploadUtils.deleteFile(path, file.getSalAtcConvertName());


            result = 1;

        } catch (Exception e) {
            FileUploadUtils.deleteFile(path, replaceFileName);
            throw new RuntimeException(e);
        }
        return (result > 0) ? "통장 파일 업데이트 성공" : "통장 파일 업데이트 실패";
    }

    @Transactional
    public String updateCertificateFile(SettingCertificateFileDTO certificateFileDTO, MultipartFile certificateFile) {
        log.info("updateCertificateFile Start~~~~~~~~~~~~");
        log.info("certificateFileDTO : " + certificateFileDTO);

        String path = IMAGE_DIR + "certificate/";

        String replaceFileName = null;

        int result = 0;

        try {

            SettingCertificateFile file = settingCertificateFileRepository.findById(certificateFileDTO.getCerAtcCode()).get();

            String[] extend = certificateFile.getOriginalFilename().split("\\.");
            String realExtend = extend[1];
            String fileName = UUID.randomUUID().toString().replace("-", "");

            replaceFileName = FileUploadUtils.saveFile(path, fileName, certificateFile);

            file = file.cerAtcExtends(realExtend)
                    .cerAtcConvertName(replaceFileName)
                    .cerAtcRegistDate(LocalDate.now().toString())
                    .cerAtcName(certificateFile.getOriginalFilename())
                    .cerAtcPath(path).build();


            boolean isDelete = FileUploadUtils.deleteFile(path, file.getCerAtcConvertName());


            result = 1;

        } catch (Exception e) {
            FileUploadUtils.deleteFile(path, replaceFileName);
            throw new RuntimeException(e);
        }
        return (result > 0) ? "자격 파일 업데이트 성공" : "자격 파일 업데이트 실패";
    }

    @Transactional
    public String updateDegreeFile(SettingDegreeFileDTO degreeFileDTO, MultipartFile degreeFile) {
        log.info("updateDocumentFile Start~~~~~~~~~~~~");
        log.info("degreeFileDTO : " + degreeFileDTO);

        String path = IMAGE_DIR + "degree/";

        String replaceFileName = null;

        int result = 0;

        try {

            SettingDegreeFile file = settingDegreeFileRepository.findById(degreeFileDTO.getDegAtcCode()).get();

            String[] extend = degreeFile.getOriginalFilename().split("\\.");
            String realExtend = extend[1];
            String fileName = UUID.randomUUID().toString().replace("-", "");

            replaceFileName = FileUploadUtils.saveFile(path, fileName, degreeFile);

            file = file.degAtcExtends(realExtend)
                    .degAtcConvertName(replaceFileName)
                    .degAtcRegistDate(LocalDate.now().toString())
                    .degAtcName(degreeFile.getOriginalFilename())
                    .degAtcPath(path).build();


            boolean isDelete = FileUploadUtils.deleteFile(path, file.getDegAtcConvertName());


            result = 1;

        } catch (Exception e) {
            FileUploadUtils.deleteFile(path, replaceFileName);
            throw new RuntimeException(e);
        }
        return (result > 0) ? "학위 파일 업데이트 성공" : "학위 파일 업데이트 실패";
    }

    @Transactional
    public String updateCareerFile(SettingCareerFileDTO careerFileDTO, MultipartFile careerFile) {
        log.info("updateDocumentFile Start~~~~~~~~~~~~");
        log.info("careerFileDTO : " + careerFileDTO);

        String path = IMAGE_DIR + "career/";

        String replaceFileName = null;


        int result = 0;



        try {

            SettingCareerFile file = settingCareerFileRepository.findById(careerFileDTO.getCrrAtcCode()).get();

            String[] extend = careerFile.getOriginalFilename().split("\\.");
            String realExtend = extend[1];
            String fileName = UUID.randomUUID().toString().replace("-", "");

            replaceFileName = FileUploadUtils.saveFile(path, fileName, careerFile);

            file = file.crrAtcExtends(realExtend)
                    .crrAtcConvertName(replaceFileName)
                    .crrAtcRegistDate(LocalDate.now().toString())
                    .crrAtcName(careerFile.getOriginalFilename())
                    .crrAtcPath(path).build();


            boolean isDelete = FileUploadUtils.deleteFile(path, file.getCrrAtcConvertName());


            result = 1;

        } catch (Exception e) {
            FileUploadUtils.deleteFile(path, replaceFileName);
            throw new RuntimeException(e);
        }
        return (result > 0) ? "경력 파일 업데이트 성공" : "경력 파일 업데이트 실패";
    }

    public List<SettingMemDepAttSchDTO> AllMemberAttendance(SettingSearchValueDTO valueDTO) {
        log.info("AllMemberAttendance 서비스 시작~~~~~~~~~~~~");
        System.out.println("valueDTO = " + valueDTO);

        List<Object[]> list = settingMemDepAttSchRepository.findByYearMonth(valueDTO.getYearMonth());
        List<SettingMemDepAttSchDTO> resultDTOList = list.stream()
                .map(this::mappingDTO)
                .collect(Collectors.toList());
        log.info("resultDTO : " + resultDTOList);

        log.info("AllMemberAttendance 서비스 끗~~~~~~~~~~~~");
        return resultDTOList;
    }

    private SettingMemDepAttSchDTO mappingDTO(Object[] array) {
        SettingMemDepAttSchDTO resultDTO = new SettingMemDepAttSchDTO();

        resultDTO.setMemCode((Integer) array[0]);
        resultDTO.setMemName((String) array[1]);
        resultDTO.setDepCode(array[2] != null ? (Integer) array[2] : 0);
        resultDTO.getAttendances().setAttCode((Integer) array[3]);
        resultDTO.getAttendances().setAttStartTime((String) array[4]);
        resultDTO.getAttendances().setAttEndTime((String) array[5]);
        resultDTO.getAttendances().setAttStatus((String) array[6]);
        resultDTO.getAttendances().setAttWorkDate((String) array[7]);
        resultDTO.getAttendances().setSchCode((String) array[8]);
        resultDTO.getScheduleDTO().setSchType((String) array[9]);
        resultDTO.getScheduleDTO().setSchColor((String) array[10]);
        resultDTO.getDepartment().setDepName((String) array[11]);


        return resultDTO;
    }

}
