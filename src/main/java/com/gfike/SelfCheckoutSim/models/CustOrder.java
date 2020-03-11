package com.gfike.SelfCheckoutSim.models;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name="CustOrder")
public class CustOrder extends AbstractEntity {

    private float subTotal;

    private float tax;

    private float orderTotal;

    private List<Integer> itemIds;

    public CustOrder () {}

//    public  CustOrder (Item i) {
//        update(i);
//    }

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
    @ElementCollection
    @Column(name="ItemIds")
    public List<Integer> getItemIds() {
        return itemIds;
    }

    public void setItemIds(List<Integer> itemIds) {
        this.itemIds = itemIds;
    }

    public void update(Item i){

        this.subTotal += i.getPrice();

        float itemTax = 0;

        if(i.isFs()) {
            itemTax = (float) (.04 * i.getPrice());
            this.tax += (.04 * i.getPrice());
        }
        else {
            itemTax = (float) (.04 * i.getPrice());
            this.tax += (.09 * i.getPrice());
        }

        this.orderTotal += i.getPrice() + itemTax;

        this.itemIds.add(i.getUid());
    }
}
