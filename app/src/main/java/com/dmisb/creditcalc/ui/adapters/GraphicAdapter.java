package com.dmisb.creditcalc.ui.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmisb.creditcalc.R;
import com.dmisb.creditcalc.data.managers.CalcManager;
import com.dmisb.creditcalc.data.models.PayModel;
import com.dmisb.creditcalc.databinding.GraphicItemBinding;

/**
 * Adapter for graphic of all credit pays
 */
public class GraphicAdapter extends RecyclerView.Adapter<GraphicAdapter.GraphicViewHolder> {

    private CalcManager mCalcManager;

    public GraphicAdapter(CalcManager calcManager) {

        mCalcManager = calcManager;
    }

    @Override
    public GraphicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        /* source https://habrahabr.ru/company/dataart/blog/267735/

           LayoutInflater inflater = LayoutInflater.from(parent.getContext());
           GraphicItemBinding binding = GraphicItemBinding.inflate(inflater, parent, false);
           return new GraphicViewHolder(binding.getRoot());
        */

        return new GraphicViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.graphic_item, parent, false));
    }

    @Override
    public void onBindViewHolder(GraphicViewHolder holder, int position) {

        PayModel payModel = mCalcManager.getPayList().get(position);
        holder.mBinding.setPayModel(payModel);
    }

    @Override
    public int getItemCount() {
        return mCalcManager.getPayList().size();
    }

    protected static class GraphicViewHolder extends RecyclerView.ViewHolder {

        protected GraphicItemBinding mBinding;

        protected GraphicViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }
}

