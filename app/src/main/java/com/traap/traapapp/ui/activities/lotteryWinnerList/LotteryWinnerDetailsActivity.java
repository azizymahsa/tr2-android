package com.traap.traapapp.ui.activities.lotteryWinnerList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.lottery.Winner;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.ui.adapters.lotteryWinnerList.LotteryPredictDetailsAdapter;
import com.traap.traapapp.ui.base.BaseActivity;
import com.traap.traapapp.ui.base.BaseView;

import java.util.List;

public class LotteryWinnerDetailsActivity extends BaseActivity implements BaseView
{
    private List<Winner> winnerList;

    private Toolbar mToolbar;
    private NestedScrollView scrollView;
    private RecyclerView recyclerView;
    private CardView cardPoints;
    //    private Integer matchId;
    private TextView tvWinnerCount;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery_winner_details);

        showLoading();

        winnerList = getIntent().getParcelableArrayListExtra("winnerList");

        mToolbar = findViewById(R.id.toolbar);

        mToolbar.findViewById(R.id.imgMenu).setVisibility(View.INVISIBLE);
        mToolbar.findViewById(R.id.imgBack).setOnClickListener(rootView -> finish());
        mToolbar.findViewById(R.id.flLogoToolbar).setOnClickListener(rootView ->
        {
            Intent returnIntent = new Intent();

            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        });
        TextView tvUserName = mToolbar.findViewById(R.id.tvUserName);
        TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvTitle.setText("لیست برندگان");
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);

        initView();
    }

    private void initView()
    {

        scrollView = findViewById(R.id.nested);
        cardPoints = findViewById(R.id.cardPoints);
        recyclerView = findViewById(R.id.recyclerView);
        tvWinnerCount = findViewById(R.id.tvWinnerCount);

        scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) ->
        {
            if (scrollY - oldScrollY > 0)
            {
                Animation animHide = AnimationUtils.loadAnimation(this, R.anim.hide_button);
                cardPoints.startAnimation(animHide);
                cardPoints.setVisibility(View.GONE);
            }
            else
            {
                Animation animShow = AnimationUtils.loadAnimation(this, R.anim.show_button);
                cardPoints.startAnimation(animShow);
                cardPoints.setVisibility(View.VISIBLE);
            }
        });

        tvWinnerCount.setText(String.valueOf(winnerList.size()));

        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(new LotteryPredictDetailsAdapter(this, winnerList));

        hideLoading();
    }

    @Override
    public void showLoading()
    {

    }

    @Override
    public void hideLoading()
    {

    }
}
