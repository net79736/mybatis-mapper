package org.wrapper.mybatismapper.join.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 회원가입 페이지 라우팅 컨트롤러
 */
@Controller
public class JoinController {

    /**
     * 회원가입 페이지
     * 
     * /join?memberCode=... 형태로 접근 시 JSP에서 파라미터를 읽어 자동 주입
     */
    @GetMapping("/join")
    public String joinPage() {
        return "forward:/join.jsp";
    }
}
