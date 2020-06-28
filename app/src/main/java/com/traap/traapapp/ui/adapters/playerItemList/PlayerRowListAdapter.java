package com.traap.traapapp.ui.adapters.playerItemList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.formation.performanceEvaluation.main.RowItem;
import com.traap.traapapp.utilities.Logger;

import java.util.List;

public class PlayerRowListAdapter extends RecyclerView.Adapter<PlayerRowListAdapter.ViewHolder>
{
    private PlayerItemListAdapter.OnPlayerItemClick listener;
    private NotifyRowHeight notifier;
    private Context context;
    private List<RowItem> rowList;
    private float itemWidth;
    private Integer matchId;
    private Integer[] heightItems = { 0, 0, 0, 0, 0, 0, 0 };

    public PlayerRowListAdapter(Context context, Integer matchId, List<RowItem> rowList, float itemWidth, PlayerItemListAdapter.OnPlayerItemClick listener)
    {
        this.context = context;
        this.matchId = matchId;
        this.rowList = rowList;
        this.itemWidth = itemWidth;
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
        RowItem item = rowList.get(position);

        holder.recyclerView.setLayoutManager(new GridLayoutManager(context, 5, RecyclerView.VERTICAL, false));

        holder.recyclerView.setAdapter(new PlayerItemListAdapter(context, matchId, item.getColumnList(), itemWidth, listener));
        holder.recyclerView.setNestedScrollingEnabled(false);

        //-------------------------------get dimension------------------------------------
        ViewTreeObserver vto = holder.recyclerView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {
                holder.recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                int width = holder.recyclerView.getMeasuredWidth();
                int height = holder.recyclerView.getMeasuredHeight();

                Logger.e("--Row dimension--", "width: " + width + ", height: " + height + " #Position: " + position);
                heightItems[position] = height;
//                notifier.onNotifyRowHeight(heightItems);
            }
        });
        //-------------------------------get dimension------------------------------------
    }

    @Override
    public int getItemCount()
    {
        return rowList.size();
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

    public interface NotifyRowHeight
    {
        void onNotifyRowHeight(Integer[] heightItems);
    }

    public void GetAllItemHeight(final NotifyRowHeight notifier)
    {
        this.notifier = notifier;
    }
}
