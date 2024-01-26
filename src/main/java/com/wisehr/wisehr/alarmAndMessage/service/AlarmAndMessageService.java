package com.wisehr.wisehr.alarmAndMessage.service;

import com.wisehr.wisehr.alarmAndMessage.dto.AllAlarmDTO;
import com.wisehr.wisehr.alarmAndMessage.dto.PerAlarmDTO;
import com.wisehr.wisehr.alarmAndMessage.dto.RecMessengerDTO;
import com.wisehr.wisehr.alarmAndMessage.dto.SendMessengerDTO;
import com.wisehr.wisehr.alarmAndMessage.entity.AllAlarm;
import com.wisehr.wisehr.alarmAndMessage.entity.PerAlarm;
import com.wisehr.wisehr.alarmAndMessage.entity.RecMessenger;
import com.wisehr.wisehr.alarmAndMessage.entity.SendMessenger;
import com.wisehr.wisehr.alarmAndMessage.repository.AllAlarmRepository;
import com.wisehr.wisehr.alarmAndMessage.repository.PerAlarmRepository;

import com.wisehr.wisehr.alarmAndMessage.repository.RecMessengerRepository;
import com.wisehr.wisehr.alarmAndMessage.repository.SendMessengerRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AlarmAndMessageService {
    private final AllAlarmRepository allAlarmRepository;
    private final PerAlarmRepository perAlarmRepository;
    private final SendMessengerRepository sendMessengerRepository;
    private final RecMessengerRepository recMessengerRepository;
    private final ModelMapper modelMapper;
    public AlarmAndMessageService(AllAlarmRepository allAlarmRepository, PerAlarmRepository perAlarmRepository, SendMessengerRepository sendMessengerRepository, RecMessengerRepository recMessengerRepository, ModelMapper modelMapper) {
        this.allAlarmRepository = allAlarmRepository;
        this.perAlarmRepository = perAlarmRepository;
        this.sendMessengerRepository = sendMessengerRepository;
        this.recMessengerRepository = recMessengerRepository;
        this.modelMapper = modelMapper;
    }

    public List<PerAlarmDTO> selectPerAlarm(int memCode) {
        List<PerAlarm> perAlarm = perAlarmRepository.findByMemCode(memCode);

        List<PerAlarmDTO> degreeDTO = perAlarm.stream()
                .map(exam -> modelMapper.map(exam, PerAlarmDTO.class))
                .collect(Collectors.toList());

        return degreeDTO;

    }

    public List<AllAlarmDTO> selectAllAlarm(int memCode) {
        List<AllAlarm> perAlarm = allAlarmRepository.findByMemCode(memCode);

        List<AllAlarmDTO> degreeDTO = perAlarm.stream()
                .map(exam -> modelMapper.map(exam, AllAlarmDTO.class))
                .collect(Collectors.toList());

        return degreeDTO;

    }
    public List<SendMessengerDTO> selectSendMessenger(int memCode) {
        List<SendMessenger> perAlarm = sendMessengerRepository.findByMemCode(memCode);

        List<SendMessengerDTO> degreeDTO = perAlarm.stream()
                .map(exam -> modelMapper.map(exam, SendMessengerDTO.class))
                .collect(Collectors.toList());

        return degreeDTO;

    }


    public List<RecMessengerDTO> selectRecMessenger(int memCode) {
        List<RecMessenger> perAlarm = recMessengerRepository.findByMemCode(memCode);

        List<RecMessengerDTO> degreeDTO = perAlarm.stream()
                .map(exam -> modelMapper.map(exam, RecMessengerDTO.class))
                .collect(Collectors.toList());

        return degreeDTO;

    }

    @Transactional
    public String insertMessenger(SendMessengerDTO sendMessengerDTO) {

        int result = 0; // 결과에 따른 값을 구분하기위한 용도의 변수

        try{


            // 저장을 위해서 일반 DTO객체를 Entity객체로 변경
            SendMessenger insertProduct = modelMapper.map(sendMessengerDTO, SendMessenger.class);

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
