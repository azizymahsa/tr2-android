package com.traap.traapapp.ui.adapters.survey;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.survey.Question;
import com.traap.traapapp.ui.adapters.spectatorList.SpectatorListAdapter;
import com.traap.traapapp.ui.fragments.survey.SurveyFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MahtabAzizi on 5/5/2020.
 */
public class SurveyDetailAdapter extends RecyclerView.Adapter<SurveyDetailAdapter.ViewHolder>
{
    private final SurveyFragment surveyFragment;
    private Context context;
    private List<Question> surveyDetailList;
    MedicinesInPrescAdapter adapter;



    public SurveyDetailAdapter(ArrayList<Question> surveyDetailList, SurveyFragment surveyFragment)
    {
        this.surveyDetailList = surveyDetailList;

        this.surveyFragment=surveyFragment;
    }


    @Override
    public SurveyDetailAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType)
    {
        this.context = parent.getContext();
        return new SurveyDetailAdapter.ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.list_item_detail_survey, parent, false));
    }


    @Override
    public void onBindViewHolder(final SurveyDetailAdapter.ViewHolder holder, final int position)
    {
        Question info = surveyDetailList.get(position);


        adapter = new MedicinesInPrescAdapter(model.getLstproduct());
        viewHolder.lstMedicines.setAdapter(adapter);
       // holder.cbSelectSpectator.setChecked(info.getIsMandatory());
       // holder.cbSelectSpectator.setEnabled(false);





    }



    @Override
    public int getItemCount()
    {

        return surveyDetailList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private RecyclerView rvQuestionRadioGroup,rvQuestionCheckBox;
        public TextView tvQuestion;

        public ViewHolder(View v)
        {
            super(v);
            tvQuestion = v.findViewById(R.id.tvQestion);
            rvQuestionRadioGroup = v.findViewById(R.id.rvQuestionRadioGroup);
            rvQuestionCheckBox = v.findViewById(R.id.rvQuestionCheckBox);

            MyLinearLayoutManager layoutManager = new MyLinearLayoutManager(itemView.getContext(), LinearLayoutManager.VERTICAL,false);
            rvQuestionRadioGroup.setHasFixedSize(false);
            rvQuestionRadioGroup.setLayoutManager(layoutManager);

        }
    }

}