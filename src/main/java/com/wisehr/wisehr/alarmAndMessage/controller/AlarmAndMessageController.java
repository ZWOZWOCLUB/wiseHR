package com.wisehr.wisehr.alarmAndMessage.controller;


import com.wisehr.wisehr.alarmAndMessage.dto.SendMessengerDTO;
import com.wisehr.wisehr.alarmAndMessage.service.AlarmAndMessageService;
import com.wisehr.wisehr.common.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("alarm")
public class AlarmAndMessageController {
    private final AlarmAndMessageService alarmService;
    public AlarmAndMessageController(AlarmAndMessageService alarmService) {
        this.alarmService = alarmService;
    }

//    개인 알람 조회
    @GetMapping("/perAlarm/{memCode}")
    public ResponseEntity<ResponseDTO> selectPerAlarm(@PathVariable int memCode){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "개인 알람 조회 성공" , alarmService.selectPerAlarm(memCode)));
    }

//    전체 알람 조회
    @GetMapping("/allAlarm/{memCode}")
    public ResponseEntity<ResponseDTO> selectAllAlarm(@PathVariable int memCode){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "전체 알람 조회 성공" , alarmService.selectAllAlarm(memCode)));
    }

//    메신저 발신 내역 조회
    @GetMapping("/sendMessenger/{memCode}")
    public ResponseEntity<ResponseDTO> selectSendMessenger(@PathVariable int memCode){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "보낸 메신저 조회 성공" , alarmService.selectSendMessenger(memCode)));
    }

//    메신저 수신 내역 조회
    @GetMapping("/recMessenger/{memCode}")
    public ResponseEntity<ResponseDTO> selectRecMessenger(@PathVariable int memCode){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "받은 메신저 조회 성공" , alarmService.selectRecMessenger(memCode)));
    }

//    메신저 등록
    @PostMapping("/messenger")
    public ResponseEntity<ResponseDTO> insertMessenger(@ModelAttribute SendMessengerDTO sendMessengerDTO){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "메신저 등록 성공" , alarmService.insertMessenger(sendMessengerDTO)));
    }

}
