package com.traap.traapapp.ui.adapters.performanceEvaluation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.traap.traapapp.R;

public class PerformanceEvaluationPlayerAdapter extends RecyclerView.Adapter<PerformanceEvaluationPlayerAdapter.ViewHolder>
{
    private OnPlayerItemClick listener;

    public PerformanceEvaluationPlayerAdapter(OnPlayerItemClick listener)
    {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_player_performance_evalue, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {

    }

    @Override
    public int getItemCount()
    {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvPlayerName;
        private ImageView imgStatus;
        private RoundedImageView imgPlayer;

        public ViewHolder(@NonNull View rootView)
        {
            super(rootView);

            tvPlayerName = rootView.findViewById(R.id.tvPlayerName);
            imgStatus = rootView.findViewById(R.id.imgStatus);
            imgPlayer = rootView.findViewById(R.id.imgPlayer);

            rootView.setOnClickListener(v ->
            {
                if (listener != null)
                {
                    listener.onPlayerClick();
                }
            });
        }
    }

    public interface OnPlayerItemClick
    {
        void onPlayerClick();
    }
}
