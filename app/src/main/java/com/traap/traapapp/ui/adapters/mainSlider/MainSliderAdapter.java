package com.traap.traapapp.ui.adapters.mainSlider;

import android.app.Activity;
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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.matchList.MatchItem;
import com.traap.traapapp.singleton.SingletonNeedGetAllBoxesRequest;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.fragments.predict.PredictFragment;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class MainSliderAdapter extends RecyclerView.Adapter<MainSliderAdapter.ViewHolder>
{
    private final MainActionView mainView;
    private OnSliderItemClickListener mItemClickListener;
    private Context mContext;
    private List<MatchItem> list;

    public MainSliderAdapter(MainActionView mainView, Context mContext, List<MatchItem> list, OnSliderItemClickListener mItemClickListener)
    {
        this.mItemClickListener = mItemClickListener;
        this.mContext = mContext;
        this.list = list;
        this.mainView = mainView;
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

        //////////////////////

        if (matchItem.getBuyEnable() && matchItem.getIsPredict())
        {
            holder.tvBuyTicket.setVisibility(View.VISIBLE);
            holder.tvPredictResult.setVisibility(View.VISIBLE);

            holder.tvPredictResult.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_buy_ticket));
            holder.tvBuyTicket.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_buy_ticket));


            holder.tvBuyTicket.setTextColor(Color.WHITE);
            holder.tvPredictResult.setTextColor(Color.WHITE);
        } else if (matchItem.getIsPredict())
        {
            holder.tvPredictResult.setVisibility(View.VISIBLE);
            holder.tvPredictResult.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_buy_ticket));
            holder.tvBuyTicket.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_buy_ticket_disable));

            holder.tvBuyTicket.setTextColor(mContext.getResources().getColor(R.color.textColorPrimary));
            holder.tvPredictResult.setTextColor(Color.WHITE);

        } else if (matchItem.getBuyEnable())
        {
            holder.tvBuyTicket.setVisibility(View.VISIBLE);
            holder.tvPredictResult.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_buy_ticket_disable));
            holder.tvBuyTicket.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_buy_ticket));

            holder.tvBuyTicket.setTextColor(Color.WHITE);
            holder.tvPredictResult.setTextColor(mContext.getResources().getColor(R.color.textColorPrimary));
        } else
        {

            holder.tvPredictResult.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_buy_ticket_disable));
            holder.tvBuyTicket.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_buy_ticket_disable));

            holder.tvBuyTicket.setTextColor(mContext.getResources().getColor(R.color.textColorPrimary));
            holder.tvPredictResult.setTextColor(mContext.getResources().getColor(R.color.textColorPrimary));

        }
        if (matchItem.getIs_chart_predict() != null)
        {
            if (matchItem.getIs_chart_predict())
            {
                //holder.tvPredictResult.setVisibility(View.VISIBLE);

                //  holder.lnrBuyEnable.setVisibility(View.VISIBLE);
                holder.tvPredictResult.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_buy_ticket));
                holder.tvPredictResult.setTextColor(Color.WHITE);
                //holder.tvPredictResult.setEnabled(true);
                // holder.tvPredictResult.setClickable(true);
            }
        }
        ///////////////////////////

        if (matchItem.getDescription() != null)
        {
            if (matchItem.getDescription().contains("/"))
            {
                String split[] = matchItem.getDescription().split("/");
                holder.tvHeaderText.setText(split[0]);
                holder.tvHeaderSubText.setText("/" + split[1]);
                holder.tvHeaderSubText.setVisibility(View.VISIBLE);
            } else
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
            holder.tvMatchResult.setText(Integer.parseInt(result[1]) + "  -  " + Integer.parseInt(result[0]));

            holder.tvMatchResult.setVisibility(View.VISIBLE);
            holder.imgCenter.setVisibility(View.GONE);
        } else
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

        holder.tvPredictResult.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (mItemClickListener != null)
                {
                    if (holder.tvPredictResult.getCurrentTextColor() == Color.WHITE)
                    {


                        mItemClickListener.onItemPredictClick(view, position, matchItem);
                    } else
                    {
                        MessageAlertDialog dialog = new MessageAlertDialog((Activity) mContext, "",
                                "برای این بازی پیش بینی وجود ندارد!",
                                false, MessageAlertDialog.TYPE_ERROR, new MessageAlertDialog.OnConfirmListener()
                        {
                            @Override
                            public void onConfirmClick()
                            {
                                // PredictFragment pastResultFragment =  PredictFragment.newInstance(mainView, matchItem, matchItem.getIsPredict());

                                //  mContext.getSupportFragmentManager().beginTransaction().replace(R.id.main_container, pastResultFragment).commit();
                            }

                            @Override
                            public void onCancelClick()
                            {

                            }
                        }
                        );
                        dialog.show(((Activity) mContext).getFragmentManager(), "dialogMessage");
                    }
                }
            }
        });
        holder.tvBuyTicket.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SingletonNeedGetAllBoxesRequest.getInstance().setNeedRequest(true);

                if (mItemClickListener != null)
                {
                    if (holder.tvBuyTicket.getCurrentTextColor() == Color.WHITE)
                    {
                        holder.tvBuyTicket.startAnimation();
                        holder.tvBuyTicket.setClickable(false);

                        mainView.getBuyEnable(() ->
                        {
                            holder.tvBuyTicket.revertAnimation();
                            holder.tvBuyTicket.setClickable(true);
                        });//mItemClickListener.onItemBuyTicketClick(view, position, matchItem);
                    } else
                    {
                        MessageAlertDialog dialog = new MessageAlertDialog((Activity) mContext, "",
                                "برای این بازی فروش بلیت وجود ندارد!",
                                false, MessageAlertDialog.TYPE_ERROR, new MessageAlertDialog.OnConfirmListener()
                        {
                            @Override
                            public void onConfirmClick()
                            {
                                // PredictFragment pastResultFragment =  PredictFragment.newInstance(mainView, matchItem, matchItem.getIsPredict());

                                //  mContext.getSupportFragmentManager().beginTransaction().replace(R.id.main_container, pastResultFragment).commit();
                            }

                            @Override
                            public void onCancelClick()
                            {

                            }
                        }
                        );
                        dialog.show(((Activity) mContext).getFragmentManager(), "dialogMessage");
                    }

                }

            }
        });
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
        } catch (NullPointerException e)
        {
            Picasso.with(mContext).load(R.drawable.img_failure).into(imageView);
        }
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView tvHeaderText, tvHeaderSubText, tvStadiumName, tvDateTime, tvMatchResult, tvHome, tvAway;
        private CircularProgressButton tvPredictResult, tvBuyTicket;
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
            tvBuyTicket = rootView.findViewById(R.id.tvBuyTicket);
            tvPredictResult = rootView.findViewById(R.id.tvPredictResult);

            progress = rootView.findViewById(R.id.progress);

            rootView.setOnClickListener(this);
            tvPredictResult.setOnClickListener(this);
            tvBuyTicket.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            if (mItemClickListener != null)
            {

                mItemClickListener.onSliderItemClick(view, list.get(getAdapterPosition()).getId(), getAdapterPosition());
            }
        }
    }


    public interface OnSliderItemClickListener
    {
        public void onSliderItemClick(View view, Integer id, Integer position);

        void onItemPredictClick(View view, int position, MatchItem matchItem);

        void onItemBuyTicketClick(View view, int position, MatchItem matchItem);

    }

//    public void SetOnItemCheckedChangeListener(final OnItemClickListener mItemClickListener)
//    {
//        this.mItemClickListener = mItemClickListener;
//    }

}
