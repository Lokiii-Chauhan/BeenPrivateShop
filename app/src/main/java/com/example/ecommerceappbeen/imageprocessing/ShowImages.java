package com.example.ecommerceappbeen.imageprocessing;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerceappbeen.R;
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

public class ShowImages extends AppCompatActivity implements SliderAdapterExample.OnItemClicked {

    ArrayList<String> Urls = new ArrayList<>();

    SliderView sliderView;
    private SliderAdapterExample adapter;
    private String des;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_images);

        Urls = new ArrayList<>();

        sliderView = findViewById(R.id.imageSlider);


        adapter = new SliderAdapterExample(this,des);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(com.smarteist.autoimageslider.SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(false);
        sliderView.startAutoCycle();

        adapter.setOnClick(this::onItemClick);


        getProductImage();

        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                Log.i("GGG", "onIndicatorClicked: " + sliderView.getCurrentPagePosition());
            }
        });


    }


    private void getProductImage() {
        String des = getIntent().getStringExtra("des");

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

                            for (int index = 0; Urls.size()> index;index++){

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
//    Listner for ImageViews
    }
}