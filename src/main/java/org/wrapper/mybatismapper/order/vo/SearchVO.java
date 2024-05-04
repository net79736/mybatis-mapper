package org.wrapper.mybatismapper.order.vo;

import lombok.Data;

@Data
public class SearchVO {
    private String userId;
    private String name;
    private String email;
    private String address;
    private String productName;
    private Double price;
}
