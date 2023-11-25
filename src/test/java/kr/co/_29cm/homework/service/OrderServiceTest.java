package kr.co._29cm.homework.service;

import static kr.co._29cm.homework.constant.service.ResultNameConstant.RESULT_NAME_DELIVERY_AMOUNT;
import static kr.co._29cm.homework.constant.service.ResultNameConstant.RESULT_NAME_PAY_AMOUNT;
import static kr.co._29cm.homework.constant.service.ResultNameConstant.RESULT_NAME_TOTAL_AMOUNT;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import kr.co._29cm.homework.model.Menu;
import kr.co._29cm.homework.model.Order;
import kr.co._29cm.homework.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class OrderServiceTest {

    @DisplayName("주문한 상품 총 금액 확인")
    @ParameterizedTest
    @CsvSource(value = {
            "111111,의류1,10000,1,111111,1,10000",
            "222222,의류2,30000,5,222222,4,120000"
    })
    void orderToTotalAmountExist(int menuNumber,
                                 String name, int price, int stock,
                                 int orderNumber, int orderVolume,
                                 int expected) {
        OrderService orderService = new OrderService();
        Menu menu = new Menu(Map.of(
                menuNumber, new Product(name, price, stock)
        ));
        Order order = new Order(Map.of(
                orderNumber, orderVolume
        ), new HashMap<>());

        orderService.orderToTotalAmount(order, menu);
        int actual = order.getOrderResultToTotalAmount(RESULT_NAME_TOTAL_AMOUNT.getMessage());

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("주문한 상품 배송비 확인")
    @ParameterizedTest
    @CsvSource(value = {
            "111111,의류1,10000,1,111111,1,2500",
            "222222,의류2,30000,5,222222,4,0"
    })
    void orderToDeliveryAmountExist(int menuNumber,
                                    String name, int price, int stock,
                                    int orderNumber, int orderVolume,
                                    int expected) {
        OrderService orderService = new OrderService();
        Menu menu = new Menu(Map.of(
                menuNumber, new Product(name, price, stock)
        ));
        Order order = new Order(Map.of(
                orderNumber, orderVolume
        ), new HashMap<>());

        orderService.orderToDeliveryAmount(order, menu);
        int actual = order.getOrderResultToDeliveryAmount(RESULT_NAME_DELIVERY_AMOUNT.getMessage());

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("주문한 상품 지불 금액 확인")
    @ParameterizedTest
    @CsvSource(value = {
            "40000,2500,0,42500",
            "50000,0,0,50000"
    })
    void orderToPayAmountExist(int totalAmount, int deliveryAmount, int payAmount,
                               int expected) {
        OrderService orderService = new OrderService();
        Order order = new Order(
                new HashMap<>(),
                new HashMap<>()
        );

        order.addOrderResult(RESULT_NAME_TOTAL_AMOUNT.getMessage(), totalAmount);
        order.addOrderResult(RESULT_NAME_DELIVERY_AMOUNT.getMessage(), deliveryAmount);
        order.addOrderResult(RESULT_NAME_PAY_AMOUNT.getMessage(), payAmount);

        orderService.orderToPayAmount(order);
        int actual = order.getOrderResultToPayAmount(RESULT_NAME_PAY_AMOUNT.getMessage());

        assertThat(actual).isEqualTo(expected);
    }
}