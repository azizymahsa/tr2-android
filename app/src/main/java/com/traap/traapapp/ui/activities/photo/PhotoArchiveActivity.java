package com.traap.traapapp.ui.activities.photo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.archiveVideo.ArchiveVideoRequest;
import com.traap.traapapp.apiServices.model.archiveVideo.ArchiveVideoResponse;
import com.traap.traapapp.apiServices.model.categoryByIdVideo.CategoryByIdVideosRequest;
import com.traap.traapapp.apiServices.model.categoryByIdVideo.CategoryByIdVideosResponse;
import com.traap.traapapp.apiServices.model.mainVideos.Category;
import com.traap.traapapp.apiServices.model.mainVideos.ListCategory;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.userProfile.UserProfileActivity;
import com.traap.traapapp.ui.adapters.photo.PhotosArchiveAdapter;
import com.traap.traapapp.ui.adapters.photo.PhotosCategoryTitleAdapter;

import com.traap.traapapp.ui.base.BaseActivity;
import com.traap.traapapp.utilities.Tools;

public class PhotoArchiveActivity extends BaseActivity implements PhotosArchiveAdapter.ArchiveVideoListener, PhotosCategoryTitleAdapter.TitleCategoryListener
{

    private TextView tvTitle, tvUserName, tvPopularPlayer;
    private View imgBack, imgMenu,rlShirt;
    private ArrayList<ListCategory> categoryTitleList;
    private RecyclerView rvCategoryTitles, rvArchiveVideo;
    private PhotosCategoryTitleAdapter videoCategoryTitleAdapter;
    private boolean FLAG_Favorite = false;
    private Integer idCategoryTitle = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_archive);
        if (savedInstanceState == null)
        {
            Bundle extras = getIntent().getExtras();
            if (extras == null)
            {

            } else
            {
                FLAG_Favorite = extras.getBoolean("FLAG_Favorite", false);

                if (!FLAG_Favorite)
                    categoryTitleList = extras.getParcelableArrayList("CategoryTitle");
            }
        }
        initView();
        if (FLAG_Favorite)
        {
            requestBookMarkPhotos();
        } else
        {
            requestArchivePhoto();

        }
    }

    /* Called when the activity is paused */
   /* public void onPause()
    {
        super.onPause();

        //Call the method
        if (FLAG_Favorite)
        {
            requestBookMarkPhotos();
        } else
        {
            requestArchivePhoto();

        }
    }*/

    public void onResume()
    {
        super.onResume();

        //Call the method
        if (FLAG_Favorite)
        {
            requestBookMarkPhotos();
        } else
        {
            requestArchivePhoto();

        }
    }

    public void showLoading()
    {
        findViewById(R.id.rlLoading).setVisibility(View.VISIBLE);
    }

    public void hideLoading()
    {
        findViewById(R.id.rlLoading).setVisibility(View.GONE);
    }

    private void requestBookMarkPhotos()
    {
        showLoading();
        ArchiveVideoRequest request = new ArchiveVideoRequest();

        SingletonService.getInstance().getArchiveVideoService().getBookMarkPhoto(new OnServiceStatus<WebServiceClass<ArchiveVideoResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<ArchiveVideoResponse> response)
            {
                hideLoading();
                try
                {

                    if (response.info.statusCode == 200)
                    {

                        onGetBookMarkPhotoSuccess(response.data.getResults());

                    } else
                    {
                        Tools.showToast(getApplicationContext(), response.info.message, R.color.red);
                    }
                } catch (Exception e)
                {
                    Tools.showToast(getApplicationContext(), e.getMessage(), R.color.red);

                }
            }

            @Override
            public void onError(String message)
            {
                hideLoading();
                Tools.showToast(getApplication(), message, R.color.red);
            }
        }, request);
    }

    private void requestArchivePhoto()
    {
        showLoading();
        String categoryId = null;
        ArchiveVideoRequest request = new ArchiveVideoRequest();
        if (idCategoryTitle != 0)
        {
            categoryId = idCategoryTitle.toString();
        } else if (categoryTitleList != null && categoryTitleList.size() > 0)
        {
            categoryId = categoryTitleList.get(0).getId().toString();
        }
        SingletonService.getInstance().getArchiveVideoService().getArchivePhoto(new OnServiceStatus<WebServiceClass<ArchiveVideoResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<ArchiveVideoResponse> response)
            {
                hideLoading();
                try
                {

                    if (response.info.statusCode == 200)
                    {

                        onGetArchivePhotoSuccess(response.data.getResults());

                    } else
                    {
                        Tools.showToast(getApplicationContext(), response.info.message, R.color.red);
                    }
                } catch (Exception e)
                {
                    Tools.showToast(getApplicationContext(), e.getMessage(), R.color.red);

                }
            }

            @Override
            public void onError(String message)
            {
                hideLoading();
                Tools.showToast(getApplication(), message, R.color.red);
            }
        }, categoryId);
    }

    private void onGetArchivePhotoSuccess(ArrayList<Category> data)
    {
        rvArchiveVideo.setAdapter(new PhotosArchiveAdapter(data, FLAG_Favorite, this));
    }

    private void onGetBookMarkPhotoSuccess(ArrayList<Category> data)
    {
        rvArchiveVideo.setAdapter(new PhotosArchiveAdapter(data, FLAG_Favorite, this));
    }

    private void initView()
    {
        try
        {
            tvTitle = findViewById(R.id.tvTitle);

            tvUserName = findViewById(R.id.tvUserName);
            tvUserName.setText(TrapConfig.HEADER_USER_NAME);

            imgMenu = findViewById(R.id.imgMenu);
            imgMenu.setVisibility(View.GONE);

            tvPopularPlayer = findViewById(R.id.tvPopularPlayer);
            tvPopularPlayer.setText(Prefs.getString("PopularPlayer", "12"));

            imgBack = findViewById(R.id.imgBack);
            imgBack.setOnClickListener(v ->
            {
                finish();
            });
            rlShirt = findViewById(R.id.rlShirt);
            rlShirt.setOnClickListener(v -> startActivity(new Intent(SingletonContext.getInstance().getContext(), UserProfileActivity.class))
            );
        } catch (Exception e)
        {

        }
        rvArchiveVideo = findViewById(R.id.rvArchiveVideo);
        rvCategoryTitles = findViewById(R.id.rvCategoryTitles);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, true);
        rvCategoryTitles.setLayoutManager(layoutManager);
        LinearLayoutManager layoutManagerArchive = new LinearLayoutManager(getApplicationContext());
        rvArchiveVideo.setLayoutManager(layoutManagerArchive);
        if (!FLAG_Favorite)
        {
            tvTitle.setText("آرشیو عکس");
            videoCategoryTitleAdapter = new PhotosCategoryTitleAdapter(categoryTitleList, this);
            rvCategoryTitles.setAdapter(videoCategoryTitleAdapter);


        } else
        {
            tvTitle.setText("عکس های مورد علاقه من");
        }
    }


    private void openCategoryDetail(ArrayList<Category> categoriesList, int position, Integer idVideo, Integer id)
    {
        Intent intent = new Intent(this, AlbumDetailActivity.class);

        intent.putParcelableArrayListExtra("Photos", categoriesList);
        intent.putExtra("IdPhotoCategory", idVideo);
        intent.putExtra("IdPhoto", id);
        intent.putExtra("positionPhoto", position);

        startActivity(intent);
    }

    @Override
    public void onItemArchiveVideoClick(int position, Category category, ArrayList<Category> recent)
    {

        if (FLAG_Favorite)
        {
            Intent intent = new Intent(this, ShowBigPhotoActivity.class);

            intent.putExtra("SRCImage", category.getImageName().getThumbnailLarge());
            intent.putExtra("LikeCount", category.getLikes());
            intent.putExtra("idPhoto", category.getId());
            intent.putExtra("isLike", category.getIsLiked());
            intent.putExtra("isBookmark", category.getIsBookmarked());
            startActivity(intent);
        } else
        {
            openCategoryDetail(recent, position, category.getId(), category.getId());
        }
    }

    private void requestGetCategoryById(Integer idCategoryTitle)
    {

        CategoryByIdVideosRequest request = new CategoryByIdVideosRequest();

        SingletonService.getInstance().categoryByIdVideosService().categoryByIdPhotosService(idCategoryTitle, request, new OnServiceStatus<WebServiceClass<CategoryByIdVideosResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<CategoryByIdVideosResponse> response)
            {
                //  mainView.hideLoading();
                try
                {

                    if (response.info.statusCode == 200)
                    {

                        onGetArchivePhotoSuccess(response.data.getResults());
                    } else
                    {
                        Tools.showToast(getApplicationContext(), response.info.message, R.color.red);
                    }
                } catch (Exception e)
                {
                    Tools.showToast(getApplicationContext(), e.getMessage(), R.color.red);

                }
            }

            @Override
            public void onError(String message)
            {
                //  mainView.hideLoading();
                Tools.showToast(getApplicationContext(), message, R.color.red);
            }
        });
    }

    @Override
    public void onItemTitleCategoryClick(ListCategory category)
    {
        //mainView.showLoading();
        idCategoryTitle = category.getId();
        requestGetCategoryById(idCategoryTitle);
    }
}
