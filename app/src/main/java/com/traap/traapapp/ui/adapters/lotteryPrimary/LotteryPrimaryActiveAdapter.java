package com.traap.traapapp.ui.adapters.lotteryPrimary;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.traap.traapapp.R;
import com.traap.traapapp.models.otherModels.predict.PredictTabModel;
import com.traap.traapapp.singleton.SingletonContext;

import java.util.List;

public class LotteryPrimaryActiveAdapter extends RecyclerView.Adapter<LotteryPrimaryActiveAdapter.ViewHolder>
{
    private OnLotteryItemClickListener listener;
    private Context mContext;
    private List<PredictTabModel> list;

    public LotteryPrimaryActiveAdapter(Context mContext, List<PredictTabModel> list, OnLotteryItemClickListener listener)
    {
        this.mContext = mContext;
        this.listener = listener;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.adapter_lottery_primary_active, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        PredictTabModel item = list.get(position);

        int width = (int) (SingletonContext.getInstance().getWidth() - mContext.getResources().getDimension(R.dimen.margin_news_main_favorite));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, R.dimen.news_favorite_height);
        holder.rlImage.setLayoutParams(params);

        holder.tvTitle.setText("فقط با یه کلیک، میتونی برنده قرعه کشی شی.");
        holder.tvSubTitle.setText("قرعه کشی 500 میلیون تومانی");

        setImageBackground(holder.progress, holder.imgBackground, item.getTitle());

    }


    private void setImageBackground(ProgressBar progressBar, ImageView imageView, String link)
    {
        try
        {
            Picasso.with(mContext).load(Uri.parse(link)).into(imageView, new Callback()
            {
                @Override
                public void onSuccess()
                {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError()
                {
                    Picasso.with(mContext).load(R.drawable.img_failure).into(imageView);
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
        catch (NullPointerException e)
        {
            Picasso.with(mContext).load(R.drawable.img_failure).into(imageView);
            progressBar.setVisibility(View.GONE);
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
        private ImageView imgBackground;
        private RelativeLayout rlImage;
        private ProgressBar progress;

        public ViewHolder(@NonNull View rootView)
        {
            super(rootView);

            rlImage = rootView.findViewById(R.id.rlImage);
            imgBackground = rootView.findViewById(R.id.image);
            tvTitle = rootView.findViewById(R.id.tvTitle);
            tvSubTitle = rootView.findViewById(R.id.tvSubTitle);

            progress = rootView.findViewById(R.id.progress);

            rootView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            if (listener != null)
            {
                listener.onLotteryItemClick(list.get(getAdapterPosition()).getId());
            }
        }
    }


    public interface OnLotteryItemClickListener
    {
        void onLotteryItemClick(Integer id);
    }
}
