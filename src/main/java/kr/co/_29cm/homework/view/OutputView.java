package kr.co._29cm.homework.view;

import static kr.co._29cm.homework.constant.view.InputViewConstant.PRODUCT_INFO;
import static kr.co._29cm.homework.constant.view.OutputViewConstant.PRODUCT_ORDER_RESULT;
import static kr.co._29cm.homework.constant.view.OutputViewConstant.PRODUCT_ORDER_THANK;
import static kr.co._29cm.homework.constant.view.OutputViewConstant.PRODUCT_SEPARATOR;
import static kr.co._29cm.homework.validate.OutputViewValidator.validateOrderAmount;
import static kr.co._29cm.homework.validate.OutputViewValidator.validateOrderDelivery;
import static kr.co._29cm.homework.validate.OutputViewValidator.validateOrderNameAndVolume;
import static kr.co._29cm.homework.validate.OutputViewValidator.validatePayAmount;

import java.util.Map.Entry;
import java.util.Set;
import kr.co._29cm.homework.model.Menu;
import kr.co._29cm.homework.model.Order;
import kr.co._29cm.homework.model.Product;

public class OutputView {
    private StringBuilder printResult;

    public void printToTotalProduct(Menu menu) {
        printResult = new StringBuilder();

        printResult
                .append(PRODUCT_INFO.getMessage());

        Set<Entry<Integer, Product>> menus = menu.getNumberAndProduct();

        for (Entry<Integer, Product> element : menus) {
            printResult
                    .append(element.getKey())
                    .append("\t")
                    .append(element.getValue().getName())
                    .append("\t")
                    .append(element.getValue().getPrice())
                    .append("\t")
                    .append(element.getValue().getStock())
                    .append("\n");
        }

        System.out.println(printResult);
    }

    public void printToOrderQuit() {
        printResult = new StringBuilder();

        printResult
                .append(PRODUCT_ORDER_THANK.getMessage());

        System.out.println(printResult);
    }

    public void printToOrderResult(Order order, Menu menu) {
        printResult = new StringBuilder();

        printResult
                .append(PRODUCT_ORDER_RESULT.getMessage())
                .append(PRODUCT_SEPARATOR.getMessage())
                .append(validateOrderNameAndVolume(order, menu))
                .append(PRODUCT_SEPARATOR.getMessage())
                .append(validateOrderAmount(order))
                .append(validateOrderDelivery(order))
                .append(PRODUCT_SEPARATOR.getMessage())
                .append(validatePayAmount(order))
                .append(PRODUCT_SEPARATOR.getMessage());

        System.out.println(printResult);
    }
}
