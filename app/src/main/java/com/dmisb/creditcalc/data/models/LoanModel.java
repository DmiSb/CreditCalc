package com.dmisb.creditcalc.data.models;

import java.util.Date;

/**
 * Model of Loan
 */

public class LoanModel {

    //ID
    public int id;
    // UID
    public String uid;
    // Date of credit
    // Дата выдачи кредита;
    public Date date;
    // Length of credit
    // Срок креда
    public int length;
    // Sum of credit
    // Сумма кредита
    public double sum;
    // Percent of credit
    // Процентная ставка
    public double percent;
    // Monthly payment
    // Максимельный ежемесячный платеж
    public double monthPay;
    // Sum all pay for percent
    // Сумма всх выплат по процентам
    public double allPercentPay;
    // Sum all pay for credit
    // Сумма всех выплат за кредит
    public double allDebtPay;
    // Is fist pay only percent
    // Первый платеж только по проуентам
    public boolean isFistPayOnlyPercent;
    // Is annuity credit
    // Аннуитетный кредит
    public boolean isAnnuity;
}
