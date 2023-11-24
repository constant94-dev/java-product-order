package kr.co._29cm.homework.service;

import static kr.co._29cm.homework.exception.handlerException.invalidToOrderNumber;
import static kr.co._29cm.homework.exception.handlerException.soldOutToProduct;

import java.util.Set;
import kr.co._29cm.homework.model.Menu;

public class OrderService {

    public boolean validateOrderNumber(String number, Menu menu) {
        Set<Integer> menuNumbers = menu.getMenuMap().keySet();
        int orderNumber = Integer.parseInt(number);

        if (!hasMenuNumbers(menuNumbers, orderNumber)) {
            invalidToOrderNumber();
            return false;
        }
        return true;
    }

    private boolean hasMenuNumbers(Set<Integer> menuNumbers, int orderNumber) {
        for (int menuNumber : menuNumbers) {
            if (isOrderNumber(menuNumber, orderNumber)) {
                return true;
            }
        }
        return false;
    }

    private boolean isOrderNumber(int menuNumber, int orderNumber) {
        return (menuNumber == orderNumber);
    }

    public void validateOrderVolume(String volume, String number, Menu menu) {
        int orderVolume = Integer.parseInt(volume);
        int orderNumber = Integer.parseInt(number);

        if (!menu.volumeInStock(orderVolume, orderNumber)) {
            soldOutToProduct();
        }
    }
}
