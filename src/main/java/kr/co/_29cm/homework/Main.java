package kr.co._29cm.homework;

import kr.co._29cm.homework.data.ProductRepository;
import kr.co._29cm.homework.service.ProductSale;

/* 29cm 과제전형 아이디어 구성
 * 1. 데이터베이스를 사용하지 않고 CSV 파일 데이터를 사용한다.
 * 2. 가져온 데이터 19줄을 상품번호 기준으로 저장한다.
 * 3. 상품 거래를 위한 반복하는 콘솔 입·출력을 만든다.
 * 4. 입력된 값에 따라 상품거래(재고수량 확인 및 차감)를 수행한다.
 * 5. 재고수량이 없거나 부족할 때는 "SoldOutException 발생. 주문한 상품량이 재고량보다 큽니다." 출력
 * 6. "q" 입력시 "고객님의 주문 감사합니다." 출력 후 프로그램 종료
 * 7. 제시된 명령 이외에 다른 명령 입력시 "알 수 없는 명령입니다. 제시된 명령을 입력해주세요." 출력
 * */
public class Main {

    public static void main(String[] args) {
        ProductRepository productRepository = new ProductRepository();
        productRepository.getData(); // CSV 파일에서 데이터 가져오기

        ProductSale productSale = new ProductSale(productRepository);
        productSale.run(); // 상품거래 시작하기

    } // main function end
} // Main class end
