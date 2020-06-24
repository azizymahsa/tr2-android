package com.traap.traapapp.ui.adapters.playerItemList;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.formation.performanceEvaluation.main.Column;

import java.util.List;

public class PlayerItemListAdapter extends RecyclerView.Adapter<PlayerItemListAdapter.ViewHolder>
{
    private OnPlayerItemClick listener;
    private List<Column> columnList;
    private Context context;
    private Integer matchId;
    private float itemWidth;

    public PlayerItemListAdapter(Context context, Integer matchId, List<Column> columnList, float itemWidth, OnPlayerItemClick listener)
    {
        this.listener = listener;
        this.matchId = matchId;
        this.columnList = columnList;
        this.context = context;
        this.itemWidth = itemWidth;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_player_item_list, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Column item = columnList.get(position);

        if (!item.getIsActive())
        {
            holder.root.setVisibility(View.INVISIBLE);
        }
        else
        {
            holder.llTitle.setVisibility(View.VISIBLE);
            holder.tvPlayerName.setText(item.getPlayer().getPlayerFirstName() + " " + item.getPlayer().getPlayerLastName());

            setImageBackground(holder.imgPlayer, item.getPlayer().getPlayerImage(), R.drawable.ic_user);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Math.round(itemWidth), ViewGroup.LayoutParams.WRAP_CONTENT);
            holder.root.setLayoutParams(params);

            if (item.getPlayer().getIsChanged())
            {
                holder.imgChange.setVisibility(View.VISIBLE);
                Picasso.with(context).load(R.drawable.ic_exchange).into(holder.imgChange);
                holder.imgPlayer.setBorderWidth(4f);
                holder.imgPlayer.setBorderColor(ContextCompat.getColorStateList(context, R.color.textColorExchange));
                holder.tvPlayerName.setTextColor(ContextCompat.getColorStateList(context, R.color.textColorExchange));
            }
            else
            {
                holder.imgPlayer.setBorderWidth(0f);
            }

            if (item.getPlayer().getIsEvaluated())
            {
                holder.imgIsEvaluated.setVisibility(View.VISIBLE);
            }
        }
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
        return columnList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvPlayerName;
        private ImageView imgChange, imgIsEvaluated;
        private RoundedImageView imgPlayer;
        private LinearLayout root, llTitle;

        public ViewHolder(@NonNull View rootView)
        {
            super(rootView);

            tvPlayerName = rootView.findViewById(R.id.tvPlayerName);
            imgChange = rootView.findViewById(R.id.imgChange);
            imgIsEvaluated = rootView.findViewById(R.id.imgIsEvaluated);
            imgPlayer = rootView.findViewById(R.id.imgPlayer);
            llTitle = rootView.findViewById(R.id.llTitle);

            root = rootView.findViewById(R.id.root);

            rootView.setOnClickListener(v ->
            {
                if (listener != null)
                {
                    listener.onPlayerClick(matchId, columnList.get(getAdapterPosition()).getPositionId());
                }
            });
        }
    }

    public interface OnPlayerItemClick
    {
        void onPlayerClick(int matchId, int positionId);
    }
}
