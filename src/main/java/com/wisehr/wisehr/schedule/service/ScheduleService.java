package com.wisehr.wisehr.schedule.service;

import com.wisehr.wisehr.schedule.dto.*;
import com.wisehr.wisehr.schedule.entity.*;
import com.wisehr.wisehr.schedule.repository.ScheduleAttendanceRepository;
import com.wisehr.wisehr.schedule.repository.SchedulePatternDayRepository;
import com.wisehr.wisehr.schedule.repository.ScheduleRepository;
import com.wisehr.wisehr.schedule.repository.ScheduleWorkPatternRepository;
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


    public ScheduleService(ModelMapper modelMapper, ScheduleAttendanceRepository scheduleAttendanceRepository, ScheduleWorkPatternRepository scheduleWorkPatternRepository, ScheduleRepository scheduleRepository, SchedulePatternDayRepository patternDayRepository) {
        this.modelMapper = modelMapper;
        this.attendanceRepository = scheduleAttendanceRepository;
        this.workPatternRepository = scheduleWorkPatternRepository;
        this.scheduleRepository = scheduleRepository;
        this.patternDayRepository = patternDayRepository;
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
    public String insertSchedule(SchedulePatternInsertDTO insertDTO
    ) {
        log.info("insertSchedule Start~~~~~~~~~~~~");
        log.info((insertDTO.toString()));
        ScheduleDTO scheduleDTO = insertDTO.getScheduleDTO();
        ScheduleWorkPatternDTO workPatternDTO = insertDTO.getWorkPatternDTO();
        List<SchedulePatternDayDTO> patternDayDTO = insertDTO.getPatternDayDTO();

        int result = 0;
        try {

            ScheduleWorkPattern workPattern = modelMapper.map(workPatternDTO, ScheduleWorkPattern.class);
            ScheduleWorkPattern insertWorkPatternResult = workPatternRepository.save(workPattern);
            System.out.println("insertWorkPatternResult = " + insertWorkPatternResult);

            result++;

            scheduleDTO.setWokCode(insertWorkPatternResult.getWokCode());
            Schedule schedule = modelMapper.map(scheduleDTO, Schedule.class);
            Schedule insertScheduleResult = scheduleRepository.save(schedule);
            System.out.println("insertScheduleResult = " + insertScheduleResult);
            result++;


            for (SchedulePatternDayDTO pattern: patternDayDTO) {
                pattern.setWokCode(insertWorkPatternResult.getWokCode());
                SchedulePatternDay patternDay = modelMapper.map(pattern, SchedulePatternDay.class);
                SchedulePatternDay insertPatternDayResult = patternDayRepository.save(patternDay);
                log.info("insertPatternDayResult = " + insertPatternDayResult);
            }

            result++;
            result++;
        } catch (Exception e) {
            log.info("일정 패턴 등록 오류");
            throw new RuntimeException(e);
        }


        log.info("insertWorkPattern 끗~~~~~~~~~~~~");
        return (result > 0)? "등록 성공": "등록 실패";
    }
}
