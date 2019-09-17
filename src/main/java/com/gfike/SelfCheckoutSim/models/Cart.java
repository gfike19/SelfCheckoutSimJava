package com.gfike.SelfCheckoutSim.models;

import java.util.ArrayList;

public class Cart {


    private ArrayList<Item> items;

//    public Cart(ArrayList<Item> items){
//        this.items = items;
//    }

    public Cart(){
        this.items = new ArrayList<Item>();
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public void addItem(Item i) {
        this.items.add(i);
    }
}
