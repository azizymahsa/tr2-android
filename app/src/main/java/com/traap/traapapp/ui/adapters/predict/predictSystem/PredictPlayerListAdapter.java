package com.traap.traapapp.ui.adapters.predict.predictSystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.formation.performanceEvaluation.getEvaluationResult.response.GetPlayerEvaluationRequestResponse;
import com.traap.traapapp.apiServices.model.predict.predictSystem.getMainPredict.PlayerItem;

import java.util.List;

public class PredictPlayerListAdapter extends RecyclerView.Adapter<PredictPlayerListAdapter.ViewHolder>
{
    private Context context;
    private List<PlayerItem> playerItemList;
    private OnPlayerSelected listener;

    public PredictPlayerListAdapter(Context context, List<PlayerItem> playerItemList, OnPlayerSelected listener)
    {
        this.context = context;
        this.playerItemList = playerItemList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_player_list_prediction, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        PlayerItem item = playerItemList.get(position);

        holder.tvPlayerName.setText(String.valueOf(item.getFullName()));
    }

    @Override
    public int getItemCount()
    {
        return playerItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvPlayerName;

        public ViewHolder(@NonNull View rootView)
        {
            super(rootView);

            tvPlayerName = rootView.findViewById(R.id.tvPlayerName);

            rootView.setOnClickListener(v ->
            {
                if (listener != null)
                {
                    listener.onPlayerSelectedFromAdapter(playerItemList.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface OnPlayerSelected
    {
        void onPlayerSelectedFromAdapter(PlayerItem playerItem);
    }
}
