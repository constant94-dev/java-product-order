package kr.co._29cm.homework.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class OrderTest {

    @DisplayName("주문 상품 추가 확인")
    @ParameterizedTest
    @CsvSource(value = {
            "111111,1,111111",
            "222222,2,222222",
            "333333,3,333333"
    })
    void addOrderNumberAndVolumeToSave(int orderNumber, int orderVolume, int expected) {
        Order order = new Order(new HashMap<>(), new HashMap<>());

        order.addOrderNumberAndVolume(orderNumber, orderVolume);
        Set<Integer> actual = order.getNumbers();

        assertThat(actual).contains(expected);
    }

    @DisplayName("주문 내역 추가 확인")
    @ParameterizedTest
    @CsvSource(value = {
            "total_amount,10000,10000",
            "delivery_amount,2500,2500",
            "pay_amount,12500,12500"
    })
    void addOrderResultToSave(String resultName, int resultAmount, int expected) {
        Order order = new Order(new HashMap<>(), new HashMap<>());

        order.addOrderResult(resultName, resultAmount);
        int actual = order.getOrderResult().get(resultName);

        assertThat(actual).isEqualTo(expected);
    }
}