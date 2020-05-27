package com.traap.traapapp.ui.adapters.survey;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.survey.Question;
import com.traap.traapapp.ui.fragments.survey.SurveyFragment;
import com.traap.traapapp.ui.others.CustomLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import library.android.eniac.flight.adapter.PassengerAdapter;

/**
 * Created by MahtabAzizi on 5/5/2020.
 */
public class SurveyDetailAdapter extends RecyclerView.Adapter<SurveyDetailAdapter.ViewHolder>
{
    private final SurveyFragment surveyFragment;
    private Context context;
    private List<Question> surveyDetailList;
    SurveyDetailRadioGroupAdapter adapter;
    ArrayList<SurveyDetailAdapter.ViewHolder> myViewHolders = new ArrayList<>();


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
    public ArrayList<ViewHolder> getMyViewHolders()
    {
        return myViewHolders;
    }

    @Override
    public void onBindViewHolder(final SurveyDetailAdapter.ViewHolder holder, final int position)
    {
        myViewHolders.add(position, holder);


        Question info = surveyDetailList.get(position);



        if (info.isMandatory()){

         //   holder.tvQuestion.setText(info.getQuery()+"<font color=red>" + " *");
        }else {
            holder.tvQuestion.setText(info.getQuery());

        }

       /* String styledText = " <font color='red'>تراپ</font>، اپلیکیشن هواداران باشگاه تراکتور";
        tvTraapTitle.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);*/
      /*  myTextView.setText(Html.fromHtml(text + "<font color=white>" + CepVizyon.getPhoneCode() + "</font><br><br>"
                + getText(R.string.currentversion) + CepVizyon.getLicenseText()));*/

        if(info.getQuestionType()==1||info.getQuestionType()==2){
            adapter = new SurveyDetailRadioGroupAdapter(info.getQuestionType(),info.getOptions());
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