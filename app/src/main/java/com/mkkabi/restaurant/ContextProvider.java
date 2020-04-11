package com.mkkabi.restaurant;

import android.app.Application;
import android.content.Context;

public class ContextProvider extends Application {

    /**
     * there is no guarantee that the non-static onCreate() will have been called before
     * some static initialization code tries to fetch Context object.
     * That means that calling code will need to be ready to deal with null values
     * Should not be used to create AlertDialog,
     * mainly used to pass context to initialize UtilitiesType String values from strings.xml
     */


    private static Context context;

    public void onCreate() {
        super.onCreate();
        ContextProvider.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return ContextProvider.context;
    }
}
