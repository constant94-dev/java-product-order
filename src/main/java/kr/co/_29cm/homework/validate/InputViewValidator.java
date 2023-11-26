package kr.co._29cm.homework.validate;

import static kr.co._29cm.homework.constant.service.OrderConstant.ORDER_VOLUME_EMPTY;
import static kr.co._29cm.homework.constant.view.InputViewConstant.PRODUCT_NUMBER_BREAK;
import static kr.co._29cm.homework.constant.view.InputViewConstant.PRODUCT_NUMBER_CONTINUE;
import static kr.co._29cm.homework.exception.handlerException.invalidToChoose;
import static kr.co._29cm.homework.exception.handlerException.invalidToOrderNumber;
import static kr.co._29cm.homework.exception.handlerException.invalidToOrderVolume;
import static kr.co._29cm.homework.exception.handlerException.soldOutToProduct;

import java.util.Map.Entry;
import java.util.Set;
import kr.co._29cm.homework.model.Menu;
import kr.co._29cm.homework.model.Order;

public final class InputViewValidator {

    public static void validateOrderORQuit(String choose) {
        if (!hasInChooses(choose)) {
            invalidToChoose();
        }
    }

    private static boolean hasInChooses(String choose) {
        return choose.matches("o|q|order|quit");
    }

    public static void validateOrderNumberToInteger(String orderNumber) {
        try {
            Integer.parseInt(orderNumber);
        } catch (NumberFormatException e) {
            invalidToOrderNumber();
        }
    }

    public static String validateOrderNumberToEmpty(Order order, Menu menu, String orderNumber) {
        if (!orderNumber.trim().isEmpty()) {
            validateOrderNumberToInteger(orderNumber);
            order.addOrderNumberAndVolume(Integer.parseInt(orderNumber), ORDER_VOLUME_EMPTY.getMessage());
            return PRODUCT_NUMBER_CONTINUE.getMessage();
        }
        stockOfVolumeOrdered(order, menu);
        return PRODUCT_NUMBER_BREAK.getMessage();
    }

    public synchronized static void stockOfVolumeOrdered(Order order, Menu menu) {
        if (menu.menuNumberToExist(order)) {
            orderVolumeOfStock(order, menu);
        }
    }

    private static void orderVolumeOfStock(Order order, Menu menu) {
        Set<Entry<Integer, Integer>> products = order.getNumberAndVolumes();

        for (Entry<Integer, Integer> product : products) {
            if (!menuVolumeToExist(menu, product)) {
                soldOutToProduct();
                break;
            }
        }

        menu.changeMenuOfOrderApply(products);
    }

    private static boolean menuVolumeToExist(Menu menu, Entry<Integer, Integer> product) {
        return menu.volumeInStock(product.getKey(), product.getValue());
    }

    public static void validateOrderVolumeToInteger(String orderVolume) {
        try {
            Integer.parseInt(orderVolume);
        } catch (NumberFormatException e) {
            invalidToOrderVolume();
        }
    }

    public static boolean validateOrderVolumeToEmpty(Order order, String orderNumber, String orderVolume) {
        if (!orderVolume.trim().isEmpty()) {
            int convertNumber = Integer.parseInt(orderNumber);
            int convertVolume = Integer.parseInt(orderVolume);
            int currentVolume = order.getVolume(convertNumber);

            order.addOrderNumberAndVolume(convertNumber, currentVolume + convertVolume);
            return true;
        }
        return false;
    }
}
