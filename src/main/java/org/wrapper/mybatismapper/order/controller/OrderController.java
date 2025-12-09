package org.wrapper.mybatismapper.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wrapper.mybatismapper.order.service.OrderService;
import org.wrapper.mybatismapper.order.vo.OrderVO;
import org.wrapper.mybatismapper.order.vo.SearchVO;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 검색 조건으로 주문 목록 조회
     * 
     * @param searchVO 검색 조건 (userId, name, email, address, productName, price)
     * @return 주문 목록
     */
    @PostMapping("/search")
    public ResponseEntity<List<OrderVO>> searchOrders(@RequestBody SearchVO searchVO) {
        List<OrderVO> orders = orderService.selectOrderList(searchVO);
        return ResponseEntity.ok(orders);
    }

    /**
     * GET 방식으로 검색 조건으로 주문 목록 조회
     * 
     * @param userId 회원 아이디 (LIKE 검색)
     * @param name 회원 이름 (LIKE 검색)
     * @param email 회원 이메일 (정확히 일치)
     * @param address 주소 (정확히 일치)
     * @param productName 제품 이름 (정확히 일치)
     * @param price 가격 이하 (이하 검색)
     * @return 주문 목록
     */
    @GetMapping
    public ResponseEntity<List<OrderVO>> getOrders(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) Double price) {
        
        SearchVO searchVO = new SearchVO();
        searchVO.setUserId(userId);
        searchVO.setName(name);
        searchVO.setEmail(email);
        searchVO.setAddress(address);
        searchVO.setProductName(productName);
        searchVO.setPrice(price);
        
        List<OrderVO> orders = orderService.selectOrderList(searchVO);
        return ResponseEntity.ok(orders);
    }

    /**
     * 주문 일련번호 리스트로 주문 목록 조회
     * 
     * @param orderSeqList 주문 일련번호 리스트
     * @return 주문 목록
     */
    @PostMapping("/by-seq")
    public ResponseEntity<List<OrderVO>> getOrdersBySeq(@RequestBody List<String> orderSeqList) {
        List<OrderVO> orders = orderService.selectOrderListByOrderSeq(orderSeqList);
        return ResponseEntity.ok(orders);
    }

    /**
     * 전체 주문 목록 조회 (검색 조건 없음)
     * 
     * @return 주문 목록
     */
    @GetMapping("/all")
    public ResponseEntity<List<OrderVO>> getAllOrders() {
        SearchVO searchVO = new SearchVO();
        List<OrderVO> orders = orderService.selectOrderList(searchVO);
        return ResponseEntity.ok(orders);
    }
}

