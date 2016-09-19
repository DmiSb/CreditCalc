package com.dmisb.creditcalc.data.managers;

import android.util.Log;

import com.dmisb.creditcalc.data.models.CalcModel;
import com.dmisb.creditcalc.utils.ConstantManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Calc Manager
 */
public class CalcManager {

    private static final double DAY_IN_MONTH = 365.0/12.0;
    private static final String TAG = ConstantManager.TAG_PREFIX + "CalcManager";

    // Date of credit
    // Дата выдачи кредита;
    private Date date;
    // Length of credit
    // Срок креда
    private int length;
    // Sum of credit
    // Сумма кредита
    private double sum;
    // Percent of credit
    // Процентная ставка
    private double percent;
    // Monthly payment
    // Максимельный ежемесячный платеж
    private double monthPay;
    // Sum all pay for percent
    // Сумма всх выплат по процентам
    private double allPercentPay;
    // Sum all pay for credit
    // Сумма всех выплат за кредит
    private double allDebtPay;
    // Is fist pay only percent
    // Первый платеж только по проуентам
    private boolean isFistPayOnlyPercent;
    // Is annuity credit
    // Аннуитетный кредит
    private boolean isAnnuity;
    // Correcting calculated percent from rounded percent sum
    // Корректировать сумму процентов на разницу при их округлении
    private boolean isCorrectDeltaPercent;
    // Graphic of calculating pays
    // График платежей
    private ArrayList<CalcModel> calcList;
    // Calendar
    private Calendar mCalendar;

    /**
     * Constructor
     */
    public CalcManager() {
        this.date = null;
        this.length = 0;
        this.sum = 0;
        this.percent = 0;
        this.monthPay = 0;
        this.allPercentPay = 0;
        this.isFistPayOnlyPercent = false;
        this.isAnnuity = true;
        this.isCorrectDeltaPercent = false;
        this.calcList = new ArrayList<>();

        this.mCalendar = Calendar.getInstance();
    }

    //region Getters -------------------------------------------------------------------------------

    public int getLength() {
        return length;
    }

    public double getSum() {
        return sum;
    }

    public double getPercent() {
        return percent;
    }

    public double getMonthPay() {
        return monthPay;
    }

    public double getAllPercentPay() {
        return allPercentPay;
    }

    public double getAllDebtPay() {
        return allDebtPay;
    }

    public boolean isAnnuity() {
        return isAnnuity;
    }

    public ArrayList<CalcModel> getCalcList() {
        return calcList;
    }

    //endregion

    //region Setters -------------------------------------------------------------------------------

    public void setDate(Date date) {
        if (this.date != date) {
            this.date = date;
            calc();
        }
    }

    public void setLength(int length) {
        if (this.length != length) {
            this.length = length;
            calc();
        }
    }

    public void setSum(double sum) {
        if (this.sum != sum) {
            this.sum = sum;
            calc();
        }
    }

    public void setPercent(double percent) {
        if (this.percent != percent) {
            this.percent = percent;
            calc();
        }
    }

    public void setFistPayOnlyPercent(boolean fistPayOnlyPercent) {
        if (this.isFistPayOnlyPercent != fistPayOnlyPercent) {
            this.isFistPayOnlyPercent = fistPayOnlyPercent;
            calc();
        }
    }

    public void setAnnuity(boolean annuity) {
        if (this.isAnnuity() != annuity) {
            this.isAnnuity = annuity;
            calc();
        }
    }

    //endregion

    /**
     * Calculating credit
     */
    public void calc() {

        if (date == null || length == 0 || sum == 0 || percent == 0) {
            return;
        }

        if (isAnnuity) {
            calcAnnuityBySumm();
        } else {
            calcDifferentialBySum();
        }
    }

