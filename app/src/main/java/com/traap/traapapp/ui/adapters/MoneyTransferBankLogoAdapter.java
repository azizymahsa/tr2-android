package com.traap.traapapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.traap.traapapp.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Authors:
 * Reza Nejati <reza.n.j.t.i@gmail.com>
 * Copyright © 2017
 */
public class MoneyTransferBankLogoAdapter extends RecyclerView.Adapter<MoneyTransferBankLogoAdapter.MyViewHolder>
{


    public MoneyTransferBankLogoAdapter()
    {

    }

    @NonNull
    @Override
    public MoneyTransferBankLogoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bank_logo_bill, parent, false);


        return new MoneyTransferBankLogoAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MoneyTransferBankLogoAdapter.MyViewHolder holder, int position)
    {


    }

    @Override
    public int getItemCount()
    {
        return 20;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {




        private MyViewHolder(View convertView)
        {
            super(convertView);


        }
    }
}

