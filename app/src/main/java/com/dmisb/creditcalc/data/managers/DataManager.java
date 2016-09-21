package com.dmisb.creditcalc.data.managers;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;

import com.dmisb.creditcalc.utils.CalcCreditApp;
import com.dmisb.creditcalc.utils.ConstantManager;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Data manager of application
 */
public class DataManager {

    private static final String TAG = ConstantManager.TAG_PREFIX + "DataManager";
    private static DataManager INSTANCE = null;

    private final Context mContext;
    private final PreferencesManager mPreferencesManager;
    private CalcManager mCalcManager;

    private static String mCurrensy;

    private DecimalFormat mSumFormat, mPercentFormat;
    private DateFormat mDateFormat, mShortFormat;

    private Typeface mCondensedFont, mMediumFont, mRegilarFont;


    /**
     * Created DataManager
     */
    private DataManager() {
        this.mCalcManager = new CalcManager();
        this.mContext = CalcCreditApp.getContext();
        this.mPreferencesManager = new PreferencesManager();

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mCurrensy = " \u20BD";
        } else {
            mCurrensy = "";
        }

        mSumFormat = new DecimalFormat("#,##0.00");
        mPercentFormat = new DecimalFormat("0.0");
        mDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        mShortFormat = new SimpleDateFormat("MM.yyyy");

        mCondensedFont = Typeface.createFromAsset(mContext.getAssets(),
            "fonts/" + ConstantManager.FONT_CONDENSED + ".ttf");
        mMediumFont = Typeface.createFromAsset(mContext.getAssets(),
            "fonts/" + ConstantManager.FONT_MEDIUM + ".ttf");
        mRegilarFont = Typeface.createFromAsset(mContext.getAssets(),
            "fonts/" + ConstantManager.FONT_REGULAR + ".ttf");
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

    /**
     * Returns value of currensy
     *
     * @return - value of currency
     */
    public String getCurrensy() {
        return mCurrensy;
    }

    /**
     * Returns decimal format for currency in "#,##0.00" , for example: 1 500 000.00
     *
     * @return - format
     */
    public DecimalFormat getSumFormat() {
        return mSumFormat;
    }

    /**
     * Returns decimal format for percent in "0.0" , for example: 11.9
     *
     * @return - format
     */
    public DecimalFormat getPercentFormat() {
        return mPercentFormat;
    }

    /**
     * Returns format for date in "dd.MM.yyyy"
     *
     * @return - format
     */
    public DateFormat getDateFormat() {
        return mDateFormat;
    }

    /**
     * Returns short format for date in "MM.yyyy"
     *
     * @return - short format
     */
    public DateFormat getShortFormat() {
        return mShortFormat;
    }

    public Typeface getFont(String typeFont) {
        switch (typeFont) {
            case ConstantManager.FONT_CONDENSED:
                return mCondensedFont;
            case ConstantManager.FONT_MEDIUM:
                return mMediumFont;
            case ConstantManager.FONT_REGULAR:
                return mRegilarFont;
            default:
                return null;
        }
    }
}
