package com.traap.traapapp.ui.activities.myPredicts;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.lottery.Winner;
import com.traap.traapapp.apiServices.model.predict.predictResult.getMyPredict.MyPredictResponse;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.ui.activities.lotteryWinnerList.LotteryWinnerDetailsActivity;
import com.traap.traapapp.ui.adapters.myPredict.MyPredictAdapter;
import com.traap.traapapp.ui.base.BaseActivity;
import com.traap.traapapp.ui.dialogs.LotteryWinnerListDialog;
import com.traap.traapapp.ui.fragments.predict.predictResult.PredictResultActionView;
import com.traap.traapapp.utilities.Logger;

import java.util.ArrayList;
import java.util.List;

public class MyPredictsActivity extends BaseActivity implements MyPredictActionView, PredictResultActionView,
        OnServiceStatus<WebServiceClass<MyPredictResponse>>, MyPredictAdapter.OnItemClickListener
{
    private Toolbar mToolbar;
    private TextView tvUserName, tvHeaderPopularNo;
    private TextView tvPointScore, tvAllPredictCount, tvTruePredictCount, tvYourRate;

    private NestedScrollView scrollView;
    private CardView cardPoints;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_predicts);

        mToolbar = findViewById(R.id.toolbar);

        mToolbar.findViewById(R.id.imgMenu).setVisibility(View.INVISIBLE);
        mToolbar.findViewById(R.id.imgBack).setOnClickListener(rootView -> finish());
        tvUserName = mToolbar.findViewById(R.id.tvUserName);
        tvHeaderPopularNo = mToolbar.findViewById(R.id.tvPopularPlayer);
        TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvTitle.setText("پیش بینی های من");
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);

        FrameLayout flLogoToolbar = findViewById(R.id.flLogoToolbar);

        flLogoToolbar.setOnClickListener(v ->
        {
            Intent returnIntent = new Intent();

            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        });

        tvPointScore = findViewById(R.id.tvPointScore);
        tvAllPredictCount = findViewById(R.id.tvAllPredictCount);
        tvTruePredictCount = findViewById(R.id.tvTruePredictCount);
        tvYourRate = findViewById(R.id.tvYourRate);

        cardPoints = findViewById(R.id.cardPoints);
        scrollView = findViewById(R.id.nested);

        recyclerView = findViewById(R.id.recyclerView);

        scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) ->
        {
            if (scrollY - oldScrollY > 0)
            {
                Animation animHide = AnimationUtils.loadAnimation(MyPredictsActivity.this, R.anim.hide_button);
                cardPoints.startAnimation(animHide);
                cardPoints.setVisibility(View.GONE);
            }
            else
            {
                Animation animShow = AnimationUtils.loadAnimation(MyPredictsActivity.this, R.anim.show_button);
                cardPoints.startAnimation(animShow);
                cardPoints.setVisibility(View.VISIBLE);
            }
        });

        getData();

    }

    private void getData()
    {
        showLoading();
        SingletonService.getInstance().getPredictService().getMyPredictService(this);
    }

    @Override
    public void onReady(WebServiceClass<MyPredictResponse> response)
    {
        hideLoading();
        if (response == null || response.data == null)
        {
            showAlertFailure(this, "خطا در دریافت اطلاعات از سرور!", getString(R.string.error), true);
            Logger.e("-MyPredictResponse-", "null");
            return;
        }
        if (response.info.statusCode != 200)
        {
            showAlertFailure(this, response.info.message, getString(R.string.error), true);
        }
        else
        {
            tvPointScore.setText(String.valueOf(response.data.getBundle().getBalance()));
            tvAllPredictCount.setText("تعداد پیش بینی های شرکت کننده: " + response.data.getBundle().getAllMyPredictions());
            tvTruePredictCount.setText("تعداد پیش بینی های درست: " + response.data.getBundle().getMyPredictionsTrue());
            tvYourRate.setText("رتبه شما: " + response.data.getBundle().getMyRank());

            recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
            recyclerView.setAdapter(new MyPredictAdapter(this, response.data.getMyPredictResults(), this));
        }
    }

    @Override
    public void onError(String message)
    {
        hideLoading();
        showAlertFailure(this, "خطا در دریافت اطلاعات از سرور!", getString(R.string.error), true);
    }

    @Override
    public void showLoading()
    {
        findViewById(R.id.rlLoading).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading()
    {
        findViewById(R.id.rlLoading).setVisibility(View.GONE);
    }

    @Override
    public void onShowWinnerList(Integer matchId)
    {
        LotteryWinnerListDialog dialog = new LotteryWinnerListDialog(matchId, this);
        dialog.show(getFragmentManager(), "predictWinListDialog");
    }

    @Override
    public void onHomeTeamClick(Integer teamId)
    {

    }

    @Override
    public void onAwayTeamClick(Integer teamId)
    {

    }

    @Override
    public void showAlertFailure(String message)
    {
        showAlertFailure(this, message, "خطا!", false);
    }

    @Override
    public void onShowDetailWinnerList(List<Winner> winnerList)
    {
        Intent intent = new Intent(this, LotteryWinnerDetailsActivity.class);
        intent.putParcelableArrayListExtra("winnerList", (ArrayList<? extends Parcelable>) winnerList);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
        {
            Intent returnIntent = new Intent();

            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }
}
