package kr.co._29cm.homework.model;

import java.util.Map;
import lombok.Getter;

@Getter
public class Menu {
    private Map<Integer, Product> menuMap;

    public Menu(Map<Integer, Product> menuMap) {
        this.menuMap = menuMap;
    }

    public boolean volumeInStock(int volume, int number) {
        int stock = menuMap.get(number).getStock();

        return (stock >= volume);
    }
}
