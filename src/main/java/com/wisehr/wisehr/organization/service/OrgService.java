package com.wisehr.wisehr.organization.service;

import com.wisehr.wisehr.organization.dto.OrgDepartmentAndOrgMemberDTO;
import com.wisehr.wisehr.organization.dto.OrgDepartmentDTO;
import com.wisehr.wisehr.organization.entity.OrgDepartment;
import com.wisehr.wisehr.organization.entity.OrgDepartmentAndOrgMember;
import com.wisehr.wisehr.organization.entity.OrgMember;
import com.wisehr.wisehr.organization.repository.OrgDepAndMemRepository;
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
    private final OrgDepAndMemRepository orgDepAndMemRepository;

    private final ModelMapper modelMapper;

        public OrgService(OrgRepository orgRepository, OrgDepAndMemRepository orgDepAndMemRepository, ModelMapper modelMapper) {

        this.orgRepository = orgRepository;
        this.orgDepAndMemRepository = orgDepAndMemRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * 전체부서 검색 메소드
     * @return
     */
    public List<OrgDepartmentDTO> selectAllOrgList(){

        List<OrgDepartment> orgDepartment = orgRepository.findAll();

        List<OrgDepartmentDTO> orgDepartmentList = orgDepartment.stream()
                .map(orgDep -> modelMapper.map(orgDep, OrgDepartmentDTO.class))
                .collect(Collectors.toList());

        return orgDepartmentList;
    }

    /**
     * 상위부서 검색 메소드
     * @return
     */
    public List<OrgDepartmentDTO> selectRefOrgList() {

        List<OrgDepartment> orgDepartment = orgRepository.findRefDepCode();

        List<OrgDepartmentDTO> orgRefList = orgDepartment.stream()
                .map(orgDep -> modelMapper.map(orgDep, OrgDepartmentDTO.class))
                .collect(Collectors.toList());
        return orgRefList;
    }


    /**
     * 부서명 등록 메소드
     * @param orgDepartmentDTO
     * @return
     */
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
    public String modifyOrgDep(OrgDepartmentDTO orgDepartmentDTO) {

        int result = 0;

    try{
        OrgDepartment modifyOrgDep = orgRepository.findById(orgDepartmentDTO.getDepCode()).get();
        modifyOrgDep = modifyOrgDep.depName(orgDepartmentDTO.getDepName()).build();

        result = 1;
    } catch (Exception e){
        log.info("에러");
    }

        return (result > 0) ? "부서 이름 수정 성공" : "부서 이름 수정 실패";
    }

    /**
     * 부서별 멤버 리스트 조회 메소드
     * @return
     */
    public List<OrgDepartmentAndOrgMemberDTO> AllMemOfDep() {

        List<OrgDepartmentAndOrgMember> orgDepartmentAndOrgMembers = orgDepAndMemRepository.findAll();
        List<OrgDepartmentAndOrgMemberDTO> orgDepartmentAndOrgMemberList = orgDepartmentAndOrgMembers.stream()
                .map(odm -> modelMapper.map(odm, OrgDepartmentAndOrgMemberDTO.class))
                .collect(Collectors.toList());



        return orgDepartmentAndOrgMemberList;
    }


    /**
     * 부서 삭제 메소드(상태 Y로 업데이트 및 멤버 null)
     * @param orgDepartmentAndOrgMemberDTO
     * @return
     */
    @Transactional
    public String deleteOrgDep(OrgDepartmentAndOrgMemberDTO orgDepartmentAndOrgMemberDTO) {

        int result = 0;

        try {

            //부서 상태를 Y로 변경
            OrgDepartmentAndOrgMember deleteOrgDep = orgDepAndMemRepository.findById(orgDepartmentAndOrgMemberDTO.getDepCode()).get();
            deleteOrgDep = deleteOrgDep.depDeleteStatus("Y")
                    .build();

            //부서에 속한 멤버들의 dep_code를 null로 변경

            List<OrgMember> memberList = deleteOrgDep.getMemberList();
                for(OrgMember member: memberList){
                    member.setOrgDepAndOrgMem(null);
                }

            result = 1;



        }catch(Exception e){
            log.info("에러");
        }

        return (result > 0) ? "부서 삭제 성공" : "부서 삭제 실패";
    }
}
