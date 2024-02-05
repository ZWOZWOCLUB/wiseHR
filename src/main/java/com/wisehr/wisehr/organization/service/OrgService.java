package com.wisehr.wisehr.organization.service;

import com.wisehr.wisehr.common.Criteria;
import com.wisehr.wisehr.organization.dto.OrgDepartmentAndOrgMemberDTO;
import com.wisehr.wisehr.organization.dto.OrgDepartmentDTO;
import com.wisehr.wisehr.organization.dto.OrgMemAndOrgDepDTO;
import com.wisehr.wisehr.organization.dto.OrgMemberDTO;
import com.wisehr.wisehr.organization.entity.OrgDepartment;
import com.wisehr.wisehr.organization.entity.OrgDepartmentAndOrgMember;
import com.wisehr.wisehr.organization.entity.OrgMemAndOrgDep;
import com.wisehr.wisehr.organization.entity.OrgMember;
import com.wisehr.wisehr.organization.repository.OrgDepAndMemRepository;
import com.wisehr.wisehr.organization.repository.OrgMemAndDepRepository;
import com.wisehr.wisehr.organization.repository.OrgMemberRepository;
import com.wisehr.wisehr.organization.repository.OrgRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrgService {

    private final OrgRepository orgRepository;
    private final OrgDepAndMemRepository orgDepAndMemRepository;
    private final OrgMemberRepository orgMemberRepository;
    private final OrgMemAndDepRepository orgMemAndDepRepository;
    private final ModelMapper modelMapper;

        public OrgService(OrgRepository orgRepository, OrgDepAndMemRepository orgDepAndMemRepository, OrgMemberRepository orgMemberRepository, OrgMemAndDepRepository orgMemAndDepRepository, ModelMapper modelMapper) {

        this.orgRepository = orgRepository;
        this.orgDepAndMemRepository = orgDepAndMemRepository;
            this.orgMemberRepository = orgMemberRepository;
            this.orgMemAndDepRepository = orgMemAndDepRepository;
            this.modelMapper = modelMapper;
    }



    /**
     * 전체부서 검색 메소드
     * @return
     */
    public List<OrgDepartmentDTO> selectAllOrgList(){

        List<OrgDepartment> orgDepartment = orgRepository.findByDepDeleteStatus("N"); //전체 중 depDeleteStatus = "N" 만 조회

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


        java.util.Date now = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        String depCreateDate = sdf.format(now); //생성일을 depCreateDate에 저장


        orgDepartmentDTO.setDepBirthDate(depCreateDate); //DTO에 생성일 저장
        orgDepartmentDTO.setDepDeleteStatus("N"); //삭제여부 기본값 N으로 저장

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


        List<OrgDepartmentAndOrgMember> orgDepartmentAndOrgMembers = orgDepAndMemRepository.findAllMemOfDep();

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

        log.info("[orgService] deleteOrgDep start------------");
        int result = 0;

        try {

            //부서 상태를 Y로 변경
            OrgDepartmentAndOrgMember deleteOrgDep = orgDepAndMemRepository.findById(orgDepartmentAndOrgMemberDTO.getDepCode()).get();

            System.out.println("deleteOrgDep = " + deleteOrgDep);

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

        log.info("[orgService] deleteOrgDep end------------");

        return (result > 0) ? "부서 삭제 성공" : "부서 삭제 실패";
    }


    /**
     * 부서 개별 상세 조회 메소드
     * @param depCode
     * @return
     */
    public OrgDepartmentAndOrgMemberDTO selectDepDetail(int depCode) {

        log.info("[orgService] selectDepDetail start----------------");

        OrgDepartmentAndOrgMember odam = orgDepAndMemRepository.findById(depCode).get();
        //get() 메소드로 옵셔널 타입의 객체 꺼내기

        System.out.println("odam = " + odam);

        OrgDepartmentAndOrgMemberDTO odamDTO = modelMapper.map(odam, OrgDepartmentAndOrgMemberDTO.class);
        //map() 메소드를 통해 odam의 각 필드를 OrgDepartmentAndOrgMemberDTO의 필드로 매핑한 결과를 odamDTO에 담아준다.

        System.out.println("odamDTO = " + odamDTO);

        log.info("[orgService] selectDepDetail end ----------------");
        return odamDTO;
    }



    /**
     * 멤버 전체 조회(페이징) 메소드
     * @param cri
     * @return
     */
    public Page<OrgMemAndOrgDepDTO> selectAllOrgMemberListWithPaging(Criteria cri) {

        int index = cri.getPageNum() - 1;
        int count = cri.getAmount();

        Pageable paging = PageRequest.of(index, count);

        Page<OrgMemAndOrgDep> result = orgMemAndDepRepository.findAll(paging);

        //엔티티->DTO로 변환
        Page<OrgMemAndOrgDepDTO> memberList = result.map(a -> modelMapper.map(a, OrgMemAndOrgDepDTO.class));


        return memberList;
    }

    /**
     * 부서의 멤버 업데이트 메소드
     * @param depCode
     * @param selectedMemberCodes
     * @return
     */
    @Transactional
    public Object insertMember(int depCode, List<Integer> selectedMemberCodes) {

        log.info("[OrgService] insertMember start");

        //List<Integer>를 통해 사용자로부터 입력받은 여러개의 멤버 코드를 받아옴(컨트롤러에서 리퀘스트바디를 통해 DTO 받아준 것)
        //받은 멤버 코드 기반으로 조회하고,
        //조회한 멤버의 부서 코드를 현재 부서로 변경(업데이트)

        //현재 부서 상세 조회
        OrgDepartmentAndOrgMember odam = orgDepAndMemRepository.findById(depCode).get();

        log.info("[OrgService] odam : {}", odam);

        //현재 부서의 모든 멤버 조회
        List<OrgMember> currentMembers = odam.getMemberList();

        log.info("[OrgService] currentMembers : {}", currentMembers);

        List<OrgMember> removedMembers = currentMembers.stream()
                .filter(member -> !selectedMemberCodes.contains(member.getMemCode()))
                .collect(Collectors.toList());

        log.info("[OrgService] removedMembers : {}", removedMembers);

        removedMembers.forEach(member -> {
            member.setOrgDepAndOrgMem(null);
            orgMemberRepository.save(member);
        });

        List<OrgMember> selectedMembers = orgMemberRepository.findAllById(selectedMemberCodes);
        selectedMembers.forEach(member -> {
            member.setOrgDepAndOrgMem(odam);
            orgMemberRepository.save(member);
        });

        log.info("[OrgService] selectedMembers : {}", selectedMembers);

        log.info("[OrgService] insertMember End");

        return "";
    }


    @Transactional
    public Object updateRole(OrgMemAndOrgDepDTO orgMemAndOrgDepDTO) {

        log.info("[OrgService] : updateRole ---------시작" );

        //현재 부서의 중간관리자를 찾기
        List<OrgMemAndOrgDep> memberList = orgMemAndDepRepository.findByorgDepartmentDepCodeAndMemRole(orgMemAndOrgDepDTO.getDepCode(), "중간관리자");

        log.info("[OrgService] memberList {}: ", memberList);

        //현재(선택한) 멤버 정보 조회
        OrgMemAndOrgDep selectedMember = orgMemAndDepRepository.findById(orgMemAndOrgDepDTO.getMemCode()).get();

        log.info("[OrgService] selectedMember {}: ", selectedMember);

        //선택한 멤버의 롤이 "중간관리자"인지 검사
        if("중간관리자".equals(selectedMember.getMemRole())){
            return "현재 해당 부서의 팀장입니다.";
        }

        //기존 중간관리자를 일반사원으로 업데이트
        //(memberList 리스트를 forEach를 통해 선택한 멤버의 코드와 다른 경우 일반사원으로 셋)
        memberList.forEach(a -> {
            if(a.getMemCode() != selectedMember.getMemCode()){
                a.setMemRole("일반사원");
                orgMemAndDepRepository.save(a);
            }
        });

        //선택한 멤버를 중간관리자로 업데이트
        selectedMember.setMemRole("중간관리자");
        orgMemAndDepRepository.save(selectedMember);

        log.info("[OrgService] : updateRole ---------끝" );

        return "팀장을 지정하였습니다.";


    }
}

