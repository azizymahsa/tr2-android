package ir.traap.tractor.android.ui.fragments.photo;

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
import java.util.List;

import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.generator.SingletonService;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.categoryByIdVideo.CategoryByIdVideosRequest;
import ir.traap.tractor.android.apiServices.model.categoryByIdVideo.CategoryByIdVideosResponse;
import ir.traap.tractor.android.apiServices.model.mainVideos.Category;
import ir.traap.tractor.android.apiServices.model.mainVideos.Favorite;
import ir.traap.tractor.android.apiServices.model.mainVideos.ListCategory;
import ir.traap.tractor.android.apiServices.model.mainVideos.MainVideoRequest;
import ir.traap.tractor.android.apiServices.model.mainVideos.MainVideosResponse;
import ir.traap.tractor.android.ui.activities.photo.PhotoArchiveActivity;
import ir.traap.tractor.android.ui.activities.photo.PhotoDetailActivity;
import ir.traap.tractor.android.ui.activities.video.VideoDetailActivity;
import ir.traap.tractor.android.ui.adapters.photo.CategoryPhotosAdapter;
import ir.traap.tractor.android.ui.adapters.photo.NewestPhotosAdapter;
import ir.traap.tractor.android.ui.adapters.photo.PhotosArchiveAdapter;
import ir.traap.tractor.android.ui.adapters.photo.PhotosCategoryTitleAdapter;
import ir.traap.tractor.android.ui.adapters.video.CategoryAdapter;
import ir.traap.tractor.android.ui.adapters.video.NewestVideosAdapter;
import ir.traap.tractor.android.ui.adapters.video.VideosCategoryTitleAdapter;
import ir.traap.tractor.android.ui.base.BaseFragment;
import ir.traap.tractor.android.ui.fragments.main.MainActionView;
import ir.traap.tractor.android.utilities.Tools;

/**
 * Created by MahsaAzizi on 11/27/2019.
 */
public class PhotosFragment extends BaseFragment  implements View.OnClickListener, PhotosCategoryTitleAdapter.TitleCategoryListener, PhotosArchiveAdapter.ArchiveVideoListener
{
    /*private MainActionView mainView;
    private View rootView;
    private BannerLayout bNewestVideo;
    private RoundedImageView ivFavorite1, ivFavorite2, ivFavorite3;
    private RecyclerView rvCategoryTitles, rvGrideCategories;
    private PhotosCategoryTitleAdapter photoCategoryTitleAdapter;
    private Integer idCategoryTitle = 0;
    private CategoryPhotosAdapter categoryAdapter;
    private TextView tvArchiveVideo;
    private MainVideosResponse mainVideosResponse;*/


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
    private TextView tvArchiveVideo;
    public PhotosFragment()
    {

    }
    private void openPhotoDetail(ArrayList<Category> categoriesList, int position, Integer idVideo, Integer id)
    {
      //  Intent intent = new Intent(this, PhotoDetailActivity.class);
        Intent intent = new Intent(getActivity(), PhotoDetailActivity.class);

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
        rvGrideCategories = rootView.findViewById(R.id.rvCategories);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        rvCategoryTitles.setLayoutManager(layoutManager);

        ivFavorite1.setOnClickListener(this);
        ivFavorite2.setOnClickListener(this);
        ivFavorite3.setOnClickListener(this);
        tvArchiveVideo.setOnClickListener(this);
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
        bNewestVideo.setAdapter(new NewestPhotosAdapter(mainPhotosResponse.getRecent(), mainView));
        setDataFavoriteList(mainPhotosResponse);
        photoCategoryTitleAdapter = new PhotosCategoryTitleAdapter(mainPhotosResponse.getListCategories(), mainView, this);
        rvCategoryTitles.setAdapter(photoCategoryTitleAdapter);
        categoryAdapter = new CategoryPhotosAdapter(mainPhotosResponse.getCategory(), mainView);
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
        mainView.showLoading();
        idCategoryTitle = category.getId();
        requestGetCategoryById(idCategoryTitle);
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
                idVideo=mainVideosResponse.getFavorites().get(position).getCategoryId();
                id=mainVideosResponse.getFavorites().get(position).getId();
              //  openPhotoDetail(categoriesList,position,idVideo,id);
                break;

            case R.id.ivFavorite2:
                categoriesList=mainVideosResponse.getFavorites();
                position=1;
                idVideo=mainVideosResponse.getFavorites().get(position).getCategoryId();
                id=mainVideosResponse.getFavorites().get(position).getId();
             //   openPhotoDetail(categoriesList,position,idVideo,id);
                break;

            case R.id.ivFavorite3:
                categoriesList=mainVideosResponse.getFavorites();
                position=2;
                idVideo=mainVideosResponse.getFavorites().get(position).getCategoryId();
                id=mainVideosResponse.getFavorites().get(position).getId();
               // openPhotoDetail(categoriesList,position,idVideo,id);
                break;
            case R.id.tvArchiveVideo:
                ArrayList<ListCategory> categoryTitleList = mainVideosResponse.getListCategories();
                Intent intent = new Intent(getActivity(), PhotoArchiveActivity.class);
                intent.putParcelableArrayListExtra("CategoryTitle", categoryTitleList);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemArchiveVideoClick(int position, Category category, ArrayList<Category> recent)
    {
        openPhotoDetail(recent,position,category.getCategoryId(),category.getId());
    }
    private void setCategoryListData(CategoryByIdVideosResponse data)
    {
        categoryAdapter = new CategoryPhotosAdapter(data.getResults(), mainView);
        rvGrideCategories.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvGrideCategories.setAdapter(categoryAdapter);

       // rvGrid.setLayoutManager(new GridLayoutManager(getContext(), 3));
       // rvGrid.setAdapter(new ItemRecyclerViewAdapter(getContext(), list, this));//, interactionListener));
    }
}
