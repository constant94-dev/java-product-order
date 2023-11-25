package kr.co._29cm.homework.exception;

import static kr.co._29cm.homework.constant.exception.messageException.INVALID_CHOOSE;
import static kr.co._29cm.homework.constant.exception.messageException.INVALID_ORDER_NUMBER;
import static kr.co._29cm.homework.constant.exception.messageException.INVALID_ORDER_VOLUME;
import static kr.co._29cm.homework.constant.exception.messageException.PRODUCT_NOT_EXIST;
import static kr.co._29cm.homework.constant.exception.messageException.PRODUCT_SOLD_OUT;

public class handlerException {
    public static void invalidToChoose() {
        throw new IllegalArgumentException(INVALID_CHOOSE.getMessage());
    }

    public static void invalidToOrderNumber() {
        throw new IllegalArgumentException(INVALID_ORDER_NUMBER.getMessage());
    }

    public static void invalidToOrderVolume() {
        throw new IllegalArgumentException(INVALID_ORDER_VOLUME.getMessage());
    }

    public static void soldOutToProduct() {
        throw new IllegalStateException(PRODUCT_SOLD_OUT.getMessage());
    }

    public static void notExistProduct() {
        throw new IllegalStateException(PRODUCT_NOT_EXIST.getMessage());
    }
}
