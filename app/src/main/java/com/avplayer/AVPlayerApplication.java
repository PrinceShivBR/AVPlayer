package com.avplayer;

import android.app.Application;
import android.content.Context;

/**
 * Created by shivappar.b on 14-03-2019
 */
public class AVPlayerApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        AVPlayerApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return context;
    }
}
