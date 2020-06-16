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

import com.traap.traapapp.R;

public class PlayerSetEvaluationAdapter extends RecyclerView.Adapter<PlayerSetEvaluationAdapter.ViewHolder>
{
    private Context context;
    private onEValueCheckedChangeListener listener;

    public PlayerSetEvaluationAdapter(Context context, onEValueCheckedChangeListener listener)
    {
        this.context = context;
        this.listener = listener;
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
        if (position % 2 == 0)
        {
            holder.tvEvaluationText.setTextColor(ContextCompat.getColor(context, R.color.textColorSecondary));
        }
        else
        {
            holder.tvEvaluationText.setTextColor(ContextCompat.getColor(context, R.color.textColorSubTitle));
        }

        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {

                switch (group.getCheckedRadioButtonId())
                {
                    case R.id.checkbox1:
                    {
                        listener.onEValueSelected(1, position);
                        break;
                    }
                    case R.id.checkbox2:
                    {
                        listener.onEValueSelected(2, position);
                        break;
                    }
                    case R.id.checkbox3:
                    {
                        listener.onEValueSelected(3, position);
                        break;
                    }
                    case R.id.checkbox4:
                    {
                        listener.onEValueSelected(4, position);
                        break;
                    }
                    case R.id.checkbox5:
                    {
                        listener.onEValueSelected(5, position);
                        break;
                    }

                }
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvEvaluationText;
        private RadioGroup radioGroup;

        public ViewHolder(@NonNull View rootView)
        {
            super(rootView);

            radioGroup = rootView.findViewById(R.id.radioGroup);
            tvEvaluationText = rootView.findViewById(R.id.tvEvaluationText);
        }
    }

    public interface onEValueCheckedChangeListener
    {
        void onEValueSelected(int value, int position);
    }
}
