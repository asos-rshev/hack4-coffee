package com.asos.covfefe_common.model;

import java.io.Serializable;
import java.util.List;

public class Item implements Serializable {
    public long count;

    public String name;

    public double unitPrice;

    public int type;

    public boolean milky;

    public boolean ready;

    public List<String> extras;

    public String size;
}
