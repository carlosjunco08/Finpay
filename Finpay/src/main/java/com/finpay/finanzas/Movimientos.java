package com.finpay.finanzas;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class Movimientos implements Serializable {
    private static final AtomicLong COUNTER = new AtomicLong();

    private Long id;
    private String label;
    private double amount;
    private String type;       // INGRESO o GASTO
    private String category;
    private Date date;

    public Movimientos() {
        this.id = COUNTER.incrementAndGet();
        this.date = new Date();
    }

    public Movimientos(String label, double amount, String type, String category) {
        this();
        this.label = label;
        this.amount = amount;
        this.type = type;
        this.category = category;
    }

    // Getters y Setters
    public Long getId() { return id; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
}
