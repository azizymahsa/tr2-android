package com.traap.traapapp.ui.fragments.galleryIntroPlayer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.moeidbannerlibrary.banner.BannerLayout;
import com.google.android.material.tabs.TabLayout;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.categoryByIdVideo.CategoryByIdVideosResponse;
import com.traap.traapapp.apiServices.model.mainPhotos.MainPhotoResponse;
import com.traap.traapapp.apiServices.model.mainVideos.Category;
import com.traap.traapapp.apiServices.model.mainVideos.ListCategory;
import com.traap.traapapp.apiServices.model.mainVideos.MainVideosResponse;
import com.traap.traapapp.apiServices.model.photo.response.ImageName;
import com.traap.traapapp.enums.SubMediaParent;
import com.traap.traapapp.ui.activities.photo.AlbumDetailActivity;
import com.traap.traapapp.ui.activities.video.VideoDetailActivity;
import com.traap.traapapp.ui.adapters.photo.CategoryPhotosAdapter;
import com.traap.traapapp.ui.adapters.photo.NewestPhotosAdapter;
import com.traap.traapapp.ui.adapters.photo.PhotosArchiveAdapter;
import com.traap.traapapp.ui.adapters.photo.PhotosCategoryTitleAdapter;
import com.traap.traapapp.ui.adapters.video.CategoryAdapter;
import com.traap.traapapp.ui.adapters.video.NewestVideosAdapter;
import com.traap.traapapp.ui.adapters.video.VideosCategoryTitleAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.photo.PhotosActionView;
import com.traap.traapapp.ui.fragments.videos.VideosActionView;
import com.traap.traapapp.ui.fragments.videos.VideosFragment;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

import static com.traap.traapapp.utilities.Utility.changeFontInViewGroup;

/**
 * Created by MahsaAzizi on 26/05/2020.
 */
