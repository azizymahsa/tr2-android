package com.traap.traapapp.ui.fragments.photo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.moeidbannerlibrary.banner.BannerLayout;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.categoryByIdVideo.CategoryByIdVideosRequest;
import com.traap.traapapp.apiServices.model.categoryByIdVideo.CategoryByIdVideosResponse;
import com.traap.traapapp.apiServices.model.mainVideos.Category;
import com.traap.traapapp.apiServices.model.mainVideos.ListCategory;
import com.traap.traapapp.apiServices.model.mainVideos.MainVideoRequest;
import com.traap.traapapp.apiServices.model.mainVideos.MainVideosResponse;
import com.traap.traapapp.apiServices.model.photo.response.ImageName;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.photo.PhotoArchiveActivity;
import com.traap.traapapp.ui.activities.photo.AlbumDetailActivity;
import com.traap.traapapp.ui.activities.userProfile.UserProfileActivity;
import com.traap.traapapp.ui.adapters.photo.CategoryPhotosAdapter;
import com.traap.traapapp.ui.adapters.photo.NewestPhotosAdapter;
import com.traap.traapapp.ui.adapters.photo.PhotosArchiveAdapter;
import com.traap.traapapp.ui.adapters.photo.PhotosCategoryTitleAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.Tools;

/**
 * Created by MahsaAzizi on 11/27/2019.
 */
