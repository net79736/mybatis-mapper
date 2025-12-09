-- 타임존 테스트를 위한 테이블 생성
-- 포스팅에서 언급한 DATETIME과 TIMESTAMP의 차이를 확인하기 위한 테이블

CREATE TABLE IF NOT EXISTS tb_timezone_test (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    -- DATETIME 타입: 타임존 변환 없이 저장된 그대로 유지
    zonedatetime DATETIME COMMENT 'DATETIME 타입 - ZonedDateTime으로 매핑',
    localdatetime DATETIME COMMENT 'DATETIME 타입 - LocalDateTime으로 매핑',
    -- TIMESTAMP 타입: UTC로 저장되고 조회 시 세션 타임존에 따라 자동 변환
    zonetimestamp TIMESTAMP COMMENT 'TIMESTAMP 타입 - ZonedDateTime으로 매핑',
    local_timestamp TIMESTAMP COMMENT 'TIMESTAMP 타입 - LocalDateTime으로 매핑',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='타임존 테스트용 테이블';

-- 테스트 데이터 삽입 (KST 환경에서 생성)
-- 포스팅의 id 1에 해당하는 데이터
INSERT INTO tb_timezone_test (zonedatetime, localdatetime, zonetimestamp, local_timestamp)
VALUES (NOW(), NOW(), NOW(), NOW());

-- UTC 환경에서 생성할 데이터는 애플리케이션에서 DB 세션 타임존을 변경한 후 생성해야 함
-- 예시:
-- SET time_zone = 'UTC';
-- INSERT INTO tb_timezone_test (zonedatetime, localdatetime, zonetimestamp, local_timestamp)
-- VALUES (NOW(), NOW(), NOW(), NOW());
-- SET time_zone = 'SYSTEM';

-- 테이블 구조 확인 쿼리
-- SELECT 
--     COLUMN_NAME,
--     DATA_TYPE,
--     COLUMN_TYPE,
--     COLUMN_COMMENT
-- FROM INFORMATION_SCHEMA.COLUMNS
-- WHERE TABLE_SCHEMA = DATABASE()
--   AND TABLE_NAME = 'tb_timezone_test';

-- DB 타임존 확인 쿼리
-- SELECT 
--     @@GLOBAL.time_zone AS global_time_zone,
--     @@SESSION.time_zone AS session_time_zone,
--     @@system_time_zone AS system_time_zone;

