package com.traap.traapapp.ui.fragments.videos;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.pixplicity.easyprefs.library.Prefs;
import com.traap.traapapp.R;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.enums.SubMediaParent;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by MahtabAzizi on 11/30/2019.
 */
public class VideosMainFragment  extends BaseFragment implements VideosActionView
{


    private View rootView;
    private VideosMainActionView mainView;

    private Toolbar mToolbar;
    private TextView tvUserName, tvHeaderPopularNo;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private View rlShirt;

    public VideosMainFragment()
    {

    }

    public static VideosMainFragment newInstance(VideosMainActionView mainView)
    {
        VideosMainFragment f = new VideosMainFragment();
        f.setMainView(mainView);
        return f;
    }

    private void setMainView(VideosMainActionView mainView)
    {
        this.mainView = mainView;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_videos_main, container, false);

        mToolbar = rootView.findViewById(R.id.toolbar);

        mToolbar.findViewById(R.id.imgMenu).setOnClickListener(v -> mainView.openDrawerVideos());
        mToolbar.findViewById(R.id.imgBack).setOnClickListener(rootView -> mainView.backToMainFragment());
        tvUserName = mToolbar.findViewById(R.id.tvUserName);
        tvHeaderPopularNo = mToolbar.findViewById(R.id.tvPopularPlayer);
        tvHeaderPopularNo.setText(String.valueOf(Prefs.getInt("popularPlayer", 12)));
        TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvTitle.setText("ویدیو");
        FrameLayout flLogoToolbar = mToolbar.findViewById(R.id.flLogoToolbar);
        flLogoToolbar.setOnClickListener(v -> {
            mainView.backToMainFragment();

        });
        rlShirt=rootView.findViewById(R.id.rlShirt);
        rlShirt.setOnClickListener(v -> startActivityForResult(new Intent(SingletonContext.getInstance().getContext(), MyProfileActivity.class),100));
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);

        fragmentManager = getChildFragmentManager();

        fragment = VideosFragment.newInstance(SubMediaParent.MainFragment, this);
        transaction = fragmentManager.beginTransaction();
//                        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        transaction.replace(R.id.container, fragment, "videosFragment")
                .commit();


        EventBus.getDefault().register(this);
        return rootView;
    }

    @Override
    public void backToMainVideosFragment()
    { }

    @Override
    public void onVideosArchiveFragment(SubMediaParent parent)
    {
        mainView.onVideosArchiveFragment(parent);
    }

    @Override
    public void onVideosFavoriteFragment(SubMediaParent parent)
    {
        mainView.onVideosFavoriteFragment(parent);
    }

    @Override
    public void openDrawerVideos()
    {
        mainView.openDrawerVideos();
    }

    @Override
    public void closeDrawerVideos()
    {
        mainView.closeDrawerVideos();
    }

    @Override
    public void showLoading()
    {
        mainView.showLoading();
    }

    @Override
    public void hideLoading()
    {
        mainView.hideLoading();
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
