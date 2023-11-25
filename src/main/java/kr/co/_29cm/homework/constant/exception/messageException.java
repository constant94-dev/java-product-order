package kr.co._29cm.homework.constant.exception;

public enum messageException {

    EXCEPTION_TEMPLATE("[ERROR] "),
    EXCEPTION_INVALID_TEMPLATE("유효하지 않은 입력입니다. 다시 입력해주세요."),
    PRODUCT_SOLD_OUT(EXCEPTION_TEMPLATE.message + "SoldOutException 발생. 주문한 상품량이 재고량보다 큽니다."),
    PRODUCT_NOT_EXIST(EXCEPTION_TEMPLATE.message + "유효하지 않은 상품 입력입니다. 상품을 다시 확인해주세요."),
    INVALID_CHOOSE(EXCEPTION_TEMPLATE.message + EXCEPTION_INVALID_TEMPLATE.message),
    INVALID_ORDER_NUMBER(EXCEPTION_TEMPLATE.message + EXCEPTION_INVALID_TEMPLATE.message),
    INVALID_ORDER_VOLUME(EXCEPTION_TEMPLATE.message + EXCEPTION_INVALID_TEMPLATE.message);

    private String message;

    messageException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
