package com.traap.traapapp.ui.adapters.cardManagement;

import android.content.Context;
import android.graphics.Color;
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
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.card.CardBankItem;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.adapters.favoriteCard.CardViewPagerAdapter;
import com.traap.traapapp.utilities.Utility;

import java.util.List;


public class CardManagementAdapter extends RecyclerView.Adapter<CardManagementAdapter.MyViewHolder>
{
    private onCardActionListener listener;
    private List<CardBankItem> cardBankList;
    private Context context;

    public CardManagementAdapter(List<CardBankItem> cardBankList, onCardActionListener listener)
    {
        this.cardBankList = cardBankList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_card_bank, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        CardBankItem item = cardBankList.get(position);


        holder.tvName.setText(item.getFullName());

        holder.tvNumber.setText(Utility.cardFormat(item.getCardNumber()));

//        String cardNumberCheck = cardBankList.get(position).getNumber().substring(0, 6);

        try
        {
            if (item.getIsFavorite())
            {
                holder.imgBookmark.setVisibility(View.VISIBLE);
            }
            else
            {
                holder.imgBookmark.setVisibility(View.GONE);
            }
        }
        catch (NullPointerException e)
        {
            holder.imgBookmark.setVisibility(View.GONE);
        }

        loadImageIntoIV(item.getBankLogo(), holder.imgBankLogo);

        holder.imgMenu.setOnClickListener(view -> listener.onMenuItemClick(position, item));
    }

    private void loadImageIntoIV(String link, ImageView imageView)
    {
        Picasso.with(context).load(link).into(imageView, new Callback()
        {
            @Override
            public void onSuccess() { }

            @Override
            public void onError()
            {
                Picasso.with(context).load(R.drawable.img_failure).into(imageView);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return cardBankList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvName, tvNumber;
        private ImageView imgBankLogo, imgBookmark, imgMenu;

        private MyViewHolder(View convertView)
        {
            super(convertView);
            tvName = convertView.findViewById(R.id.tvName);
            tvNumber = convertView.findViewById(R.id.tvNumber);
            imgBankLogo = convertView.findViewById(R.id.imgBankLogo);
            imgBookmark = convertView.findViewById(R.id.imgBookmark);
            imgMenu = convertView.findViewById(R.id.imgMenu);
        }
    }

    public interface onCardActionListener
    {
        void onMenuItemClick(int position, CardBankItem cardBankItem);
    }

}
