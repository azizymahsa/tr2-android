package com.traap.traapapp.ui.adapters.allMenu;

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

import java.util.List;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.allService.response.SubMenu;
import com.traap.traapapp.models.otherModels.mainService.MainServiceModelItem;

public class AllMenuServiceModelAdapter extends RecyclerView.Adapter<AllMenuServiceModelAdapter.ViewHolder>
{
    private AllMenuServiceModelAdapter.OnItemAllMenuClickListener mItemClickListener;
    private Context mContext;
    private List<MainServiceModelItem> list;
    private int row_index = 0;
    private View view;

    public AllMenuServiceModelAdapter(Context mContext, List<MainServiceModelItem> list, OnItemAllMenuClickListener mItemClickListener)
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
                R.layout.adapter_main_service_model, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        MainServiceModelItem item = list.get(position);

        holder.tvTitle.setText(item.getTitle());
        holder.rootView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                row_index = position;
                mItemClickListener.OnItemAllMenuClick(view, item.getId(), item.getSubMenu());
                notifyDataSetChanged();

            }
        });
        if (row_index == 0)
            mItemClickListener.OnItemAllMenuClick(view, list.get(0).getId(), list.get(0).getSubMenu());
        if (row_index == position)
        {
            try
            {
                Picasso.with(mContext).load(item.getLogo_selected()).into(holder.image, new Callback()
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
        } else
        {
            try
            {
                Picasso.with(mContext).load(item.getImageLink()).into(holder.image, new Callback()
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
            } catch (Exception e1)
            {
                holder.progressBar.setVisibility(View.GONE);
                Picasso.with(mContext).load(R.drawable.ic_logo_red).into(holder.image);
            }
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


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView tvTitle;
        public ImageView image;
        public ProgressBar progressBar;
        public LinearLayout rootView;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            view = itemView;
            rootView = itemView.findViewById(R.id.rootView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            image = itemView.findViewById(R.id.image);
            progressBar = itemView.findViewById(R.id.progress);


        }

      /*  @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {

            }
        }*/
    }


    public interface OnItemAllMenuClickListener
    {
        public void OnItemAllMenuClick(View view, Integer id, List<SubMenu> list);
    }

//    public void SetOnItemCheckedChangeListener(final OnItemClickListener mItemClickListener)
//    {
//        this.mItemClickListener = mItemClickListener;
//    }

}
