package kr.co._29cm.homework;

import kr.co._29cm.homework.data.ProductRepository;
import kr.co._29cm.homework.domain.Product;
import kr.co._29cm.homework.service.ProductSale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class MultiThreadRequestTest {
    private ProductRepository productRepository;
    private ProductSale productSale;

    @BeforeEach
    public void setup() {
        productRepository = new ProductRepository();
        productRepository.getData();
        productSale = new ProductSale(productRepository);
    }

    // 주문 수량이 사용 가능한 재고를 초과하는 테스트 시나리오를 나타냅니다.
    @Test
    public void testOrderWithLackStock() {
        // 사용자 입력 값을 미리 정의합니다.
        String userInputs = "o\n213341\n100\n \nq";

        InputStream inputStream = new ByteArrayInputStream(userInputs.getBytes());
        System.setIn(inputStream);

        // System.out을 PrintStream으로 리디렉션하여 콘솔 출력을 캡처한 다음 ProductSale의 run() 메서드를 실행합니다.
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        productSale.run();

        // 주문 성공 메시지가 콘솔 출력에 있는지 확인합니다.
        String consoleOutput = outputStream.toString();
        assertTrue(consoleOutput.contains("고객님의 주문 감사합니다."));
    }

    @DisplayName("기본 테스트")
    @Test
    void defaultTest() {
        // given
        String str1 = "Target";
        String str2 = "Coders";

        // when
        String result = str1.concat(str2);

        // then
        assertThat("TargetCoders").isEqualTo(result);
    }

    /**
     * Test 에서는 반드시 multi thread 요청으로 SoldOutException 이
     * 정상 동작하는지 확인하는 단위테스트가 작성되어야 합니다.
     */
    @DisplayName("동시 요청 테스트")
    @Test
    void multiRequestTest(String input) {
        // given
        Scanner sc = new Scanner(System.in);

        // 테스트할 메소드 호출


        // when


        // then

    }
}