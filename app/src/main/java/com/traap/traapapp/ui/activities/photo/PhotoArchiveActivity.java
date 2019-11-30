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
import com.traap.traapapp.apiServices.model.mainVideos.Category;
import com.traap.traapapp.apiServices.model.mainVideos.ListCategory;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.ui.adapters.photo.PhotosArchiveAdapter;
import com.traap.traapapp.ui.adapters.photo.PhotosCategoryTitleAdapter;

import com.traap.traapapp.ui.base.BaseActivity;
import com.traap.traapapp.utilities.Tools;

public class PhotoArchiveActivity extends BaseActivity implements PhotosArchiveAdapter.ArchiveVideoListener
{

    private TextView tvTitle, tvUserName, tvPopularPlayer;
    private View imgBack, imgMenu;
    private ArrayList<ListCategory> categoryTitleList;
    private RecyclerView rvCategoryTitles, rvArchiveVideo;
    private PhotosCategoryTitleAdapter videoCategoryTitleAdapter;
    private boolean FLAG_Favorite = false;

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
        if(FLAG_Favorite){
            requestBookMarkPhotos();
        }else {
            requestArchivePhoto();

        }
    }

    private void requestBookMarkPhotos()
    {
        ArchiveVideoRequest request = new ArchiveVideoRequest();

        SingletonService.getInstance().getArchiveVideoService().getBookMarkPhoto(new OnServiceStatus<WebServiceClass<ArchiveVideoResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<ArchiveVideoResponse> response)
            {
                // mainView.hideLoading();
                try
                {

                    if (response.info.statusCode == 200)
                    {

                        onGetBookMarkPhotoSuccess(response.data);

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
                // mainView.hideLoading();
                Tools.showToast(getApplication(), message, R.color.red);
            }
        }, request);
    }

    private void requestArchivePhoto()
    {
        ArchiveVideoRequest request = new ArchiveVideoRequest();

        SingletonService.getInstance().getArchiveVideoService().getArchivePhoto(new OnServiceStatus<WebServiceClass<ArchiveVideoResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<ArchiveVideoResponse> response)
            {
                // mainView.hideLoading();
                try
                {

                    if (response.info.statusCode == 200)
                    {

                        onGetArchivePhotoSuccess(response.data);

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
                // mainView.hideLoading();
                Tools.showToast(getApplication(), message, R.color.red);
            }
        }, request);
    }

    private void onGetArchivePhotoSuccess(ArchiveVideoResponse data)
    {
        rvArchiveVideo.setAdapter(new PhotosArchiveAdapter(data.getResults(),FLAG_Favorite, this));
    }
    private void onGetBookMarkPhotoSuccess(ArchiveVideoResponse data)
    {
        rvArchiveVideo.setAdapter(new PhotosArchiveAdapter(data.getResults(),FLAG_Favorite, this));
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
            videoCategoryTitleAdapter = new PhotosCategoryTitleAdapter(categoryTitleList, this, false);
            rvCategoryTitles.setAdapter(videoCategoryTitleAdapter);


        }else {
            tvTitle.setText("عکس های مورد علاقه من");
        }
    }


    private void openVideoDetail(ArrayList<Category> categoriesList, int position, Integer idVideo, Integer id)
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
        Intent intent = new Intent(this, ShowBigPhotoActivity.class);
        if (FLAG_Favorite )
        {
            intent.putExtra("SRCImage", category.getImageName().getThumbnailLarge());

        }else{
            intent.putExtra("SRCImage", category.getCover());

        }
        intent.putExtra("LikeCount", category.getLikes());
        intent.putExtra("idPhoto",  category.getId());
        startActivity(intent);
        //openVideoDetail(recent, position, category.getCategoryId(), category.getId());
    }
}
