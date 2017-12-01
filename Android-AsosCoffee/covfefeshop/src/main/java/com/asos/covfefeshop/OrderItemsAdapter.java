package com.asos.covfefeshop;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asos.covfefe_common.model.OrderItem;

import java.util.List;

class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsAdapter.OrderItemVH> {
    private final List<OrderItem> items;
    private final LayoutInflater inflater;

    OrderItemsAdapter(List<OrderItem> items, LayoutInflater inflater) {
        this.items = items;
        this.inflater = inflater;
    }

    @Override
    public OrderItemVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderItemVH(inflater.inflate(R.layout.order_item, null));
    }

    @Override
    public void onBindViewHolder(OrderItemVH holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class OrderItemVH extends RecyclerView.ViewHolder {

        private final ImageView icon;
        private final TextView type;
        private final TextView count;
        private final TextView extras;

        OrderItemVH(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            type = itemView.findViewById(R.id.item_type);
            count = itemView.findViewById(R.id.count);
            extras = itemView.findViewById(R.id.item_extras);
        }

        void bind(OrderItem item) {
            type.setText(item.getName());
            extras.setText(getExtrasAsString(item.getExtras()));
            count.setText(String.valueOf(item.getCount()));
        }

        private String getExtrasAsString(List<String> extras) {
            if (extras == null || extras.isEmpty()) {
                return "no extras";
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < extras.size(); i++) {
                String extra = extras.get(i);
                sb.append(extra);
                if (i + 1 < extras.size()) {
                    sb.append(", ");
                }
            }
            return sb.toString();
        }
    }
}
