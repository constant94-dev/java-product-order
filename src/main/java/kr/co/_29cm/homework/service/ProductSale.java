package kr.co._29cm.homework.service;

import kr.co._29cm.homework.data.ProductRepository;
import kr.co._29cm.homework.domain.Product;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ProductSale {
    ProductRepository productRepository;
    Product product;

    public ProductSale(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 상품거래 시작하기
    public void run() {
        Scanner sc = new Scanner(System.in);
        int productNumber = 0; // 상품번호
        boolean productCheck = false; // 상품번호 존재여부
        int productStock = 0; // 상품재고 수
        String order = ""; // 주문 or 종료

        while (true) { // 종료 입력 전까지 '주문'을 반복적으로 입력 받는 반복문
            System.out.print("입력(o[order]: 주문, q[quit]: 종료) : ");
            if (sc.hasNext()) {
                order = sc.nextLine();

                if (order.equals("o")) {
                    productRepository.getCurrentProductInfo();
                    boolean orderComplete = false;
                    while (!orderComplete) { // '상품번호', '수량'을 반복적으로 입력 받는 반복문
                        System.out.print("상품번호: ");
                        if (sc.hasNext()) {
                            System.out.println("상품번호 입력 받았어!!!!!");
                            productNumber = Integer.parseInt(sc.next());
                            productCheck = productRepository.getProductCheck(productNumber);
                            if (productCheck) {
                                System.out.print("수량: ");
                                if (sc.hasNextInt()) {
                                    productStock = sc.nextInt();
                                    // (상품재고 수 - 입력한 수량)을 구하고 해당 상품은 재고 수가 변경된다.
                                    productRepository.updateProductStock(productNumber, productStock);
                                    // 해당 상품 재고 수 변경 후 아이템 조회
                                    productRepository.getCurrentProductInfo();
                                } else if (sc.hasNext("\\s")) {
                                    sc.nextLine(); // Space + Enter 입력을 처리하고 다음 입력 준비
                                }
                            }
                        } else {
                            System.out.println("잘못된 상품번호입니다. 제시된 상품번호를 확인해주세요.");
                            // 정수값이 입력되지 않았을 때 스캐너에 문자값이 잔여물로 남아있어 다음 주문 때,
                            // 문자값이 사용되므로 스캐너의 개행을 수행해 잔여물이 남아있지 않게 해준다.
                            sc.nextLine();
                        }
                    }
                } else if (order.equals("q") || order.equals("quit")) {
                    System.out.println("고객님의 주문 감사합니다.");
                    break;
                } else {
                    System.out.println("알 수 없는 명령입니다. 제시된 명령을 입력해주세요. o 또는 q 입력 이외 값 체크");
                }
            }
        }

        sc.close(); // 스캐너 입력 종료
    } // run function 끝
}