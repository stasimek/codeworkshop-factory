package de.conrad.codeworkshop.factory.services.order.api;

import java.util.List;

import static de.conrad.codeworkshop.factory.services.order.api.OrderStatus.*;
import java.math.BigDecimal;

/**
 * @author Andreas Hartmann
 */
public class Order {

    private List<Position> positions;
    private OrderConfirmation orderConfirmation;
    private OrderStatus status = PENDING;

    public void validate() {
        if (positions != null
                && !positions.isEmpty()
                && status == PENDING
                && arePositionsValid()) {
            status = ACCEPTED;
        } else {
            status = DECLINED;
        }
    }

    private boolean arePositionsValid() {
        boolean valid = true;

        for (Position position : positions) {
            // Orders are only valid if the productId has between 6 and 9 digits (including)
            String productId = position.getProductId() + "";
            if (!productId.matches("[0-9]{6,9}")) {
                valid = false;
            }

            // Orders are only valid if quantity is either
            //   - divisible by 10
            //   - or less than one and larger than 0
            //   - or exactly 42.42
            BigDecimal q = position.getQuantity();
            if (q == null) {
                valid = false;
            } else if (q.remainder(new BigDecimal(10)).compareTo(BigDecimal.ZERO) == 0
                    || (q.compareTo(BigDecimal.ONE) < 0 && q.compareTo(BigDecimal.ZERO) > 0)
                    || q.equals(new BigDecimal(42.42))) {
                // this one is valid
            } else {
                valid = false;
            }
        }

        return valid;
    }

    public void setOrderConfirmation(final OrderConfirmation orderConfirmation) {
        this.orderConfirmation = orderConfirmation;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
