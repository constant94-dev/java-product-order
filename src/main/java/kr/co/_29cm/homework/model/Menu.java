package kr.co._29cm.homework.model;

import java.util.Map;
import lombok.Getter;

@Getter
public class ProductMenu {
    private Map<Integer, Product> menuMap;

    public ProductMenu(Map<Integer, Product> menuMap) {
        this.menuMap = menuMap;
    }
}
