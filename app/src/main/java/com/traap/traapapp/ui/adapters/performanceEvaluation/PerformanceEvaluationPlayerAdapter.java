package com.traap.traapapp.ui.adapters.performanceEvaluation;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.formation.performanceEvaluation.playerSubstitution.response.PerformanceEvaluationPlayerSubstitutionResponse;

import java.util.List;

public class PerformanceEvaluationPlayerAdapter extends RecyclerView.Adapter<PerformanceEvaluationPlayerAdapter.ViewHolder>
{
    private OnPlayerItemClick listener;
    private List<PerformanceEvaluationPlayerSubstitutionResponse> playerList;
    private Context context;
    private int matchId;

    public PerformanceEvaluationPlayerAdapter(OnPlayerItemClick listener, Context context, int matchId, List<PerformanceEvaluationPlayerSubstitutionResponse> playerList)
    {
        this.listener = listener;
        this.playerList = playerList;
        this.context = context;
        this.matchId = matchId;
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
        PerformanceEvaluationPlayerSubstitutionResponse player = playerList.get(position);

        setImageBackground(holder.imgPlayer, player.getPlayerItem().getPlayerImage(), R.drawable.ic_user);

        holder.tvPlayerName.setText(player.getPlayerItem().getPlayerFirstName() + " " + player.getPlayerItem().getPlayerLastName());

        String name = player.getPlayerItem().getPlayerFirstName() + " " + player.getPlayerItem().getPlayerLastName();
        holder.root.setOnClickListener(v ->
        {
            if (player.getPlayerItem().getIsEvaluated())
            {
                listener.onPlayerShowEvaluatedResult(matchId, player.getPlayerItem().getId(), name, player.getPlayerItem().getPlayerImage());
            }
            else
            {
                listener.onPlayerSetEvaluation(matchId, player.getPlayerItem().getId(), name, player.getPlayerItem().getPlayerImage());
            }
        });
    }

    private void setImageBackground(ImageView imageView, String link, int placeHolder)
    {
        try
        {
            Picasso.with(context).load(Uri.parse(link)).into(imageView, new Callback()
            {
                @Override
                public void onSuccess()
                {
                }

                @Override
                public void onError()
                {
                    Picasso.with(context).load(placeHolder).into(imageView);
                }
            });
        }
        catch (NullPointerException e)
        {
            Picasso.with(context).load(placeHolder).into(imageView);
        }
    }

    @Override
    public int getItemCount()
    {
        return playerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvPlayerName;
        private RoundedImageView imgPlayer;
        private LinearLayout root;

        public ViewHolder(@NonNull View rootView)
        {
            super(rootView);

            tvPlayerName = rootView.findViewById(R.id.tvPlayerName);
            imgPlayer = rootView.findViewById(R.id.imgPlayer);
            root = rootView.findViewById(R.id.root);

//            rootView.setOnClickListener(v ->
//            {
//                if (listener != null)
//                {
//                    listener.onPlayerClick();
//                }
//            });
        }
    }

    public interface OnPlayerItemClick
    {
        void onPlayerShowEvaluatedResult(int matchId, int playerId, String name, String imageURL);

        void onPlayerSetEvaluation(int matchId, int playerId, String name, String imageURL);
    }
}
