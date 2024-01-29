CREATE EVENT IF NOT EXISTS InsertNewYearVacationLessthen1year  #여기서부터 새로운 이벤트 생성 IF NOT EXISTS로 해당 이름의 이벤트가 존재하지않을 경우에만 이벤트 생성
    ON SCHEDULE
        EVERY 1 YEAR
            STARTS TIMESTAMP(DATE_FORMAT(NOW(), '%Y-01-01 09:00:00'))  # 매년 1월 1일 09시에 실행
    enable
    comment '근무일 1년 미만이고 결근 없는 사람 연차 update (재직일 / 365) * 15  '
    DO                                                 #이벤트가 실행될 때 수행할 작업 정의 UPDATE부터는 쿼리문임
    UPDATE hold_vacation C
        JOIN member A ON C.mem_code = A.mem_code
        JOIN attendance B ON A.mem_code = B.mem_code
        SET C.vct_deadline = C.vct_count,                               #남아있는 vct_count값은 vct_deadline에 넣음
            C.vct_count = ROUND((DATEDIFF(DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 YEAR), '%Y-12-31'), A.mem_hire_date) / 365)
            * 15)
    WHERE A.mem_status = 'N'
        AND YEAR(A.mem_hire_date) = YEAR(CURDATE()) - 1
#         AND B.att_status <> '결근'
#         AND B.att_work_date BETWEEN DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 YEAR), '%Y-01-01')
        AND DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 YEAR), '%Y-12-31');
SELECT * FROM INFORMATION_SCHEMA.EVENTS WHERE EVENT_NAME = 'InsertNewYearVacationLessthen1year'; #생성된 이벤트가 정상적으로 등록되었는지 확인하기 위해 .EVENT 테이블 조회하는 쿼리