package kr.co._29cm.homework.service;

import static kr.co._29cm.homework.constant.exception.messageException.INVALID_ORDER_NUMBER;
import static kr.co._29cm.homework.constant.exception.messageException.PRODUCT_SOLD_OUT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Map;
import kr.co._29cm.homework.model.Menu;
import kr.co._29cm.homework.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class OrderServiceTest {

    @DisplayName("주문 상품 번호 존재하는 경우 성공")
    @ParameterizedTest
    @CsvSource(value = {"768848,true"})
    void orderNumberToExist(String number, boolean expected) {
        Menu menu = new Menu(Map.of(
                768848, new Product("[STANLEY] GO CERAMIVAC 진공 텀블러/보틀 3종", 21000, 45)
        ));
        OrderService orderService = new OrderService();

        boolean actual = orderService.validateOrderNumber(number, menu);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("주문 상품 번호 존재하지 않는 경우 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"111111", "222222", "333333", "777777"})
    void orderNumberToNotExist(String number) {
        Menu menu = new Menu(Map.of(
                768848, new Product("[STANLEY] GO CERAMIVAC 진공 텀블러/보틀 3종", 21000, 45)
        ));
        OrderService orderService = new OrderService();

        assertThatThrownBy(() -> orderService.validateOrderNumber(number, menu))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(INVALID_ORDER_NUMBER.getMessage());
    }

    @DisplayName("주문 상품 재고 부족 여부 확인")
    @ParameterizedTest
    @CsvSource(value = {"1,768848,true", "46,768848,false", "45,768848,true"})
    void orderVolumeInStock(int volume, int number, boolean expected) {
        Menu menu = new Menu(Map.of(
                768848, new Product("[STANLEY] GO CERAMIVAC 진공 텀블러/보틀 3종", 21000, 45)
        ));

        assertThat(menu.volumeInStock(volume, number)).isEqualTo(expected);
    }

    @DisplayName("주문 상품 재고 부족 예외 발생")
    @ParameterizedTest
    @CsvSource(value = {"46,768848", "50,768848", "100,768848"})
    void orderVolumeNotInStock(String volume, String number) {
        Menu menu = new Menu(Map.of(
                768848, new Product("[STANLEY] GO CERAMIVAC 진공 텀블러/보틀 3종", 21000, 45)
        ));
        OrderService orderService = new OrderService();

        assertThatThrownBy(() -> orderService.validateOrderVolume(volume, number, menu))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(PRODUCT_SOLD_OUT.getMessage());
    }
}