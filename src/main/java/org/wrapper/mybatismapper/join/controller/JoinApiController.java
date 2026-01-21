package org.wrapper.mybatismapper.join.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 회원가입 URL 생성용 REST API
 */
@RestController
public class JoinApiController {

    /**
     * 회원가입 URL 생성
     * 예: /api/join-url?memberCode=8927182
     */
    @GetMapping("/api/join-url")
    public ResponseEntity<Map<String, String>> getJoinUrl(
            @RequestParam(required = false) String memberCode,
            HttpServletRequest request) {
        String baseUrl = getBaseUrl(request);
        String joinUrl = buildJoinUrl(baseUrl, memberCode);

        try {
            // 테스트를 위해 0.3초 대기
            Thread.sleep(3000); // 0.3초 대기
        } catch (InterruptedException e) {
            System.out.println("InterruptedException: " + e.getMessage());
        }

        Map<String, String> response = new HashMap<>();
        response.put("url", joinUrl);
        response.put("memberCode", memberCode == null ? "" : memberCode);

        return ResponseEntity.ok(response);
    }

    private String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();

        boolean isDefaultPort = ("http".equalsIgnoreCase(scheme) && serverPort == 80)
                || ("https".equalsIgnoreCase(scheme) && serverPort == 443);

        String portPart = isDefaultPort ? "" : ":" + serverPort;
        return scheme + "://" + serverName + portPart + contextPath;
    }

    private String buildJoinUrl(String baseUrl, String memberCode) {
        String trimmedCode = memberCode == null ? "" : memberCode.trim();
        if (trimmedCode.isEmpty()) {
            return baseUrl + "/join";
        }
        return baseUrl + "/join?memberCode=" + encodeQuery(trimmedCode);
    }

    private String encodeQuery(String value) {
        return value.replace(" ", "%20");
    }
}
