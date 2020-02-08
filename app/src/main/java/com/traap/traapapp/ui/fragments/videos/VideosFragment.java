package com.traap.traapapp.ui.fragments.videos;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.moeidbannerlibrary.banner.BannerLayout;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.categoryByIdVideo.CategoryByIdVideosResponse;
import com.traap.traapapp.apiServices.model.mainVideos.Category;
import com.traap.traapapp.apiServices.model.mainVideos.MainVideosResponse;
import com.traap.traapapp.enums.SubMediaParent;
import com.traap.traapapp.ui.activities.video.VideoDetailActivity;
import com.traap.traapapp.ui.adapters.video.NewestVideosAdapter;
import com.traap.traapapp.apiServices.model.mainVideos.ListCategory;
import com.traap.traapapp.ui.adapters.video.CategoryAdapter;
import com.traap.traapapp.ui.adapters.video.VideosCategoryTitleAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.media.MediaFragment;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;

/**
 * Created by MahtabAzizi on 11/23/2019.
 */
public class VideosFragment extends BaseFragment implements VideosCategoryTitleAdapter.TitleCategoryListener,
        View.OnClickListener, NewestVideosAdapter.NewestVideoListener, CategoryAdapter.CategoryListener
{
    private VideosActionView mainView;
    private View rootView;
    private LinearLayout llPager;

    private Context context;

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
    private TextView tvArchiveVideo, tvMyFavoriteVideo;
    private View rlShirt;
    private NestedScrollView nestedScroll;
    private View tvEmpty;
    private View tvEmptyFavorite;
    private View llFavorites;
    private SubMediaParent parent;
    private ScrollingPagerIndicator indicatorNewestPhotos;
    private ListCategory category;
    private LinearLayout llTop;

    public VideosFragment()
    {

    }

    public static VideosFragment newInstance(SubMediaParent parent, VideosActionView mainView)
    {
        VideosFragment fragment = new VideosFragment();
        fragment.setMainView(mainView);
        fragment.setParent(parent);
        return fragment;
    }

    private void setParent(SubMediaParent parent)
    {
        this.parent = parent;
    }

    private void setMainView(VideosActionView mainView)
    {
        this.mainView = mainView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        YoYo.with(Techniques.FadeIn)
                .duration(700)
                .playOn(rootView);
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (rootView != null)
        {
            return rootView;
        }
        rootView = inflater.inflate(R.layout.fragment_videos, container, false);

        llPager = rootView.findViewById(R.id.llPager);
        bNewestVideo = rootView.findViewById(R.id.bNewestPhotos);
        ivFavorite1 = rootView.findViewById(R.id.ivFavorite1);
        ivFavorite2 = rootView.findViewById(R.id.ivFavorite2);
        ivFavorite3 = rootView.findViewById(R.id.ivFavorite3);
        nestedScroll = rootView.findViewById(R.id.nestedScroll);
        indicatorNewestPhotos = rootView.findViewById(R.id.indicatorNewestPhotos);
        rvCategoryTitles = rootView.findViewById(R.id.rvCategoryTitles);
        tvArchiveVideo = rootView.findViewById(R.id.tvArchivePhotos);
        rvCategories = rootView.findViewById(R.id.rvCategories);
        llTop = rootView.findViewById(R.id.llTop);
        tvEmpty = rootView.findViewById(R.id.tvEmpty);
        tvEmptyFavorite = rootView.findViewById(R.id.tvEmptyFavorite);
        llFavorites = rootView.findViewById(R.id.llFavorites);
        tvMyFavoriteVideo = rootView.findViewById(R.id.tvMyFavoriteVideo);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        rvCategoryTitles.setLayoutManager(layoutManager);
        tvMyFavoriteVideo.setOnClickListener(this);
        ivFavorite1.setOnClickListener(this);
        ivFavorite2.setOnClickListener(this);
        ivFavorite3.setOnClickListener(this);
        tvArchiveVideo.setOnClickListener(this);
        LinearLayoutManager layoutManagerCategory = new LinearLayoutManager(getContext());
        rvCategories.setLayoutManager(layoutManagerCategory);
        // requestMainVideos();

        return rootView;
    }

    public void onResume()
    {
        super.onResume();

        //Call the method
        requestMainVideos();
    }

    private void requestMainVideos()
    {
        mainView.showLoading();
        SingletonService.getInstance().getMainVideosService().getMainVideos(new OnServiceStatus<WebServiceClass<MainVideosResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<MainVideosResponse> response)
            {
                mainView.hideLoading();
                try
                {

                    if (response.info.statusCode == 200)
                    {
                        mainVideosResponse = response.data;
                        onGetMainVideosSuccess(response.data);
                    }
                    else
                    {
                        //  Tools.showToast(getContext(), response.info.message, R.color.red);
                        Logger.e("-getMainVideos-", "status: " + response.info.statusCode);
                    }
                } catch (Exception e)
                {
                    //  Tools.showToast(getContext(), e.getMessage(), R.color.red);

                    Logger.e("-getMainVideos Exception-", "Exception: " + e.getMessage());
                }
            }

            @Override
            public void onError(String message)
            {
                mainView.hideLoading();
                if (Tools.isNetworkAvailable((Activity) context))
                {
                    Logger.e("-OnError-", "Error: " + message);
                    showError(context, "خطا در دریافت اطلاعات از سرور!");
                }
                else
                {
                    showAlert(context, R.string.networkErrorMessage, R.string.networkError);
                }
                // Tools.showToast(context, message, R.color.red);
            }
        });
    }

    private void onGetMainVideosSuccess(MainVideosResponse mainVideosResponse)
    {
        bNewestVideo.setAdapter(new NewestVideosAdapter(mainVideosResponse.getRecent(), this));
        setDataFavoriteList(mainVideosResponse);
        indicatorNewestPhotos.attachToRecyclerView(bNewestVideo.getmRecyclerView());

        bNewestVideo.setAutoPlaying(true);

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

        videoCategoryTitleAdapter = new VideosCategoryTitleAdapter(mainVideosResponse.getListCategories(), this);
        rvCategoryTitles.setAdapter(videoCategoryTitleAdapter);
        categoryAdapter = new CategoryAdapter(mainVideosResponse.getCategory(), this);
        rvCategories.setAdapter(categoryAdapter);
        rvCategories.setNestedScrollingEnabled(false);


        try
        {
            idCategoryTitle = category.getId();
            requestGetCategoryById(idCategoryTitle);

  /*              RecyclerView.ViewHolder viewHolder = rvCategoryTitles.findViewHolderForAdapterPosition(position);


                ((VideosCategoryTitleAdapter.ViewHolder ) viewHolder).tvTitle.setTextColor(context.getResources().getColor(R.color.borderColorRed));
                ((VideosCategoryTitleAdapter.ViewHolder ) viewHolder).tvTitle.setBackgroundResource(R.drawable.background_border_a);*/
        }
        catch (Exception e)
        {
//                rvCategoryTitles.scrollToPosition(0);
            idCategoryTitle = category.getId();
            requestGetCategoryById(idCategoryTitle);
        }
    }

    private void setDataFavoriteList(MainVideosResponse mainVideosResponse)
    {
        if (!mainVideosResponse.getFavorites().isEmpty())
        {
            llFavorites.setVisibility(View.VISIBLE);
            tvEmptyFavorite.setVisibility(View.GONE);
            List<Category> favoriteList = mainVideosResponse.getFavorites();
            setImageBackground(ivFavorite1, favoriteList.get(0).getBigPoster().replace("\\", ""));
            setImageBackground(ivFavorite2, favoriteList.get(1).getBigPoster().replace("\\", ""));
            setImageBackground(ivFavorite3, favoriteList.get(2).getBigPoster().replace("\\", ""));
        }
        else
        {
            llFavorites.setVisibility(View.GONE);
            tvEmptyFavorite.setVisibility(View.VISIBLE);
        }

    }

    private void setImageBackground(ImageView image, String link)
    {
        try
        {
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
        }
        catch (NullPointerException e)
        {
            Picasso.with(getContext()).load(R.drawable.img_failure).into(image);
        }
    }

    @Override
    public void onItemTitleCategoryClick(ListCategory category, int position)
    {

        this.category = category;

        idCategoryTitle = category.getId();
        requestGetCategoryById(idCategoryTitle);
    }

    private void requestGetCategoryById(Integer idCategoryTitle)
    {
        mainView.showLoading();

        SingletonService.getInstance().categoryByIdVideosService().categoryByIdVideosService(idCategoryTitle, new OnServiceStatus<WebServiceClass<CategoryByIdVideosResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<CategoryByIdVideosResponse> response)
            {
                mainView.hideLoading();
                try
                {
                    if (response.info.statusCode == 200)
                    {

                        setCategoryListData(response.data);

                    }
                    else
                    {
                        //  Tools.showToast(getContext(), response.info.message, R.color.red);
                    }
                }
                catch (Exception e)
                {
                    //  Tools.showToast(getContext(), e.getMessage(), R.color.red);
                }
            }

            @Override
            public void onError(String message)
            {
                mainView.hideLoading();
                if (Tools.isNetworkAvailable((Activity) context))
                {
                    Logger.e("-OnError-", "Error: " + message);
                    showError(context, "خطا در دریافت اطلاعات از سرور!");
                }
                else
                {
                    showAlert(context, R.string.networkErrorMessage, R.string.networkError);
                }
                // Tools.showToast(context, message, R.color.red);
            }
        });
    }

    @SuppressLint("CheckResult")
    private void setCategoryListData(CategoryByIdVideosResponse data)
    {
        if (data.getResults().isEmpty())
        {
            tvEmpty.setVisibility(View.VISIBLE);
            rvCategories.setVisibility(View.GONE);
        }
        else
        {
            tvEmpty.setVisibility(View.GONE);
            rvCategories.setVisibility(View.VISIBLE);
            categoryAdapter = new CategoryAdapter(data.getResults(), this);
            rvCategories.setAdapter(categoryAdapter);

            Observable.just(llTop.getHeight())
//                        .repeat(2)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(scrollTo ->
                    {
                        nestedScroll.post(() -> nestedScroll.smoothScrollTo(0, scrollTo));
                    });
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

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.ivFavorite1:
                if (mainVideosResponse != null)
                {
                    categoriesList = mainVideosResponse.getFavorites();
                    position = 0;
                    idVideo = mainVideosResponse.getFavorites().get(position).getCategoryId();
                    id = mainVideosResponse.getFavorites().get(position).getId();
                    openVideoDetail(categoriesList, position, idVideo, id);
                }
                break;

            case R.id.ivFavorite2:
                if (mainVideosResponse != null)
                {
                    categoriesList = mainVideosResponse.getFavorites();
                    position = 1;
                    idVideo = mainVideosResponse.getFavorites().get(position).getCategoryId();
                    id = mainVideosResponse.getFavorites().get(position).getId();
                    openVideoDetail(categoriesList, position, idVideo, id);
                }
                break;

            case R.id.ivFavorite3:
                if (mainVideosResponse != null)
                {
                    categoriesList = mainVideosResponse.getFavorites();
                    position = 2;
                    idVideo = mainVideosResponse.getFavorites().get(position).getCategoryId();
                    id = mainVideosResponse.getFavorites().get(position).getId();
                    openVideoDetail(categoriesList, position, idVideo, id);
                }
                break;
            case R.id.tvArchivePhotos:
            {
                mainView.onVideosArchiveFragment(parent);
//                try
//                {
//                    ArrayList<ListCategory> categoryTitleList = mainVideosResponse.getListCategories();
//                    Intent intent = new Intent(context, VideoArchiveActivity.class);
//                    intent.putParcelableArrayListExtra("CategoryTitle", categoryTitleList);
//                    startActivityForResult(intent,100);
//                } catch (Exception e)
//                {
//                    if (Tools.isNetworkAvailable(context))
//                    {
//                        Logger.e("-OnError-", "Error: " + e.getMessage());
//                        showError(context, "خطا در دریافت اطلاعات از سرور!");
//                    } else
//                    {
//                        showAlert(context, R.string.networkErrorMessage, R.string.networkError);
//                    }
//                }
                break;
            }
            case R.id.tvMyFavoriteVideo:
            {
                mainView.onVideosFavoriteFragment(parent);
//                try
//                {
//                    Intent intent1 = new Intent(context, VideoArchiveActivity.class);
//                    intent1.putExtra("FLAG_Favorite", true);
//                    startActivityForResult(intent1);
//                } catch (Exception e)
//                {
//                    if (Tools.isNetworkAvailable(context))
//                    {
//                        Logger.e("-OnError-", "Error: " + e.getMessage());
//                        showError(context, "خطا در دریافت اطلاعات از سرور!");
//                    } else
//                    {
//                        showAlert(context, R.string.networkErrorMessage, R.string.networkError);
//                    }
//                }
                break;
            }
        }
    }

    private void openVideoDetail(ArrayList<Category> categoriesList, int position, Integer idVideo, Integer id)
    {
        Intent intent = new Intent(context, VideoDetailActivity.class);

        intent.putParcelableArrayListExtra("Videos", categoriesList);
        intent.putExtra("IdVideoCategory", idVideo);
        intent.putExtra("IdVideo", id);
        intent.putExtra("positionVideo", position);
        intent.putExtra("idCategoryTitle", idCategoryTitle);

        startActivityForResult(intent, 100);
    }

    @Override
    public void onItemNewestVideoClick(int position, Category category)
    {
        categoriesList = mainVideosResponse.getRecent();
        openVideoDetail(categoriesList, position, category.getCategoryId(), category.getId());
    }

    @Override
    public void onItemCategoryClick(int position, Category category, ArrayList<Category> categories)
    {
        categoriesList = categories;
        openVideoDetail(categoriesList, position, category.getCategoryId(), category.getId());
    }
}
