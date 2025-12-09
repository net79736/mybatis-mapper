package org.wrapper.mybatismapper.timezone.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.wrapper.mybatismapper.timezone.mapper.ZoneMapper;
import org.wrapper.mybatismapper.timezone.vo.ZoneEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 타임존 테스트를 위한 Service
 * 
 * 포스팅에서 언급한 여러 시나리오를 테스트하기 위한 서비스입니다.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ZoneService {
    
    private final ZoneMapper zoneMapper;
    
    /**
     * 모든 타임존 테스트 데이터 조회
     */
    public List<ZoneEntity> findAll() {
        return zoneMapper.findAll();
    }
    
    /**
     * ID로 특정 데이터 조회
     */
    public ZoneEntity findById(Long id) {
        return zoneMapper.findById(id);
    }
    
    /**
     * DB의 타임존 정보 조회
     */
    public TimeZoneInfo getTimeZoneInfo() {
        String globalTimeZone = zoneMapper.getGlobalTimeZone();
        String sessionTimeZone = zoneMapper.getSessionTimeZone();
        String systemTimeZone = zoneMapper.getSystemTimeZone();
        
        return new TimeZoneInfo(globalTimeZone, sessionTimeZone, systemTimeZone);
    }
    
    /**
     * KST 환경에서 데이터 삽입
     */
    public void insertWithKSTNow() {
        zoneMapper.insertWithKSTNow();
    }
    
    /**
     * UTC 환경에서 데이터 삽입
     * 주의: 실제로는 DB 세션 타임존을 변경한 후 실행해야 함
     */
    public void insertWithUTCNow() {
        zoneMapper.insertWithUTCNow();
    }
    
    /**
     * ZoneEntity 객체를 받아서 데이터 삽입
     * 주의: 사용자가 입력한 값은 무시되고, 무조건 서버의 LocalDateTime.now() 값으로 INSERT됩니다.
     * 이는 타임존 테스트를 위해 서버의 현재 시간을 기준으로 데이터를 생성하기 위함입니다.
     */
    public void insert(ZoneEntity entity) {
        // 서버의 현재 시간을 LocalDateTime으로 가져옴
        LocalDateTime serverNow = LocalDateTime.now();
        
        // 서버의 타임존 정보 가져오기
        ZoneId serverZoneId = ZoneId.systemDefault();
        
        log.info("서버의 현재 시간으로 데이터 삽입 - LocalDateTime.now(): {}, ZoneId: {}", serverNow, serverZoneId);
        
        // 모든 날짜/시간 필드를 서버의 현재 시간으로 설정
        // 사용자가 입력한 값은 무시됨
        entity.setLocalDateTime(serverNow);
        entity.setLocalTimestamp(serverNow);
        
        // ZonedDateTime 필드는 서버의 타임존을 사용하여 설정
        ZonedDateTime serverZonedNow = serverNow.atZone(serverZoneId);
        entity.setZoneDateTime(serverZonedNow);
        entity.setZoneTimestamp(serverZonedNow);
        
        log.info("설정된 값 - zoneDateTime: {}, localDateTime: {}, zoneTimestamp: {}, localTimestamp: {}", 
            entity.getZoneDateTime(), entity.getLocalDateTime(), 
            entity.getZoneTimestamp(), entity.getLocalTimestamp());
        
        zoneMapper.insert(entity);
    }
    
    /**
     * 타임존 정보를 담는 내부 클래스
     */
    public static class TimeZoneInfo {
        private final String globalTimeZone;
        private final String sessionTimeZone;
        private final String systemTimeZone;
        
        public TimeZoneInfo(String globalTimeZone, String sessionTimeZone, String systemTimeZone) {
            this.globalTimeZone = globalTimeZone;
            this.sessionTimeZone = sessionTimeZone;
            this.systemTimeZone = systemTimeZone;
        }
        
        public String getGlobalTimeZone() {
            return globalTimeZone;
        }
        
        public String getSessionTimeZone() {
            return sessionTimeZone;
        }
        
        public String getSystemTimeZone() {
            return systemTimeZone;
        }
        
        @Override
        public String toString() {
            return String.format("Global: %s, Session: %s, System: %s", 
                globalTimeZone, sessionTimeZone, systemTimeZone);
        }
    }
}

