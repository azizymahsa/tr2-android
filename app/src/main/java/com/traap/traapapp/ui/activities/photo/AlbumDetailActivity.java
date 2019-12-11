package com.traap.traapapp.ui.activities.photo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moeidbannerlibrary.banner.BannerLayout;
import com.makeramen.roundedimageview.RoundedImageView;
import com.pixplicity.easyprefs.library.Prefs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.categoryByIdVideo.CategoryByIdVideosRequest;
import com.traap.traapapp.apiServices.model.categoryByIdVideo.CategoryByIdVideosResponse;
import com.traap.traapapp.apiServices.model.likeVideo.LikeVideoRequest;
import com.traap.traapapp.apiServices.model.likeVideo.LikeVideoResponse;
import com.traap.traapapp.apiServices.model.mainVideos.Category;
import com.traap.traapapp.apiServices.model.photo.response.Content;
import com.traap.traapapp.apiServices.model.photo.response.PhotosByIdResponse;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.ui.adapters.photo.AlbumDetailsItemAdapter;
import com.traap.traapapp.ui.adapters.photo.NewestPhotosAdapter;
import com.traap.traapapp.ui.base.BaseActivity;
import com.traap.traapapp.utilities.Tools;
import com.traap.traapapp.utilities.Utility;

public class AlbumDetailActivity extends BaseActivity implements View.OnClickListener, AlbumDetailsItemAdapter.OnItemAllMenuClickListener
{
    private TextView tvTitle, tvUserName, tvPopularPlayer, tvLike;
    private View imgBack, imgMenu;
    private RoundedImageView ivPhoto;
    private ImageView imgBookmark, imgLike;
    private int positionVideo, idVideoCategory;
    private ArrayList<Category> videosList;
    private Category videoItem;
    private RelativeLayout rlLike;
    private String urlVideo;
    private int idVideo;
    private Integer likeCount = 0;
    private BannerLayout bRelatedAlbums;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private AlbumDetailsItemAdapter adapter;
    private TextView titleAlbum, tvCaption;
    private Integer idPhoto;
    private String largeImageClick = "";
    private String coverImg = "";
    private Boolean isBookmark = false;
    private Boolean isLike = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        if (savedInstanceState == null)
        {
            Bundle extras = getIntent().getExtras();
            if (extras == null)
            {

            } else
            {

                videosList = extras.getParcelableArrayList("Photos");
                idVideoCategory = extras.getInt("IdVideoCategory", 0);
                idVideo = extras.getInt("IdPhoto", 0);
                positionVideo = extras.getInt("positionPhoto", 0);
            }
        }

