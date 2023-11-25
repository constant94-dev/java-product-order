package kr.co._29cm.homework.constant.service;

public enum OrderConstant {
    ORDER_NUMBER_EMPTY(0),
    ORDER_VOLUME_EMPTY(0),
    ORDER_AMOUNT_EMPTY(0),
    ORDER_DELIVERY_EMPTY(0),
    ORDER_PAY_AMOUNT_EMPTY(0),
    ORDER_DELIVERY_IMPOSE(50000),
    ORDER_DELIVERY_AMOUNT(2500);

    private int message;

    OrderConstant(int message) {
        this.message = message;
    }

    public int getMessage() {
        return message;
    }
}
