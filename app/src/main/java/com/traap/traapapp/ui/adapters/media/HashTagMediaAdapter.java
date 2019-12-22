package com.traap.traapapp.ui.adapters.media;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.traap.traapapp.R;
import com.traap.traapapp.models.otherModels.mediaModel.MediaModel;

import java.util.List;

public class HashTagMediaAdapter extends RecyclerView.Adapter<HashTagMediaAdapter.ViewHolder>
{
    private List<String> list;
    private int row_index = 0;
    private View view;

    public HashTagMediaAdapter(List<String> list)
    {
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.adapter_filter_hashtag_item, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        String item = list.get(position);

        holder.tvTitle.setText(item);
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView tvTitle;
        public LinearLayout rootView;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
//            rootView = itemView.findViewById(R.id.rootView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }
    }


}
