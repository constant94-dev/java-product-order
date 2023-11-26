package kr.co._29cm.homework.validate;

import static kr.co._29cm.homework.constant.service.ResultNameConstant.RESULT_NAME_DELIVERY_AMOUNT;
import static kr.co._29cm.homework.constant.service.ResultNameConstant.RESULT_NAME_PAY_AMOUNT;
import static kr.co._29cm.homework.constant.service.ResultNameConstant.RESULT_NAME_TOTAL_AMOUNT;
import static kr.co._29cm.homework.validate.OutputViewValidator.validateOrderAmount;
import static kr.co._29cm.homework.validate.OutputViewValidator.validateOrderDelivery;
import static kr.co._29cm.homework.validate.OutputViewValidator.validateOrderNameAndVolume;
import static kr.co._29cm.homework.validate.OutputViewValidator.validatePayAmount;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import kr.co._29cm.homework.model.Menu;
import kr.co._29cm.homework.model.Order;
import kr.co._29cm.homework.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class OutputViewValidatorTest {

    @DisplayName("주문한 상품 이름과 수량이 있는 경우 출력 확인")
    @ParameterizedTest
    @CsvSource(value = {
            "222222,의류2,20000,2,222222,1,의류2 - 1개"
    })
    void nameAndVolumeInOrder(int menuNumber,
                              String name, int price, int stock,
                              int orderNumber, int orderVolume,
                              String expected) {
        Menu menu = new Menu(Map.of(
                menuNumber, new Product(name, price, stock)
        ));
        Order order = new Order(Map.of(
                orderNumber, orderVolume
        ), new HashMap<>());

        String actual = validateOrderNameAndVolume(order, menu);

        assertThat(actual).isEqualToIgnoringWhitespace(expected);
    }

    @DisplayName("주문한 상품 이름과 수량이 없는 경우 출력 확인")
    @ParameterizedTest
    @CsvSource(value = {
            "222222,의류2,20000,2,주문 내역이 없습니다."
    })
    void nameAndVolumeNotInOrder(int menuNumber,
                                 String name, int price, int stock,
                                 String expected) {
        Menu menu = new Menu(Map.of(
                menuNumber, new Product(name, price, stock)
        ));
        Order order = new Order(new HashMap<>(), new HashMap<>());

        String actual = validateOrderNameAndVolume(order, menu);

        assertThat(actual).isEqualToIgnoringWhitespace(expected);
    }

    @DisplayName("주문한 상품의 총 금액에 맞는 출력 확인.")
    @ParameterizedTest
    @CsvSource(value = {
            "0,주문금액: 0원",
            "30000,'주문금액: 30,000원'"
    })
    void amountNotEmptyToOrder(int totalAmount, String expected) {
        Order order = new Order(new HashMap<>(), new HashMap<>());

        order.addOrderResult(RESULT_NAME_TOTAL_AMOUNT.getMessage(), totalAmount);
        String actual = validateOrderAmount(order);

        assertThat(actual).isEqualToIgnoringWhitespace(expected);
    }

    @DisplayName("주문한 상품의 배송비 존재 여부에 따른 출력 확인.")
    @ParameterizedTest
    @CsvSource(value = {
            "0,''",
            "2500,'배송비: 2,500원'"
    })
    void deliveryNotEmptyToOrder(int deliveryAmount, String expected) {
        Order order = new Order(new HashMap<>(), new HashMap<>());

        order.addOrderResult(RESULT_NAME_DELIVERY_AMOUNT.getMessage(), deliveryAmount);
        String actual = validateOrderDelivery(order);

        assertThat(actual).isEqualToIgnoringWhitespace(expected);
    }

    @DisplayName("주문한 상품의 실제 지불 금액에 맞는 출력 확인.")
    @ParameterizedTest
    @CsvSource(value = {
            "0,지불금액: 0원",
            "70000,'지불금액: 70,000원'"
    })
    void payAmountEmptyToOrder(int payAmount, String expected) {
        Order order = new Order(new HashMap<>(), new HashMap<>());

        order.addOrderResult(RESULT_NAME_PAY_AMOUNT.getMessage(), payAmount);
        String actual = validatePayAmount(order);

        assertThat(actual).isEqualToIgnoringWhitespace(expected);
    }
}