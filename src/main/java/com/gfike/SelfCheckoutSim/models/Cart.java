package com.gfike.SelfCheckoutSim.models;

import java.util.ArrayList;

public class Cart {
    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    private ArrayList<Item> items;

    public Cart(ArrayList<Item> items){
        this.items = items;
    }

    public Cart(){}
}
