package ir.traap.tractor.android.ui.activities.photo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.generator.SingletonService;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.archiveVideo.ArchiveVideoRequest;
import ir.traap.tractor.android.apiServices.model.archiveVideo.ArchiveVideoResponse;
import ir.traap.tractor.android.apiServices.model.mainVideos.Category;
import ir.traap.tractor.android.apiServices.model.mainVideos.ListCategory;
import ir.traap.tractor.android.conf.TrapConfig;
import ir.traap.tractor.android.ui.adapters.photo.PhotosArchiveAdapter;
import ir.traap.tractor.android.ui.adapters.photo.PhotosCategoryTitleAdapter;

import ir.traap.tractor.android.ui.base.BaseActivity;
import ir.traap.tractor.android.utilities.Tools;

public class PhotoArchiveActivity extends BaseActivity implements PhotosCategoryTitleAdapter.TitleCategoryListener, PhotosArchiveAdapter.ArchiveVideoListener
{

    private TextView tvTitle, tvUserName, tvPopularPlayer;
    private View imgBack, imgMenu;
    private ArrayList<ListCategory> categoryTitleList;
    private RecyclerView rvCategoryTitles,rvArchiveVideo;
    private PhotosCategoryTitleAdapter videoCategoryTitleAdapter;

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
        requestArchivePhoto();
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
        rvArchiveVideo.setAdapter(new PhotosArchiveAdapter(data.getResults(),this));
    }

    private void initView()
    {
        try
        {
            tvTitle = findViewById(R.id.tvTitle);
            tvTitle.setText("آرشیو عکس");

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
        videoCategoryTitleAdapter = new PhotosCategoryTitleAdapter(categoryTitleList,  this);
        rvCategoryTitles.setAdapter(videoCategoryTitleAdapter);
    }

    @Override
    public void onItemTitleCategoryClick(ListCategory category)
    {

    }

    private void openVideoDetail(ArrayList<Category> categoriesList, int position, Integer idVideo, Integer id)
    {
        Intent intent = new Intent(this, PhotoDetailActivity.class);

        intent.putParcelableArrayListExtra("Photos", categoriesList);
        intent.putExtra("IdPhotoCategory",idVideo);
        intent.putExtra("IdPhoto",id);
        intent.putExtra("positionPhoto",position);

        startActivity(intent);
    }

    @Override
    public void onItemArchiveVideoClick(int position, Category category, ArrayList<Category> recent)
    {
        openVideoDetail(recent,position,category.getCategoryId(),category.getId());
    }
}
