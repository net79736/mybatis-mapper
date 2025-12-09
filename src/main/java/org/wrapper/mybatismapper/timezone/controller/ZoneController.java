package org.wrapper.mybatismapper.timezone.controller;

import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wrapper.mybatismapper.timezone.service.ZoneService;
import org.wrapper.mybatismapper.timezone.vo.ZoneEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 타임존 테스트를 위한 Controller
 * 
 * 포스팅에서 언급한 내용을 실제로 확인할 수 있는 API와 UI를 제공합니다.
 */
@Slf4j
@Controller
@RequestMapping("/timezone")
@RequiredArgsConstructor
public class ZoneController {
    
    private final String TO_UTC = "toUTC";
    private final String TO_SEOUL = "toSeoul";

    private final ZoneService zoneService;
    
    /**
     * 타임존 테스트 메인 페이지
     */
    @GetMapping
    public String timezonePage(Model model) {
        return "timezone";
    }
    
    /**
     * 모든 타임존 테스트 데이터 조회 (REST API)
     * 
     * 포스팅에서 언급한 여러 시나리오를 확인할 수 있습니다.
     */
    @GetMapping("/api/test-data")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getAllTestData() {
        // JVM 타임존 정보 출력
        String jvmTimeZone = java.util.TimeZone.getDefault().getID();
        log.info("JVM 타임존: {}", jvmTimeZone);
        
        // DB 타임존 정보 조회
        ZoneService.TimeZoneInfo timeZoneInfo = zoneService.getTimeZoneInfo();
        log.info("DB 타임존 정보: {}", timeZoneInfo);
        
        // 데이터 조회
        List<ZoneEntity> entities = zoneService.findAll();
        
        Map<String, Object> response = new HashMap<>();
        response.put("jvmTimeZone", jvmTimeZone); // JVM 타임존
        response.put("dbTimeZoneInfo", Map.of(
            "global", timeZoneInfo.getGlobalTimeZone(), // DB Global 타임존
            "session", timeZoneInfo.getSessionTimeZone(), // DB Session 타임존
            "system", timeZoneInfo.getSystemTimeZone() // DB System 타임존
        ));
        response.put("data", entities); // 데이터
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * ID로 특정 데이터 조회 및 상세 분석 (REST API)
     * 
     * 포스팅에서 언급한 문제점을 확인할 수 있습니다.
     */
    @GetMapping("/api/test-data/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getTestDataById(@PathVariable Long id) {
        String jvmTimeZone = java.util.TimeZone.getDefault().getID();
        ZoneEntity entity = zoneService.findById(id);
        
        if (entity == null) {
            return ResponseEntity.notFound().build();
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("jvmTimeZone", jvmTimeZone);
        response.put("data", entity);
        
        // ZonedDateTime을 UTC로 변환한 결과 (포스팅에서 언급한 문제 확인용)
        if (entity.getZoneDateTime() != null) {
            Map<String, String> zoneDateTimeAnalysis = new HashMap<>();
            zoneDateTimeAnalysis.put("original", entity.getZoneDateTime().toString());
            zoneDateTimeAnalysis.put(
                TO_UTC, 
                entity.getZoneDateTime().withZoneSameInstant(ZoneId.of("UTC")).toString()
            ); // UTC로 변환한 결과
            zoneDateTimeAnalysis.put(
                TO_SEOUL, 
                entity.getZoneDateTime().withZoneSameInstant(ZoneId.of("Asia/Seoul")).toString()
            ); // SEOUL 타임존으로 변환한 결과
            response.put("zoneDateTimeAnalysis", zoneDateTimeAnalysis);
        }
        
        if (entity.getZoneTimestamp() != null) {
            Map<String, String> zoneTimestampAnalysis = new HashMap<>();
            zoneTimestampAnalysis.put("original", entity.getZoneTimestamp().toString());
            zoneTimestampAnalysis.put(
                TO_UTC,
                entity.getZoneTimestamp().withZoneSameInstant(ZoneId.of("UTC")).toString()
            );
            zoneTimestampAnalysis.put(
                TO_SEOUL, 
                entity.getZoneTimestamp().withZoneSameInstant(ZoneId.of("Asia/Seoul")).toString()
            );
            response.put("zoneTimestampAnalysis", zoneTimestampAnalysis);
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * DB 타임존 정보만 조회 (REST API)
     */
    @GetMapping("/api/db-timezone")
    @ResponseBody
    public ResponseEntity<Map<String, String>> getDbTimeZone() {
        ZoneService.TimeZoneInfo timeZoneInfo = zoneService.getTimeZoneInfo();
        
        Map<String, String> response = new HashMap<>();
        response.put("global", timeZoneInfo.getGlobalTimeZone());
        response.put("session", timeZoneInfo.getSessionTimeZone());
        response.put("system", timeZoneInfo.getSystemTimeZone());
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * KST 환경에서 테스트 데이터 생성 (REST API)
     */
    @PostMapping("/api/test-data/kst")
    @ResponseBody
    public ResponseEntity<Map<String, String>> createKSTTestData() {
        zoneService.insertWithKSTNow();
        return ResponseEntity.ok(Map.of("message", "KST 환경에서 테스트 데이터가 생성되었습니다."));
    }
    
    /**
     * UTC 환경에서 테스트 데이터 생성 (REST API)
     * 주의: 실제로는 DB 세션 타임존을 변경한 후 실행해야 함
     */
    @PostMapping("/api/test-data/utc")
    @ResponseBody
    public ResponseEntity<Map<String, String>> createUTCTestData() {
        zoneService.insertWithUTCNow();
        return ResponseEntity.ok(Map.of("message", "UTC 환경에서 테스트 데이터가 생성되었습니다. (실제 DB 세션 타임존 확인 필요)"));
    }
    
    /**
     * ZoneEntity 객체를 받아서 데이터 삽입 (REST API)
     * NOW() 대신 객체의 필드 값을 사용하여 INSERT
     */
    @PostMapping("/api/test-data")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> createTestData(@RequestBody ZoneEntity entity) {
        log.info("받은 ZoneEntity 객체: {}", entity);
        
        // 객체를 받아서 insert
        zoneService.insert(entity);
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "객체를 통해 테스트 데이터가 생성되었습니다.");
        response.put("data", entity);
        
        return ResponseEntity.ok(response);
    }
}

