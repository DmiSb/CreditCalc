package com.dmisb.creditcalc.ui.aktivities;

import android.content.Intent;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dmisb.creditcalc.R;
import com.dmisb.creditcalc.data.managers.CalcManager;
import com.dmisb.creditcalc.data.managers.DataManager;
import com.dmisb.creditcalc.ui.fragments.CreditValueDialog;
import com.dmisb.creditcalc.utils.ConstantManager;
import com.dmisb.creditcalc.utils.FormatUtil;

import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, CreditValueDialog.NoticeDialogListener {

    private static final String TAG = ConstantManager.TAG_PREFIX + "MainActivity";

    private DataManager mDataManager;
    private CalcManager mCalcManager;

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private BottomSheetBehavior mBottomSheetBehavior;

    private TextView mBottomSheetCaption, mSumValue, mLengthValue, mPercentValue;
    private TextView mPayValue, mPaysValue, mOverPay, mOverPercent, mDate;

    private SeekBar mSumSeekBar, mLengthSeekBar, mPercentSeekBar;

    private ImageView mSumImg, mLengthImg, mPercentImg;

    private RadioButton mAnnuity, mDifferent;

    private CheckBox mFirstPay;

    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDataManager = DataManager.getInstance();
        mCalcManager = mDataManager.getCalcManager();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setupToolBar();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer);
        setupDrawer();

        LinearLayout bottomSheet = (LinearLayout) findViewById(R.id.bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mBottomSheetBehavior.setHideable(false);

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(this);

        mBottomSheetCaption = (TextView) findViewById(R.id.bottom_sheet_caption);
        mBottomSheetCaption.setOnClickListener(this);

        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
                if (BottomSheetBehavior.STATE_DRAGGING == newState) {
                    mFab.animate().scaleX(0).scaleY(0).setDuration(300).start();
                } else if (BottomSheetBehavior.STATE_COLLAPSED == newState) {
                    mFab.animate().scaleX(1).scaleY(1).setDuration(300).start();
                }
            }

            @Override
            public void onSlide(View bottomSheet, float slideOffset) {

            }
        });

        mPayValue = (TextView) findViewById(R.id.result_pay);
        mPaysValue = (TextView) findViewById(R.id.result_all_pay);
        mOverPay = (TextView) findViewById(R.id.result_over_pay);
        mOverPercent = (TextView) findViewById(R.id.result_over_percent);

        mDate = (TextView) findViewById(R.id.credit_date);

        mSumValue = (TextView) findViewById(R.id.credit_sum_value);
        mSumValue.setOnClickListener(this);
        mSumImg = (ImageView) findViewById(R.id.credit_sum_img);
        mSumImg.setOnClickListener(this);
        mSumSeekBar = (SeekBar) findViewById(R.id.credit_sum_sb);
        mSumSeekBar.setOnSeekBarChangeListener(this);

        mLengthValue = (TextView) findViewById(R.id.credit_length_value);
        mLengthValue.setOnClickListener(this);
        mLengthImg = (ImageView) findViewById(R.id.credit_length_img);
        mLengthImg.setOnClickListener(this);
        mLengthSeekBar = (SeekBar) findViewById(R.id.credit_length_sb);
        mLengthSeekBar.setOnSeekBarChangeListener(this);

        mPercentValue = (TextView) findViewById(R.id.credit_percent_value);
        mPercentValue.setOnClickListener(this);
        mPercentImg = (ImageView) findViewById(R.id.credit_percent_img);
        mPercentImg.setOnClickListener(this);
        mPercentSeekBar = (SeekBar) findViewById(R.id.credit_percent_sb);
        mPercentSeekBar.setOnSeekBarChangeListener(this);

        mPayValue = (TextView) findViewById(R.id.result_pay);
        mPaysValue = (TextView) findViewById(R.id.result_all_pay);
        mOverPay = (TextView) findViewById(R.id.result_over_pay);
        mOverPercent = (TextView) findViewById(R.id.result_over_percent);

        mAnnuity = (RadioButton) findViewById(R.id.annuity_rb);
        mAnnuity.setOnClickListener(this);
        mDifferent = (RadioButton) findViewById(R.id.differential_rb);
        mDifferent.setOnClickListener(this);

        mFirstPay = (CheckBox) findViewById(R.id.first_pay_percent_cb);
        mFirstPay.setOnClickListener(this);

        if (savedInstanceState == null) {

            mCalcManager.setAnnuity(mAnnuity.isChecked());

            double creditSum = mDataManager.getPreferencesManager().getCreditSum();
            setSumSeekBar(creditSum);

            int creditLength = mDataManager.getPreferencesManager().getCreditLength();
            setLengthSeekBar(creditLength);

            double creditPercent = mDataManager.getPreferencesManager().getCreditPercent();
            setPercentSeekBar(creditPercent);

            Date date = new Date();
            mDate.setText(FormatUtil.dateFormat(date));
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
                    mFab.animate().scaleX(0).scaleY(0).setDuration(300).start();
                } else {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    mFab.animate().scaleX(1).scaleY(1).setDuration(300).start();
                }
                break;

            case R.id.credit_sum_img:
            case R.id.credit_sum_value:
                getCreditSumVaue(mCalcManager.getSum(), mSumSeekBar);
                break;

            case R.id.credit_length_img:
            case R.id.credit_length_value:
                getCreditLengthVaue(mCalcManager.getLength(), mLengthSeekBar);
                break;

            case R.id.credit_percent_img:
            case R.id.credit_percent_value:
                getCreditPercentVaue(mCalcManager.getPercent(), mPercentSeekBar);
                break;

            case R.id.first_pay_percent_cb:
                mCalcManager.setFistPayOnlyPercent(mFirstPay.isChecked());
                setCreditValues();
                break;

            case R.id.fab:
                showGraphic();
                break;

            case R.id.annuity_rb:
                mDifferent.setChecked(false);
                if (! mCalcManager.isAnnuity()) {
                    mCalcManager.setAnnuity(true);
                    setCreditValues();
                }
                break;

            case R.id.differential_rb:
                mAnnuity.setChecked(false);
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
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
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
        mSumSeekBar.setOnSeekBarChangeListener(this);
        mLengthSeekBar.setOnSeekBarChangeListener(this);
        mPercentSeekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

        mSumSeekBar.setOnSeekBarChangeListener(this);
        mLengthSeekBar.setOnSeekBarChangeListener(this);
        mPercentSeekBar.setOnSeekBarChangeListener(this);
    }

    /**
     * Setup Toolbar and ActionBar
     */
    private void setupToolBar() {
        Log.d(TAG, "setupToolBar");

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Setup navigation drawer
     */
    private void setupDrawer() {

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                    switch (item.getItemId()) {
                        case R.id.save_action:

                            break;
                        case R.id.send_action:

                            break;
                        case R.id.credit_list:

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

    private void setCreditSum(double creditSum) {
        String value = FormatUtil.sumFormat(creditSum) + mDataManager.getCurrensy();
        mSumValue.setText(value);
        mCalcManager.setSum(creditSum);
    }

    private void setCreditLength(int creditLength) {
        String value = String.valueOf(creditLength) + " / " +
                FormatUtil.percentFormat((double) creditLength/12);
        mLengthValue.setText(value);
        mCalcManager.setLength(creditLength);
    }

    private void setCreditPercent(double creditPercent) {
        String value = FormatUtil.percentFormat(creditPercent) + " %";
        mPercentValue.setText(value);
        mCalcManager.setPercent(creditPercent);
    }

    private void setSumSeekBar(double creditSum) {
        mSumSeekBar.setProgress((int) (creditSum/ConstantManager.SUM_INCREMENT - 1));
    }

    private void setLengthSeekBar(int creditLength) {
        mLengthSeekBar.setProgress(creditLength/ConstantManager.LENGTH_INCREMENT - 1);
    }

    private void setPercentSeekBar(double creditPercent) {
        mPercentSeekBar.setProgress((int) (creditPercent/ConstantManager.PERCENT_INCREMENT -1));
    }

    private void setCreditValues() {

        String value = FormatUtil.sumFormat(mCalcManager.getMonthPay()) +
                mDataManager.getCurrensy();
        mPayValue.setText(value);

        value = FormatUtil.sumFormat(
                mCalcManager.getAllPercentPay() + mCalcManager.getSum()) +
                mDataManager.getCurrensy();
        mPaysValue.setText(value);

        value = FormatUtil.sumFormat(
                mCalcManager.getAllPercentPay()) +
                mDataManager.getCurrensy();
        mOverPay.setText(value);

        value = FormatUtil.percentFormat(
                mCalcManager.getAllPercentPay() / mCalcManager.getSum() * 100) + " %";
        mOverPercent.setText(value);
    }

    private void getCreditSumVaue(double value, SeekBar seekBar){

        seekBar.setOnSeekBarChangeListener(null);
        DialogFragment dialog = CreditValueDialog.newSumInstance(value);
        dialog.show(getSupportFragmentManager(), "value_dialog");
    }

    private void getCreditPercentVaue(double value, SeekBar seekBar){

        seekBar.setOnSeekBarChangeListener(null);
        DialogFragment dialog = CreditValueDialog.newPercentInstance(value);
        dialog.show(getSupportFragmentManager(), "value_dialog");
    }

    private void getCreditLengthVaue(int value, SeekBar seekBar){

        seekBar.setOnSeekBarChangeListener(null);
        DialogFragment dialog = CreditValueDialog.newLengthInstance(value);
        dialog.show(getSupportFragmentManager(), "value_dialog");
    }

    private void showGraphic() {
        Intent graphicIntent = new Intent(MainActivity.this, GraphicActivity.class);
        startActivity(graphicIntent);
    }
}
