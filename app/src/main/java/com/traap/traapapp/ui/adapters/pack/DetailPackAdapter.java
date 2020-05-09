package com.traap.traapapp.ui.adapters.pack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.getRightelPack.response.Detail;
import com.traap.traapapp.utilities.Utility;

/**
 * Created by Javad.Abadi on 8/13/2018.
 */
public class DetailPackAdapter extends RecyclerView.Adapter<DetailPackAdapter.ViewHolder>
{

    private final List<Detail> data;
    private Context context;
    private GetPackInAdapter getPackInAdapter;
    private Integer operatorType;

    public DetailPackAdapter(final List<Detail> data, GetPackInAdapter getPackInAdapter,Integer operatorType)
    {
        this.data = data;
        this.getPackInAdapter = getPackInAdapter;
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

        final Detail item = data.get(position);
        String type = "";
/*        if (item.getPackageType() != null) {
            if (item.getPackageType().equals("2"))
                type = "دائمی";

            else
                type = "اعتباری";*/

        holder.tvTitle.setText(item.getTitle() + " ( " + item.getTitlePackageType() + " ) ");

   /*     } else {
            holder.tvTitle.setText(item.getTitle());

        }*/


        holder.tvAmount.setText("مبلغ با احتساب 9 درصد مالیات " + Utility.priceFormat(item.getBillAmount()) + " ریال ");
        holder.tvMainAmount.setText(Utility.priceFormat(item.getAmount()) + " ریال ");
        holder.container.setOnClickListener(view -> {
            getPackInAdapter.getPackRightel(item,operatorType);
        });

        if (operatorType==1){
            holder.imgArrow.setImageDrawable(context.getResources().getDrawable(R.drawable.left_arrow_irancell));


        }else if (operatorType==2){
            holder.imgArrow.setImageDrawable(context.getResources().getDrawable(R.drawable.left_arrow_mci));

        }else{
            holder.imgArrow.setImageDrawable(context.getResources().getDrawable(R.drawable.left_arrow_rightel));

        }

    }


    @Override
    public int getItemCount()
    {
        return data.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView tvTitle, tvAmount,tvMainAmount;
        public LinearLayout container;
        public ImageView imgArrow;


        public ViewHolder(View v)
        {
            super(v);
            tvTitle = v.findViewById(R.id.tvTitle);
            tvAmount = v.findViewById(R.id.tvAmount);
            container = v.findViewById(R.id.container);
            tvMainAmount=v.findViewById(R.id.tvMainAmount);
            imgArrow=v.findViewById(R.id.imgArrow);
        }
    }

    public interface GetPackInAdapter
    {
        void getPackRightel(Detail o,Integer operatorType);
    }


}
