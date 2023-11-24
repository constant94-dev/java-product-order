package kr.co._29cm.homework.model;

import static kr.co._29cm.homework.constant.exception.messageException.PRODUCT_NOT_EXIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class OrderTest {

    @DisplayName("주문 상품 추가 확인")
    @ParameterizedTest
    @CsvSource(value = {"111111,1,111111", "222222,2,222222", "333333,3,333333"})
    void addOrderNumberAndVolumeToSave(int orderNumber, int orderVolume, int expected) {
        Order order = new Order(new HashMap<>());

        order.addOrderNumberAndVolume(orderNumber, orderVolume);
        Set<Integer> actual = order.getOrders().keySet();

        assertThat(actual).contains(expected);
    }

    @DisplayName("주문 상품 번호와 수량이 존재하지 않는 경우 예외 발생")
    @ParameterizedTest
    @CsvSource(value = {
            "111111,1,222222,의류1,10000,1",
            "222222,3,111111,의류2,20000,3"
    })
    void orderNumberAndVolumeToNotExist(int orderNumber, int orderVolume,
                                        int menuNumber,
                                        String name, int price, int stock) {
        Order order = new Order(Map.of(
                orderNumber, orderVolume
        ));
        Menu menu = new Menu(Map.of(
                menuNumber, new Product(name, price, stock)
        ));

        assertThatThrownBy(() -> order.validateOrderNumberAndVolume(menu))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(PRODUCT_NOT_EXIST.getMessage());
    }

}