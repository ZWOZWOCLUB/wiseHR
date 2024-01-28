CREATE EVENT IF NOT EXISTS UpdateNewMonthVacationLessthen1year
    ON SCHEDULE
        EVERY 1 MONTH  # 수행 반복할 시간 여기선 매월
            STARTS TIMESTAMP(DATE_FORMAT(NOW(), '%Y-%m-01 09:00:00'))
    enable
    comment '근무일 1년 미만인 결근 없는 사람 연차 update'
    DO
    UPDATE hold_vacation C
        JOIN member A ON C.mem_code = A.mem_code
        JOIN attendance B ON A.mem_code = B.mem_code
        SET C.vct_count = C.vct_count + 1
    WHERE A.mem_status = 'N'
        AND YEAR(A.mem_hire_date) = YEAR(CURDATE())
        AND C.att_status <> '결근'
        AND B.att_work_date BETWEEN DATE_FORMAT(CURDATE(), '%Y-01-01') AND DATE_FORMAT(CURDATE(), '%Y-12-31');
SELECT * FROM INFORMATION_SCHEMA.EVENTS WHERE EVENT_NAME = 'UpdateNewMonthVacationLessthen1year';