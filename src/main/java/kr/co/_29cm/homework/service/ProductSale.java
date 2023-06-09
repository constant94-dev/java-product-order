package kr.co._29cm.homework.service;

import kr.co._29cm.homework.data.ProductRepository;
import kr.co._29cm.homework.domain.Product;

import java.util.*;

public class ProductSale {
    ProductRepository productRepository;
    Product product;

    public ProductSale(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 상품거래 시작하기
    public void run() {
        Scanner sc = new Scanner(System.in);
        List<Integer> productNumbers = new ArrayList<>(); // 상품번호
        List<Integer> productStocks = new ArrayList<>(); // 상품재고 수
        int productNumber = 0;
        int productStock = 0;
        boolean productCheck = false; // 상품번호 존재여부
        Map<Integer, Integer> ff = new HashMap<>();
        String order = ""; // 주문 or 종료

        while (true) { // 종료 입력 전까지 '주문'을 반복적으로 입력 받는 반복문
            System.out.print("입력(o[order]: 주문, q[quit]: 종료) : ");
            order = sc.nextLine();

            if (order.equals("o")) {
                productRepository.getCurrentProductInfo();
                boolean orderComplete = false;
                while (!orderComplete) { // '상품번호', '수량'을 반복적으로 입력 받는 반복문
                    System.out.print("상품번호: ");
                    String orderNumberCheck = sc.nextLine();

                    if (orderNumberCheck.isEmpty() || orderNumberCheck.trim().isEmpty()) { // 상품번호 공백일 떼
                        System.out.print("수량: ");
                        String orderStockCheck = sc.nextLine();

                        if (orderStockCheck.isEmpty() || orderStockCheck.trim().isEmpty()) { // 수량 공백일 때
                            break;
                        }
                    } else {
                        System.out.println("상품번호 입력 받았어!!!!!");
                        productNumber = Integer.parseInt(orderNumberCheck);
                        productCheck = productRepository.getProductCheck(productNumber);
                        if (productCheck) {
                            System.out.print("수량: ");
                            if (sc.hasNextInt()) {
                                productStock = sc.nextInt();
                                productNumbers.add(productNumber);
                                productStocks.add(productStock);
                                sc.nextLine();
                            }
                        }
                    }
//                    if (productCheck) {
//                        System.out.print("수량: ");
//                        if (sc.hasNextInt()) {
//                            productStock = sc.nextInt();
//                            productNumbers.add(productNumber);
//                            productStocks.add(productStock);
//                        }
//                    }
                }
                // 지금까지 입력된 상품번호와 상품수량을 활용해 상품정보(상품이름, 상품가격)를 가져오고,
                // 아래 출력문에서 사용한다.
                if (productNumbers.size() != 0) {
                    productRepository.orderComplete(productNumbers, productStocks);
                }
            } else if (order.equals("q") || order.equals("quit")) {
                System.out.println("고객님의 주문 감사합니다.");
                break;
            } else {
                System.out.println("알 수 없는 명령입니다. 제시된 명령을 입력해주세요. o 또는 q 입력 이외 값 체크");
            }
        }

        sc.close(); // 스캐너 입력 종료
    } // run function 끝
}