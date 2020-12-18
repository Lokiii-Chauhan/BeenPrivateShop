package com.example.ecommerceappbeen.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.ecommerceappbeen.MainAdapter;
import com.example.ecommerceappbeen.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import static com.parse.Parse.getApplicationContext;

public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private MainAdapter mainAdapter;
    private ArrayList<String> nameArrayList, desArrayList, priceArrayList,urlArrayList;
    private String names, des;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Bitmap image;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        nameArrayList = new ArrayList<>();
        desArrayList = new ArrayList<>();
        priceArrayList = new ArrayList<>();
        urlArrayList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.rv_main);
        swipeRefreshLayout = view.findViewById(R.id.swipeToRefresh);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mainAdapter = new MainAdapter(getApplicationContext(), nameArrayList, desArrayList, priceArrayList,urlArrayList);

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
        return view;
    }

    private void getNamePriceAndDesc() {
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
    }
}