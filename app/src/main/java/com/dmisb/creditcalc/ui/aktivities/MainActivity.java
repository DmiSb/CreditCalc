package com.dmisb.creditcalc.ui.aktivities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;

import com.dmisb.creditcalc.R;
import com.dmisb.creditcalc.data.managers.CalcManager;
import com.dmisb.creditcalc.data.managers.DataManager;
import com.dmisb.creditcalc.databinding.ActivityMainBinding;
import com.dmisb.creditcalc.ui.fragments.EditValueDialog;
import com.dmisb.creditcalc.utils.ConstantManager;
import com.dmisb.creditcalc.utils.FormatUtil;

import java.util.Date;

public class MainActivity extends BaseActivity
        implements
            View.OnClickListener,
            SeekBar.OnSeekBarChangeListener,
            EditValueDialog.NoticeDialogListener {

    private static final String TAG = ConstantManager.TAG_PREFIX + "MainActivity";

    private ActivityMainBinding mBinding;
    private DataManager mDataManager;
    private CalcManager mCalcManager;
    private BottomSheetBehavior mBottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mDataManager = DataManager.getInstance();
        mCalcManager = mDataManager.getCalcManager();

        setupToolBar();
        setupDrawer();

        mBottomSheetBehavior = BottomSheetBehavior.from(mBinding.bottomLayout.bottomSheet);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mBottomSheetBehavior.setHideable(false);

        mBinding.bottomLayout.bottomSheetCaption.setOnClickListener(this);

        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
                if (BottomSheetBehavior.STATE_DRAGGING == newState) {
                    mBinding.fab.animate().scaleX(0).scaleY(0).setDuration(300).start();
                    setListenClick(false);
                    setListenSeekBar(false, false);
                } else if (BottomSheetBehavior.STATE_COLLAPSED == newState) {
                    mBinding.fab.animate().scaleX(1).scaleY(1).setDuration(300).start();
                    setListenClick(true);
                    setListenSeekBar(true, true);
                }
            }

            @Override
            public void onSlide(View bottomSheet, float slideOffset) {

            }
        });

        mBinding.bottomLayout.annuityRb.setOnClickListener(this);
        mBinding.bottomLayout.differentialRb.setOnClickListener(this);
        mBinding.bottomLayout.firstPayPercentCb.setOnClickListener(this);

        mBinding.fab.setOnClickListener(this);

        setListenClick(true);
        setListenSeekBar(true, true);

        if (savedInstanceState == null) {

            mCalcManager.setAnnuity(mBinding.bottomLayout.annuityRb.isChecked());

            double creditSum = mDataManager.getPreferencesManager().getCreditSum();
            setSumSeekBar(creditSum);

            int creditLength = mDataManager.getPreferencesManager().getCreditLength();
            setLengthSeekBar(creditLength);

            double creditPercent = mDataManager.getPreferencesManager().getCreditPercent();
            setPercentSeekBar(creditPercent);

            Date date = new Date();
            mBinding.bottomLayout.creditDate.setText(FormatUtil.dateFormat(date));
            mCalcManager.setDate(date);
            setCreditValues();
        }

        Log.d(TAG, "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bottom_sheet_caption:
                if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    mBinding.fab.animate().scaleX(0).scaleY(0).setDuration(300).start();
                    setListenClick(false);
                    setListenSeekBar(false, false);
                } else {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    mBinding.fab.animate().scaleX(1).scaleY(1).setDuration(300).start();
                    setListenClick(true);
                    setListenSeekBar(true, true);
                }
                break;

            case R.id.credit_sum_img:
            case R.id.credit_sum_value:
                getCreditSumVaue(mCalcManager.getSum(), mBinding.creditLayout.creditSumSb);
                break;

            case R.id.credit_length_img:
            case R.id.credit_length_value:
                getCreditLengthVaue(mCalcManager.getLength(), mBinding.creditLayout.creditLengthSb);
                break;

            case R.id.credit_percent_img:
            case R.id.credit_percent_value:
                getCreditPercentVaue(mCalcManager.getPercent(), mBinding.creditLayout.creditPercentSb);
                break;

            case R.id.first_pay_percent_cb:
                mCalcManager.setFistPayOnlyPercent(mBinding.bottomLayout.firstPayPercentCb.isChecked());
                setCreditValues();
                break;

            case R.id.fab:
                showGraphic();
                break;

            case R.id.annuity_rb:
                mBinding.bottomLayout.differentialRb.setChecked(false);
                if (! mCalcManager.isAnnuity()) {
                    mCalcManager.setAnnuity(true);
                    setCreditValues();
                }
                break;

            case R.id.differential_rb:
                mBinding.bottomLayout.annuityRb.setChecked(false);
                if ( mCalcManager.isAnnuity()) {
                    mCalcManager.setAnnuity(false);
                    setCreditValues();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mBinding.navigationDrawer.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed");

        if(mBinding.navigationDrawer.isDrawerOpen(GravityCompat.START)) {
            mBinding.navigationDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        switch (seekBar.getId()) {
            case R.id.credit_sum_sb:
                setCreditSum((double) (seekBar.getProgress() + 1) * ConstantManager.SUM_INCREMENT);
                setCreditValues();
                break;
            case R.id.credit_length_sb:
                setCreditLength((seekBar.getProgress() + 1) * ConstantManager.LENGTH_INCREMENT);
                setCreditValues();
                break;
            case R.id.credit_percent_sb:
                setCreditPercent((double) (seekBar.getProgress() + 1) * ConstantManager.PERCENT_INCREMENT);
                setCreditValues();
                break;
            default:
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, double value, int valueType) {

        switch (valueType) {
            case ConstantManager.ARG_TYPE_SUM:
                setCreditSum(value);
                setSumSeekBar(value);
                break;
            case ConstantManager.ARG_TYPE_PERCENT:
                setCreditPercent(value);
                setPercentSeekBar(value);
                break;
            case ConstantManager.ARG_TYPE_LENGTH:
                setCreditLength((int) value);
                setLengthSeekBar((int) value);
                break;
        }
        setCreditValues();
        setListenSeekBar(true, true);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

        setListenSeekBar(true, true);
    }

    /**
     * Setup Toolbar
     */
    private void setupToolBar() {
        Log.d(TAG, "setupToolBar");

        setSupportActionBar(mBinding.toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Setup Navigation drawer
     */
    private void setupDrawer() {

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    mBinding.navigationDrawer.closeDrawer(GravityCompat.START);
                    switch (item.getItemId()) {
                        case R.id.save_action:

                            break;
                        case R.id.send_action:

                            break;
                        case R.id.calc_list:

                            break;
                        case R.id.new_credit:

                            break;
                        case R.id.settings:

                            break;

                    }
                    return true;
                }
            });
        }
    }

    private void setListenClick(boolean listen) {

        if (listen) {
            mBinding.creditLayout.creditSumValue.setOnClickListener(this);
            mBinding.creditLayout.creditSumImg.setOnClickListener(this);

            mBinding.creditLayout.creditLengthValue.setOnClickListener(this);
            mBinding.creditLayout.creditLengthImg.setOnClickListener(this);

            mBinding.creditLayout.creditPercentValue.setOnClickListener(this);
            mBinding.creditLayout.creditPercentImg.setOnClickListener(this);
        } else {
            mBinding.creditLayout.creditSumValue.setOnClickListener(null);
            mBinding.creditLayout.creditSumImg.setOnClickListener(null);

            mBinding.creditLayout.creditLengthValue.setOnClickListener(null);
            mBinding.creditLayout.creditLengthImg.setOnClickListener(this);

            mBinding.creditLayout.creditPercentValue.setOnClickListener(null);
            mBinding.creditLayout.creditPercentImg.setOnClickListener(null);
        }
    }

    private void setListenSeekBar(boolean listen, boolean enabled) {

        if (listen) {
            mBinding.creditLayout.creditSumSb.setOnSeekBarChangeListener(this);
            mBinding.creditLayout.creditLengthSb.setOnSeekBarChangeListener(this);
            mBinding.creditLayout.creditPercentSb.setOnSeekBarChangeListener(this);
        } else {
            mBinding.creditLayout.creditSumSb.setOnSeekBarChangeListener(null);
            mBinding.creditLayout.creditLengthSb.setOnSeekBarChangeListener(null);
            mBinding.creditLayout.creditPercentSb.setOnSeekBarChangeListener(null);
        }

        mBinding.creditLayout.creditSumSb.setEnabled(enabled);
        mBinding.creditLayout.creditLengthSb.setEnabled(enabled);
        mBinding.creditLayout.creditPercentSb.setEnabled(enabled);
    }

    private void setCreditSum(double creditSum) {
        String value = FormatUtil.sumFormat(creditSum);
        mBinding.creditLayout.creditSumValue.setText(value);
        mCalcManager.setSum(creditSum);
    }

    private void setCreditLength(int creditLength) {
        String value = String.valueOf(creditLength) + " / " +
                FormatUtil.percentFormat((double) creditLength/12);
        mBinding.creditLayout.creditLengthValue.setText(value);
        mCalcManager.setLength(creditLength);
    }

    private void setCreditPercent(double creditPercent) {
        String value = FormatUtil.percentFormat(creditPercent) + " %";
        mBinding.creditLayout.creditPercentValue.setText(value);
        mCalcManager.setPercent(creditPercent);
    }

    private void setSumSeekBar(double creditSum) {
        mBinding.creditLayout.creditSumSb.setProgress((int) (creditSum/ConstantManager.SUM_INCREMENT - 1));
    }

    private void setLengthSeekBar(int creditLength) {
        mBinding.creditLayout.creditLengthSb.setProgress(creditLength/ConstantManager.LENGTH_INCREMENT - 1);
    }

    private void setPercentSeekBar(double creditPercent) {
        mBinding.creditLayout.creditPercentSb.setProgress((int) (creditPercent/ConstantManager.PERCENT_INCREMENT -1));
    }

    private void setCreditValues() {

        String value = FormatUtil.sumFormat(mCalcManager.getMonthPay());
        mBinding.resultLayout.resultPay.setText(value);

        value = FormatUtil.sumFormat(
                mCalcManager.getAllPercentPay() + mCalcManager.getSum());
        mBinding.resultLayout.resultAllPay.setText(value);

        value = FormatUtil.sumFormat(
                mCalcManager.getAllPercentPay());
        mBinding.resultLayout.resultOverPay.setText(value);

        value = FormatUtil.percentFormat(
                mCalcManager.getAllPercentPay() / mCalcManager.getSum() * 100) + " %";
        mBinding.resultLayout.resultOverPercent.setText(value);
    }

    private void getCreditSumVaue(double value, SeekBar seekBar){

        setListenSeekBar(false, true);
        DialogFragment dialog = EditValueDialog.newSumInstance(value);
        dialog.show(getSupportFragmentManager(), "value_dialog");
    }

    private void getCreditPercentVaue(double value, SeekBar seekBar){

        setListenSeekBar(false, true);
        DialogFragment dialog = EditValueDialog.newPercentInstance(value);
        dialog.show(getSupportFragmentManager(), "value_dialog");
    }

    private void getCreditLengthVaue(int value, SeekBar seekBar){

        setListenSeekBar(false, true);
        DialogFragment dialog = EditValueDialog.newLengthInstance(value);
        dialog.show(getSupportFragmentManager(), "value_dialog");
    }

    private void showGraphic() {
        Intent graphicIntent = new Intent(MainActivity.this, GraphicActivity.class);
        startActivity(graphicIntent);
    }
}
