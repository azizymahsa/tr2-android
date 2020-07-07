package com.traap.traapapp.ui.adapters.pack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.getSimPackageList.response.SimContentItem;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.utilities.Utility;

import java.util.List;

/**
 * Created by Javad.Abadi on 8/13/2018.
 */
public class DetailPackAdapter extends RecyclerView.Adapter<DetailPackAdapter.ViewHolder>
{

    private List<SimContentItem> contentList;
    private Context context;
    private GetPackFromAdapterListener listener;
    private Integer operatorType;

    public DetailPackAdapter(int operatorType, List<SimContentItem> contentList, GetPackFromAdapterListener listener)
    {
        this.contentList = contentList;
        this.listener = listener;
        this.operatorType = operatorType;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType)
    {
        this.context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.recycler_view_rightel_detail_item, parent, false));
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {
        final SimContentItem item = contentList.get(position);

        holder.tvTitle.setText(item.getTitle() + " ( " + item.getTitlePackageType() + " ) ");

        holder.tvAmount.setText("مبلغ با احتساب 9 درصد مالیات " + Utility.priceFormat(String.valueOf(item.getBillAmount())) + " ریال ");
        holder.tvMainAmount.setText(Utility.priceFormat(String.valueOf(item.getAmount())) + " ریال ");

        holder.container.setOnClickListener(view -> listener.getPackDetails(operatorType, item));

        switch (operatorType)
        {
            case TrapConfig.OPERATOR_TYPE_MTN:
            {
                holder.imgArrow.setImageDrawable(context.getResources().getDrawable(R.drawable.left_arrow_irancell));
                break;
            }
            case TrapConfig.OPERATOR_TYPE_MCI:
            {
                holder.imgArrow.setImageDrawable(context.getResources().getDrawable(R.drawable.left_arrow_mci));
                break;
            }
            case TrapConfig.OPERATOR_TYPE_RIGHTELL:
            {
                holder.imgArrow.setImageDrawable(context.getResources().getDrawable(R.drawable.left_arrow_rightel));
                break;
            }
        }
    }


    @Override
    public int getItemCount()
    {
        return contentList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView tvTitle, tvAmount, tvMainAmount;
        public LinearLayout container;
        public ImageView imgArrow;

        public ViewHolder(View v)
        {
            super(v);
            tvTitle = v.findViewById(R.id.tvTitle);
            tvAmount = v.findViewById(R.id.tvAmount);
            container = v.findViewById(R.id.container);
            tvMainAmount = v.findViewById(R.id.tvMainAmount);
            imgArrow = v.findViewById(R.id.imgArrow);
        }
    }

    public interface GetPackFromAdapterListener
    {
        //        void getPackRightel(Detail o, Integer operatorType);
        void getPackDetails(int operatorType, SimContentItem simContentItem);
    }
}
