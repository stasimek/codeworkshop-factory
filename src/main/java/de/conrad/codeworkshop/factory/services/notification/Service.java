package de.conrad.codeworkshop.factory.services.notification;

import de.conrad.codeworkshop.factory.services.order.api.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andreas Hartmann
 */
@org.springframework.stereotype.Service("notificationService")
public class Service {

    Logger logger = LoggerFactory.getLogger(Service.class);

    public void notifyCustomer(final Order order) {
        // Dummy function that would notify the customer that manufacturing is completed.
        logger.info("Notification to order: " + order);
        try {
            Thread.sleep(500);
        } catch (final InterruptedException interruptedException) {
            System.err.println(interruptedException.getMessage());
            // Do nothing
        }
    }
}
