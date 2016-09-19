package com.dmisb.creditcalc.ui.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dmisb.creditcalc.R;
import com.dmisb.creditcalc.data.managers.CalcManager;
import com.dmisb.creditcalc.data.managers.DataManager;
import com.dmisb.creditcalc.data.models.CalcModel;

import java.text.DateFormat;
import java.text.DecimalFormat;

/**
 * Adapter for graphic of all credit pays
 */
public class GraphicAdapter extends RecyclerView.Adapter<GraphicAdapter.GraphicViewHolder> {

    private Context mContext;
    private CalcManager mCalcManager;
    private DateFormat mShortFormat;
    private DecimalFormat mSumFormat;

    public GraphicAdapter(CalcManager calcManager) {
        mCalcManager = calcManager;

        DataManager dataManager = DataManager.getInstance();
        mShortFormat = dataManager.getShortFormat();
        mSumFormat = dataManager.getSumFormat();
    }

    @Override
    public GraphicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.graphic_item, parent, false);
        return new GraphicViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(GraphicViewHolder holder, int position) {

        CalcModel calcModel = mCalcManager.getCalcList().get(position);
        holder.mGraphicDate.setText(mShortFormat.format(calcModel.data));
        holder.mGraphicPercent.setText(mSumFormat.format(calcModel.payPercent));
        holder.mGraphicPay.setText(mSumFormat.format(calcModel.payDebt));
    }

    @Override
    public int getItemCount() {
        return mCalcManager.getCalcList().size();
    }

    public static class GraphicViewHolder extends RecyclerView.ViewHolder {

        protected LinearLayout mGraphicItem;
        protected TextView mGraphicDate, mGraphicPercent, mGraphicPay;

        public GraphicViewHolder(View itemView) {
            super(itemView);

            mGraphicItem = (LinearLayout) itemView.findViewById(R.id.graphic_item);
            mGraphicDate = (TextView) itemView.findViewById(R.id.graphic_date);
            mGraphicPercent = (TextView) itemView.findViewById(R.id.graphic_percent);
            mGraphicPay = (TextView) itemView.findViewById(R.id.graphic_pay);
        }
    }
}