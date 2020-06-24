package com.traap.traapapp.ui.adapters.performanceEvaluation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.formation.performanceEvaluation.getEvaluationQuestion.GetPlayerEvaluationQuestionResponse;

import java.util.List;

public class PlayerSetEvaluationAdapter extends RecyclerView.Adapter<PlayerSetEvaluationAdapter.ViewHolder>
{
    private Context context;
    private onEValueCheckedChangeListener listener;
    private List<GetPlayerEvaluationQuestionResponse> questionList;

    public PlayerSetEvaluationAdapter(Context context, List<GetPlayerEvaluationQuestionResponse> questionList, onEValueCheckedChangeListener listener)
    {
        this.context = context;
        this.listener = listener;
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_player_set_evaluation, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        GetPlayerEvaluationQuestionResponse question = questionList.get(position);
        holder.tvEvaluationText.setText(question.getQuestionTitle());

        if (position % 2 == 0)
        {
            holder.tvEvaluationText.setTextColor(ContextCompat.getColor(context, R.color.textColorSecondary));
        }
        else
        {
            holder.tvEvaluationText.setTextColor(ContextCompat.getColor(context, R.color.textColorSubTitle));
        }
//        try
//        {
//            if (position == questionList.size() - 1 || position == questionList.size() - 2)
//            {
//                holder.tvEvaluationText.setTextColor(ContextCompat.getColor(context, R.color.textColorPrimary));
//            }
//        }
//        catch (Exception e)
//        {
//
//        }

        holder.seekBar.setIndicatorTextDecimalFormat("0");
        holder.seekBar.setRange(1f, question.getRange());
        holder.seekBar.setProgress(1f);
//        holder.seekBar.setIndicatorTextStringFormat("0");
        //rangeLeft = rangeBar.getLeftSeekBar().getProgress();
        holder.seekBar.setOnRangeChangedListener(new OnRangeChangedListener()
        {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser)
            {
                int left = (int) leftValue;
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft)
            {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft)
            {

            }
        });
//        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
//        {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId)
//            {
//
//                switch (group.getCheckedRadioButtonId())
//                {
//                    case R.id.checkbox1:
//                    {
//                        listener.onEValueSelected(1, position);
//                        break;
//                    }
//                    case R.id.checkbox2:
//                    {
//                        listener.onEValueSelected(2, position);
//                        break;
//                    }
//                    case R.id.checkbox3:
//                    {
//                        listener.onEValueSelected(3, position);
//                        break;
//                    }
//                    case R.id.checkbox4:
//                    {
//                        listener.onEValueSelected(4, position);
//                        break;
//                    }
//                    case R.id.checkbox5:
//                    {
//                        listener.onEValueSelected(5, position);
//                        break;
//                    }
//
//                }
//            }
//        });

    }

    @Override
    public int getItemCount()
    {
        return questionList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvEvaluationText;
        private RadioGroup radioGroup;
        private RangeSeekBar seekBar;

        public ViewHolder(@NonNull View rootView)
        {
            super(rootView);

            seekBar = rootView.findViewById(R.id.seekBar);
            radioGroup = rootView.findViewById(R.id.radioGroup);
            tvEvaluationText = rootView.findViewById(R.id.tvEvaluationText);
        }
    }

    public interface onEValueCheckedChangeListener
    {
        void onEValueSelected(int value, int position);
    }
}
