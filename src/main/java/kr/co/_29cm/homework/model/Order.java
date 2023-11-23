package kr.co._29cm.homework.model;

import java.util.Map;
import lombok.Getter;

@Getter
public class Order {
    private Map<Integer, Integer> orderMap;

    public Order(Map<Integer, Integer> orderMap) {
        this.orderMap = orderMap;
    }
}
