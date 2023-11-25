package kr.co._29cm.homework.model;

import static kr.co._29cm.homework.constant.exception.messageException.PRODUCT_NOT_EXIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import kr.co._29cm.homework.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MenuTest {
    @DisplayName("주문한 수량이 재고 수를 초과 여부 확인")
    @ParameterizedTest
    @CsvSource(value = {
            "111111,의류1,10000,1,111111,2,false",
            "222222,의류2,20000,2,222222,3,false",
            "333333,의류3,30000,3,333333,3,true",
            "444444,의류4,40000,4,444444,4,true"
    })
    void orderVolumeInMenuStock(int menuNumber,
                                String name, int price, int stock,
                                int orderNumber, int orderVolume,
                                boolean expected) {
        Menu menu = new Menu(Map.of(
                menuNumber, new Product(name, price, stock)
        ));

        boolean actual = menu.volumeInStock(orderNumber, orderVolume);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("주문한 상품번호와 상품수량을 기존 메뉴에 반영 여부 확인")
    @ParameterizedTest
    @CsvSource(value = {
            "111111,의류1,10000,1,111111,1",
            "222222,의류2,20000,4,222222,3"
    })
    void orderNumberAndVolumeToMenuApply(int menuNumber,
                                         String name, int price, int stock,
                                         int orderNumber, int orderVolume) {
        Menu menu = new Menu(new HashMap<>());
        menu.getMenus().put(menuNumber, new Product(name, price, stock));

        Order order = new Order(Map.of(
                orderNumber, orderVolume
        ), new HashMap<>());

        Set<Entry<Integer, Integer>> products = order.getNumberAndVolumes();
        menu.changeMenuOfOrderApply(products);
        int actual = stock - orderVolume;
        int expected = menu.getStock(orderNumber);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("주문한 상품번호가 메뉴에 없는 경우 예외 발생")
    @ParameterizedTest
    @CsvSource(value = {
            "123456,1",
            "999999,2"
    })
    void orderNumberNotInMenuNumber(int orderNumber, int orderVolume) {
        ProductRepository productRepository = new ProductRepository();
        Order order = new Order(Map.of(
                orderNumber, orderVolume
        ), new HashMap<>());
        Menu menu = productRepository.getCSVData();

        assertThatThrownBy(() -> menu.menuNumberToExist(order))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(PRODUCT_NOT_EXIST.getMessage());

    }
}