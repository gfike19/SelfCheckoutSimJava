package com.gfike.SelfCheckoutSim.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name="Orders")
public class Orders extends AbstractEntity {

    private float subTotal;

    private float tax;

    private float orderTotal;

    private List<Item> items;

    public Orders () {}

    public Orders (float subTotal, float tax, float orderTotal, List<Item> items){
        this.subTotal = subTotal;
        this.tax = tax;
        this.orderTotal = orderTotal;
        this.items = items;
    }

    @NotNull
    @Column(name="subTotal")
    public float getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(float subTotal) {
        this.subTotal = subTotal;
    }

    @NotNull
    @Column(name="Tax")
    public float getTax() {
        return tax;
    }

    public void setTax(float tax) {
        this.tax = tax;
    }

    @NotNull
    @Column(name="OrderTotal")
    public float getorderTotal() {
        return orderTotal;
    }

    public void setorderTotal(float orderTotal) {
        this.orderTotal = orderTotal;
    }

    @NotNull
    @Column(name="Items")
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
