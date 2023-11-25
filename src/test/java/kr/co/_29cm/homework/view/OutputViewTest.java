package kr.co._29cm.homework.view;

import java.util.Map;
import kr.co._29cm.homework.model.Menu;
import kr.co._29cm.homework.model.Order;
import kr.co._29cm.homework.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class OutputViewTest {

    private OutputView outputView;
    private ProductRepository productRepository;
    private Menu menu;


    @BeforeEach
    void setUp() {
        outputView = new OutputView();
        productRepository = new ProductRepository();
        menu = new Menu(productRepository.getCSVData().getMenus());
    }

    @DisplayName("전체 상품 데이터 출력 확인")
    @Test
    void printToTotalProductCheck() {
        productRepository = new ProductRepository();
        menu = productRepository.getCSVData();

        outputView.printToTotalProduct(menu);
    }

    @DisplayName("고객 주문 종료 출력 확인")
    @Test
    void printToOrderQuitCheck() {
        outputView.printToOrderQuit();
    }

    @DisplayName("고객 주문 내역 출력 확인")
    @ParameterizedTest
    @CsvSource(value = {
            "768848,10,total_amount,210000,delivery_amount,0,pay_amount,210000",
            "768848,1,total_amount,21000,delivery_amount,2500,pay_amount,23500"
    })
    void printToOrderResultCheck(int orderNumber, int orderVolume,
                                 String totalName, int totalAmount,
                                 String deliveryName, int deliveryAmount,
                                 String payName, int payAmount) {
        Order order = new Order(
                Map.of(
                        orderNumber, orderVolume
                ),
                Map.of(
                        totalName, totalAmount,
                        deliveryName, deliveryAmount,
                        payName, payAmount
                )
        );
        menu = new Menu(productRepository.getCSVData().getMenus());

        outputView.printToOrderResult(order, menu);
    }
}