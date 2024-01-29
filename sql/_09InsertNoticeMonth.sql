CREATE EVENT IF NOT EXISTS InsertNoticeMonth
    ON SCHEDULE
        EVERY 1 YEAR
            STARTS TIMESTAMP(DATE_FORMAT(NOW(), '%Y-03-01 09:00:00'))
    enable
    comment '연차 촉진 공지사항 insert'
    DO
        INSERT INTO notice (not_name, not_comment, not_create_date, not_delete_status)
        VALUES(
        '연차 소멸 안내',
        '3월 31일 전년도 연차가 소멸될 예정입니다. 가급적 빠른 시일 내에 사용 권장드립니다.',
        NOW(),
        'N');
    END;
SELECT * FROM INFORMATION_SCHEMA.EVENTS WHERE EVENT_NAME = 'InsertNoticeMonth';