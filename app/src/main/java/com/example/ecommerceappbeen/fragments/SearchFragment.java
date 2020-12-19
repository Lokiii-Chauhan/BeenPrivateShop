package com.example.ecommerceappbeen.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.ecommerceappbeen.adapters.MainAdapter;
import com.example.ecommerceappbeen.activities.ProductActivity;
import com.example.ecommerceappbeen.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import static com.parse.Parse.getApplicationContext;

public class SearchFragment extends Fragment implements MainAdapter.OnItemClicked {
    private RecyclerView recyclerView;
    private MainAdapter mainAdapter;
    private ArrayList<String> nameArrayList, desArrayList, priceArrayList, urlArrayList;
    private String names, des;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        nameArrayList = new ArrayList<>();
        desArrayList = new ArrayList<>();
        priceArrayList = new ArrayList<>();
        urlArrayList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.rv_main);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mainAdapter = new MainAdapter(getApplicationContext(), nameArrayList, desArrayList, priceArrayList, urlArrayList);

        swipeRefreshLayout = view.findViewById(R.id.swipeToRefresh);


        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        getNamePriceAndDesc();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (swipeRefreshLayout.isRefreshing()) {

                    nameArrayList.clear();
                    desArrayList.clear();
                    priceArrayList.clear();

                    names = "";
                    des = "";

                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Photo");
                    query.orderByDescending("createdAt");

                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (objects.size() > 0 && e == null) {

                                for (ParseObject object : objects) {

                                    names = object.get("name") + "";
                                    des = object.get("des") + "";
                                    nameArrayList.add(names);
                                    desArrayList.add(des);
                                    priceArrayList.add(object.get("price") + "");
                                    urlArrayList.add(object.getParseFile("picture1").getUrl());
                                    mainAdapter.notifyDataSetChanged();
                                }

                            }
                        }
                    });
                }
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        mainAdapter.setOnClick(this::onItemClick);

        return view;
    }

    private void getNamePriceAndDesc() {
        try {
            names = "";
            des = "";
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Photo");
            query.orderByDescending("createdAt");

            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (objects.size() > 0 && e == null) {

                        for (ParseObject object : objects) {

                            names = object.get("name") + "";
                            des = object.get("des") + "";
                            nameArrayList.add(names);
                            desArrayList.add(des);
                            priceArrayList.add(object.get("price") + "");
                            urlArrayList.add(object.getParseFile("picture1").getUrl());
                            recyclerView.setAdapter(mainAdapter);
                            mainAdapter.notifyDataSetChanged();
                        }

                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
        intent.putExtra("name", nameArrayList.get(position));
        intent.putExtra("des", desArrayList.get(position));
        intent.putExtra("price", priceArrayList.get(position));
        intent.putExtra("position", position);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}