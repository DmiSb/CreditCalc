package com.dmisb.creditcalc.ui.aktivities;

import android.databinding.DataBindingUtil;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.dmisb.creditcalc.R;
import com.dmisb.creditcalc.data.managers.DataManager;
import com.dmisb.creditcalc.databinding.ActivityGraphicBinding;
import com.dmisb.creditcalc.ui.adapters.GraphicAdapter;
import com.dmisb.creditcalc.utils.ConstantManager;
import com.dmisb.creditcalc.utils.FormatUtil;

public class GraphicActivity extends AppCompatActivity {

    private static final String TAG = ConstantManager.TAG_PREFIX + "GraphicActivity";

    private ActivityGraphicBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_graphic);

        setupToolBar();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mBinding.graphicRecycledView.setLayoutManager(linearLayoutManager);
        mBinding.graphicRecycledView.setHasFixedSize(true);
        showGraphic();

        Log.d(TAG, "onCreate");
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    private void setupToolBar() {
        setSupportActionBar(mBinding.graphicToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.graphic_caption_app);
        }
    }

    private void showGraphic() {

        DataManager dataManager = DataManager.getInstance();
        String value = FormatUtil.sumFormat(dataManager.getCalcManager().getAllPercentPay());
        mBinding.allPercent.setText(value);

        value = FormatUtil.sumFormat(dataManager.getCalcManager().getAllDebtPay());
        mBinding.allPay.setText(value);

        GraphicAdapter graphicAdapter = new GraphicAdapter(DataManager.getInstance().getCalcManager());
        mBinding.graphicRecycledView.swapAdapter(graphicAdapter, false);
    }
}
