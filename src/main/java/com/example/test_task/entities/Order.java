package com.example.test_task.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client", nullable = false)
    private String client;

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "address", nullable = false)
    private String address;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private Set<OrderLine> orderLines = new HashSet<>();

    public void add(OrderLine item) {
        if (item != null) {
            if (orderLines == null) {
                orderLines = new HashSet<>();
            }

            orderLines.add(item);
            item.setOrder(this);
        }
    }

    public Set<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(Set<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Order() {
    }
}
