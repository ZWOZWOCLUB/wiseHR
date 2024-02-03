package com.wisehr.wisehr.main.service;

import com.wisehr.wisehr.main.entity.MainAppCom;
import com.wisehr.wisehr.main.entity.MainMember;
import com.wisehr.wisehr.main.repository.MainAppComRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Slf4j
public class MainService {


    private final MainAppComRepository mainAppComRepository;

    public MainService(MainAppComRepository mainAppComRepository) {
        this.mainAppComRepository = mainAppComRepository;
    }

    public Map<String, Integer> selectApprovalCount(Long memCode) {

        log.info("[MainService] selectApprovalCount start------------");

        // LocalDate.now()를 통해 현재날짜를 가져와서 currentDate 변수에 저장
        LocalDate currentDate = LocalDate.now();
        //endDate에서 minus() 메소드를 사용해서 7일을 빼는 연산 수행하여 그 결과값을 sevenDaysBefore에 저장
        LocalDate sevenDaysBefore = currentDate.minus(100, ChronoUnit.DAYS); //예시로 100일 범위로 일단..실제는 7일 범위로 변경할 것.

        log.info("[MainService] currentDate : {}", currentDate);
        log.info("[MainService] sevenDaysBefore : {}", sevenDaysBefore);

        MainMember mainMember = new MainMember();
        mainMember.setMemCode(memCode); //멤버 엔티티에 현재 멤버코드 저장

        //조회하고자하는 상태값을 배열로 저장하여 state를 쿼리메소드에 넘겨주기
        List<String> states = Arrays.asList("승인", "반려", "대기");
        Map<String, Integer> count = new HashMap<>();

        //날짜에 따른 상태값 가져와서 app에 저장
        for(String state : states) {
            List<MainAppCom> app = mainAppComRepository.findByMainMemberAndAppStateAndAppDateBetween(mainMember, state, sevenDaysBefore, currentDate);
            int result = app.size(); //result 는 가져온 데이터의 사이즈
            count.put(state, result); //반복문 실행할 때마다 state 에 값 저장

            log.info("[MainService] app : {} ", app);
            log.info("[MainService] result : {} ", result);
        }

        int appCompletedCount = count.get("승인");
        int appDeniedCount = count.get("반려");
        int appWaitCount = count.get("대기");

        log.info("[MainService] appCompletedCount = " + appCompletedCount);
        log.info("[MainService] appDeniedCount = " + appDeniedCount);
        log.info("[MainService] appWaitCount = " + appWaitCount);

        log.info("[MainService] selectApprovalCount end------------");

        return count;
    }
}
