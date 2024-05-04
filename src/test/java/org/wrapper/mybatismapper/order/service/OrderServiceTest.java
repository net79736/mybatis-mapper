package org.wrapper.mybatismapper.order.service;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.wrapper.mybatismapper.order.vo.OrderVO;
import org.wrapper.mybatismapper.order.vo.ProductVO;
import org.wrapper.mybatismapper.order.vo.SearchVO;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Test
    @Deprecated
    public void selectOrderListWhereClause() {
        SearchVO searchVO = new SearchVO();
        searchVO.setName("John Doe");
        searchVO.setPrice(90.0);
        List<OrderVO> orderVOS = orderService.selectOrderList(searchVO);
        log.info("사이즈 출력 : {}", orderVOS.size());
        for (OrderVO orderVO : orderVOS) {
            log.info("## 주문 출력 START:");
            log.info(orderVO.toString());
            for (ProductVO productVO : orderVO.getProductObjList()) {
                log.info("######## 제품 출력 START:");
                log.info(productVO.toString());
            }
        }
    }

    @Test
    public void selectOrderListByOrderSeq() {
        log.info("selectOrderListByOrderSeq 시작해요!!!");
        List<String> searchSeqList = new ArrayList<>();
        searchSeqList.add("1");
        searchSeqList.add("2");
        searchSeqList.add("3");
        searchSeqList.add("4");

        List<OrderVO> orderVOS = orderService.selectOrderListByOrderSeq(searchSeqList);

        for (OrderVO orderVO : orderVOS) {
            log.info("## 주문 출력 START:");
            log.info(orderVO.toString());
            for (ProductVO productVO : orderVO.getProductObjList()) {
                log.info("######## 제품 출력 START:");
                log.info(productVO.toString());
            }
        }
    }

}