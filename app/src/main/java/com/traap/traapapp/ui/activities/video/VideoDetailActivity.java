package com.traap.traapapp.ui.activities.video;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.pixplicity.easyprefs.library.Prefs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.bookMarkPhoto.BookMarkPhotoRequest;
import com.traap.traapapp.apiServices.model.bookMarkPhoto.BookMarkPhotoResponse;
import com.traap.traapapp.apiServices.model.categoryByIdVideo.CategoryByIdVideosRequest;
import com.traap.traapapp.apiServices.model.categoryByIdVideo.CategoryByIdVideosResponse;
import com.traap.traapapp.apiServices.model.likeVideo.LikeVideoRequest;
import com.traap.traapapp.apiServices.model.likeVideo.LikeVideoResponse;
import com.traap.traapapp.apiServices.model.mainVideos.Category;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.userProfile.UserProfileActivity;
import com.traap.traapapp.ui.base.BaseActivity;
import com.traap.traapapp.utilities.Tools;
import com.traap.traapapp.utilities.Utility;

public class VideoDetailActivity extends BaseActivity implements View.OnClickListener
{
    private TextView tvTitle, tvUserName, tvPopularPlayer, tvDate, tvTitleVideo, tvDesc, tvLike;
    private View imgBack, imgMenu,rlShirt;
    private RoundedImageView ivVideo, ivRelated1, ivRelated2, ivRelated3, ivRelated4;
    private ImageView imgBookmark, imgLike;
    private int positionVideo, idVideoCategory;
    private ArrayList<Category> videosList;
    private Category videoItem;
    private RelativeLayout rlVideo, rlLike;
    private String urlVideo;
    private int idVideo;
    private Integer likeCount = 0;
    private ArrayList<Category> relatedList;
    private ImageView btnShareVideo, btnBookmark;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        if (savedInstanceState == null)
        {
            Bundle extras = getIntent().getExtras();
            if (extras == null)
            {

            } else
            {
                videosList = extras.getParcelableArrayList("Videos");
                idVideoCategory = extras.getInt("IdVideoCategory", 0);
                idVideo = extras.getInt("IdVideo", 0);
                positionVideo = extras.getInt("positionVideo", 0);
            }
        }

