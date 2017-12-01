package com.asos.covfefeshop;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asos.covfefe_common.model.Order;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

class OrdersAdapter extends FirebaseRecyclerAdapter<Order, OrdersAdapter.OrderViewHolder> {
    private static final String TAG = "OrdersAdapter";
    private LayoutInflater layoutInflater;

    OrdersAdapter(FirebaseRecyclerOptions<Order> options, LayoutInflater layoutInflater) {
        super(options);
        this.layoutInflater = layoutInflater;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderViewHolder(layoutInflater.inflate(R.layout.order, null));
    }

    @Override
    protected void onBindViewHolder(OrderViewHolder holder, int position, Order model) {
        holder.setOrder(model);
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder{

        private final TextView orderName;

        OrderViewHolder(View itemView) {
            super(itemView);
            orderName = itemView.findViewById(R.id.order_name);
        }

        void setOrder(Order order) {
            orderName.setText(order.name);
        }
    }
}
