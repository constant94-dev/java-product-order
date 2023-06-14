package kr.co._29cm.homework;

import kr.co._29cm.homework.data.repository.products.ProductRepository;
import kr.co._29cm.homework.service.products.ProductSale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;


public class MultiThreadRequestTest {
    private ProductRepository productRepository;
    private ProductSale productSale;

    // 해당 클레스에 위치한 모든 테스트 메서드 실행 전에 딱 한번 실행되는 메서드
    // 매 테스트 실행 때마다 조건들이 초기화된다
    @BeforeEach
    public void setup() {
        productRepository = new ProductRepository();
        productRepository.getData();
        productSale = new ProductSale(productRepository);
    }

    /**
     * Test 에서는 반드시 multi thread 요청으로 'SoldOutException'이 정상 동작하는지 확인하는 단위테스트가 작성되어야 합니다.
     */
    @DisplayName("동시 요청 테스트")
    @Test
    void testSaleWithConcurrency() throws InterruptedException {
        // given
        // 2 개의 Thread 로 구성된 고정 Thread 풀 생성
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(2);

        // 'AtomicBoolean' 다른 스레드의 영향을 받지 않고 값을 수정할 수 있다.
        // 한 스레드에서 변수 값을 변경하면 다른 스레드에서 변경된 값을 즉시 볼 수 있다.
        AtomicBoolean result_userA = new AtomicBoolean(true);
        AtomicBoolean result_userB = new AtomicBoolean(true);

        // 테스트의 사용할 데이터 정의
        List<Integer> productNumbers_userA = new ArrayList<>();
        List<Integer> productStocks_userA = new ArrayList<>();

        List<Integer> productNumbers_userB = new ArrayList<>();
        List<Integer> productStocks_userB = new ArrayList<>();

        productNumbers_userA.add(213341);
        productStocks_userA.add(50);
        productNumbers_userB.add(213341);
        productStocks_userB.add(100);

        // when
        executorService.submit(() -> {
            result_userA.set(productRepository.getOrderComplete(productNumbers_userA, productStocks_userA));
            latch.countDown();
        });
        executorService.submit(() -> {
            result_userB.set(productRepository.getOrderComplete(productNumbers_userB, productStocks_userB));
            latch.countDown();
        });
        latch.await(); // 카운트 값이 0이 되어 모든 작업이 완료될 때까지 대기

        // System.out 을 PrintStream 으로 리디렉션하여 콘솔 출력을 캡처
        ByteArrayOutputStream user_outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(user_outputStream);
        System.setOut(printStream);
        String user_consoleOutput = user_outputStream.toString();
        executorService.shutdown();

        // then
        assertFalse(user_consoleOutput.contains("SoldOutException 발생. 주문한 상품량이 재고량보다 큽니다."));
    }

    @DisplayName("비동시 요청 테스트")
    @Test
    void testSaleWithNotConcurrency() {
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
        productSale.run();

        // when
        // 주문 관련 메시지가 콘솔 출력에 있는지 확인합니다.
        String consoleOutput = outputStream.toString();

        // then
        assertTrue(consoleOutput.contains("고객님의 주문 감사합니다."));
    }

    @DisplayName("미완성된 상품 주문 기능 전체 테스트")
    @Test
    void coverageTest() {
        // 사용자 입력 값을 미리 정의합니다.
        String userInputs = "o\n782858\n1\n628066\n2\n \n \no\n760709\n10\n \n \no\n213341\n100\n \nq\n";

        // 사용자 입력을 콘솔 창에 순서대로 흐르게 하는 'InputStream'
        InputStream inputStream = new ByteArrayInputStream(userInputs.getBytes());
        System.setIn(inputStream);

        // 'System.out'을 'PrintStream'으로 리디렉션하여 콘솔 출력을 캡처한 다음 'ProductSale'의 run() 메서드를 실행합니다.
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        productSale.run();

        String consoleOutput = outputStream.toString();
        boolean stockCheck = consoleOutput.contains("SoldOutException 발생. 주문한 상품량이 재고량보다 큽니다.");
        if (stockCheck) {
            System.out.println("SoldOutException 발생!!!!!!!!!!!!!!!!");
        } else {
            System.out.println("SoldOutException 안 발생!!!!!!!!!!!!!!!!");
        }
    }

}