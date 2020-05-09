package com.traap.traapapp.ui.adapters.survey;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.survey.Question;
import com.traap.traapapp.ui.fragments.survey.SurveyFragment;
import com.traap.traapapp.ui.others.CustomLinearLayoutManager;

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
    SurveyDetailRadioGroupAdapter adapter;



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
                .inflate(R.layout.list_item_detail_survey, parent, false),context);
    }


    @Override
    public void onBindViewHolder(final SurveyDetailAdapter.ViewHolder holder, final int position)
    {
        Question info = surveyDetailList.get(position);


        adapter = new SurveyDetailRadioGroupAdapter();
        holder.rvQuestionRadioGroup.setAdapter(adapter);

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

        public ViewHolder(View v, Context context)
        {
            super(v);
            tvQuestion = v.findViewById(R.id.tvQestion);
            rvQuestionRadioGroup = v.findViewById(R.id.rvQuestionRadioGroup);
            rvQuestionCheckBox = v.findViewById(R.id.rvQuestionCheckBox);

            RecyclerView.LayoutManager layoutManager = new CustomLinearLayoutManager(context);
            rvQuestionRadioGroup.setLayoutManager(layoutManager);

            rvQuestionRadioGroup.setHasFixedSize(false);
            rvQuestionRadioGroup.setLayoutManager(layoutManager);

        }
    }

}