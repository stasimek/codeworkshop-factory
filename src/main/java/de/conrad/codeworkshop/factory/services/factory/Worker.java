package de.conrad.codeworkshop.factory.services.factory;

import de.conrad.codeworkshop.factory.services.order.api.Order;
import de.conrad.codeworkshop.factory.services.order.api.OrderStatus;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class Worker implements DisposableBean, Runnable {

    private final Thread thread;
    private volatile boolean amIWorking = true;

    Service service;
    de.conrad.codeworkshop.factory.services.notification.Service notificationService;

    @Autowired
    Worker(
            Service service,
            de.conrad.codeworkshop.factory.services.notification.Service notificationService
    ) {
        this.service = service;
        this.notificationService = notificationService;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (amIWorking) {
            //dequeueOrdersOneByOne();
            dequeueAllOrdersAtOnce();
        }
    }

    //private void dequeueOrdersOneByOne() {
    //    Order order = service.dequeue();
    //    if (order != null) {
    //        order.setStatus(OrderStatus.COMPLETED);
    //        sleepForSeconds(5);
    //        notificationService.notifyCustomer(order);
    //    } else {
    //        sleepForSeconds(1);
    //    }
    //}

    private void dequeueAllOrdersAtOnce() {
        List<Order> orders = service.dequeueAll();
        if (!orders.isEmpty()) {
            for (Order order : orders) {
                order.setStatus(OrderStatus.COMPLETED);
            }
            sleepForSeconds(5);
            for (Order order : orders) {
                notificationService.notifyCustomer(order);
            }
        } else {
            sleepForSeconds(1);
        }
    }

    private void sleepForSeconds(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void destroy() {
        amIWorking = false;
    }

}
