package kr.co._29cm.homework.constant.view;

public enum OutputViewConstant {
    PRODUCT_SEPARATOR("---------------------------------\n"),
    PRODUCT_ORDER_RESULT("주문 내역:\n"),
    PRODUCT_ORDER_AMOUNT("주문금액: %s원\n"),
    PRODUCT_DELIVERY_AMOUNT("배송비: %s원\n"),
    PRODUCT_PAY_AMOUNT("지불금액: %s원\n"),
    PRODUCT_ORDER_THANK("고객님의 주문 감사합니다.\n"),
    PRODUCT_ORDER_EMPTY("주문 내역이 없습니다.\n"),
    PRODUCT_DELIVERY_EMPTY("");

    private String message;

    OutputViewConstant(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
