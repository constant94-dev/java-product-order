package kr.co._29cm.homework.model;

import static kr.co._29cm.homework.exception.handlerException.soldOutToProduct;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import lombok.Getter;

@Getter
public class Order {
    private Map<Integer, Integer> orders;

    public Order(Map<Integer, Integer> orders) {
        this.orders = orders;
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

    public void addOrderNumberAndVolume(int orderNumber, int orderVolume) {
        orders.put(orderNumber, orderVolume);
    }

    public void validateOrderNumberAndVolume(Menu menu) {
        // 여기서 사용자가 주문한 상품과 수량을 검증한다.
        // 검증의 목표는
        // 1. 상품에 존재 여부
        // 2. 상품에 재고 수 존재 여부
        // 위 목표를 달성하면 주문 내역을 출력하고,
        // 달성하지 못하면 에외 문구를 보여주고 처음으로 돌아간다.
        if (menu.menuNumberToExist(orders)) {
            orderVolumeOfStock(menu);
        }
    }

    private void orderVolumeOfStock(Menu menu) {
        Set<Entry<Integer, Integer>> products = orders.entrySet();

        try {
            for (Entry<Integer, Integer> product : products) {
                if (!menuVolumeToExist(menu, product)) {
                    soldOutToProduct();
                }
            }
            // 상품번호 검증과 상품수량 검증이 끝나도 문제가 없었다면,
            // 이제는 메뉴판의 주문내역을 반영해주고 출력하면된다.
            menu.changeMenuOfOrderApply(products);
        } catch (IllegalStateException e) {
            e.getMessage();
        }

    }

    private boolean menuVolumeToExist(Menu menu, Entry<Integer, Integer> product) {
        return menu.volumeInStock(product.getKey(), product.getValue());
    }
}
