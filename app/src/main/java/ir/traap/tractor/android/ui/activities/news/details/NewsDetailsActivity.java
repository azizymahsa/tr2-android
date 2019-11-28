package ir.traap.tractor.android.ui.activities.news.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import ir.traap.tractor.android.R;
import ir.traap.tractor.android.conf.TrapConfig;
import ir.traap.tractor.android.models.otherModels.newsModel.NewsDetailsPositionIdsModel;
import ir.traap.tractor.android.ui.base.BaseActivity;

public class NewsDetailsActivity extends BaseActivity
{
    private Toolbar mToolbar;
    private List<NewsDetailsPositionIdsModel> positionIdsList;
    private Integer currentId = 0, currentPosition = 0;

    private TextView tvPrevNews, tvNextNews;

    private Fragment newsDetailFragment, newsRelatedFragment, newsCommentFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        initView();
    }

    private void initView()
    {
        mToolbar = findViewById(R.id.toolbar);

//        mToolbar.findViewById(R.id.imgMenu).setOnClickListener(v -> mainView.openDrawerNews());
        mToolbar.findViewById(R.id.imgMenu).setVisibility(View.INVISIBLE);
        mToolbar.findViewById(R.id.imgBack).setOnClickListener(rootView -> finish());
        TextView tvUserName = mToolbar.findViewById(R.id.tvUserName);
        TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvTitle.setText("جزئیات خبر");
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);

        currentId = getIntent().getIntExtra("currentId", 0);
        currentPosition = getIntent().getIntExtra("currentPosition", 0);
        positionIdsList = getIntent().getParcelableArrayListExtra("positionIdsList");

        tvPrevNews = findViewById(R.id.tvPrevNews);
        tvNextNews = findViewById(R.id.tvNextNews);

        getData(currentId);

        tvPrevNews.setOnClickListener(v ->
        {
            if (currentPosition != 0)
            {
                currentPosition--;
                currentId = positionIdsList.get(currentPosition).getId();

//                getPrevData
//                getData(currentId);
            }
        });

        tvNextNews.setOnClickListener(v ->
        {
            if (currentPosition != positionIdsList.size()-1)
            {
                currentPosition++;
                currentId = positionIdsList.get(currentPosition).getId();

//                getNextData
//                getData(currentId);
            }
        });


    }

    private void getData(Integer currentId)
    {
        //show Loading
        if (currentPosition == 0)
        {
            tvPrevNews.setAlpha(0.5f);
            tvPrevNews.setClickable(false);
            tvPrevNews.setEnabled(false);
            tvPrevNews.setActivated(false);
        }
        else
        {
            tvPrevNews.setAlpha(1f);
            tvPrevNews.setClickable(true);
            tvPrevNews.setEnabled(true);
            tvPrevNews.setActivated(true);
        }

        if (currentPosition == positionIdsList.size()-1)
        {
            tvNextNews.setAlpha(0.5f);
            tvNextNews.setClickable(false);
            tvNextNews.setEnabled(false);
            tvNextNews.setActivated(false);
        }
        else
        {
            tvNextNews.setAlpha(1f);
            tvNextNews.setClickable(true);
            tvNextNews.setEnabled(true);
            tvNextNews.setActivated(true);
        }
    }
}
