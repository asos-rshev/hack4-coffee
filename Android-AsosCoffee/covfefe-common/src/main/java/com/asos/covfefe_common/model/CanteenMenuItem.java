package com.asos.covfefe_common.model;

import java.io.Serializable;
import java.util.List;

public class CanteenMenuItem implements Serializable {
    public String name;
    public int type;
    public List<CanteenMenuItemSize> sizes;
    public List<CanteenMenuItemExtra> extras;
}
