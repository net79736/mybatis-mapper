package org.wrapper.mybatismapper.order.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.wrapper.mybatismapper.order.vo.OrderVO;
import org.wrapper.mybatismapper.order.vo.SearchVO;

import java.util.List;

@Mapper
public interface OrderMapper {
    List<OrderVO> selectOrderList(SearchVO searchVO);
    List<OrderVO> selectOrderListByOrderSeq(List<String> orderSeqList);
}
