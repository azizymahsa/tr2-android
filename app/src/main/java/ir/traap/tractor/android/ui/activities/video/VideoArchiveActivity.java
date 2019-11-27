package ir.traap.tractor.android.ui.activities.video;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.model.mainVideos.ListCategory;
import ir.traap.tractor.android.conf.TrapConfig;
import ir.traap.tractor.android.ui.adapters.video.VideosCategoryTitleAdapter;
import ir.traap.tractor.android.ui.base.BaseActivity;

public class VideoArchiveActivity extends BaseActivity implements VideosCategoryTitleAdapter.TitleCategoryListener
{

    private TextView tvTitle, tvUserName, tvPopularPlayer;
    private View imgBack, imgMenu;
    private ArrayList<ListCategory> categoryTitleList;
    private RecyclerView rvCategoryTitles;
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

        rvCategoryTitles = findViewById(R.id.rvCategoryTitles);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, true);
        rvCategoryTitles.setLayoutManager(layoutManager);

        videoCategoryTitleAdapter = new VideosCategoryTitleAdapter(categoryTitleList,  this);
        rvCategoryTitles.setAdapter(videoCategoryTitleAdapter);
    }

    @Override
    public void onItemTitleCategoryClick(ListCategory category)
    {

    }
}
