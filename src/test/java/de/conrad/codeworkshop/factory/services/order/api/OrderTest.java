package de.conrad.codeworkshop.factory.services.order.api;

import java.util.*;
import org.junit.Test;
import static org.junit.Assert.*;
import static de.conrad.codeworkshop.factory.services.order.api.OrderStatus.*;
import java.math.BigDecimal;

public class OrderTest {

    @Test
    public void givenEmptyOrder_whenValidate_thenStatusDeclined() {
        Order order = new Order();
        order.setStatus(PENDING);
        order.validate();
        assertEquals(DECLINED, order.getStatus());
    }

    @Test
    public void givenNullProductId_whenValidate_thenStatusDeclined() {
        Order order = new Order();
        order.setStatus(PENDING);
        List<Position> positions = new ArrayList<>();
        positions.add(new Position(null, new BigDecimal(10)));
        order.setPositions(positions);
        order.validate();
        assertEquals(DECLINED, order.getStatus());
    }

    @Test
    public void givenProductId12345_whenValidate_thenStatusDeclined() {
        Order order = new Order();
        order.setStatus(PENDING);
        List<Position> positions = new ArrayList<>();
        positions.add(new Position(12345, new BigDecimal(10)));
        order.setPositions(positions);
        order.validate();
        assertEquals(DECLINED, order.getStatus());
    }

    @Test
    public void givenProductIdMinus12345_whenValidate_thenStatusDeclined() {
        Order order = new Order();
        order.setStatus(PENDING);
        List<Position> positions = new ArrayList<>();
        positions.add(new Position(-12345, new BigDecimal(10)));
        order.setPositions(positions);
        order.validate();
        assertEquals(DECLINED, order.getStatus());
    }

    @Test
    public void givenProductId123456_whenValidate_thenStatusAccepted() {
        Order order = new Order();
        order.setStatus(PENDING);
        List<Position> positions = new ArrayList<>();
        positions.add(new Position(123456, new BigDecimal(10)));
        order.setPositions(positions);
        order.validate();
        assertEquals(ACCEPTED, order.getStatus());
    }

    @Test
    public void givenProductId123456789_whenValidate_thenStatusAccepted() {
        Order order = new Order();
        order.setStatus(PENDING);
        List<Position> positions = new ArrayList<>();
        positions.add(new Position(123456789, new BigDecimal(10)));
        order.setPositions(positions);
        order.validate();
        assertEquals(ACCEPTED, order.getStatus());
    }

    @Test
    public void givenProductId1234567890_whenValidate_thenStatusDeclined() {
        Order order = new Order();
        order.setStatus(PENDING);
        List<Position> positions = new ArrayList<>();
        positions.add(new Position(1234567890, new BigDecimal(10)));
        order.setPositions(positions);
        order.validate();
        assertEquals(DECLINED, order.getStatus());
    }

    @Test
    // 0 is divisible by 10
    public void givenQuantity0_whenValidate_thenStatusAccepted() {
        Order order = new Order();
        order.setStatus(PENDING);
        List<Position> positions = new ArrayList<>();
        positions.add(new Position(123456, new BigDecimal(0)));
        order.setPositions(positions);
        order.validate();
        assertEquals(ACCEPTED, order.getStatus());
    }

    @Test
    public void givenQuantity0point5_whenValidate_thenStatusAccepted() {
        Order order = new Order();
        order.setStatus(PENDING);
        List<Position> positions = new ArrayList<>();
        positions.add(new Position(123456, new BigDecimal(0.5)));
        order.setPositions(positions);
        order.validate();
        assertEquals(ACCEPTED, order.getStatus());
    }

    @Test
    public void givenQuantity1_whenValidate_thenStatusDeclined() {
        Order order = new Order();
        order.setStatus(PENDING);
        List<Position> positions = new ArrayList<>();
        positions.add(new Position(123456, new BigDecimal(1)));
        order.setPositions(positions);
        order.validate();
        assertEquals(DECLINED, order.getStatus());
    }

    @Test
    public void givenQuantity42point42_whenValidate_thenStatusAccepted() {
        Order order = new Order();
        order.setStatus(PENDING);
        List<Position> positions = new ArrayList<>();
        positions.add(new Position(123456, new BigDecimal(42.42)));
        order.setPositions(positions);
        order.validate();
        assertEquals(ACCEPTED, order.getStatus());
    }

    @Test
    public void givenQuantity42point43_whenValidate_thenStatusDeclined() {
        Order order = new Order();
        order.setStatus(PENDING);
        List<Position> positions = new ArrayList<>();
        positions.add(new Position(123456, new BigDecimal(42.43)));
        order.setPositions(positions);
        order.validate();
        assertEquals(DECLINED, order.getStatus());
    }

    @Test
    public void givenQuantity10_whenValidate_thenStatusAccepted() {
        Order order = new Order();
        order.setStatus(PENDING);
        List<Position> positions = new ArrayList<>();
        positions.add(new Position(123456, new BigDecimal(10)));
        order.setPositions(positions);
        order.validate();
        assertEquals(ACCEPTED, order.getStatus());
    }

    @Test
    public void givenQuantity100_whenValidate_thenStatusAccepted() {
        Order order = new Order();
        order.setStatus(PENDING);
        List<Position> positions = new ArrayList<>();
        positions.add(new Position(123456, new BigDecimal(100)));
        order.setPositions(positions);
        order.validate();
        assertEquals(ACCEPTED, order.getStatus());
    }
}
