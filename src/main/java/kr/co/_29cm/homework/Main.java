package kr.co._29cm.homework;

import kr.co._29cm.homework.data.repository.products.ProductRepository;
import kr.co._29cm.homework.service.products.ProductSale;

public class Main {
    public static void main(String[] args) {
        ProductRepository productRepository = new ProductRepository();
        productRepository.getData(); // CSV 파일에서 데이터 가져오기

        ProductSale productSale = new ProductSale(productRepository);
        productSale.run(); // 상품주문 시작하기
    }
}