package kr.co._29cm.homework.service.products;

import kr.co._29cm.homework.data.repository.products.ProductRepository;

import java.util.*;

public class ProductSale {
    ProductRepository productRepository;

    public ProductSale(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 상품거래 시작하기
    public void run() {
        Scanner sc = new Scanner(System.in);
        List<Integer> productNumbers = new ArrayList<>(); // 주문할 상품번호 모음
        List<Integer> productStocks = new ArrayList<>(); // 주문할 상품재고 수 모음
        int orderNumber = 0; // 주문할 상품번호
        int orderStock = 0; // 주문할 상품재고 수
        boolean productCheck = false; // 상품번호 존재여부
        String order = ""; // 주문 or 종료

        while (true) { // 종료 입력 전까지 '주문'을 반복적으로 입력 받는 반복문
            System.out.print("입력(o[order]: 주문, q[quit]: 종료) : ");
            order = sc.nextLine();

            if (order.equals("o") || order.equals("order")) {
                productRepository.getCurrentProductInfo();
                while (true) { // '상품번호', '수량'을 반복적으로 입력 받는 반복문
                    System.out.print("상품번호: ");
                    String orderNumberCheck = sc.nextLine();

                    if (orderNumberCheck.isEmpty() || orderNumberCheck.trim().isEmpty()) { // 상품번호 공백일 떼

                        productCheck = productRepository.getStockCheck(orderNumber, orderStock); // 고객이 주문한 상품의 재고량을 확인
                        if (!productCheck) {
                            productNumbers.clear();
                            productStocks.clear();
                            break;
                        }

                        System.out.print("수량: ");
                        String orderStockCheck = sc.nextLine();

                        if (orderStockCheck.isEmpty() || orderStockCheck.trim().isEmpty()) { // 수량 공백일 때
                            break;
                        }
                    } else { // 상품번호 공백이 아닐 때
                        try {
                            orderNumber = Integer.parseInt(orderNumberCheck);
                            productCheck = productRepository.getProductCheck(orderNumber);

                            if (productCheck) {
                                System.out.print("수량: ");
                                String orderStockCheck = sc.nextLine();
                                orderStock = Integer.parseInt(orderStockCheck.trim());

                                productNumbers.add(orderNumber);
                                productStocks.add(orderStock);
                            }
                        } catch (Exception e) {
                            System.out.println("주문하시는 상품번호와 수량을 다시 한번 확인해주세요.");
                        }
                    }
                }
                // 지금까지 입력된 상품번호와 상품수량을 활용해 상품정보(상품이름, 상품가격)를 확인하고 주문내역을 출력한다
                if (productNumbers.size() > 0 && productStocks.size() > 0) {
                    productRepository.getOrderComplete(productNumbers, productStocks);
                    // 주문이 완료되었으니 주문내역을 삭제한다.
                    productNumbers.clear();
                    productStocks.clear();
                }
            } else if (order.equals("q") || order.equals("quit")) {
                System.out.println("고객님의 주문 감사합니다.");
                break;
            } else {
                System.out.println("알 수 없는 명령입니다. 제시된 명령을 입력해주세요. o 또는 q 입력 이외 값 체크");
            }
        }

        sc.close(); // 스캐너 입력 종료
    }
}