public class PhotosFragment extends BaseFragment  implements View.OnClickListener, PhotosCategoryTitleAdapter.TitleCategoryListener, PhotosArchiveAdapter.ArchiveVideoListener
        ,CategoryPhotosAdapter.TitleCategoryListener,NewestPhotosAdapter.OnItemTopListPhotoClickListener
{

    private MainActionView mainView;
    private View rootView;
    private BannerLayout bNewestVideo;
    private RoundedImageView ivFavorite1, ivFavorite2, ivFavorite3;
    private RecyclerView rvCategoryTitles, rvGrideCategories;
    private PhotosCategoryTitleAdapter photoCategoryTitleAdapter;
    private Integer idCategoryTitle = 0;
    private CategoryPhotosAdapter categoryAdapter;
    private MainVideosResponse mainVideosResponse;
    private ArrayList<Category> categoriesList;
    private int position=0;
    private Integer idVideo;
    private Integer id;
    private TextView tvArchiveVideo,tvMyFavoritePhoto;
    private View rlShirt;

    public PhotosFragment()
    {

    }
    private void openAlbumDetail(ArrayList<Category> categoriesList, int position, Integer idVideo, Integer id)
    {
        Intent intent = new Intent(getActivity(), AlbumDetailActivity.class);

        intent.putParcelableArrayListExtra("Photos", categoriesList);
        intent.putExtra("IdVideoCategory",idVideo);
        intent.putExtra("IdPhoto",id);
        intent.putExtra("positionPhoto",position);

        startActivity(intent);
    }
    public static PhotosFragment newInstance(MainActionView mainView)
    {
        PhotosFragment fragment = new PhotosFragment();
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
        rootView = inflater.inflate(R.layout.fragment_photos, container, false);



        mainView.showLoading();
        bNewestVideo = rootView.findViewById(R.id.bNewestVideo);
        ivFavorite1 = rootView.findViewById(R.id.ivFavorite1);
        ivFavorite2 = rootView.findViewById(R.id.ivFavorite2);
        ivFavorite3 = rootView.findViewById(R.id.ivFavorite3);
        rvCategoryTitles = rootView.findViewById(R.id.rvCategoryTitles);
        tvArchiveVideo=rootView.findViewById(R.id.tvArchiveVideo);
        tvMyFavoritePhoto=rootView.findViewById(R.id.tvMyFavoritePhoto);
        rvGrideCategories = rootView.findViewById(R.id.rvCategories);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        rvCategoryTitles.setLayoutManager(layoutManager);

        ivFavorite1.setOnClickListener(this);
        ivFavorite2.setOnClickListener(this);
        ivFavorite3.setOnClickListener(this);
        tvArchiveVideo.setOnClickListener(this);
        tvMyFavoritePhoto.setOnClickListener(this);
        rvGrideCategories.setLayoutManager(new GridLayoutManager(getContext(), 2));
        requestMainVideos();

        return rootView;
    }

    private void requestMainVideos()
    {
        MainVideoRequest request = new MainVideoRequest();

        SingletonService.getInstance().getMainVideosService().getMainPhotos(new OnServiceStatus<WebServiceClass<MainVideosResponse>>()
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

    private void onGetMainVideosSuccess(MainVideosResponse mainPhotosResponse)
    {
        bNewestVideo.setAdapter(new NewestPhotosAdapter(mainPhotosResponse.getRecent(), mainView,this));
        setDataFavoriteList(mainPhotosResponse);
        photoCategoryTitleAdapter = new PhotosCategoryTitleAdapter(mainPhotosResponse.getListCategories(), mainView, this);
        rvCategoryTitles.setAdapter(photoCategoryTitleAdapter);
        categoryAdapter = new CategoryPhotosAdapter(mainPhotosResponse.getCategory(), mainView,this);
        rvGrideCategories.setLayoutManager(new GridLayoutManager(getContext(), 2));

        rvGrideCategories.setAdapter(categoryAdapter);

    }

    private void setDataFavoriteList(MainVideosResponse mainPhotosResponse)
    {
        if (!mainPhotosResponse.getFavorites().isEmpty())
        {
            ArrayList<Category> favoriteList = mainPhotosResponse.getFavorites();
            setImageBackground(ivFavorite1, favoriteList.get(0).getCover().replace("\\", ""));
            setImageBackground(ivFavorite2, favoriteList.get(1).getCover().replace("\\", ""));
            setImageBackground(ivFavorite3, favoriteList.get(2).getCover().replace("\\", ""));
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
       /* ArrayList<Category> categoriesList=new ArrayList<>();
        Category category1=new Category();
        category1.setId(category.getId());
        category1.setTitle(category.getTitle());
        categoriesList.add(category1);
        ///*/
        mainView.showLoading();
        idCategoryTitle = category.getId();
        requestGetCategoryById(idCategoryTitle);
      //  openAlbumDetail(categoriesList,position,category.getId(),category.getId());

    }

    private void requestGetCategoryById(Integer idCategoryTitle)
    {

        CategoryByIdVideosRequest request = new CategoryByIdVideosRequest();

        SingletonService.getInstance().categoryByIdVideosService().categoryByIdPhotosService(idCategoryTitle,request,new    OnServiceStatus<WebServiceClass<CategoryByIdVideosResponse>>()
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
    @Override
    public void onClick(View v)
    {
        switch (v.getId()){

            case R.id.ivFavorite1:
                categoriesList=mainVideosResponse.getFavorites();
                position=0;
                idVideo=mainVideosResponse.getFavorites().get(position).getId();//getCategoryId();
                id=mainVideosResponse.getFavorites().get(position).getId();
                openAlbumDetail(categoriesList,position,idVideo,id);
                break;

            case R.id.ivFavorite2:
                categoriesList=mainVideosResponse.getFavorites();
                position=1;
                idVideo=mainVideosResponse.getFavorites().get(position).getId();//getCategoryId();
                id=mainVideosResponse.getFavorites().get(position).getId();
                openAlbumDetail(categoriesList,position,idVideo,id);
                break;

            case R.id.ivFavorite3:
                categoriesList=mainVideosResponse.getFavorites();
                position=2;
                idVideo=mainVideosResponse.getFavorites().get(position).getCategoryId();
                id=mainVideosResponse.getFavorites().get(position).getId();
                openAlbumDetail(categoriesList,position,idVideo,id);
                break;
            case R.id.tvArchiveVideo:
                ArrayList<ListCategory> categoryTitleList = mainVideosResponse.getListCategories();
                Intent intent = new Intent(getActivity(), PhotoArchiveActivity.class);
                intent.putParcelableArrayListExtra("CategoryTitle", categoryTitleList);
                intent.putExtra("FLAG_Favorite",false);

                startActivity(intent);
                break;
            case R.id.tvMyFavoritePhoto:
                Intent intent1 = new Intent(getActivity(), PhotoArchiveActivity.class);
                intent1.putExtra("FLAG_Favorite",true);
                startActivity(intent1);
                break;
        }
    }

    @Override
    public void onItemArchiveVideoClick(int position, Category category, ArrayList<Category> recent)
    {
        openAlbumDetail(recent,position,category.getId(),category.getId());
    }
    private void setCategoryListData(CategoryByIdVideosResponse data)
    {
        categoryAdapter = new CategoryPhotosAdapter(data.getResults(), mainView,this);
        rvGrideCategories.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvGrideCategories.setAdapter(categoryAdapter);

       // rvGrid.setLayoutManager(new GridLayoutManager(getContext(), 3));
       // rvGrid.setAdapter(new ItemRecyclerViewAdapter(getContext(), list, this));//, interactionListener));
    }

    //onClick Archive
    @Override
    public void onItemTitleCategoryClick(Category category)
    {
         ArrayList<Category> categoriesList=new ArrayList<>();
        Category category1=new Category();
        category1.setId(category.getId());
        category1.setTitle(category.getTitle());
        categoriesList.add(category1);

        openAlbumDetail(categoriesList,position,category.getId(),category.getId());

    }

    @Override
    public void OnItemTopPhotoClick(View view, Category category)
    {
        ArrayList<Category> categoriesList=new ArrayList<>();
        Category category1=new Category();
        category1.setId(category.getId());
        category1.setTitle(category.getTitle());
        category1.setCover(category.getCover());
        ImageName imageName=new ImageName();
        imageName.setThumbnailLarge(category.getCover());
        category1.setImageName(imageName);
        categoriesList.add(category1);

        openAlbumDetail(categoriesList,position,category.getId(),category.getId());

    }
}
