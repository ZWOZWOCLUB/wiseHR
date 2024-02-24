package com.wisehr.wisehr.schedule.service;

import com.wisehr.wisehr.organization.dto.OrgMemAndOrgDepDTO;
import com.wisehr.wisehr.organization.dto.TreeDepDTO;
import com.wisehr.wisehr.organization.dto.TreeMemDTO;
import com.wisehr.wisehr.organization.entity.OrgMemAndOrgDep;
import com.wisehr.wisehr.organization.repository.OrgMemAndDepRepository;
import com.wisehr.wisehr.organization.repository.OrgTreeMemRepository;
import com.wisehr.wisehr.organization.repository.OrgTreeRepository;
import com.wisehr.wisehr.schedule.dto.*;
import com.wisehr.wisehr.schedule.entity.*;
import com.wisehr.wisehr.schedule.repository.*;
import com.wisehr.wisehr.setting.dto.SettingMemberDTO;
import com.wisehr.wisehr.setting.entity.SettingMember;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ScheduleService {

    private final ModelMapper modelMapper;
    private final ScheduleAttendanceRepository attendanceRepository;
    private final ScheduleWorkPatternRepository workPatternRepository;
    private final ScheduleRepository scheduleRepository;
    private final SchedulePatternDayRepository patternDayRepository;
    private final ScheduleEtcPatternRepository etcPatternRepository;
    private final ScheduleAllowanceRepository allowanceRepository;
    private final ScheduleAllSelectRepository allSelectRepository;
    private final ScheduleInsertPatternDayRepository insertPatternDayRepository;
    private final ScheduleInsertAllowanceRepository insertAllowanceRepository;
    private final ScheduleCountDepCodeRepository countDepCodeRepository;
    private final ScheduleMemSchRepository memSchRepository;

    private final OrgMemAndDepRepository orgMemAndDepRepository;
    private final OrgTreeRepository orgTreeRepository;
    private final OrgTreeMemRepository orgTreeMemRepository;


    public ScheduleService(ModelMapper modelMapper, ScheduleAttendanceRepository scheduleAttendanceRepository, ScheduleWorkPatternRepository scheduleWorkPatternRepository, ScheduleRepository scheduleRepository, SchedulePatternDayRepository patternDayRepository, ScheduleEtcPatternRepository etcPatternRepository, ScheduleAllowanceRepository allowanceRepository, ScheduleAllSelectRepository allSelectRepository, ScheduleInsertPatternDayRepository insertPatternDayRepository, ScheduleInsertAllowanceRepository insertAllowanceRepository, ScheduleCountDepCodeRepository countDepCodeRepository, ScheduleMemSchRepository memSchRepository, OrgMemAndDepRepository orgMemAndDepRepository, OrgTreeRepository orgTreeRepository, OrgTreeMemRepository orgTreeMemRepository) {
        this.modelMapper = modelMapper;
        this.attendanceRepository = scheduleAttendanceRepository;
        this.workPatternRepository = scheduleWorkPatternRepository;
        this.scheduleRepository = scheduleRepository;
        this.patternDayRepository = patternDayRepository;
        this.etcPatternRepository = etcPatternRepository;
        this.allowanceRepository = allowanceRepository;
        this.allSelectRepository = allSelectRepository;
        this.insertPatternDayRepository = insertPatternDayRepository;
        this.insertAllowanceRepository = insertAllowanceRepository;
        this.countDepCodeRepository = countDepCodeRepository;

        this.memSchRepository = memSchRepository;
        this.orgMemAndDepRepository = orgMemAndDepRepository;
        this.orgTreeRepository = orgTreeRepository;
        this.orgTreeMemRepository = orgTreeMemRepository;
    }


    public List<ScheduleAllSelectDTO> searchMonth(ScheduleSearchValueDTO value) {
        log.info("searchDate 서비스 시작~~~~~~~~~~~~");
        log.info("searchDate 서비스 시작~~~~~~~~~~~~", value);
        System.out.println("value = " + value);

        if(!value.getMemberCode().isEmpty()) {


                List<ScheduleAllSelect> allSelect = allSelectRepository.findByYearMonthAndMemCode(value.getMemberCode(), value.getYearMonth());
            List<ScheduleAllSelectDTO> selectDTOList = allSelect.stream()
                    .map(list -> modelMapper.map(list, ScheduleAllSelectDTO.class))
                    .collect(Collectors.toList());
            log.info("searchDate 서비스 끗~~~~~~~~~~~~");

            return selectDTOList;

        }else{
        String yearMonth = value.getYearMonth();
        log.info("yearMonth : " + yearMonth);


        List<ScheduleAllSelect> allSelect = allSelectRepository.findByYearMonth(yearMonth);
        log.info("allselect : " + allSelect);
        List<ScheduleAllSelectDTO> selectDTOList = allSelect.stream()
                .map(list -> modelMapper.map(list, ScheduleAllSelectDTO.class))
                .collect(Collectors.toList());
        System.out.println("selectDTOList = " + selectDTOList);
        log.info("searchDate 서비스 끗~~~~~~~~~~~~");
        System.out.println("selectDTOList = " + selectDTOList);

        return selectDTOList;
    }

    }

    @Transactional
    public List<ScheduleDTO> insertSchedule(ScheduleInsertDTO insertDTO) {
        log.info("insertSchedule Start~~~~~~~~~~~~");
        log.info((insertDTO.toString()));
        try {

        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setWokCode(insertDTO.getWokCode());
        scheduleDTO.setSchColor(insertDTO.getSchColor());
        scheduleDTO.setSchStartDate(insertDTO.getSchStartDate());
        scheduleDTO.setSchEndDate(insertDTO.getSchEndDate());
        scheduleDTO.setSchDeleteStatus(insertDTO.getSchDeleteStatus());
        scheduleDTO.setSchType(insertDTO.getSchType());

        List<SchedulePatternDayDTO> patternDayDTOList = new ArrayList<>();
        for (int i = 0; i < insertDTO.getDayCode().size(); i++) {
            int dayCode = Integer.parseInt(insertDTO.getDayCode().get(i));

            SchedulePatternDayDTO patternDayDTO = new SchedulePatternDayDTO();
            patternDayDTO.setDayCode(dayCode +1);
            patternDayDTO.setWokCode(insertDTO.getWokCode());

            System.out.println("dayCode = " + dayCode);
            ScheduleInsertPatternDay patternDay = modelMapper.map(patternDayDTO, ScheduleInsertPatternDay.class);

            ScheduleInsertPatternDay result = insertPatternDayRepository.save(patternDay);
            System.out.println("patternDayresult = " + result);

        }
            Schedule schedule = modelMapper.map(scheduleDTO, Schedule.class);
            Schedule insertScheduleResult = scheduleRepository.save(schedule);
            System.out.println("insertScheduleResult = " + insertScheduleResult);

            if(insertDTO.getMemCode().size() > 0) {

                List<ScheduleAllowanceDTO> scheduleInsertAllowance = new ArrayList<>();
                for (int i = 0; i < insertDTO.getMemCode().size(); i++) {
                    int memCode = Integer.parseInt(insertDTO.getMemCode().get(i));
                    ScheduleAllowanceDTO allowance = new ScheduleAllowanceDTO();
                    allowance.setSchCode(insertScheduleResult.getSchCode());
                    allowance.setMemCode(memCode);

                    scheduleInsertAllowance.add(allowance);
                }

                List<ScheduleInsertAllowance> allowances = scheduleInsertAllowance.stream()
                        .map(list -> modelMapper.map(list, ScheduleInsertAllowance.class))
                        .collect(Collectors.toList());


                    List<ScheduleInsertAllowance> insertResult = insertAllowanceRepository.saveAll(allowances);
            }

        } catch (Exception e) {
            log.info("일정 패턴 등록 오류");
            throw new RuntimeException(e);
        }

        List<Schedule> result1 = scheduleRepository.findAll();

        List<ScheduleDTO> result = result1.stream()
                .map(list -> modelMapper.map(list, ScheduleDTO.class))
                .collect(Collectors.toList());

        log.info("insertWorkPattern 끗~~~~~~~~~~~~");
        return result;

    }


    @Transactional
    public List<ScheduleWorkPatternDTO>  insertWorkPattern(ScheduleWorkPatternDTO patternDTO) {
        log.info("insertWorkPattern Start~~~~~~~~~~~~");
        log.info(patternDTO.toString());
        int result = 0;

        List<ScheduleWorkPatternDTO> list = new ArrayList<>();

        try {
            ScheduleWorkPattern insertWorkPattern = modelMapper.map(patternDTO, ScheduleWorkPattern.class);

            ScheduleWorkPattern insertResult = workPatternRepository.save(insertWorkPattern);
            System.out.println("insertResult = " + insertResult);


        } catch (Exception e) {
            log.info("오류~~~~~~~");
            throw new RuntimeException(e);
        }
        List<ScheduleWorkPattern> findAll = workPatternRepository.findAll();
        list = findAll.stream()
                .map(resultList -> modelMapper.map(resultList, ScheduleWorkPatternDTO.class))
                .collect(Collectors.toList());

        return list;
    }

    @Transactional
    public List<ScheduleWorkPatternDTO> updateWorkPattern(ScheduleWorkPatternDTO patternDTO) {
        log.info("updateWorkPattern Start~~~~~~~~~~~~");
        log.info(patternDTO.toString());

        int result = 0;

        try {
            ScheduleWorkPattern pattern = workPatternRepository.findById(patternDTO.getWokCode()).get();

            if (patternDTO.getWokDeleteState().equals("N")) {
                pattern = pattern.wokStartTime(patternDTO.getWokStartTime())
                        .wokEndTime(patternDTO.getWokEndTime())
                        .wokRestTime(patternDTO.getWokRestTime())
                        .wokColor(patternDTO.getWokColor())
                        .wokType(patternDTO.getWokType())
                        .wokDeleteState(patternDTO.getWokDeleteState()).build();
            }else {
                System.out.println("여기~~~~~~");
                pattern = pattern.wokDeleteState("Y").build();
            }


            result = 1;

        } catch (Exception e) {
            log.info("오류~~~~~~~");
            throw new RuntimeException(e);
        }
        List<ScheduleWorkPattern> workPattern = workPatternRepository.findAll();

        List<ScheduleWorkPatternDTO> resultDTO = workPattern.stream()
                .map(resultList -> modelMapper.map(resultList, ScheduleWorkPatternDTO.class))
                .collect(Collectors.toList());

        return resultDTO;

    }

    @Transactional
    public List<ScheduleDTO> updateSchedule(ScheduleInsertDTO insertDTO) {
        log.info("updateSchedule Start~~~~~~~~~~~~");
        log.info(insertDTO.toString());
        int result = 0;
        try {

            if(!insertDTO.getSchDeleteStatus().equals("Y")) {
                Schedule schedule = scheduleRepository.findById(insertDTO.getSchCode()).get();
                System.out.println("schedule = " + schedule);

                schedule = schedule.schType(insertDTO.getSchType())
                        .schStartDate(insertDTO.getSchStartDate())
                        .schEndDate(insertDTO.getSchEndDate())
                        .schColor(insertDTO.getSchColor())
                        .schDeleteStatus(insertDTO.getSchDeleteStatus())
                        .wokCode(insertDTO.getWokCode())
                        .build();
                List<SchedulePatternDayDTO> patternDayDTO = new ArrayList<>();

                System.out.println("update schedule");
                if (!insertDTO.getDayCode().isEmpty()) {
                    for(int j = 0; j < insertDTO.getPrevDayCode().size(); j++){
                        int prevDayCode = Integer.parseInt(insertDTO.getPrevDayCode().get(j));
                        int prevWokCode = Integer.parseInt(insertDTO.getPrevWokCode().get(j));

                        ScheduleInsertPatternDay pattern = insertPatternDayRepository.findByDayCodeAndWokCode(prevDayCode, prevWokCode);
                        System.out.println("pattern = " + pattern);

                        insertPatternDayRepository.delete(pattern);
                        log.info("-----------------------------");
                        System.out.println("delete pattern");
                    }
                    for (int i = 0; i < insertDTO.getDayCode().size(); i++) {

                        SchedulePatternDayDTO schedulePatternDayDTO = new SchedulePatternDayDTO();

                        schedulePatternDayDTO.setWokCode(insertDTO.getWokCode());
                        schedulePatternDayDTO.setDayCode(Integer.parseInt(insertDTO.getDayCode().get(i)) + 1);

                        patternDayDTO.add(schedulePatternDayDTO);
                        System.out.println("add pattern");

                    }
                    System.out.println("patternDay -----------------------------patternDay");

                    List<ScheduleInsertPatternDay> patternDay = patternDayDTO.stream()
                            .map(pattern -> modelMapper.map(pattern, ScheduleInsertPatternDay.class))
                            .collect(Collectors.toList());
                    System.out.println("patternDay = " + patternDay);

                    List<ScheduleInsertPatternDay> insertPatternDayResult = insertPatternDayRepository.saveAll(patternDay);
                    log.info("insertPatternDayResult = " + insertPatternDayResult);



                }
                System.out.println("insertDTO.getMemCode().size() = " + insertDTO.getMemCode().size());

                if (insertDTO.getMemCode().size() > 0) {
                    List<ScheduleInsertAllowance> allowance = insertAllowanceRepository.findBySchCode(insertDTO.getSchCode());

                    insertAllowanceRepository.deleteAll(allowance);
                    System.out.println("deleteAll allowance");

                    for (int i = 0; i < insertDTO.getMemCode().size(); i++) {
                        int memCode = Integer.parseInt(insertDTO.getMemCode().get(i));
                        String SchCode = insertDTO.getSchCode();
                        ScheduleInsertAllowance allowance1 = new ScheduleInsertAllowance();
                        allowance1.setSchCode(SchCode);
                        allowance1.setMemCode(memCode);
                        ScheduleInsertAllowance insertAllowance = insertAllowanceRepository.save(allowance1);
                        System.out.println("save ScheduleInsertAllowance");

                    }
                }
            }else {
                Schedule schedule = scheduleRepository.findById(insertDTO.getSchCode()).get();
                System.out.println("삭제");

                System.out.println("delete = " + schedule);
                schedule = schedule.schDeleteStatus("Y")
                        .build();
                System.out.println("삭제2");

                List<ScheduleInsertPatternDay> patternDay = insertPatternDayRepository.findByWokCode(schedule.getWokCode());
                System.out.println("삭제3");
                for(int i =0; i < patternDay.size(); i++) {
                    insertPatternDayRepository.deleteByWokCode(patternDay.get(i).getWokCode());
                    System.out.println("삭43");
                }

            }
            result = 1;
        } catch (Exception e) {
            log.info("오류~~~~~~~");
            throw new RuntimeException(e);
        }

        List<Schedule> schedules = scheduleRepository.findAll();
        List<ScheduleDTO> resultDTO = schedules.stream()
                .map(resultList -> modelMapper.map(resultList, ScheduleDTO.class))
                .collect(Collectors.toList());

        return resultDTO;
    }

    @Transactional
    public String insertEtcPattern(ScheduleEtcPatternDTO etcPatternDTO) {
        log.info("insertEtcPattern Start~~~~~~~~~~~~");
        log.info(etcPatternDTO.toString());

        int result = 0;

        try {
            ScheduleEtcPattern insertEtcPattern = modelMapper.map(etcPatternDTO, ScheduleEtcPattern.class);

            ScheduleEtcPattern insertResult = etcPatternRepository.save(insertEtcPattern);
            System.out.println("insertResult = " + insertResult);

            result = 1;

        } catch (Exception e) {
            log.info("오류~~~~~~~");
            throw new RuntimeException(e);
        }

        return (result > 0) ? "등록 성공" : "등록 실패";
    }

    @Transactional
    public String updateEtcPattern(ScheduleEtcPatternDTO etcPatternDTO) {
        log.info("updateEtcPattern Start~~~~~~~~~~~~");
        log.info(etcPatternDTO.toString());

        int result = 0;

        try {
            if (!"10".equals(etcPatternDTO.getEtcKind())) {
                ScheduleEtcPattern pattern = etcPatternRepository.findById(etcPatternDTO.getEtcCode()).get();

                pattern = pattern.etcDate(etcPatternDTO.getEtcDate())
                        .etcKind(etcPatternDTO.getEtcKind()).build();
            } else {
                ScheduleEtcPattern pattern = etcPatternRepository.findById(etcPatternDTO.getEtcCode()).get();

                etcPatternRepository.delete(pattern);
            }


            result = 1;

        } catch (Exception e) {
            log.info("오류~~~~~~~");
            throw new RuntimeException(e);
        }

        return (result > 0) ? "수정 성공" : "수정 실패";
    }

    @Transactional
    public List<ScheduleAllowanceDTO> insertScheduleAllowance(ScheduleAllowanceDTO allowanceDTO) {
        log.info("insertScheduleAllowance Start~~~~~~~~~~~~");
        log.info(allowanceDTO.toString());

        int result = 0;

        try {
            ScheduleInsertAllowance insertAllowance = modelMapper.map(allowanceDTO, ScheduleInsertAllowance.class);

            ScheduleInsertAllowance insertResult = insertAllowanceRepository.save(insertAllowance);
            System.out.println("insertResult = " + insertResult);

            result = 1;

        } catch (Exception e) {
            log.info("오류~~~~~~~");
            throw new RuntimeException(e);
        }

        List<ScheduleInsertAllowance> allowances = insertAllowanceRepository.findAll();

        List<ScheduleAllowanceDTO> resultDTO = allowances.stream()
                .map(resultList -> modelMapper.map(resultList, ScheduleAllowanceDTO.class))
                .collect(Collectors.toList());

        return resultDTO;

    }

    @Transactional
    public List<ScheduleAllowanceDTO> updateScheduleAllowance(ScheduleAllowanceDTO allowanceDTO) {
        log.info("updateScheduleAllowance Start~~~~~~~~~~~~");
        log.info(allowanceDTO.toString());

        int result = 0;

        try {
            ScheduleInsertAllowance allowance = insertAllowanceRepository.findByMemCodeAndSchCode(allowanceDTO.getMemCode(), allowanceDTO.getSchCode());

            System.out.println("allowance = " + allowance);
            insertAllowanceRepository.delete(allowance);
            log.info("-------------------------------------------");
            String schCode = allowanceDTO.getChangeSchCode();
            allowanceDTO.setSchCode(allowanceDTO.getChangeSchCode());

            ScheduleInsertAllowance insertAllowance = modelMapper.map(allowanceDTO, ScheduleInsertAllowance.class);

            ScheduleInsertAllowance insertResult = insertAllowanceRepository.save(insertAllowance);
            System.out.println("insertResult = " + insertResult);


            result = 1;

        } catch (Exception e) {
            log.info("오류~~~~~~~");
            throw new RuntimeException(e);
        }

        List<ScheduleInsertAllowance> allowances = insertAllowanceRepository.findAll();

        List<ScheduleAllowanceDTO> resultDTO = allowances.stream()
                .map(resultList -> modelMapper.map(resultList, ScheduleAllowanceDTO.class))
                .collect(Collectors.toList());

        return resultDTO;
    }

    public List<ScheduleAllSelectDTO> searchValue(ScheduleSearchValueDTO valueDTO) {
        log.info("searchValue 시작~~~~~~~~~~");

        log.info("날짜로 조회~~~~~~~~~");
        String yearMonth = valueDTO.getYearMonth();
        String notContain = valueDTO.getNotContain();

        List<ScheduleAllSelect> allSelects = allSelectRepository.findByYearMonthDate(yearMonth, notContain);

        List<ScheduleAllSelectDTO> list = allSelects.stream()
                .map(resultList -> modelMapper.map(resultList, ScheduleAllSelectDTO.class))
                .collect(Collectors.toList());
        System.out.println("list = " + list);
        log.info("searchValue 끝~~~~~~~~~~");

        return list;

    }

//        public List<ScheduleAllSelectDTO> notContain(ScheduleSearchValueDTO valueDTO) {
//            log.info("searchValue 시작~~~~~~~~~~");
//                List<ScheduleAllSelect> allSelects = allSelectRepository.findByYearMonthAndMemCodeNotContain(valueDTO.getNotContain());
//
//                List<ScheduleAllSelectDTO> list = allSelects.stream()
//                        .map(resultList -> modelMapper.map(resultList, ScheduleAllSelectDTO.class))
//                        .collect(Collectors.toList());
//
//                return list;
//
//
//    }

    public List<SearchCountDepCodeDTO> seachCountDepCode() {
        log.info("SearchCountDepCode 시작~~~~~~~~~~");

        List<SearchCountDepCode> result = countDepCodeRepository.findByCount();

        List<SearchCountDepCodeDTO> list = result.stream()
                .map(resultList -> modelMapper.map(resultList, SearchCountDepCodeDTO.class))
                .collect(Collectors.toList());

        return list;
    }

    public List<ScheduleAllSelectDTO> patternAndDaySearch() {

        log.info("patternAndDaySearch 시작~~~~~~~");


        List<ScheduleAllSelect> allSelect = allSelectRepository.findByAll();
        log.info("allselect : " + allSelect);


        List<ScheduleAllSelectDTO> selectDTOList = allSelect.stream()
                .map(list -> modelMapper.map(list, ScheduleAllSelectDTO.class))
                .collect(Collectors.toList());



        System.out.println("selectDTOList = " + selectDTOList);


        log.info("patternAndDaySearch 끝~~~~~~~");
        return selectDTOList;
    }

    public List<ScheduleWorkPatternDTO> patternSearch() {

        log.info("patternSearch 시작~~~~~~~");


        List<ScheduleWorkPattern> patterns = workPatternRepository.findByWokDeleteState("N");
        log.info("patterns : " + patterns);

        List<ScheduleWorkPatternDTO> selectDTOList = patterns.stream()
                .map(list -> modelMapper.map(list, ScheduleWorkPatternDTO.class))
                .collect(Collectors.toList());
        System.out.println("selectDTOList = " + selectDTOList);


        log.info("patternSearch 끝~~~~~~~");
        return selectDTOList;
    }

    public List<ScheduleEtcPatternDTO> etcPatternSearch() {
            log.info("etcPatternSearch 시작~~~~~~~~~~");

            List<ScheduleEtcPattern> etcPatternList = etcPatternRepository.findAll();
        System.out.println("etcPatternList = " + etcPatternList);
            List<ScheduleEtcPatternDTO> list = etcPatternList.stream()
                    .map(resultList -> modelMapper.map(resultList, ScheduleEtcPatternDTO.class))
                    .collect(Collectors.toList());
            System.out.println("list = " + list);


            log.info("etcPatternSearch 끝~~~~~~~~~~");

            return list;
    }

    public List<ScheduleMemSchDTO> notContain(ScheduleSearchValueDTO valueDTO) {
        log.info("notContain 시작~~~~~~~~~~");

        String notContain = valueDTO.getNotContain();

        List<ScheduleMemSch> allSelects = memSchRepository.findByYearMonthNotContain(notContain);

        List<ScheduleMemSchDTO> list = allSelects.stream()
                .map(resultList -> modelMapper.map(resultList, ScheduleMemSchDTO.class))
                .collect(Collectors.toList());
        System.out.println("list = " + list);
        log.info("notContain 끝~~~~~~~~~~");

        return list;

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

//        List<TreeMemDTO> memberList = orgTreeMemRepository.findMembersByDepartment(treeDepDTO.getDepCode());
        List<TreeMemDTO> memberList = orgTreeMemRepository.findMembersByDepartmentNotContainSchedule(treeDepDTO.getDepCode());

        treeDepDTO.setMemberList(memberList);
        System.out.println("memberList = " + memberList);

        return treeDepDTO;
    }



}