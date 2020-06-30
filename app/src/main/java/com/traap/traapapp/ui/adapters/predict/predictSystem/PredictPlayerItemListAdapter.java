package com.traap.traapapp.ui.adapters.predict.predictSystem;

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
import com.traap.traapapp.apiServices.model.predict.predictSystem.getSystem.response.GetPredictSystemFromIdResponse;

import java.util.List;

public class PredictPlayerItemListAdapter extends RecyclerView.Adapter<PredictPlayerItemListAdapter.ViewHolder>
{
    private OnPositionItemClick listener;
    private List<GetPredictSystemFromIdResponse> columnList;
    private Context context;
    private int rowPosition;
    private float itemWidth;

    public PredictPlayerItemListAdapter(Context context, int rowPosition, List<GetPredictSystemFromIdResponse> columnList, float itemWidth, OnPositionItemClick listener)
    {
        this.context = context;
        this.rowPosition = rowPosition;
        this.columnList = columnList;
        this.itemWidth = itemWidth;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_predict_player_item_list, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        int columnPosition = position;
        GetPredictSystemFromIdResponse item = columnList.get(position);

        if (!item.getIsActive())
        {
            holder.root.setVisibility(View.INVISIBLE);
        }
        else
        {
            holder.llTitle.setVisibility(View.VISIBLE);
            holder.tvPlayerName.setText(item.getPlayer().getFullName());

            setImageBackground(holder.imgPlayer, item.getPlayer().getImage(), R.drawable.ic_user);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Math.round(itemWidth), ViewGroup.LayoutParams.WRAP_CONTENT);
            holder.root.setLayoutParams(params);

            holder.root.setOnClickListener(v ->
            {
                if (listener != null)
                {
                    listener.onPositionClick(
                            item.getPositionId(),
                            item.getPlayer().getPlayerId(),
                            rowPosition,
                            columnPosition
                    );
                }
            });
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
        private RoundedImageView imgPlayer;
        private LinearLayout root, llTitle;

        public ViewHolder(@NonNull View rootView)
        {
            super(rootView);

            tvPlayerName = rootView.findViewById(R.id.tvPlayerName);
            imgPlayer = rootView.findViewById(R.id.imgPlayer);
            llTitle = rootView.findViewById(R.id.llTitle);

            root = rootView.findViewById(R.id.root);

//            rootView.setOnClickListener(v ->
//            {
//                if (listener != null)
//                {
//                    listener.onPositionClick(
//                            columnList.get(getAdapterPosition()).getPositionId(),
//                            columnList.get(getAdapterPosition()).getPlayer().getPlayerId()
//                    );
//                }
//            });
        }
    }

    public interface OnPositionItemClick
    {
        void onPositionClick(int positionId, int playerId, int rowPosition, int columnPosition);
    }
}
