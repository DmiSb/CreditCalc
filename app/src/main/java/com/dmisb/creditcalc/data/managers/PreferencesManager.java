package com.dmisb.creditcalc.data.managers;

import android.content.SharedPreferences;

import com.dmisb.creditcalc.utils.CalcCreditApp;
import com.dmisb.creditcalc.utils.ConstantManager;

/**
 * PreferencesManager
 */
public class PreferencesManager {

    private SharedPreferences mSharedPreferences;

    /**
     * Returns Shared Preferences of Application
     */
    public PreferencesManager() {
        mSharedPreferences = CalcCreditApp.getSharedPreferences();
    }

    /**
     * Saves Sum of Credit in Shared Preferences
     *
     * @param creditSum - Sum of Credit
     */
    public void setCreditSum(double creditSum) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(ConstantManager.CREDIT_SUM_KEY, String.valueOf(creditSum));
        editor.apply();
    }

    /**
     * Returns Sum of Credit from Shared Preferences
     *
     * @return - Sum of Credit
     */
    public double getCreditSum() {
        return Double.valueOf(mSharedPreferences.getString(ConstantManager.CREDIT_SUM_KEY, "1000000"));
    }

    /**
     * Saves Length of Credit in Shared Preferences
     *
     * @param creditLength - Length of Credit
     */
    public void setCreditLength(int creditLength) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(ConstantManager.CREDIT_LENGTH_KEY, creditLength);
        editor.apply();
    }

    /**
     * Returns Length of Credit from Shared Preferences
     *
     * @return - Length of Credit
     */
    public int getCreditLength() {
        return mSharedPreferences.getInt(ConstantManager.CREDIT_LENGTH_KEY, 60);
    }

    /**
     * Saves Percent of Credit in Shared Preferences
     *
     * @param creditPercent - Percent of Credit
     */
    public void setCreditPercent(double creditPercent) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(ConstantManager.CREDIT_PERCENT_KEY, String.valueOf(creditPercent));
        editor.apply();
    }

    /**
     * Returns Percent of Credit from Shared Preferences
     *
     * @return - Percent of Credit
     */
    public double getCreditPercent() {
        return Double.valueOf(mSharedPreferences.getString(ConstantManager.CREDIT_PERCENT_KEY, "11.9"));
    }

    /**
     * Returns saved E-mail for sending report
     *
     * @return - E-mail
     */
    public String getSettingEmail() {
        return mSharedPreferences.getString(ConstantManager.SETTING_EMAIL, "");
    }

    /**
     * Saved E-mail for sending report from SettingActivity
     *
     * @param email - E-mail
     */
    public void setSettingEmail(String email) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(ConstantManager.SETTING_EMAIL, email);
        editor.apply();
    }
}
