package com.traap.traapapp.ui.fragments.videos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.traap.traapapp.apiServices.model.mainVideos.MainVideoRequest;
import com.traap.traapapp.apiServices.model.mainVideos.MainVideosResponse;
import com.traap.traapapp.ui.activities.video.VideoArchiveActivity;
import com.traap.traapapp.ui.activities.video.VideoDetailActivity;
import com.traap.traapapp.ui.adapters.video.NewestVideosAdapter;
import com.traap.traapapp.apiServices.model.categoryByIdVideo.CategoryByIdVideosRequest;
import com.traap.traapapp.apiServices.model.mainVideos.ListCategory;
import com.traap.traapapp.ui.adapters.video.CategoryAdapter;
import com.traap.traapapp.ui.adapters.video.VideosCategoryTitleAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.Tools;

/**
 * Created by MahtabAzizi on 11/23/2019.
 */
public class VideosFragment extends BaseFragment implements VideosCategoryTitleAdapter.TitleCategoryListener, View.OnClickListener, NewestVideosAdapter.NewestVideoListener, CategoryAdapter.CategoryListener
{
    private MainActionView mainView;
    private View rootView;
    private BannerLayout bNewestVideo;
    private RoundedImageView ivFavorite1, ivFavorite2, ivFavorite3;
    private RecyclerView rvCategoryTitles, rvCategories;
    private VideosCategoryTitleAdapter videoCategoryTitleAdapter;
    private Integer idCategoryTitle = 0;
    private CategoryAdapter categoryAdapter;
    private MainVideosResponse mainVideosResponse;
    private ArrayList<Category> categoriesList;
    private int position=0;
    private Integer idVideo;
    private Integer id;
    private TextView tvArchiveVideo;

    public VideosFragment()
    {

    }

    public static VideosFragment newInstance(MainActionView mainView)
    {
        VideosFragment fragment = new VideosFragment();
        fragment.setMainView(mainView);
        return fragment;
    }

    private void setMainView(MainActionView mainView)
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
            return rootView;
        rootView = inflater.inflate(R.layout.fragment_videos, container, false);

        mainView.showLoading();
        bNewestVideo = rootView.findViewById(R.id.bNewestVideo);
        ivFavorite1 = rootView.findViewById(R.id.ivFavorite1);
        ivFavorite2 = rootView.findViewById(R.id.ivFavorite2);
        ivFavorite3 = rootView.findViewById(R.id.ivFavorite3);
        rvCategoryTitles = rootView.findViewById(R.id.rvCategoryTitles);
        tvArchiveVideo=rootView.findViewById(R.id.tvArchiveVideo);
        rvCategories = rootView.findViewById(R.id.rvCategories);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        rvCategoryTitles.setLayoutManager(layoutManager);

        ivFavorite1.setOnClickListener(this);
        ivFavorite2.setOnClickListener(this);
        ivFavorite3.setOnClickListener(this);
        tvArchiveVideo.setOnClickListener(this);
        LinearLayoutManager layoutManagerCategory = new LinearLayoutManager(getContext());
        rvCategories.setLayoutManager(layoutManagerCategory);
        requestMainVideos();

