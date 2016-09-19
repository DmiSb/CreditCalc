package com.dmisb.creditcalc.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Extension for Application
 */
public class CalcCreditApp extends Application {

    private static Context sContext;
    private static SharedPreferences sSharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();

        sContext = this;
        sSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    /**
     * Returns Context of Application
     *
     * @return - Context
     */
    public static Context getContext() {
        return sContext;
    }

    /**
     * Returns Shared Preferences of Application
     *
     * @return - Shared Preferences
     */
    public static SharedPreferences getSharedPreferences() {
        return sSharedPreferences;
    }
}
