package com.dmisb.creditcalc.utils;

import com.dmisb.creditcalc.data.managers.DataManager;

import java.util.Date;

/**
 * FormatUtil
 */
public class FormatUtil {

    /**
     * Returns double value of currency to formatted string
     *
     * @param value - value of currency
     * @return - formatted string
     */
    public static String sumFormat(double value) {

        String result = DataManager.getInstance().getSumFormat().format(value);
        return result.replace(',', '.');
    }

    /**
     * Returns double value of percent to formatted string
     *
     * @param value - value of percent
     * @return - formatted string
     */
    public static String percentFormat(double value) {

        String result = DataManager.getInstance().getPercentFormat().format(value);
        return result.replace(',', '.');
    }

    /**
     * Returns data value to formatted string
     *
      * @param value - value of date
     * @return - formatted string
     */
    public static String dateFormat(Date value) {

        return DataManager.getInstance().getDateFormat().format(value);
    }

    /**
     * Returns date value to formatted string in view "MM.yyyy"
     *
     * @param value - value of date
     * @return - formatted string
     */
    public static String periodFormat(Date value) {

        return DataManager.getInstance().getShortFormat().format(value);
    }
}
