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



    public ScheduleService(ModelMapper modelMapper, ScheduleAttendanceRepository scheduleAttendanceRepository, ScheduleWorkPatternRepository scheduleWorkPatternRepository, ScheduleRepository scheduleRepository, SchedulePatternDayRepository patternDayRepository, ScheduleEtcPatternRepository etcPatternRepository, ScheduleAllowanceRepository allowanceRepository) {
        this.modelMapper = modelMapper;
        this.attendanceRepository = scheduleAttendanceRepository;
        this.workPatternRepository = scheduleWorkPatternRepository;
        this.scheduleRepository = scheduleRepository;
        this.patternDayRepository = patternDayRepository;
        this.etcPatternRepository = etcPatternRepository;
        this.allowanceRepository = allowanceRepository;
    }


    public List<ScheduleAttendanceDTO> searchPrev(String yearMonth) {
        log.info("searchDate 서비스 시작~~~~~~~~~~~~");
        List<ScheduleAttendance> scheduleAttendances = attendanceRepository.findByAttWorkDateContaining(yearMonth);
        List<ScheduleAttendanceDTO> scheduleAttendanceDTO = scheduleAttendances.stream()
                        .map(list -> modelMapper.map(list, ScheduleAttendanceDTO.class))
                        .collect(Collectors.toList());
        log.info("searchDate 서비스 끗~~~~~~~~~~~~");

        return scheduleAttendanceDTO;
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


            for (SchedulePatternDayDTO pattern: patternDayDTO) {
                pattern.setWokCode(insertDTO.getScheduleDTO().getWokCode());

            }
            List<SchedulePatternDay> patternDay = patternDayDTO.stream()
                    .map(pattern -> modelMapper.map(pattern, SchedulePatternDay.class))
                    .collect(Collectors.toList());

            List<SchedulePatternDay> insertPatternDayResult = patternDayRepository.saveAll(patternDay);
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

                SchedulePatternDay pattern = patternDayRepository.findByDayCodeAndWokCode(dayCode, wokCode);

                patternDayRepository.delete(pattern);

                patternDayDTO.get(i).setDayCode(patternDayDTO.get(i).getChangeDayCode());
            }

            List<SchedulePatternDay> patternDay = patternDayDTO.stream()
                    .map(pattern -> modelMapper.map(pattern, SchedulePatternDay.class))
                    .collect(Collectors.toList());

            List<SchedulePatternDay> insertPatternDayResult = patternDayRepository.saveAll(patternDay);
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
            if (!"delete".equals(etcPatternDTO.getEtcKind())) {
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
            ScheduleAllowance insertAllowance = modelMapper.map(allowanceDTO, ScheduleAllowance.class);

            ScheduleAllowance insertResult = allowanceRepository.save(insertAllowance);
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
            ScheduleAllowance allowance = allowanceRepository.findByMemCodeAndSchCode(allowanceDTO.getMemCode(), allowanceDTO.getSchCode());

            allowanceRepository.delete(allowance);

            allowanceDTO.setSchCode(allowanceDTO.getChangeSchCode());

            ScheduleAllowance insertAllowance = modelMapper.map(allowanceDTO, ScheduleAllowance.class);

            ScheduleAllowance insertResult = allowanceRepository.save(insertAllowance);
            System.out.println("insertResult = " + insertResult);


            result = 1;

        }catch(Exception e) {
            log.info("오류~~~~~~~");
            throw new RuntimeException(e);
        }

        return (result > 0)? "수정 성공": "수정 실패";
    }
}
