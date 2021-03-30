package de.conrad.codeworkshop.factory.services.factory;

import de.conrad.codeworkshop.factory.services.order.api.Order;
import de.conrad.codeworkshop.factory.services.order.api.OrderStatus;
import java.util.*;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Andreas Hartmann
 */
@org.springframework.stereotype.Service("factoryService")
class Service {

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final ConcurrentLinkedQueue<Order> manufacturingQueue = new ConcurrentLinkedQueue<>();

    void enqueue(final Order order) {
        order.setStatus(OrderStatus.IN_PROGRESS);
        manufacturingQueue.add(order);
    }

    Order dequeue() {
        return manufacturingQueue.poll();
    }

    List<Order> dequeueAll() {
        List<Order> orders = new ArrayList<>();
        synchronized (manufacturingQueue) {
            while (!manufacturingQueue.isEmpty()) {
                orders.add(manufacturingQueue.poll());
            }
        }
        return orders;
    }
}
