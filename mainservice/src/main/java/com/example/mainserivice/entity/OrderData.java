package com.example.mainserivice.entity;

import java.util.List;


public class OrderData {

    public List<Item> items;

    Integer userid;
    public int getUserid() {
        return userid;
    }
    public double sum;
    public List<Item> getItems(){return items;}

}