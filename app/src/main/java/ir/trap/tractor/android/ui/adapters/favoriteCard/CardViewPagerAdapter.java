package ir.trap.tractor.android.ui.adapters.favoriteCard;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.pixplicity.easyprefs.library.Prefs;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.List;

import ir.trap.tractor.android.R;
import ir.trap.tractor.android.conf.TrapConfig;
import ir.trap.tractor.android.models.dbModels.ArchiveCardDBModel;
import ir.trap.tractor.android.singleton.SingletonContext;


/**
 * Created by Javad.Abadi on 7/4/2018.
 */
public class CardViewPagerAdapter extends RecyclerView.Adapter<CardViewPagerAdapter.MyViewHolder>
{

    private List<ArchiveCardDBModel> models;
//    private MainView mainView;
    private Context context;
    private ViewPagerAdapterAction action;


//    public CardViewPagerAdapter(List<ArchiveCardDBModel> models, MainView mainView, ViewPagerAdapterAction action)
//    {
//        this.models = models;
//        this.mainView = mainView;
//        this.action = action;
//        context = SingletonContext.getInstance().getContext();
//    }

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
        ArchiveCardDBModel model = models.get(position);

        holder.tvEdit.setOnClickListener(view ->
        {
//            ArchiveCardDBModel cardDBModel = ArchiveCardDBModel.findById(ArchiveCardDBModel.class, model.getId());
//            mainView.onShowEditDialog(cardDBModel, model.getId(), position);
        });

        if (model.isMainCard())
        {
            holder.tvDelete.setVisibility(View.GONE);
            holder.vDelete.setVisibility(View.GONE);
            holder.tvBankNameAdapter.setVisibility(View.GONE);
            holder.ivBankLogoAdapter.setVisibility(View.GONE);
            holder.tvNumberCard.setVisibility(View.GONE);
            holder.tvExpireDate.setVisibility(View.GONE);
            holder.tvFullName.setVisibility(View.GONE);
            holder.tvPrice.setVisibility(View.VISIBLE);
//            holder.ivLoyal.setVisibility(View.GONE);

            // holder.tvPrice.setText("موجودی: " + "125,000" + " ریال");
            holder.cvContent.setBackgroundColor(Color.TRANSPARENT);


        } else
        {
            holder.tvDelete.setVisibility(View.VISIBLE);
            holder.vDelete.setVisibility(View.VISIBLE);
            holder.tvBankNameAdapter.setVisibility(View.VISIBLE);
            holder.ivBankLogoAdapter.setVisibility(View.VISIBLE);
            holder.tvNumberCard.setVisibility(View.VISIBLE);
            holder.tvExpireDate.setVisibility(View.VISIBLE);
            holder.tvFullName.setVisibility(View.VISIBLE);
            holder.tvPrice.setVisibility(View.GONE);
            holder.ivLoyal.setVisibility(View.VISIBLE);

            holder.cvContent.setBackgroundColor(Color.BLUE);

        }
        holder.llChangePas.setOnClickListener(view -> {
//            ArchiveCardDBModel cardDBModel = ArchiveCardDBModel.findById(ArchiveCardDBModel.class, model.getId());
//            mainView.onShowPasswordChangeDialog(cardDBModel, model.getId(), position);

        });


//        holder.tvDelete.setOnClickListener(view -> mainView.showConfirmDeleteCard(Utility.cardFormat(model.getNumber()), position));


        holder.tvStar.setOnClickListener(view ->
        {
            action.onFavorite(position);
//            mainView.onFavoriteRequest(Prefs.getInt("userId", 0), model.getCardId());
            if (model.isFavorite())
            {
//                holder.tvStar.setText(R.string.star_f_icon_R);

            } else
            {
//                holder.tvStar.setText(R.string.star_icon_R);


            }
            notifyDataSetChanged();


        });
        holder.tvSecurity.setOnClickListener(view -> {
//            ArchiveCardDBModel cardDBModel = ArchiveCardDBModel.findById(ArchiveCardDBModel.class, model.getId());
//            mainView.securityDialog(cardDBModel, model.getId(), position);

        });


