package com.asos.covfefe_common.model;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    public String id;

    public List<Item> items;

    public String name;

    public String inProgress;

    public String totalPrice;
}