public class GalleryVideoFragment extends BaseFragment implements VideosCategoryTitleAdapter.TitleCategoryListener,
        View.OnClickListener, NewestVideosAdapter.NewestVideoListener, CategoryAdapter.CategoryListener {
    private VideosActionView mainView;
    private View rootView;
    private LinearLayout rlLoading;
    public Integer bNewestVideoPosition;
    public Integer categoryClickPosition;
    public Integer categoryClickPositionX;
    public Integer categoryClickPositionY;
    private Context context;
    private FrameLayout flProgress;

    private BannerLayout bNewestVideo;
    private RoundedImageView ivFavorite1, ivFavorite2, ivFavorite3;
    private RecyclerView rvCategoryTitles, rvCategories;
    private VideosCategoryTitleAdapter videoCategoryTitleAdapter;
    private Integer idCategoryTitle = 0;
    private CategoryAdapter categoryAdapter;
    private MainVideosResponse mainVideosResponse;
    private ArrayList<Category> categoriesList;
    private int position = 0;
    private Integer idVideo;
    private Integer id;
    private View rlShirt;
    private NestedScrollView nestedScroll;
    private View tvEmpty;
    private View tvEmptyFavorite;
    private View llFavorites;
    private SubMediaParent parent;
    private ScrollingPagerIndicator indicatorNewestPhotos;
    private ListCategory category;
    private LinearLayout llTop;
    private ProgressBar pbMoreVideo;
    private Integer pageNumber = 1;
    private TabLayout tabLayout;
    ArrayList<Category> categories = new ArrayList<>();

    public GalleryVideoFragment() {

    }

    public static GalleryVideoFragment newInstance(SubMediaParent parent, VideosActionView mainView) {
        GalleryVideoFragment fragment = new GalleryVideoFragment();
        fragment.setMainView(mainView);
        fragment.setParent(parent);
        return fragment;
    }

    private void setParent(SubMediaParent parent) {
        this.parent = parent;
    }

    private void setMainView(VideosActionView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        YoYo.with(Techniques.FadeIn)
                .duration(700)
                .playOn(rootView);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView != null) {
            return rootView;
        }
        rootView = inflater.inflate(R.layout.fragment_videos_players, container, false);

        flProgress = rootView.findViewById(R.id.flProgress);
        pbMoreVideo = rootView.findViewById(R.id.pbMoreVideo);
        bNewestVideo = rootView.findViewById(R.id.bNewestPhotos);
        ivFavorite1 = rootView.findViewById(R.id.ivFavorite1);
        ivFavorite2 = rootView.findViewById(R.id.ivFavorite2);
        ivFavorite3 = rootView.findViewById(R.id.ivFavorite3);
        nestedScroll = rootView.findViewById(R.id.nestedScroll);
        tabLayout = rootView.findViewById(R.id.tabLayout);
        indicatorNewestPhotos = rootView.findViewById(R.id.indicatorNewestPhotos);
        rvCategoryTitles = rootView.findViewById(R.id.rvCategoryTitles);
        rvCategories = rootView.findViewById(R.id.rvCategories);
        llTop = rootView.findViewById(R.id.llTop);
        tvEmpty = rootView.findViewById(R.id.tvEmpty);
        tvEmptyFavorite = rootView.findViewById(R.id.tvEmptyFavorite);
        llFavorites = rootView.findViewById(R.id.llFavorites);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        rvCategoryTitles.setLayoutManager(layoutManager);
        ivFavorite1.setOnClickListener(this);
        ivFavorite2.setOnClickListener(this);
        ivFavorite3.setOnClickListener(this);
        LinearLayoutManager layoutManagerCategory = new LinearLayoutManager(getContext());
        rvCategories.setLayoutManager(layoutManagerCategory);

/*        nestedScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {


                if (scrollY == ( v.getMeasuredHeight() - v.getChildAt(0).getMeasuredHeight() )) {
                    if (pbMoreVideo.getVisibility()==View.GONE){
                        pageNumber++;
                        requestGetCategoryById(idCategoryTitle,true);

                        pbMoreVideo.setVisibility(View.VISIBLE);

                    }
                }
            }
        });*/
        nestedScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {


                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    if (pbMoreVideo.getVisibility() == View.GONE) {
                        pbMoreVideo.setVisibility(View.VISIBLE);

                        pageNumber++;
                        requestGetCategoryById(idCategoryTitle, true, false);


                    }

                }
            }
        });


        // requestMainVideos();


        return rootView;
    }

    public void onResume() {
        super.onResume();
        requestMainVideos();

        //Call the method

    }

    private void requestMainVideos() {
        flProgress.setVisibility(View.VISIBLE);
        SingletonService.getInstance().getMainVideosService().getMainVideos(new OnServiceStatus<WebServiceClass<MainVideosResponse>>() {
            @Override
            public void onReady(WebServiceClass<MainVideosResponse> response) {
                flProgress.setVisibility(View.GONE);
                try {

                    if (response.info.statusCode == 200) {
                        mainVideosResponse = response.data;
                        onGetMainVideosSuccess(response.data);
                    } else {
                        //  Tools.showToast(getContext(), response.info.message, R.color.red);
                        Logger.e("-getMainVideos-", "status: " + response.info.statusCode);
                    }
                } catch (Exception e) {
                    //  Tools.showToast(getContext(), e.getMessage(), R.color.red);

                    Logger.e("-getMainVideos Exception-", "Exception: " + e.getMessage());
                }
            }

            @Override
            public void onError(String message) {
                flProgress.setVisibility(View.GONE);
                if (Tools.isNetworkAvailable((Activity) context)) {
                    Logger.e("-OnError-", "Error: " + message);
                    showError(context, "خطا در دریافت اطلاعات از سرور!");
                } else {
                    showAlert(context, R.string.networkErrorMessage, R.string.networkError);
                }
                // Tools.showToast(context, message, R.color.red);
            }
        });
    }

    private void onGetMainVideosSuccess(MainVideosResponse mainVideosResponse) {

        bNewestVideo.setAdapter(new NewestVideosAdapter(mainVideosResponse.getRecent(), this));
        setDataFavoriteList(mainVideosResponse);
        indicatorNewestPhotos.attachToRecyclerView(bNewestVideo.getmRecyclerView());

        bNewestVideo.setAutoPlaying(true);
        if (bNewestVideoPosition != null) {
            try {
                bNewestVideo.getmRecyclerView().scrollToPosition(bNewestVideoPosition);

            } catch (Exception e) {
                bNewestVideo.getmRecyclerView().scrollToPosition(0);

            }
        }
//        nestedScroll.post(() -> nestedScroll.smoothScrollTo(0, llPager.getTop()));

//        if (bNewestVideoPosition != null)
//        {
//            try
//            {
//                bNewestVideo.getmRecyclerView().scrollToPosition(bNewestVideoPosition);
//
//            } catch (Exception e)
//            {
//                bNewestVideo.getmRecyclerView().scrollToPosition(0);
//
//            }
//
//
//        }


        if (idCategoryTitle != 0) {
            flProgress.setVisibility(View.GONE);
            pbMoreVideo.setVisibility(View.GONE);


            return;
        }
        Collections.reverse( mainVideosResponse.getListCategories());
        for (ListCategory listCategory : mainVideosResponse.getListCategories()) {
            tabLayout.addTab(tabLayout.newTab().setText(listCategory.getTitle()));


        }
        tabLayout.setTabTextColors(getResources().getColor(R.color.black), getResources().getColor(R.color.borderColorRed));

        tabLayout.getTabAt(mainVideosResponse.getListCategories().size()-1).select();

        new Handler().postDelayed(
                new Runnable() {
                    @Override public void run() {
                        tabLayout.getTabAt(mainVideosResponse.getListCategories().size()-1).select();
                    }
                }, 100);
        changeFontInViewGroup(tabLayout,"fonts/iran_sans_normal.ttf");

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabLayout.setTabTextColors(getResources().getColor(R.color.black), getResources().getColor(R.color.borderColorRed));
                changeFontInViewGroup(tabLayout,"fonts/iran_sans_normal.ttf");
                categoryClickPosition = position;
                flProgress.setVisibility(View.VISIBLE);
                categoryClickPositionX = tabLayout.getScrollX();
                categoryClickPositionY = tabLayout.getScrollY();
                idCategoryTitle = mainVideosResponse.getListCategories().get(tab.getPosition()).getId();
                flProgress.setVisibility(View.VISIBLE);
                pageNumber = 1;
                requestGetCategoryById(mainVideosResponse.getListCategories().get(tab.getPosition()).getId(), false, false);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        requestGetCategoryById(mainVideosResponse.getListCategories().get(mainVideosResponse.getListCategories().size()-1).getId(), false, true);




     /*   videoCategoryTitleAdapter = new VideosCategoryTitleAdapter(mainVideosResponse.getListCategories(), this);
        rvCategoryTitles.setAdapter(videoCategoryTitleAdapter);*/
        // idCategoryTitle = mainVideosResponse.getListCategories().get(0).getId();

        // requestGetCategoryById(mainVideosResponse.getListCategories().get(0).getId(), false, true);
        rvCategories.setNestedScrollingEnabled(false);
     /*   if (categoryClickPosition != null) {
            try {
                rvCategoryTitles.scrollTo(categoryClickPositionX, categoryClickPositionY);
                flProgress.setVisibility(View.VISIBLE);

                idCategoryTitle = mainVideosResponse.getListCategories().get(0).getId();
                flProgress.setVisibility(View.VISIBLE);

                requestGetCategoryById(idCategoryTitle, false, true);
                videoCategoryTitleAdapter.setSelectedPosition(categoryClickPosition);


  *//*              RecyclerView.ViewHolder viewHolder = rvCategoryTitles.findViewHolderForAdapterPosition(position);


                ((VideosCategoryTitleAdapter.ViewHolder ) viewHolder).tvTitle.setTextColor(context.getResources().getColor(R.color.borderColorRed));
                ((VideosCategoryTitleAdapter.ViewHolder ) viewHolder).tvTitle.setBackgroundResource(R.drawable.background_border_a);*//*
            } catch (Exception e) {
                rvCategoryTitles.scrollToPosition(0);
                idCategoryTitle = category.getId();
                flProgress.setVisibility(View.VISIBLE);

                requestGetCategoryById(idCategoryTitle, false, true);
            }
        }*/
    }

    private void setDataFavoriteList(MainVideosResponse mainVideosResponse) {
        if (!mainVideosResponse.getFavorites().isEmpty()) {
            llFavorites.setVisibility(View.VISIBLE);
            tvEmptyFavorite.setVisibility(View.GONE);
            List<Category> favoriteList = mainVideosResponse.getFavorites();
            setImageBackground(ivFavorite1, favoriteList.get(0).getBigPoster().replace("\\", ""));
            setImageBackground(ivFavorite2, favoriteList.get(1).getBigPoster().replace("\\", ""));
            setImageBackground(ivFavorite3, favoriteList.get(2).getBigPoster().replace("\\", ""));
        } else {
            llFavorites.setVisibility(View.GONE);
            tvEmptyFavorite.setVisibility(View.VISIBLE);
        }

    }

    private void setImageBackground(ImageView image, String link) {
        try {
            Glide.with(getContext()).load(Uri.parse(link)).into(image);
         /*   Picasso.with(getContext()).load(Uri.parse(link)).into(image, new Callback()
            {
                @Override
                public void onSuccess() { }

                @Override
                public void showErrorMessage()
                {
                    Picasso.with(getContext()).load(R.drawable.img_failure).into(image);
                }
            });*/
        } catch (NullPointerException e) {
            Picasso.with(getContext()).load(R.drawable.img_failure).into(image);
        }
    }

    @Override
    public void onItemTitleCategoryClick(ListCategory category, int position) {

        this.category = category;
        categoryClickPosition = position;
        flProgress.setVisibility(View.VISIBLE);
        categoryClickPositionX = rvCategoryTitles.getScrollX();
        categoryClickPositionY = rvCategoryTitles.getScrollY();
        idCategoryTitle = category.getId();
        flProgress.setVisibility(View.VISIBLE);
        pageNumber = 1;

        requestGetCategoryById(idCategoryTitle, false, false);
    }

    private void requestGetCategoryById(Integer idCategoryTitle, Boolean isPage, boolean isFirst) {

        SingletonService.getInstance().categoryByIdVideosService().categoryByIdVideosService(pageNumber, 10, idCategoryTitle, new OnServiceStatus<WebServiceClass<CategoryByIdVideosResponse>>() {
            @Override
            public void onReady(WebServiceClass<CategoryByIdVideosResponse> response) {
                try {
                    if (response.info.statusCode == 200) {
                        if (isFirst) {
                            flProgress.setVisibility(View.GONE);
                            pbMoreVideo.setVisibility(View.GONE);
                            rvCategories.setVisibility(View.VISIBLE);
                            categories.clear();
                            categories.addAll(response.data.getResults());
                            categoryAdapter = new CategoryAdapter(categories, GalleryVideoFragment.this);
                            rvCategories.setAdapter(categoryAdapter);


                            return;
                        }

                        setCategoryListData(response.data, isPage);

                    } else {
                        pbMoreVideo.setVisibility(View.GONE);
                        //  Tools.showToast(getContext(), response.info.message, R.color.red);
                    }
                } catch (Exception e) {
                    pbMoreVideo.setVisibility(View.GONE);

                    //  Tools.showToast(getContext(), e.getMessage(), R.color.red);
                }
            }

            @Override
            public void onError(String message) {
                flProgress.setVisibility(View.GONE);
                pbMoreVideo.setVisibility(View.GONE);
                if (Tools.isNetworkAvailable((Activity) context)) {
                    Logger.e("-OnError-", "Error: " + message);
                    showError(context, "خطا در دریافت اطلاعات از سرور!");
                } else {
                    showAlert(context, R.string.networkErrorMessage, R.string.networkError);
                }
                // Tools.showToast(context, message, R.color.red);
            }
        });
    }

    @SuppressLint("CheckResult")
    private void setCategoryListData(CategoryByIdVideosResponse data, Boolean isPage) {
        if (data.getResults().isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            rvCategories.setVisibility(View.GONE);
            flProgress.setVisibility(View.GONE);

        } else {
            tvEmpty.setVisibility(View.GONE);
            //  pbMoreVideo.setVisibility(View.GONE);


            if (isPage) {
                categories.addAll(data.getResults());
                categoryAdapter.notifyItemRangeChanged(categories.size() - 1, data.getResults().size());
                flProgress.setVisibility(View.GONE);

                new Handler().postDelayed(() -> pbMoreVideo.setVisibility(View.GONE), 700);

                return;
            }
            pbMoreVideo.setVisibility(View.GONE);

            rvCategories.setVisibility(View.VISIBLE);
            categories.clear();
            categories.addAll(data.getResults());
            categoryAdapter = new CategoryAdapter(categories, this);
            rvCategories.setAdapter(categoryAdapter);
            Observable.just(rvCategories.getHeight())
                    .subscribeOn(Schedulers.io())
                    .delay(250, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(scrollTo -> {

                        flProgress.setVisibility(View.GONE);

                        nestedScroll.post(() -> {
                            nestedScroll.scrollTo(0, llTop.getHeight());
                        });
                    });

        }

           /* new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                  //  nestedScroll.startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
                //    nestedScroll.dispatchNestedPreScroll(0, rvCategoryTitles.getHeight()+bNewestVideo.getHeight(), null, null);
                 //   nestedScroll.dispatchNestedScroll(0, 0, 0, 0, new int[]{0, -(rvCategoryTitles.getHeight()+bNewestVideo.getHeight())});
                    nestedScroll.smoothScrollBy(0,bNewestVideo.getScrollY()-llFavorites.getScrollY());
                }
            },50);*/


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivFavorite1:
                if (mainVideosResponse != null) {
                    categoriesList = mainVideosResponse.getFavorites();
                    position = 0;
                    idVideo = mainVideosResponse.getFavorites().get(position).getCategoryId();
                    id = mainVideosResponse.getFavorites().get(position).getId();
                    openVideoDetail(categoriesList, position, idVideo, id);
                }
                break;

            case R.id.ivFavorite2:
                if (mainVideosResponse != null) {
                    categoriesList = mainVideosResponse.getFavorites();
                    position = 1;
                    idVideo = mainVideosResponse.getFavorites().get(position).getCategoryId();
                    id = mainVideosResponse.getFavorites().get(position).getId();
                    openVideoDetail(categoriesList, position, idVideo, id);
                }
                break;

            case R.id.ivFavorite3:
                if (mainVideosResponse != null) {
                    categoriesList = mainVideosResponse.getFavorites();
                    position = 2;
                    idVideo = mainVideosResponse.getFavorites().get(position).getCategoryId();
                    id = mainVideosResponse.getFavorites().get(position).getId();
                    openVideoDetail(categoriesList, position, idVideo, id);
                }
                break;


        }
    }

    private void openVideoDetail(ArrayList<Category> categoriesList, int position, Integer idVideo, Integer id) {
        Intent intent = new Intent(context, VideoDetailActivity.class);

        intent.putParcelableArrayListExtra("Videos", categoriesList);
        intent.putExtra("IdVideoCategory", idVideo);
        intent.putExtra("IdVideo", id);
        intent.putExtra("positionVideo", position);
        intent.putExtra("idCategoryTitle", idCategoryTitle);

        startActivityForResult(intent, 100);
    }

    @Override
    public void onItemNewestVideoClick(int position, Category category) {
        bNewestVideoPosition = position;

        categoriesList = mainVideosResponse.getRecent();
        openVideoDetail(categoriesList, position, category.getCategoryId(), category.getId());
    }

    @Override
    public void onItemCategoryClick(int position, Category category, ArrayList<Category> categories) {
        categoriesList = categories;
        openVideoDetail(categoriesList, position, category.getCategoryId(), category.getId());
    }
}
