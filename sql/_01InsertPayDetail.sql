DELIMITER //  #MySQL에서 사용하는 구분자를 설정, 쿼리문의 끝이라는 표식을 뭘로할지 지정 기본은 ; 인데 여기서는 #//사용한다는 의미
CREATE EVENT IF NOT EXISTS updateEventProductStatusEvent  #여기서부터 새로운 이벤트 생성 IF NOT EXISTS로 해당 이름의 이벤트가 존재하지않을 경우에만 이벤트 생성
    ON SCHEDULE
    EVERY 1 MONTH  # 매월
    STARTS TIMESTAMP(DATE_FORMAT(NOW(), '%Y-%m-25 00:00:00'))  # 25일에 실행
    DO                                                 #이벤트가 실행될 때 수행할 작업 정의 UPDATE부터는 쿼리문임
BEGIN                                              #이벤트 테이블의 end_day가 오늘(CURRENT_DATE)면 event_product_list에서 product코드를 찾아 일치하는 제품만 event 상태를 N으로 바꾼다
UPDATE tbl_product
SET event_progressionstatus = 'N'
WHERE product_code IN (
    SELECT B.product_code
    FROM tbl_event A
             JOIN tbl_event_product_list B ON A.post_code = B.post_code
    WHERE DATE(end_day) = CURRENT_DATE
    );
UPDATE tbl_event
SET status = 'N'
WHERE DATE(end_day) = CURRENT_DATE;
END //
DELIMITER ; #구분자를 다시 ; 로 설정

SELECT * FROM INFORMATION_SCHEMA.EVENTS WHERE EVENT_NAME = 'updateEventProductStatusEvent'; #생성된 이벤트가 정상적으로 등록되었는지 확인하기 위해 .EVENT 테이블 조회하는 쿼리