package kr.co._29cm.homework.view;

import static kr.co._29cm.homework.constant.view.InputViewConstant.ORDER_OR_QUIT;
import static kr.co._29cm.homework.constant.view.InputViewConstant.ORDER_OR_QUIT_BREAK;
import static kr.co._29cm.homework.constant.view.InputViewConstant.ORDER_OR_QUIT_CONTINUE;
import static kr.co._29cm.homework.constant.view.InputViewConstant.PRODUCT_NUMBER;
import static kr.co._29cm.homework.constant.view.InputViewConstant.PRODUCT_NUMBER_BREAK;
import static kr.co._29cm.homework.constant.view.InputViewConstant.PRODUCT_NUMBER_CONTINUE;
import static kr.co._29cm.homework.constant.view.InputViewConstant.PRODUCT_VOLUME;
import static kr.co._29cm.homework.constant.view.InputViewConstant.PRODUCT_VOLUME_BREAK;
import static kr.co._29cm.homework.constant.view.InputViewConstant.PRODUCT_VOLUME_CONTINUE;
import static kr.co._29cm.homework.validate.InputViewValidator.validateOrderNumberToEmpty;
import static kr.co._29cm.homework.validate.InputViewValidator.validateOrderORQuit;
import static kr.co._29cm.homework.validate.InputViewValidator.validateOrderVolumeToEmpty;
import static kr.co._29cm.homework.validate.InputViewValidator.validateOrderVolumeToInteger;

import kr.co._29cm.homework.model.Menu;
import kr.co._29cm.homework.model.Order;


public class InputView {

    public String orderORQuit(OutputView outputView, Order order, Menu menu) {
        System.out.printf(ORDER_OR_QUIT.getMessage());
        String choose = Console.readLine();

        validateOrderORQuit(choose);
        return checkToOrderORQuitFlag(choose, order, menu, outputView);
    }

    private String checkToOrderORQuitFlag(String choose, Order order, Menu menu, OutputView outputView) {
        if (choose.matches("q|quit")) {
            return ORDER_OR_QUIT_BREAK.getMessage();
        }
        return ORDER_OR_QUIT_CONTINUE.getMessage();
    }

    public String orderOfNumberAndVolume(Order order, Menu menu) {
        String productNumberFlag = PRODUCT_NUMBER_CONTINUE.getMessage();
        while (!productNumberFlag.equals(PRODUCT_NUMBER_BREAK.getMessage())) {
            try {
                System.out.printf(PRODUCT_NUMBER.getMessage());
                String orderNumber = Console.readLine();

                productNumberFlag = validateOrderNumberToEmpty(order, menu, orderNumber);
                checkToNumberFlag(order, orderNumber, productNumberFlag);
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println(e.getMessage());
                return e.getMessage();
            }
        }
        return productNumberFlag;
    }

    private void checkToNumberFlag(Order order, String orderNumber, String productNumberFlag) {
        if (!productNumberFlag.equals(PRODUCT_NUMBER_BREAK.getMessage())) {
            orderOfVolume(order, orderNumber);
        }
    }

    public void orderOfVolume(Order order, String orderNumber) {
        String productVolumeFlag = PRODUCT_VOLUME_CONTINUE.getMessage();

        while (!productVolumeFlag.equals(PRODUCT_VOLUME_BREAK.getMessage())) {
            try {
                System.out.printf(PRODUCT_VOLUME.getMessage());
                String orderVolume = Console.readLine();

                validateOrderVolumeToInteger(orderVolume);
                productVolumeFlag = checkToVolumeFlag(order, orderNumber, orderVolume);
            } catch (IllegalArgumentException e) {
                e.getMessage();
            }
        }
    }

    private String checkToVolumeFlag(Order order, String orderNumber, String orderVolume) {
        if (validateOrderVolumeToEmpty(order, orderNumber, orderVolume)) {
            return PRODUCT_VOLUME_BREAK.getMessage();
        }
        return PRODUCT_VOLUME_CONTINUE.getMessage();
    }
}