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
        List<PayDetails> payList = payDetailsRepository.findByMemCodeAndPdeYymm(year, memCode);

        List<PayDetailsDTO> payDetailsDTOList = payList.stream()
                .map(pay -> modelMapper.map(pay, PayDetailsDTO.class))
                .collect(Collectors.toList());

        int pdeSalary = payDetailsDTOList.get(0).getPdeSalary();
        int basicSalary = pdeSalary - 200000;
        int nationalPension = 0;
        if(pdeSalary > 590000){
            nationalPension = 265500;
        } else {
            nationalPension = (int)(pdeSalary * 0.045);
        }
        int healthInsurance = (int) (pdeSalary * 0.03454);
        payDetailsDTOList.get(0).setHealthInsurance(healthInsurance);
        int employmentInsurance = (int) (pdeSalary * 0.09);
        int incomeTax = 0;
        if(pdeSalary >= 33200000){
            incomeTax = 84620;
        } else if (pdeSalary >= 33400000) {
            incomeTax = 86330;
        }else if (pdeSalary >= 10000000) {
            incomeTax = 1442570;
        }
        int localIncomeTax= (int) (incomeTax * 0.1);
        int medicalInsurance = (int) (pdeSalary * 0.004591);

        payDetailsDTOList.get(0).setBasicSalary(basicSalary);
        payDetailsDTOList.get(0).setFoodExpenses(200000);
        payDetailsDTOList.get(0).setNationalPension(nationalPension);
        payDetailsDTOList.get(0).setHealthInsurance(healthInsurance);
        payDetailsDTOList.get(0).setEmploymentInsurance(employmentInsurance);
        payDetailsDTOList.get(0).setIncomeTax(incomeTax);
        payDetailsDTOList.get(0).setLocalIncomeTax(localIncomeTax);
        payDetailsDTOList.get(0).setMedicalInsurance(medicalInsurance);
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
