package kr.co._29cm.homework.model;

import java.util.Map;
import lombok.Getter;

@Getter
public class ProductMap {
    private Map<Integer, Product> productMap;

    public ProductMap(Map<Integer, Product> productMap) {
        this.productMap = productMap;
    }
}
