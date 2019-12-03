package com.traap.traapapp.ui.fragments.media;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.traap.traapapp.R;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.enums.MediaPosition;
import com.traap.traapapp.enums.NewsParent;
import com.traap.traapapp.models.otherModels.mediaModel.MediaModel;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.main.MainActivity;
import com.traap.traapapp.ui.activities.userProfile.UserProfileActivity;
import com.traap.traapapp.ui.adapters.media.MediaAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.fragments.news.NewsActionView;
import com.traap.traapapp.ui.fragments.photo.PhotosFragment;
import com.traap.traapapp.ui.fragments.news.mainNews.NewsMainContentFragment;
import com.traap.traapapp.ui.fragments.videos.VideosFragment;


@SuppressLint("ValidFragment")
public class MediaFragment extends BaseFragment implements MediaAdapter.OnItemAllMenuClickListener, NewsActionView
{
    private View rootView;
    private MainActionView mainView;

    private Toolbar mToolbar;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private MediaAdapter adapter;

    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private MediaPosition mediaPosition;
    private View rlShirt;

    public MediaFragment()
    {

    }

    public static MediaFragment newInstance(MediaPosition mediaPosition, MainActionView mainView)
    {
        MediaFragment f = new MediaFragment();
        f.setMainView(mainView);
        f.setMediaPosition(mediaPosition);
        return f;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    private void setMediaPosition(MediaPosition mediaPosition)
    {
        this.mediaPosition = mediaPosition;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_media, container, false);

        mToolbar = rootView.findViewById(R.id.toolbar);

        mToolbar.findViewById(R.id.imgMenu).setOnClickListener(v -> mainView.openDrawer());
        mToolbar.findViewById(R.id.imgBack).setOnClickListener(rootView -> mainView.backToMainFragment());
        TextView tvUserName = mToolbar.findViewById(R.id.tvUserName);
        TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvTitle.setText("رسانه");
        rlShirt=rootView.findViewById(R.id.rlShirt);
        rlShirt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
              //  startActivity(new Intent(SingletonContext.getInstance().getContext(), UserProfileActivity.class));

            }
        });
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);

        initView();

        return rootView;
    }


    public void initView()
    {
        try
        {

            fragmentManager = getChildFragmentManager();

            recyclerView = rootView.findViewById(R.id.recyclerView);
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);
            recyclerView.setLayoutManager(layoutManager);

            List<MediaModel> list = new ArrayList<>();

            MediaModel item = new MediaModel();
            item.setId(1);
            item.setTitle("اخبار");
            item.setIconDrawable(R.drawable.ic_news);
            item.setIconDrawableSelected(R.drawable.ic_news_selected);
            list.add(item);

            item = new MediaModel();
            item.setId(2);
            item.setTitle("عکس");
            item.setIconDrawable(R.drawable.ic_image);
            item.setIconDrawableSelected(R.drawable.ic_image_selected);
            list.add(item);

            item = new MediaModel();
            item.setId(3);
            item.setTitle("ویدیو");
            item.setIconDrawable(R.drawable.ic_movie);
            item.setIconDrawableSelected(R.drawable.ic_movie_selected);
            list.add(item);

            adapter = new MediaAdapter(getActivity(), list, MediaFragment.this);
            recyclerView.setAdapter(adapter);

            //MediaPosition.valueOf(mediaPosition.name()).ordinal()
            recyclerView.scrollToPosition(mediaPosition.ordinal() - 1);
        } catch (Exception e)
        {
            e.getMessage();
        }
    }

    @Override
    public void OnItemAllMenuClick(View view, Integer id)
    {
        switch (id)
        {
            case 1://اخبار
            {
//                fragment = NewsArchiveFragment.newInstance(this);
                fragment = NewsMainContentFragment.newInstance(NewsParent.MediaFragment, MainActivity.newsMainResponse, this);
                transaction = fragmentManager.beginTransaction();
//                        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

//                transaction.replace(R.id.main_container, fragment, "newsArchiveCategoryFragment")
                transaction.replace(R.id.main_container, fragment, "newsMainContentFragment")
                        .commit();
                break;
            }
            case 2://عکس
            {
                fragment = PhotosFragment.newInstance(mainView);
                transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

                transaction.replace(R.id.main_container, fragment, "marketFragment")
                        .commit();
                break;
            }
            case 3://فیلم
            {
                fragment = VideosFragment.newInstance(mainView);
                transaction = fragmentManager.beginTransaction();
//                transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

                transaction.replace(R.id.main_container, fragment, "videosFragment")
                        .commit();
                break;
            }
        }
    }

    @Override
    public void backToMainNewsFragment()
    {

    }

    @Override
    public void onNewsArchiveFragment(NewsParent parent)
    {
        mainView.onNewsArchiveClick(parent, MediaPosition.News);
    }

    @Override
    public void onNewsFavoriteFragment(NewsParent parent)
    {
        mainView.onNewsFavoriteClick(parent, MediaPosition.News);
    }

    @Override
    public void openDrawerNews()
    {

    }

    @Override
    public void closeDrawerNews()
    {

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
