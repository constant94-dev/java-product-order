package kr.co._29cm.homework.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import kr.co._29cm.homework.model.Product;
import kr.co._29cm.homework.model.ProductMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductRepositoryTest {


    @DisplayName("csv 파일에서 가져온 상품정보 가져오기 성공")
    @Test
    void getCSVDataSuccess() {

        ProductRepository productRepository = new ProductRepository();
        Set<Integer> expected = Map.of(
                768848, new Product("1", 1, 1),
                648418, new Product("1", 2, 2)
        ).keySet();

        ProductMap productMap = productRepository.getCSVData();
        Set<Integer> actual = productMap.getProductMap().keySet();

        Set<Entry<Integer, Product>> tokens = productMap.getProductMap().entrySet();

        for (Entry<Integer, Product> token : tokens) {
            System.out.println("상품번호: " + token.getKey() + "\t상품명: " + token.getValue().getName());
        }

        assertThat(actual).containsAll(expected);
    }
}