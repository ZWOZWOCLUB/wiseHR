package com.wisehr.wisehr.organization.service;

import com.wisehr.wisehr.common.Criteria;
import com.wisehr.wisehr.organization.dto.*;
import com.wisehr.wisehr.organization.entity.*;
import com.wisehr.wisehr.organization.repository.*;
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
    private final OrgTreeRepository orgTreeRepository;
    private final OrgTreeMemRepository orgTreeMemRepository;
    private final ModelMapper modelMapper;

        public OrgService(OrgRepository orgRepository, OrgDepAndMemRepository orgDepAndMemRepository, OrgMemberRepository orgMemberRepository, OrgMemAndDepRepository orgMemAndDepRepository, OrgTreeRepository orgTreeRepository, OrgTreeMemRepository orgTreeMemRepository, ModelMapper modelMapper) {

        this.orgRepository = orgRepository;
        this.orgDepAndMemRepository = orgDepAndMemRepository;
            this.orgMemberRepository = orgMemberRepository;
            this.orgMemAndDepRepository = orgMemAndDepRepository;
            this.orgTreeRepository = orgTreeRepository;
            this.orgTreeMemRepository = orgTreeMemRepository;
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
//        modifyOrgDep = modifyOrgDep.depName(orgDepartmentDTO.getDepName()).build();
        modifyOrgDep.setDepName(orgDepartmentDTO.getDepName());

        //상위부서 코드가 null인 경우 null로 설정하고 그렇지 않은 경우 refDepCode 사용
        if(orgDepartmentDTO.getRefDepCode() == null){
            modifyOrgDep.setRefDepCode(null);
        }else{
            modifyOrgDep.setRefDepCode(orgDepartmentDTO.getRefDepCode());
        }

        orgRepository.save(modifyOrgDep);

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

        /*
        * List<Integer>를 통해 사용자로부터 입력받은 여러개의 멤버 코드를 받아옴(컨트롤러에서 리퀘스트바디를 통해 DTO 받아준 것)
        * 받은 멤버 코드 기반으로 조회하고,
        * 조회한 멤버의 부서 코드를 현재 부서로 변경(업데이트)
        * */

        //현재 부서 상세 조회
        OrgDepartmentAndOrgMember odam = orgDepAndMemRepository.findById(depCode).get();

        log.info("[OrgService] odam : {}", odam);

        //현재 부서의 모든 멤버 조회
        List<OrgMember> currentMembers = odam.getMemberList();

        log.info("[OrgService] currentMembers : {}", currentMembers);

        //위의 조회한 결과(currentMembers)에서 selectedMemberCodes 가 없는 멤버들만 필터링
        List<OrgMember> removedMembers = currentMembers.stream()
                .filter(member -> !selectedMemberCodes.contains(member.getMemCode()))
                .collect(Collectors.toList());

        log.info("[OrgService] removedMembers : {}", removedMembers);

        //그 멤버들은 부서코드 null로 업데이트(즉, 부서에서 제외)
        removedMembers.forEach(member -> {
            member.setOrgDepAndOrgMem(null);
            orgMemberRepository.save(member);
        });
        //selectedMemberCodes(사용자가 선택한 멤버들)의 부서를 현재 부서로 업데이트
        List<OrgMember> selectedMembers = orgMemberRepository.findAllById(selectedMemberCodes);
        selectedMembers.forEach(member -> {
            member.setOrgDepAndOrgMem(odam);
//            member.setMemRole("USER"); //중간관리자는 부서에 1명이어야 하는데, 수정하는 과정에서 여러명이 될 수 있으므로 일반으로 초기화
            orgMemberRepository.save(member);
        });

        log.info("[OrgService] selectedMembers : {}", selectedMembers);

        log.info("[OrgService] insertMember End");

        return "멤버 추가 완료";
    }

