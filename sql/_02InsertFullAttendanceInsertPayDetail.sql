CREATE EVENT IF NOT EXISTS InsertFullAttendanceInsertPayDetailEvent  #여기서부터 새로운 이벤트 생성 IF NOT EXISTS로 해당 이름의 이벤트가 존재하지않을 경우에만 이벤트 생성
    ON SCHEDULE
        EVERY 1 MONTH  # 수행 반복할 시간 여기선 매월
            STARTS TIMESTAMP(DATE_FORMAT(NOW(), '%Y-%m-25 09:00:00'))  # 25일 09시에 실행
    enable
    comment '결근이 없는 사람 - 매월 25일 급여내역 insert '
    DO                                                 #이벤트가 실행될 때 수행할 작업 정의 UPDATE부터는 쿼리문임
    insert into paydetails (pde_date, pde_salary, pde_yymm, mem_code)
    SELECT
        DATE_FORMAT(NOW(), '%Y-%m-25'), #pde_date 에 이번년도, 이번달 25일을 넣음
        B.pos_salary / 12, # position 테이블에 mem_code에 맞는 pos_salary 값을 나누기 12해서 넣음
        DATE_FORMAT(LAST_DAY(DATE_SUB(NOW(), INTERVAL 1 MONTH)), '%Y-%m-%d'),
        DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01'), #지난달 일한 급여를 지급하는거니 지난달 마지막날을 입력
        A.mem_code
    FROM member A
             JOIN position B ON A.pos_code = B.pos_code
             JOIN attendance C on A.mem_code = C.mem_code
    WHERE A.mem_status = 'N' #직원이 퇴직한 상태가 아니어야함
      AND C.att_work_date BETWEEN DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH), '%Y-%m-01') AND LAST_DAY(DATE_SUB(NOW(), INTERVAL 1 MONTH))
      AND C.att_status <> '결근'; # 지난달 1일부터 지난달 31일에 결근이 없어야함
SELECT * FROM INFORMATION_SCHEMA.EVENTS WHERE EVENT_NAME = 'InsertFullAttendanceInsertPayDetailEvent'; #생성된 이벤트가 정상적으로 등록되었는지 확인하기 위해 .EVENT 테이블 조회하는 쿼리