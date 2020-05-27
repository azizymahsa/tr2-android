package com.traap.traapapp.ui.adapters.survey;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.survey.Option;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MahtabAzizi on 5/5/2020.
 */
public class SurveyDetailRadioGroupAdapter extends RecyclerView.Adapter<SurveyDetailRadioGroupAdapter.SurveyDetailViewHolder>{

    private  List<Option> optionsList;
    private Integer questionType;
   public int mSelectedItem = -1;
    private int lastSelectedPosition = -1;
    ArrayList<SurveyDetailRadioGroupAdapter.SurveyDetailViewHolder> myViewHolders = new ArrayList<>();


    public SurveyDetailRadioGroupAdapter(Integer questionType, List<Option> options)
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
            viewHolder.radioButton.setText(option.getAnswerOption());
            viewHolder.radioButton.setVisibility(View.VISIBLE);
            viewHolder.checkBox.setVisibility(View.GONE);

            viewHolder.radioButton.setChecked(lastSelectedPosition == position);


        } else if (questionType==2)
        {
            viewHolder.checkBox.setText(option.getAnswerOption());
            viewHolder.radioButton.setVisibility(View.GONE);
            viewHolder.checkBox.setVisibility(View.VISIBLE);
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
                    notifyDataSetChanged();

                }
            });
        }
    }
}
