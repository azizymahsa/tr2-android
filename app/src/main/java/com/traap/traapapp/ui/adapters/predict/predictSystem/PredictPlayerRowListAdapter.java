package com.traap.traapapp.ui.adapters.predict.predictSystem;

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
import com.traap.traapapp.apiServices.model.predict.predictSystem.getSystem.response.GetPredictSystemFromIdResponse;
import com.traap.traapapp.utilities.Logger;

import java.util.List;

public class PredictPlayerRowListAdapter extends RecyclerView.Adapter<PredictPlayerRowListAdapter.ViewHolder>
{
    private PredictPlayerItemListAdapter.OnPositionItemClick listener;
    private NotifyRowHeight notifier;
    private Context context;
    private List<List<GetPredictSystemFromIdResponse>> rowList;
    private int rowPosition = 0;
    private float itemWidth;
    private Integer matchId;
    private int defaultFormationId;
    private Integer[] heightItems = { 0, 0, 0, 0, 0, 0, 0 };

    public PredictPlayerRowListAdapter(Context context, int defaultFormationId, Integer matchId, List<List<GetPredictSystemFromIdResponse>> rowList, float itemWidth,
                                       PredictPlayerItemListAdapter.OnPositionItemClick listener)
    {
        this.context = context;
        this.matchId = matchId;
        this.rowList = rowList;
        this.itemWidth = itemWidth;
        this.defaultFormationId = defaultFormationId;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_predict_player_row_list, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        rowPosition = position;
        List<GetPredictSystemFromIdResponse> item = rowList.get(position);

        holder.recyclerView.setLayoutManager(new GridLayoutManager(context, 5, RecyclerView.VERTICAL, false));

        holder.recyclerView.setAdapter(new PredictPlayerItemListAdapter(context, defaultFormationId, rowPosition, item, itemWidth, listener));
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

//                Logger.e("--Row dimension--", "width: " + width + ", height: " + height + " #Position: " + position);
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
