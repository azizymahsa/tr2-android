package com.traap.traapapp.ui.adapters.lotteryPrimary;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.lottery.Winner;

import java.util.List;

public class LotteryHistoryWinnersAdapter extends RecyclerView.Adapter<LotteryHistoryWinnersAdapter.ViewHolder>
{
    private Context mContext;
    private List<Winner> list;

    public LotteryHistoryWinnersAdapter(Context mContext, List<Winner> list)
    {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.adapter_lottery_winners, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Winner item = list.get(position);

        holder.tvFullName.setText(item.getFirstName() + " " + item.getLastName());

        holder.tvMobile.setText(item.getMobile());

        holder.tvLotteryTitle.setText(item.getDescription());

        setImageBackground(holder.imageView, "");
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    private void setImageBackground(ImageView imageView, String link)
    {
        try
        {
            Picasso.with(mContext).load(Uri.parse(link)).into(imageView, new Callback()
            {
                @Override
                public void onSuccess()
                {
                }

                @Override
                public void onError()
                {
                    Picasso.with(mContext).load(R.drawable.img_failure).into(imageView);
                }
            });
        }
        catch (NullPointerException e)
        {
            Picasso.with(mContext).load(R.drawable.img_failure).into(imageView);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvLotteryTitle, tvMobile, tvFullName;
        private ImageView imageView;
        private View  viewBottom;

        public ViewHolder(@NonNull View rootView)
        {
            super(rootView);

            viewBottom = rootView.findViewById(R.id.viewBottom);

            tvLotteryTitle = rootView.findViewById(R.id.tvLotteryTitle);
            tvMobile = rootView.findViewById(R.id.tvMobile);
            tvFullName = rootView.findViewById(R.id.tvFullName);
            imageView = rootView.findViewById(R.id.image);
        }
    }

}
