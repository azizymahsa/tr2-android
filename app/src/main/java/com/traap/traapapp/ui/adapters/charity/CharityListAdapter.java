package com.traap.traapapp.ui.adapters.charity;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.charity.CharityModel;

import java.util.List;

public class CharityListAdapter extends RecyclerView.Adapter<CharityListAdapter.ViewHolder>
{
    private OnCharityItemClickListener mItemClickListener;
    private Context mContext;
    private List<CharityModel> list;

    public CharityListAdapter(Context mContext, List<CharityModel> list, OnCharityItemClickListener listener)
    {
        this.mContext = mContext;
        this.mItemClickListener = listener;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.adapter_charity_list, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        CharityModel item = list.get(position);

        holder.tvSubTitle.setText(item.getSubTitle());

        holder.tvTitle.setText(item.getTitle());

        if (item.getImageUrl() != null)
        {
            setImageBackground(holder.progress, holder.imageView, item.getImageUrl().getThumbnailSmall());
        }
    }


    private void setImageBackground(ProgressBar progress, ImageView imageView, String link)
    {
        try
        {
            Picasso.with(mContext).load(Uri.parse(link)).into(imageView, new Callback()
            {
                @Override
                public void onSuccess()
                {
                    progress.setVisibility(View.GONE);
                }

                @Override
                public void onError()
                {
                    Picasso.with(mContext).load(R.drawable.img_failure).into(imageView);
                    progress.setVisibility(View.GONE);
                }
            });
        }
        catch (NullPointerException e)
        {
            Picasso.with(mContext).load(R.drawable.img_failure).into(imageView);
            progress.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener
    {
        private TextView tvTitle, tvSubTitle;
        private ImageView imageView;
        private ProgressBar progress;

        public ViewHolder(@NonNull View rootView)
        {
            super(rootView);

            imageView = rootView.findViewById(R.id.image);
            tvTitle = rootView.findViewById(R.id.tvTitle);
            tvSubTitle = rootView.findViewById(R.id.tvSubTitle);
            progress = rootView.findViewById(R.id.progress);

            rootView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            if (mItemClickListener != null)
            {
                mItemClickListener.onCharityItemClick(list.get(getAdapterPosition()));
            }
        }
    }

    public interface OnCharityItemClickListener
    {
        public void onCharityItemClick(CharityModel charityModel);
    }
}