//    /**
//     * 부서에 중간관리자 지정 메소드
//     * @param orgMemAndOrgDepDTO
//     * @return
//     */
//
//    @Transactional
//    public Object updateRole(OrgMemAndOrgDepDTO orgMemAndOrgDepDTO) {
//
//        log.info("[OrgService] : updateRole ---------시작" );
//
//        //현재 부서의 중간관리자를 찾기
//        List<OrgMemAndOrgDep> memberList = orgMemAndDepRepository.findByorgDepartmentDepCodeAndMemRole(orgMemAndOrgDepDTO.getDepCode(), "ADMIN");
//
//        log.info("[OrgService] memberList {}: ", memberList);
//
//        //현재(사용자가 선택한) 멤버 정보 조회
//        OrgMemAndOrgDep selectedMember = orgMemAndDepRepository.findById(orgMemAndOrgDepDTO.getMemCode()).get();
//
//        log.info("[OrgService] selectedMember {}: ", selectedMember);
//
//        //선택한 멤버의 롤이 "중간관리자"인지 검사
//        if("ADMIN".equals(selectedMember.getMemRole())){
//            return "현재 해당 부서의 팀장입니다.";
//        }
//
//        //기존 중간관리자를 일반사원으로 업데이트
//        //(memberList 리스트를 forEach를 통해 선택한 멤버의 코드와 다른 경우 일반사원으로 셋)
//        memberList.forEach(a -> {
//            if(a.getMemCode() != selectedMember.getMemCode()){
//                a.setMemRole("USER");
//                orgMemAndDepRepository.save(a);
//            }
//        });
//
//        //선택한 멤버를 중간관리자로 업데이트
//        selectedMember.setMemRole("ADMIN");
//        orgMemAndDepRepository.save(selectedMember);
//
//        log.info("[OrgService] : updateRole ---------끝" );
//
//        return "팀장을 지정하였습니다.";
//
//
//    }

    /**
     * 멤버 권한 설정
     * @param orgMemAndOrgDepDTO
     * @return
     */
    @Transactional
    public Object updateMemberRole(OrgMemAndOrgDepDTO orgMemAndOrgDepDTO){

        log.info("[OrgService]: updateMemberRole 시작");

        OrgMemAndOrgDep updateMember = orgMemAndDepRepository.findById(orgMemAndOrgDepDTO.getMemCode()).get();

        updateMember.setMemRole(orgMemAndOrgDepDTO.getMemRole());
        orgMemAndDepRepository.save(updateMember);

        log.info("[OrgService]: updateMemberRole 종료");

        return "멤버 권한 업데이트 성공";
    }


    public TreeDepDTO showTreeView() {

        //최상위부서 목록 조회
        List<TreeDepDTO> topDep = orgTreeRepository.findTopDep();
        System.out.println("topDep = " + topDep);
        //최상위부서 목록이 비어있지 않은 경우
        if(!topDep.isEmpty()){
            TreeDepDTO rootDep = topDep.get(0); //리스트의 첫번째요소를 최상위 부서로 선택
            rootDep = subDepAndMemberList(rootDep); //subDepAndMemberList 메서드로 하위, 멤버 트리 구조 구성
            System.out.println("rootDep = " + rootDep);
            return rootDep;
        }
        return null; //최상위부서가 없다면 null
    }

    private TreeDepDTO subDepAndMemberList(TreeDepDTO treeDepDTO) {

        Integer depCode = treeDepDTO.getDepCode();
        System.out.println("depCode = " + depCode);

        List<TreeDepDTO> subDep = orgTreeRepository.findSubDep(treeDepDTO.getDepCode());
        System.out.println("subDep = " + subDep);

        subDep.forEach(this::subDepAndMemberList);
        treeDepDTO.setChildren(subDep);

        List<TreeMemDTO> memberList = orgTreeMemRepository.findMembersByDepartment(treeDepDTO.getDepCode());
        treeDepDTO.setMemberList(memberList);
        System.out.println("memberList = " + memberList);

        return treeDepDTO;
    }


    public List<OrgMemAndOrgDepDTO> searchMemberName(String search) {

        log.info("[OrgService] : searchMemberName ---------시작" );
        log.info("searchMemberName: {}", search);

        List<OrgMemAndOrgDep> orgMemSearchValue = orgMemAndDepRepository.findByMemNameContaining(search);
        List<OrgMemAndOrgDepDTO> orgSearchMemberList = orgMemSearchValue.stream()
                        .map(a -> modelMapper.map(a, OrgMemAndOrgDepDTO.class))
                                .collect(Collectors.toList());
        log.info("searchMemberName: {}", orgMemSearchValue);
        log.info("searchMemberName: {}", orgSearchMemberList);

        log.info("[OrgService] : searchMemberName ---------끝" );

        return orgSearchMemberList;
    }

//    public List<OrgMemAndOrgDepDTO> searchDepName(String search) {
//        log.info("[OrgService] : searchDepName ---------시작" );
//        log.info("searchDepName: {}", search);
//
//
//
//        log.info("[OrgService] : searchDepName ---------끝" );
//
//        return null;
//
//    }
}

