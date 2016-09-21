package com.dmisb.creditcalc.ui.adapters;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dmisb.creditcalc.R;
import com.dmisb.creditcalc.data.managers.CalcManager;
import com.dmisb.creditcalc.data.managers.DataManager;
import com.dmisb.creditcalc.data.models.CalcModel;
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

        CalcModel calcModel = mCalcManager.getCalcList().get(position);
        holder.mBinding.setCalcModel(calcModel);
    }

    @Override
    public int getItemCount() {
        return mCalcManager.getCalcList().size();
    }

    protected static class GraphicViewHolder extends RecyclerView.ViewHolder {

        protected GraphicItemBinding mBinding;

        protected GraphicViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }

    /**
     * Font Binding, example:  app:font="@{`Roboto_Condensed`}"
     *
     * @param textView - view
     * @param fontName - short name font
     */
    @BindingAdapter({"bind:font"})
    public static void setFont(TextView textView, String fontName) {
        Typeface typeface = DataManager.getInstance().getFont(fontName);
        if (typeface != null) {
            textView.setTypeface(typeface);
        }
    }
}

