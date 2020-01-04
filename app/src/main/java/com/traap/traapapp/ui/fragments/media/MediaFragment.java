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

import com.pixplicity.easyprefs.library.Prefs;
import com.traap.traapapp.R;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.enums.MediaPosition;
import com.traap.traapapp.enums.SubMediaParent;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.models.otherModels.mediaModel.MediaModel;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.adapters.media.MediaAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.fragments.news.NewsActionView;
import com.traap.traapapp.ui.fragments.photo.PhotosActionView;
import com.traap.traapapp.ui.fragments.photo.PhotosFragment;
import com.traap.traapapp.ui.fragments.news.mainNews.NewsMainContentFragment;
import com.traap.traapapp.ui.fragments.videos.VideosFragment;
import com.traap.traapapp.utilities.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


@SuppressLint("ValidFragment")
public class MediaFragment extends BaseFragment implements MediaAdapter.OnItemAllMenuClickListener, NewsActionView, PhotosActionView
{
    private View rootView;
    private MainActionView mainView;

    private Toolbar mToolbar;
    private TextView tvUserName, tvHeaderPopularNo;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private MediaAdapter adapter;

    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private MediaPosition mediaPosition;
    private int myMediaType;
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
        tvUserName = mToolbar.findViewById(R.id.tvUserName);
        tvHeaderPopularNo = mToolbar.findViewById(R.id.tvPopularPlayer);
        TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvTitle.setText("رسانه");
        rlShirt = mToolbar.findViewById(R.id.rlShirt);
        rlShirt.setOnClickListener(v -> startActivity(new Intent(SingletonContext.getInstance().getContext(), MyProfileActivity.class)));
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);
        tvHeaderPopularNo.setText(String.valueOf(Prefs.getInt("popularPlayer", 12)));

        initView();

        EventBus.getDefault().register(this);
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

            myMediaType = mediaPosition.ordinal();
            Logger.e("-mediaPosition-", mediaPosition + "# myMediaType:" + myMediaType);

            adapter = new MediaAdapter(getActivity(), myMediaType, list, MediaFragment.this);
            recyclerView.setAdapter(adapter);

            //------------------------initPager----------------------
            switch (mediaPosition)
            {
                case News:
                {
                    myMediaType = MediaPosition.News.ordinal();

                    fragment = NewsMainContentFragment.newInstance(SubMediaParent.MediaFragment, null, this);
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.main_container, fragment, "newsMainContentFragment")
                            .commit();
                    break;
                }
                case ImageGallery:
                {
                    myMediaType = MediaPosition.ImageGallery.ordinal();

                    fragment = PhotosFragment.newInstance(this);
                    transaction = fragmentManager.beginTransaction();

                    transaction.replace(R.id.main_container, fragment, "photosFragment")
                            .commit();
                    break;
                }
                case VideoGallery:
                {
                    myMediaType = MediaPosition.VideoGallery.ordinal();

                    fragment = VideosFragment.newInstance(mainView);
                    transaction = fragmentManager.beginTransaction();

                    transaction.replace(R.id.main_container, fragment, "videosFragment")
                            .commit();
                    break;
                }
            }

//            recyclerView.scrollToPosition(myMediaType);
            recyclerView.scrollToPosition(1);
            //------------------------initPager----------------------

        }
        catch (Exception e)
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
                if (myMediaType != MediaPosition.News.ordinal())
                {
                    myMediaType = MediaPosition.News.ordinal();

                    fragment = NewsMainContentFragment.newInstance(SubMediaParent.MediaFragment, null, this);
                    transaction = fragmentManager.beginTransaction();

                    transaction.replace(R.id.main_container, fragment, "newsMainContentFragment")
                            .commit();
                }
                break;
            }
            case 2://عکس
            {
                if (myMediaType != MediaPosition.ImageGallery.ordinal())
                {
                    myMediaType = MediaPosition.ImageGallery.ordinal();

                    fragment = PhotosFragment.newInstance(this);
                    transaction = fragmentManager.beginTransaction();

                    transaction.replace(R.id.main_container, fragment, "photosFragment")
                            .commit();
                }
                break;
            }
            case 3://فیلم
            {
                if (myMediaType != MediaPosition.VideoGallery.ordinal())
                {
                    myMediaType = MediaPosition.VideoGallery.ordinal();

                    fragment = VideosFragment.newInstance(mainView);
                    transaction = fragmentManager.beginTransaction();

                    transaction.replace(R.id.main_container, fragment, "videosFragment")
                            .commit();
                }
                break;
            }
        }
    }

    @Override
    public void backToMainNewsFragment()
    {

    }

    @Override
    public void onNewsArchiveFragment(SubMediaParent parent)
    {
        mainView.onNewsArchiveClick(parent, MediaPosition.News);
    }

    @Override
    public void onNewsFavoriteFragment(SubMediaParent parent)
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

    @Override
    public void backToMainPhotosFragment()
    {

    }

    @Override
    public void onPhotosArchiveFragment(SubMediaParent parent)
    {
        mainView.onPhotosArchiveClick(parent, MediaPosition.ImageGallery);
    }

    @Override
    public void onPhotosFavoriteFragment(SubMediaParent parent)
    {
        mainView.onPhotosFavoriteClick(parent, MediaPosition.ImageGallery);
    }

    @Override
    public void openDrawerPhotos()
    {

    }

    @Override
    public void closeDrawerPhotos()
    {

    }
}
