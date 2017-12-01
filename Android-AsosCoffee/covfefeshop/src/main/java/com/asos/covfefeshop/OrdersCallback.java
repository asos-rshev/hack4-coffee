package com.asos.covfefeshop;

import com.asos.covfefe_common.model.Order;

interface OrdersCallback {
    void orderClicked(Order order);
}
