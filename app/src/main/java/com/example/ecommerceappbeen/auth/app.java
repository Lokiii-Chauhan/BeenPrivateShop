package com.example.ecommerceappbeen.auth;

import android.app.Application;

import com.parse.Parse;

public class app extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("oWAUPjhHc1NNzdgaGfPC4bUteEEz1m3b0Tvurs2B")
                .clientKey("1E49gZuRxorqml3Mq1gPNe92vT7Wjs3t7gxShHAZ")
                .server("https://parseapi.back4app.com")
                .build()
        );

    }
}
