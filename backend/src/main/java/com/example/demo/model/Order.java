package com.example.demo.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_orders")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @NotNull(message = "User cannot be null")
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @NotNull(message = "Order status cannot be null")
    private String orderStatus;

    @OneToMany(targetEntity = OrderDetail.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<OrderDetail> orderDetails;


    @NotNull(message = "Payment method cannot be null")
    private PaymentMethod paymentMethod = PaymentMethod.CASH; // Default payment method

    @NotNull(message = "Payment status cannot be null")
    private PaymentStatus paymentStatus;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();
}

enum PaymentMethod {
    CASH,
    KHALTI
}

enum PaymentStatus {
    PENDING,
    COMPLETED,
    FAILED
}