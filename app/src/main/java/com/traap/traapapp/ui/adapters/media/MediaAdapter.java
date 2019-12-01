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

import java.util.List;

import com.traap.traapapp.R;
import com.traap.traapapp.models.otherModels.mediaModel.MediaModel;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.ViewHolder>
{
    private OnItemAllMenuClickListener mItemClickListener;
    private Context mContext;
    private List<MediaModel> list;
    private int row_index = 0;
    private View view;

    public MediaAdapter(Context mContext, List<MediaModel> list, OnItemAllMenuClickListener mItemClickListener)
    {
        this.mContext = mContext;
        this.mItemClickListener = mItemClickListener;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.adapter_media_item, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        MediaModel item = list.get(position);

        holder.tvTitle.setText(item.getTitle());
        if (row_index == 0)
        {
            mItemClickListener.OnItemAllMenuClick(view, list.get(0).getId());
        }
        if (row_index == position)
        {
            try
            {
                Picasso.with(mContext).load(item.getIconDrawableSelected()).into(holder.image, new Callback()
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
        }
        else
        {
            try
            {
                Picasso.with(mContext).load(item.getIconDrawable()).into(holder.image, new Callback()
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
            catch (Exception e1)
            {
                holder.progressBar.setVisibility(View.GONE);
                Picasso.with(mContext).load(R.drawable.ic_logo_red).into(holder.image);
            }
        }
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
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

            rootView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view)
        {
            if (mItemClickListener != null)
            {
                row_index = getAdapterPosition();
                mItemClickListener.OnItemAllMenuClick(view, list.get(getAdapterPosition()).getId());
                notifyDataSetChanged();
            }
        }
    }


    public interface OnItemAllMenuClickListener
    {
        public void OnItemAllMenuClick(View view, Integer id);
    }

//    public void SetOnItemClickListener(final OnItemAllMenuClickListener mItemClickListener)
//    {
//        this.mItemClickListener = mItemClickListener;
//    }

}
