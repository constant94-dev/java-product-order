package kr.co._29cm.homework.constant.view;

public enum InputViewConstant {
    ORDER_OR_QUIT("입력(o[order]: 주문, q[quit]: 종료) : "),
    PRODUCT_INFO("상품번호\t상품명\t\t\t\t\t\t\t\t\t판매가격\t재고수\n"),
    PRODUCT_NUMBER("상품번호: "),
    PRODUCT_VOLUME("수량: "),
    ORDER_OR_QUIT_CONTINUE("continue"),
    ORDER_OR_QUIT_BREAK("break"),
    PRODUCT_NUMBER_CONTINUE("continue"),
    PRODUCT_NUMBER_BREAK("break"),
    PRODUCT_VOLUME_CONTINUE("continue"),
    PRODUCT_VOLUME_BREAK("break"),
    ORDER_EMPTY("");


    private String message;

    InputViewConstant(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
