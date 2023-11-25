package kr.co._29cm.homework.model;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import lombok.Getter;

@Getter
public class Order {
    private Map<Integer, Integer> orders;
    private Map<String, Integer> orderResult;

    public Order(Map<Integer, Integer> orders, Map<String, Integer> orderResult) {
        this.orders = orders;
        this.orderResult = orderResult;
    }

    public int getSize() {
        return orders.size();
    }

    public Set<Integer> getNumbers() {
        return orders.keySet();
    }

    public int getVolume(int orderNumber) {
        return orders.get(orderNumber);
    }

    public Set<Entry<Integer, Integer>> getNumberAndVolumes() {
        return orders.entrySet();
    }

    public int getOrderResultToTotalAmount(String resultName) {
        return orderResult.get(resultName);
    }

    public int getOrderResultToDeliveryAmount(String resultName) {
        return orderResult.get(resultName);
    }

    public int getOrderResultToPayAmount(String resultName) {
        return orderResult.get(resultName);
    }

    public void addOrderNumberAndVolume(int orderNumber, int orderVolume) {
        orders.put(orderNumber, orderVolume);
    }

    public void addOrderResult(String resultName, int resultAmount) {
        orderResult.put(resultName, resultAmount);
    }
}
