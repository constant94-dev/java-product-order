package kr.co._29cm.homework.service;

import kr.co._29cm.homework.data.ProductRepository;
import kr.co._29cm.homework.domain.Product;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ProductSale {
    ProductRepository productRepository;

    public ProductSale(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 상품거래 시작하기
    public void run() {
        Scanner sc = new Scanner(System.in);
        int productNumber = 0; // 상품번호
        String order = ""; // 주문 or 종료

        while (true) {
            System.out.print("입력(o[order]: 주문, q[quit]: 종료) : ");
            if (sc.hasNext()) {
                order = sc.nextLine();

                if (order.equals("o")) {
                    productRepository.getCurrentProductInfo();
                    System.out.print("상품번호: ");
                    if (sc.hasNextInt()) {
                        productNumber = sc.nextInt();
                    } else {
                        System.out.println("상품번호를 입력해주세요.");
                        // 정수값이 입력되지 않았을 때 스캐너에 문자값이 잔여물로 남아있어 다음 주문 때,
                        // 문자값이 사용되므로 스캐너의 개행을 수행해 잔여물이 남아있지 않게 해준다.
                        sc.nextLine();
                    }
                } else if (order.equals("q")) {
                    System.out.println("고객님의 주문 감사합니다.");
                    break;
                } else {
                    System.out.println("알 수 없는 명령입니다. 제시된 명령을 입력해주세요.1111");
                }
            } else {
                System.out.println("알 수 없는 명령입니다. 제시된 명령을 입력해주세요.2222");
            }
        } // while 반복문 끝

        sc.close(); // 스캐너 입력 종료
    } // run function 끝
}