package kr.co._29cm.homework.validate;

import static kr.co._29cm.homework.constant.service.OrderConstant.ORDER_AMOUNT_EMPTY;
import static kr.co._29cm.homework.constant.service.OrderConstant.ORDER_DELIVERY_EMPTY;
import static kr.co._29cm.homework.constant.service.OrderConstant.ORDER_NUMBER_EMPTY;
import static kr.co._29cm.homework.constant.service.OrderConstant.ORDER_PAY_AMOUNT_EMPTY;
import static kr.co._29cm.homework.constant.service.ResultNameConstant.RESULT_NAME_DELIVERY_AMOUNT;
import static kr.co._29cm.homework.constant.service.ResultNameConstant.RESULT_NAME_PAY_AMOUNT;
import static kr.co._29cm.homework.constant.service.ResultNameConstant.RESULT_NAME_TOTAL_AMOUNT;
import static kr.co._29cm.homework.constant.view.OutputViewConstant.PRODUCT_DELIVERY_AMOUNT;
import static kr.co._29cm.homework.constant.view.OutputViewConstant.PRODUCT_DELIVERY_EMPTY;
import static kr.co._29cm.homework.constant.view.OutputViewConstant.PRODUCT_ORDER_AMOUNT;
import static kr.co._29cm.homework.constant.view.OutputViewConstant.PRODUCT_ORDER_EMPTY;
import static kr.co._29cm.homework.constant.view.OutputViewConstant.PRODUCT_PAY_AMOUNT;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Set;
import kr.co._29cm.homework.model.Menu;
import kr.co._29cm.homework.model.Order;

public final class OutputViewValidator {
    private static StringBuilder printResult;
    private static NumberFormat numberFormat;

    public static String validateOrderNameAndVolume(Order order, Menu menu) {
        int orderSize = order.getSize();
        if (orderSize == ORDER_NUMBER_EMPTY.getMessage()) {
            return PRODUCT_ORDER_EMPTY.getMessage();
        }

        return createToPrintNameAndVolume(menu, order);
    }

    private static String createToPrintNameAndVolume(Menu menu, Order order) {
        printResult = new StringBuilder();
        Set<Integer> orderNumbers = order.getNumbers();

        for (int orderNumber : orderNumbers) {
            String name = menu.getName(orderNumber);
            int volume = order.getVolume(orderNumber);

            printResult
                    .append(name)
                    .append(" - ")
                    .append(volume)
                    .append("개\n");
        }

        return printResult.toString();
    }

    public static String validateOrderAmount(Order order) {
        printResult = new StringBuilder();
        int totalAmount = order.getOrderResultToTotalAmount(RESULT_NAME_TOTAL_AMOUNT.getMessage());

        if (totalAmount == ORDER_AMOUNT_EMPTY.getMessage()) {
            printResult
                    .append(String.format(PRODUCT_ORDER_AMOUNT.getMessage(), totalAmount));
            return printResult.toString();
        }
        numberFormat = NumberFormat.getNumberInstance(Locale.KOREA);

        printResult
                .append(String.format(PRODUCT_ORDER_AMOUNT.getMessage(), numberFormat.format(totalAmount)));

        return printResult.toString();
    }

    public static String validateOrderDelivery(Order order) {
        int deliveryAmount = order.getOrderResultToDeliveryAmount(RESULT_NAME_DELIVERY_AMOUNT.getMessage());

        if (deliveryAmount == ORDER_DELIVERY_EMPTY.getMessage()) {
            return PRODUCT_DELIVERY_EMPTY.getMessage();
        }
        printResult = new StringBuilder();
        numberFormat = NumberFormat.getNumberInstance(Locale.KOREA);

        printResult
                .append(String.format(PRODUCT_DELIVERY_AMOUNT.getMessage(), numberFormat.format(deliveryAmount)));

        return printResult.toString();
    }

    public static String validatePayAmount(Order order) {
        printResult = new StringBuilder();
        int payAmount = order.getOrderResultToPayAmount(RESULT_NAME_PAY_AMOUNT.getMessage());

        if (payAmount == ORDER_PAY_AMOUNT_EMPTY.getMessage()) {
            printResult
                    .append(String.format(PRODUCT_PAY_AMOUNT.getMessage(), payAmount));
            return printResult.toString();
        }
        numberFormat = NumberFormat.getNumberInstance(Locale.KOREA);

        printResult
                .append(String.format(PRODUCT_PAY_AMOUNT.getMessage(), numberFormat.format(payAmount)));

        return printResult.toString();
    }
}
