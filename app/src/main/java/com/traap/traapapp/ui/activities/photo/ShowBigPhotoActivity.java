package com.traap.traapapp.ui.activities.photo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.bookMarkPhoto.BookMarkPhotoRequest;
import com.traap.traapapp.apiServices.model.bookMarkPhoto.BookMarkPhotoResponse;
import com.traap.traapapp.apiServices.model.likeVideo.LikeVideoRequest;
import com.traap.traapapp.apiServices.model.likeVideo.LikeVideoResponse;
import com.traap.traapapp.apiServices.model.photo.response.Content;
import com.traap.traapapp.ui.adapters.photo.AlbumDetailsItemAdapter;
import com.traap.traapapp.ui.base.BaseActivity;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.ScreenShot;
import com.traap.traapapp.utilities.Tools;

public class ShowBigPhotoActivity extends BaseActivity implements View.OnClickListener, AlbumDetailsItemAdapter.OnItemAllMenuClickListener
{
    private TextView tvLike;
    private TextView imgBack;
    private RoundedImageView ivPhoto,ivBigLike;
    private ImageView btnSharePic, imgLike, btnBookmark;

    private RelativeLayout rlLike, rlPic;

    private int likeCount = 0;

    private Boolean isBookmark = false;
    private Integer idPhoto;
    private String largeImageClick = "";
    private boolean isLike=false;

    private static final long DOUBLE_CLICK_TIME_DELTA = 300;
    long lastClickTime = 0;
    private boolean doubleClick=false;
    private boolean isMoving=false;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo);
        initView();
        if (savedInstanceState == null)
        {
            Bundle extras = getIntent().getExtras();
            if (extras == null)
            {

            } else
            {

                idPhoto = extras.getInt("idPhoto", 0);
                largeImageClick = extras.getString("SRCImage", "");
                likeCount = extras.getInt("LikeCount", 0);
                isBookmark = extras.getBoolean("isBookmark", false);
                isLike = extras.getBoolean("isLike", false);
                setImageBackground(ivPhoto, largeImageClick);
                tvLike.setText(likeCount + "");
                if(isLike){
                    imgLike.setColorFilter(getResources().getColor(R.color.backgroundButton));
                    tvLike.setTextColor(getResources().getColor(R.color.backgroundButton));
                }else{
                    imgLike.setColorFilter(getResources().getColor(R.color.white));
                    tvLike.setTextColor(getResources().getColor(R.color.white));
                }

            }
        }
        setColorBookmark();


    }

    private void setColorBookmark()
    {
        if (isBookmark)
            btnBookmark.setColorFilter(getResources().getColor(R.color.backgroundButton));
        else
            btnBookmark.setColorFilter(getResources().getColor(R.color.white));
    }

    private void initView()
    {
        try
        {

            imgBack = findViewById(R.id.imgBack);
            imgBack.setOnClickListener(v ->
            {
                finish();
            });


            ivPhoto = findViewById(R.id.ivPhoto);
            ivBigLike = findViewById(R.id.ivBigLike);
            btnSharePic = findViewById(R.id.btnSharePic);
            imgLike = findViewById(R.id.imgLike);
            tvLike = findViewById(R.id.tvLike);
            rlLike = findViewById(R.id.rlLike);
            rlPic = findViewById(R.id.rlPic);
            btnBookmark = findViewById(R.id.btnBookmark);


            rlLike.setOnClickListener(this);
            btnSharePic.setOnClickListener(this);
            ivPhoto.setOnClickListener(this);
            btnBookmark.setOnClickListener(this);
        } catch (Exception e)
        {
            e.getMessage();
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

            case R.id.rlLike:
               // imgLike.setColorFilter(getResources().getColor(R.color.backgroundButton));
               // tvLike.setTextColor(getResources().getColor(R.color.backgroundButton));
                requestLike();
                break;
            case R.id.btnSharePic:
                new ScreenShot(rlPic, this);

                break;
            case R.id.btnBookmark:
                btnBookmark.setColorFilter(getResources().getColor(R.color.backgroundButton));

                requestBookMark();

                break;
            case R.id.ivPhoto:
                v.setAlpha((float) 1.0);
                if(!isMoving){
                    long clickTime = System.currentTimeMillis();
                    if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA){
                        doubleClick = true;
                        System.out.println("-----------doubleClick");
                        lastClickTime = 0;
                        ivBigLike.setVisibility(View.VISIBLE);
                        requestLike();



                    } else {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if(!doubleClick){
                                    System.out.println("--------------singleClick");

                                }else
                                    doubleClick = false;
                            }
                        },350);
                    }
                    lastClickTime = clickTime;
                }

                break;
        }
    }

    private void requestBookMark()
    {
        BookMarkPhotoRequest request = new BookMarkPhotoRequest();

        SingletonService.getInstance().getLikeVideoService().bookMarkPhotoService(idPhoto, request, new OnServiceStatus<WebServiceClass<BookMarkPhotoResponse>>()
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
               // Tools.showToast(getApplicationContext(), message, R.color.red);
                if (!Tools.isNetworkAvailable(ShowBigPhotoActivity.this))
                {
                    Logger.e("-OnError-", "Error: " + message);
                    showError( getApplicationContext(),"خطا در دریافت اطلاعات از سرور!");
                } else
                {
                   // showError(getApplicationContext(),String.valueOf(R.string.networkErrorMessage));

                    showAlert(getApplicationContext(), R.string.networkErrorMessage, R.string.networkError);
                }
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
            btnBookmark.setColorFilter(getResources().getColor(R.color.white));

        }
    }

    private void requestLike()
    {
        LikeVideoRequest request = new LikeVideoRequest();

        SingletonService.getInstance().getLikeVideoService().likePhotoService(idPhoto, request, new OnServiceStatus<WebServiceClass<LikeVideoResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<LikeVideoResponse> response)
            {

                try
                {

                    if (response.info.statusCode == 200)
                    {
                        animateHeart(ivBigLike);
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
                if (!Tools.isNetworkAvailable(ShowBigPhotoActivity.this))
                {
                    Logger.e("-OnError-", "Error: " + message);
                    showError( getApplicationContext(),"خطا در دریافت اطلاعات از سرور!");
                } else
                {
                    // showError(getApplicationContext(),String.valueOf(R.string.networkErrorMessage));

                    showAlert(getApplicationContext(), R.string.networkErrorMessage, R.string.networkError);
                }
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

    }
    public void animateHeart(final ImageView view) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        prepareAnimation(scaleAnimation);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        prepareAnimation(alphaAnimation);

        AnimationSet animation = new AnimationSet(true);
        animation.addAnimation(alphaAnimation);
        animation.addAnimation(scaleAnimation);
        animation.setDuration(700);
        animation.setFillAfter(true);

        view.startAnimation(animation);

    }

    private Animation prepareAnimation(Animation animation){
        animation.setRepeatCount(1);
        animation.setRepeatMode(Animation.REVERSE);
        return animation;
    }

    @Override
    public void OnItemAllMenuClick(View view, Integer id, Content content)
    {


    }
}
