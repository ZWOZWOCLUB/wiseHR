package com.wisehr.wisehr.schedule.service;

import com.wisehr.wisehr.schedule.dto.*;
import com.wisehr.wisehr.schedule.entity.*;
import com.wisehr.wisehr.schedule.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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



    public ScheduleService(ModelMapper modelMapper, ScheduleAttendanceRepository scheduleAttendanceRepository, ScheduleWorkPatternRepository scheduleWorkPatternRepository, ScheduleRepository scheduleRepository, SchedulePatternDayRepository patternDayRepository, ScheduleEtcPatternRepository etcPatternRepository, ScheduleAllowanceRepository allowanceRepository, ScheduleAllSelectRepository allSelectRepository, ScheduleInsertPatternDayRepository insertPatternDayRepository, ScheduleInsertAllowanceRepository insertAllowanceRepository) {
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
    }


    public List<ScheduleAllSelectDTO> searchMonth(String yearMonth) {
        log.info("searchDate 서비스 시작~~~~~~~~~~~~");


        List<ScheduleAllSelect> allSelect = allSelectRepository.findByYearMonth(yearMonth);
        log.info("allselect : " + allSelect);

        List<ScheduleAllSelectDTO> selectDTOList = allSelect.stream()
                        .map(list -> modelMapper.map(list, ScheduleAllSelectDTO.class))
                        .collect(Collectors.toList());
        System.out.println("selectDTOList = " + selectDTOList);

        log.info("searchDate 서비스 끗~~~~~~~~~~~~");

        return selectDTOList;
    }

    @Transactional
    public String insertSchedule(ScheduleInsertDTO insertDTO) {
        log.info("insertSchedule Start~~~~~~~~~~~~");
        log.info((insertDTO.toString()));
        ScheduleDTO scheduleDTO = insertDTO.getScheduleDTO();
        List<SchedulePatternDayDTO> patternDayDTO = insertDTO.getPatternDayDTO();

        int result = 0;
        try {


            scheduleDTO.setWokCode(insertDTO.getScheduleDTO().getWokCode());
            Schedule schedule = modelMapper.map(scheduleDTO, Schedule.class);
            Schedule insertScheduleResult = scheduleRepository.save(schedule);
            System.out.println("insertScheduleResult = " + insertScheduleResult);
            result++;


            System.out.println("patternDayDTO = " + patternDayDTO);
            for (SchedulePatternDayDTO pattern: patternDayDTO) {
                pattern.setWokCode(insertDTO.getScheduleDTO().getWokCode());
                System.out.println("pattern = " + pattern);

            }
            List<ScheduleInsertPatternDay> patternDay = patternDayDTO.stream()
                    .map(pattern -> modelMapper.map(pattern, ScheduleInsertPatternDay.class))
                    .collect(Collectors.toList());



            List<ScheduleInsertPatternDay> insertPatternDayResult = insertPatternDayRepository.saveAll(patternDay);
            log.info("insertPatternDayResult = " + insertPatternDayResult);

            result++;
        } catch (Exception e) {
            log.info("일정 패턴 등록 오류");
            throw new RuntimeException(e);
        }


        log.info("insertWorkPattern 끗~~~~~~~~~~~~");
        return (result > 1)? "등록 성공": "등록 실패";
    }


    @Transactional
    public String insertWorkPattern(ScheduleWorkPatternDTO patternDTO) {
        log.info("insertWorkPattern Start~~~~~~~~~~~~");
        log.info(patternDTO.toString());

        int result = 0;

        try{
            ScheduleWorkPattern insertWorkPattern = modelMapper.map(patternDTO, ScheduleWorkPattern.class);

            ScheduleWorkPattern insertResult = workPatternRepository.save(insertWorkPattern);
            System.out.println("insertResult = " + insertResult);

            result = 1;

        }catch(Exception e) {
            log.info("오류~~~~~~~");
            throw new RuntimeException(e);
        }

        return (result > 0)? "등록 성공": "등록 실패";
    }

    @Transactional
    public String updateWorkPattern(ScheduleWorkPatternDTO patternDTO) {
        log.info("updateWorkPattern Start~~~~~~~~~~~~");
        log.info(patternDTO.toString());

        int result = 0;

        try{
            ScheduleWorkPattern pattern = workPatternRepository.findById(patternDTO.getWokCode()).get();

            pattern = pattern.wokStartTime(patternDTO.getWokStartTime())
                    .wokEndTime(patternDTO.getWokEndTime())
                    .wokRestTime(patternDTO.getWokRestTime())
                    .wokDeleteState(patternDTO.getWokDeleteState()).build();


            result = 1;

        }catch(Exception e) {
            log.info("오류~~~~~~~");
            throw new RuntimeException(e);
        }

        return (result > 0)? "수정 성공": "수정 실패";
    }

    @Transactional
    public String updateSchedule(ScheduleInsertDTO insertDTO) {
        log.info("updateSchedule Start~~~~~~~~~~~~");
        log.info(insertDTO.toString());
        ScheduleDTO scheduleDTO = insertDTO.getScheduleDTO();
        List<SchedulePatternDayDTO> patternDayDTO = insertDTO.getPatternDayDTO();

        int result = 0;

        try{
            Schedule schedule = scheduleRepository.findById(scheduleDTO.getSchCode()).get();

            schedule = schedule.schType(scheduleDTO.getSchType())
                    .schStartDate(scheduleDTO.getSchStartDate())
                    .schEndDate(scheduleDTO.getSchEndDate())
                    .schColor(scheduleDTO.getSchColor())
                    .schDeleteStatus(scheduleDTO.getSchDeleteStatus()).build();

            for (int i =0; i < patternDayDTO.size(); i++) {
                int dayCode = patternDayDTO.get(i).getDayCode();
                int wokCode = patternDayDTO.get(i).getWokCode();

                ScheduleInsertPatternDay pattern = insertPatternDayRepository.findByDayCodeAndWokCode(dayCode, wokCode);

                insertPatternDayRepository.delete(pattern);

                patternDayDTO.get(i).setDayCode(patternDayDTO.get(i).getChangeDayCode());
            }

            List<ScheduleInsertPatternDay> patternDay = patternDayDTO.stream()
                    .map(pattern -> modelMapper.map(pattern, ScheduleInsertPatternDay.class))
                    .collect(Collectors.toList());

            List<ScheduleInsertPatternDay> insertPatternDayResult = insertPatternDayRepository.saveAll(patternDay);
            log.info("insertPatternDayResult = " + insertPatternDayResult);




            result = 1;

        }catch(Exception e) {
            log.info("오류~~~~~~~");
            throw new RuntimeException(e);
        }

        return (result > 0)? "수정 성공": "수정 실패";
    }

    @Transactional
    public String insertEtcPattern(ScheduleEtcPatternDTO etcPatternDTO) {
        log.info("insertEtcPattern Start~~~~~~~~~~~~");
        log.info(etcPatternDTO.toString());

        int result = 0;

        try{
            ScheduleEtcPattern insertEtcPattern = modelMapper.map(etcPatternDTO, ScheduleEtcPattern.class);

            ScheduleEtcPattern insertResult = etcPatternRepository.save(insertEtcPattern);
            System.out.println("insertResult = " + insertResult);

            result = 1;

        }catch(Exception e) {
            log.info("오류~~~~~~~");
            throw new RuntimeException(e);
        }

        return (result > 0)? "등록 성공": "등록 실패";
    }

    @Transactional
    public String updateEtcPattern(ScheduleEtcPatternDTO etcPatternDTO) {
        log.info("updateEtcPattern Start~~~~~~~~~~~~");
        log.info(etcPatternDTO.toString());

        int result = 0;

        try{
            if (!"10".equals(etcPatternDTO.getEtcKind())) {
                ScheduleEtcPattern pattern = etcPatternRepository.findById(etcPatternDTO.getEtcCode()).get();

                pattern = pattern.etcDate(etcPatternDTO.getEtcDate())
                        .etcKind(etcPatternDTO.getEtcKind()).build();
            }else {
                ScheduleEtcPattern pattern = etcPatternRepository.findById(etcPatternDTO.getEtcCode()).get();

                etcPatternRepository.delete(pattern);
            }


            result = 1;

        }catch(Exception e) {
            log.info("오류~~~~~~~");
            throw new RuntimeException(e);
        }

        return (result > 0)? "수정 성공": "수정 실패";
    }

    @Transactional
    public String insertScheduleAllowance(ScheduleAllowanceDTO allowanceDTO) {
        log.info("insertScheduleAllowance Start~~~~~~~~~~~~");
        log.info(allowanceDTO.toString());

        int result = 0;

        try{
            ScheduleInsertAllowance insertAllowance = modelMapper.map(allowanceDTO, ScheduleInsertAllowance.class);

            ScheduleInsertAllowance insertResult = insertAllowanceRepository.save(insertAllowance);
            System.out.println("insertResult = " + insertResult);

            result = 1;

        }catch(Exception e) {
            log.info("오류~~~~~~~");
            throw new RuntimeException(e);
        }

        return (result > 0)? "등록 성공": "등록 실패";
    }

    @Transactional
    public String updateScheduleAllowance(ScheduleAllowanceDTO allowanceDTO) {
        log.info("updateScheduleAllowance Start~~~~~~~~~~~~");
        log.info(allowanceDTO.toString());

        int result = 0;

        try{
            ScheduleInsertAllowance allowance = insertAllowanceRepository.findByMemCodeAndSchCode(allowanceDTO.getMemCode(), allowanceDTO.getSchCode());

            insertAllowanceRepository.delete(allowance);

            allowanceDTO.setSchCode(allowanceDTO.getChangeSchCode());

            ScheduleInsertAllowance insertAllowance = modelMapper.map(allowanceDTO, ScheduleInsertAllowance.class);

            ScheduleInsertAllowance insertResult = insertAllowanceRepository.save(insertAllowance);
            System.out.println("insertResult = " + insertResult);


            result = 1;

        }catch(Exception e) {
            log.info("오류~~~~~~~");
            throw new RuntimeException(e);
        }

        return (result > 0)? "수정 성공": "수정 실패";
    }

    public List<ScheduleAllSelectDTO> searchValue(ScheduleSearchValueDTO valueDTO) {
        log.info("searchValue 시작~~~~~~~~~~");
        if(valueDTO.getMemCode() != 0){
            log.info("멤버 코드로 조회~~~~~~~~~");
            List<ScheduleAllSelect> allSelects = allSelectRepository.findByYearMonthAndMemCode(valueDTO.getYearMonth(), valueDTO.getMemCode());

            List<ScheduleAllSelectDTO> list = allSelects.stream()
                    .map(resultList -> modelMapper.map(resultList, ScheduleAllSelectDTO.class))
                    .collect(Collectors.toList());
            log.info("멤버 코드로 끝~~~~~~~~~");

            return list;
        }else if (valueDTO.getMemName() != null){
            log.info("멤버 이름으로 조회~~~~~~~~~");
            List<ScheduleAllSelect> allSelects = allSelectRepository.findByYearMonthAndMemName(valueDTO.getYearMonth(), valueDTO.getMemName());

            List<ScheduleAllSelectDTO> list = allSelects.stream()
                    .map(resultList -> modelMapper.map(resultList, ScheduleAllSelectDTO.class))
                    .collect(Collectors.toList());
            log.info("멤버 이름으로 끝~~~~~~~~~");

            return list;
        }else if (valueDTO.getDepCode() != 0){
            log.info("부서 코드로 조회~~~~~~~~~");
            List<ScheduleAllSelect> allSelects = allSelectRepository.findByYearMonthAndDepCode(valueDTO.getYearMonth(), valueDTO.getDepCode());

            List<ScheduleAllSelectDTO> list = allSelects.stream()
                    .map(resultList -> modelMapper.map(resultList, ScheduleAllSelectDTO.class))
                    .collect(Collectors.toList());

            return list;
        }else if (valueDTO.getDepName() != null){
            log.info("부서 이름 조회~~~~~~~~~");
            List<ScheduleAllSelect> allSelects = allSelectRepository.findByYearMonthAndDepName(valueDTO.getYearMonth(), valueDTO.getDepName());

            List<ScheduleAllSelectDTO> list = allSelects.stream()
                    .map(resultList -> modelMapper.map(resultList, ScheduleAllSelectDTO.class))
                    .collect(Collectors.toList());
            log.info("부서 이름 끝~~~~~~~~~");

            return list;
        }else {
            log.info("날짜로 조회~~~~~~~~~");

            List<ScheduleAllSelect> allSelects = allSelectRepository.findByYearMonthDate(valueDTO.getChooseDate());

            List<ScheduleAllSelectDTO> list = allSelects.stream()
                    .map(resultList -> modelMapper.map(resultList, ScheduleAllSelectDTO.class))
                    .collect(Collectors.toList());

            return list;
        }

    }
}
