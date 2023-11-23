package kr.co._29cm.homework.repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kr.co._29cm.homework.model.Product;
import kr.co._29cm.homework.model.ProductMap;

public class ProductRepository {
    private Map<Integer, Product> productMap; // 상품번호로 상품정보 매핑하기 위한 Map 변수
    private BufferedReader file;

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
    public synchronized boolean getOrderComplete(List<Integer> productNumbers, List<Integer> productStocks) {
        int deliveryFee = 2500; // 배송료
        int freeDelivery = 50000; // 무료 배송이 적용되는 최소 주문 금액
        int pay; // 결제 금액
        int orderPrice = 0; // 주문한 금액
        String resultOrderPrice = ""; // 주문내역에 출력하는 '주문금액'
        String resultPay = ""; // 주문내역에 출력하는 '지불금액'
        String resultDeliveryFee = ""; // 주문내역에 출력하는 '배송비'
        boolean checkStock = false; // 재고 수가 남아있는지 여부

        StringBuilder orderDetails = new StringBuilder();
        orderDetails.append("주문내역:\n")
                .append("---------------------------------\n");

        // 고객이 지금까지 주문한 상품번호를 활용해 주문내역을 만들고 재고를 변경한다
        for (int i = 0; i < productNumbers.size(); i++) {
            int currentNumber = productNumbers.get(i);

            // 결제 진행되기 전 재고 확인하고 재고 부족할 경우 'SoldOutException' 발생
            checkStock = getStockCheck(currentNumber, productStocks.get(i));

            if (!checkStock) { // 'checkStock' false 일 때, 함수 리턴
                return checkStock;
            }
        }

        if (checkStock) { // 재고가 부족하지 않을 때,
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
        }

        // TODO: orderDetails 문자열의 '주문금액' 과 '지불금액' 을 추가해야한다!!!!!
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
        return checkStock;
    }

    public ProductMap getCSVData() {
        try {
            String line;
            Map<Integer, Product> saveProduct = new HashMap<>();
            file = new BufferedReader(new FileReader("_items.csv"));

            while ((line = file.readLine()) != null) { // readLine()은 파일에서 개행된 한 줄의 데이터를 읽어온다.
                String[] tokens = splitToComma(line);

                validateTokens(tokens, saveProduct);
            }
            return new ProductMap(saveProduct);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fileReaderClose(file);
        }
        return null;
    }

    private void validateTokens(String[] tokens, Map<Integer, Product> saveProduct) {
        for (int idx = 0; idx < tokens.length; idx++) {
            tokens[idx] = tokens[idx].replaceAll("^\"|\"$", "");
            if (!validateTitle(tokens[idx])) {
                addProductMap(tokens, saveProduct);
            }
        }
    }

    private String[] splitToComma(String line) {
        // 문자열을 나누는 split() 함수의 작성된 정규표현식은,
        // 콤마(,)를 기준으로 분할하되, 큰 따옴표(") 내부에 있는 콤마는 무시한다.
        return line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
    }

    private boolean validateTitle(String token) {
        return token.matches("상품번호|상품명|판매가격|재고수량");
    }

    private void addProductMap(String[] tokens, Map<Integer, Product> saveProduct) {
        int productNumber = Integer.parseInt(tokens[0]);
        String productName = tokens[1];
        int productPrice = Integer.parseInt(tokens[2]);
        int productStock = Integer.parseInt(tokens[3]);

        saveProduct.put(productNumber, new Product(productName, productPrice, productStock));
    }

    private void fileReaderClose(BufferedReader reader) {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}