package org.wrapper.mybatismapper.order.service;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.wrapper.mybatismapper.order.vo.OrderVO;
import org.wrapper.mybatismapper.order.vo.ProductVO;

import java.util.List;

@Slf4j
@SpringBootTest
class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Test
    public void orderListTest() {
        List<OrderVO> orderVOS = orderService.selectOrderList();
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