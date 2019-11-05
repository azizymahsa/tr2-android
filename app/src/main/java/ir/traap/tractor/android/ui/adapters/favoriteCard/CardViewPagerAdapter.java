package ir.traap.tractor.android.ui.adapters.favoriteCard;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.List;

import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.model.card.Result;
import ir.traap.tractor.android.singleton.SingletonContext;
import ir.traap.tractor.android.ui.base.GoToActivity;
import ir.traap.tractor.android.ui.fragments.favoriteCard.FavoriteCardActionView;
import ir.traap.tractor.android.utilities.Tools;
import ir.traap.tractor.android.utilities.Utility;


/**
 * Created by Javad.Abadi on 7/4/2018.
 */
public class CardViewPagerAdapter extends RecyclerView.Adapter<CardViewPagerAdapter.MyViewHolder>
{

    private List<Result> cardList;
    private Context context;
    private FavoriteCardActionView actionView;


    public CardViewPagerAdapter(List<Result> models, FavoriteCardActionView actionView)
    {
        this.cardList = models;
        this.actionView = actionView;
        context = SingletonContext.getInstance().getContext();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_viewpager_item, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        Result item = cardList.get(position);

        if (position == 0)
        {
            holder.cvAddCard.setVisibility(View.VISIBLE);
            holder.cvContent.setVisibility(View.INVISIBLE);
            holder.rlBackView.setVisibility(View.INVISIBLE);
        }
        else
        {
            holder.cvAddCard.setVisibility(View.INVISIBLE);
            holder.cvContent.setVisibility(View.VISIBLE);
            holder.rlBackView.setVisibility(View.VISIBLE);


            holder.tvFullName.setText(item.getFullName());
//            holder.tvNumberCard.setText(item.getCardNumber());
            holder.tvNumberCard.setText(Utility.cardFormat(item.getCardNumber()));
//            holder.tvExpireDate.setText(item.getExpirationDateYear() + "/" + item.getExpirationDateMonth());

            holder.tvFullName.setTextColor(Color.parseColor(item.getColorText()));
//            holder.tvExpireDate.setTextColor(Color.parseColor(item.getColorText()));
            holder.tvNumberCard.setTextColor(Color.parseColor(item.getColorNumber()));

            if (item.getIsMainCard()!=null)
            if (item.getIsMainCard())
            {
                holder.imgDelete.setVisibility(View.GONE);
                holder.vDelete.setVisibility(View.GONE);
                holder.vChangePass.setVisibility(View.GONE);
                holder.imgEdit.setVisibility(View.GONE);
                holder.tvNumberCard.setVisibility(View.GONE);
                holder.tvFullName.setVisibility(View.GONE);

//                holder.cvContent.setVisibility(View.INVISIBLE);
                holder.cvAddCard.setVisibility(View.INVISIBLE);
            }
            else
            {
                holder.imgEdit.setVisibility(View.VISIBLE);
                holder.vChangePass.setVisibility(View.VISIBLE);
                holder.imgDelete.setVisibility(View.VISIBLE);
                holder.vDelete.setVisibility(View.VISIBLE);
                holder.tvNumberCard.setVisibility(View.VISIBLE);
                holder.tvFullName.setVisibility(View.VISIBLE);

//                if (!item.getBankBin().equalsIgnoreCase(TrapConfig.HappyBaseCardNo))
//                {
//                }
                holder.llChangePass.setVisibility(View.GONE);
            }

//            if (item.getIsFavorite() && !item.getIsMainCard())
//            {
//                holder.imgStar.setImageResource(R.drawable.ic_star_24dp);
//                holder.lottieView.setMinFrame(1);
//                holder.lottieView.setVisibility(View.VISIBLE);
//            }
//            else
//            {
//                holder.imgStar.setImageResource(R.drawable.ic_star_border_24dp);
//                holder.lottieView.setVisibility(View.GONE);
//            }


        }


        holder.imgEdit.setOnClickListener(view ->
        {
            actionView.onShowEditDialog(item, position);
        });
//

        holder.llChangePass.setOnClickListener(view ->
        {
            actionView.onShowChangePasswordDialog(item, position);
        });


        holder.imgDelete.setOnClickListener(view ->
        {
            Tools.showToast(context, "Delete", R.color.gray);
            actionView.onShowConfirmDeleteDialog(item, position);

        });


//        holder.imgStar.setOnClickListener(view ->
//        {
//            Tools.showToast(context, "Star", R.color.gray);
//
//            action.onFavorite(position, item.getCardId());
////            mainView.onFavoriteRequest(Prefs.getInt("userId", 0), model.getCardId());
//            if (item.getIsFavorite())
//            {
//                Picasso.with(context).load(R.drawable.ic_star_border_24dp).into(holder.imgStar);
//            }
//            else
//            {
//                Picasso.with(context).load(R.drawable.ic_star_24dp).into(holder.imgStar);
//            }
//            notifyDataSetChanged();
//
//
//        });

        holder.cvContent.setOnClickListener(view ->
        {
            if (position != 0)
            {
                holder.myEasyFlipView.flipTheView();
            }
        });

        holder.rlBackView.setOnClickListener(view ->
        {
            if (position != 0)
            {
                holder.myEasyFlipView.flipTheView();
            }
        });

        holder.cvAddCard.setOnClickListener(view ->
        {
            actionView.startActivity(GoToActivity.AddCardActivity);
        });

        holder.imgShare.setOnClickListener(view ->
        {
            Tools.showToast(context, "Share", R.color.gray);

            actionView.onShareCard(holder.cvContent);

        });

        try
        {
            if (item.getCardImageBack() == null)
            {
                loadImageIntoIV(item.getCardImage(), holder.ivBack, holder);
            } else
            {
                loadImageIntoIV(item.getCardImageBack(), holder.ivBack, holder);
            }

            loadImageIntoIV(item.getCardImage(), holder.ivLoyal, holder);
        }
        catch (Exception e)
        {
//                mainView.onError(e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadImageIntoIV(String link, ImageView imageView, @NonNull MyViewHolder holder)
    {
        Picasso.with(context).load(link).into(imageView, new Callback()
        {
            @Override
            public void onSuccess()
            {
                holder.cvContent.setBackgroundColor(Color.TRANSPARENT);
            }

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
        return cardList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvNumberCard, tvFullName;
        private ImageView imgDelete, imgEdit, imgShare;
        //        private LottieAnimationView lottieView;
        private RelativeLayout cvAddCard;
        private ImageView ivLoyal, ivBack;
        private LinearLayout llChangePass;
        private EasyFlipView myEasyFlipView;
        private View vDelete, vChangePass;
        private RelativeLayout cvContent, rlBackView;


        private MyViewHolder(View convertView)
        {
            super(convertView);
            ivLoyal = convertView.findViewById(R.id.ivLoyal);
            imgShare = convertView.findViewById(R.id.imgShare);
            vDelete = convertView.findViewById(R.id.vDelete);
            vChangePass = convertView.findViewById(R.id.vChangePass);
            ivBack = convertView.findViewById(R.id.ivBack);
            cvContent = convertView.findViewById(R.id.cvContent);
            rlBackView = convertView.findViewById(R.id.rlBackView);
//            lottieView = convertView.findViewById(R.id.lottieView);
            cvAddCard = convertView.findViewById(R.id.cvAddCard);
//            tvExpireDate = convertView.findViewById(R.id.tvExpireDate);
            tvNumberCard = convertView.findViewById(R.id.tvNumberCard);
            tvFullName = convertView.findViewById(R.id.tvFullName);
//            imgStar = convertView.findViewById(R.id.imgStar);
            imgDelete = convertView.findViewById(R.id.imgDelete);
            imgEdit = convertView.findViewById(R.id.imgEdit);
            llChangePass = convertView.findViewById(R.id.llChangePass);
            myEasyFlipView = itemView.findViewById(R.id.myEasyFlipView);
        }
    }

}
