package com.traap.traapapp.ui.fragments.galleryIntroPlayer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pixplicity.easyprefs.library.Prefs;
import com.traap.traapapp.R;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.enums.MediaPosition;
import com.traap.traapapp.enums.SubMediaParent;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.models.otherModels.mediaModel.MediaModel;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.adapters.media.MediaAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.fragments.news.NewsActionView;
import com.traap.traapapp.ui.fragments.news.mainNews.NewsMainContentFragment;
import com.traap.traapapp.ui.fragments.photo.PhotosActionView;
import com.traap.traapapp.ui.fragments.photo.PhotosFragment;
import com.traap.traapapp.ui.fragments.videos.VideosActionView;
import com.traap.traapapp.ui.fragments.videos.VideosFragment;
import com.traap.traapapp.utilities.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("ValidFragment")
public class MediaPlayersFragment extends BaseFragment implements MediaAdapter.OnItemAllMenuClickListener,
        NewsActionView, PhotosActionView, VideosActionView, View.OnClickListener
{
    private View rootView;
    private MainActionView mainView;

    private Toolbar mToolbar;
    private TextView tvUserName, tvHeaderPopularNo;
    private TextView txtPic, txtVideo;

    private LinearLayoutManager layoutManager;
    private MediaAdapter adapter;

    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private MediaPosition mediaPosition;
    private int myMediaType;
    private View rlShirt;

    public MediaPlayersFragment()
    {

    }


    public static MediaPlayersFragment newInstance(MediaPosition mediaPosition, MainActionView mainView)
    {
        MediaPlayersFragment f = new MediaPlayersFragment();
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
        rootView = inflater.inflate(R.layout.fragment_media_player, container, false);

        mToolbar = rootView.findViewById(R.id.toolbar);

        mToolbar.findViewById(R.id.imgMenu).setOnClickListener(v -> mainView.openDrawer());
//        mToolbar.findViewById(R.id.imgBack).setOnClickListener(rootView -> mainView.backToMainFragment());
        mToolbar.findViewById(R.id.imgBack).setOnClickListener(rootView -> getActivity().onBackPressed());
//        mToolbar.findViewById(R.id.flLogoToolbar).setOnClickListener(rootView -> mainView.backToMainFragment());
        mToolbar.findViewById(R.id.flLogoToolbar).setOnClickListener(rootView -> getActivity().onBackPressed());
        tvUserName = mToolbar.findViewById(R.id.tvUserName);
        tvHeaderPopularNo = mToolbar.findViewById(R.id.tvPopularPlayer);
        TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvTitle.setText("گالری عکس و فیلم");
        rlShirt = mToolbar.findViewById(R.id.rlShirt);

        rlShirt.setOnClickListener(v -> startActivityForResult(new Intent(SingletonContext.getInstance().getContext(), MyProfileActivity.class), 100));
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);
        tvHeaderPopularNo.setText(String.valueOf(Prefs.getInt("popularPlayer", 12)));
        txtPic = rootView.findViewById(R.id.txtPic);
        txtVideo = rootView.findViewById(R.id.txtVideo);
        txtPic.setOnClickListener(this);
        txtVideo.setOnClickListener(this);


        initView();

        EventBus.getDefault().register(this);
        return rootView;
    }


    public void initView()
    {
        try
        {
            fragmentManager = getChildFragmentManager();


            //------------------------initPager----------------------
           /* switch (mediaPosition)
            {

                case ImageGallery:
                {*/
            myMediaType = MediaPosition.VideoGallery.ordinal();

            fragment = GalleryVideoFragment.newInstance(SubMediaParent.MediaFragment, this);
            transaction = fragmentManager.beginTransaction();

            transaction.replace(R.id.main_container, fragment, "GalleryVideoFragment")
                    .commit();

                /*    break;
                }
                case VideoGallery:
                {
                    myMediaType = MediaPosition.VideoGallery.ordinal();

                   myMediaType = MediaPosition.ImageGallery.ordinal();
                    fragment = GalleryPicFragment.newInstance(this);
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.main_container, fragment, "GalleryPicFragment").commit();

                    break;
                }
            }*/

//            recyclerView.scrollToPosition(myMediaType);
            // recyclerView.scrollToPosition(1);
            //------------------------initPager----------------------

        } catch (Exception e)
        {
            e.getMessage();
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {

            case R.id.txtPic:



                txtPic.setTextColor(getResources().getColor(R.color.textColorPrimary));
                txtPic.setBackgroundResource(R.drawable.background_border_a);
                txtVideo.setTextColor(getResources().getColor(R.color.returnButtonColor));
                txtVideo.setBackgroundResource(android.R.color.transparent);
                if (myMediaType != MediaPosition.ImageGallery.ordinal())
                {
                    myMediaType = MediaPosition.ImageGallery.ordinal();

                    fragment = GalleryPicFragment.newInstance(this);
                    transaction = fragmentManager.beginTransaction();

                    transaction.replace(R.id.main_container, fragment, "GalleryPicFragment")
                            .commit();
                    mainView.onChangeMediaPosition(MediaPosition.ImageGallery);
                }
                break;
            case R.id.txtVideo:

                txtPic.setTextColor(getResources().getColor(R.color.returnButtonColor));
                txtPic.setBackgroundResource(android.R.color.transparent);
                txtVideo.setTextColor(getResources().getColor(R.color.textColorPrimary));
                txtVideo.setBackgroundResource(R.drawable.background_border_a);
                if (myMediaType != MediaPosition.VideoGallery.ordinal())
                {
                    myMediaType = MediaPosition.VideoGallery.ordinal();

                    fragment = GalleryVideoFragment.newInstance(SubMediaParent.MediaFragment, this);
                    transaction = fragmentManager.beginTransaction();

                    transaction.replace(R.id.main_container, fragment, "GalleryVideoFragment")
                            .commit();
                    mainView.onChangeMediaPosition(MediaPosition.VideoGallery);
                }


                break;
        }
    }


    @Override
    public void backToMainNewsFragment()
    {
        getActivity().onBackPressed();
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
        getActivity().onBackPressed();
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

    @Override
    public void backToMainVideosFragment()
    {
    }

    @Override
    public void onVideosArchiveFragment(SubMediaParent parent)
    {
        mainView.onVideosArchiveClick(parent, MediaPosition.VideoGallery);
    }

    @Override
    public void onVideosFavoriteFragment(SubMediaParent parent)
    {
        mainView.onVideosFavoriteClick(parent, MediaPosition.VideoGallery);
    }

    @Override
    public void openDrawerVideos()
    {
    }

    @Override
    public void closeDrawerVideos()
    {
    }

    @Override
    public void OnItemAllMenuClick(View view, Integer id)
    {

    }
}
