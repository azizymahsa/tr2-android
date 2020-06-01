package com.traap.traapapp.ui.adapters.compations.questions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import androidx.recyclerview.widget.RecyclerView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.getQuestionCompat.Option;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by MahsaAzizi on 5/5/2020.
 */
public class CompationDetailRadioGroupAdapter extends RecyclerView.Adapter<CompationDetailRadioGroupAdapter.SurveyDetailViewHolder>{

    private  List<Option> optionsList;
    private Integer questionType;
   public int mSelectedItem = -1;
    private int lastSelectedPosition = -1;
    ArrayList<CompationDetailRadioGroupAdapter.SurveyDetailViewHolder> myViewHolders = new ArrayList<>();


    public CompationDetailRadioGroupAdapter(Integer questionType, List<Option> options)
    {
        this.questionType=questionType;
        this.optionsList=options;
    }

    public ArrayList<SurveyDetailViewHolder> getMyViewHolders()
    {
        return myViewHolders;
    }

    @Override
    public SurveyDetailViewHolder onCreateViewHolder(
            ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.list_item_detail_survey_radio_group,
                        viewGroup,
                        false);
        return new SurveyDetailViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(
            SurveyDetailViewHolder viewHolder, int position)
    {
        myViewHolders.add(position, viewHolder);


        Option option = optionsList.get(position);

        if (questionType == 1)
        {
            viewHolder.radioButton.setText(option.getTitle());
            viewHolder.radioButton.setVisibility(View.VISIBLE);
            viewHolder.checkBox.setVisibility(View.GONE);

            viewHolder.radioButton.setChecked(lastSelectedPosition == position);


        } else if (questionType==2)
        {/*
            viewHolder.checkBox.setText(option.getAnswerOption());
            viewHolder.checkBox.setTag(option.getId());
            viewHolder.radioButton.setVisibility(View.GONE);
            viewHolder.checkBox.setVisibility(View.VISIBLE);
            viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    optionsList.get(position).setCheck(isChecked);

                }
            });*/
        }
    }

    @Override
    public int getItemCount() {
        return
                optionsList.size();
    }

    public final  class SurveyDetailViewHolder
            extends RecyclerView.ViewHolder {


        public RadioButton radioButton;
        public CheckBox checkBox;

        public SurveyDetailViewHolder(View itemView) {
            super(itemView);

            radioButton = (RadioButton) itemView.findViewById(R.id.radioButton);
            checkBox=(CheckBox) itemView.findViewById(R.id.checkBox);


            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   lastSelectedPosition = getAdapterPosition();
                    optionsList.get(lastSelectedPosition).setCheck(true);
                    for (int i = 0; i <optionsList.size() ; i++)
                    {
                        if (i!=lastSelectedPosition){
                            optionsList.get(i).setCheck(false);

                        }
                    }

                    notifyDataSetChanged();

                }
            });
        }
    }
}
