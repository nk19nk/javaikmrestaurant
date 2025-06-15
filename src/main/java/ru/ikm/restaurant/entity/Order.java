package ru.ikm.restaurant.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "dish_id", nullable = false)
    private Menu dish; // Связь с блюдом

    private int quantity;

    // Геттеры и сеттеры
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public Menu getDish() { return dish; } // Новый геттер
    public void setDish(Menu dish) { this.dish = dish; } // Новый сеттер
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}