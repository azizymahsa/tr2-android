package com.traap.traapapp.ui.adapters.mainServiceModel;

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
import com.traap.traapapp.models.otherModels.mainService.MainServiceModelItem;
import com.traap.traapapp.ui.fragments.main.MainFragment;

import java.util.List;

public class BanerServiceModelAdapter extends RecyclerView.Adapter<BanerServiceModelAdapter.ViewHolder>
{
    private OnItemClickListener mItemClickListener;
    private Context mContext;
    private List<MainServiceModelItem> list;

    public BanerServiceModelAdapter(Context mContext, List<MainServiceModelItem> list, OnItemClickListener mItemClickListener)
    {
        this.mItemClickListener = mItemClickListener;
        this.mContext = mContext;
        this.list = list;
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.adapter_baner_service_model, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        MainServiceModelItem item = list.get(position);

        holder.tvTitle.setText(item.getTitle());

        try
        {
            Picasso.with(mContext).load(item.getImageLink().replace(" ","%20")).into(holder.image, new Callback()
            {
                @Override
                public void onSuccess()
                {
                    holder.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError()
                {
                    holder.progressBar.setVisibility(View.GONE);
                    Picasso.with(mContext).load(R.drawable.ic_logo_red).into(holder.image);
                }
            });
        }
        catch (Exception e)
        {
            holder.progressBar.setVisibility(View.GONE);
            Picasso.with(mContext).load(R.drawable.ic_logo_red).into(holder.image);
        }

        //------------test--------------------
//        holder.progressBar.setVisibility(View.GONE);
//        Picasso.with(mContext).load(R.drawable.ic_logo_red).into(holder.image);
        //------------test--------------------


    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener
    {
        public TextView tvTitle;
        public ImageView image;
        public ProgressBar progressBar;
        public LinearLayout rootView;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            rootView = itemView.findViewById(R.id.rootView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            image = itemView.findViewById(R.id.image);
            progressBar = itemView.findViewById(R.id.progress);

            rootView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            if (mItemClickListener != null)
            {
                mItemClickListener.onBannerItemClick(view,  list.get(getAdapterPosition()).getId(),list.get(getAdapterPosition()).getLogin_url(),list.get(getAdapterPosition()).getBase_url());
            }
        }
    }


    public interface OnItemClickListener
    {
        public void onBannerItemClick(View view, Integer id, String loginUrl, String baseUrl);
    }

//    public void SetOnItemCheckedChangeListener(final OnItemClickListener mItemClickListener)
//    {
//        this.mItemClickListener = mItemClickListener;
//    }

}
