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
    private final AAMRecUpdateRepository aamRecUpdateRepository;
    private final AAMSendUpdateRepository aamSendUpdateRepository;
    private final ModelMapper modelMapper;
    public AlarmAndMessageService(AAMAllAlarmRepository allAlarmRepository, AAMPerAlarmRepository perAlarmRepository, AAMSendMessengerRepository sendMessengerRepository, AAMRecMessengerRepository recMessengerRepository, AAMApprovalCompleteRepository aamApprovalCompleteRepository, AAMMessageRepository aamMessageRepository, AAMRecMessageRepository aamRecMessageRepository, AAMRecUpdateRepository aamRecUpdateRepository, AAMSendUpdateRepository aamSendUpdateRepository, ModelMapper modelMapper) {
        this.allAlarmRepository = allAlarmRepository;
        this.perAlarmRepository = perAlarmRepository;
        this.sendMessengerRepository = sendMessengerRepository;
        this.recMessengerRepository = recMessengerRepository;
        this.aamApprovalCompleteRepository = aamApprovalCompleteRepository;
        this.aamMessageRepository = aamMessageRepository;
        this.aamRecMessageRepository = aamRecMessageRepository;
        this.aamRecUpdateRepository = aamRecUpdateRepository;
        this.aamSendUpdateRepository = aamSendUpdateRepository;
        this.modelMapper = modelMapper;
    }

    public List<AAMApprovalCompleteDTO> selectPerAlarm(int memCode) {
        List<AAMPerAlarm> perAlarm = perAlarmRepository.findTop30ByMemCodeOrderByPerArmCodeDesc(memCode);

        // 개인 알람 리스트 조회
//        perAlarm 에서 memCode를 기준으로 조회
        List<AAMPerAlarmDTO> degreeDTO = perAlarm.stream()
                .map(exam -> modelMapper.map(exam, AAMPerAlarmDTO.class))
                .collect(Collectors.toList());


        List<AAMApprovalCompleteDTO> aamApprovalCompleteDTOS = new ArrayList<>();
        // 추가로 고유결재번호와 state(승인, 반려 상태의)가 필요함

//        아까 찾은 perAlarm에서의 perArmCode를 가지고 approval_complete에서 조회 후 리스트에 담아 반환
        for(AAMPerAlarmDTO code : degreeDTO){
            System.out.println("code.getPerArmCode() = " + code.getPerArmCode());
            AAMApprovalComplete myPageMember = aamApprovalCompleteRepository.findByPerArmCode(code.getPerArmCode());
            AAMApprovalCompleteDTO settingMemberDTO = modelMapper.map(myPageMember, AAMApprovalCompleteDTO.class);

            settingMemberDTO.setPerArmCheckStatus(code.getPerArmCheckStatus());

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

        List<AAMMessage> perAlarm = aamMessageRepository.findTop30ByMemCodeAndMsgDeleteStatusOrderByMsgCodeDesc(memCode,"N");
        System.out.println("perAlarm = " + perAlarm);

        List<AAMMessageDTO> degreeDTO = perAlarm.stream()
                .map(exam -> modelMapper.map(exam, AAMMessageDTO.class))
                .collect(Collectors.toList());
        System.out.println("degreeDTO = " + degreeDTO);

        return degreeDTO;

    }


//    받은 내역에서 확인하는 곳
    public List<AAMRecMessageDTO> selectRecMessenger(int memCode) {
        List<AAMRecMessage> perAlarm = aamRecMessageRepository.findTop30ByMemCodeAndRecMsgDeleteStatusOrderByMsgCodeDesc(memCode,"N");
        System.out.println("perAlarm = " + perAlarm);

        List<AAMRecMessageDTO> degreeDTO = perAlarm.stream()
                .map(exam -> modelMapper.map(exam, AAMRecMessageDTO.class))
                .collect(Collectors.toList());
        System.out.println("degreeDTO = " + degreeDTO);

        return degreeDTO;

    }

    @Transactional
    public Object insertMessenger(AAMSendMessengerDTO sendMessengerDTO) {
        int count=0;
        System.out.println("sendMessengerDTO.getCodes() = " + sendMessengerDTO.getCodes());

        System.out.println("sendMessengerDTO service = " + sendMessengerDTO);
        int result = 0; // 결과에 따른 값을 구분하기위한 용도의 변수

        AAMSendMessenger insertProduct = null;
        try{

            // 저장을 위해서 일반 DTO객체를 Entity객체로 변경
            insertProduct = modelMapper.map(sendMessengerDTO, AAMSendMessenger.class);

            System.out.println("insertProduct = " + insertProduct);
            sendMessengerRepository.save(insertProduct);

            for (int i = 0; i < 1; i++) {
                AAMRecMessengerDTO aamRecMessengerDTO = new AAMRecMessengerDTO();

                aamRecMessengerDTO.setMsgCode(insertProduct.getMsgCode());
                aamRecMessengerDTO.setMemCode(Integer.parseInt(sendMessengerDTO.getCodes().get(0)));
                aamRecMessengerDTO.setRecMsgDeleteStatus("N");
                aamRecMessengerDTO.setRecMsgCheckStatus("N");

                AAMRecUpdate aamRecUpdate = modelMapper.map(aamRecMessengerDTO, AAMRecUpdate.class);
                System.out.println("aamRecUpdate = " + aamRecUpdate);

                aamRecUpdateRepository.save(aamRecUpdate);
            }
            for (int i = 1; i < sendMessengerDTO.getCodes().size(); i++) {
                AAMRecMessengerDTO aamRecMessengerDTO = new AAMRecMessengerDTO();

                aamRecMessengerDTO.setRecMsgCode(null);
                aamRecMessengerDTO.setMsgCode(insertProduct.getMsgCode());
                aamRecMessengerDTO.setMemCode(Integer.parseInt(sendMessengerDTO.getCodes().get(i)));
                aamRecMessengerDTO.setRecMsgDeleteStatus("N");
                aamRecMessengerDTO.setRecMsgCheckStatus("N");

                AAMRecUpdate aamRecUpdate = modelMapper.map(aamRecMessengerDTO, AAMRecUpdate.class);
                System.out.println("aamRecUpdate = " + aamRecUpdate);

                aamRecUpdateRepository.save(aamRecUpdate);

                result = 1;
                count++;
            }

//            for(String code : sendMessengerDTO.getCodes()){
//                System.out.println("이번에 넣을 코드 code = " + code);
//                AAMRecMessengerDTO aamRecMessengerDTO = new AAMRecMessengerDTO();
//
//                aamRecMessengerDTO.setMsgCode(insertProduct.getMsgCode());
//                aamRecMessengerDTO.setMemCode(Integer.parseInt(code));
//                aamRecMessengerDTO.setRecMsgDeleteStatus("N");
//                aamRecMessengerDTO.setRecMsgCheckStatus("N");
//
//                AAMRecUpdate aamRecUpdate = modelMapper.map(aamRecMessengerDTO, AAMRecUpdate.class);
//                System.out.println("aamRecUpdate = " + aamRecUpdate);
//
//                aamRecUpdateRepository.save(aamRecUpdate);
//
//                result = 1;
//                count++;
//            }

        } catch (Exception e){
            System.out.println("check");
            throw new RuntimeException(e);
        }

        System.out.println("insertProduct.getMsgCode() = " + insertProduct.getMsgCode());

        log.info("[ProductService] insertProduct End ===================");
        return (result > 0)? "메세지 입력 성공" : "메세지 입력 실패" ;
    }

    @Transactional
    public Object recUpdateCheck(int memCode) {
//        멤코드라 써있지만 msgCode로 조회할거임
        int result = 0;
        int count = 0;
        List<AAMRecUpdate> recUpdate1 = aamRecUpdateRepository.findByMemCode(memCode);

        for(AAMRecUpdate update : recUpdate1){
            update = update.recMsgCheckStatus("Y").build();
            count++;
        }
////            update 할 엔티티 조회
//        AAMRecUpdate recUpdate = aamRecUpdateRepository.findByMsgCodeAndMemCode(msgCode, memCode);
////            값 조회 후 update.build();
//        recUpdate = recUpdate.recMsgCheckStatus("Y").build();

        if(count == recUpdate1.size()){
            result = 1;
        }
        return (result > 0) ? "받은 메신저 확인 상태 업데이트 성공" : "받은 메신저 확인 상태 업데이트 실패";
    }

//    보낸 메세지 삭제하기
    @Transactional
    public Object deleteSendAndRecMsg(int msgCode) {
        int result = 0;
        int count = 0;

//        1. recMessage 업데이트
        List<AAMRecUpdate> recUpdate = aamRecUpdateRepository.findByMsgCode(msgCode);

        for(AAMRecUpdate exam : recUpdate){
            AAMRecUpdate aamRecUpdate = aamRecUpdateRepository.findByRecMsgCode(exam.getRecMsgCode());

            aamRecUpdate.recMsgDeleteStatus("Y").build();

            count ++;
        }

//        2. sendMessage 업데이트
        AAMSendUpdate sendUpdate = aamSendUpdateRepository.findById(msgCode).get();
        sendUpdate = sendUpdate.msgDeleteStatus("Y").build();

        if(count == recUpdate.toArray().length && sendUpdate.getMsgDeleteStatus() == "Y"){
            result = 1;
        }
        return (result > 0) ? "보낸 메세지 삭제 성공" : "보낸 메세지 삭제 실패";

    }

//    받은 메세지 삭제하기
    @Transactional
    public Object deleteRecMsg(int msgCode, int memCode) {
        int result = 0;

        AAMRecUpdate recUpdate = aamRecUpdateRepository.findByMsgCodeAndMemCode(msgCode,memCode);
        recUpdate = recUpdate.recMsgDeleteStatus("Y").build();

        if(recUpdate.getRecMsgDeleteStatus() == "Y"){
            result = 1;
        }
        return (result > 0) ? "받은 메세지 삭제 성공" : "받은 메세지 삭제 실패";
    }


//    개인 알람 확인 상태 업데이트
    @Transactional
    public Object perAlarmCheckUpdate(int perArmCode) {

        int result = 0;

//            update 할 엔티티 조회
        AAMPerAlarm perAlarm = perAlarmRepository.findById(perArmCode).get();

//            값 조회 후 update.build();
        perAlarm = perAlarm.perArmCheckStatus("Y").build();

        if(perAlarm.getPerArmCheckStatus() == "Y"){
            result = 1;
        }
        return (result > 0) ? "개인 알람 확인 상태 업데이트 성공" : "개인 알람 확인 상태 업데이트 실패";
    }



    //    전체 알람 확인 상태 업데이트
    @Transactional
    public Object allAlarmCheckUpdate(int allArmCode) {

        int result = 0;
//            update 할 엔티티 조회
        AAMAllAlarm allAlarm = allAlarmRepository.findById(allArmCode).get();

//            값 조회 후 update.build();
        allAlarm = allAlarm.allArmCheck("Y").build();

        if(allAlarm.getAllArmCheck() == "Y"){
            result = 1;
        }
        return (result > 0) ? "전체 알람 확인 상태 업데이트 성공" : "전체 알람 확인 상태 업데이트 실패";
    }


//    받는 메신저 insert
    @Transactional
    public Object insertRecMessenger(AAMRecMessengerDTO recMessengerDTO, List<Integer> codes) {


        System.out.println("codes = " + codes);
        int result = 0; // 결과에 따른 값을 구분하기위한 용도의 변수

        recMessengerDTO.setRecMsgDeleteStatus("N");
        recMessengerDTO.setRecMsgCheckStatus("N");

        try{

            // 저장을 위해서 일반 DTO객체를 Entity객체로 변경
            AAMRecUpdate insertProduct = modelMapper.map(recMessengerDTO, AAMRecUpdate.class);

            aamRecUpdateRepository.save(insertProduct);

            result = 1;
        } catch (Exception e){
            System.out.println("check");
            throw new RuntimeException(e);
        }


        log.info("[ProductService] insertProduct End ===================");
        return (result > 0)? "받는 메세지 입력 성공" : "메세지 입력 실패" ;
    }
}
