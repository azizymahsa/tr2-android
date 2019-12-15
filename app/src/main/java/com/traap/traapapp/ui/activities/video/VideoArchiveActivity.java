package com.traap.traapapp.ui.activities.video;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.adapters.video.VideosArchiveAdapter;
import com.traap.traapapp.ui.adapters.video.VideosCategoryTitleAdapter;
import com.traap.traapapp.ui.base.BaseActivity;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;

public class VideoArchiveActivity extends BaseActivity implements VideosCategoryTitleAdapter.TitleCategoryListener, VideosArchiveAdapter.ArchiveVideoListener
{

    private TextView tvTitle, tvUserName, tvPopularPlayer;
    private View imgBack, imgMenu, rlShirt;
    private ArrayList<ListCategory> categoryTitleList;
    private RecyclerView rvCategoryTitles, rvArchiveVideo;
    private VideosCategoryTitleAdapter videoCategoryTitleAdapter;
    private int position = 0;
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
        if (FLAG_Favorite)
        {
            requestBookMarkVideos();
        } else
        {
            requestArchiveVideo(position);

        }
    }

    private void requestBookMarkVideos()
    {
        ArchiveVideoRequest request = new ArchiveVideoRequest();

        SingletonService.getInstance().getArchiveVideoService().getBookMarkVideo(new OnServiceStatus<WebServiceClass<ArchiveVideoResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<ArchiveVideoResponse> response)
            {
                // mainView.hideLoading();
                try
                {

                    if (response.info.statusCode == 200)
                    {

                        onGetBookMarkVideoSuccess(response.data);

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
                // Tools.showToast(getApplication(), message, R.color.red);
                if (!Tools.isNetworkAvailable(VideoArchiveActivity.this))
                {
                    Logger.e("-OnError-", "Error: " + message);
                    showError(getApplicationContext(), "خطا در دریافت اطلاعات از سرور!");
                } else
                {
                    showAlert(getApplicationContext(), R.string.networkErrorMessage, R.string.networkError);
                }
            }
        }, request);
    }

    private void requestArchiveVideo(int position)
    {
        ArchiveVideoRequest request = new ArchiveVideoRequest();

        SingletonService.getInstance().getArchiveVideoService().getArchiveVideo(new OnServiceStatus<WebServiceClass<ArchiveVideoResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<ArchiveVideoResponse> response)
            {
                // mainView.hideLoading();
                try
                {

                    if (response.info.statusCode == 200)
                    {

                        onGetArchiveVideoSuccess(response.data);

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
                if (!Tools.isNetworkAvailable(VideoArchiveActivity.this))
                {
                    Logger.e("-OnError-", "Error: " + message);
                    showError(getApplicationContext(), "خطا در دریافت اطلاعات از سرور!");
                } else
                {
                    showAlert(getApplicationContext(), R.string.networkErrorMessage, R.string.networkError);
                }
            }
        }, request, categoryTitleList.get(position).getId());
    }

    private void onGetArchiveVideoSuccess(ArchiveVideoResponse data)
    {
        rvArchiveVideo.setAdapter(new VideosArchiveAdapter(data.getResults(), FLAG_Favorite, this));
    }

    private void onGetBookMarkVideoSuccess(ArchiveVideoResponse data)
    {
        rvArchiveVideo.setAdapter(new VideosArchiveAdapter(data.getResults(), FLAG_Favorite, this));
    }

    private void initView()
    {
        try
        {
            tvTitle = findViewById(R.id.tvTitle);
            if (!FLAG_Favorite)
            {
                tvTitle.setText("آرشیو ویدیو");
            } else
            {
                tvTitle.setText("ویدیو های مورد علاقه من");
            }
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
            rlShirt.setOnClickListener(v -> startActivity(new Intent(SingletonContext.getInstance().getContext(), MyProfileActivity.class))
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
        videoCategoryTitleAdapter = new VideosCategoryTitleAdapter(categoryTitleList, this);
        rvCategoryTitles.setAdapter(videoCategoryTitleAdapter);
    }

    @Override
    public void onItemTitleCategoryClick(ListCategory category, int position)
    {
        requestArchiveVideo(position);

    }

    private void openVideoDetail(ArrayList<Category> categoriesList, int position, Integer idVideo, Integer id)
    {
        Intent intent = new Intent(this, VideoDetailActivity.class);

        intent.putParcelableArrayListExtra("Videos", categoriesList);
        intent.putExtra("IdVideoCategory", idVideo);
        intent.putExtra("IdVideo", id);
        intent.putExtra("positionVideo", position);

        startActivity(intent);
    }

    @Override
    public void onItemArchiveVideoClick(int position, Category category, ArrayList<Category> recent)
    {
        openVideoDetail(recent, position, category.getCategoryId(), category.getId());
    }
}
