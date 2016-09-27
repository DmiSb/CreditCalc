package com.dmisb.creditcalc.data.managers;

import android.content.Context;

import com.dmisb.creditcalc.utils.CalcCreditApp;
import com.dmisb.creditcalc.utils.ConstantManager;
import com.dmisb.creditcalc.utils.FontUtil;
import com.dmisb.creditcalc.utils.FormatUtil;

/**
 * Data manager of application
 */
public class DataManager {

    private static final String TAG = ConstantManager.TAG_PREFIX + "DataManager";
    private static DataManager INSTANCE = null;

    private final Context mContext;
    private final PreferencesManager mPreferencesManager;
    private final CalcManager mCalcManager;

    /**
     * Created DataManager
     */
    private DataManager() {
        this.mCalcManager = new CalcManager();
        this.mContext = CalcCreditApp.getContext();
        this.mPreferencesManager = new PreferencesManager();

        FormatUtil.initFormat();
        FontUtil.initFont(this.mContext);
    }

    /**
     * Returns INSTANCE of DataManager
     *
     * @return - INSTANCE of DataManager
     */
    public static DataManager getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new DataManager();
        }
        return INSTANCE;
    }

    /**
     * Returns Context of application
     *
     * @return - Context of application
     */
    public Context getContext() {
        return mContext;
    }

    /**
     * Returns PreferencesManager
     *
     * @return - PreferencesManager
     */
    public PreferencesManager getPreferencesManager() {
        return mPreferencesManager;
    }

    /**
     * Returns CalcManager
     *
     * @return - CalcManager
     */
    public CalcManager getCalcManager() {
        return mCalcManager;
    }
}
