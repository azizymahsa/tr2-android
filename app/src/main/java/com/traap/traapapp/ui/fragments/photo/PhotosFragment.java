package com.traap.traapapp.ui.fragments.photo;

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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
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
import java.util.concurrent.TimeUnit;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.categoryByIdVideo.CategoryByIdVideosRequest;
import com.traap.traapapp.apiServices.model.categoryByIdVideo.CategoryByIdVideosResponse;
import com.traap.traapapp.apiServices.model.mainPhotos.MainPhotoResponse;
import com.traap.traapapp.apiServices.model.mainVideos.Category;
import com.traap.traapapp.apiServices.model.mainVideos.ListCategory;
import com.traap.traapapp.apiServices.model.photo.response.ImageName;
import com.traap.traapapp.enums.SubMediaParent;
import com.traap.traapapp.ui.activities.photo.AlbumDetailActivity;
import com.traap.traapapp.ui.adapters.photo.CategoryPhotosAdapter;
import com.traap.traapapp.ui.adapters.photo.NewestPhotosAdapter;
import com.traap.traapapp.ui.adapters.photo.PhotosArchiveAdapter;
import com.traap.traapapp.ui.adapters.photo.PhotosCategoryTitleAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;

/**
 * Created by MahsaAzizi on 11/27/2019.
 */
