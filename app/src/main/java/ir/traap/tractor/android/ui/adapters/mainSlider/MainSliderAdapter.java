package ir.traap.tractor.android.ui.adapters.mainSlider;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.model.matchList.MatchItem;
import lombok.Getter;
import lombok.Setter;

public class MainSliderAdapter extends RecyclerView.Adapter<MainSliderAdapter.ViewHolder>
{
    private OnSliderItemClickListener mItemClickListener;
    private Context mContext;
    private List<MatchItem> list;

    public MainSliderAdapter(Context mContext, List<MatchItem> list, OnSliderItemClickListener mItemClickListener)
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
                R.layout.slider_item_view, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        MatchItem matchItem = list.get(position);

        //--------------------start------------------------
//            textSliderView.setColorDateTime("#000");
//            textSliderView.setColorStadiumName("#aaa");
        //--------------------end--------------------------

        if (matchItem.getDescription() != null)
        {
            if (matchItem.getDescription().contains("/"))
            {
                String split[] = matchItem.getDescription().split("/");
                holder.tvHeaderText.setText(split[0]);
                holder.tvHeaderSubText.setText("/" + split[1]);
                holder.tvHeaderSubText.setVisibility(View.VISIBLE);
            }
            else
            {
                holder.tvHeaderText.setText(matchItem.getDescription());
//                tvHeaderText.setTextSize(mContext.getResources().getDimension(R.dimen.headerMainWeeklessTextSize));
                holder.tvHeaderSubText.setVisibility(View.GONE);
            }
        }

        if (matchItem.getTeamHome().getName() != null)
        {
            holder.tvHome.setText(matchItem.getTeamHome().getName());
        }

        if (matchItem.getTeamAway().getName() != null)
        {
            holder.tvAway.setText(matchItem.getTeamAway().getName());
        }

        if (matchItem.getStadium().getName() != null)
        {
            holder.tvStadiumName.setText(matchItem.getStadium().getName());
        }

//        if (getColorStadiumName() != null)
//        {
//            holder.tvStadiumName.setTextColor(Color.parseColor(getColorStadiumName()));
//        }

        if (matchItem.getMatchDatetimeStr() != null)
        {
            holder.tvDateTime.setText(matchItem.getMatchDatetimeStr());
        }

        if (matchItem.getResult() != null)
        {
            //---------test----------
//            String test1 = "01|00";
//            String test2[] = test1.split("\\|");
//            Logger.e("--result test--", test2[1] + "  :  " + test2[0]);
            //---------test----------
            String result[] = matchItem.getResult().split("-");
            holder.tvMatchResult.setText(Integer.parseInt(result[1] )+ "  -  " + Integer.parseInt(result[0]));

            holder.tvMatchResult.setVisibility(View.VISIBLE);
            holder.imgCenter.setVisibility(View.GONE);
        }
        else
        {
            holder.tvMatchResult.setVisibility(View.GONE);
            holder.imgCenter.setVisibility(View.VISIBLE);
        }

//        if (getColorDateTime() != null)
//        {
//            holder.tvDateTime.setTextColor(Color.parseColor(getColorDateTime()));
//        }

        if (matchItem.getCup().getImageName() != null)
        {
            setImageBackground(holder.imgBackground, matchItem.getCup().getImageName());
        }

        if (matchItem.getTeamHome().getLogo() != null)
        {
            setImageBackground(holder.imgHost, matchItem.getTeamHome().getLogo());
        }

        if (matchItem.getTeamAway().getLogo() != null)
        {
            setImageBackground(holder.imgGuest, matchItem.getTeamAway().getLogo());
        }

        holder.progress.setVisibility(View.GONE);
        holder.rlRoot.setVisibility(View.VISIBLE);

    }


    private void setImageBackground(ImageView imageView, String link)
    {
        try
        {
            Picasso.with(mContext).load(Uri.parse(link)).into(imageView, new Callback()
            {
                @Override
                public void onSuccess() { }

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

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener
    {
        private TextView tvHeaderText, tvHeaderSubText, tvStadiumName, tvDateTime, tvMatchResult, tvHome, tvAway;
        private ImageView imgGuest, imgHost, imgBackground, imgCenter;
        private RelativeLayout rlRoot;
        private ProgressBar progress;

        public ViewHolder(@NonNull View rootView)
        {
            super(rootView);

            rlRoot = rootView.findViewById(R.id.rlRoot);
            imgBackground = rootView.findViewById(R.id.imgBackground);
            imgCenter = rootView.findViewById(R.id.imgCenter);
            imgHost = rootView.findViewById(R.id.imgHost);
            imgGuest = rootView.findViewById(R.id.imgGuest);
            tvHeaderText = rootView.findViewById(R.id.tvHeaderText);
            tvHome = rootView.findViewById(R.id.tvHome);
            tvAway = rootView.findViewById(R.id.tvAway);
            tvHeaderSubText = rootView.findViewById(R.id.tvHeaderSubText);
            tvStadiumName = rootView.findViewById(R.id.tvStadiumName);
            tvDateTime = rootView.findViewById(R.id.tvDateTime);
            tvMatchResult = rootView.findViewById(R.id.tvMatchResult);

            progress = rootView.findViewById(R.id.progress);

            rootView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            if (mItemClickListener != null)
            {
                mItemClickListener.onSliderItemClick(view,  list.get(getAdapterPosition()).getId(), getAdapterPosition());
            }
        }
    }


    public interface OnSliderItemClickListener
    {
        public void onSliderItemClick(View view, Integer id, Integer position);
    }

//    public void SetOnItemClickListener(final OnSliderItemClickListener mItemClickListener)
//    {
//        this.mItemClickListener = mItemClickListener;
//    }

}