        holder.cvContent.setOnClickListener(view -> {
            holder.myEasyFlipView.flipTheView();



           /* if (model.isSelect()) {
                model.setSelect(false);
                ObjectAnimator anim2 = ObjectAnimator.ofFloat(holder.cvContent, "translationY", context.getResources().getInteger(R.integer.values_anim), 0f);
                anim2.setDuration(200);
                anim2.setInterpolator(new AccelerateInterpolator());
                anim2.start();

            } else {
                model.setSelect(true);
                ObjectAnimator anim2 = ObjectAnimator.ofFloat(holder.cvContent, "translationY", 0f, context.getResources().getInteger(R.integer.values_anim));
                anim2.setDuration(200);
                anim2.setInterpolator(new AccelerateInterpolator());
                anim2.start();


            }*/
        });
        holder.rlBackView.setOnClickListener(view -> {
            holder.myEasyFlipView.flipTheView();


        });
        holder.cardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
//                mainView.startActivity(GoToActivity.AddCardActivity);

            }
        });

//        if (model.isDelete())
//        {
//            ObjectAnimator anim = ObjectAnimator.ofFloat(holder.cvContent, "translationY", context.getResources().getInteger(R.integer.values_anim), 1000f);
//            anim.setDuration(400);
//            anim.setInterpolator(new AccelerateInterpolator());
//            anim.addListener(new Animator.AnimatorListener()
//            {
//                @Override
//                public void onAnimationStart(Animator animator)
//                {
//
//                }
//
//                @Override
//                public void onAnimationEnd(Animator animator)
//                {
//                    mainView.onDeleteRequest(Prefs.getInt("userId", 0), model.getCardId());
//                    action.onDelete(position);
//                }
//
//                @Override
//                public void onAnimationCancel(Animator animator)
//                {
//
//                }
//
//                @Override
//                public void onAnimationRepeat(Animator animator)
//                {
//
//                }
//            });
//            anim.start();
//
//
//        }

        if (model.isFavorite() && !model.isMainCard())
        {
//            holder.tvStar.setText(R.string.star_f_icon_R);
//            holder.lottieView.setMinFrame(1);
//            holder.lottieView.setVisibility(View.VISIBLE);

        }
        else
        {
//            holder.tvStar.setText(R.string.star_icon_R);
//            holder.lottieView.setVisibility(View.GONE);
        }

      /*  if (model.isSelect()) {
            model.setSelect(true);
            ObjectAnimator anim = ObjectAnimator.ofFloat(holder.cvContent, "translationY", 0f, context.getResources().getInteger(R.integer.values_anim));
            anim.setDuration(0);
            anim.setInterpolator(new AccelerateInterpolator());
            anim.start();

        } else {
            model.setSelect(false);
            ObjectAnimator anim = ObjectAnimator.ofFloat(holder.cvContent, "translationY", context.getResources().getInteger(R.integer.values_anim), 0f);
            anim.setDuration(0);
            anim.setInterpolator(new AccelerateInterpolator());
            anim.start();


        }

*/
//        if (model.getPic() == 100)
//        {
//            holder.cardView.setVisibility(View.VISIBLE);
//            holder.cvContent.setVisibility(View.GONE);
//
//        } else
//        {
//            holder.cardView.setVisibility(View.GONE);
//            holder.cvContent.setVisibility(View.VISIBLE);
//
//
//            holder.tvExpireDate.setText(model.getExpireYear() + "/" + model.getExpireMonth());
//            holder.tvFullName.setText(model.getfullName());
//            try
//            {
//                if (model.getNumber().length() > 0)
//                {
//                    holder.tvNumberCard.setText(Utility.cardFormat(model.getNumber()));
//
//                }
//            } catch (Exception e)
//            {
//            }
//
//            holder.tvNumberCard.setTextColor(model.getCardNumberColor() == null ? Color.parseColor("#666666") : Color.parseColor(model.getCardNumberColor()));
//            holder.tvFullName.setTextColor(model.getTextColor() == null ? Color.parseColor("#666666") : Color.parseColor(model.getTextColor()));
//            holder.tvExpireDate.setTextColor(model.getTextColor() == null ? Color.parseColor("#666666") : Color.parseColor(model.getTextColor()));
//
//
//            try
//            {
//                //todo
//                String bankNumber = model.getNumber().substring(0, 6);
//                if (bankNumber.equals(TrapConfig.HappyBaseCardNo))
//                {
//                    holder.tvExpireDate.setVisibility(View.GONE);
//                    holder.llChangePas.setVisibility(View.VISIBLE);
//                } else
//                {
//                    holder.tvExpireDate.setVisibility(View.VISIBLE);
//                    holder.llChangePas.setVisibility(View.GONE);
//
//                }
//
//                holder.tvShare.setOnClickListener(view -> {
//                    holder.tvExpireDate.setVisibility(View.INVISIBLE);
//                    mainView.capture(holder.cvContent);
//                    if (!bankNumber.equals(TrapConfig.HappyBaseCardNo))
//                        holder.tvExpireDate.setVisibility(View.VISIBLE);
//
//
//                });
//
//            } catch (Exception e)
//            {
//                // Toast.makeText(context, "eerrrrrrrrrrrrrrrroorrrrrrrrrrrrrrrrrrr", Toast.LENGTH_SHORT).show();
//
//
//            }
//
//            try
//            {
//                holder.tvBankNameAdapter.setText(model.getOfflineCardName());
//                // holder.ivBankLogoAdapter.setImageDrawable(ContextCompat.getDrawable(context, model.getOfflineLogoImage()));
//
//            } catch (Exception e)
//            {
//                mainView.onError(e.getMessage(), this.getClass().getCanonicalName(), DibaConfig.showClassNameInException);
//            }
//
//
//            try
//            {
//                if (model.getBackCardImage() == null)
//                {
//                    loadImageIntoIV(model.getCardImage(), holder.ivBack, holder);
//                } else
//                {
//                    loadImageIntoIV(model.getBackCardImage(), holder.ivBack, holder);
//                }
//
//                loadImageIntoIV(model.getCardImage(), holder.ivLoyal, holder);
//
//            } catch (Exception e)
//            {
//                mainView.onError(e.getMessage(), this.getClass().getCanonicalName(), DibaConfig.showClassNameInException);
//
//            }
//
//
//        }
    }

    private void loadImageIntoIV(String link, ImageView imageView, @NonNull MyViewHolder holder)
    {
//        GlideApp.with(context).load(link).into(imageView);
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
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return models.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvExpireDate, tvNumberCard, tvFullName, tvStar, tvDelete, tvEdit, tvBankNameAdapter, tvSecurity, tvShare, tvPrice;
        private LottieAnimationView lottieView;
        private RelativeLayout cardView, cvContent, rlBackView;
        private ImageView ivLoyal, ivBankLogoAdapter, ivBack;
        private LinearLayout llChangePas;
        private EasyFlipView myEasyFlipView;
        private View vDelete;


        private MyViewHolder(View convertView)
        {
            super(convertView);
         /*   ivLoyal = convertView.findViewById(R.id.ivLoyal);
            tvShare = convertView.findViewById(R.id.tvShare);
            vDelete = convertView.findViewById(R.id.vDelete);
            rlBackView = convertView.findViewById(R.id.rlBackView);
            ivBack = convertView.findViewById(R.id.ivBack);
            cvContent = convertView.findViewById(R.id.cvContent);
            lottieView = convertView.findViewById(R.id.lottieView);
            cardView = convertView.findViewById(R.id.cvAddCard);
            tvExpireDate = convertView.findViewById(R.id.tvExpireDate);
            tvPrice = convertView.findViewById(R.id.tvPrice);
            tvNumberCard = convertView.findViewById(R.id.tvNumberCard);
            tvFullName = convertView.findViewById(R.id.tvFullName);
            tvStar = convertView.findViewById(R.id.tvStar);
            tvDelete = convertView.findViewById(R.id.tvDelete);
            tvEdit = convertView.findViewById(R.id.tvEdit);
//            ivBankLogoAdapter = convertView.findViewById(R.id.ivBankLogoAdapter);
//            tvBankNameAdapter = convertView.findViewById(R.id.tvBankNameAdapter);
            llChangePas = convertView.findViewById(R.id.llChangePas);
            tvSecurity = convertView.findViewById(R.id.tvSecurity);
            myEasyFlipView = itemView.findViewById(R.id.myEasyFlipView);*/

        }
    }

}
