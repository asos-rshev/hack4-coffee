package com.asos.covfefe_common.model;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    public long id;

    public List<Item> items;

    public String name;

    public int inProgress;

    public double totalPrice;
}