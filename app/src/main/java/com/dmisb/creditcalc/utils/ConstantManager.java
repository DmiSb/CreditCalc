package com.dmisb.creditcalc.utils;

import java.io.Serializable;

/**
 * Constant Manager
 */
public interface ConstantManager {

    String TAG_PREFIX = "C/C ";

    String CREDIT_SUM_KEY = "CREDIT_SUM_KEY";
    String CREDIT_LENGTH_KEY = "CREDIT_LENGTH_KEY";
    String CREDIT_PERCENT_KEY = "CREDIT_PERCENT_KEY";

    String ARG_VALUE = "ARG_VALUE";
    String ARG_TYPE = "ARG_TYPE";

    int ARG_TYPE_SUM = 0;
    int ARG_TYPE_LENGTH = 2;
    int ARG_TYPE_PERCENT = 1;

    int SUM_INCREMENT = 50000;
    int LENGTH_INCREMENT = 6;
    double PERCENT_INCREMENT = 0.1;

    String FONT_CONDENSED = "Roboto_Condensed";
    String FONT_MEDIUM = "Roboto_Medium";
    String FONT_REGULAR = "Roboto_Regular";
}
