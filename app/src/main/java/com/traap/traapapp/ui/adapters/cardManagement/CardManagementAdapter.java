package com.traap.traapapp.ui.adapters.cardManagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.card.CardBankItem;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.utilities.Utility;

import java.util.List;


public class CardManagementAdapter extends RecyclerView.Adapter<CardManagementAdapter.MyViewHolder>
{
    private onCardActionListener listener;
    private List<CardBankItem> cardBankList;
    private Context context;

    public CardManagementAdapter(List<CardBankItem> cardBankList)
    {
        for (int i = 0; i < cardBankList.size(); i++)
        {
//            if (cardBankList.get(i).getPic() == 100)
//            {
//                cardBankList.remove(i);
//            }
        }
        this.cardBankList = cardBankList;
//        this.archiveView = archiveView;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_card_bank, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {


//        holder.llLayout.startAnimation(AnimationUtils.loadAnimation(SingletonContext.getInstance().getContext(), R.anim.scale_up_fast));

//        holder.tvName.setText(cardBankList.get(position).getfullName());

//        holder.tvNumber.setText(Utility.cardFormat(cardBankList.get(position).getNumber()));

//        String cardNumberCheck = cardBankList.get(position).getNumber().substring(0, 6);

//        holder.llLayout.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
////                archiveView.showDialogs(position, cardNumberCheck.equals("003825") || cardNumberCheck.equals("003725"), cardBankList.get(position).isMainCard());
//            }
//        });

//        if (cardBankList.get(position).isFavorite())
//        {
//            holder.lottieView.setMinFrame(1);
//            holder.lottieView.setVisibility(View.VISIBLE);
//
//        } else
//        {
//            holder.lottieView.setVisibility(View.GONE);
//
//
//        }
//        GlideApp.with(SingletonContext.getInstance().getContext()).load(archiveCardDBModels.get(position).getImageLogo()).into(holder.ivBankLogo);

    }

    @Override
    public int getItemCount()
    {
        return cardBankList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
//        TextView tvName, tvNumber;
//        LinearLayout llLayout;
//        LottieAnimationView lottieView;
//        ImageView ivBankLogo;

        private MyViewHolder(View convertView)
        {
            super(convertView);
//            tvName = (TextView) convertView.findViewById(R.id.tvName);
//            tvNumber = (TextView) convertView.findViewById(R.id.tvNumber);
//            llLayout = (LinearLayout) convertView.findViewById(R.id.llLayout);
//            lottieView = (LottieAnimationView) convertView.findViewById(R.id.lottieView);
//            ivBankLogo = convertView.findViewById(R.id.ivBankLogo);

        }
    }

    public interface onCardActionListener
    {
        void onMenuItemClick(int position, CardBankItem cardBankItem);
    }

}
