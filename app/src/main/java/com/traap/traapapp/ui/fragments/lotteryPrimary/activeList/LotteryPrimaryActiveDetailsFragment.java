package com.traap.traapapp.ui.fragments.lotteryPrimary.activeList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.pixplicity.easyprefs.library.Prefs;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.traap.traapapp.R;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.activities.points.PointsActivity;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.dialogs.LotteryGetScoreDialog;
import com.traap.traapapp.ui.dialogs.LotteryMessageAlertDialog;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.ui.fragments.main.MainActionView;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;


@SuppressLint("LotteryPrimaryActiveFragment")
public class LotteryPrimaryActiveDetailsFragment extends BaseFragment
{
    private MainActionView actionView;
    private Context context;
    private View rootView;
    //    private List<PointGuide> guideList;
    private TextView tvCardTitle, tvCardSubTitle, tvDesc, tvFail, tvPointScore, tvChanceScore;
    private ImageView imgBackground;
    private RelativeLayout rlImage;
    private CircularProgressButton btnConfirm;
    private TextView tvTitle, tvUserName, tvHeaderPopularNo;
    private Toolbar mToolbar;

    private int pointScore = 15, chanceScore = 0;
    private int stepPointScore = 10, stepChanceScore = 10;

    public LotteryPrimaryActiveDetailsFragment()
    {

    }

    public static LotteryPrimaryActiveDetailsFragment newInstance(MainActionView actionView)
    {
        LotteryPrimaryActiveDetailsFragment f = new LotteryPrimaryActiveDetailsFragment();
        f.setActionView(actionView);
        return f;
    }

    private void setActionView(MainActionView actionView)
    {
        this.actionView = actionView;
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (rootView != null)
        {
            return rootView;
        }
        rootView = inflater.inflate(R.layout.fragment_lottery_primary_active_details, container, false);

        mToolbar = rootView.findViewById(R.id.toolbar);

        mToolbar.findViewById(R.id.imgMenu).setOnClickListener(v -> actionView.openDrawer());
        mToolbar.findViewById(R.id.imgBack).setOnClickListener(rootView -> getActivity().onBackPressed());
        tvUserName = mToolbar.findViewById(R.id.tvUserName);
        tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvTitle.setText("قرعه کشی");
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);tvHeaderPopularNo = mToolbar.findViewById(R.id.tvPopularPlayer);
        tvHeaderPopularNo.setText(String.valueOf(Prefs.getInt("popularPlayer", 12)));

        rootView.findViewById(R.id.rlShirt).setOnClickListener(v ->
                startActivityForResult(new Intent(SingletonContext.getInstance().getContext(),
                        MyProfileActivity.class),100)
        );

        mToolbar.findViewById(R.id.flLogoToolbar).setOnClickListener(v ->
        {
            actionView.backToMainFragment();
        });

        initView();

        return rootView;
    }


    public void initView()
    {
        rlImage = rootView.findViewById(R.id.rlImage);
        btnConfirm = rootView.findViewById(R.id.btnConfirm);
        imgBackground = rootView.findViewById(R.id.image);
        tvCardTitle = rootView.findViewById(R.id.tvCardTitle);
        tvCardSubTitle = rootView.findViewById(R.id.tvCardSubTitle);
        tvDesc = rootView.findViewById(R.id.tvDesc);
        tvFail = rootView.findViewById(R.id.tvFail);
        tvChanceScore = rootView.findViewById(R.id.tvChanceScore);
        tvPointScore = rootView.findViewById(R.id.tvPointScore);

        tvChanceScore.setText(String.valueOf(chanceScore));
        tvPointScore.setText(String.valueOf(pointScore));

        tvCardTitle.setText("فقط با یه کلیک، میتونی برنده قرعه کشی شی.");
        tvCardSubTitle.setText("قرعه کشی 500 میلیون تومانی");
        setImageBackground(imgBackground, "");

        tvDesc.setText(String.format("به ازای هر %d امتیاز، %d کد شانس دریافت میکنید. برای تبدیل امتیازات خود به کدهای شانس کلیک کنید.", stepPointScore, stepChanceScore));
        tvFail.setText("امتیاز شما برای شرکت در این قرعه کشی کافی نمیباشد. با انجام تراکنشها و شرکت در بخش های مختلف اپلیکیشن، میتوانید امتیازات خود را افزایش دهید.");

        if (pointScore < stepPointScore)
        {
            btnConfirm.setText("راهنمای کسب امتیازات");
            tvFail.setVisibility(View.VISIBLE);
        }
        else
        {
            btnConfirm.setText("تبدیل امتیاز به کد شانس");
            tvFail.setVisibility(View.GONE);
        }


        btnConfirm.setOnClickListener(v ->
        {
            if (pointScore < stepPointScore)
            {
                startActivityForResult(new Intent(context, PointsActivity.class), 100);
            }
            else
            {
                getSelectedScore();
            }

        });
    }

    private void getSelectedScore()
    {
        new LotteryGetScoreDialog((Activity) context, 1, pointScore, pointScore, score ->
        {

        }).show(((Activity) context).getFragmentManager(), "dialog");
    }

    public void showLotteryAlertSuccess(Context context, String Msg, int score, int chance)
    {
        LotteryMessageAlertDialog dialog = new LotteryMessageAlertDialog((Activity) context, "", Msg, "بازگشت به خانه",
                LotteryMessageAlertDialog.TYPE_SUCCESS, score, chance, () ->
        {
            actionView.backToMainFragment();
        });
//        dialog.setCancelable(!finish);
        dialog.show(((Activity) context).getFragmentManager(), "dialog");
    }


    public void showLotteryAlertFailure(Context context, String Msg, int score, int chance)
    {
        LotteryMessageAlertDialog dialog = new LotteryMessageAlertDialog((Activity) context, getString(R.string.error), Msg, "تایید",
                LotteryMessageAlertDialog.TYPE_ERROR, score, chance, () ->
                {

                });
//        dialog.setCancelable(!finish);
        dialog.show(((Activity) context).getFragmentManager(), "dialog");
    }

    private void setImageBackground(ImageView imageView, String link)
    {
        try
        {
            Picasso.with(context).load(Uri.parse(link)).into(imageView, new Callback()
            {
                @Override
                public void onSuccess()
                {}

                @Override
                public void onError()
                {
                    Picasso.with(context).load(R.drawable.img_failure).into(imageView);
                }
            });
        }
        catch (NullPointerException e)
        {
            Picasso.with(context).load(R.drawable.img_failure).into(imageView);
        }
    }

}
