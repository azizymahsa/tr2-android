package com.traap.traapapp.ui.adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.traap.traapapp.R;
import com.traap.traapapp.models.CountryCodeModel;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.annotations.NonNull;


/**
 * Authors:
 * Reza Nejati <reza.n.j.t.i@gmail.com>
 * Copyright © 2017
 */
public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    private Context context;
    private List<CountryCodeModel> results;
    private OnCountryItemClick onCountryItemClick;


    public CountryAdapter(Context context, ArrayList<CountryCodeModel> results, OnCountryItemClick onCountryItemClick) {
        this.context = context;
        this.results = results;
        this.onCountryItemClick = onCountryItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        this.context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.country_item, parent, false);
        return new ViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        CountryCodeModel result = results.get(position);

        holder.tvName.setText(result.getName());
        holder.tvCode.setText(result.getDialCode());
        holder.llRoot.setOnClickListener(view -> {
            onCountryItemClick.onCountryCode(result);

        });
      /*  if ((position % 2) == 0)
            holder.llRoot.setBackgroundColor(context.getResources().getColor(R.color.light_app_color));
        else
            holder.llRoot.setBackgroundColor(context.getResources().getColor(R.color.light_app_color2));*/


    }


    @Override
    public int getItemCount() {
        return results.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCode, tvName;
        private LinearLayout llRoot;
        private View view;

        private ViewHolder(View v) {
            super(v);
            tvCode = v.findViewById(R.id.tvCode);
            tvName = v.findViewById(R.id.tvName);
            llRoot = v.findViewById(R.id.llRoot);


        }
    }

    public interface OnCountryItemClick {
        void onCountryCode(CountryCodeModel codeModel);
    }

}
