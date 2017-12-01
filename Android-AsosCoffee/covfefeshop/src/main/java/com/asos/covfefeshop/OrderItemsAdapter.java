package com.asos.covfefeshop;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asos.covfefe_common.model.Item;

import java.util.List;

class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsAdapter.OrderItemVH> {
    OrderItemsAdapter(List<Item> items, LayoutInflater inflater) {
    }

    @Override
    public OrderItemVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(OrderItemVH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class OrderItemVH extends RecyclerView.ViewHolder {

        public OrderItemVH(View itemView) {
            super(itemView);

        }
    }
}
