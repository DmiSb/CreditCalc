package com.dmisb.creditcalc.utils;

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

    String SETTING_EMAIL = "SETTING_EMAIL";

    String FIELD_PRIMARY = "INTEGER PRIMARY KEY AUTOINCREMENT";
    String FIELD_INTEGER = "INTEGER";
    String FIELD_DATETIME = "DATETIME";
    String FIELD_NUMERIC_15_2 = "NUMERIC(15,2)";
    String FIELD_NUMERIC_5_1 = "NUMERIC(5,1)";
    String FIELD_NVARCHAR_40 = "NVARCHAR(40)";
    String FIELD_NVARCHAR_200 = "NVARCHAR(200)";

}
