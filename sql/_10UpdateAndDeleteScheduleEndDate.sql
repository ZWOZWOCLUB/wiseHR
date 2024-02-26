CREATE EVENT IF NOT EXISTS UpdateAndDeleteScheduleEndDate
    ON SCHEDULE
        EVERY 1 DAY
            STARTS TIMESTAMP(CONCAT(CURDATE(), ' 09:00:00'))
    enable
    comment '스케줄 종료일 status 변경 및 patternDay 행 삭제'
    DO
    UPDATE schedule
    SET sch_delete_status = 'Y'
    WHERE sch_end_date = CURDATE() - INTERVAL 1 DAY;
    DELETE FROM pattern_day A
    WHERE A.wok_code IN (SELECT B.sch_code, B.wok_code FROM schedule B WHERE sch_end_date = CURDATE() - INTERVAL 1 DAY);
    END;
SELECT * FROM INFORMATION_SCHEMA.EVENTS WHERE EVENT_NAME = 'UpdateAndDeleteScheduleEndDate';