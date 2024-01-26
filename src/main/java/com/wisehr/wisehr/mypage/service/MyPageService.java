package com.wisehr.wisehr.mypage.service;

import com.wisehr.wisehr.mypage.dto.*;
import com.wisehr.wisehr.mypage.entity.*;
import com.wisehr.wisehr.mypage.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Date;
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
    private final ModelMapper modelMapper;

    public MyPageService(MyPageRepository myPageRepository, DegreeRepository degreeRepository, CertificateRepository certificateRepository, CareerRepository careerRepository, AttendanceRepository attendanceRepository, ModelMapper modelMapper) {
        this.myPageRepository = myPageRepository;
        this.degreeRepository = degreeRepository;
        this.certificateRepository = certificateRepository;
        this.careerRepository = careerRepository;
        this.attendanceRepository = attendanceRepository;
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

    public AttendanceDTO selectAttendance(Date attWorkDate) {

        Attendance myPageMember = attendanceRepository.findByAttWorkDate(attWorkDate);
        AttendanceDTO settingMemberDTO = modelMapper.map(myPageMember, AttendanceDTO.class);

        return settingMemberDTO;
    }
}
