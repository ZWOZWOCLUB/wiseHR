CREATE EVENT IF NOT EXISTS InsertVacation1yearLessthen  #여기서부터 새로운 이벤트 생성 IF NOT EXISTS로 해당 이름의 이벤트가 존재하지않을 경우에만 이벤트 생성
    ON SCHEDULE
        EVERY 1 YEAR  # 수행 반복할 시간 여기선 매월
            STARTS TIMESTAMP(DATE_FORMAT(NOW(), '%Y-01-01 09:00:00'))  # 매년 1월 1일 09시에 실행
    enable
    comment '근무일 1년 이상인 사람 연차 insert '
    DO                                                 #이벤트가 실행될 때 수행할 작업 정의 UPDATE부터는 쿼리문임
    insert into hold_vacation (mem_code, vct_count, vct_deadline)
    SELECT
        A.mem_code,
        ,
        0
    FROM member A
    WHERE A.mem_status = 'N' #직원이 퇴직한 상태가 아니어야함
SELECT * FROM INFORMATION_SCHEMA.EVENTS WHERE EVENT_NAME = 'InsertVacation1yearLessthen'; #생성된 이벤트가 정상적으로 등록되었는지 확인하기 위해 .EVENT 테이블 조회하는 쿼리