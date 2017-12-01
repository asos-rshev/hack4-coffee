package com.asos.covfefeshop;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
    TabLayout tabLayout;

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
        // actionBar.setHomeButtonEnabled(false);

        // Set up the ViewPager, attaching the adapter and setting up a listener for when the
        // user swipes between sections.
        mViewPager = findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);

        tabLayout = findViewById(R.id.titles);
        tabLayout.setupWithViewPager(mViewPager);

    }

    private void initLists() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference query = database.getReference().child("orders");
        options = new FirebaseRecyclerOptions.Builder<Order>()
                .setQuery(query, Order.class)
                .build();

    }

    private void showOrder(Order order) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.container, OrderDetails.newInstance(order)).commit();
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
            switch (position) {
                case 0:
                    return "New";
                case 1:
                    return "Processing";
                case 2:
                    return "Ready";
                default:
                    return "New";
            }
        }
    }

    public static class OrdersList extends Fragment implements OrdersCallback {

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
            adapter = new OrdersAdapter(options, inflater, this);
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
        public void onResume() {
            super.onResume();

        }

        @Override
        public void onStop() {
            super.onStop();
            adapter.stopListening();
        }

        @Override
        public void orderClicked(Order order) {
            ((MainActivity) getActivity()).showOrder(order);
        }
    }

    public static class OrderDetails extends Fragment implements View.OnClickListener {

        TextView counter;
        CountDownTimer countDownTimer;
        private int minutes = 2;
        private int seconds = 0;
        private boolean isStarted = false;
        private TextView button;

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
                TextView name = rootView.findViewById(R.id.customer_name);
                name.setText(currentOrder.name);
                counter = rootView.findViewById(R.id.counter);
                minutes = currentOrder.items.size();
                updateCounter();
                button = rootView.findViewById(R.id.textView6);
                button.setOnClickListener(this);
                TextView plus = rootView.findViewById(R.id.textView4);
                plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "onClick() called with: v = [" + v + "]");
                        minutes++;
                        reinit();
                    }
                });
                TextView minus = rootView.findViewById(R.id.textView3);
                minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "onClick() called with: v = [" + v + "]");
                        minutes--;
                        if (minutes >= 0) {
                            reinit();
                        } else {
                            minutes = 0;
                        }
                    }
                });
            }
            return rootView;
        }

        @Override
        public void onStop() {
            super.onStop();
            if (countDownTimer != null) {
                countDownTimer.cancel();
                countDownTimer = null;
            }
        }

        void reinit() {
            updateCounter();
            if (isStarted) {
                countDownTimer.cancel();
                init();
                countDownTimer.start();
            }
        }

        private void updateCounter() {
            counter.setText(minutes + ":" + seconds);
        }


        @Override
        public void onClick(View v) {
            v.setOnClickListener(null);
            isStarted = true;
            button.setText("READY");

            init();
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    countDownTimer.cancel();
                    minutes = 0;
                    seconds = 0;
                    updateCounter();
                    Toast.makeText(getActivity(), "Order is ready!", Toast.LENGTH_LONG).show();
                }
            });
            countDownTimer.start();
        }

        void init() {
            countDownTimer = new CountDownTimer((minutes * 60 + seconds) * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    seconds--;
                    if (seconds < 0) {
                        seconds = 59;
                        minutes--;
                        if (minutes < 0) {
                            minutes = 0;
                        }
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateCounter();
                        }
                    });
                }

                @Override
                public void onFinish() {

                }
            };
        }
    }
}
