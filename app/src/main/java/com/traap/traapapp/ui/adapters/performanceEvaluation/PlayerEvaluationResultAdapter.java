package com.traap.traapapp.ui.adapters.performanceEvaluation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.traap.traapapp.R;

public class PlayerEvaluationResultAdapter extends RecyclerView.Adapter<PlayerEvaluationResultAdapter.ViewHolder>
{
    private Context context;

    public PlayerEvaluationResultAdapter(Context context)
    {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_player_evaluation_result, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {


        if (position % 2 == 0)
        {
            holder.tvAvgEvaluation.setTextColor(ContextCompat.getColor(context, R.color.textColorSecondary));
            holder.tvEvaluationIndex.setTextColor(ContextCompat.getColor(context, R.color.textColorSecondary));
            holder.tvMyEvaluation.setTextColor(ContextCompat.getColor(context, R.color.textColorSecondary));
        }
        else
        {
            holder.tvAvgEvaluation.setTextColor(ContextCompat.getColor(context, R.color.textColorSubTitle));
            holder.tvEvaluationIndex.setTextColor(ContextCompat.getColor(context, R.color.textColorSubTitle));
            holder.tvMyEvaluation.setTextColor(ContextCompat.getColor(context, R.color.textColorSubTitle));
        }

//        if ((position == size - 1) || (position == size - 2))
//        {
//            holder.tvAvgEvaluation.setTextColor(ContextCompat.getColor(context, R.color.textColorPrimary));
//            holder.tvEvaluationIndex.setTextColor(ContextCompat.getColor(context, R.color.textColorPrimary));
//            holder.tvMyEvaluation.setTextColor(ContextCompat.getColor(context, R.color.textColorPrimary));
//        }



    }

    @Override
    public int getItemCount()
    {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvAvgEvaluation, tvMyEvaluation, tvEvaluationIndex;

        public ViewHolder(@NonNull View rootView)
        {
            super(rootView);

            tvAvgEvaluation = rootView.findViewById(R.id.tvAvgEvaluation);
            tvMyEvaluation = rootView.findViewById(R.id.tvMyEvaluation);
            tvEvaluationIndex = rootView.findViewById(R.id.tvEvaluationIndex);
        }
    }
}
