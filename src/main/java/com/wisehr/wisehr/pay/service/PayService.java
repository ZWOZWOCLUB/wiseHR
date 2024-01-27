package com.wisehr.wisehr.pay.service;

import com.wisehr.wisehr.pay.dto.PayDetailsDTO;
import com.wisehr.wisehr.pay.entity.PayDetails;
import com.wisehr.wisehr.pay.repository.PayDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PayService {
    private final PayDetailsRepository payDetailsRepository;
    private final ModelMapper modelMapper;


    public PayService(PayDetailsRepository payDetailsRepository, ModelMapper modelMapper) {
        this.payDetailsRepository = payDetailsRepository;
        this.modelMapper = modelMapper;
    }

    public List<PayDetailsDTO> selectPayList(String year, String memCode) {
        log.info("selectPayList 서비스 시작~~~~~~~~~~~~");
        List<PayDetails> payList = payDetailsRepository.findByMemCodeAndPdeYymmContaining(year, memCode);

        List<PayDetailsDTO> payDetailsDTOList = payList.stream()
                .map(pay -> modelMapper.map(pay, PayDetailsDTO.class))
                .collect(Collectors.toList());

        log.info("selectPayList 서비스 끗~~~~~~~~~~~~");
        System.out.println("payList = " + payList);

        return payDetailsDTOList;

    }

    public List<PayDetailsDTO> searchYear(int memCode) {
        log.info("searchYear 서비스 시작~~~~~~~~~~~~~~~~");
        List<PayDetails> payDetails = payDetailsRepository.findByMemCodeOrderByPdeDate(memCode);

        List<PayDetailsDTO> payDetailsDTOList = payDetails.stream()
                .map(pay -> modelMapper.map(pay, PayDetailsDTO.class))
                .collect(Collectors.toList());

        log.info("searchYear 서비스 끗~~~~~~~~~~~~~~~~");
        System.out.println("payDetails = " + payDetails);
        return payDetailsDTOList;

    }
}
