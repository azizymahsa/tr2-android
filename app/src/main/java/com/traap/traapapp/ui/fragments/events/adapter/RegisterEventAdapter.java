package com.traap.traapapp.ui.fragments.events.adapter;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.traap.traapapp.R;
import com.traap.traapapp.ui.fragments.events.PersonEvent;
import com.traap.traapapp.utilities.ClearableEditText;
import com.traap.traapapp.utilities.NationalCodeValidation;
import com.traap.traapapp.utilities.Utility;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Authors:
 * Reza Nejati <reza.n.j.t.i@gmail.com>
 * Copyright © 2017
 */
public class RegisterEventAdapter extends RecyclerView.Adapter<RegisterEventAdapter.ViewHolder>
{

    private boolean flagUpdateList = false;
    private RegisterEventsAdapter events;

    private Activity activity;
    private Integer count;
    private ArrayList<PersonEvent> personEvents;

    public RegisterEventAdapter(Activity activity, Integer count, ArrayList<PersonEvent> personEvents, RegisterEventsAdapter events, boolean flagUpdateList)
    {

        this.activity = activity;
        this.count = count;
        this.personEvents = personEvents;
        this.events = events;
        this.flagUpdateList = flagUpdateList;

    }



    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.register_event_item, parent, false));
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {

        if (flagUpdateList)
        {
            if (personEvents.get(position).getMobile().length() < 11)
            {
                events.onItemAlertValid("شماره همراه باید 11 رقم باشد.",personEvents);
            }else
            if (personEvents.get(position).getFirst_name().length() < 2)
            {
                events.onItemAlertValid("لطفا نام خود را صحیح وارد کنید.(حداقل 2 کاراکتر)",personEvents);
            }else

            if (personEvents.get(position).getLast_name().length() < 2)
            {
                events.onItemAlertValid("لطفا نام خانوادگی خود را صحیح وارد کنید.(حداقل 2 کاراکتر)",personEvents);
            }else
           if (personEvents.get(position).getNational_code().length() != 10 || !NationalCodeValidation.isValidNationalCode(personEvents.get(position).getNational_code()))

            {
                events.onItemAlertValid("کدملی خود را صحیح وارد کنید.",personEvents);
            }else
            if (!personEvents.get(position).getEmail().trim().matches("[a-zA-Z0-9._-]+@[a-zA-Z]+.[a-zA-Z]+") ){
                events.onItemAlertValid("ایمیل خود را صحیح وارد کنید.",personEvents);

            }

        }
        holder.etMobile.setText(personEvents.get(position).getMobile());
        holder.etNationalCode.setText(personEvents.get(position).getNational_code());
        holder.etLastName.setText(personEvents.get(position).getLast_name());
        holder.etFirstName.setText(personEvents.get(position).getFirst_name());
        holder.etEmail.setText(personEvents.get(position).getEmail());

        holder.etMobile.addTextChangedListener(new TextWatcher()
        {

            public void afterTextChanged(Editable s)
            {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after)
            {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                personEvents.get(position).setMobile(s.toString());
            }
        });
        holder.etNationalCode.addTextChangedListener(new TextWatcher()
        {

            public void afterTextChanged(Editable s)
            {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after)
            {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                personEvents.get(position).setNational_code(s.toString());
            }
        });
        holder.etLastName.addTextChangedListener(new TextWatcher()
        {

            public void afterTextChanged(Editable s)
            {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after)
            {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                personEvents.get(position).setLast_name(s.toString());
            }
        });
        holder.etFirstName.addTextChangedListener(new TextWatcher()
        {

            public void afterTextChanged(Editable s)
            {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after)
            {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                personEvents.get(position).setFirst_name(s.toString());
            }
        });
        holder.etEmail.addTextChangedListener(new TextWatcher()
        {

            public void afterTextChanged(Editable s)
            {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after)
            {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                personEvents.get(position).setEmail(s.toString());
            }
        });

        holder.rvDelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                events.onItemClickDelete(position, personEvents.get(position).getWorkshopId(), personEvents);
            }
        });
        holder.tvCount.setText(" ﺷﺮﮐﺖ ﮐﻨﻨﺪه " + Utility.getNameNumber(position) + " :" + " کارگاه شماره: " + personEvents.get(position).getWorkshopId());
        if (position == count - 1)
        {
            holder.vUnderLine.setVisibility(View.GONE);
        } else
        {
            holder.vUnderLine.setVisibility(View.VISIBLE);

        }

    }


    @Override
    public int getItemCount()
    {

        return count;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private RelativeLayout rvDelete;
        private TextView tvCount;
        private ClearableEditText etMobile, etFirstName, etLastName, etNationalCode, etEmail;
        private View vUnderLine;


        public ViewHolder(View v)
        {
            super(v);
            rvDelete = v.findViewById(R.id.rvDelete);
            tvCount = v.findViewById(R.id.tvCount);
            vUnderLine = v.findViewById(R.id.vUnderLine);
            etFirstName = v.findViewById(R.id.etFirstName);
            etMobile = v.findViewById(R.id.etMobile);
            etLastName = v.findViewById(R.id.etLastName);
            etNationalCode = v.findViewById(R.id.etNationalCode);
            etEmail = v.findViewById(R.id.etEmail);
        }
    }

    public interface RegisterEventsAdapter
    {

        public void onItemClickDelete(Integer position, Integer workshopId, ArrayList<PersonEvent> personEvents);

        public void onItemAlertValid(String message, ArrayList<PersonEvent> personEvents);
    }
}
