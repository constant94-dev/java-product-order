package kr.co._29cm.homework.validate;

import static kr.co._29cm.homework.constant.exception.messageException.INVALID_CHOOSE;
import static kr.co._29cm.homework.constant.exception.messageException.INVALID_ORDER_NUMBER;
import static kr.co._29cm.homework.constant.exception.messageException.INVALID_ORDER_VOLUME;
import static kr.co._29cm.homework.constant.exception.messageException.PRODUCT_NOT_EXIST;
import static kr.co._29cm.homework.constant.exception.messageException.PRODUCT_SOLD_OUT;
import static kr.co._29cm.homework.validate.InputViewValidator.stockOfVolumeOrdered;
import static kr.co._29cm.homework.validate.InputViewValidator.validateOrderNumberToEmpty;
import static kr.co._29cm.homework.validate.InputViewValidator.validateOrderNumberToInteger;
import static kr.co._29cm.homework.validate.InputViewValidator.validateOrderORQuit;
import static kr.co._29cm.homework.validate.InputViewValidator.validateOrderVolumeToEmpty;
import static kr.co._29cm.homework.validate.InputViewValidator.validateOrderVolumeToInteger;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashMap;
import java.util.Map;
import kr.co._29cm.homework.model.Menu;
import kr.co._29cm.homework.model.Order;
import kr.co._29cm.homework.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class InputViewValidatorTest {

    @DisplayName("주문 또는 종료 관련 명령 아닌 경우 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"or", "", "ord", "orde", "qu", "qui"})
    void orderORQuitNotInChoose(String choose) {
        assertThatThrownBy(() -> validateOrderORQuit(choose))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(INVALID_CHOOSE.getMessage());
    }

    @DisplayName("주문 번호가 정수로 변환되지 않을 경우 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"한국 ", "사랑", " 독도"})
    void notConvertOrderToInteger(String orderNumber) {
        assertThatThrownBy(() -> validateOrderNumberToInteger(orderNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(INVALID_ORDER_NUMBER.getMessage());
    }

    @DisplayName("주문 번호가 비어있는 여부에 따라 상품번호 플래그 상수 확인")
    @ParameterizedTest
    @CsvSource(value = {
            "111111,의류1,10000,1,111111,continue",
            "222222,의류2,20000,2,'',break"
    })
    void orderNumberToEmptyORNotEmpty(int menuNumber,
                                      String name, int price, int stock,
                                      String orderNumber,
                                      String expected) {
        Menu menu = new Menu(Map.of(
                menuNumber, new Product(name, price, stock)
        ));
        Order order = new Order(new HashMap<>(), new HashMap<>());

        String actual = validateOrderNumberToEmpty(order, menu, orderNumber);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("주문 수량이 정수로 변환되지 않을 경우 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"사자", "호랑이", "독수리"})
    void notConvertOrderVolumeToInteger(String orderVolume) {
        assertThatThrownBy(() -> validateOrderVolumeToInteger(orderVolume))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(INVALID_ORDER_VOLUME.getMessage());
    }

    @DisplayName("주문 수량에 존재 여부 확인")
    @ParameterizedTest
    @CsvSource(value = {
            "111111,2,111111,'',false",
            "222222,4,222222,1,true"
    })
    void orderVolumeToEmptyORNotEmpty(int orderNumber, int orderVolume,
                                      String number, String volume,
                                      boolean expected) {
        Order order = new Order(new HashMap<>(), new HashMap<>());

        order.addOrderNumberAndVolume(orderNumber, orderVolume);
        boolean actual = validateOrderVolumeToEmpty(order, number, volume);

        assertThat(actual).isEqualTo(expected);
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
        ), new HashMap<>());
        Menu menu = new Menu(Map.of(
                menuNumber, new Product(name, price, stock)
        ));

        assertThatThrownBy(() -> stockOfVolumeOrdered(order, menu))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(PRODUCT_NOT_EXIST.getMessage());
    }

    @DisplayName("주문 상품 수량이 재고 수를 초과한 경우 예외 발생")
    @ParameterizedTest
    @CsvSource(value = {
            "111111,100,111111,의류1,10000,90",
            "222222,20,222222,의류2,20000,19"
    })
    void orderVolumeOfStockToOver(int orderNumber, int orderVolume,
                                  int menuNumber,
                                  String name, int price, int stock) {
        Order order = new Order(Map.of(
                orderNumber, orderVolume
        ), new HashMap<>());
        Menu menu = new Menu(Map.of(
                menuNumber, new Product(name, price, stock)
        ));

        assertThatThrownBy(() -> stockOfVolumeOrdered(order, menu))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(PRODUCT_SOLD_OUT.getMessage());
    }

    @DisplayName("주문 수량이 재고가 있는 경우 메뉴판에 반영")
    @ParameterizedTest
    @CsvSource(value = {"111111,50,111111,의류1,10000,50,0"})
    void orderNumberAndVolumeOfMenuApply(int orderNumber, int orderVolume,
                                         int menuNumber, String name, int price, int stock,
                                         int expected) {
        Order order = new Order(Map.of(
                orderNumber, orderVolume
        ), new HashMap<>());
        Menu menu = new Menu(new HashMap<>());

        menu.addMenuToProduct(menuNumber, new Product(name, price, stock));
        stockOfVolumeOrdered(order, menu);
        int actual = menu.getStock(orderNumber); // 주문 수량에서 재고 수를 제거한 남은 재고 수

        assertThat(actual).isEqualTo(expected);
    }
}