CREATE EVENT IF NOT EXISTS InsertNewYearVacationLessthen1year
    ON SCHEDULE
        EVERY 1 YEAR
            STARTS TIMESTAMP(DATE_FORMAT(NOW(), '%Y-01-01 09:00:00'))
    enable
    comment '근무일 1년 미만이고 결근 없는 사람 연차 update (재직일 / 365) * 15  '
    DO
    UPDATE hold_vacation C
        JOIN member A ON C.mem_code = A.mem_code
        JOIN attendance B ON A.mem_code = B.mem_code
        SET C.vct_deadline = C.vct_count,
            C.vct_count = ROUND((DATEDIFF(DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 YEAR), '%Y-12-31'), A.mem_hire_date) / 365)
            * 15)
    WHERE A.mem_status = 'N'
        AND YEAR(A.mem_hire_date) = YEAR(CURDATE()) - 1
#         AND B.att_status <> '결근'
#         AND B.att_work_date BETWEEN DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 YEAR), '%Y-01-01')
        AND DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 YEAR), '%Y-12-31');
SELECT * FROM INFORMATION_SCHEMA.EVENTS WHERE EVENT_NAME = 'InsertNewYearVacationLessthen1year';