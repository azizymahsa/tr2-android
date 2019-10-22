package ir.trap.tractor.android.ui.adapters.favoriteCard;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.List;

import ir.trap.tractor.android.R;
import ir.trap.tractor.android.apiServices.model.card.getCardList.Result;
import ir.trap.tractor.android.singleton.SingletonContext;
import ir.trap.tractor.android.ui.base.GoToActivity;
import ir.trap.tractor.android.ui.fragments.favoriteCard.FavoriteCardActionView;
import ir.trap.tractor.android.utilities.Utility;


/**
 * Created by Javad.Abadi on 7/4/2018.
 */
public class CardViewPagerAdapter extends RecyclerView.Adapter<CardViewPagerAdapter.MyViewHolder>
{

    private List<Result> cardList;
    private Context context;
    private ViewPagerAdapterAction action;
    private FavoriteCardActionView actionView;


    public CardViewPagerAdapter(List<Result> models, FavoriteCardActionView actionView, ViewPagerAdapterAction action)
    {
        this.cardList = models;
        this.actionView = actionView;
        this.action = action;
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
            holder.tvExpireDate.setText(item.getExpirationDateYear() + "/" + item.getExpirationDateMonth());

            holder.tvFullName.setTextColor(Color.parseColor(item.getColorText()));
            holder.tvExpireDate.setTextColor(Color.parseColor(item.getColorText()));
            holder.tvNumberCard.setTextColor(Color.parseColor(item.getColorNumber()));

            loadImageIntoIV(item.getCardImage(), holder.ivLoyal, holder);
            loadImageIntoIV(item.getCardImageBack(), holder.ivBack, holder);


            if (item.getIsMainCard())
            {
//            holder.tvDelete.setVisibility(View.GONE);
//            holder.vDelete.setVisibility(View.GONE);
                holder.tvNumberCard.setVisibility(View.GONE);
                holder.tvExpireDate.setVisibility(View.GONE);
                holder.tvFullName.setVisibility(View.GONE);

                holder.cvContent.setVisibility(View.INVISIBLE);
                holder.cvAddCard.setVisibility(View.INVISIBLE);
            }
            else
            {
//            holder.tvDelete.setVisibility(View.VISIBLE);
//            holder.vDelete.setVisibility(View.VISIBLE);
                holder.tvNumberCard.setVisibility(View.VISIBLE);
                holder.tvExpireDate.setVisibility(View.VISIBLE);
                holder.tvFullName.setVisibility(View.VISIBLE);
//
            }

            if (item.getIsFavorite() && !item.getIsMainCard())
            {
////            holder.tvStar.setText(R.string.star_f_icon_R);
                holder.lottieView.setMinFrame(1);
                holder.lottieView.setVisibility(View.VISIBLE);
//
            }
            else
            {
////            holder.tvStar.setText(R.string.star_icon_R);
                holder.lottieView.setVisibility(View.GONE);
            }

        }



//        holder.tvEdit.setOnClickListener(view ->
//        {
////            ArchiveCardDBModel cardDBModel = ArchiveCardDBModel.findById(ArchiveCardDBModel.class, model.getId());
////            mainView.onShowEditDialog(cardDBModel, model.getId(), position);
//        });
//

//        holder.llChangePas.setOnClickListener(view -> {
////            ArchiveCardDBModel cardDBModel = ArchiveCardDBModel.findById(ArchiveCardDBModel.class, model.getId());
////            mainView.onShowPasswordChangeDialog(cardDBModel, model.getId(), position);
//
//        });
//
//
////        holder.tvDelete.setOnClickListener(view -> mainView.showConfirmDeleteCard(Utility.cardFormat(model.getNumber()), position));
//
//
//        holder.tvStar.setOnClickListener(view ->
//        {
//            action.onFavorite(position);
////            mainView.onFavoriteRequest(Prefs.getInt("userId", 0), model.getCardId());
//            if (model.isFavorite())
//            {
////                holder.tvStar.setText(R.string.star_f_icon_R);
//
//            } else
//            {
////                holder.tvStar.setText(R.string.star_icon_R);
//
//
//            }
//            notifyDataSetChanged();
//
//
//        });
//        holder.tvSecurity.setOnClickListener(view -> {
////            ArchiveCardDBModel cardDBModel = ArchiveCardDBModel.findById(ArchiveCardDBModel.class, model.getId());
////            mainView.securityDialog(cardDBModel, model.getId(), position);
//
//        });
//
//
//        holder.cvContent.setOnClickListener(view ->
//        {
//            if (position != 0)
//            {
//                holder.myEasyFlipView.flipTheView();
//            }
//
//
//
//           /* if (model.isSelect()) {
//                model.setSelect(false);
//                ObjectAnimator anim2 = ObjectAnimator.ofFloat(holder.cvContent, "translationY", context.getResources().getInteger(R.integer.values_anim), 0f);
//                anim2.setDuration(200);
//                anim2.setInterpolator(new AccelerateInterpolator());
//                anim2.start();
//
//            } else {
//                model.setSelect(true);
//                ObjectAnimator anim2 = ObjectAnimator.ofFloat(holder.cvContent, "translationY", 0f, context.getResources().getInteger(R.integer.values_anim));
//                anim2.setDuration(200);
//                anim2.setInterpolator(new AccelerateInterpolator());
//                anim2.start();
//
//
//            }*/
//        });
//        holder.rlBackView.setOnClickListener(view ->
//        {
//            if (position != 0)
//            {
//                holder.myEasyFlipView.flipTheView();
//            }
//
//        });
        holder.cvAddCard.setOnClickListener(view ->
                actionView.startActivity(GoToActivity.AddCardActivity)
        );
//
////        if (model.isDelete())
////        {
////            ObjectAnimator anim = ObjectAnimator.ofFloat(holder.cvContent, "translationY", context.getResources().getInteger(R.integer.values_anim), 1000f);
////            anim.setDuration(400);
////            anim.setInterpolator(new AccelerateInterpolator());
////            anim.addListener(new Animator.AnimatorListener()
////            {
////                @Override
////                public void onAnimationStart(Animator animator)
////                {
////
////                }
////
////                @Override
////                public void onAnimationEnd(Animator animator)
////                {
////                    mainView.onDeleteRequest(Prefs.getInt("userId", 0), model.getCardId());
////                    action.onDelete(position);
////                }
////
////                @Override
////                public void onAnimationCancel(Animator animator)
////                {
////
////                }
////
////                @Override
////                public void onAnimationRepeat(Animator animator)
////                {
////
////                }
////            });
////            anim.start();
////
////
////        }
//

//
//      /*  if (model.isSelect()) {
//            model.setSelect(true);
//            ObjectAnimator anim = ObjectAnimator.ofFloat(holder.cvContent, "translationY", 0f, context.getResources().getInteger(R.integer.values_anim));
//            anim.setDuration(0);
//            anim.setInterpolator(new AccelerateInterpolator());
//            anim.start();
//
//        } else {
//            model.setSelect(false);
//            ObjectAnimator anim = ObjectAnimator.ofFloat(holder.cvContent, "translationY", context.getResources().getInteger(R.integer.values_anim), 0f);
//            anim.setDuration(0);
//            anim.setInterpolator(new AccelerateInterpolator());
//            anim.start();
//
//
//        }
//
//*/
////        if (model.getPic() == 100)
////        {
////            holder.cvAddCard.setVisibility(View.VISIBLE);
////            holder.cvContent.setVisibility(View.GONE);
////
////        } else
////        {
////            holder.cvAddCard.setVisibility(View.GONE);
////            holder.cvContent.setVisibility(View.VISIBLE);
////
////
////            holder.tvExpireDate.setText(model.getExpireYear() + "/" + model.getExpireMonth());
////            holder.tvFullName.setText(model.getfullName());
////            try
////            {
////                if (model.getNumber().length() > 0)
////                {
////                    holder.tvNumberCard.setText(Utility.cardFormat(model.getNumber()));
////
////                }
////            } catch (Exception e)
////            {
////            }
////
////            holder.tvNumberCard.setTextColor(model.getCardNumberColor() == null ? Color.parseColor("#666666") : Color.parseColor(model.getCardNumberColor()));
////            holder.tvFullName.setTextColor(model.getTextColor() == null ? Color.parseColor("#666666") : Color.parseColor(model.getTextColor()));
////            holder.tvExpireDate.setTextColor(model.getTextColor() == null ? Color.parseColor("#666666") : Color.parseColor(model.getTextColor()));
////
////
////            try
////            {
////                //todo
////                String bankNumber = model.getNumber().substring(0, 6);
////                if (bankNumber.equals(TrapConfig.HappyBaseCardNo))
////                {
////                    holder.tvExpireDate.setVisibility(View.GONE);
////                    holder.llChangePas.setVisibility(View.VISIBLE);
////                } else
////                {
////                    holder.tvExpireDate.setVisibility(View.VISIBLE);
////                    holder.llChangePas.setVisibility(View.GONE);
////
////                }
////
////                holder.tvShare.setOnClickListener(view -> {
////                    holder.tvExpireDate.setVisibility(View.INVISIBLE);
////                    mainView.capture(holder.cvContent);
////                    if (!bankNumber.equals(TrapConfig.HappyBaseCardNo))
////                        holder.tvExpireDate.setVisibility(View.VISIBLE);
////
////
////                });
////
////            } catch (Exception e)
////            {
////                // Toast.makeText(context, "eerrrrrrrrrrrrrrrroorrrrrrrrrrrrrrrrrrr", Toast.LENGTH_SHORT).show();
////
////
////            }
////
////
////
////            try
////            {
////                if (model.getBackCardImage() == null)
////                {
////                    loadImageIntoIV(model.getCardImage(), holder.ivBack, holder);
////                } else
////                {
////                    loadImageIntoIV(model.getBackCardImage(), holder.ivBack, holder);
////                }
////
////                loadImageIntoIV(model.getCardImage(), holder.ivLoyal, holder);
////
////            } catch (Exception e)
////            {
////                mainView.onError(e.getMessage(), this.getClass().getCanonicalName(), DibaConfig.showClassNameInException);
////
////            }
////
////
////        }
    }

    private void loadImageIntoIV(String link, ImageView imageView, @NonNull MyViewHolder holder)
    {
//        GlideApp.with(context).load(link).into(imageView);
        Picasso.with(context).load(link).into(imageView, new Callback()
        {
            @Override
            public void onSuccess()
            {
//                holder.cvContent.setBackgroundColor(Color.TRANSPARENT);
            }

            @Override
            public void onError()
            {
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
        private TextView tvExpireDate, tvNumberCard, tvFullName;
//        private TextView tvStar, tvDelete, tvEdit, tvSecurity, tvShare;
        private LottieAnimationView lottieView;
        private RelativeLayout cvAddCard;
        private ImageView ivLoyal, ivBack;
//        private LinearLayout llChangePas;
        private EasyFlipView myEasyFlipView;
//        private View vDelete;
        private RelativeLayout cvContent, rlBackView;


        private MyViewHolder(View convertView)
        {
            super(convertView);
            ivLoyal = convertView.findViewById(R.id.ivLoyal);
////            tvShare = convertView.findViewById(R.id.tvShare);
//            vDelete = convertView.findViewById(R.id.vDelete);
            ivBack = convertView.findViewById(R.id.ivBack);
            cvContent = convertView.findViewById(R.id.cvContent);
            rlBackView = convertView.findViewById(R.id.rlBackView);
            lottieView = convertView.findViewById(R.id.lottieView);
            cvAddCard = convertView.findViewById(R.id.cvAddCard);
            tvExpireDate = convertView.findViewById(R.id.tvExpireDate);
            tvNumberCard = convertView.findViewById(R.id.tvNumberCard);
            tvFullName = convertView.findViewById(R.id.tvFullName);
//            tvStar = convertView.findViewById(R.id.tvStar);
//            tvDelete = convertView.findViewById(R.id.tvDelete);
//            tvEdit = convertView.findViewById(R.id.tvEdit);
//            llChangePas = convertView.findViewById(R.id.llChangePas);
//            tvSecurity = convertView.findViewById(R.id.tvSecurity);
            myEasyFlipView = itemView.findViewById(R.id.myEasyFlipView);
        }
    }

}
