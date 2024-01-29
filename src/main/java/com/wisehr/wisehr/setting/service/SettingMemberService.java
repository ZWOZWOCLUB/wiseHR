package com.wisehr.wisehr.setting.service;

import com.wisehr.wisehr.common.Criteria;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SettingMemberService {
    private final SettingMemberRepository settingMemberRepository;
    private final SettingMemDepPosRepository settingMemDepPosRepository;
    private final SettingDepartmentRepository settingDepartmentRepository;
    private final SettingPositionRepository settingPositionRepository;
    private final SettingDegreeRepository settingDegreeRepository;
    private final SettingCareerRepository settingCareerRepository;
    private final SettingCertificateRepository settingCertificateRepository;

    private final ModelMapper modelMapper;

    public SettingMemberService(SettingMemberRepository settingMemberRepository, SettingMemDepPosRepository settingMemDepPosRepository, SettingDepartmentRepository settingDepartmentRepository, SettingPositionRepository settingPositionRepository, SettingDegreeRepository settingDegreeRepository, SettingCareerRepository settingCareerRepository, SettingCertificateRepository settingCertificateRepository, ModelMapper modelMapper) {
        this.settingMemberRepository = settingMemberRepository;
        this.settingMemDepPosRepository = settingMemDepPosRepository;
        this.settingDepartmentRepository = settingDepartmentRepository;
        this.settingPositionRepository = settingPositionRepository;
        this.settingDegreeRepository = settingDegreeRepository;
        this.settingCareerRepository = settingCareerRepository;
        this.settingCertificateRepository = settingCertificateRepository;
        this.modelMapper = modelMapper;
    }


    @Transactional
    public String insertMember(SettingMemberDTO settingMemberDTO, MultipartFile profile) {
        log.info("insertMember Start~~~~~~~~~~~~");
        log.info(settingMemberDTO.toString());

        int result = 0;

        try{
            SettingMember inserMember = modelMapper.map(settingMemberDTO, SettingMember.class);

            SettingMember reuslt = settingMemberRepository.save(inserMember);
            System.out.println("reuslt = " + reuslt);
        result = 1;


        }catch(Exception e) {
            log.info("오류~~~~~~~");
            throw new RuntimeException(e);
        }

        return (result > 0)? "등록 성공": "등록 실패";
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
        System.out.println("degreeList = " + degreeList);
        System.out.println("careerList = " + careerList);
        System.out.println("certificateList = " + certificateList);

        List<SettingCertificateDTO> certificateDTOList = certificateList.stream()
                .map(certificate -> modelMapper.map(certificate, SettingCertificateDTO.class))
                .collect(Collectors.toList());
        List<SettingCareerDTO> careerDTOList = careerList.stream()
                .map(career -> modelMapper.map(career, SettingCareerDTO.class))
                .collect(Collectors.toList());
        List<SettingDegreeDTO> degreeDTOList = degreeList.stream()
                .map(degree -> modelMapper.map(degree, SettingDegreeDTO.class))
                .collect(Collectors.toList());

        SettingResourcesDTO resourcesDTO = new SettingResourcesDTO();
        resourcesDTO.setMemCode(memCode);
        resourcesDTO.setCareerDTO(careerDTOList);
        resourcesDTO.setDegreeDTO(degreeDTOList);
        resourcesDTO.setCertificateDTO(certificateDTOList);

        log.info("searchResourcesInformation 서비스 끗~~~~~~~~~~");

        return resourcesDTO;
    }

    @Transactional
    public SettingResourcesDTO insertResourcesInformation(SettingResourcesDTO settingResourcesDTO) {
        log.info("insertResourcesInformation 서비스 시작~~~~~~~~~~");
        log.info(settingResourcesDTO.toString());

        log.info("insertResourcesInformation 서비스 끗~~~~~~~~~~");

        return null;
    }
}
