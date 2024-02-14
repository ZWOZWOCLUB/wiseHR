package com.wisehr.wisehr.alarmAndMessage.service;

import com.wisehr.wisehr.alarmAndMessage.dto.*;
import com.wisehr.wisehr.alarmAndMessage.entity.*;
import com.wisehr.wisehr.alarmAndMessage.repository.*;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AlarmAndMessageService {
    private final AAMAllAlarmRepository allAlarmRepository;
    private final AAMPerAlarmRepository perAlarmRepository;
    private final AAMSendMessengerRepository sendMessengerRepository;
    private final AAMRecMessengerRepository recMessengerRepository;
    private final AAMApprovalCompleteRepository aamApprovalCompleteRepository;
    private final AAMMessageRepository aamMessageRepository;
    private final AAMRecMessageRepository aamRecMessageRepository;
    private final ModelMapper modelMapper;
    public AlarmAndMessageService(AAMAllAlarmRepository allAlarmRepository, AAMPerAlarmRepository perAlarmRepository, AAMSendMessengerRepository sendMessengerRepository, AAMRecMessengerRepository recMessengerRepository, AAMApprovalCompleteRepository aamApprovalCompleteRepository, AAMMessageRepository aamMessageRepository, AAMRecMessageRepository aamRecMessageRepository, ModelMapper modelMapper) {
        this.allAlarmRepository = allAlarmRepository;
        this.perAlarmRepository = perAlarmRepository;
        this.sendMessengerRepository = sendMessengerRepository;
        this.recMessengerRepository = recMessengerRepository;
        this.aamApprovalCompleteRepository = aamApprovalCompleteRepository;
        this.aamMessageRepository = aamMessageRepository;
        this.aamRecMessageRepository = aamRecMessageRepository;
        this.modelMapper = modelMapper;
    }

    public List<AAMApprovalCompleteDTO> selectPerAlarm(int memCode) {
        List<AAMPerAlarm> perAlarm = perAlarmRepository.findByMemCodeOrderByPerArmCodeDesc(memCode);

        // 개인 알람 리스트 조회
        List<AAMPerAlarmDTO> degreeDTO = perAlarm.stream()
                .map(exam -> modelMapper.map(exam, AAMPerAlarmDTO.class))
                .collect(Collectors.toList());


        List<AAMApprovalCompleteDTO> aamApprovalCompleteDTOS = new ArrayList<>();
        // 추가로 고유결재번호와 state(승인, 반려 상태의)가 필요함
        for(AAMPerAlarmDTO code : degreeDTO){
            System.out.println("code.getPerArmCode() = " + code.getPerArmCode());
            AAMApprovalComplete myPageMember = aamApprovalCompleteRepository.findByPerArmCode(code.getPerArmCode());
            AAMApprovalCompleteDTO settingMemberDTO = modelMapper.map(myPageMember, AAMApprovalCompleteDTO.class);

            aamApprovalCompleteDTOS.add(settingMemberDTO);
            System.out.println("settingMemberDTO = " + settingMemberDTO);
        }
        System.out.println("aamApprovalCompleteDTOS = " + aamApprovalCompleteDTOS);

        return aamApprovalCompleteDTOS;

    }

    public List<AAMAllAlarmDTO> selectAllAlarm(int memCode) {
        List<AAMAllAlarm> perAlarm = allAlarmRepository.findByMemCode(memCode);

        List<AAMAllAlarmDTO> degreeDTO = perAlarm.stream()
                .map(exam -> modelMapper.map(exam, AAMAllAlarmDTO.class))
                .collect(Collectors.toList());

        return degreeDTO;

    }
    public List<AAMMessageDTO> selectSendMessenger(int memCode) {
        System.out.println("memCode = " + memCode);
        List<AAMMessage> perAlarm = aamMessageRepository.findByMemCode(memCode);
        System.out.println("perAlarm = " + perAlarm);

        List<AAMMessageDTO> degreeDTO = perAlarm.stream()
                .map(exam -> modelMapper.map(exam, AAMMessageDTO.class))
                .collect(Collectors.toList());
        System.out.println("degreeDTO = " + degreeDTO);

        return degreeDTO;

    }


    public List<AAMRecMessageDTO> selectRecMessenger(int memCode) {
        List<AAMRecMessage> perAlarm = aamRecMessageRepository.findByMemCode(memCode);
        System.out.println("perAlarm = " + perAlarm);

        List<AAMRecMessageDTO> degreeDTO = perAlarm.stream()
                .map(exam -> modelMapper.map(exam, AAMRecMessageDTO.class))
                .collect(Collectors.toList());
        System.out.println("degreeDTO = " + degreeDTO);

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
