package com.wisehr.wisehr.pay.service;

import com.wisehr.wisehr.pay.dto.PayDetailsDTO;
import com.wisehr.wisehr.pay.dto.PayMemberDTO;
import com.wisehr.wisehr.pay.entity.PayDetails;
import com.wisehr.wisehr.pay.entity.PayMember;
import com.wisehr.wisehr.pay.repository.PayDetailsRepository;
import com.wisehr.wisehr.pay.repository.PayMemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PayService {
    private final PayDetailsRepository payDetailsRepository;
    private final PayMemberRepository memberRepository;
    private final ModelMapper modelMapper;


    public PayService(PayDetailsRepository payDetailsRepository, PayMemberRepository memberRepository, ModelMapper modelMapper) {
        this.payDetailsRepository = payDetailsRepository;
        this.memberRepository = memberRepository;
        this.modelMapper = modelMapper;
    }

    public List<PayDetailsDTO> selectPayList(String yearMonth, String memCode) {
        log.info("selectPayList 서비스 시작~~~~~~~~~~~~");
        List<PayDetails> payList = payDetailsRepository.findByMemCodeAndPdeYymm(yearMonth, memCode);

        List<PayDetailsDTO> payDetailsDTOList = payList.stream()
                .map(pay -> modelMapper.map(pay, PayDetailsDTO.class))
                .collect(Collectors.toList());

        int totalDdeSalary = 0;
        int totalDeductedAmount = 0;
        int totalDeductions = 0;

        for(int i = 0; i < payDetailsDTOList.size(); i++){
            int pdeSalary = payDetailsDTOList.get(i).getPdeSalary();
            int basicSalary = pdeSalary - 200000;
            int nationalPension = 0; //국민연금
            if (pdeSalary > 590000) {
                nationalPension = 265500;
            } else {
                nationalPension = (int) (pdeSalary * 0.045);
            }
            int healthInsurance = (int) (pdeSalary * 0.03454); //건강보험
            payDetailsDTOList.get(0).setHealthInsurance(healthInsurance);
            int employmentInsurance = (int) (pdeSalary * 0.09); //고용보험
            int incomeTax = 0; //소득세
            if (basicSalary >= 3000000 && basicSalary <3020000) {
                incomeTax = 74350;
            } else if (basicSalary >= 3020000 && basicSalary <3040000) {incomeTax = 76060;
            } else if (basicSalary >= 3040000 && basicSalary <3060000) {incomeTax = 77770;
            } else if (basicSalary >= 3060000 && basicSalary <3080000) {incomeTax = 79480;
            } else if (basicSalary >= 3080000 && basicSalary <3100000) {incomeTax = 81190;
            } else if (basicSalary >= 3100000 && basicSalary <3120000) {incomeTax = 82900;
            } else if (basicSalary >= 3120000 && basicSalary <3140000) {incomeTax = 84620;
            } else if (basicSalary >= 3140000 && basicSalary <3160000) {incomeTax = 86330;
            } else if (basicSalary >= 3160000 && basicSalary <3180000) {incomeTax = 88040;
            } else if (basicSalary >= 3180000 && basicSalary <3200000) {incomeTax = 89750;
            } else if (basicSalary >= 3200000 && basicSalary <3220000) {incomeTax = 91460;
            } else if (basicSalary >= 3220000 && basicSalary <3240000) {incomeTax = 93170;
            } else if (basicSalary >= 3240000 && basicSalary <3260000) {incomeTax = 95430;
            } else if (basicSalary >= 3260000 && basicSalary <3280000) {incomeTax = 97880;
            } else if (basicSalary >= 3280000 && basicSalary <3300000) {incomeTax = 100320;
            } else if (basicSalary >= 3300000 && basicSalary <3320000) {incomeTax = 102770;
            } else if (basicSalary >= 3320000 && basicSalary <3340000) {incomeTax = 105210;
            } else if (basicSalary >= 3340000 && basicSalary <3360000) {incomeTax = 107660;
            } else if (basicSalary >= 3360000 && basicSalary <3380000) {incomeTax = 110100;
            } else if (basicSalary >= 3380000 && basicSalary <3400000) {incomeTax = 112550;
            } else if (basicSalary >= 3400000 && basicSalary <3420000) {incomeTax = 114990;
            } else if (basicSalary >= 3420000 && basicSalary <3440000) {incomeTax = 117440;
            } else if (basicSalary >= 3440000 && basicSalary <3460000) {incomeTax = 119880;
            } else if (basicSalary >= 3460000 && basicSalary <3480000) {incomeTax = 122330;
            } else if (basicSalary >= 3480000 && basicSalary <3500000) {incomeTax = 124770;
            } else if (basicSalary >= 3500000 && basicSalary <3520000) {incomeTax = 127220;
            } else if (basicSalary >= 3520000 && basicSalary <3540000) {incomeTax = 129660;
            } else if (basicSalary >= 3540000 && basicSalary <3560000) {incomeTax = 132110;
            } else if (basicSalary >= 3560000 && basicSalary <3580000) {incomeTax = 134550;
            } else if (basicSalary >= 3580000 && basicSalary <3600000) {incomeTax = 137000;
            } else if (basicSalary >= 3600000 && basicSalary <3620000) {incomeTax = 139440;
            } else if (basicSalary >= 3620000 && basicSalary <3640000) {incomeTax = 141890;
            } else if (basicSalary >= 3640000 && basicSalary <3660000) {incomeTax = 144330;
            } else if (basicSalary >= 3660000 && basicSalary <3680000) {incomeTax = 146780;
            } else if (basicSalary >= 3680000 && basicSalary <3700000) {incomeTax = 149220;
            } else if (basicSalary >= 3700000 && basicSalary <3720000) {incomeTax = 151670;
            } else if (basicSalary >= 3720000 && basicSalary <3740000) {incomeTax = 154110;
            } else if (basicSalary >= 3740000 && basicSalary <3760000) {incomeTax = 156560;
            } else if (basicSalary >= 3760000 && basicSalary <3780000) {incomeTax = 163920;
            } else if (basicSalary >= 3780000 && basicSalary <3800000) {incomeTax = 166590;
            } else if (basicSalary >= 3800000 && basicSalary <3820000) {incomeTax = 169260;
            } else if (basicSalary >= 3820000 && basicSalary <3840000) {incomeTax = 171930;
            } else if (basicSalary >= 3840000 && basicSalary <3860000) {incomeTax = 174600;
            } else if (basicSalary >= 3860000 && basicSalary <3880000) {incomeTax = 177270;
            } else if (basicSalary >= 3880000 && basicSalary <3900000) {incomeTax = 179940;
            } else if (basicSalary >= 3900000 && basicSalary <3920000) {incomeTax = 182610;
            } else if (basicSalary >= 3920000 && basicSalary <3940000) {incomeTax = 185280;
            } else if (basicSalary >= 3940000 && basicSalary <3960000) {incomeTax = 187950;
            } else if (basicSalary >= 3960000 && basicSalary <3980000) {incomeTax = 190620;
            } else if (basicSalary >= 3980000 && basicSalary <4000000) {incomeTax = 193290;
            } else if (basicSalary >= 4000000 && basicSalary <4020000) {incomeTax = 195960;
            } else if (basicSalary >= 4020000 && basicSalary <4040000) {incomeTax = 198630;
            } else if (basicSalary >= 4040000 && basicSalary <4060000) {incomeTax = 201300;
            } else if (basicSalary >= 4060000 && basicSalary <4080000) {incomeTax = 203970;
            } else if (basicSalary >= 4080000 && basicSalary <4100000) {incomeTax = 206640;
            } else if (basicSalary >= 4100000 && basicSalary <4120000) {incomeTax = 209310;
            } else if (basicSalary >= 4120000 && basicSalary <4140000) {incomeTax = 211980;
            } else if (basicSalary >= 4140000 && basicSalary <4160000) {incomeTax = 214650;
            } else if (basicSalary >= 4160000 && basicSalary <4180000) {incomeTax = 217320;
            } else if (basicSalary >= 4180000 && basicSalary <4200000) {incomeTax = 219990;
            } else if (basicSalary >= 4200000 && basicSalary <4220000) {incomeTax = 222660;
            } else if (basicSalary >= 4220000 && basicSalary <4240000) {incomeTax = 225330;
            } else if (basicSalary >= 4240000 && basicSalary <4260000) {incomeTax = 228000;
            } else if (basicSalary >= 4260000 && basicSalary <4280000) {incomeTax = 230670;
            } else if (basicSalary >= 4280000 && basicSalary <4300000) {incomeTax = 233340;
            } else if (basicSalary >= 4300000 && basicSalary <4320000) {incomeTax = 236010;
            } else if (basicSalary >= 4320000 && basicSalary <4340000) {incomeTax = 238680;
            } else if (basicSalary >= 4340000 && basicSalary <4360000) {incomeTax = 241350;
            } else if (basicSalary >= 4360000 && basicSalary <4380000) {incomeTax = 244020;
            } else if (basicSalary >= 4380000 && basicSalary <4400000) {incomeTax = 246690;
            } else if (basicSalary >= 4400000 && basicSalary <4420000) {incomeTax = 249360;
            } else if (basicSalary >= 4420000 && basicSalary <4440000) {incomeTax = 252030;
            } else if (basicSalary >= 4440000 && basicSalary <4460000) {incomeTax = 254700;
            } else if (basicSalary >= 4460000 && basicSalary <4480000) {incomeTax = 257370;
            } else if (basicSalary >= 4480000 && basicSalary <4500000) {incomeTax = 260040;
            } else if (basicSalary >= 4500000 && basicSalary <4520000) {incomeTax = 262840;
            } else if (basicSalary >= 4520000 && basicSalary <4540000) {incomeTax = 265650;
            } else if (basicSalary >= 4540000 && basicSalary <4560000) {incomeTax = 268450;
            } else if (basicSalary >= 4560000 && basicSalary <4580000) {incomeTax = 271260;
            } else if (basicSalary >= 4580000 && basicSalary <4600000) {incomeTax = 276560;
            } else if (basicSalary >= 4600000 && basicSalary <4620000) {incomeTax = 279370;
            } else if (basicSalary >= 4620000 && basicSalary <4640000) {incomeTax = 282170;
            } else if (basicSalary >= 4640000 && basicSalary <4660000) {incomeTax = 284980;
            } else if (basicSalary >= 4660000 && basicSalary <4680000) {incomeTax = 287780;
            } else if (basicSalary >= 4680000 && basicSalary <4700000) {incomeTax = 290590;
            } else if (basicSalary >= 4700000 && basicSalary <4720000) {incomeTax = 293390;
            } else if (basicSalary >= 4720000 && basicSalary <4740000) {incomeTax = 296200;
            } else if (basicSalary >= 4740000 && basicSalary <4760000) {incomeTax = 299000;
            } else if (basicSalary >= 4760000 && basicSalary <4780000) {incomeTax = 301810;
            } else if (basicSalary >= 4780000 && basicSalary <4800000) {incomeTax = 304610;
            } else if (basicSalary >= 4800000 && basicSalary <4820000) {incomeTax = 307420;
            } else if (basicSalary >= 4820000 && basicSalary <4840000) {incomeTax = 310220;
            } else if (basicSalary >= 4840000 && basicSalary <4860000) {incomeTax = 313030;
            } else if (basicSalary >= 4860000 && basicSalary <4880000) {incomeTax = 315830;
            } else if (basicSalary >= 4880000 && basicSalary <4900000) {incomeTax = 318640;
            } else if (basicSalary >= 4900000 && basicSalary <4920000) {incomeTax = 321440;
            } else if (basicSalary >= 4920000 && basicSalary <4940000) {incomeTax = 324250;
            } else if (basicSalary >= 4940000 && basicSalary <4960000) {incomeTax = 327050;
            } else if (basicSalary >= 4960000 && basicSalary <4980000) {incomeTax = 329860;
            } else if (basicSalary >= 4980000 && basicSalary <5000000) {incomeTax = 332660;
            } else if (basicSalary >= 5000000 && basicSalary <5020000) {incomeTax = 335470;
            } else if (basicSalary >= 5020000 && basicSalary <5040000) {incomeTax = 338270;
            } else if (basicSalary >= 5040000 && basicSalary <5060000) {incomeTax = 341080;
            } else if (basicSalary >= 5060000 && basicSalary <5080000) {incomeTax = 343880;
            } else if (basicSalary >= 5080000 && basicSalary <5100000) {incomeTax = 346690;
            } else if (basicSalary >= 5100000 && basicSalary <5120000) {incomeTax = 349490;
            } else if (basicSalary >= 5120000 && basicSalary <5140000) {incomeTax = 352300;
            } else if (basicSalary >= 5140000 && basicSalary <5160000) {incomeTax = 355100;
            } else if (basicSalary >= 5160000 && basicSalary <5180000) {incomeTax = 357910;
            } else if (basicSalary >= 5180000 && basicSalary <5200000) {incomeTax = 360710;
            } else if (basicSalary >= 5200000 && basicSalary <5220000) {incomeTax = 363520;
            } else if (basicSalary >= 5220000 && basicSalary <5240000) {incomeTax = 366320;
            } else if (basicSalary >= 5240000 && basicSalary <5260000) {incomeTax = 369130;
            } else if (basicSalary >= 5260000 && basicSalary <5280000) {incomeTax = 371930;
            } else if (basicSalary >= 5280000 && basicSalary <5300000) {incomeTax = 374740;
            } else if (basicSalary >= 5300000 && basicSalary <5320000) {incomeTax = 377540;
            } else if (basicSalary >= 5320000 && basicSalary <5340000) {incomeTax = 380350;
            } else if (basicSalary >= 5340000 && basicSalary <5360000) {incomeTax = 383150;
            } else if (basicSalary >= 5360000 && basicSalary <5380000) {incomeTax = 385960;
            } else if (basicSalary >= 5380000 && basicSalary <5400000) {incomeTax = 388760;
            } else if (basicSalary >= 5400000 && basicSalary <5420000) {incomeTax = 391570;
            } else if (basicSalary >= 5420000 && basicSalary <5440000) {incomeTax = 394370;
            } else if (basicSalary >= 5440000 && basicSalary <5460000) {incomeTax = 397180;
            } else if (basicSalary >= 5460000 && basicSalary <5480000) {incomeTax = 399980;
            } else if (basicSalary >= 5480000 && basicSalary <5500000) {incomeTax = 402790;
            } else if (basicSalary >= 5500000 && basicSalary <5520000) {incomeTax = 405590;
            } else if (basicSalary >= 5520000 && basicSalary <5540000) {incomeTax = 408400;
            } else if (basicSalary >= 5540000 && basicSalary <5560000) {incomeTax = 411200;
            } else if (basicSalary >= 5560000 && basicSalary <5580000) {incomeTax = 414010;
            } else if (basicSalary >= 5580000 && basicSalary <5600000) {incomeTax = 416810;
            } else if (basicSalary >= 5600000 && basicSalary <5620000) {incomeTax = 419620;
            } else if (basicSalary >= 5620000 && basicSalary <5640000) {incomeTax = 422420;
            } else if (basicSalary >= 5640000 && basicSalary <5660000) {incomeTax = 425230;
            } else if (basicSalary >= 5660000 && basicSalary <5680000) {incomeTax = 428030;
            } else if (basicSalary >= 5680000 && basicSalary <5700000) {incomeTax = 430840;
            } else if (basicSalary >= 5700000 && basicSalary <5720000) {incomeTax = 433640;
            } else if (basicSalary >= 5720000 && basicSalary <5740000) {incomeTax = 436450;
            } else if (basicSalary >= 5740000 && basicSalary <5760000) {incomeTax = 439250;
            } else if (basicSalary >= 5760000 && basicSalary <5780000) {incomeTax = 442060;
            } else if (basicSalary >= 5780000 && basicSalary <5800000) {incomeTax = 444860;
            } else if (basicSalary >= 5800000 && basicSalary <5820000) {incomeTax = 447670;
            } else if (basicSalary >= 5820000 && basicSalary <5840000) {incomeTax = 450470;
            } else if (basicSalary >= 5840000 && basicSalary <5860000) {incomeTax = 470380;
            } else if (basicSalary >= 5860000 && basicSalary <5880000) {incomeTax = 475720;
            } else if (basicSalary >= 5880000 && basicSalary <5900000) {incomeTax = 478690;
            } else if (basicSalary >= 5900000 && basicSalary <5920000) {incomeTax = 483220;
            } else if (basicSalary >= 5920000 && basicSalary <5940000) {incomeTax = 487760;
            } else if (basicSalary >= 5940000 && basicSalary <5960000) {incomeTax = 492300;
            } else if (basicSalary >= 5960000 && basicSalary <5980000) {incomeTax = 496830;
            } else if (basicSalary >= 5980000 && basicSalary <6000000) {incomeTax = 501370;
            } else if (basicSalary >= 6000000 && basicSalary <6020000) {incomeTax = 505900;
            } else if (basicSalary >= 6020000 && basicSalary <6040000) {incomeTax = 510440;
            } else if (basicSalary >= 6040000 && basicSalary <6060000) {incomeTax = 514980;
            } else if (basicSalary >= 6060000 && basicSalary <6080000) {incomeTax = 519510;
            } else if (basicSalary >= 6080000 && basicSalary <6100000) {incomeTax = 524050;
            } else if (basicSalary >= 6100000 && basicSalary <6120000) {incomeTax = 528580;
            } else if (basicSalary >= 6120000 && basicSalary <6140000) {incomeTax = 533120;
            } else if (basicSalary >= 6140000 && basicSalary <6160000) {incomeTax = 537660;
            } else if (basicSalary >= 6160000 && basicSalary <6180000) {incomeTax = 542190;
            } else if (basicSalary >= 6180000 && basicSalary <6200000) {incomeTax = 546730;
            } else if (basicSalary >= 6200000 && basicSalary <6220000) {incomeTax = 551260;
            } else if (basicSalary >= 6220000 && basicSalary <6240000) {incomeTax = 555800;
            } else if (basicSalary >= 6240000 && basicSalary <6260000) {incomeTax = 560340;
            } else if (basicSalary >= 6260000 && basicSalary <6280000) {incomeTax = 564870;
            } else if (basicSalary >= 6280000 && basicSalary <6300000) {incomeTax = 569410;
            } else if (basicSalary >= 6300000 && basicSalary <6320000) {incomeTax = 573940;
            } else if (basicSalary >= 6320000 && basicSalary <6340000) {incomeTax = 578480;
            } else if (basicSalary >= 6340000 && basicSalary <6360000) {incomeTax = 583020;
            } else if (basicSalary >= 6360000 && basicSalary <6380000) {incomeTax = 587550;
            } else if (basicSalary >= 6380000 && basicSalary <6400000) {incomeTax = 592090;
            } else if (basicSalary >= 6400000 && basicSalary <6420000) {incomeTax = 596620;
            } else if (basicSalary >= 6420000 && basicSalary <6440000) {incomeTax = 601160;
            } else if (basicSalary >= 6440000 && basicSalary <6460000) {incomeTax = 605700;
            } else if (basicSalary >= 6460000 && basicSalary <6480000) {incomeTax = 610230;
            } else if (basicSalary >= 6480000 && basicSalary <6500000) {incomeTax = 614770;
            } else if (basicSalary >= 6500000 && basicSalary <6520000) {incomeTax = 619300;
            } else if (basicSalary >= 6520000 && basicSalary <6540000) {incomeTax = 623840;
            } else if (basicSalary >= 6540000 && basicSalary <6560000) {incomeTax = 628380;
            } else if (basicSalary >= 6560000 && basicSalary <6580000) {incomeTax = 632910;
            } else if (basicSalary >= 6580000 && basicSalary <6600000) {incomeTax = 637450;
            } else if (basicSalary >= 6600000 && basicSalary <6620000) {incomeTax = 641980;
            } else if (basicSalary >= 6620000 && basicSalary <6640000) {incomeTax = 646520;
            } else if (basicSalary >= 6640000 && basicSalary <6660000) {incomeTax = 651060;
            } else if (basicSalary >= 6660000 && basicSalary <6680000) {incomeTax = 655590;
            } else if (basicSalary >= 6680000 && basicSalary <6700000) {incomeTax = 660130;
            } else if (basicSalary >= 6700000 && basicSalary <6720000) {incomeTax = 664660;
            } else if (basicSalary >= 6720000 && basicSalary <6740000) {incomeTax = 669200;
            } else if (basicSalary >= 6740000 && basicSalary <6760000) {incomeTax = 673740;
            } else if (basicSalary >= 6760000 && basicSalary <6780000) {incomeTax = 678270;
            } else if (basicSalary >= 6780000 && basicSalary <6800000) {incomeTax = 682810;
            } else if (basicSalary >= 6800000 && basicSalary <6820000) {incomeTax = 687340;
            } else if (basicSalary >= 6820000 && basicSalary <6840000) {incomeTax = 691880;
            } else if (basicSalary >= 6840000 && basicSalary <6860000) {incomeTax = 696420;
            } else if (basicSalary >= 6860000 && basicSalary <6880000) {incomeTax = 700950;
            } else if (basicSalary >= 6880000 && basicSalary <6900000) {incomeTax = 705490;
            } else if (basicSalary >= 6900000 && basicSalary <6920000) {incomeTax = 710020;
            } else if (basicSalary >= 6920000 && basicSalary <6940000) {incomeTax = 714560;
            } else if (basicSalary >= 6940000 && basicSalary <6960000) {incomeTax = 719100;
            } else if (basicSalary >= 6960000 && basicSalary <6980000) {incomeTax = 723630;
            } else if (basicSalary >= 6980000 && basicSalary <7000000) {incomeTax = 728170;
            } else if (basicSalary >= 7000000 && basicSalary <7020000) {incomeTax = 732700;
            } else if (basicSalary >= 7020000 && basicSalary <7040000) {incomeTax = 737240;
            } else if (basicSalary >= 7040000 && basicSalary <7060000) {incomeTax = 741780;
            } else if (basicSalary >= 7060000 && basicSalary <7080000) {incomeTax = 746310;
            } else if (basicSalary >= 7080000 && basicSalary <7100000) {incomeTax = 750850;
            } else if (basicSalary >= 7100000 && basicSalary <7120000) {incomeTax = 755380;
            } else if (basicSalary >= 7120000 && basicSalary <7140000) {incomeTax = 759920;
            } else if (basicSalary >= 7140000 && basicSalary <7160000) {incomeTax = 764460;
            } else if (basicSalary >= 7160000 && basicSalary <7180000) {incomeTax = 768990;
            } else if (basicSalary >= 7180000 && basicSalary <7200000) {incomeTax = 773530;
            } else if (basicSalary >= 7200000 && basicSalary <7220000) {incomeTax = 778060;
            } else if (basicSalary >= 7220000 && basicSalary <7240000) {incomeTax = 782600;
            } else if (basicSalary >= 7240000 && basicSalary <7260000) {incomeTax = 787140;
            } else if (basicSalary >= 7260000 && basicSalary <7280000) {incomeTax = 791670;
            } else if (basicSalary >= 7280000 && basicSalary <7300000) {incomeTax = 796210;
            } else if (basicSalary >= 7300000 && basicSalary <7320000) {incomeTax = 800740;
            } else if (basicSalary >= 7320000 && basicSalary <7340000) {incomeTax = 805280;
            } else if (basicSalary >= 7340000 && basicSalary <7360000) {incomeTax = 809820;
            } else if (basicSalary >= 7360000 && basicSalary <7380000) {incomeTax = 814350;
            } else if (basicSalary >= 7380000 && basicSalary <7400000) {incomeTax = 818890;
            } else if (basicSalary >= 7400000 && basicSalary <7420000) {incomeTax = 823420;
            } else if (basicSalary >= 7420000 && basicSalary <7440000) {incomeTax = 827960;
            } else if (basicSalary >= 7440000 && basicSalary <7460000) {incomeTax = 832500;
            } else if (basicSalary >= 7460000 && basicSalary <7480000) {incomeTax = 837030;
            } else if (basicSalary >= 7480000 && basicSalary <7500000) {incomeTax = 841570;
            } else if (basicSalary >= 7500000 && basicSalary <7520000) {incomeTax = 846100;
            } else if (basicSalary >= 7520000 && basicSalary <7540000) {incomeTax = 850640;
            } else if (basicSalary >= 7540000 && basicSalary <7560000) {incomeTax = 855180;
            } else if (basicSalary >= 7560000 && basicSalary <7580000) {incomeTax = 859710;
            } else if (basicSalary >= 7580000 && basicSalary <7600000) {incomeTax = 864250;
            } else if (basicSalary >= 7600000 && basicSalary <7620000) {incomeTax = 868780;
            } else if (basicSalary >= 7620000 && basicSalary <7640000) {incomeTax = 873320;
            } else if (basicSalary >= 7640000 && basicSalary <7660000) {incomeTax = 877860;
            } else if (basicSalary >= 7660000 && basicSalary <7680000) {incomeTax = 882390;
            } else if (basicSalary >= 7680000 && basicSalary <7700000) {incomeTax = 886930;
            } else if (basicSalary >= 7700000 && basicSalary <7720000) {incomeTax = 891460;
            } else if (basicSalary >= 7720000 && basicSalary <7740000) {incomeTax = 896000;
            } else if (basicSalary >= 7740000 && basicSalary <7760000) {incomeTax = 900540;
            } else if (basicSalary >= 7760000 && basicSalary <7780000) {incomeTax = 905070;
            } else if (basicSalary >= 7780000 && basicSalary <7800000) {incomeTax = 909610;
            } else if (basicSalary >= 7800000 && basicSalary <7820000) {incomeTax = 914140;
            } else if (basicSalary >= 7820000 && basicSalary <7840000) {incomeTax = 918680;
            } else if (basicSalary >= 7840000 && basicSalary <7860000) {incomeTax = 923220;
            } else if (basicSalary >= 7860000 && basicSalary <7880000) {incomeTax = 927750;
            } else if (basicSalary >= 7880000 && basicSalary <7900000) {incomeTax = 932290;
            } else if (basicSalary >= 7900000 && basicSalary <7920000) {incomeTax = 936820;
            } else if (basicSalary >= 7920000 && basicSalary <7940000) {incomeTax = 941360;
            } else if (basicSalary >= 7940000 && basicSalary <7960000) {incomeTax = 945900;
            } else if (basicSalary >= 7960000 && basicSalary <7980000) {incomeTax = 950430;
            } else if (basicSalary >= 7980000 && basicSalary <8000000) {incomeTax = 954970;
            } else if (basicSalary >= 8000000 && basicSalary <8020000) {incomeTax = 959500;
            } else if (basicSalary >= 8020000 && basicSalary <8040000) {incomeTax = 964040;
            } else if (basicSalary >= 8040000 && basicSalary <8060000) {incomeTax = 968580;
            } else if (basicSalary >= 8060000 && basicSalary <8080000) {incomeTax = 973110;
            } else if (basicSalary >= 8080000 && basicSalary <8100000) {incomeTax = 977650;
            } else if (basicSalary >= 8100000 && basicSalary <8120000) {incomeTax = 982180;
            } else if (basicSalary >= 8120000 && basicSalary <8140000) {incomeTax = 986720;
            } else if (basicSalary >= 8140000 && basicSalary <8160000) {incomeTax = 991260;
            } else if (basicSalary >= 8160000 && basicSalary <8180000) {incomeTax = 995790;
            } else if (basicSalary >= 8180000 && basicSalary <8200000) {incomeTax = 1000330;
            } else if (basicSalary >= 8200000 && basicSalary <8220000) {incomeTax = 1004860;
            } else if (basicSalary >= 8220000 && basicSalary <8240000) {incomeTax = 1009400;
            } else if (basicSalary >= 8240000 && basicSalary <8260000) {incomeTax = 1013940;
            } else if (basicSalary >= 8260000 && basicSalary <8280000) {incomeTax = 1018470;
            } else if (basicSalary >= 8280000 && basicSalary <8300000) {incomeTax = 1023010;
            } else if (basicSalary >= 8300000 && basicSalary <8320000) {incomeTax = 1027540;
            } else if (basicSalary >= 8320000 && basicSalary <8340000) {incomeTax = 1032080;
            } else if (basicSalary >= 8340000 && basicSalary <8360000) {incomeTax = 1036740;
            } else if (basicSalary >= 8360000 && basicSalary <8380000) {incomeTax = 1041420;
            } else if (basicSalary >= 8380000 && basicSalary <8400000) {incomeTax = 1046100;
            } else if (basicSalary >= 8400000 && basicSalary <8420000) {incomeTax = 1050780;
            } else if (basicSalary >= 8420000 && basicSalary <8440000) {incomeTax = 1055460;
            } else if (basicSalary >= 8440000 && basicSalary <8460000) {incomeTax = 1060140;
            } else if (basicSalary >= 8460000 && basicSalary <8480000) {incomeTax = 1064820;
            } else if (basicSalary >= 8480000 && basicSalary <8500000) {incomeTax = 1069500;
            } else if (basicSalary >= 8500000 && basicSalary <8520000) {incomeTax = 1074180;
            } else if (basicSalary >= 8520000 && basicSalary <8540000) {incomeTax = 1078860;
            } else if (basicSalary >= 8540000 && basicSalary <8560000) {incomeTax = 1083540;
            } else if (basicSalary >= 8560000 && basicSalary <8580000) {incomeTax = 1088220;
            } else if (basicSalary >= 8580000 && basicSalary <8600000) {incomeTax = 1092900;
            } else if (basicSalary >= 8600000 && basicSalary <8620000) {incomeTax = 1097580;
            } else if (basicSalary >= 8620000 && basicSalary <8640000) {incomeTax = 1102260;
            } else if (basicSalary >= 8640000 && basicSalary <8660000) {incomeTax = 1106940;
            } else if (basicSalary >= 8660000 && basicSalary <8680000) {incomeTax = 1111620;
            } else if (basicSalary >= 8680000 && basicSalary <8700000) {incomeTax = 1116300;
            } else if (basicSalary >= 8700000 && basicSalary <8720000) {incomeTax = 1120980;
            } else if (basicSalary >= 8720000 && basicSalary <8740000) {incomeTax = 1125660;
            } else if (basicSalary >= 8740000 && basicSalary <8760000) {incomeTax = 1130340;
            } else if (basicSalary >= 8760000 && basicSalary <8780000) {incomeTax = 1135020;
            } else if (basicSalary >= 8780000 && basicSalary <8800000) {incomeTax = 1139700;
            } else if (basicSalary >= 8800000 && basicSalary <8820000) {incomeTax = 1144380;
            } else if (basicSalary >= 8820000 && basicSalary <8840000) {incomeTax = 1149060;
            } else if (basicSalary >= 8840000 && basicSalary <8860000) {incomeTax = 1153740;
            } else if (basicSalary >= 8860000 && basicSalary <8880000) {incomeTax = 1158420;
            } else if (basicSalary >= 8880000 && basicSalary <8900000) {incomeTax = 1163100;
            } else if (basicSalary >= 8900000 && basicSalary <8920000) {incomeTax = 1167780;
            } else if (basicSalary >= 8920000 && basicSalary <8940000) {incomeTax = 1172460;
            } else if (basicSalary >= 8940000 && basicSalary <8960000) {incomeTax = 1177140;
            } else if (basicSalary >= 8960000 && basicSalary <8980000) {incomeTax = 1181820;
            } else if (basicSalary >= 8980000 && basicSalary <9000000) {incomeTax = 1186500;
            } else if (basicSalary >= 9000000 && basicSalary <9020000) {incomeTax = 1191180;
            } else if (basicSalary >= 9020000 && basicSalary <9040000) {incomeTax = 1195860;
            } else if (basicSalary >= 9040000 && basicSalary <9060000) {incomeTax = 1200540;
            } else if (basicSalary >= 9060000 && basicSalary <9080000) {incomeTax = 1205220;
            } else if (basicSalary >= 9080000 && basicSalary <9100000) {incomeTax = 1209900;
            } else if (basicSalary >= 9100000 && basicSalary <9120000) {incomeTax = 1214580;
            } else if (basicSalary >= 9120000 && basicSalary <9140000) {incomeTax = 1219260;
            } else if (basicSalary >= 9140000 && basicSalary <9160000) {incomeTax = 1223940;
            } else if (basicSalary >= 9160000 && basicSalary <9180000) {incomeTax = 1228620;
            } else if (basicSalary >= 9180000 && basicSalary <9200000) {incomeTax = 1233300;
            } else if (basicSalary >= 9200000 && basicSalary <9220000) {incomeTax = 1237980;
            } else if (basicSalary >= 9220000 && basicSalary <9240000) {incomeTax = 1244640;
            } else if (basicSalary >= 9240000 && basicSalary <9260000) {incomeTax = 1251470;
            } else if (basicSalary >= 9260000 && basicSalary <9280000) {incomeTax = 1258290;
            } else if (basicSalary >= 9280000 && basicSalary <9300000) {incomeTax = 1265120;
            } else if (basicSalary >= 9300000 && basicSalary <9320000) {incomeTax = 1271940;
            } else if (basicSalary >= 9320000 && basicSalary <9340000) {incomeTax = 1278770;
            } else if (basicSalary >= 9340000 && basicSalary <9360000) {incomeTax = 1285590;
            } else if (basicSalary >= 9360000 && basicSalary <9380000) {incomeTax = 1292420;
            } else if (basicSalary >= 9380000 && basicSalary <9400000) {incomeTax = 1299240;
            } else if (basicSalary >= 9400000 && basicSalary <9420000) {incomeTax = 1306070;
            } else if (basicSalary >= 9420000 && basicSalary <9440000) {incomeTax = 1312890;
            } else if (basicSalary >= 9440000 && basicSalary <9460000) {incomeTax = 1319720;
            } else if (basicSalary >= 9460000 && basicSalary <9480000) {incomeTax = 1326540;
            } else if (basicSalary >= 9480000 && basicSalary <9500000) {incomeTax = 1333370;
            } else if (basicSalary >= 9500000 && basicSalary <9520000) {incomeTax = 1340190;
            } else if (basicSalary >= 9520000 && basicSalary <9540000) {incomeTax = 1347020;
            } else if (basicSalary >= 9540000 && basicSalary <9560000) {incomeTax = 1353840;
            } else if (basicSalary >= 9560000 && basicSalary <9580000) {incomeTax = 1360670;
            } else if (basicSalary >= 9580000 && basicSalary <9600000) {incomeTax = 1367490;
            } else if (basicSalary >= 9600000 && basicSalary <9620000) {incomeTax = 1374320;
            } else if (basicSalary >= 9620000 && basicSalary <9640000) {incomeTax = 1381140;
            } else if (basicSalary >= 9640000 && basicSalary <9660000) {incomeTax = 1387970;
            } else if (basicSalary >= 9660000 && basicSalary <9680000) {incomeTax = 1394790;
            } else if (basicSalary >= 9680000 && basicSalary <9700000) {incomeTax = 1401620;
            } else if (basicSalary >= 9700000 && basicSalary <9720000) {incomeTax = 1408440;
            } else if (basicSalary >= 9720000 && basicSalary <9740000) {incomeTax = 1415270;
            } else if (basicSalary >= 9740000 && basicSalary <9760000) {incomeTax = 1422090;
            } else if (basicSalary >= 9760000 && basicSalary <9780000) {incomeTax = 1428920;
            } else if (basicSalary >= 9780000 && basicSalary <9800000) {incomeTax = 1435740;
            } else if (basicSalary >= 9800000 && basicSalary <9820000) {incomeTax = 1442570;
            } else if (basicSalary >= 9820000 && basicSalary <9840000) {incomeTax = 1449390;
            } else if (basicSalary >= 9840000 && basicSalary <9860000) {incomeTax = 1456220;
            } else if (basicSalary >= 9860000 && basicSalary <9880000) {incomeTax = 1463040;
            } else if (basicSalary >= 9880000 && basicSalary <9900000) {incomeTax = 1469870;
            } else if (basicSalary >= 9900000 && basicSalary <9920000) {incomeTax = 1476690;
            } else if (basicSalary >= 9920000 && basicSalary <9940000) {incomeTax = 1483520;
            } else if (basicSalary >= 9940000 && basicSalary <9960000) {incomeTax = 1490340;
            } else if (basicSalary >= 9960000 && basicSalary <9980000) {incomeTax = 1497170;
            } else if (basicSalary >= 9980000 && basicSalary <10000000) {incomeTax = 1503990;
            }else {incomeTax = 3822980;}


            int localIncomeTax = (int) (incomeTax * 0.1); //지방소득세
            int medicalInsurance = (int) (pdeSalary * 0.004591); //장기요양보험료
            int deductedAmount = nationalPension + healthInsurance + employmentInsurance + incomeTax + localIncomeTax + medicalInsurance;
            int totalDeduction = basicSalary - deductedAmount; //총 공제 금액
            totalDdeSalary += pdeSalary; //1년 지급 총액
            totalDeductedAmount += deductedAmount;  //1년 총 공제 총액
            totalDeductions += totalDeduction; //1년 총 차감지급금액


            payDetailsDTOList.get(i).setBasicSalary(basicSalary);
            payDetailsDTOList.get(i).setFoodExpenses(200000);
            payDetailsDTOList.get(i).setNationalPension(nationalPension);
            payDetailsDTOList.get(i).setHealthInsurance(healthInsurance);
            payDetailsDTOList.get(i).setEmploymentInsurance(employmentInsurance);
            payDetailsDTOList.get(i).setIncomeTax(incomeTax);
            payDetailsDTOList.get(i).setLocalIncomeTax(localIncomeTax);
            payDetailsDTOList.get(i).setMedicalInsurance(medicalInsurance);
            payDetailsDTOList.get(i).setDeductedAmount(deductedAmount);
            payDetailsDTOList.get(i).setTotalDeduction(totalDeduction);
            payDetailsDTOList.get(i).setTotalDdeSalary(totalDdeSalary);
            payDetailsDTOList.get(i).setTotalDeductedAmount(totalDeductedAmount);
            payDetailsDTOList.get(i).setTotalDeductions(totalDeductions);
        }
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


    public List<Integer > hireYearList(int memCode) {
        log.info("hireYearList 서비스 시작~~~~~~~~~~~~~~~~");
        PayMember details = memberRepository.findAllByMemCode(memCode);
        System.out.println("details = " + details);

        PayMemberDTO memberDTO = modelMapper.map(details, PayMemberDTO.class);

        String hireYear = memberDTO.getMemHireDate();
        int startYear = Integer.parseInt(hireYear.substring(0, 4)); // 입사 연도 추출
        int currentYear = LocalDate.now().getYear(); // 현재 연도 가져오기

        List<Integer> resultYearList = new ArrayList<>();

        for (int year = startYear; year <= currentYear; year++) {
            resultYearList.add(year); // 입사 연도부터 현재 연도까지의 연도 목록 생성
            log.info(resultYearList.toString());
        }


        log.info("searchYear 서비스 끗~~~~~~~~~~~~~~~~");



        return resultYearList;

    }
}