        initView();
    }

    private void initView()
    {
        try
        {
            tvTitle = findViewById(R.id.tvTitle);
            tvTitle.setText("محتوای یک ویدیو");

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

        tvDate = findViewById(R.id.tvDate);
        tvTitleVideo = findViewById(R.id.tvTitleVideo);
        tvDesc = findViewById(R.id.tvDesc);
        ivVideo = findViewById(R.id.ivVideo);
        imgBookmark = findViewById(R.id.btnBookmark);
        imgLike = findViewById(R.id.imgLike);
        tvLike = findViewById(R.id.tvLike);
        rlVideo = findViewById(R.id.rlVideo);
        rlLike = findViewById(R.id.rlLike);
        ivRelated1 = findViewById(R.id.ivRelated1);
        ivRelated2 = findViewById(R.id.ivRelated2);
        ivRelated3 = findViewById(R.id.ivRelated3);
        ivRelated4 = findViewById(R.id.ivRelated4);
        btnBookmark = findViewById(R.id.btnBookmark);
        btnShareVideo = findViewById(R.id.btnShareVideo);

        ivRelated1.setOnClickListener(this);
        ivRelated2.setOnClickListener(this);
        ivRelated3.setOnClickListener(this);
        ivRelated4.setOnClickListener(this);
        btnBookmark.setOnClickListener(this);
        btnShareVideo.setOnClickListener(this);

        //imgLike.setOnClickListener(this);

        setDataItems(videosList, idVideoCategory, idVideo, positionVideo);

        requestGetRelatedVideos(idVideoCategory);
        rlVideo.setOnClickListener(this);
        rlLike.setOnClickListener(this);

    }

    private void requestGetRelatedVideos(int idVideoCategory)
    {
        CategoryByIdVideosRequest request = new CategoryByIdVideosRequest();

        SingletonService.getInstance().categoryByIdVideosService().categoryByIdVideosService(idVideoCategory, request, new OnServiceStatus<WebServiceClass<CategoryByIdVideosResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<CategoryByIdVideosResponse> response)
            {
                //mainView.hideLoading();
                try
                {

                    if (response.info.statusCode == 200)
                    {

                        setRelatedVideoData(response.data);

                    } else
                    {
                       // Tools.showToast(getApplicationContext(), response.info.message, R.color.red);
                    }
                } catch (Exception e)
                {
                  //  Tools.showToast(getApplicationContext(), e.getMessage(), R.color.red);

                }
            }

            @Override
            public void onError(String message)
            {
                //  mainView.hideLoading();
               // Tools.showToast(getApplicationContext(), message, R.color.red);
            }
        });
    }

    private void setRelatedVideoData(CategoryByIdVideosResponse data)
    {
        if (!data.getResults().isEmpty())
        {
            relatedList = data.getResults();
            setImageBackground(ivRelated1, relatedList.get(0).getBigPoster().replace("\\", ""));
            setImageBackground(ivRelated2, relatedList.get(1).getBigPoster().replace("\\", ""));
            setImageBackground(ivRelated3, relatedList.get(2).getBigPoster().replace("\\", ""));
            setImageBackground(ivRelated4, relatedList.get(3).getBigPoster().replace("\\", ""));

        }
    }

    private void setDataItems(ArrayList<Category> videosList, int idVideoCategorys, int idVideos, int positionVideo)
    {
        videoItem = videosList.get(positionVideo);
        idVideo=idVideos;
        idVideoCategory=idVideoCategorys;
        urlVideo = videoItem.getFrame().replace("\\", "");
        tvLike.setText(videoItem.getLikes().toString());
        likeCount = videoItem.getLikes();
        if (videoItem.getIsLiked())
        {
            imgLike.setColorFilter(getResources().getColor(R.color.backgroundButton));
            tvLike.setTextColor(getResources().getColor(R.color.backgroundButton));

        } else
        {
            imgLike.setColorFilter(getResources().getColor(R.color.gray));
            tvLike.setTextColor(getResources().getColor(R.color.gray));

        }
        if (videoItem.getIsBookmarked())
        {
            imgBookmark.setColorFilter(getResources().getColor(R.color.backgroundButton));
        } else
        {
            imgBookmark.setColorFilter(getResources().getColor(R.color.gray));
        }
        tvDate.setText(videoItem.getCreateDateFormatted());
        tvTitleVideo.setText(videoItem.getTitle());
        tvDesc.setText(videoItem.getCaption());
        setImageBackground(ivVideo, videoItem.getBigPoster().replace("\\", ""));
    }

    private void setImageBackground(ImageView image, String link)
    {
        try
        {
            Glide.with(this).load(Uri.parse(link)).into(image);
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
            case R.id.rlVideo:
                playVideo(urlVideo);
                break;
            case R.id.rlLike:
                imgLike.setColorFilter(getResources().getColor(R.color.backgroundButton));
                tvLike.setTextColor(getResources().getColor(R.color.backgroundButton));
                requestLikeVideo();
                break;
            case R.id.ivRelated1:
                onRelatedClick(0);
                break;
            case R.id.ivRelated2:
                onRelatedClick(1);
                break;
            case R.id.ivRelated3:
                onRelatedClick(2);
                break;
            case R.id.ivRelated4:
                onRelatedClick(3);
                break;
            case R.id.btnBookmark:
                btnBookmark.setColorFilter(getResources().getColor(R.color.backgroundButton));

                requestBookMark();
                break;

        }
    }

    private void requestBookMark()
    {
        BookMarkPhotoRequest request = new BookMarkPhotoRequest();

        SingletonService.getInstance().getLikeVideoService().bookMarkVideoService(idVideo, request, new OnServiceStatus<WebServiceClass<BookMarkPhotoResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<BookMarkPhotoResponse> response)
            {

                try
                {

                    if (response.info.statusCode == 200)
                    {

                        setBookMark(response.data);

                    } else
                    {
                       // Tools.showToast(getApplicationContext(), response.info.message, R.color.red);
                    }
                } catch (Exception e)
                {
                //    Tools.showToast(getApplicationContext(), e.getMessage(), R.color.red);

                }
            }

            @Override
            public void onError(String message)
            {
             //   Tools.showToast(getApplicationContext(), message, R.color.red);

            }
        });
    }

    private void setBookMark(BookMarkPhotoResponse data)
    {
        if (data.getBookMarked())
        {
            btnBookmark.setColorFilter(getResources().getColor(R.color.backgroundButton));


        } else
        {
            btnBookmark.setColorFilter(getResources().getColor(R.color.gray));

        }
    }

    private void onRelatedClick(int position)
    {
        setDataItems(relatedList, relatedList.get(position).getCategoryId(), relatedList.get(position).getId()
                , position);

        requestGetRelatedVideos(relatedList.get(position).getCategoryId());
    }

    private void requestLikeVideo()
    {
        //rlLike.setClickable(false);
        LikeVideoRequest request = new LikeVideoRequest();

        SingletonService.getInstance().getLikeVideoService().likeVideoService(idVideo, request, new OnServiceStatus<WebServiceClass<LikeVideoResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<LikeVideoResponse> response)
            {
                // rlLike.setClickable(true);

                try
                {

                    if (response.info.statusCode == 200)
                    {

                        setLiked(response.data);

                    } else
                    {
                      //  Tools.showToast(getApplicationContext(), response.info.message, R.color.red);
                    }
                } catch (Exception e)
                {
                   // Tools.showToast(getApplicationContext(), e.getMessage(), R.color.red);

                }
            }

            @Override
            public void onError(String message)
            {
                //  mainView.hideLoading();
              //  Tools.showToast(getApplicationContext(), message, R.color.red);
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
}
