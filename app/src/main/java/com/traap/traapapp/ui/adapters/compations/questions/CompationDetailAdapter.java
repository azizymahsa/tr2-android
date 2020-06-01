package com.traap.traapapp.ui.adapters.compations.questions;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.getQuestionCompat.Question;
import com.traap.traapapp.ui.fragments.competitions.QuestionCompationFragment;

import com.traap.traapapp.ui.others.CustomLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MahsaAzizi on 5/5/2020.
 */
public class CompationDetailAdapter extends RecyclerView.Adapter<CompationDetailAdapter.ViewHolder>
{
    private final QuestionCompationFragment surveyFragment;
    private Context context;
    private List<Question> surveyDetailList;
    CompationDetailRadioGroupAdapter adapter;
    ArrayList<CompationDetailAdapter.ViewHolder> myViewHolders = new ArrayList<>();


    public CompationDetailAdapter(ArrayList<Question> surveyDetailList, QuestionCompationFragment surveyFragment)
    {
        this.surveyDetailList = surveyDetailList;

        this.surveyFragment=surveyFragment;
    }




    @Override
    public CompationDetailAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType)
    {
        this.context = parent.getContext();
        return new CompationDetailAdapter.ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.list_item_detail_survey, parent, false),context);
    }
    public ArrayList<ViewHolder> getMyViewHolders()
    {
        return myViewHolders;
    }

    @Override
    public void onBindViewHolder(final CompationDetailAdapter.ViewHolder holder, final int position)
    {
        myViewHolders.add(position, holder);


        Question info = surveyDetailList.get(position);



     /*   if (info.isMandatory()){

            String styledText = info.getQuery()+" <font color='red'>*</font>";
            holder.tvQuestion.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);

        }else {*/
            holder.tvQuestion.setText(info.getTitle()+"");

       // }

        if(info!= null){
            adapter = new CompationDetailRadioGroupAdapter(1,info.getOptions());
            holder.rvQuestionRadioGroup.setAdapter(adapter);
            holder.rvQuestionRadioGroup.setVisibility(View.VISIBLE);
            holder.etAnswer.setVisibility(View.GONE);
        }else {
            holder.rvQuestionRadioGroup.setVisibility(View.GONE);
            holder.etAnswer.setVisibility(View.VISIBLE);
        }


    }



    @Override
    public int getItemCount()
    {

        return surveyDetailList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public EditText etAnswer;
        public RecyclerView rvQuestionRadioGroup;
        public TextView tvQuestion;

        public ViewHolder(View v, Context context)
        {
            super(v);
            tvQuestion = v.findViewById(R.id.tvQestion);
            rvQuestionRadioGroup = v.findViewById(R.id.rvQuestionRadioGroup);
            etAnswer=v.findViewById(R.id.etAnswer);

            RecyclerView.LayoutManager layoutManager = new CustomLinearLayoutManager(context);
            rvQuestionRadioGroup.setLayoutManager(layoutManager);

            rvQuestionRadioGroup.setHasFixedSize(false);
            rvQuestionRadioGroup.setLayoutManager(layoutManager);

        }
    }

}