package kr.co._29cm.homework.data;

import kr.co._29cm.homework.domain.Product;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Getter
public class ProductRepository {
    private Map<Integer, Product> productMap = new HashMap<>();

    public ProductRepository() {}

    // 상품번호로 상품정보 가져오기
/*    public void getProductInfo(int productNumber) {
        Product product = productMap.get(productNumber);
        if (product != null) {
            System.out.println("상품명: " + product.getName());
            System.out.println("판매가격: " + product.getPrice());
            System.out.println("재고수량: " + product.getStock());
        } else {
            System.out.println("해당 상품번호의 상품이 존재하지 않습니다.");
        }
    }*/

    // 현재 상품정보 가져오기
    public void getCurrentProductInfo(){
        System.out.println("상품번호\t상품명\t\t\t\t\t\t\t\t\t\t판매가격\t재고수");
        productMap.forEach((k, v) -> {
            System.out.println(k + "\t" + v.getName() + "\t" + v.getPrice() + "\t" + v.getStock());
        });
    }

    // CSV 파일 불러오기
    public void getCSV() {
        String line = "";
        BufferedReader file = null;

        try {
            file = new BufferedReader(
                    new FileReader("C:\\Users\\sjpar\\IdeaProjects\\_items.csv"));

            while ((line = file.readLine()) != null) { // readLine()은 파일에서 개행된 한 줄의 데이터를 읽어온다.
                // 문자열을 나누는 split() 함수의 작성된 정규표현식은,
                // 콤마(,)를 기준으로 분할하되, 따옴표(") 내부에 있는 콤마는 무시한다.
                String[] arr = line.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
                // 상품번호 라인은 상품거래 로직에서 사용하지 않으니 저장하지 않고 패스
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
    } // getCSV function end
}
