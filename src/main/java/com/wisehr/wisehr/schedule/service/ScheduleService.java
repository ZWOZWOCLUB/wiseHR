package com.wisehr.wisehr.schedule.service;

import com.wisehr.wisehr.schedule.dto.ScheduleAttendanceDTO;
import com.wisehr.wisehr.schedule.entity.ScheduleAttendance;
import com.wisehr.wisehr.schedule.repository.ScheduleAttendanceRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ScheduleService {

    private final ModelMapper modelMapper;
    private final ScheduleAttendanceRepository scheduleAttendanceRepository;


    public ScheduleService(ModelMapper modelMapper, ScheduleAttendanceRepository scheduleAttendanceRepository) {
        this.modelMapper = modelMapper;
        this.scheduleAttendanceRepository = scheduleAttendanceRepository;
    }


    public List<ScheduleAttendanceDTO> searchPrev(String date) {
        log.info("searchDate 서비스 시작~~~~~~~~~~~~");
        List<ScheduleAttendance> scheduleAttendances = scheduleAttendanceRepository.findByAttWorkDateContaining(date);
        List<ScheduleAttendanceDTO> scheduleAttendanceDTO = scheduleAttendances.stream()
                        .map(list -> modelMapper.map(list, ScheduleAttendanceDTO.class))
                        .collect(Collectors.toList());
        log.info("searchDate 서비스 끗~~~~~~~~~~~~");

        return scheduleAttendanceDTO;
    }
}
