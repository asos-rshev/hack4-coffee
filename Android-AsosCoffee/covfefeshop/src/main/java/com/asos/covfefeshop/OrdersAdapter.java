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
    private final OrdersCallback callback;
    private LayoutInflater layoutInflater;

    OrdersAdapter(FirebaseRecyclerOptions<Order> options, LayoutInflater layoutInflater, OrdersCallback callback) {
        super(options);
        this.layoutInflater = layoutInflater;
        this.callback = callback;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderViewHolder(layoutInflater.inflate(R.layout.order, null), callback);
    }

    @Override
    protected void onBindViewHolder(OrderViewHolder holder, int position, Order model) {
        holder.setOrder(model);
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView orderName;
        private final OrdersCallback callback;
        private Order order;

        OrderViewHolder(View itemView, OrdersCallback callback) {
            super(itemView);
            orderName = itemView.findViewById(R.id.order_name);
            this.callback = callback;
            itemView.setOnClickListener(this);
        }

        void setOrder(Order order) {
            this.order = order;
            orderName.setText(order.name);
        }

        @Override
        public void onClick(View v) {
            callback.orderClicked(order);
        }
    }
}
