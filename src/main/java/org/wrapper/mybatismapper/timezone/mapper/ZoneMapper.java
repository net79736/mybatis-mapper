package org.wrapper.mybatismapper.timezone.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.wrapper.mybatismapper.timezone.vo.ZoneEntity;

/**
 * 타임존 테스트를 위한 Mapper 인터페이스
 */
@Mapper
public interface ZoneMapper {
    /**
     * 모든 타임존 테스트 데이터 조회
     */
    List<ZoneEntity> findAll();
    
    /**
     * ID로 특정 데이터 조회
     */
    ZoneEntity findById(Long id);
    
    /**
     * 현재 시간으로 데이터 삽입 (DB의 NOW() 사용)
     * KST 환경에서 생성
     */
    void insertWithKSTNow();
    
    /**
     * 현재 시간으로 데이터 삽입 (DB의 NOW() 사용)
     * UTC 환경에서 생성
     */
    void insertWithUTCNow();
    
    /**
     * ZoneEntity 객체를 받아서 데이터 삽입
     * 객체의 필드 값을 직접 사용하여 INSERT
     */
    void insert(ZoneEntity entity);
    
    /**
     * DB의 타임존 설정 조회
     */
    String getGlobalTimeZone();
    
    String getSessionTimeZone();
    
    String getSystemTimeZone();
}

