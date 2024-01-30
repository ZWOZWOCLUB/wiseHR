package com.wisehr.wisehr.organization.service;

import com.wisehr.wisehr.organization.dto.OrgDepartmentDTO;
import com.wisehr.wisehr.organization.entity.OrgDepartment;
import com.wisehr.wisehr.organization.repository.OrgRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrgService {

    private final OrgRepository orgRepository;

    private final ModelMapper modelMapper;

    public OrgService(OrgRepository orgRepository, ModelMapper modelMapper) {
        this.orgRepository = orgRepository;
        this.modelMapper = modelMapper;
    }

    public List<OrgDepartmentDTO> selectAllOrgList(){

        List<OrgDepartment> orgDepartment = orgRepository.findAll();

        List<OrgDepartmentDTO> orgDepartmentList = orgDepartment.stream()
                .map(orgDep -> modelMapper.map(orgDep, OrgDepartmentDTO.class))
                .collect(Collectors.toList());

        return orgDepartmentList;
    }

    public List<OrgDepartmentDTO> selectRefOrgList() {

        List<OrgDepartment> orgDepartment = orgRepository.findRefDepCode();

        List<OrgDepartmentDTO> orgRefList = orgDepartment.stream()
                .map(orgDep -> modelMapper.map(orgDep, OrgDepartmentDTO.class))
                .collect(Collectors.toList());
        return orgRefList;
    }

    @Transactional
    public String insertOrgDepartment(OrgDepartmentDTO orgDepartmentDTO) {


        int result = 0;

        OrgDepartment insertOrgDep = modelMapper.map(orgDepartmentDTO, OrgDepartment.class);

        try {
            orgRepository.save(insertOrgDep);
            result = 1;
        } catch (Exception e){
            throw new RuntimeException(e);
        }


        return (result > 0)? "부서 등록 성공" : "부서 등록 실패";
    }

    /**
     * 부서명 수정 메소드
     * @param orgDepartmentDTO
     * @return
     */
    @Transactional
    public String  modifyOrgDep(OrgDepartmentDTO orgDepartmentDTO) {

        OrgDepartment modifyOrgDep = orgRepository.findById(orgDepartmentDTO.getDepCode()).get();

        //작성중.

        return "";
    }
}
