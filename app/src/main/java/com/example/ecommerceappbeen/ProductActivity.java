package com.example.ecommerceappbeen;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.example.ecommerceappbeen.imageprocessing.SliderAdapterExample;
import com.example.ecommerceappbeen.imageprocessing.SliderItem;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity implements SliderAdapterExample.OnItemClicked {

    private TextView txtProductName,txtProductDes,txtProductPrice;
    private int position;
    private ArrayList<String> nameArraylist,desArrayList,priceArrayList,Urls;
    private String name,price,des;
    private Toolbar toolbar;
    private Button btnCallus,btnChat;
    private SliderView productsliderView;
    private SliderAdapterExample adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.botom_navigation_bar_background_color));

        txtProductDes = findViewById(R.id.txtProductDes);
        txtProductName = findViewById(R.id.txtProductName);
        txtProductPrice = findViewById(R.id.txtProductPrice);

        position = getIntent().getIntExtra("position",0);
        name = getIntent().getStringExtra("name");
        des = getIntent().getStringExtra("des");
        Urls = new ArrayList<>();
        price = getIntent().getStringExtra("price");


        txtProductName.setText(name);
        txtProductDes.setText(des);
        txtProductPrice.setText(price);

        btnCallus = findViewById(R.id.btnCallUs);
        btnChat = findViewById(R.id.btnChat);
        productsliderView = findViewById(R.id.productsliderView);
        
        adapter = new SliderAdapterExample(this,des);
        productsliderView.setSliderAdapter(adapter);
        productsliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        productsliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        productsliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        productsliderView.setIndicatorSelectedColor(Color.WHITE);
        productsliderView.setIndicatorUnselectedColor(Color.GRAY);
        productsliderView.setScrollTimeInSec(3);
        productsliderView.setAutoCycle(false);
        productsliderView.startAutoCycle();

        adapter.setOnClick(ProductActivity.this);


        getProductImage();

        productsliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                Log.i("GGG", "onIndicatorClicked: " + productsliderView.getCurrentPagePosition());
            }
        });


        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        btnCallus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:9817751476"));
                startActivity(intent);
            }
        });
    }

    private void getProductImage() {

        try {

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Photo");

            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {

                    for (ParseObject object : objects) {
                        if ((object.get("des") + "").equals(des)) {

                            Urls.add(object.getParseFile("picture1").getUrl());

                            if (object.get("picture2") != null) {
                                Urls.add(object.getParseFile("picture2").getUrl());
                            }
                            if (object.get("picture3") != null) {
                                Urls.add(object.getParseFile("picture3").getUrl());
                            }

                            for (int index = 0; Urls.size() > index;index++) {

                                SliderItem sliderItem = new SliderItem();
                                sliderItem.setImageUrl(Urls.get(index));
                                adapter.addItem(sliderItem);
                            }

                        }
                    }

                }

            });

        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, ShowImages.class);
        intent.putExtra("des", des);
        startActivity(intent);
    }
}