        initView();
    }

    private void initView()
    {
        try
        {
            bRelatedAlbums = findViewById(R.id.bRelatedAlbums);
            recyclerView = findViewById(R.id.recyclerView);
            titleAlbum = findViewById(R.id.titleAlbum);
            tvCaption = findViewById(R.id.tvCaption);

            tvTitle = findViewById(R.id.tvTitle);
            tvTitle.setText("محتوای یک عکس");

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

        ivPhoto = findViewById(R.id.ivPhoto);
        ivPhoto.setOnClickListener(this);
        imgBookmark = findViewById(R.id.imgBookmark);
        imgLike = findViewById(R.id.imgLike);
        tvLike = findViewById(R.id.tvLike);
        rlLike = findViewById(R.id.rlLike);
        rlLike.setOnClickListener(this);
        showLoading();


        sendRequestListPhotos(idVideo);


    }
    public void onResume()
    {
        super.onResume();

        //Call the method
        sendRequestListPhotos(idVideo);

    }
    public void showLoading()
    {
        findViewById(R.id.rlLoading).setVisibility(View.VISIBLE);
    }

    public void hideLoading()
    {
        findViewById(R.id.rlLoading).setVisibility(View.GONE);
    }

    private void sendRequestListPhotos(int idVideoCategory)
    {
        CategoryByIdVideosRequest request = new CategoryByIdVideosRequest();

        SingletonService.getInstance().categoryByIdVideosService().photosByIdPhotosService(idVideoCategory, request, new OnServiceStatus<WebServiceClass<PhotosByIdResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<PhotosByIdResponse> response)
            {
                hideLoading();
                try
                {

                    if (response.info.statusCode == 200)
                    {

                        setRelatedPhotosData(response.data);
                        requestGetRelatedVideos(response.data.getCategoryId());

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
                Tools.showToast(getApplicationContext(), message, R.color.red);
            }
        });
    }

    private void setRelatedPhotosData(PhotosByIdResponse data)
    {
        //recycler
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        recyclerView.setLayoutManager(layoutManager);

       titleAlbum.setText(data.getTitle() + "");
        tvCaption.setText(data.getCaption()+ "");
        for (int i = 0; i < data.getContent().size(); i++)
        {
            if (data.getContent().get(i).getIsCover())
            {
                setImageBackground(ivPhoto, data.getContent().get(i).getImageName().getThumbnailLarge().replace("\\", ""));
                idPhoto = data.getContent().get(i).getId();
                likeCount = data.getContent().get(i).getLikes();
                isLike = data.getContent().get(i).getIsLiked();
                isBookmark = data.getContent().get(i).getIsBookmarked();
                tvLike.setText(likeCount + "");
                coverImg = data.getContent().get(i).getCover();
                largeImageClick = data.getContent().get(i).getImageName().getThumbnailLarge();
            }

        }

        adapter = new AlbumDetailsItemAdapter(this, data.getContent(), this);
        recyclerView.setAdapter(adapter);
    }

    private void requestGetRelatedVideos(int idVideoCategory)
    {
        CategoryByIdVideosRequest request = new CategoryByIdVideosRequest();

        SingletonService.getInstance().categoryByIdVideosService().categoryByIdPhotosService(idVideoCategory, request, new OnServiceStatus<WebServiceClass<CategoryByIdVideosResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<CategoryByIdVideosResponse> response)
            {
                hideLoading();
                try
                {

                    if (response.info.statusCode == 200)
                    {

                        setRelatedVideoData(response.data);

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
                Tools.showToast(getApplicationContext(), message, R.color.red);
            }
        });
    }

    private void setRelatedVideoData(CategoryByIdVideosResponse data)
    {
        if (!data.getResults().isEmpty())
        {
            bRelatedAlbums.setAdapter(new NewestPhotosAdapter(data.getResults()));
            List<Category> relatedList = data.getResults();

           /* for (int i = 0; i < relatedList.size(); i++)
            {
                // setImageBackground(ivRelated1, relatedList.get(i).getCover().replace("\\", ""));
               *//* setImageBackground(ivRelated2, relatedList.get(1).getCover().replace("\\", ""));
                setImageBackground(ivRelated3, relatedList.get(2).getCover().replace("\\", ""));
                setImageBackground(ivRelated4, relatedList.get(3).getCover().replace("\\", ""));*//*
            }*/


        }
    }



    private void setImageBackground(ImageView image, String link)
    {
        try
        {
            Glide.with(this).load(Uri.parse(link)).into(image);

        } catch (NullPointerException e)
        {
            Picasso.with(this).load(R.drawable.img_failure).into(image);
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
           /* case R.id.rlVideo:
                playVideo(urlVideo);
                break;*/
            case R.id.rlLike:
                imgLike.setColorFilter(getResources().getColor(R.color.backgroundButton));
                tvLike.setTextColor(getResources().getColor(R.color.backgroundButton));
                requestLikeVideo();
                break;
            case R.id.ivPhoto:

                Intent intent = new Intent(this, ShowBigPhotoActivity.class);
                if (largeImageClick == "")
                    largeImageClick = coverImg;

                intent.putExtra("SRCImage", largeImageClick);
                intent.putExtra("LikeCount", likeCount);
                intent.putExtra("idPhoto", idPhoto);
                intent.putExtra("isLike", isLike);
                intent.putExtra("isBookmark", isBookmark);
                startActivity(intent);
                break;
        }
    }

    private void requestLikeVideo()
    {
        showLoading();
        LikeVideoRequest request = new LikeVideoRequest();

        SingletonService.getInstance().getLikeVideoService().likePhotoService(idPhoto, request, new OnServiceStatus<WebServiceClass<LikeVideoResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<LikeVideoResponse> response)
            {
                // rlLike.setClickable(true);
                hideLoading();

                try
                {

                    if (response.info.statusCode == 200)
                    {

                        setLiked(response.data);

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
                Tools.showToast(getApplicationContext(), message, R.color.red);
                //rlLike.setClickable(true);

            }
        });
    }

    private void setLiked(LikeVideoResponse data)
    {
        if (data.getIsLiked())
        {
            imgLike.setColorFilter(getResources().getColor(R.color.backgroundButton));
            tvLike.setTextColor(getResources().getColor(R.color.backgroundButton));
            likeCount = likeCount + 1;
            tvLike.setText(likeCount + "");

        } else
        {
            imgLike.setColorFilter(getResources().getColor(R.color.gray));
            tvLike.setTextColor(getResources().getColor(R.color.gray));
            if (likeCount > 0)
                likeCount = likeCount - 1;
            tvLike.setText(likeCount + "");
        }
        //tvLike.setText();

    }

    private void playVideo(String urlVideo)
    {
        Utility.openUrlCustomTab(this, urlVideo);
    }

    @Override
    public void OnItemAllMenuClick(View view, Integer id, Content content)
    {
       // titleAlbum.setText(content.getTitle() + "");
        tvCaption.setText(content.getCaption() + " "+content.getTitle() );
        idPhoto = content.getId();
        likeCount = content.getLikes();
        isBookmark = content.getIsBookmarked();
        isLike = content.getIsLiked();

        tvLike.setText(likeCount + "");
        if (content.getImageName().getThumbnailLarge() == "")
            largeImageClick = content.getCover();
        else
            largeImageClick = content.getImageName().getThumbnailLarge();

        setImageBackground(ivPhoto, content.getImageName().getThumbnailLarge().replace("\\", ""));


    }
}
