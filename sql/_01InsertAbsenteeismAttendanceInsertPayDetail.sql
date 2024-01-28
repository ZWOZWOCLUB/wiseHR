CREATE EVENT IF NOT EXISTS InsertAbsenteeismAttendanceInsertPayDetailEvent  #여기서부터 새로운 이벤트 생성 IF NOT EXISTS로 해당 이름의 이벤트가 존재하지않을 경우에만 이벤트 생성
    ON SCHEDULE
        EVERY 1 MONTH  # 수행 반복할 시간 여기선 매월
            STARTS TIMESTAMP(DATE_FORMAT(NOW(), '%Y-%m-25 09:00:00'))  # 25일 09시에 실행
    enable
    comment '결근 있는 사람 - 매월 25일 급여내역 insert ((연봉 /12) / 지난달 날짜수) * 결근일을 뺀 근무일 '
    DO
    INSERT INTO paydetails (pde_date, pde_salary, pde_yymm, mem_code)
    SELECT
        DATE_FORMAT(NOW(), '%Y-%m-25'),
        ((B.pos_salary / 12) / (DATE_FORMAT(LAST_DAY(DATE_SUB(NOW(), INTERVAL 1 MONTH)), '%d'))) *
#        (pos_salary / 12) / (지난달 마지막날짜 찾음) *
        #DATEDIFF는 두 날짜간의 차이 구하는 함수 DATEDIFF(계산할 단위(DAY, MONTH, YEAR), 시작 날짜, 종료날짜), LAST_DAY는 이번달 마지막 날짜 반환
#        DATE_SUB는 기준날짜에서 일정 기간을 뺀 날짜 계산 DATE_SUB(기준날짜, 밸 기간 수치, 뺄 기간 단위(DAY, MONTH, YEAR)
#        INTERVAL은 날짜나 시간 간격 더하거나 뺄때 사용
        ((DATE_FORMAT(LAST_DAY(DATE_SUB(NOW(), INTERVAL 1 MONTH)), '%d'))
            - COUNT(CASE WHEN C.att_status = '결근' THEN 1 END)),
        DATE_FORMAT(LAST_DAY(DATE_SUB(NOW(), INTERVAL 1 MONTH)), '%Y-%m-%d'),
        A.mem_code
    FROM member A
        JOIN position B ON A.pos_code = B.pos_code
        JOIN attendance C ON A.mem_code = C.mem_code
    WHERE A.mem_status = 'N'
        AND C.att_work_date BETWEEN DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH), '%Y-%m-01') AND LAST_DAY(DATE_SUB(NOW(), INTERVAL 1 MONTH))
        AND C.att_status = '결근'
GROUP BY A.mem_code;
SELECT * FROM INFORMATION_SCHEMA.EVENTS WHERE EVENT_NAME = 'InsertAbsenteeismAttendanceInsertPayDetailEvent';