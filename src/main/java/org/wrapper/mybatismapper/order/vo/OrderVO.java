package org.wrapper.mybatismapper.order.vo;

import java.util.List;

public class OrderVO {
    private int seq;      /*주문 일련번호*/
    private String userId;      /*회원 아이디*/
    private String name;        /*회원 이름*/
    private String email;       /*회원 이메일*/
    private String phone;       /*회원 전화번호*/
    private String address;       /*주소*/
    private String regDate;       /*생성일시*/
    private String editDate;       /*수정일시*/
    private List<String> productSeqList;      /* 상품 번호 목록 */
    private List<Product> productObjList;       /* 상품 정보 목록 */
}