    /**
     * Calculating annuity credit
     */
    private void calcAnnuityBySumm() {

        Log.d(TAG, "calcAnnuityBySumm for sum=" + String.valueOf(sum) +
            ", percent=" + String.valueOf(percent) +
            ", length=" + String.valueOf(length));

        int firstPayDelta = 0;
        if (isFistPayOnlyPercent) {
            firstPayDelta = 1;
        }

        double monthPercent = percent / 12 / 100;
        monthPay = ((double) Math.round(sum * (monthPercent + monthPercent /
                (Math.pow(1 + monthPercent, (length - firstPayDelta)) -1)) * 100)) / 100;

        calcList.clear();

        allPercentPay = 0;
        allDebtPay = 0;

        double creditSum = sum;
        double calcPercent;
        double deltaPercent = 0;
        double dayDelta = 0;

        for (int i = 0; i < length; i++) {
            CalcModel calcModel = new CalcModel();

            mCalendar.setTime(date);
            dayDelta += DAY_IN_MONTH;
            mCalendar.add(mCalendar.DATE, (int) dayDelta);
            calcModel.data = mCalendar.getTime();

            calcModel.dayCount = 31;
            calcModel.debt = creditSum;
            calcPercent = creditSum * DAY_IN_MONTH * percent / 365 / 100 + deltaPercent;
            calcModel.payPercent = (double) Math.round(calcPercent * 100) / 100;

            if (isCorrectDeltaPercent)  {
                deltaPercent = calcPercent - calcModel.payPercent;
            }

            if (calcModel.payPercent < monthPay && firstPayDelta == 0) {

                if (i == length - 1) {
                    calcModel.payDebt = creditSum;
                } else {
                    calcModel.payDebt = monthPay - calcModel.payPercent;
                    if (calcModel.payDebt > creditSum) {
                        calcModel.payDebt = creditSum;
                    }
                }

            } else {
                calcModel.payDebt = 0;
            }

            firstPayDelta = 0;

            creditSum -= calcModel.payDebt;

            allPercentPay += calcModel.payPercent;
            allDebtPay += calcModel.payDebt;

            calcList.add(calcModel);
        }
    }

    /**
     * Calculating differential credit
     */
    private void calcDifferentialBySum() {

        Log.d(TAG, "calcDifferentialBySum for sum=" + String.valueOf(sum) +
                ", percent=" + String.valueOf(percent) +
                ", length=" + String.valueOf(length));

        int firstPayDelta = 0;
        if (isFistPayOnlyPercent) {
            firstPayDelta = 1;
        }

        calcList.clear();

        allPercentPay = 0;
        allDebtPay = 0;

        double creditSum = sum;
        double deltaPercent = 0;
        double dayDelta = 0;
        double calcPercent;
        double calcPay = (double) Math.round(sum * 100 / (length - firstPayDelta)) / 100;

        for (int i = 0; i < length; i++) {
            CalcModel calcModel = new CalcModel();

            mCalendar.setTime(date);
            dayDelta += DAY_IN_MONTH;
            mCalendar.add(mCalendar.DATE, (int) dayDelta);
            calcModel.data = mCalendar.getTime();

            calcModel.dayCount = 31;
            calcModel.debt = creditSum;
            calcPercent = creditSum * DAY_IN_MONTH * percent / 365 / 100 + deltaPercent;
            calcModel.payPercent = (double) Math.round(calcPercent * 100) / 100;

            if (i == 0 ) {
                monthPay = calcPay + calcModel.payPercent;
            }

            if (isCorrectDeltaPercent)  {
                deltaPercent = calcPercent - calcModel.payPercent;
            }

            if (firstPayDelta == 0) {

                if (i == length - 1) {
                    calcModel.payDebt = creditSum;
                } else {
                    calcModel.payDebt = calcPay;
                    if (calcModel.payDebt > creditSum) {
                        calcModel.payDebt = creditSum;
                    }
                }

            } else {
                calcModel.payDebt = 0;
            }

            firstPayDelta = 0;

            creditSum -= calcModel.payDebt;

            allPercentPay += calcModel.payPercent;
            allDebtPay += calcModel.payDebt;

            calcList.add(calcModel);
        }
    }
}
