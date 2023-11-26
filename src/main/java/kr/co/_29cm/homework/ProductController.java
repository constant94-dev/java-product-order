package kr.co._29cm.homework;

import static kr.co._29cm.homework.constant.exception.messageException.EXCEPTION_TEMPLATE;
import static kr.co._29cm.homework.constant.view.InputViewConstant.ORDER_OR_QUIT_BREAK;
import static kr.co._29cm.homework.constant.view.InputViewConstant.ORDER_OR_QUIT_CONTINUE;

import java.util.HashMap;
import kr.co._29cm.homework.model.Menu;
import kr.co._29cm.homework.model.Order;
import kr.co._29cm.homework.repository.ProductRepository;
import kr.co._29cm.homework.service.OrderService;
import kr.co._29cm.homework.view.Console;
import kr.co._29cm.homework.view.InputView;
import kr.co._29cm.homework.view.OutputView;

public class ProductController {
    private OrderService orderService;
    private Menu menu;
    private Order order;
    private InputView inputView;
    private OutputView outputView;

    public void run() {
        ready();
        orderPlay();
    }

    private void ready() {
        ProductRepository productRepository = new ProductRepository();
        menu = new Menu(productRepository.getCSVData().getMenus());
        order = new Order(new HashMap<>(), new HashMap<>());
        orderService = new OrderService();
        inputView = new InputView();
        outputView = new OutputView();
    }

    private void orderPlay() {
        String flag = ORDER_OR_QUIT_CONTINUE.getMessage();

        while (!flag.equals(ORDER_OR_QUIT_BREAK.getMessage())) {
            try {
                flag = inputView.orderORQuit(outputView, order, menu);

                orderToProductOfPrint(flag);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        outputView.printToOrderQuit();
        Console.close();
    }

    private void orderToProductOfPrint(String flag) {
        if (flag.equals(ORDER_OR_QUIT_CONTINUE.getMessage())) {
            outputView.printToTotalProduct(menu);
            String orderCheck = inputView.orderOfNumberAndVolume(order, menu);

            orderToResultOfException(orderCheck, flag);
        }
    }

    private void orderToResultOfException(String orderCheck, String flag) {
        if (!orderCheck.contains(EXCEPTION_TEMPLATE.getMessage())) {
            orderService.orderToTotalAmount(order, menu);
            orderService.orderToDeliveryAmount(order, menu);
            orderService.orderToPayAmount(order);

            orderToQuitOfPrint(flag);
            orderToInit();
        }
    }

    private void orderToQuitOfPrint(String flag) {
        if (!flag.equals(ORDER_OR_QUIT_BREAK.getMessage())) {
            outputView.printToOrderResult(order, menu);
        }
    }

    private void orderToInit() {
        order = new Order(new HashMap<>(), new HashMap<>());
    }
}
