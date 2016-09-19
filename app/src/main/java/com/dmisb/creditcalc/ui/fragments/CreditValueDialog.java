package com.dmisb.creditcalc.ui.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dmisb.creditcalc.R;
import com.dmisb.creditcalc.ui.aktivities.MainActivity;
import com.dmisb.creditcalc.utils.ConstantManager;
import com.dmisb.creditcalc.utils.FormatUtil;

import java.text.DecimalFormat;

/**
 * CreditValueDialog
 */
public class CreditValueDialog extends DialogFragment implements View.OnClickListener {

    private TextView mValueText;
    private double mValue;
    private int mValueType;
    private int mAfterPoint;
    private Button mClear, mPoint, mBack;
    private Button mOne, mTwo, mThree, mFour, mFive, mSix, mSeven, mEight, mNine, mZero;

    NoticeDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_edit, null);
        builder.setView(view)
                .setCancelable(true)
                .setPositiveButton(R.string.ok_caption, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (checkValue()) {
                            mListener.onDialogPositiveClick(CreditValueDialog.this, mValue, mValueType);
                        }
                    }
                })
                .setNegativeButton(R.string.cancel_caption, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogNegativeClick(CreditValueDialog.this);
                    }
                });

        mClear = (Button) view.findViewById(R.id.clear_btn);
        mPoint = (Button) view.findViewById(R.id.point_btn);
        mBack = (Button) view.findViewById(R.id.back_btn);

        mClear.setOnClickListener(this);
        mPoint.setOnClickListener(this);
        mBack.setOnClickListener(this);

        mOne   = (Button) view.findViewById(R.id.one_btn);
        mTwo   = (Button) view.findViewById(R.id.two_btn);
        mThree = (Button) view.findViewById(R.id.three_btn);
        mFour  = (Button) view.findViewById(R.id.four_btn);
        mFive  = (Button) view.findViewById(R.id.five_btn);
        mSix   = (Button) view.findViewById(R.id.six_btn);
        mSeven = (Button) view.findViewById(R.id.seven_btn);
        mEight = (Button) view.findViewById(R.id.eight_btn);
        mNine  = (Button) view.findViewById(R.id.nine_btn);
        mZero  = (Button) view.findViewById(R.id.zero_btn);

        mOne.setOnClickListener(this);
        mTwo.setOnClickListener(this);
        mThree.setOnClickListener(this);
        mFour.setOnClickListener(this);
        mFive.setOnClickListener(this);
        mSix.setOnClickListener(this);
        mSeven.setOnClickListener(this);
        mEight.setOnClickListener(this);
        mNine.setOnClickListener(this);
        mZero.setOnClickListener(this);

        mAfterPoint = 0;

        mValueText = (TextView) view.findViewById(R.id.dialog_text);
        mValueType = (int) getArguments().getSerializable(ConstantManager.ARG_TYPE);
        if (mValueType == ConstantManager.ARG_TYPE_LENGTH) {
            int v = (int) getArguments().getSerializable(ConstantManager.ARG_VALUE);
            mValue = (double) v;
        } else {
            mValue = (double) getArguments().getSerializable(ConstantManager.ARG_VALUE);
        }
        formatValue();
        if (mValueType == ConstantManager.ARG_TYPE_LENGTH) {
            mPoint.setEnabled(false);
        }
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    /**
     * Returns Fragment Dialog
     *
     * @param args - input arguments
     * @return - Fragment Dialog
     */
    private static CreditValueDialog newInstance(Bundle args) {

        CreditValueDialog dialog = new CreditValueDialog();
        dialog.setArguments(args);
        dialog.setTargetFragment(null, 0);
        return dialog;
    }

    /**
     * Returns Fragment Dialog for edit the sum of credit
     *
     * @param sum - input value sum
     * @return - Fragment Dialog
     */
    public static CreditValueDialog newSumInstance(double sum) {
        Bundle args = new Bundle();
        args.putSerializable(ConstantManager.ARG_VALUE, sum);
        args.putSerializable(ConstantManager.ARG_TYPE, ConstantManager.ARG_TYPE_SUM);

        return newInstance(args);
    }

    /**
     * Returns Fragment Dialog for edit the percent of credit
     *
     * @param percent - input value percent
     * @return - Fragment Dialog
     */
    public static CreditValueDialog newPercentInstance(double percent) {
        Bundle args = new Bundle();
        args.putSerializable(ConstantManager.ARG_VALUE, percent);
        args.putSerializable(ConstantManager.ARG_TYPE, ConstantManager.ARG_TYPE_PERCENT);

        return newInstance(args);
    }

    /**
     * Returns Fragment Dialog for edit the length of credit
     *
     * @param length - input value length
     * @return - Fragment Dialog
     */
    public static CreditValueDialog newLengthInstance(int length) {
        Bundle args = new Bundle();
        args.putSerializable(ConstantManager.ARG_VALUE, length);
        args.putSerializable(ConstantManager.ARG_TYPE, ConstantManager.ARG_TYPE_LENGTH);

        return newInstance(args);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.clear_btn:
                mValue = 0;
                mAfterPoint = 0;
                formatValue();
                break;
            case R.id.back_btn:
                delDigit();
                break;
            case R.id.point_btn:
                mAfterPoint = 1;
                break;

            case R.id.one_btn:
            case R.id.two_btn:
            case R.id.three_btn:
            case R.id.four_btn:
            case R.id.five_btn:
            case R.id.six_btn:
            case R.id.seven_btn:
            case R.id.eight_btn:
            case R.id.nine_btn:
            case R.id.zero_btn:
                addDigit(Integer.valueOf(((TextView) view).getText().toString()));
                break;
        }
    }

    /**
     * Setts value of View mValueText
     */
    private void formatValue() {
        switch (mValueType) {
            case ConstantManager.ARG_TYPE_SUM:
                mValueText.setText(FormatUtil.sumFormat(mValue));
                break;
            case ConstantManager.ARG_TYPE_PERCENT:
                mValueText.setText(FormatUtil.percentFormat(mValue));
                break;
            case ConstantManager.ARG_TYPE_LENGTH:
                mValueText.setText(String.valueOf((int) mValue));
        }
    }

    /**
     * Add one digit to value of mValueText
     *
     * @param digit - one integer digit
     */
    private void addDigit(int digit) {

        int part = (int) Math.floor(mValue);
        double fract = mValue - part;

        switch (mAfterPoint) {
            case 0:
                part = part * 10 + digit;
                break;
            case 1:
                fract = (double) digit/10;
                mAfterPoint++;
                break;
            case 2:
                fract = (double) ((int) Math.floor(Math.round(fract * 10)) * 10 + digit)/100;
                break;
        }

        mValue = fract + part;
        formatValue();
    }

    /**
     * Delete one digit
     */
    private void delDigit() {

        int part = (int) Math.floor(mValue);
        double fract = mValue - part;

        switch (mAfterPoint) {
            case 0:
                part = part/10;
                break;
            case 1:
                fract = 0;
                mAfterPoint = 0;
                break;
            case 2:
                fract = (double) ((int) (fract * 10))/10;
                mAfterPoint = 1;
                break;
        }

        mValue = fract + part;
        formatValue();
    }

    private Boolean checkValue() {

        Boolean result = false;

        switch (mValueType) {
            case ConstantManager.ARG_TYPE_SUM:
                if (mValue > ConstantManager.SUM_INCREMENT) {
                    result = true;
                }
                break;
            case ConstantManager.ARG_TYPE_PERCENT:
                if (mValue > ConstantManager.PERCENT_INCREMENT) {
                    result = true;
                }
                break;
            case ConstantManager.ARG_TYPE_LENGTH:
                if (mValue > ConstantManager.LENGTH_INCREMENT) {
                    result = true;
                }
                break;
        }

        return result;
    }

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, double value, int valueType);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
}
