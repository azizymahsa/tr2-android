package ir.traap.tractor.android.ui.activities.video;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.generator.SingletonService;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.archiveVideo.ArchiveVideo;
import ir.traap.tractor.android.apiServices.model.archiveVideo.ArchiveVideoRequest;
import ir.traap.tractor.android.apiServices.model.archiveVideo.ArchiveVideoResponse;
import ir.traap.tractor.android.apiServices.model.mainVideos.Category;
import ir.traap.tractor.android.apiServices.model.mainVideos.ListCategory;
import ir.traap.tractor.android.conf.TrapConfig;
import ir.traap.tractor.android.ui.adapters.video.VideosArchiveAdapter;
import ir.traap.tractor.android.ui.adapters.video.VideosCategoryTitleAdapter;
import ir.traap.tractor.android.ui.base.BaseActivity;
import ir.traap.tractor.android.utilities.Tools;

public class VideoArchiveActivity extends BaseActivity implements VideosCategoryTitleAdapter.TitleCategoryListener, VideosArchiveAdapter.ArchiveVideoListener
{

    private TextView tvTitle, tvUserName, tvPopularPlayer;
    private View imgBack, imgMenu;
    private ArrayList<ListCategory> categoryTitleList;
    private RecyclerView rvCategoryTitles,rvArchiveVideo;
    private VideosCategoryTitleAdapter videoCategoryTitleAdapter;

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
                categoryTitleList = extras.getParcelableArrayList("CategoryTitle");
            }
        }
        initView();
        requestArchiveVideo();
    }

    private void requestArchiveVideo()
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
                Tools.showToast(getApplication(), message, R.color.red);
            }
        }, request);
    }

    private void onGetArchiveVideoSuccess(ArchiveVideoResponse data)
    {
        rvArchiveVideo.setAdapter(new VideosArchiveAdapter(data.getResults(),this));
    }

    private void initView()
    {
        try
        {
            tvTitle = findViewById(R.id.tvTitle);
            tvTitle.setText("آرشیو فیلم");

            tvUserName = findViewById(R.id.tvUserName);
            tvUserName.setText(TrapConfig.HEADER_USER_NAME);

            imgMenu = findViewById(R.id.imgMenu);
            imgMenu.setVisibility(View.GONE);

            tvPopularPlayer = findViewById(R.id.tvPopularPlayer);
            tvPopularPlayer.setText(Prefs.getString("PopularPlayer", ""));

            imgBack = findViewById(R.id.imgBack);
            imgBack.setOnClickListener(v ->
            {
                finish();
            });
        } catch (Exception e)
        {

        }
        rvArchiveVideo=findViewById(R.id.rvArchiveVideo);
        rvCategoryTitles = findViewById(R.id.rvCategoryTitles);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, true);
        rvCategoryTitles.setLayoutManager(layoutManager);
        LinearLayoutManager layoutManagerArchive = new LinearLayoutManager(getApplicationContext());
        rvArchiveVideo.setLayoutManager(layoutManagerArchive);
        videoCategoryTitleAdapter = new VideosCategoryTitleAdapter(categoryTitleList,  this);
        rvCategoryTitles.setAdapter(videoCategoryTitleAdapter);
    }

    @Override
    public void onItemTitleCategoryClick(ListCategory category)
    {

    }

    private void openVideoDetail(ArrayList<Category> categoriesList, int position, Integer idVideo, Integer id)
    {
        Intent intent = new Intent(this, VideoDetailActivity.class);

        intent.putParcelableArrayListExtra("Videos", categoriesList);
        intent.putExtra("IdVideoCategory",idVideo);
        intent.putExtra("IdVideo",id);
        intent.putExtra("positionVideo",position);

        startActivity(intent);
    }

    @Override
    public void onItemArchiveVideoClick(int position, Category category, ArrayList<Category> recent)
    {
        openVideoDetail(recent,position,category.getCategoryId(),category.getId());
    }
}
