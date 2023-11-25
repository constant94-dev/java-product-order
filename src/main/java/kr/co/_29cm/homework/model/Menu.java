package kr.co._29cm.homework.model;

import static kr.co._29cm.homework.exception.handlerException.notExistProduct;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import lombok.Getter;

@Getter
public class Menu {
    private Map<Integer, Product> menus;

    public Menu(Map<Integer, Product> menus) {
        this.menus = menus;
    }

    public String getName(int orderNumber) {
        return menus.get(orderNumber).getName();
    }

    public int getPrice(int orderNumber) {
        return menus.get(orderNumber).getPrice();
    }

    public int getStock(int orderNumber) {
        return menus.get(orderNumber).getStock();
    }


    public Set<Entry<Integer, Product>> getNumberAndProduct() {
        return menus.entrySet();
    }

    public boolean volumeInStock(int number, int volume) {
        int stock = menus.get(number).getStock();

        return (stock >= volume);
    }

    public boolean menuNumberToExist(Order orders) {
        boolean numberExist = orders
                .getOrders()
                .keySet()
                .stream()
                .allMatch(menus::containsKey);

        if (!numberExist) {
            notExistProduct();
            return false;
        }
        return true;
    }

    public void addMenuToProduct(int orderNumber, Product product) {
        menus.put(orderNumber, product);
    }

    public void changeMenuOfOrderApply(Set<Entry<Integer, Integer>> products) {
        for (Entry<Integer, Integer> product : products) {
            String name = menus.get(product.getKey()).getName();
            int price = menus.get(product.getKey()).getPrice();
            int stock = menus.get(product.getKey()).getStock();

            int applyStock = stock - product.getValue();

            menus.put(product.getKey(), new Product(name, price, applyStock));
        }
    }
}
