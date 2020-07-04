package com.traap.traapapp.ui.activities.trueFormationActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.pixplicity.easyprefs.library.Prefs;
import com.traap.traapapp.R;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.base.BaseActivity;
import com.traap.traapapp.ui.base.BaseView;
import com.traap.traapapp.ui.fragments.predict.predictSystemTeam.PredictSystemTeamFragment;
import com.traap.traapapp.utilities.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class TrueFormationActivity extends BaseActivity implements BaseView
{
    private Toolbar mToolbar;
    private TextView tvUserName, tvHeaderPopularNo;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private int matchId;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_true_formation);

        mToolbar = findViewById(R.id.toolbar);

        mToolbar.findViewById(R.id.imgMenu).setVisibility(View.GONE);
        mToolbar.findViewById(R.id.imgBack).setOnClickListener(rootView -> finish());
        tvUserName = mToolbar.findViewById(R.id.tvUserName);
        tvHeaderPopularNo = mToolbar.findViewById(R.id.tvPopularPlayer);
        tvHeaderPopularNo.setText(String.valueOf(Prefs.getInt("popularPlayer", 12)));
        TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvTitle.setText("ترکیب بازی");

        FrameLayout flLogoToolbar = mToolbar.findViewById(R.id.flLogoToolbar);
        flLogoToolbar.setOnClickListener(v ->
        {
            Intent returnIntent = new Intent();

            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        });

        findViewById(R.id.rlShirt).setOnClickListener(v -> startActivityForResult(new Intent(SingletonContext.getInstance().getContext(),
                MyProfileActivity.class),100));
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);

        matchId = getIntent().getIntExtra("matchId", 0);

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();

        showLoading();
        fragment = PredictSystemTeamFragment.newInstance(this, matchId, false);

        transaction.add(R.id.container, fragment, "predictSystemTeamFragment")
                .commit();

        EventBus.getDefault().register(this);
    }

    @Override
    public void showLoading()
    {
        Logger.e("--Loading--", "showLoading");
        findViewById(R.id.rlLoading).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading()
    {
        Logger.e("--Loading--", "hideLoading");
        findViewById(R.id.rlLoading).setVisibility(View.GONE);
    }

    @Subscribe
    public void getHeaderContent(HeaderModel headerModel)
    {
        if (headerModel.getPopularNo() != 0)
        {
            tvHeaderPopularNo.setText(String.valueOf(headerModel.getPopularNo()));
        }
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
