package kr.co._29cm.homework.service;

import static kr.co._29cm.homework.constant.service.OrderConstant.ORDER_AMOUNT_EMPTY;
import static kr.co._29cm.homework.constant.service.OrderConstant.ORDER_DELIVERY_AMOUNT;
import static kr.co._29cm.homework.constant.service.OrderConstant.ORDER_DELIVERY_EMPTY;
import static kr.co._29cm.homework.constant.service.OrderConstant.ORDER_DELIVERY_IMPOSE;
import static kr.co._29cm.homework.constant.service.ResultNameConstant.RESULT_NAME_DELIVERY_AMOUNT;
import static kr.co._29cm.homework.constant.service.ResultNameConstant.RESULT_NAME_PAY_AMOUNT;
import static kr.co._29cm.homework.constant.service.ResultNameConstant.RESULT_NAME_TOTAL_AMOUNT;

import java.util.Set;
import kr.co._29cm.homework.model.Menu;
import kr.co._29cm.homework.model.Order;

public class OrderService {

    public void orderToTotalAmount(Order order, Menu menu) {
        Set<Integer> orderNumbers = order.getNumbers();
        int amount = ORDER_AMOUNT_EMPTY.getMessage();

        for (int orderNumber : orderNumbers) {
            int price = menu.getPrice(orderNumber);
            amount += price * order.getVolume(orderNumber);
        }

        order.addOrderResult(RESULT_NAME_TOTAL_AMOUNT.getMessage(), amount);
    }

    public void orderToDeliveryAmount(Order order, Menu menu) {
        Set<Integer> orderNumbers = order.getNumbers();
        int amount = ORDER_DELIVERY_EMPTY.getMessage();

        for (int orderNumber : orderNumbers) {
            int price = menu.getPrice(orderNumber);
            amount += price * order.getVolume(orderNumber);
        }

        orderToOverORLessAmount(order, amount);
    }

    private void orderToOverORLessAmount(Order order, int amount) {
        if (orderToNotEmptyAndImpose(amount)) {
            order.addOrderResult(
                    RESULT_NAME_DELIVERY_AMOUNT.getMessage(),
                    ORDER_DELIVERY_AMOUNT.getMessage()
            );
            return;
        }
        order.addOrderResult(
                RESULT_NAME_DELIVERY_AMOUNT.getMessage(),
                ORDER_DELIVERY_EMPTY.getMessage()
        );
    }

    private boolean orderToNotEmptyAndImpose(int amount) {
        return (ORDER_DELIVERY_IMPOSE.getMessage() > amount && ORDER_DELIVERY_EMPTY.getMessage() < amount);
    }

    public void orderToPayAmount(Order order) {
        int totalAmount = order.getOrderResultToTotalAmount(RESULT_NAME_TOTAL_AMOUNT.getMessage());
        int deliveryAmount = order.getOrderResultToDeliveryAmount(RESULT_NAME_DELIVERY_AMOUNT.getMessage());
        int payAmount = totalAmount + deliveryAmount;

        order.addOrderResult(RESULT_NAME_PAY_AMOUNT.getMessage(), payAmount);
    }
}
