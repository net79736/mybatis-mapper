package org.wrapper.mybatismapper.order.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.wrapper.mybatismapper.order.vo.OrderVO;

import java.util.List;

@Mapper
public interface OrderMapper {
    List<OrderVO> selectOrderList();
}