public class PhotosFragment extends BaseFragment implements View.OnClickListener,
        PhotosCategoryTitleAdapter.TitleCategoryListener, PhotosArchiveAdapter.ArchivePhotosListener
        , CategoryPhotosAdapter.TitleCategoryListener, NewestPhotosAdapter.OnItemRelatedAlbumsClickListener
{
    private Context context;
    private PhotosActionView mainView;
    private LinearLayout llPager,llTop;
    private NestedScrollView nestedScrollView;
    private View rootView;
    private BannerLayout bNewestPhotos;
    private RoundedImageView ivFavorite1, ivFavorite2, ivFavorite3;
    private RecyclerView rvCategoryTitles, rvGrideCategories;
    private PhotosCategoryTitleAdapter photoCategoryTitleAdapter;
    private Integer idCategoryTitle = 0;
    private CategoryPhotosAdapter categoryAdapter;
    private MainPhotoResponse mainPhotosResponse;
    private ArrayList<Category> categoriesList;
    private int position = 0;
    private Integer idVideo;
    private Integer id;
    private TextView tvArchivePhotos, tvMyFavoritePhoto;
    private View rlShirt;
    private View tvEmpty, tvEmptyFavorite, llFavorites;
    private ScrollingPagerIndicator indicatorNewestPhotos;
    private boolean isFirstClick=true;
    private FrameLayout flProgress;


    public PhotosFragment()
    {

    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    private void openAlbumDetail(ArrayList<Category> categoriesList, int position, Integer idVideo, Integer id)
    {
        Intent intent = new Intent(context, AlbumDetailActivity.class);

        intent.putParcelableArrayListExtra("Photos", categoriesList);
        intent.putExtra("IdVideoCategory", idVideo);
        intent.putExtra("IdPhoto", id);
        intent.putExtra("positionPhoto", position);

        startActivityForResult(intent, 100);
    }

    public static PhotosFragment newInstance(PhotosActionView mainView)
    {
        PhotosFragment fragment = new PhotosFragment();
        fragment.setMainView(mainView);
        return fragment;
    }

    private void setMainView(PhotosActionView mainView)
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (rootView != null)
        {
            return rootView;
        }
        rootView = inflater.inflate(R.layout.fragment_photos, container, false);

        flProgress = rootView.findViewById(R.id.flProgress);

        flProgress.setVisibility(View.VISIBLE);
        llPager = rootView.findViewById(R.id.llPager);
        llTop=rootView.findViewById(R.id.llTop);
        nestedScrollView = rootView.findViewById(R.id.nestedScroll);
        bNewestPhotos = rootView.findViewById(R.id.bNewestPhotos);
        ivFavorite1 = rootView.findViewById(R.id.ivFavorite1);
        ivFavorite2 = rootView.findViewById(R.id.ivFavorite2);
        ivFavorite3 = rootView.findViewById(R.id.ivFavorite3);
        indicatorNewestPhotos = rootView.findViewById(R.id.indicatorNewestPhotos);
        rvCategoryTitles = rootView.findViewById(R.id.rvCategoryTitles);
        tvArchivePhotos = rootView.findViewById(R.id.tvArchivePhotos);
        tvMyFavoritePhoto = rootView.findViewById(R.id.tvMyFavoritePhoto);
        rvGrideCategories = rootView.findViewById(R.id.rvCategories);
        tvEmpty = rootView.findViewById(R.id.tvEmpty);
        tvEmptyFavorite = rootView.findViewById(R.id.tvEmptyFavorite);
        llFavorites = rootView.findViewById(R.id.llFavorites);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        rvCategoryTitles.setLayoutManager(layoutManager);

        ivFavorite1.setOnClickListener(this);
        ivFavorite2.setOnClickListener(this);
        ivFavorite3.setOnClickListener(this);
        tvArchivePhotos.setOnClickListener(this);
        tvMyFavoritePhoto.setOnClickListener(this);
        rvGrideCategories.setLayoutManager(new GridLayoutManager(getContext(), 2));
        requestMainPhotos();

        return rootView;
    }

    private void requestMainPhotos()
    {
        SingletonService.getInstance().getMainPhotosService().getMainPhotos(new OnServiceStatus<WebServiceClass<MainPhotoResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<MainPhotoResponse> response)
            {
                try
                {
                    flProgress.setVisibility(View.GONE);

                    if (response.info.statusCode == 200)
                    {
                        mainPhotosResponse = response.data;
                        onGetMainVideosSuccess(response.data);

                    }
                    else
                    {
                        showToast(context, response.info.message, R.color.red);
                    }
                } catch (Exception e)
                {
                    showToast(context, e.getMessage(), R.color.red);

                }
            }

            @Override
            public void onError(String message)
            {
                flProgress.setVisibility(View.GONE);
                if (Tools.isNetworkAvailable((Activity) context))
                {
                    Logger.e("-OnError-", "Error: " + message);
                    showError(context, "خطا در دریافت اطلاعات از سرور!");
                }
                else
                {
                    showAlert(context, R.string.networkErrorMessage, R.string.networkError);
                }
                //  showToast(context, message, R.color.red);
            }
        });
    }

    private void onGetMainVideosSuccess(MainPhotoResponse mainPhotosResponse)
    {
        bNewestPhotos.setAdapter(new NewestPhotosAdapter(mainPhotosResponse.getRecent(), this));
        setDataFavoriteList(mainPhotosResponse);

        indicatorNewestPhotos.attachToRecyclerView(bNewestPhotos.getmRecyclerView());
        bNewestPhotos.setAutoPlaying(true);

        photoCategoryTitleAdapter = new PhotosCategoryTitleAdapter(mainPhotosResponse.getListCategories(), this);
        rvCategoryTitles.setAdapter(photoCategoryTitleAdapter);
        categoryAdapter = new CategoryPhotosAdapter(mainPhotosResponse.getCategory(), this);
        rvGrideCategories.setLayoutManager(new GridLayoutManager(getContext(), 2));

        rvGrideCategories.setAdapter(categoryAdapter);

    }

    private void setDataFavoriteList(MainPhotoResponse mainPhotosResponse)
    {
        if (!mainPhotosResponse.getFavorites().isEmpty())
        {
            llFavorites.setVisibility(View.VISIBLE);
            tvEmptyFavorite.setVisibility(View.GONE);
            ArrayList<Category> favoriteList = mainPhotosResponse.getFavorites();
            try
            {
               /* if (favoriteList.size()>4)
                {*/
                setImageBackground(ivFavorite1, favoriteList.get(0).getCover().replace("\\", ""));
                setImageBackground(ivFavorite2, favoriteList.get(1).getCover().replace("\\", ""));
                setImageBackground(ivFavorite3, favoriteList.get(2).getCover().replace("\\", ""));
                // }
            } catch (Exception e)
            {
                Log.d("Favorit:", e.getMessage());
            }

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
        } catch (NullPointerException e)
        {
            Picasso.with(getContext()).load(R.drawable.img_failure).into(image);
        }
    }

    @Override
    public void onItemTitleCategoryClick(ListCategory category)
    {
       /* ArrayList<Category> categoriesList=new ArrayList<>();
        Category category1=new Category();
        category1.setId(category.getId());
        category1.setTitle(category.getTitle());
        categoriesList.add(category1);
        ///*/
        flProgress.setVisibility(View.VISIBLE);
        idCategoryTitle = category.getId();
        requestGetCategoryById(idCategoryTitle);
        //  openAlbumDetail(categoriesList,position,category.getId(),category.getId());

    }

    private void requestGetCategoryById(Integer idCategoryTitle)
    {
        SingletonService.getInstance().categoryByIdVideosService().categoryByIdPhotosService(idCategoryTitle, new OnServiceStatus<WebServiceClass<CategoryByIdVideosResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<CategoryByIdVideosResponse> response)
            {
                try
                {
                    if (response.info.statusCode == 200)
                    {

                        setCategoryListData(response.data);
                    }
                    else
                    {
                        showToast(context, response.info.message, R.color.red);
                    }
                }
                catch (Exception e)
                {
                    showToast(context, e.getMessage(), R.color.red);
                }
            }

            @Override
            public void onError(String message)
            {
                flProgress.setVisibility(View.GONE);
                if (Tools.isNetworkAvailable((Activity) context))
                {
                    Logger.e("-OnError-", "Error: " + message);
                    showError(context, "خطا در دریافت اطلاعات از سرور!");
                }
                else
                {
                    showAlert(context, R.string.networkErrorMessage, R.string.networkError);
                }
                //  showToast(context, message, R.color.red);
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {

            case R.id.ivFavorite1:
                if (mainPhotosResponse != null)
                {
                    categoriesList = mainPhotosResponse.getFavorites();
                    position = 0;
                    idVideo = mainPhotosResponse.getFavorites().get(position).getId();//getCategoryId();
                    id = mainPhotosResponse.getFavorites().get(position).getId();
                    openAlbumDetail(categoriesList, position, idVideo, id);
                }
                break;

            case R.id.ivFavorite2:
                if (mainPhotosResponse != null)
                {
                    categoriesList = mainPhotosResponse.getFavorites();
                    position = 1;
                    idVideo = mainPhotosResponse.getFavorites().get(position).getId();//getCategoryId();
                    id = mainPhotosResponse.getFavorites().get(position).getId();
                    openAlbumDetail(categoriesList, position, idVideo, id);
                }
                break;

            case R.id.ivFavorite3:
                if (mainPhotosResponse != null)
                {
                    categoriesList = mainPhotosResponse.getFavorites();
                    position = 2;
                    idVideo = mainPhotosResponse.getFavorites().get(position).getCategoryId();
                    id = mainPhotosResponse.getFavorites().get(position).getId();
                    openAlbumDetail(categoriesList, position, idVideo, id);
                }
                break;
            case R.id.tvArchivePhotos:
            {
                //photoArchiveFragment
                mainView.onPhotosArchiveFragment(SubMediaParent.MediaFragment);

//                try
//                {
//
//                    ArrayList<ListCategory> categoryTitleList = mainPhotosResponse.getListCategories();
//                    Intent intent = new Intent(context, PhotoArchiveActivity.class);
//                    intent.putParcelableArrayListExtra("CategoryTitle", categoryTitleList);
//                    intent.putExtra("FLAG_Favorite", false);
//
//                    startActivityForResult(intent,100);
//
//                }
//                catch (Exception e)
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
            case R.id.tvMyFavoritePhoto:
            {
                //Favorite Fragment
                mainView.onPhotosFavoriteFragment(SubMediaParent.MediaFragment);
//                try
//                {
//                    Intent intent1 = new Intent(context, PhotoArchiveActivity.class);
//                    intent1.putExtra("FLAG_Favorite", true);
//                    startActivityForResult(intent1);
//                }
//                catch (Exception e)
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

    @Override
    public void onItemArchiveVideoClick(int position, Category category, ArrayList<Category> recent)
    {
        openAlbumDetail(recent, position, category.getId(), category.getId());
    }

    @SuppressLint("CheckResult")
    private void setCategoryListData(CategoryByIdVideosResponse data)
    {
        if (data.getResults().isEmpty())
        {
            tvEmpty.setVisibility(View.VISIBLE);
            rvGrideCategories.setVisibility(View.GONE);
            flProgress.setVisibility(View.GONE);

        } else
        {
            tvEmpty.setVisibility(View.GONE);
            rvGrideCategories.setVisibility(View.VISIBLE);
            categoryAdapter = new CategoryPhotosAdapter(data.getResults(), this);
            rvGrideCategories.setLayoutManager(new GridLayoutManager(getContext(), 2));
            rvGrideCategories.setAdapter(categoryAdapter);

    /*            new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Observable.just(llTop.getHeight())
//                        .repeat(2)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(scrollTo ->
                                {
                                    nestedScrollView.post(() -> nestedScrollView.scrollTo(0, scrollTo));
                                });
                    }
                }, 300);*/


            Observable.just(rvGrideCategories.getHeight())
                    .subscribeOn(Schedulers.io())
                    .delay(250, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(scrollTo -> {

                        flProgress.setVisibility(View.GONE);

                        nestedScrollView.post(() ->{nestedScrollView.scrollTo(0, llTop.getHeight()); } ); });
          //  flProgress.setVisibility(View.GONE);

            // isFirstClick=false;
             /*else
            {
                Observable.just(llTop.getHeight())
//                        .repeat(2)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(scrollTo ->
                        {
                            nestedScrollView.post(() -> nestedScrollView.smoothScrollTo(0, scrollTo));
                        });
            }
*/

    }
        //nestedScrollView.post(() -> nestedScrollView.smoothScrollTo(0, llPager.getTop()));


        // rvGrid.setLayoutManager(new GridLayoutManager(getContext(), 3));
        // rvGrid.setAdapter(new ItemRecyclerViewAdapter(getContext(), list, this));//, interactionListener));
    }

    //onClick Archive
    @Override
    public void onItemTitleCategoryClick(Category category)
    {
        ArrayList<Category> categoriesList = new ArrayList<>();
        Category category1 = new Category();
        category1.setId(category.getId());
        category1.setTitle(category.getTitle());
        categoriesList.add(category1);

        openAlbumDetail(categoriesList, position, category.getId(), category.getId());

    }

    @Override
    public void OnItemRelatedAlbumsClick(View view, Category category)
    {
        ArrayList<Category> categoriesList = new ArrayList<>();
        Category category1 = new Category();
        category1.setId(category.getId());
        category1.setTitle(category.getTitle());
        category1.setCover(category.getCover());
        ImageName imageName = new ImageName();
        imageName.setThumbnailLarge(category.getCover());
        category1.setImageName(imageName);
        categoriesList.add(category1);

        openAlbumDetail(categoriesList, position, category.getId(), category.getId());

    }
}
