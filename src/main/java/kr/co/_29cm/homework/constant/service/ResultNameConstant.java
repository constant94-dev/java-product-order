package kr.co._29cm.homework.constant.service;

public enum ResultNameConstant {
    RESULT_NAME_TOTAL_AMOUNT("total_amount"),
    RESULT_NAME_DELIVERY_AMOUNT("delivery_amount"),
    RESULT_NAME_PAY_AMOUNT("pay_amount");

    private String message;

    ResultNameConstant(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
