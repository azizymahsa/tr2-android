package com.traap.traapapp.ui.adapters.playerItemList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.traap.traapapp.R;

public class PlayerRowListAdapter extends RecyclerView.Adapter<PlayerRowListAdapter.ViewHolder>
{
    private PlayerItemListAdapter.OnPlayerItemClick listener;
    private Context context;

    public PlayerRowListAdapter(Context context, PlayerItemListAdapter.OnPlayerItemClick listener)
    {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_player_row_list, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {

        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true));

        holder.recyclerView.setAdapter(new PlayerItemListAdapter(listener));
    }

    @Override
    public int getItemCount()
    {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private RecyclerView recyclerView;

        public ViewHolder(@NonNull View rootView)
        {
            super(rootView);

            recyclerView = rootView.findViewById(R.id.recyclerView);
        }
    }
}
