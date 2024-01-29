package com.wisehr.wisehr.organization.service;

import com.wisehr.wisehr.organization.dto.OrgDepartmentDTO;
import com.wisehr.wisehr.organization.entity.OrgDepartment;
import com.wisehr.wisehr.organization.repository.OrgRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrgService {

    private final OrgRepository orgRepository;

    private final ModelMapper modelMapper;

    public OrgService(OrgRepository orgRepository, ModelMapper modelMapper) {
        this.orgRepository = orgRepository;
        this.modelMapper = modelMapper;
    }

    public OrgDepartmentDTO selectAllOrgList(){

        List<OrgDepartment> orgDepartment = orgRepository.findAll();
        OrgDepartmentDTO orgDepartmentList = modelMapper.map(orgDepartment, OrgDepartmentDTO.class);



        return orgDepartmentList;
    }

}
