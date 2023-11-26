package kr.co._29cm.homework;

import static kr.co._29cm.homework.constant.exception.messageException.PRODUCT_SOLD_OUT;
import static kr.co._29cm.homework.constant.view.OutputViewConstant.PRODUCT_ORDER_THANK;
import static kr.co._29cm.homework.validate.InputViewValidator.stockOfVolumeOrdered;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kr.co._29cm.homework.model.Menu;
import kr.co._29cm.homework.model.Order;
import kr.co._29cm.homework.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class MultiThreadRequestTest {
    private ProductRepository productRepository;
    private Menu menu;
    private ProductController controller;

    // 해당 클레스에 위치한 모든 테스트 메서드 실행 전에 딱 한번 실행되는 메서드
    // 매 테스트 실행 때마다 조건들이 초기화된다
    @BeforeEach
    public void setup() {
        productRepository = new ProductRepository();
        menu = new Menu(productRepository.getCSVData().getMenus());
        controller = new ProductController();
    }

    /**
     * Test 에서는 반드시 multi thread 요청으로 'SoldOutException'이 정상 동작하는지 확인하는 단위테스트가 작성되어야 합니다.
     */
    @DisplayName("동시 요청 테스트")
    @Test
    void orderWithConcurrency() throws InterruptedException {
        // given
        // 2 개의 Thread 로 구성된 고정 Thread 풀 생성
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(2);

        // 테스트의 사용할 데이터 정의
        Order orderA = new Order(
                Map.of(
                        213341, 50
                ), new HashMap<>()
        );
        Order orderB = new Order(
                Map.of(
                        213341, 100
                ), new HashMap<>()
        );

        // when
        // 예외를 캡처하는 방법을 'System.out'을 사용하지 않고
        // 동기화 리스트를 사용해 캡처하고 저장한다.
        List<Throwable> exceptions = Collections.synchronizedList(new ArrayList<>());

        executorService.submit(() -> {
            try {
                stockOfVolumeOrdered(orderA, menu);
            } catch (Throwable e) {
                exceptions.add(e);
            } finally {
                latch.countDown();
            }
        });

        executorService.submit(() -> {
            try {
                stockOfVolumeOrdered(orderB, menu);
            } catch (Throwable e) {
                exceptions.add(e);
            } finally {
                latch.countDown();
            }
        });
        latch.await();

        // then
        // 각 스레드에서 발생한 예외를 exceptions 리스트에 저장하고,
        // 마지막에 모든 예외를 검사하여 특정 메시지가 있는지 확인한다.
        // 이를 통해 어떤 스레드에서 예외가 발생했는지 확인할 수 있다.
        for (Throwable exception : exceptions) {
            assertThat(exception.getMessage()).contains(PRODUCT_SOLD_OUT.getMessage());
        }
    }

    @DisplayName("비동시 요청 테스트")
    @Test
    void orderWithNotConcurrency() {
        // given
        // 사용자 입력 값을 미리 정의합니다.
        String userInputs = "o\n782858\n1\n628066\n2\n \n \no\n760709\n10\n \n \no\n213341\n100\n \nq\n";

        InputStream inputStream = new ByteArrayInputStream(userInputs.getBytes());
        System.setIn(inputStream);

        // System.out을 PrintStream으로 리디렉션하여 콘솔 출력을 캡처한 다음 ProductSale의 run() 메서드를 실행합니다.
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        // 테스트할 메소드 호출
        controller.run();

        // when
        // 주문 관련 메시지가 콘솔 출력에 있는지 확인합니다.
        String consoleOutput = outputStream.toString();

        // then
        assertThat(consoleOutput).contains(PRODUCT_ORDER_THANK.getMessage());
    }

}