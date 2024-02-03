package com.wisehr.wisehr.alarmAndMessage.service;

import com.wisehr.wisehr.alarmAndMessage.dto.AAMAllAlarmDTO;
import com.wisehr.wisehr.alarmAndMessage.dto.AAMPerAlarmDTO;
import com.wisehr.wisehr.alarmAndMessage.dto.AAMRecMessengerDTO;
import com.wisehr.wisehr.alarmAndMessage.dto.AAMSendMessengerDTO;
import com.wisehr.wisehr.alarmAndMessage.entity.AAMAllAlarm;
import com.wisehr.wisehr.alarmAndMessage.entity.AAMPerAlarm;
import com.wisehr.wisehr.alarmAndMessage.entity.AAMRecMessenger;
import com.wisehr.wisehr.alarmAndMessage.entity.AAMSendMessenger;
import com.wisehr.wisehr.alarmAndMessage.repository.AAMAllAlarmRepository;
import com.wisehr.wisehr.alarmAndMessage.repository.AAMPerAlarmRepository;

import com.wisehr.wisehr.alarmAndMessage.repository.AAMRecMessengerRepository;
import com.wisehr.wisehr.alarmAndMessage.repository.AAMSendMessengerRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AlarmAndMessageService {
    private final AAMAllAlarmRepository allAlarmRepository;
    private final AAMPerAlarmRepository perAlarmRepository;
    private final AAMSendMessengerRepository sendMessengerRepository;
    private final AAMRecMessengerRepository recMessengerRepository;
    private final ModelMapper modelMapper;
    public AlarmAndMessageService(AAMAllAlarmRepository allAlarmRepository, AAMPerAlarmRepository perAlarmRepository, AAMSendMessengerRepository sendMessengerRepository, AAMRecMessengerRepository recMessengerRepository, ModelMapper modelMapper) {
        this.allAlarmRepository = allAlarmRepository;
        this.perAlarmRepository = perAlarmRepository;
        this.sendMessengerRepository = sendMessengerRepository;
        this.recMessengerRepository = recMessengerRepository;
        this.modelMapper = modelMapper;
    }

    public List<AAMPerAlarmDTO> selectPerAlarm(int memCode) {
        List<AAMPerAlarm> perAlarm = perAlarmRepository.findByMemCode(memCode);

        List<AAMPerAlarmDTO> degreeDTO = perAlarm.stream()
                .map(exam -> modelMapper.map(exam, AAMPerAlarmDTO.class))
                .collect(Collectors.toList());

        return degreeDTO;

    }

    public List<AAMAllAlarmDTO> selectAllAlarm(int memCode) {
        List<AAMAllAlarm> perAlarm = allAlarmRepository.findByMemCode(memCode);

        List<AAMAllAlarmDTO> degreeDTO = perAlarm.stream()
                .map(exam -> modelMapper.map(exam, AAMAllAlarmDTO.class))
                .collect(Collectors.toList());

        return degreeDTO;

    }
    public List<AAMSendMessengerDTO> selectSendMessenger(int memCode) {
        List<AAMSendMessenger> perAlarm = sendMessengerRepository.findByMemCode(memCode);

        List<AAMSendMessengerDTO> degreeDTO = perAlarm.stream()
                .map(exam -> modelMapper.map(exam, AAMSendMessengerDTO.class))
                .collect(Collectors.toList());

        return degreeDTO;

    }


    public List<AAMRecMessengerDTO> selectRecMessenger(int memCode) {
        List<AAMRecMessenger> perAlarm = recMessengerRepository.findByMemCode(memCode);

        List<AAMRecMessengerDTO> degreeDTO = perAlarm.stream()
                .map(exam -> modelMapper.map(exam, AAMRecMessengerDTO.class))
                .collect(Collectors.toList());

        return degreeDTO;

    }

    @Transactional
    public String insertMessenger(AAMSendMessengerDTO sendMessengerDTO) {


        System.out.println("sendMessengerDTO service = " + sendMessengerDTO);
        int result = 0; // 결과에 따른 값을 구분하기위한 용도의 변수


        try{


            // 저장을 위해서 일반 DTO객체를 Entity객체로 변경
            AAMSendMessenger insertProduct = modelMapper.map(sendMessengerDTO, AAMSendMessenger.class);

            System.out.println("insertProduct = " + insertProduct);
            sendMessengerRepository.save(insertProduct);

            result = 1;
        } catch (Exception e){
            System.out.println("check");
            throw new RuntimeException(e);
        }


        log.info("[ProductService] insertProduct End ===================");
        return (result > 0)? "메신저 입력 성공": "메신저 입력 실패";
    }
}
