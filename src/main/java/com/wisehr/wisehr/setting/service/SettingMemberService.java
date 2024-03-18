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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import com.wisehr.wisehr.util.FileUploadUtils;


import java.time.LocalDate;
import java.util.ArrayList;
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

    private final BCryptPasswordEncoder passwordEncoder;

    private final SettingResourcesRepository resourcesRepository;


    private final ModelMapper modelMapper;

    @Value("${image.image-dir}")
    private String IMAGE_DIR;

    @Value("${image.image-url}")
    private String IMAGE_URL;


    public SettingMemberService(SettingMemberRepository settingMemberRepository, SettingMemDepPosRepository settingMemDepPosRepository, SettingDepartmentRepository settingDepartmentRepository, SettingPositionRepository settingPositionRepository, SettingDegreeRepository settingDegreeRepository, SettingDegreeFileRepository settingDegreeFileRepository, SettingCareerRepository settingCareerRepository, SettingCareerFileRepository settingCareerFileRepository, SettingCertificateRepository settingCertificateRepository, SettingCertificateFileRepository settingCertificateFileRepository, SettingDocumentFileRepository settingDocumentFileRepository, SettingSalaryFileRepository settingSalaryFileRepository, SettingSalaryRepository settingSalaryRepository, MPHoldVacationRepository vacationRepository, SettingMemDepAttSchRepository settingMemDepAttSchRepository, BCryptPasswordEncoder passwordEncoder, SettingResourcesRepository resourcesRepository, ModelMapper modelMapper) {
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
        this.passwordEncoder = passwordEncoder;
        this.resourcesRepository = resourcesRepository;
        this.modelMapper = modelMapper;
    }


    @Transactional
    public SettingMemberDTO insertMember(SettingMemberDTO settingMemberDTO, MultipartFile profile) {
        if("Y".equals(settingMemberDTO.getMemRole())) {
            settingMemberDTO.setMemRole("ADMIN");
        }else {
            settingMemberDTO.setMemRole("USER");
        }

        try{
            if (profile != null) {
                SettingDocumentFileDTO fileDTO = new SettingDocumentFileDTO();

                String fileName = UUID.randomUUID().toString().replace("-", "");
                String replaceFileName = null;

                String path = IMAGE_DIR + "/profile/" ;

                int result = 0;
                String[] extend = profile.getOriginalFilename().split("\\.");

                String realExtend = extend[1];

                SettingMember insertMember = modelMapper.map(settingMemberDTO, SettingMember.class);

                SettingMember insertResult = settingMemberRepository.save(insertMember);

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
                return memberDTO;

            }else {
                SettingMember insertMember = modelMapper.map(settingMemberDTO, SettingMember.class);

                SettingMember insertResult = settingMemberRepository.save(insertMember);
                SettingMemberDTO memberDTO = modelMapper.map(insertResult, SettingMemberDTO.class);
                return memberDTO;
            }

        }catch(Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Transactional
    public SettingMemberDTO updateMember(SettingMemberDTO settingMemberDTO, MultipartFile profile) {
        String path = IMAGE_DIR + "/profile/";

        String replaceFileName = null;
        String kind = "프로필";

        try {

            SettingMember member = settingMemberRepository.findById(settingMemberDTO.getMemCode()).get();
            if (settingMemberDTO.getMemStatus() == null || !settingMemberDTO.getMemStatus().equals("Y")) {
                if ("0000".equals(settingMemberDTO.getMemPassword())) {
                    member = member.memPassword(passwordEncoder.encode(settingMemberDTO.getMemPassword())).build();
                    SettingMemberDTO memberDTO = modelMapper.map(member, SettingMemberDTO.class);

                    return memberDTO;
                } else {
                    if("Y".equals(settingMemberDTO.getMemRole())) {
                        settingMemberDTO.setMemRole("ADMIN");
                    }else {
                        settingMemberDTO.setMemRole("USER");
                    }

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
                    SettingDocumentFile file = settingDocumentFileRepository.findByMemCodeAndDocAtcKind(settingMemberDTO.getMemCode(), kind);
                    if (file != null) {

                        if (profile != null) {
                            FileUploadUtils.deleteFile(file.getDocAtcStorage(), file.getDocAtcConvertName());

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

                            SettingDocumentFileDTO resultFileDTO = modelMapper.map(file, SettingDocumentFileDTO.class);

                            String url = "profile/" + resultFileDTO.getDocAtcConvertName();
                            memberDTO.setProfileURL(url);
                            return memberDTO;
                        } else {

                            SettingDocumentFileDTO resultFileDTO = modelMapper.map(file, SettingDocumentFileDTO.class);

                            String url = "profile/" + resultFileDTO.getDocAtcConvertName();
                            memberDTO.setProfileURL(url);
                            return memberDTO;
                        }
                    } else {
                        SettingDocumentFileDTO fileDTO = new SettingDocumentFileDTO();

                        String fileName = UUID.randomUUID().toString().replace("-", "");
                        String[] extend = profile.getOriginalFilename().split("\\.");

                        String realExtend = extend[1];

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
                        fileDTO.setMemCode(settingMemberDTO.getMemCode());

                        SettingDocumentFile insertFile = modelMapper.map(fileDTO, SettingDocumentFile.class);
                        settingDocumentFileRepository.save(insertFile);

                        SettingDocumentFileDTO resultFileDTO = modelMapper.map(insertFile, SettingDocumentFileDTO.class);

                        String url = "profile/" + resultFileDTO.getDocAtcConvertName();
                        memberDTO.setProfileURL(url);
                        return memberDTO;
                    }
                }
            }else {
                member = member.memStatus(settingMemberDTO.getMemStatus()).build();
                SettingMemberDTO memberDTO = modelMapper.map(member, SettingMemberDTO.class);
                return memberDTO;

            }
        } catch (Exception e) {
            FileUploadUtils.deleteFile(path, replaceFileName);
            throw new RuntimeException(e);
        }
    }



    public Page<SettingMemDepPosDTO> allMemberSearchWithPaging(Criteria cri) {
        int index = cri.getPageNum() -1;

        int count = cri.getAmount();

        Pageable paging = PageRequest.of(index, count, Sort.by("memCode"));

        Page<SettingMemDepPos> result = settingMemDepPosRepository.findAll(paging);

        Page<SettingMemDepPosDTO> memberList = result.map(member -> modelMapper.map(member, SettingMemDepPosDTO.class));

        return memberList;
    }

    public List<SettingMemDepPosDTO> searchMemberList(String search) {

        List<SettingMemDepPos> memberListWithSearchValue = settingMemDepPosRepository.findByMemNameContaining(search);

        List<SettingMemDepPosDTO> memberDTOList = memberListWithSearchValue.stream()
                .map(member -> modelMapper.map(member, SettingMemDepPosDTO.class))
                .collect(Collectors.toList());

        return memberDTOList;
    }

    public List<SettingDepartmentDTO> searchDepName() {
        List<SettingDepartment> depList = settingDepartmentRepository.findByDepDeleteStatus("N");

        List<SettingDepartmentDTO> depDTOList = depList.stream()
                .map(dep -> modelMapper.map(dep, SettingDepartmentDTO.class))
                .collect(Collectors.toList());

        return depDTOList;
    }

    public List<SettingPositionDTO> searchPosition() {
        List<SettingPosition> positionList = settingPositionRepository.findAll();

        List<SettingPositionDTO> positionDTOList = positionList.stream()
                .map(position -> modelMapper.map(position, SettingPositionDTO.class))
                .collect(Collectors.toList());
        return positionDTOList;
    }

    public SettingResourcesDTO searchResourcesInformation(int memCode) {
        SettingResourcesDTO resourcesDTO = new SettingResourcesDTO();
        resourcesDTO.setMemCode(memCode);

        SettingResources resources = resourcesRepository.findByMemCode(memCode);

        List<SettingDocumentFile> documentFiles = resources.getDocumentFiles();

        List<SettingDocumentFile> filteredDocumentFiles = documentFiles.stream()
                .filter(file -> !("프로필".equals(file.getDocAtcKind()) || "서명".equals(file.getDocAtcKind())))
                .collect(Collectors.toList());
        List<SettingDocumentFileDTO> resultFiles = filteredDocumentFiles.stream()
                .map(fileList -> modelMapper.map(fileList, SettingDocumentFileDTO.class))
                .collect(Collectors.toList());

        resourcesDTO = modelMapper.map(resources, SettingResourcesDTO.class);

        resourcesDTO.setDocumentFiles(resultFiles);

        return resourcesDTO;
    }



    @Transactional
    public List<SettingDegreeDTO> insertDegree(List<SettingDegreeDTO> degreeDTO) {
        try {
            List<SettingDegree> insertDegrees = degreeDTO.stream()
                    .map(degree -> modelMapper.map(degree, SettingDegree.class))
                    .collect(Collectors.toList());

            List<SettingDegree> insertResults = settingDegreeRepository.saveAll(insertDegrees);

            List<SettingDegreeDTO> resultDTO = insertResults.stream()
                    .map(convertDTO -> modelMapper.map(convertDTO, SettingDegreeDTO.class))
                    .collect(Collectors.toList());

            return resultDTO;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    @Transactional
    public List<SettingCertificateDTO> insertCertificate(List<SettingCertificateDTO> certificateDTO) {
        log.info(certificateDTO.toString());

        try{
            List<SettingCertificate> insertCertificate = certificateDTO.stream()
                    .map(certificate -> modelMapper.map(certificate, SettingCertificate.class))
                    .collect(Collectors.toList());

            List<SettingCertificate> insertResult = settingCertificateRepository.saveAll(insertCertificate);

            List<SettingCertificateDTO> resultDTO = insertResult.stream()
                    .map(convertDTO -> modelMapper.map(convertDTO, SettingCertificateDTO.class))
                    .collect(Collectors.toList());

            return resultDTO;

        }catch(Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Transactional
    public List<SettingCareerDTO> insertCareer(List<SettingCareerDTO> careerDTO) {
        log.info("insertCareer Start~~~~~~~~~~~~");
        log.info(careerDTO.toString());

        try{
            List<SettingCareer> insertCareer = careerDTO.stream()
                    .map(career -> modelMapper.map(career, SettingCareer.class))
                    .collect(Collectors.toList());

            List<SettingCareer> insertResult = settingCareerRepository.saveAll(insertCareer);
            System.out.println("insertResult = " + insertResult);

            List<SettingCareerDTO> resultDTO = insertResult.stream()
                    .map(convertDTO -> modelMapper.map(convertDTO, SettingCareerDTO.class))
                    .collect(Collectors.toList());

            return resultDTO;


        }catch(Exception e) {
            log.info("오류~~~~~~~");
            throw new RuntimeException(e);
        }

    }


    @Transactional
    public SettingDegreeFileDTO insertDegreeFile(SettingDegreeDTO degreeDTO, MultipartFile degreeFile) {
        log.info("insertDegreeFile Start~~~~~~~~~~~~");
        log.info(degreeDTO.toString());

        SettingDegreeFileDTO fileDTO = new SettingDegreeFileDTO();

        String fileName = UUID.randomUUID().toString().replace("-", "");
        String replaceFileName = null;

        String path = IMAGE_DIR + "/degree/" ;

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
            SettingDegreeFile insertResult = settingDegreeFileRepository.save(insertFile);

            SettingDegreeFileDTO convertDTO = modelMapper.map(insertResult, SettingDegreeFileDTO.class);

            return convertDTO;

        }catch(Exception e) {
            log.info("오류~~~~~~~");
            throw new RuntimeException(e);
        }

    }

    @Transactional
    public SettingCertificateFileDTO insertCertificateFile(SettingCertificateDTO certificateDTO, MultipartFile certificateFile) {
        log.info(certificateDTO.toString());

        SettingCertificateFileDTO fileDTO = new SettingCertificateFileDTO();

        String fileName = UUID.randomUUID().toString().replace("-", "");
        String replaceFileName = null;

        String path = IMAGE_DIR + "/certificate/" ;

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

            SettingCertificateFileDTO certificateFileDTO = modelMapper.map(insertFile, SettingCertificateFileDTO.class);

            return certificateFileDTO;

        }catch(Exception e) {
            log.info("오류~~~~~~~");
            throw new RuntimeException(e);
        }

    }

    @Transactional
    public SettingCareerFileDTO insertCareerFile(SettingCareerDTO careerDTO, MultipartFile careerFile) {
        log.info("insertCareerFile Start~~~~~~~~~~~~");
        log.info(careerDTO.toString());

        SettingCareerFileDTO fileDTO = new SettingCareerFileDTO();

        String fileName = UUID.randomUUID().toString().replace("-", "");
        String replaceFileName = null;

        String path = IMAGE_DIR + "/career/" ;

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

            SettingCareerFileDTO careerFileDTO = modelMapper.map(insertFile, SettingCareerFileDTO.class);

            return careerFileDTO;


        }catch(Exception e) {
            log.info("오류~~~~~~~");
            throw new RuntimeException(e);
        }

    }

    @Transactional
    public SettingSalaryFileDTO insertSalaryFile(SettingSalaryDTO salaryDTO, MultipartFile salaryFile) {
        log.info("insertSalaryFile Start~~~~~~~~~~~~");
        log.info(salaryDTO.toString());

        SettingSalaryFileDTO fileDTO = new SettingSalaryFileDTO();

        String fileName = UUID.randomUUID().toString().replace("-", "");
        String replaceFileName = null;

        String path = IMAGE_DIR + "/salary/" ;

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

            SettingSalaryFileDTO salaryFileDTO = modelMapper.map(insertFile, SettingSalaryFileDTO.class);

            return salaryFileDTO;
        }catch(Exception e) {
            log.info("오류~~~~~~~");
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public SettingDocumentFileDTO insertDocumentFile(SettingDocumentFileDTO etcfileDTO, MultipartFile etcFile) {

        String fileName = UUID.randomUUID().toString().replace("-", "");
        String replaceFileName = null;

        String path = IMAGE_DIR + "/etcDocumentFile/" ;

        String[] extend = etcFile.getOriginalFilename().split("\\.");

        String realExtend = extend[1];
        try{

            replaceFileName = FileUploadUtils.saveFile(path, fileName, etcFile);

            etcfileDTO.setDocAtcExtends(realExtend);
            etcfileDTO.setDocAtcConvertName(replaceFileName);
            etcfileDTO.setDocAtcRegistDate(LocalDate.now().toString());
            etcfileDTO.setDocAtcStorage(path);
            etcfileDTO.setDocAtcDeleteStatus("N");
            etcfileDTO.setDocAtcPath(path);
            etcfileDTO.setDocAtcOriginName(etcFile.getOriginalFilename());

            SettingDocumentFile insertFile = modelMapper.map(etcfileDTO, SettingDocumentFile.class);
            settingDocumentFileRepository.save(insertFile);

            SettingDocumentFileDTO documentFileDTO = modelMapper.map(insertFile, SettingDocumentFileDTO.class);

            return documentFileDTO;

        }catch(Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Transactional
    public List<SettingDegreeDTO> updateDegree(List<SettingDegreeDTO> degreeDTO) {
        List<SettingDegreeDTO> updateDegree = new ArrayList<>();
        try{
            for (SettingDegreeDTO settingDegreeDTO : degreeDTO) {

                SettingDegree degree = settingDegreeRepository.findById(settingDegreeDTO.getDegCode()).get();
                if (degree != null) {

                    degree = degree.degKind(settingDegreeDTO.getDegKind())
                            .degMajor(settingDegreeDTO.getDegMajor())
                            .degName(settingDegreeDTO.getDegName())
                            .degGraduation(settingDegreeDTO.getDegGraduation())
                            .degState(settingDegreeDTO.getDegState())
                            .degAdmissions(settingDegreeDTO.getDegAdmissions()).build();
                }
            }

        }catch(Exception e) {
            throw new RuntimeException(e);
        }

        List<SettingDegree> degreeList = settingDegreeRepository.findAll();
        List<SettingDegreeDTO> resultDTO = degreeList.stream()
                .map(resultList -> modelMapper.map(resultList, SettingDegreeDTO.class))
                .collect(Collectors.toList());

        return resultDTO;

    }

    @Transactional
    public List<SettingCertificateDTO> updateCertificate(List<SettingCertificateDTO> certificateDTO) {
        log.info("updateCertificate Start~~~~~~~~~~~~");
        log.info(certificateDTO.toString());
        List<SettingCertificateDTO> updateCertificate = new ArrayList<>();


        try{
            for (SettingCertificateDTO certificateDTO1 : certificateDTO) {
                SettingCertificate certificate = settingCertificateRepository.findById(certificateDTO1.getCerCode()).get();

                if (certificate != null) {

                    certificate = certificate.cerName(certificateDTO1.getCerName())
                            .cerKind(certificateDTO1.getCerKind())
                            .cerDay(certificateDTO1.getCerDay())
                            .cerEndDate(certificateDTO1.getCerEndDate())
                            .cerDescription(certificateDTO1.getCerDescription())
                            .cerInstitution(certificateDTO1.getCerInstitution())
                            .build();
                }
            }


        }catch(Exception e) {
            log.info("오류~~~~~~~");
            throw new RuntimeException(e);
        }

        List<SettingCertificate> degreeList = settingCertificateRepository.findAll();
        List<SettingCertificateDTO> resultDTO = degreeList.stream()
                .map(resultList -> modelMapper.map(resultList, SettingCertificateDTO.class))
                .collect(Collectors.toList());

        return resultDTO;
    }

    @Transactional
    public List<SettingCareerDTO> updateCareer(List<SettingCareerDTO> careerDTOList) {
        log.info("updateCareers Start~~~~~~~~~~~~");

        List<SettingCareerDTO> updatedCareers = new ArrayList<>();

        try {
            for (SettingCareerDTO careerDTO : careerDTOList) {
                SettingCareer career = settingCareerRepository.findById(careerDTO.getCrrCode()).get();

                if (career != null) {
                    career = career.crrName(careerDTO.getCrrName())
                            .crrPosition(careerDTO.getCrrPosition())
                            .crrStartDate(careerDTO.getCrrStartDate())
                            .crrEndDate(careerDTO.getCrrEndDate())
                            .crrState(careerDTO.getCrrState())
                            .crrDescription(careerDTO.getCrrDescription())
                            .build();

                }

            }

            List<SettingCareer> degreeList = settingCareerRepository.findAll();
            List<SettingCareerDTO> resultDTO = degreeList.stream()
                    .map(resultList -> modelMapper.map(resultList, SettingCareerDTO.class))
                    .collect(Collectors.toList());

            return resultDTO;

        } catch (Exception e) {
            log.error("오류 발생: ", e);
            throw new RuntimeException("수정 실패: 오류 발생");
        }
    }


    @Transactional
    public SettingSalaryDTO insertSalary(SettingSalaryDTO salaryDTO) {
        log.info("insertSalary Start~~~~~~~~~~~~");
        log.info(salaryDTO.toString());


        try{
            SettingSalary insertSalary = modelMapper.map(salaryDTO, SettingSalary.class);

            SettingSalary insertResult = settingSalaryRepository.save(insertSalary);
            System.out.println("insertResult = " + insertResult);


        }catch(Exception e) {
            log.info("오류~~~~~~~");
            throw new RuntimeException(e);
        }

        SettingSalary salary = settingSalaryRepository.findByMemCode(salaryDTO.getMemCode());
        SettingSalaryDTO resultDTO = modelMapper.map(salary, SettingSalaryDTO.class);
        return resultDTO;
    }

    @Transactional
    public SettingSalaryDTO updateSalary(SettingSalaryDTO salaryDTO) {
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

        SettingSalary salary = settingSalaryRepository.findByMemCode(salaryDTO.getMemCode());
        SettingSalaryDTO resultDTO = modelMapper.map(salary, SettingSalaryDTO.class);
        return resultDTO;
    }


    @Transactional
    public SettingDocumentFileDTO updateDocumentFile(SettingDocumentFileDTO etcfileDTO, MultipartFile etcFile) {
        log.info("updateDocumentFile Start~~~~~~~~~~~~");
        log.info("etcfileDTO : " + etcfileDTO);
        System.out.println("여기11111");

        String path = IMAGE_DIR + "/etcDocumentFile/";
        System.out.println("여기11133311");

        String replaceFileName = null;
        System.out.println("여기114444111");

        System.out.println("여기11111");

        try {
            if(etcFile != null && !etcFile.isEmpty()) {
                System.out.println("여기");
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
                        .docAtcKind(etcfileDTO.getDocAtcKind()).build();
            }else {
                System.out.println("여기~~");

                SettingDocumentFile file = settingDocumentFileRepository.findByDocAtcCode(etcfileDTO.getDocAtcCode());
                System.out.println("file = " + file);
                file = file.docAtcKind(etcfileDTO.getDocAtcKind()).build();
            }


        } catch (Exception e) {
            FileUploadUtils.deleteFile(path, replaceFileName);
            throw new RuntimeException(e);
        }
        SettingDocumentFile file = settingDocumentFileRepository.findByDocAtcCode(etcfileDTO.getDocAtcCode());
        SettingDocumentFileDTO resultDTO = modelMapper.map(file, SettingDocumentFileDTO.class);
        return resultDTO;    }


    @Transactional
    public MPHoldVacationDTO updateVacation(MPHoldVacationDTO holdVacationDTO) {

        try{
            MPHoldVacation vacation = vacationRepository.findByMemCode(holdVacationDTO.getMemCode());
            int vct = vacation.getVctCount();
            int vctResult = vct + holdVacationDTO.getVctCount();
            holdVacationDTO.setVctCount(vctResult);

            vacation = vacation.vctCount(holdVacationDTO.getVctCount()).build();

        }catch(Exception e) {
            throw new RuntimeException(e);
        }

        MPHoldVacation vacation = vacationRepository.findByMemCode(holdVacationDTO.getMemCode());
        MPHoldVacationDTO resultDTO = modelMapper.map(vacation, MPHoldVacationDTO.class);
        return resultDTO;
    }

    @Transactional
    public SettingSalaryFileDTO updateSalaryFile(SettingSalaryFileDTO salaryFileDTO, MultipartFile salaryFile) {
        log.info("updateSalaryFile Start~~~~~~~~~~~~");
        log.info("salaryFileDTO : " + salaryFileDTO);
        log.info("salaryFile :" + salaryFile.toString() );

        String path = IMAGE_DIR + "/salary/";

        String replaceFileName = null;

        try {

            SettingSalaryFile file = settingSalaryFileRepository.findById(salaryFileDTO.getSalAtcCode()).get();

            System.out.println("file = " + file);
            String fileName = UUID.randomUUID().toString().replace("-", "");

            replaceFileName = FileUploadUtils.saveFile(path, fileName, salaryFile);
            System.out.println("replaceFileName = " + replaceFileName);

            file = file.salAtcName(salaryFile.getOriginalFilename())
                    .salAtcRegistDate(LocalDate.now().toString())
                    .salAtcPath(path)
                    .salAtcConvertName(replaceFileName)
                    .salAtcCode(salaryFileDTO.getSalAtcCode())
                    .salCode(salaryFileDTO.getSalCode())
                    .salAtcDeleteStatus("N")
                    .build();

        } catch (Exception e) {
            FileUploadUtils.deleteFile(path, replaceFileName);
            throw new RuntimeException(e);
        }
        SettingSalaryFile file = settingSalaryFileRepository.findById(salaryFileDTO.getSalAtcCode()).get();
        SettingSalaryFileDTO resultDTO = modelMapper.map(file, SettingSalaryFileDTO.class);
        return resultDTO;
    }

    @Transactional
    public SettingCertificateFileDTO updateCertificateFile(SettingCertificateFileDTO certificateFileDTO, MultipartFile certificateFile) {
        log.info("updateCertificateFile Start~~~~~~~~~~~~");
        log.info("certificateFileDTO : " + certificateFileDTO);

        String path = IMAGE_DIR + "/certificate/";

        String replaceFileName = null;


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


        } catch (Exception e) {
            FileUploadUtils.deleteFile(path, replaceFileName);
            throw new RuntimeException(e);
        }
        SettingCertificateFile file = settingCertificateFileRepository.findById(certificateFileDTO.getCerAtcCode()).get();
        SettingCertificateFileDTO resultDTO = modelMapper.map(file, SettingCertificateFileDTO.class);
        return resultDTO;
    }

    @Transactional
    public SettingDegreeFileDTO updateDegreeFile(SettingDegreeFileDTO degreeFileDTO, MultipartFile degreeFile) {
        log.info("updateDocumentFile Start~~~~~~~~~~~~");
        log.info("degreeFileDTO : " + degreeFileDTO);

        String path = IMAGE_DIR + "/degree/";

        String replaceFileName = null;


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


        } catch (Exception e) {
            FileUploadUtils.deleteFile(path, replaceFileName);
            throw new RuntimeException(e);
        }
        SettingDegreeFile file = settingDegreeFileRepository.findById(degreeFileDTO.getDegAtcCode()).get();
        SettingDegreeFileDTO resultDTO = modelMapper.map(file, SettingDegreeFileDTO.class);
        return resultDTO;
    }

    @Transactional
    public SettingCareerFileDTO updateCareerFile(SettingCareerFileDTO careerFileDTO, MultipartFile careerFile) {

        String path = IMAGE_DIR + "/career/";

        String replaceFileName = null;

        try {
            SettingCareerFile file = settingCareerFileRepository.findById(careerFileDTO.getCrrAtcCode()).get();
            FileUploadUtils.deleteFile(path, file.getCrrAtcConvertName());

            String[] extend = careerFile.getOriginalFilename().split("\\.");
            String realExtend = extend[1];
            String fileName = UUID.randomUUID().toString().replace("-", "");

            replaceFileName = FileUploadUtils.saveFile(path, fileName, careerFile);

            file = file.crrAtcExtends(realExtend)
                    .crrAtcConvertName(replaceFileName)
                    .crrAtcRegistDate(LocalDate.now().toString())
                    .crrAtcName(careerFile.getOriginalFilename())
                    .crrAtcPath(path).build();

        } catch (Exception e) {
            FileUploadUtils.deleteFile(path, replaceFileName);
            throw new RuntimeException(e);
        }
        SettingCareerFile file = settingCareerFileRepository.findById(careerFileDTO.getCrrAtcCode()).get();
        SettingCareerFileDTO resultDTO = modelMapper.map(file, SettingCareerFileDTO.class);
        return resultDTO;
    }

//    public List<SettingMemDepAttSchDTO> AllMemberAttendance(SettingSearchValueDTO valueDTO) {
//
//        List<Object[]> list = settingMemDepAttSchRepository.findByYearMonth(valueDTO.getYearMonth());
//        List<SettingMemDepAttSchDTO> resultDTOList = list.stream()
//                .map(this::mappingDTO)
//                .collect(Collectors.toList());
//        return resultDTOList;
//    }

    public List<SettingMemDepAttSchDTO> AllMemberAttendance(SettingSearchValueDTO valueDTO) {
        List<SettingMemDepAttSchDTO> resultDTOList =settingMemDepAttSchRepository.findByYearMonth(valueDTO.getYearMonth());
        return resultDTOList;
    }

//    private SettingMemDepAttSchDTO mappingDTO(Object[] array) {
//        SettingMemDepAttSchDTO resultDTO = new SettingMemDepAttSchDTO();
//
//        resultDTO.setMemCode((Integer) array[0]);
//        resultDTO.setMemName((String) array[1]);
//        resultDTO.setDepCode(array[2] != null ? (Integer) array[2] : 0);
//        resultDTO.getAttendances().setAttCode((Integer) array[3]);
//        resultDTO.getAttendances().setAttStartTime((String) array[4]);
//        resultDTO.getAttendances().setAttEndTime((String) array[5]);
//        resultDTO.getAttendances().setAttStatus((String) array[6]);
//        resultDTO.getAttendances().setAttWorkDate((String) array[7]);
//        resultDTO.getAttendances().setSchCode((String) array[8]);
//        resultDTO.getScheduleDTO().setSchType((String) array[9]);
//        resultDTO.getScheduleDTO().setSchColor((String) array[10]);
//        resultDTO.getDepartment().setDepName((String) array[11]);
//
//
//        return resultDTO;
//    }

    @Transactional
    public List<SettingSalaryFileDTO> deleteSalaryFile(SettingSalaryFileDTO salaryFileDTO) {
        log.info("deleteSalaryFile Start~~~~~~~~~~~~");
        int result = 0;
        try {
            settingSalaryFileRepository.deleteById(salaryFileDTO.getSalAtcCode());
            result = 1;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        log.info("deleteSalaryFile 끝~~~~~~~~~~~~");
        List<SettingSalaryFile> fileDTOS = settingSalaryFileRepository.findAll();
        List<SettingSalaryFileDTO> resultDTO = fileDTOS.stream()
                .map(resultList -> modelMapper.map(resultList, SettingSalaryFileDTO.class))
                .collect(Collectors.toList());

        return resultDTO;
    }
    @Transactional
    public List<SettingDocumentFileDTO> deleteDocumentFile(SettingDocumentFileDTO documentFileDTO) {
        log.info("deleteDocumentFile Start~~~~~~~~~~~~");
        try {
            settingDocumentFileRepository.deleteById(documentFileDTO.getDocAtcCode());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        log.info("deleteDocumentFile 끝~~~~~~~~~~~~");
        List<SettingDocumentFile> fileDTOS = settingDocumentFileRepository.findAll();
        List<SettingDocumentFileDTO> resultDTO = fileDTOS.stream()
                .map(resultList -> modelMapper.map(resultList, SettingDocumentFileDTO.class))
                .collect(Collectors.toList());

        return resultDTO;
    }
    @Transactional
    public List<SettingDegreeFileDTO> deleteDegreeFile(SettingDegreeFileDTO degreeFileDTO) {
        log.info("deleteDegreeFile Start~~~~~~~~~~~~");
        try {
            settingDegreeFileRepository.deleteById(degreeFileDTO.getDegAtcCode());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        log.info("deleteDegreeFile 끝~~~~~~~~~~~~");
        List<SettingDegreeFile> fileDTOS = settingDegreeFileRepository.findAll();
        List<SettingDegreeFileDTO> resultDTO = fileDTOS.stream()
                .map(resultList -> modelMapper.map(resultList, SettingDegreeFileDTO.class))
                .collect(Collectors.toList());

        return resultDTO;
    }
    @Transactional
    public List<SettingCertificateFileDTO> deleteCertificateFile(SettingCertificateFileDTO certificateFileDTO) {
        try {
            settingCertificateFileRepository.deleteById(certificateFileDTO.getCerAtcCode());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        List<SettingCertificateFile> fileDTOS = settingCertificateFileRepository.findAll();
        List<SettingCertificateFileDTO> resultDTO = fileDTOS.stream()
                .map(resultList -> modelMapper.map(resultList, SettingCertificateFileDTO.class))
                .collect(Collectors.toList());

        return resultDTO;
    }
    @Transactional
    public List<SettingCareerFileDTO> deleteCareerFile(SettingCareerFileDTO careerFileDTO) {
        log.info("deleteCareerFile Start~~~~~~~~~~~~");
        try {
            settingCareerFileRepository.deleteById(careerFileDTO.getCrrAtcCode());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        log.info("deleteCareerFile 끝~~~~~~~~~~~~");
        List<SettingCareerFile> fileDTOS = settingCareerFileRepository.findAll();
        List<SettingCareerFileDTO> resultDTO = fileDTOS.stream()
                .map(resultList -> modelMapper.map(resultList, SettingCareerFileDTO.class))
                .collect(Collectors.toList());

        return resultDTO;     }

    @Transactional
    public List<SettingCertificateDTO> deleteCertificate(SettingCertificateDTO certificateDTO) {
        try {
            settingCertificateRepository.deleteById(certificateDTO.getCerCode());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        List<SettingCertificate> fileDTOS = settingCertificateRepository.findAll();
        List<SettingCertificateDTO> resultDTO = fileDTOS.stream()
                .map(resultList -> modelMapper.map(resultList, SettingCertificateDTO.class))
                .collect(Collectors.toList());

        return resultDTO;     }

    @Transactional
    public List<SettingCareerDTO> deleteCareer(SettingCareerDTO careerDTO) {
        log.info("deleteCertificate Start~~~~~~~~~~~~");
        try {
            settingCareerRepository.deleteById(careerDTO.getCrrCode());

        }catch (Exception e){
            throw new RuntimeException(e);
        }
        log.info("deleteCertificate 끝~~~~~~~~~~~~");
        List<SettingCareer> fileDTOS = settingCareerRepository.findAll();
        List<SettingCareerDTO> resultDTO = fileDTOS.stream()
                .map(resultList -> modelMapper.map(resultList, SettingCareerDTO.class))
                .collect(Collectors.toList());

        return resultDTO;     }

    @Transactional
    public List<SettingDegreeDTO> deleteDegree(SettingDegreeDTO degreeDTO) {
        log.info("deleteCertificate Start~~~~~~~~~~~~");
        try {
            settingDegreeRepository.deleteById(degreeDTO.getDegCode());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        log.info("deleteCertificate 끝~~~~~~~~~~~~");
        List<SettingDegree> fileDTOS = settingDegreeRepository.findAll();
        List<SettingDegreeDTO> resultDTO = fileDTOS.stream()
                .map(resultList -> modelMapper.map(resultList, SettingDegreeDTO.class))
                .collect(Collectors.toList());

        return resultDTO;     }

    @Transactional
    public List<SettingSalaryDTO> deleteSalary(SettingSalaryDTO salaryDTO) {
        log.info("deleteSalary Start~~~~~~~~~~~~");
        try {
            settingSalaryRepository.deleteById(salaryDTO.getSalCode());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        log.info("deleteSalary 끝~~~~~~~~~~~~");
        List<SettingSalary> fileDTOS = settingSalaryRepository.findAll();
        List<SettingSalaryDTO> resultDTO = fileDTOS.stream()
                .map(resultList -> modelMapper.map(resultList, SettingSalaryDTO.class))
                .collect(Collectors.toList());

        return resultDTO;     }

}
