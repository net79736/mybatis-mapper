package org.wrapper.mybatismapper.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wrapper.mybatismapper.order.mapper.OrderMapper;
import org.wrapper.mybatismapper.order.vo.OrderVO;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderMapper orderMapper;

    List<OrderVO> selectOrderList() {
        return orderMapper.selectOrderList();
    }

}
