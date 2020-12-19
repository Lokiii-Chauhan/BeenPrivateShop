package com.example.ecommerceappbeen.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.ecommerceappbeen.R;
import com.example.ecommerceappbeen.adapters.MainAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import static com.parse.Parse.getApplicationContext;

public class LikedItemsActivity extends AppCompatActivity implements MainAdapter.OnItemClicked {

    private ArrayList<String> nameArrayList, emailArrayList, desArrayList, priceArrayList, urlsArraylist;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_items);

        Window window = LikedItemsActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(LikedItemsActivity.this, R.color.botom_navigation_bar_background_color));


        nameArrayList = new ArrayList<>();
        desArrayList = new ArrayList<>();
        emailArrayList = new ArrayList<>();
        priceArrayList = new ArrayList<>();
        urlsArraylist = new ArrayList<>();

        recyclerView = findViewById(R.id.liked_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        MainAdapter mainAdapter = new MainAdapter(this, nameArrayList, desArrayList, priceArrayList, urlsArraylist);

        mainAdapter.setOnClick(this::onItemClick);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("LikedBy");
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size() > 0 && e == null) {
                    for (ParseObject object : objects) {
                        if (object.get("likedBy").equals(ParseUser.getCurrentUser().getEmail())) ;

                        nameArrayList.add(object.get("name") + "");
                        emailArrayList.add(object.get("likedBy") + "");
                        desArrayList.add(object.get("desc") + "");
                        priceArrayList.add(object.get("price") + "");
                        urlsArraylist.add(object.get("photoURL") + "");
                        recyclerView.setAdapter(mainAdapter);
                        mainAdapter.notifyDataSetChanged();

                    }
                }
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, ProductActivity.class);
        intent.putExtra("name", nameArrayList.get(position));
        intent.putExtra("des", desArrayList.get(position));
        intent.putExtra("price", priceArrayList.get(position));
        intent.putExtra("position", position);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}