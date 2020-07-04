package com.traap.traapapp.ui.fragments.events.adapter;

import android.app.Activity;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.event.Result;
import com.traap.traapapp.utilities.calendar.mohamadamin_t.persianmaterialdatetimepicker.utils.PersianCalendar;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

/**
 * Authors:
 * Reza Nejati <reza.n.j.t.i@gmail.com>
 * Copyright © 2017
 */
public class NextEventAdapter extends RecyclerView.Adapter<NextEventAdapter.ViewHolder>
{


    private List<Result> results = new ArrayList<>();
    private Activity activity;
    private NextEventAdapterEvent event;

    public NextEventAdapter(Activity activity, NextEventAdapterEvent event, List<Result> results)
    {
        this.activity = activity;
        this.event = event;
        this.results = results;
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.next_event_item, parent, false));
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {

        /*if (position % 2 == 0)
        {
            holder.imgBackground.setImageDrawable(activity.getResources().getDrawable(R.drawable.test_event_1));
        } else
        {
            holder.imgBackground.setImageDrawable(activity.getResources().getDrawable(R.drawable.test_event_2));

        }*/
        holder.txtTitle.setText(" ◄ "+results.get(position).getTitle() );


        try
        {
            String gorgianDate = getDate(results.get(position).getCreateDate().longValue());
            //  private String getDate(Date d)
            //  {
            //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
           // String date = dateFormat.format(gorgianDate);  // formatted date in string
            String[] splitsDate = gorgianDate.split("-");

            PersianDate persianDate = new PersianDate();
            persianDate.setGrgYear(Integer.valueOf(splitsDate[0]));
            persianDate.setGrgMonth(Integer.valueOf(splitsDate[1]));
            persianDate.setGrgDay(Integer.valueOf(splitsDate[2]));

            PersianDateFormat pdformater1 = new PersianDateFormat("Y-m-d-H:i");
            pdformater1.format(persianDate);//1396/05/20 15:21

            gorgianDate = String.valueOf(pdformater1.format(persianDate));//1396/05/20

            String[] splitsDateP = gorgianDate.split("-");

            PersianCalendar mPersianCalendar = new PersianCalendar();
             mPersianCalendar.setPersianDate(Integer.valueOf(splitsDateP[0]),Integer.valueOf(splitsDateP[1]), Integer.valueOf(splitsDateP[2]));

           // holder.txtDate.setText(mPersianCalendar.getPersianLongDate()+" - "+splitsDateP[3]);
            holder.txtDate.setText(persianDate.toString());
            persianDate.getDayInYear();
            persianDate.getMonthDays();
        } catch (Exception e)
        {
            e.getMessage();
        }


        //return date;
        //  }


        holder.txtDesc.setText(results.get(position).getDescription() + "");
        holder.txtTeacher.setText(results.get(position).getTeacher() + "");
        holder.imgBackground.setImageDrawable(activity.getResources().getDrawable(R.drawable.test_event_2));
        try
        {
            Picasso.with(activity).load(results.get(position).getImage()).into(holder.imgBackground);

            //setImageBackground(holder.imgBackground,results.get(position).getImage());
            holder.progressBar.setVisibility(View.GONE);
        } catch (Exception e1)
        {
            holder.progressBar.setVisibility(View.GONE);
            Picasso.with(activity).load(R.drawable.ic_logo_red).into(holder.imgBackground);
        }
        holder.llRoot.setOnClickListener(v ->
        {
            event.onNextEventClick(results.get(position).getId());

        });


    }

    private String getDate(long time)
    {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("yyyy-MM-dd", cal).toString();
        return date;
    }

    @Override
    public int getItemCount()
    {

        return results.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private RoundedImageView imgBackground;
        private LinearLayout llRoot;
        private TextView txtTitle, txtDate, txtDesc, txtTeacher;
        public ProgressBar progressBar;


        public ViewHolder(View v)
        {
            super(v);
            imgBackground = v.findViewById(R.id.imgBackground);
            llRoot = v.findViewById(R.id.llRoot);
            txtTitle = v.findViewById(R.id.txtTitle);
            txtDate = v.findViewById(R.id.txtDate);
            txtDesc = v.findViewById(R.id.txtDesc);
            txtTeacher = v.findViewById(R.id.txtTeacher);
            progressBar = v.findViewById(R.id.progress);

        }
    }

    public interface NextEventAdapterEvent
    {


        public void onNextEventClick(Integer id);
    }


}
