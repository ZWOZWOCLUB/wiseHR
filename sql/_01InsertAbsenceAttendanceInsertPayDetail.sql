CREATE EVENT IF NOT EXISTS InsertAbsenceAttendanceInsertPayDetailEvent  #여기서부터 새로운 이벤트 생성 IF NOT EXISTS로 해당 이름의 이벤트가 존재하지않을 경우에만 이벤트 생성
    ON SCHEDULE
        EVERY 1 MONTH  # 수행 반복할 시간 여기선 매월
            STARTS TIMESTAMP(DATE_FORMAT(NOW(), '%Y-%m-25 09:00:00'))  # 25일 09시에 실행
    enable
    comment '결근 있는 사람 - 매월 25일 급여내역 insert ((pos_salary / 12)/((지난달 첫날 ~ 마지막날 수) - 결근일수)'
    DO
    INSERT INTO paydetails (pde_date, pde_salary, pde_yymm, mem_code)
    SELECT
        DATE_FORMAT(NOW(), '%Y-%m-25'),
        (B.pos_salary / 12) / (DATEDIFF(LAST_DAY(DATE_SUB(NOW(), INTERVAL 1 MONTH)), DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH), '%Y-%m-01')) + 1) *
        ((DATEDIFF(LAST_DAY(DATE_SUB(NOW(), INTERVAL 1 MONTH)), DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH), '%Y-%m-01')) + 1)
            - COUNT(CASE WHEN C.att_status = '결근' THEN 1 END)),
        DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH), '%Y-%m-01'),
        A.mem_code
    FROM member A
        JOIN position B ON A.pos_code = B.pos_code
        JOIN attendance C ON A.mem_code = C.mem_code
    WHERE A.mem_status = 'N'
        AND C.att_work_date BETWEEN DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH), '%Y-%m-01') AND LAST_DAY(DATE_SUB(NOW(), INTERVAL 1 MONTH))
        AND C.att_status = '결근';
SELECT * FROM INFORMATION_SCHEMA.EVENTS WHERE EVENT_NAME = 'InsertAbsenceAttendanceInsertPayDetailEvent';