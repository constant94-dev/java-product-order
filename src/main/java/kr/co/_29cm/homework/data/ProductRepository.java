package kr.co._29cm.homework.data;

import kr.co._29cm.homework.domain.Product;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ProductRepository {
    private Map<Integer, Product> productMap;

    public ProductRepository() {
        this.productMap = new HashMap<>();
    }

    // 현재 상품정보 가져오기
    public void getCurrentProductInfo() {
        System.out.println("상품번호\t상품명\t\t\t\t\t\t\t\t\t\t판매가격\t재고수");
        productMap.forEach((k, v) ->
                System.out.println(String.format("%s\t%s\t%s\t%s", k, v.getName(), v.getPrice(), v.getStock()))
        );
    }

    // 상품번호로 상품 존재여부 검사 기능
    public boolean getProductCheck(int productNumber) {
        Product productExist = productMap.get(productNumber);
        if (productExist != null) {
            return true;
        }
        System.out.println("존재하지 않는 상품번호 입니다. 제시된 상품번호를 확인해주세요.");
        return false;
    }

    // 고객이 주문한 상품의 재고 수 확인 기능
    public boolean getStockCheck(int orderNumber, int orderStock) {
        int currentStock;
        Product product = productMap.get(orderNumber);
        if (product != null) {
            currentStock = product.getStock();
            if (currentStock < orderStock) {
                System.out.println("SoldOutException 발생. 주문한 상품량이 재고량보다 큽니다.");
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    // 고객이 주문한 상품결제 기능
    public void orderComplete(List<Integer> productNumbers, List<Integer> productStocks) {
        int deliveryFee = 2500; // 배송료
        int freeDelivery = 50000; // 무료 배송이 적용되는 최소 주문 금액
        int pay; // 결제 금액
        int orderPrice = 0; // 주문한 금액
        String resultOrderPrice = "";
        String resultPay = "";
        String resultDeliveryFee = "";

        StringBuilder orderDetails = new StringBuilder("---------------------------------\n");
        // 고객이 지금까지 주문한 상품번호를 활용해 주문내역을 만들고 재고를 변경한다
        for (int i = 0; i < productNumbers.size(); i++) {
            int currentNumber = productNumbers.get(i);
            Product productComplete = productMap.get(currentNumber);
            orderDetails.append(productComplete.getName()).append(" - ").append(productStocks.get(i)).append("개\n");

            orderPrice += (productComplete.getPrice() * productStocks.get(i));

            int orderStock = productComplete.getStock(); // 상품번호가 현재 가지는 재고 수
            int updateStock = orderStock - productStocks.get(i); // 상품번호가 현재 가지는 재고 수 - 고객이 주문한 재고 수
            productMap.put(productNumbers.get(i),
                    new Product(
                            productMap.get(currentNumber).getName(),
                            productMap.get(currentNumber).getPrice(),
                            updateStock));
        }


        // TODO:orderDetails 문자열의 '주문금액' 과 '지불금액' 을 추가해야한다!!!!!
        if (orderPrice < freeDelivery) {
            // 배송료 2,500원 추가
            pay = (orderPrice + deliveryFee);
            resultOrderPrice = String.format("%,d원\n", orderPrice);
            resultDeliveryFee = String.format("%,d원\n", deliveryFee);
            resultPay = String.format("%,d원\n", pay);

            orderDetails
                    .append("---------------------------------\n")
                    .append("주문금액: ").append(resultOrderPrice)
                    .append("배송비: ").append(resultDeliveryFee)
                    .append("---------------------------------\n")
                    .append("지불금액: ").append(resultPay)
                    .append("---------------------------------\n");
        } else {
            // 무료 배송
            pay = orderPrice;
            resultOrderPrice = String.format("%,d원\n", orderPrice);
            resultPay = String.format("%,d원\n", pay);

            orderDetails
                    .append("---------------------------------\n")
                    .append("주문금액: ").append(resultOrderPrice)
                    .append("---------------------------------\n")
                    .append("지불금액: ").append(resultPay)
                    .append("---------------------------------\n");
        }


        // 주문 내역과 주문 금액, 결제 금액(배송비 포함) 을 화면에 display 한다.
        String orderDetailsResult = orderDetails.toString();
        System.out.println(orderDetailsResult);
    }

    // CSV 파일 불러오기
    public void getData() {
        String line = "";
        BufferedReader file = null;

        try {
            file = new BufferedReader(
                    new FileReader("[29CM 23 SS 공채] 백엔드 과제 _items.csv"));

            while ((line = file.readLine()) != null) { // readLine()은 파일에서 개행된 한 줄의 데이터를 읽어온다.
                // 문자열을 나누는 split() 함수의 작성된 정규표현식은,
                // 콤마(,)를 기준으로 분할하되, 따옴표(") 내부에 있는 콤마는 무시한다.
                String[] arr = line.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
                // (상품번호, 상품명, 판매가격, 재고수) 라인은 상품거래 로직에서 사용하지 않으니 저장하지 않고 패스
                if (arr[0].equals("상품번호")) {
                    continue;
                }

                // 19줄의 데이터 저장하기
                int productNumber = Integer.parseInt(arr[0]);
                String productName = arr[1];
                int productPrice = Integer.parseInt(arr[2]);
                int productStock = Integer.parseInt(arr[3]);
                productMap.put(productNumber, new Product(productName, productPrice, productStock));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (file != null) {
                    file.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } // finally end
    } // getData function end
}