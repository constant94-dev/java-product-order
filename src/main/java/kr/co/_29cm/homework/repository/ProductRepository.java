package kr.co._29cm.homework.repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import kr.co._29cm.homework.model.Menu;
import kr.co._29cm.homework.model.Product;

public class ProductRepository {
    private BufferedReader file;

    public Menu getCSVData() {
        try {
            String line;
            Map<Integer, Product> readProduct = new HashMap<>();
            file = new BufferedReader(new FileReader("_items.csv"));

            while ((line = file.readLine()) != null) { // readLine()은 파일에서 개행된 한 줄의 데이터를 읽어온다.
                String[] tokens = splitToComma(line);

                validateTokens(tokens, readProduct);
            }
            return new Menu(readProduct);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fileReaderClose(file);
        }
        return null;
    }

    private void validateTokens(String[] tokens, Map<Integer, Product> readProduct) {
        for (int idx = 0; idx < tokens.length; idx++) {
            tokens[idx] = tokens[idx].replaceAll("^\"|\"$", "");
            if (!validateTitle(tokens[idx])) {
                addProducts(tokens, readProduct);
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

    private void addProducts(String[] tokens, Map<Integer, Product> saveProduct) {
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