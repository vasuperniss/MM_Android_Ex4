package com.amaze_ing.mm.amazeandroid;

import android.app.Application;

import java.net.CookieHandler;
import java.net.CookieManager;

/**
 * Created by user on 21/06/2016.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CookieHandler.setDefault(new CookieManager());
    }
}