        return rootView;
    }

    private void requestMainVideos()
    {
        MainVideoRequest request = new MainVideoRequest();

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

                        mainVideosResponse=response.data;
                        onGetMainVideosSuccess(response.data);

                    } else
                    {
                        Tools.showToast(getContext(), response.info.message, R.color.red);
                    }
                } catch (Exception e)
                {
                    Tools.showToast(getContext(), e.getMessage(), R.color.red);

                }
            }

            @Override
            public void onError(String message)
            {
                mainView.hideLoading();
                Tools.showToast(getActivity(), message, R.color.red);
            }
        }, request);
    }

    private void onGetMainVideosSuccess(MainVideosResponse mainVideosResponse)
    {
        bNewestVideo.setAdapter(new NewestVideosAdapter(mainVideosResponse.getRecent(), mainView,this));
        setDataFavoriteList(mainVideosResponse);
        videoCategoryTitleAdapter = new VideosCategoryTitleAdapter(mainVideosResponse.getListCategories(), mainView, this);
        rvCategoryTitles.setAdapter(videoCategoryTitleAdapter);
        categoryAdapter = new CategoryAdapter(mainVideosResponse.getCategory(), mainView,this);
        rvCategories.setAdapter(categoryAdapter);

    }

    private void setDataFavoriteList(MainVideosResponse mainVideosResponse)
    {
        if (!mainVideosResponse.getFavorites().isEmpty())
        {
            List<Category> favoriteList = mainVideosResponse.getFavorites();
            setImageBackground(ivFavorite1, favoriteList.get(0).getBigPoster().replace("\\", ""));
            setImageBackground(ivFavorite2, favoriteList.get(1).getBigPoster().replace("\\", ""));
            setImageBackground(ivFavorite3, favoriteList.get(2).getBigPoster().replace("\\", ""));
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
                public void onError()
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
        mainView.showLoading();
        idCategoryTitle = category.getId();
        requestGetCategoryById(idCategoryTitle);
    }

    private void requestGetCategoryById(Integer idCategoryTitle)
    {

        CategoryByIdVideosRequest request = new CategoryByIdVideosRequest();

        SingletonService.getInstance().categoryByIdVideosService().categoryByIdVideosService(idCategoryTitle,request,new    OnServiceStatus<WebServiceClass<CategoryByIdVideosResponse>>()
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

                    } else
                    {
                        Tools.showToast(getContext(), response.info.message, R.color.red);
                    }
                } catch (Exception e)
                {
                    Tools.showToast(getContext(), e.getMessage(), R.color.red);

                }
            }

            @Override
            public void onError(String message)
            {
                mainView.hideLoading();
                Tools.showToast(getActivity(), message, R.color.red);
            }
        });
    }

    private void setCategoryListData(CategoryByIdVideosResponse data)
    {
        categoryAdapter = new CategoryAdapter(data.getResults(), mainView,this);
        rvCategories.setAdapter(categoryAdapter);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.ivFavorite1:
                categoriesList=mainVideosResponse.getFavorites();
                position=0;
                idVideo=mainVideosResponse.getFavorites().get(position).getCategoryId();
                id=mainVideosResponse.getFavorites().get(position).getId();
              openVideoDetail(categoriesList,position,idVideo,id);
                break;

            case R.id.ivFavorite2:
                categoriesList=mainVideosResponse.getFavorites();
                position=1;
                idVideo=mainVideosResponse.getFavorites().get(position).getCategoryId();
                id=mainVideosResponse.getFavorites().get(position).getId();
                openVideoDetail(categoriesList,position,idVideo,id);
                break;

            case R.id.ivFavorite3:
                categoriesList=mainVideosResponse.getFavorites();
                position=2;
                idVideo=mainVideosResponse.getFavorites().get(position).getCategoryId();
                id=mainVideosResponse.getFavorites().get(position).getId();
                openVideoDetail(categoriesList,position,idVideo,id);
                break;
            case R.id.tvArchiveVideo:
                ArrayList<ListCategory> categoryTitleList = mainVideosResponse.getListCategories();
                Intent intent = new Intent(getActivity(), VideoArchiveActivity.class);
                intent.putParcelableArrayListExtra("CategoryTitle", categoryTitleList);
                startActivity(intent);
                break;
        }
    }

    private void openVideoDetail(ArrayList<Category> categoriesList, int position, Integer idVideo,Integer id)
    {
        Intent intent = new Intent(getActivity(), VideoDetailActivity.class);

        intent.putParcelableArrayListExtra("Videos", categoriesList);
        intent.putExtra("IdVideoCategory",idVideo);
        intent.putExtra("IdVideo",id);
        intent.putExtra("positionVideo",position);

        startActivity(intent);
    }

    @Override
    public void onItemNewestVideoClick(int position, Category category)
    {
        categoriesList=mainVideosResponse.getRecent();
        openVideoDetail(categoriesList,position,category.getCategoryId(),category.getId());
    }

    @Override
    public void onItemCategoryClick(int position, Category category, ArrayList<Category> categories)
    {
        categoriesList=categories;
        openVideoDetail(categoriesList,position,category.getCategoryId(),category.getId());
    }
}