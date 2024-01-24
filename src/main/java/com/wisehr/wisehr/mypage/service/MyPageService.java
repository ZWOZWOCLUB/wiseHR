package com.wisehr.wisehr.mypage.service;

import com.wisehr.wisehr.common.Criteria;
import com.wisehr.wisehr.mypage.repository.MyPageRepository;
import com.wisehr.wisehr.setting.dto.SettingMemberDTO;
import com.wisehr.wisehr.setting.entity.SettingMember;
import com.wisehr.wisehr.setting.repository.SettingMemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class MyPageService {
    private final SettingMemberRepository settingMemberRepository;
    private final MyPageRepository myPageRepository;

    private final ModelMapper modelMapper;

    public MyPageService(SettingMemberRepository settingMemberRepository, MyPageRepository myPageRepository, ModelMapper modelMapper) {
        this.settingMemberRepository = settingMemberRepository;
        this.myPageRepository = myPageRepository;
        this.modelMapper = modelMapper;
    }


    public SettingMemberDTO selectMem(int memCode) {

        SettingMember settingMember = settingMemberRepository.findById(memCode).get();
        SettingMemberDTO settingMemberDTO = modelMapper.map(settingMember, SettingMemberDTO.class);

        return settingMemberDTO;

    }

    @Transactional
    public String insertMember(SettingMemberDTO settingMemberDTO) {
        log.info("insertMember Start~~~~~~~~~~~~");
        int result = 0;

        try{
            SettingMember inserMember = modelMapper.map(settingMemberDTO, SettingMember.class);

        settingMemberRepository.save(inserMember);
        result = 1;
        }catch(Exception e) {
            log.info("오류~~~~~~~");
            throw new RuntimeException(e);
        }

        return (result > 0)? "등록 성공": "등록 실패";
    }

    public Page<SettingMemberDTO> allMemberSearchWithPaging(Criteria cri) {

        log.info("allMemberSearchWithPaging 서비스 시작~~~~~~~~~~~~");
        int index = cri.getPageNum() -1;

        int count = cri.getAmount();
        List<String> memStatusList = Arrays.asList("Y", "N");

        Pageable paging = PageRequest.of(index, count, Sort.by("memberCode").descending());

        Page<SettingMember> result = settingMemberRepository.findByMemStatusIn(memStatusList, paging);

        Page<SettingMemberDTO> memberList = result.map(member -> modelMapper.map(member, SettingMemberDTO.class));

        return memberList;
    }
}
