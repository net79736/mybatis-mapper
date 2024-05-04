package org.wrapper.mybatismapper.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wrapper.mybatismapper.order.mapper.OrderMapper;
import org.wrapper.mybatismapper.order.vo.OrderVO;
import org.wrapper.mybatismapper.order.vo.SearchVO;

import java.util.List;

@Service
public class OrderService implements OrderMapper {

    @Autowired
    OrderMapper orderMapper;

    public List<OrderVO> selectOrderList(SearchVO searchVO) {
        return orderMapper.selectOrderList(searchVO);
    }
    @Override
    public List<OrderVO> selectOrderListByOrderSeq(List<String> orderSeqList) {
        return orderMapper.selectOrderListByOrderSeq(orderSeqList);
    }
}
