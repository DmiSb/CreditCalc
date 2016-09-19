package com.dmisb.creditcalc.data.models;

import java.util.Date;

/**
 * Model for calculation pays
 */
public class CalcModel {

    // Date of pay
    public Date data;
    // Count day of pay period
    public int dayCount;
    // The outstanding debt
    public double debt;
    // Payment for percent
    public double payPercent;
    // Payment for debt
    public double payDebt;
}
