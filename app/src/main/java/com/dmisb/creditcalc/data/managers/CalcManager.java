package com.dmisb.creditcalc.data.managers;

import android.util.Log;

import com.dmisb.creditcalc.data.models.LoanModel;
import com.dmisb.creditcalc.data.models.PayModel;
import com.dmisb.creditcalc.utils.ConstantManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Calc Manager
 */
public class CalcManager {

    private static final double DAY_IN_MONTH = 365.0/12.0;
    private static final String TAG = ConstantManager.TAG_PREFIX + "CalcManager";

    // Model of Loan
    private LoanModel loan;
    // Correcting calculated percent from rounded percent sum
    // Корректировать сумму процентов на разницу при их округлении
    private boolean isCorrectDeltaPercent;
    // Graphic of calculating pays
    // График платежей
    private ArrayList<PayModel> payList;
    // Calendar
    private Calendar mCalendar;

    /**
     * Constructor
     */
    public CalcManager() {

        loan = new LoanModel();
        loan.uid = UUID.randomUUID().toString();
        loan.date = null;
        loan.length = 0;
        loan.sum = 0;
        loan.percent = 0;
        loan.monthPay = 0;
        loan.allPercentPay = 0;
        loan.isFistPayOnlyPercent = false;
        loan.isAnnuity = true;

        isCorrectDeltaPercent = false;
        payList = new ArrayList<>();

        mCalendar = Calendar.getInstance();
    }

    //region Getters -------------------------------------------------------------------------------

    public int getLength() {
        return loan.length;
    }

    public double getSum() {
        return loan.sum;
    }

    public double getPercent() {
        return loan.percent;
    }

    public double getMonthPay() {
        return loan.monthPay;
    }

    public double getAllPercentPay() {
        return loan.allPercentPay;
    }

    public double getAllDebtPay() {
        return loan.allDebtPay;
    }

    public boolean isAnnuity() {
        return loan.isAnnuity;
    }

    public ArrayList<PayModel> getPayList() {
        return payList;
    }

    //endregion

    //region Setters -------------------------------------------------------------------------------

    public void setDate(Date date) {
        if (loan.date != date) {
            loan.date = date;
            calc();
        }
    }

    public void setLength(int length) {
        if (loan.length != length) {
            loan.length = length;
            calc();
        }
    }

    public void setSum(double sum) {
        if (loan.sum != sum) {
            loan.sum = sum;
            calc();
        }
    }

    public void setPercent(double percent) {
        if (loan.percent != percent) {
            loan.percent = percent;
            calc();
        }
    }

    public void setFistPayOnlyPercent(boolean fistPayOnlyPercent) {
        if (loan.isFistPayOnlyPercent != fistPayOnlyPercent) {
            loan.isFistPayOnlyPercent = fistPayOnlyPercent;
            calc();
        }
    }

    public void setAnnuity(boolean annuity) {
        if (loan.isAnnuity != annuity) {
            loan.isAnnuity = annuity;
            calc();
        }
    }

    //endregion

    /**
     * Calculating credit
     */
    public void calc() {

        if (loan.date == null || loan.length == 0 || loan.sum == 0 || loan.percent == 0) {
            return;
        }

        if (loan.isAnnuity) {
            calcAnnuityBySumm();
        } else {
            calcDifferentialBySum();
        }
    }

    /**
     * Calculating annuity credit
     */
    private void calcAnnuityBySumm() {

        Log.d(TAG, "calcAnnuityBySumm for sum=" + String.valueOf(loan.sum) +
            ", percent=" + String.valueOf(loan.percent) +
            ", length=" + String.valueOf(loan.length));

        int firstPayDelta = 0;
        if (loan.isFistPayOnlyPercent) {
            firstPayDelta = 1;
        }

        double monthPercent = loan.percent / 12 / 100;
        loan.monthPay = ((double) Math.round(loan.sum * (monthPercent + monthPercent /
                (Math.pow(1 + monthPercent, (loan.length - firstPayDelta)) -1)) * 100)) / 100;

        payList.clear();

        loan.allPercentPay = 0;
        loan.allDebtPay = 0;

        double creditSum = loan.sum;
        double calcPercent;
        double deltaPercent = 0;
        double dayDelta = 0;

        for (int i = 0; i < loan.length; i++) {
            PayModel payModel = new PayModel();

            mCalendar.setTime(loan.date);
            dayDelta += DAY_IN_MONTH;
            mCalendar.add(mCalendar.DATE, (int) dayDelta);
            payModel.data = mCalendar.getTime();

            payModel.dayCount = 31;
            payModel.debt = creditSum;
            calcPercent = creditSum * DAY_IN_MONTH * loan.percent / 365 / 100 + deltaPercent;
            payModel.payPercent = (double) Math.round(calcPercent * 100) / 100;

            if (isCorrectDeltaPercent)  {
                deltaPercent = calcPercent - payModel.payPercent;
            }

            if (payModel.payPercent < loan.monthPay && firstPayDelta == 0) {

                if (i == loan.length - 1) {
                    payModel.payDebt = creditSum;
                } else {
                    payModel.payDebt = loan.monthPay - payModel.payPercent;
                    if (payModel.payDebt > creditSum) {
                        payModel.payDebt = creditSum;
                    }
                }

            } else {
                payModel.payDebt = 0;
            }

            firstPayDelta = 0;

            creditSum -= payModel.payDebt;

            loan.allPercentPay += payModel.payPercent;
            loan.allDebtPay += payModel.payDebt;

            payList.add(payModel);
        }
    }

    /**
     * Calculating differential credit
     */
    private void calcDifferentialBySum() {

        Log.d(TAG, "calcDifferentialBySum for sum=" + String.valueOf(loan.sum) +
                ", percent=" + String.valueOf(loan.percent) +
                ", length=" + String.valueOf(loan.length));

        int firstPayDelta = 0;
        if (loan.isFistPayOnlyPercent) {
            firstPayDelta = 1;
        }

        payList.clear();

        loan.allPercentPay = 0;
        loan.allDebtPay = 0;

        double creditSum = loan.sum;
        double deltaPercent = 0;
        double dayDelta = 0;
        double calcPercent;
        double calcPay = (double) Math.round(loan.sum * 100 / (loan.length - firstPayDelta)) / 100;

        for (int i = 0; i < loan.length; i++) {
            PayModel payModel = new PayModel();

            mCalendar.setTime(loan.date);
            dayDelta += DAY_IN_MONTH;
            mCalendar.add(mCalendar.DATE, (int) dayDelta);
            payModel.data = mCalendar.getTime();

            payModel.dayCount = 31;
            payModel.debt = creditSum;
            calcPercent = creditSum * DAY_IN_MONTH * loan.percent / 365 / 100 + deltaPercent;
            payModel.payPercent = (double) Math.round(calcPercent * 100) / 100;

            if (i == 0 ) {
                loan.monthPay = calcPay + payModel.payPercent;
            }

            if (isCorrectDeltaPercent)  {
                deltaPercent = calcPercent - payModel.payPercent;
            }

            if (firstPayDelta == 0) {

                if (i == loan.length - 1) {
                    payModel.payDebt = creditSum;
                } else {
                    payModel.payDebt = calcPay;
                    if (payModel.payDebt > creditSum) {
                        payModel.payDebt = creditSum;
                    }
                }

            } else {
                payModel.payDebt = 0;
            }

            firstPayDelta = 0;

            creditSum -= payModel.payDebt;

            loan.allPercentPay += payModel.payPercent;
            loan.allDebtPay += payModel.payDebt;

            payList.add(payModel);
        }
    }
}
