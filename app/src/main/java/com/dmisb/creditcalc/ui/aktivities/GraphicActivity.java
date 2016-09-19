package com.dmisb.creditcalc.ui.aktivities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.dmisb.creditcalc.R;
import com.dmisb.creditcalc.data.managers.DataManager;
import com.dmisb.creditcalc.ui.adapters.GraphicAdapter;
import com.dmisb.creditcalc.utils.ConstantManager;

public class GraphicActivity extends AppCompatActivity {

    private static final String TAG = ConstantManager.TAG_PREFIX + "GraphicActivity";

    RecyclerView mRecyclerView;
    Toolbar mToolbar;

    TextView mAllPercent, mAllPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic);

        mToolbar = (Toolbar) findViewById(R.id.graphic_toolbar);
        setupToolBar();

        mAllPercent = (TextView) findViewById(R.id.all_percent);
        mAllPay = (TextView) findViewById(R.id.all_pay);

        mRecyclerView = (RecyclerView) findViewById(R.id.graphic_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        showGraphic();

        Log.d(TAG, "onCreate");
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    private void setupToolBar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void showGraphic() {

        DataManager dataManager = DataManager.getInstance();
        String value = dataManager.getSumFormat().format(dataManager.getCalcManager().getAllPercentPay());
        mAllPercent.setText(value);

        value = dataManager.getSumFormat().format(dataManager.getCalcManager().getAllDebtPay());
        mAllPay.setText(value);

        GraphicAdapter graphicAdapter = new GraphicAdapter(DataManager.getInstance().getCalcManager());
        mRecyclerView.swapAdapter(graphicAdapter, false);
    }
}
