package com.dmisb.creditcalc.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * FormatUtil
 */
public class FormatUtil {

    private static DecimalFormat sSumFormat, sPercentFormat;
    private static DateFormat sDateFormat, sShortFormat;

    /**
     * Initialize formatting methods
     */
    public static void initFormat() {

        sSumFormat = new DecimalFormat("#,##0.00");
        sPercentFormat = new DecimalFormat("0.0");
        sDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        sShortFormat = new SimpleDateFormat("MM.yyyy");
    }

    /**
     * Returns double value of currency to formatted string
     *
     * @param value - value of currency
     * @return - formatted string
     */
    public static String sumFormat(double value) {

        String result = sSumFormat.format(value);
        return result.replace(',', '.');
    }

    /**
     * Returns double value of percent to formatted string
     *
     * @param value - value of percent
     * @return - formatted string
     */
    public static String percentFormat(double value) {

        String result = sPercentFormat.format(value);
        return result.replace(',', '.');
    }

    /**
     * Returns data value to formatted string
     *
      * @param value - value of date
     * @return - formatted string
     */
    public static String dateFormat(Date value) {

        return sDateFormat.format(value);
    }

    /**
     * Returns date value to formatted string in view "MM.yyyy"
     *
     * @param value - value of date
     * @return - formatted string
     */
    public static String periodFormat(Date value) {

        return sShortFormat.format(value);
    }

    public static DateFormat getDateFormat() {
        return sDateFormat;
    }
}
