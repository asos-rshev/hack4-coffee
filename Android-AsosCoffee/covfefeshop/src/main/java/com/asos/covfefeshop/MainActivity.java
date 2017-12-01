package com.asos.covfefeshop;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asos.covfefe_common.model.Order;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends FragmentActivity {
    private static final String TAG = "MainActivity";
    static FirebaseRecyclerOptions<Order> options;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
     * three primary sections of the app. We use a {@link android.support.v4.app.FragmentPagerAdapter}
     * derivative, which will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    AppSectionsPagerAdapter mAppSectionsPagerAdapter;
    /**
     * The {@link ViewPager} that will display the three primary sections of the app, one at a
     * time.
     */
    ViewPager mViewPager;

    public static Intent createIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());
        initLists();
        // Set up the action bar.
        final ActionBar actionBar = getActionBar();

        // Specify that the Home/Up button should not be enabled, since there is no hierarchical
        // parent.
        actionBar.setHomeButtonEnabled(false);

        // Set up the ViewPager, attaching the adapter and setting up a listener for when the
        // user swipes between sections.
        mViewPager = findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
    }

    private void initLists() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference query = database.getReference().child("orders");
        options = new FirebaseRecyclerOptions.Builder<Order>()
                .setQuery(query, Order.class)
                /*.setQuery(query, new SnapshotParser<Order>() {
                    @Override
                    public Order parseSnapshot(DataSnapshot snapshot) {
                        Log.d(TAG, "parseSnapshot() called with: snapshot = [" + snapshot + "]");
                        return null;
                    }
                })*/
                .build();

    }

    public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

        AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                default:
                    return OrdersList.newInstance(i);
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Section " + (position + 1);
        }
    }

    public static class OrdersList extends Fragment {

        public static final String ARG_SECTION_NUMBER = "section_number";
        private OrdersAdapter adapter;

        public static OrdersList newInstance(int i) {
            OrdersList fragment = new OrdersList();
            Bundle args = new Bundle();
            args.putInt(OrdersList.ARG_SECTION_NUMBER, i + 1);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_orders, container, false);
            Bundle args = getArguments();
            RecyclerView orders = rootView.findViewById(R.id.orders);
            adapter = new OrdersAdapter(options, inflater);
            orders.setAdapter(adapter);
            orders.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            return rootView;
        }

        @Override
        public void onStart() {
            super.onStart();
            adapter.startListening();
        }

        @Override
        public void onStop() {
            super.onStop();
            adapter.stopListening();
        }
    }

    public static class OrderDetails extends Fragment {

        public static OrderDetails newInstance(Order order) {
            OrderDetails fragment = new OrderDetails();
            Bundle args = new Bundle();
            args.putSerializable("ORDER", order);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_order_details, container, false);
            Bundle args = getArguments();
            Order currentOrder = (Order) args.getSerializable("ORDER");
            if (currentOrder != null) {
                RecyclerView listOfItems = rootView.findViewById(R.id.order_items);
                listOfItems.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                listOfItems.setAdapter(new OrderItemsAdapter(currentOrder.items, inflater));
            }
            return rootView;
        }

        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onStop() {
            super.onStop();
        }
    }
}
