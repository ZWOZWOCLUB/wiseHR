CREATE EVENT IF NOT EXISTS UpdateNewYearVacationOver1year  #여기서부터 새로운 이벤트 생성 IF NOT EXISTS로 해당 이름의 이벤트가 존재하지않을 경우에만 이벤트 생성
    ON SCHEDULE
        EVERY 1 YEAR  # 수행 반복할 시간 여기선 매월
            STARTS TIMESTAMP(DATE_FORMAT(NOW(), '%Y-01-01 09:00:00'))  # 매년 1월 1일 09시에 실행
    enable
    comment '근무일 1년 이상 결근 없는 사람 update 연차는 기본 15, 3년차부터 2년단위로 +1(3년차에 16개, 5년차에 17개, 7년차에 18개로 최대 25개)'
    DO                                                 #이벤트가 실행될 때 수행할 작업 정의 UPDATE부터는 쿼리문임
UPDATE hold_vacation C
    JOIN member A ON C.mem_code = A.mem_code
    JOIN attendance B ON A.mem_code = B.mem_code
    SET C.vct_deadline = C.vct_count,
        C.vct_count = case
        when (DATEDIFF(CURDATE(), B.att_work_date) < 1095) then 15
        when (DATEDIFF(CURDATE(), B.att_work_date) >= 1095 AND DATEDIFF(CURDATE(), B.att_work_date) < 1825) then 16
        when (DATEDIFF(CURDATE(), B.att_work_date) >= 1825 AND DATEDIFF(CURDATE(), B.att_work_date) < 2555) then 17
        when (DATEDIFF(CURDATE(), B.att_work_date) >= 3285 AND DATEDIFF(CURDATE(), B.att_work_date) < 3285) then 18
        when (DATEDIFF(CURDATE(), B.att_work_date) >= 4015 AND DATEDIFF(CURDATE(), B.att_work_date) < 4015) then 19
        when (DATEDIFF(CURDATE(), B.att_work_date) >= 4745 AND DATEDIFF(CURDATE(), B.att_work_date) < 4745) then 20
        when (DATEDIFF(CURDATE(), B.att_work_date) >= 5475 AND DATEDIFF(CURDATE(), B.att_work_date) < 5475) then 21
        when (DATEDIFF(CURDATE(), B.att_work_date) >= 6205 AND DATEDIFF(CURDATE(), B.att_work_date) < 6205) then 22
        when (DATEDIFF(CURDATE(), B.att_work_date) >= 6935 AND DATEDIFF(CURDATE(), B.att_work_date) < 6935) then 23
        when (DATEDIFF(CURDATE(), B.att_work_date) >= 7665 AND DATEDIFF(CURDATE(), B.att_work_date) < 7665) then 24
        else 25
        end
            WHERE A.mem_status = 'N'
#                 AND B.att_status <> '결근'
#                 AND B.att_work_date BETWEEN DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 YEAR), '%Y-01-01')
                AND DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 YEAR), '%Y-12-31')
                AND DATEDIFF(CURDATE(), B.att_work_date) >= 365;
SELECT * FROM INFORMATION_SCHEMA.EVENTS WHERE EVENT_NAME = 'UpdateNewYearVacationOver1year'; #생성된 이벤트가 정상적으로 등록되었는지 확인하기 위해 .EVENT 테이블 조회하는 쿼리