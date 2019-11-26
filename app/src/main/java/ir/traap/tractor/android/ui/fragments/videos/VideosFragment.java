package ir.traap.tractor.android.ui.fragments.videos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.generator.SingletonService;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.categoryByIdVideo.CategoryByIdVideosResponse;
import ir.traap.tractor.android.apiServices.model.mainVideos.Category;
import ir.traap.tractor.android.apiServices.model.mainVideos.MainVideoRequest;
import ir.traap.tractor.android.apiServices.model.mainVideos.MainVideosResponse;
import ir.traap.tractor.android.ui.activities.video.VideoDetailActivity;
import ir.traap.tractor.android.ui.adapters.video.NewestVideosAdapter;
import ir.traap.tractor.android.apiServices.model.categoryByIdVideo.CategoryByIdVideosRequest;
import ir.traap.tractor.android.apiServices.model.mainVideos.ListCategory;
import ir.traap.tractor.android.ui.adapters.video.CategoryAdapter;
import ir.traap.tractor.android.ui.adapters.video.VideosCategoryTitleAdapter;
import ir.traap.tractor.android.ui.base.BaseFragment;
import ir.traap.tractor.android.ui.fragments.main.MainActionView;
import ir.traap.tractor.android.utilities.Tools;

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
        rvCategories = rootView.findViewById(R.id.rvCategories);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        rvCategoryTitles.setLayoutManager(layoutManager);

        ivFavorite1.setOnClickListener(this);
        ivFavorite2.setOnClickListener(this);
        ivFavorite3.setOnClickListener(this);
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
    public void onItemTitleCategoryClick(ListCategory category)
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
                idVideo=mainVideosResponse.getFavorites().get(0).getCategoryId();
              openVideoDetail(categoriesList,position,idVideo);
                break;

            case R.id.ivFavorite2:
                categoriesList=mainVideosResponse.getFavorites();
                position=1;
                idVideo=mainVideosResponse.getFavorites().get(position).getCategoryId();
                openVideoDetail(categoriesList,position,idVideo);
                break;

            case R.id.ivFavorite3:
                categoriesList=mainVideosResponse.getFavorites();
                position=2;
                idVideo=mainVideosResponse.getFavorites().get(position).getCategoryId();
                openVideoDetail(categoriesList,position,idVideo);
                break;
        }
    }

    private void openVideoDetail(ArrayList<Category> categoriesList, int position, Integer idVideo)
    {
        Intent intent = new Intent(getActivity(), VideoDetailActivity.class);

        intent.putParcelableArrayListExtra("Videos", categoriesList);
       // intent.putExtra("Videos", (ArrayList) categoriesList);
        intent.putExtra("IdVideo",idVideo);
        intent.putExtra("positionVideo",position);

        startActivity(intent);
    }

    @Override
    public void onItemNewestVideoClick(int position, Category category)
    {
        categoriesList=mainVideosResponse.getRecent();
        openVideoDetail(categoriesList,position,category.getCategoryId());
    }

    @Override
    public void onItemCategoryClick(int position, Category category, ArrayList<Category> categories)
    {
        categoriesList=categories;
        openVideoDetail(categoriesList,position,category.getCategoryId());
    }
}
