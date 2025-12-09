package org.wrapper.mybatismapper.timezone.vo;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import lombok.Data;

/**
 * 타임존 테스트를 위한 Entity
 * 
 * 포스팅에서 언급한 내용을 테스트하기 위한 엔티티입니다.
 * - zonedatetime: DATETIME 타입을 ZonedDateTime으로 받는 필드
 * - localdatetime: DATETIME 타입을 LocalDateTime으로 받는 필드
 * - zonetimestamp: TIMESTAMP 타입을 ZonedDateTime으로 받는 필드
 * - localtimestamp: TIMESTAMP 타입을 LocalDateTime으로 받는 필드
 */
@Data
public class ZoneEntity {
    private Long id;
    
    /**
     * DATETIME 타입 컬럼을 ZonedDateTime으로 매핑
     * DB의 DATETIME은 타임존 정보 없이 저장되지만, 
     * 서버에서 ZonedDateTime으로 받을 때 타임존이 부여됨
     */
    private ZonedDateTime zoneDateTime;
    
    /**
     * DATETIME 타입 컬럼을 LocalDateTime으로 매핑
     * 타임존 정보 없이 그대로 받아옴
     */
    private LocalDateTime localDateTime;
    
    /**
     * TIMESTAMP 타입 컬럼을 ZonedDateTime으로 매핑
     * DB의 TIMESTAMP는 UTC로 저장되고, 조회 시 세션 타임존에 따라 변환됨
     */
    private ZonedDateTime zoneTimestamp;
    
    /**
     * TIMESTAMP 타입 컬럼을 LocalDateTime으로 매핑
     * 타임존 변환은 되지만 타임존 정보는 제거됨
     */
    private LocalDateTime localTimestamp;
}

