package org.wrapper.mybatismapper.join.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 추천인 URL 복사 테스트 페이지
 */
@Controller
public class RecommendController {

    /**
     * 추천인 URL 복사 페이지
     */
    @GetMapping("/recommend")
    public String recommendPage() {
        return "forward:/recommend.jsp";
    }
}
