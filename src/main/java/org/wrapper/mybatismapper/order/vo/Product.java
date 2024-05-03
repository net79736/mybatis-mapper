package org.wrapper.mybatismapper.order.vo;

import lombok.Data;

@Data
public class Product {
    private String seq;     /*제품 일련번호*/
    private String order_seq;       /*주문 일련번호*/
    private String name;        /*제품 이름*/
    private double price;       /*가격*/
}
