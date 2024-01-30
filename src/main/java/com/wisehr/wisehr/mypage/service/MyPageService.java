package com.wisehr.wisehr.mypage.service;

import com.wisehr.wisehr.mypage.dto.*;
import com.wisehr.wisehr.mypage.entity.*;
import com.wisehr.wisehr.mypage.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MyPageService {
    private final MyPageRepository myPageRepository;
    private final DegreeRepository degreeRepository;
    private final CertificateRepository certificateRepository;
    private final CareerRepository careerRepository;
    private final AttendanceRepository attendanceRepository;
    private final DocumentRepository documentRepository;
    private final ModelMapper modelMapper;

    public MyPageService(MyPageRepository myPageRepository, DegreeRepository degreeRepository, CertificateRepository certificateRepository, CareerRepository careerRepository, AttendanceRepository attendanceRepository, DocumentFileRepository documentFileRepository, DocumentRepository documentRepository, ModelMapper modelMapper) {
        this.myPageRepository = myPageRepository;
        this.degreeRepository = degreeRepository;
        this.certificateRepository = certificateRepository;
        this.careerRepository = careerRepository;
        this.attendanceRepository = attendanceRepository;
        this.documentRepository = documentRepository;
        this.modelMapper = modelMapper;
    }


    public MyPageDTO selectMem(int memCode) {

        MyPageMember myPageMember = myPageRepository.findById(memCode).get();
        MyPageDTO settingMemberDTO = modelMapper.map(myPageMember, MyPageDTO.class);

        return settingMemberDTO;

    }


    public List<DegreeDTO> selectDegree(int memCode) {

        List<Degree> degree = degreeRepository.findByMemCode(memCode);
        List<DegreeDTO> degreeDTO = degree.stream()
                .map(exam -> modelMapper.map(exam, DegreeDTO.class))
                .collect(Collectors.toList());

        return degreeDTO;
    }

    public List<CertificateDTO> selectCer(int memCode) {
        List<Certificate> certificates = certificateRepository.findByMemCode(memCode);
        List<CertificateDTO> degreeDTO = certificates.stream()
                .map(exam -> modelMapper.map(exam, CertificateDTO.class))
                .collect(Collectors.toList());

        return degreeDTO;
    }

    public List<CareerDTO> selectCareer(int memCode) {
        List<Career> certificates = careerRepository.findByMemCode(memCode);
        List<CareerDTO> degreeDTO = certificates.stream()
                .map(exam -> modelMapper.map(exam, CareerDTO.class))
                .collect(Collectors.toList());

        return degreeDTO;
    }

    public AttendanceDTO selectAttendance(Date attWorkDate, int memCode) {


        Attendance attendance = attendanceRepository.findByAttWorkDateAndMemCode(attWorkDate,memCode);
        AttendanceDTO settingMemberDTO = modelMapper.map(attendance, AttendanceDTO.class);

        Time start = settingMemberDTO.getAttStartTime();
        Time end = settingMemberDTO.getAttEndTime();

        // Time을 LocalTime으로 변환
        LocalTime startLocalTime = start.toLocalTime();
        LocalTime endLocalTime = end.toLocalTime();

        // 두 LocalTime 객체 간의 차이 계산
        long hoursDiff = ChronoUnit.HOURS.between(startLocalTime, endLocalTime);
        long minutesDiff = ChronoUnit.MINUTES.between(startLocalTime, endLocalTime) % 60;

        // 결과 출력
        System.out.println("시간 차이: " + hoursDiff + " 시간 " + minutesDiff + " 분");

        return settingMemberDTO;
    }

//    public List<DocumentDTO> selectDocument(int memCode) {
//        List<Document> certificates = documentRepository.findByMemCode(memCode);
//        List<DocumentDTO> degreeDTO = certificates.stream()
//                .map(exam -> modelMapper.map(exam, DocumentDTO.class))
//                .collect(Collectors.toList());
//
//        return degreeDTO;
//    }
    public DocumentDTO selectDocument(int memCode) {

        Document myPageMember = documentRepository.findByMemCode(memCode);
        DocumentDTO settingMemberDTO = modelMapper.map(myPageMember, DocumentDTO.class);

        return settingMemberDTO;

    }
}
