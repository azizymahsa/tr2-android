package com.traap.traapapp.ui.fragments.events.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.event.getWorkshopById.Result;
import com.traap.traapapp.utilities.ConvertPersianNumberToString;
import com.traap.traapapp.utilities.Utility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Authors:
 * Reza Nejati <reza.n.j.t.i@gmail.com>
 * Copyright © 2017
 */
public class DetailEventAdapter extends RecyclerView.Adapter<DetailEventAdapter.ViewHolder>
{


    private Activity activity;
    private ArrayList<String> count;
    private DetailEventAdapterEvents events;
    List<Result> results=new ArrayList<>();
    public DetailEventAdapter(Activity activity, List<Result> results, DetailEventAdapterEvents events, ArrayList<String> count)
    {

        this.activity=activity;
        this.events=events;
        this.count=count;
        this.results=results;

    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detail_event_item, parent, false));
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {

        holder.txtPrice.setText(Utility.priceFormat(results.get(position).getPrice().intValue())+"ریال");
        holder.txtDate.setText(results.get(position).getRegisterEndDate()+"");
        holder.txtClass.setText(results.get(position).getName()+"");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(activity, R.layout.my_spinner_item, count);
        holder.spCount.setAdapter(dataAdapter);
        holder.spCount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int spPosition, long id)
            {
                events.onItemCountSelected(count.get(spPosition),position,results.get(position).getId(),results.get(position).getPrice().intValue());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

    }


    @Override
    public int getItemCount()
    {
        return results.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private Spinner spCount;
        private TextView txtClass,txtDate,txtPrice;



        public ViewHolder(View v)
        {
            super(v);
            spCount=v.findViewById(R.id.spCount);
            txtPrice=v.findViewById(R.id.txtPrice);
            txtDate=v.findViewById(R.id.txtDate);
            txtClass=v.findViewById(R.id.txtClass);

        }
    }
    public interface DetailEventAdapterEvents{

        public void onItemCountSelected(String count,Integer position,Integer workshopId,Integer priceDouble);
    }


}
