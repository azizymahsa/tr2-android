package com.traap.traapapp.ui.activities.photo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

import com.bumptech.glide.Glide;
import com.example.moeidbannerlibrary.banner.BannerLayout;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.pixplicity.easyprefs.library.Prefs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.bookMarkPhoto.BookMarkPhotoRequest;
import com.traap.traapapp.apiServices.model.categoryByIdVideo.CategoryByIdVideosRequest;
import com.traap.traapapp.apiServices.model.categoryByIdVideo.CategoryByIdVideosResponse;
import com.traap.traapapp.apiServices.model.likeVideo.LikeVideoRequest;
import com.traap.traapapp.apiServices.model.likeVideo.LikeVideoResponse;
import com.traap.traapapp.apiServices.model.mainVideos.Category;
import com.traap.traapapp.apiServices.model.photo.response.Content;
import com.traap.traapapp.apiServices.model.photo.response.PhotosByIdResponse;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.adapters.photo.AlbumDetailsItemAdapter;
import com.traap.traapapp.ui.adapters.photo.NewestPhotosAdapter;
import com.traap.traapapp.ui.base.BaseActivity;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;

public class AlbumDetailActivity extends BaseActivity implements View.OnClickListener, AlbumDetailsItemAdapter.OnItemAllMenuClickListener, NewestPhotosAdapter.OnItemRelatedAlbumsClickListener
{
    private TextView tvTitle, tvUserName, tvPopularPlayer, tvLike;
    private View imgBack, imgMenu, rlShirt, flLogoToolbar;
    private RoundedImageView ivPhoto, ivBigLike;
    private ImageView imgBookmark, imgLike;
    private int positionVideo, idVideoCategory;
    private ArrayList<Category> videosList;
    private Category videoItem;
    private RelativeLayout rlLike;
    private String urlVideo;
    private int idAlbum;
    private Integer likeCount = 0;
    private BannerLayout bRelatedAlbums;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private AlbumDetailsItemAdapter adapter;
    private TextView titleAlbum, tvCaption, tvEmpty;
    private Integer idPhoto;
    private String largeImageClick = "";
    private String coverImg = "";
    private Boolean isBookmark = false;
    private Boolean isLike = false;
    private static final long DOUBLE_CLICK_TIME_DELTA = 300;
    long lastClickTime = 0;
    private boolean doubleClick = false;
    private boolean isMoving = false;
    private Toolbar mToolbar;
    private List<Content> list;
    private Integer position;
    public Integer clickPositionX = 0;
    public Integer clickPositionY = 0;
    public Integer clickPosition;
    ScrollingPagerIndicator indicator;


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

            }
            else
            {

                videosList = extras.getParcelableArrayList("Photos");
                idVideoCategory = extras.getInt("IdVideoCategory", 0);
                idAlbum = extras.getInt("IdPhoto", 0);
                positionVideo = extras.getInt("positionPhoto", 0);
            }
        }

        initView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK)
        {
            Intent returnIntent = new Intent();

            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }

    private void initView()
    {
        try
        {
            tvEmpty = findViewById(R.id.tvEmpty);
            mToolbar = findViewById(R.id.toolbar);
            bRelatedAlbums = findViewById(R.id.bRelatedAlbums);
            recyclerView = findViewById(R.id.recyclerView);
            titleAlbum = findViewById(R.id.titleAlbum);
            tvCaption = findViewById(R.id.tvCaption);
            indicator = findViewById(R.id.indicator);

            tvTitle = findViewById(R.id.tvTitle);
            tvTitle.setText("محتوای یک عکس");

            tvUserName = findViewById(R.id.tvUserName);
            tvUserName.setText(TrapConfig.HEADER_USER_NAME);

            imgMenu = findViewById(R.id.imgMenu);
            imgMenu.setVisibility(View.GONE);

            tvPopularPlayer = findViewById(R.id.tvPopularPlayer);
            tvPopularPlayer.setText(Prefs.getString("PopularPlayer", "12"));

            imgBack = findViewById(R.id.imgBack);
            flLogoToolbar = mToolbar.findViewById(R.id.flLogoToolbar);
            imgBack.setOnClickListener(v ->
            {
                finish();
            });

            flLogoToolbar.setOnClickListener(v ->
            {
                Intent returnIntent = new Intent();

                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            });


            rlShirt = findViewById(R.id.rlShirt);
            rlShirt.setOnClickListener(v -> startActivityForResult(new Intent(SingletonContext.getInstance().getContext(),
                    MyProfileActivity.class), 100)
            );

        } catch (Exception e)
        {

        }

        ivBigLike = findViewById(R.id.ivBigLike);
        ivPhoto = findViewById(R.id.ivPhoto);
        ivPhoto.setOnClickListener(this);
        imgBookmark = findViewById(R.id.imgBookmark);
        imgLike = findViewById(R.id.imgLike);
        tvLike = findViewById(R.id.tvLike);
        rlLike = findViewById(R.id.rlLike);
        rlLike.setOnClickListener(this);
        showLoading();


        sendRequestListPhotos(idAlbum);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
/*
                clickPositionX+= dx;
                clickPositionY+= dy;
                Log.e("clickPositionX2", dx+"");
                Log.e("clickPositionY2", dy+"")*/
                ;
            }
        });
    }

    public void onResume()
    {
        super.onResume();

        //Call the method
        sendRequestListPhotos(idAlbum);

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
        showLoading();
        CategoryByIdVideosRequest request = new CategoryByIdVideosRequest();

        SingletonService.getInstance().categoryByIdVideosService().photosByIdPhotosService(idVideoCategory, request, new OnServiceStatus<WebServiceClass<PhotosByIdResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<PhotosByIdResponse> response)
            {
                try
                {
                    hideLoading();

                    if (response.info.statusCode == 200)
                    {

                        setRelatedPhotosData(response.data);
                        //   requestGetRelatedVideos(response.data.getCategoryId());
                        requestGetRelatedVideos(idAlbum);

                    }
                    else
                    {
                        showToast(AlbumDetailActivity.this, response.info.message, R.color.red);
                    }
                } catch (Exception e)
                {
                    showToast(AlbumDetailActivity.this, "خطا در دریافت اطلاعات از سرور!", R.color.red);

                }
            }

            @Override
            public void onError(String message)
            {
                hideLoading();
                // showToast(AlbumDetailActivity.this, message, R.color.red);

            }
        });
    }

    private void setRelatedPhotosData(PhotosByIdResponse data)
    {
        //recycler
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        recyclerView.setLayoutManager(layoutManager);

        titleAlbum.setText(data.getTitle() + "");
        tvCaption.setText(data.getCaption() + "");
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
                position = i;
            }

        }
        list = new ArrayList<>();
        list.addAll(data.getContent());
        adapter = new AlbumDetailsItemAdapter(this, data.getContent(), this);
        recyclerView.setAdapter(adapter);

        if (clickPosition != null)
        {
            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    recyclerView.scrollTo(clickPositionX, clickPositionY);
                }
            }, 1000);


        }
    }

    private void requestGetRelatedVideos(int idVideoCategory)
    {
        CategoryByIdVideosRequest request = new CategoryByIdVideosRequest();

        SingletonService.getInstance().categoryByIdVideosService().categoryByIdPhotosService2(idVideoCategory, request, new OnServiceStatus<WebServiceClass<CategoryByIdVideosResponse>>()
                //  SingletonService.getInstance().categoryByIdVideosService().categoryByIdPhotosService(idVideoCategory, request, new OnServiceStatus<WebServiceClass<CategoryByIdVideosResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<CategoryByIdVideosResponse> response)
            {
                try
                {
                    hideLoading();

                    if (response.info.statusCode == 200)
                    {

                        setRelatedVideoData(response.data);

                    }
                    else
                    {
                        //  showToast(AlbumDetailActivity.this, response.info.message, R.color.red);
                    }
                } catch (Exception e)
                {
                    //  showToast(AlbumDetailActivity.this, e.getMessage(), R.color.red);
                    Logger.e("-onReady-", "Error: " + e.getMessage());

                }
            }

            @Override
            public void onError(String message)
            {
                hideLoading();
                // showToast(AlbumDetailActivity.this, message, R.color.red);
                if (!Tools.isNetworkAvailable(AlbumDetailActivity.this))
                {
                    Logger.e("-OnError-", "Error: " + message);
                    showError(AlbumDetailActivity.this, "خطا در دریافت اطلاعات از سرور!");
                }
                else
                {
                    // showError(AlbumDetailActivity.this,String.valueOf(R.string.networkErrorMessage));

                    showAlert(AlbumDetailActivity.this, R.string.networkErrorMessage, R.string.networkError);
                }
            }
        });
    }

    private void setRelatedVideoData(CategoryByIdVideosResponse data)
    {
        if (data.getResults().isEmpty())
        {
            bRelatedAlbums.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        }
        else
        {
            bRelatedAlbums.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
            bRelatedAlbums.setAdapter(new NewestPhotosAdapter(data.getResults(), this::OnItemRelatedAlbumsClick));
            indicator.attachToRecyclerView(bRelatedAlbums.getmRecyclerView());


            bRelatedAlbums.setAutoPlaying(true);
    /*        if (bNewestVideoPosition!=null)
            {
                try
                {
                    bRelatedAlbums.getmRecyclerView().scrollToPosition(bNewestVideoPosition);

                } catch (Exception e)
                {
                    bRelatedAlbums.getmRecyclerView().scrollToPosition(0);

                }
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

    public void animateHeart(final ImageView view)
    {
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

    private Animation prepareAnimation(Animation animation)
    {
        animation.setRepeatCount(1);
        animation.setRepeatMode(Animation.REVERSE);
        return animation;
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
                ivBigLike.setVisibility(View.VISIBLE);
                requestLike();
                break;
            case R.id.ivPhoto:
                v.setAlpha((float) 1.0);
                if (!isMoving)
                {
                    long clickTime = System.currentTimeMillis();
                    if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA)
                    {
                        doubleClick = true;
                        System.out.println("-----------doubleClick");
                        lastClickTime = 0;
                        ivBigLike.setVisibility(View.VISIBLE);
                        requestLike();


                    }
                    else
                    {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                if (!doubleClick)
                                {
                                    System.out.println("--------------singleClick");
                                    Intent intent = new Intent(AlbumDetailActivity.this, ShowBigPhotoActivity.class);
                                    if (largeImageClick.equals(""))
                                    {
                                        largeImageClick = coverImg;
                                    }

                                    intent.putExtra("pic", position);
                                    intent.putExtra("content", new Gson().toJson(list));
                                    startActivityForResult(intent, 100);


                                }
                                else
                                {
                                    doubleClick = false;
                                }
                            }
                        }, 350);
                    }
                    lastClickTime = clickTime;
                }

                break;
        }
    }

    private void requestLike()
    {
        //showLoading();

        LikeVideoRequest request = new LikeVideoRequest();

        SingletonService.getInstance().getLikeVideoService().likePhotoService(idPhoto, request, new OnServiceStatus<WebServiceClass<LikeVideoResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<LikeVideoResponse> response)
            {
                // rlLike.setClickable(true);
                // hideLoading();

                try
                {

                    if (response.info.statusCode == 200)
                    {
                        animateHeart(ivBigLike);

                        setLiked(response.data);

                    }
                    else
                    {
                        showToast(AlbumDetailActivity.this, response.info.message, R.color.red);
                    }
                } catch (Exception e)
                {
                    showToast(AlbumDetailActivity.this, e.getMessage(), R.color.red);

                }
            }

            @Override
            public void onError(String message)
            {
                // hideLoading();
                //  showToast(AlbumDetailActivity.this, message, R.color.red);
                if (Tools.isNetworkAvailable(AlbumDetailActivity.this))
                {
                    Logger.e("-OnError-", "Error: " + message);
                    showError(AlbumDetailActivity.this, "خطا در دریافت اطلاعات از سرور!");
                }
                else
                {
                    // showError(AlbumDetailActivity.this,String.valueOf(R.string.networkErrorMessage));

                    showAlert(AlbumDetailActivity.this, R.string.networkErrorMessage, R.string.networkError);
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


        }
        else
        {
            imgLike.setColorFilter(getResources().getColor(R.color.white));
            tvLike.setTextColor(getResources().getColor(R.color.white));
            if (likeCount > 0)
            {
                likeCount = likeCount - 1;
            }
            tvLike.setText(likeCount + "");

        }

        list.get(position).setIsLiked(data.getIsLiked());
        list.get(position).setLikes(likeCount);

        //tvLike.setText();

    }


    @Override
    public void OnItemAllMenuClick(View view, Integer id, Content content, Integer position)
    {
        // titleAlbum.setText(content.getTitle() +


        /// clickPositionX=layoutManager.findFirstVisibleItemPosition();;


        clickPosition = position;
        try
        {
            this.position = position;
            updateContentPhotoItem(id);

        } catch (Exception e)
        {
            e.getMessage();
        }


    }

    private void updateContentPhotoItem(Integer id)
    {
        showLoading();
        BookMarkPhotoRequest request = new BookMarkPhotoRequest();

        SingletonService.getInstance().getLikeVideoService().getPhotoDetailService(id, request, new OnServiceStatus<WebServiceClass<Content>>()
        {
            @Override
            public void onReady(WebServiceClass<Content> content)
            {
                try
                {
                    hideLoading();

                    if (content.info.statusCode == 200)
                    {

                        hideLoading();
                        tvCaption.setText(content.data.getCaption() + " " + content.data.getTitle());
                        idPhoto = content.data.getId();
                        likeCount = content.data.getLikes();
                        isBookmark = content.data.getIsBookmarked();
                        isLike = content.data.getIsLiked();
                        if (isLike)
                        {
                            imgLike.setColorFilter(getResources().getColor(R.color.backgroundButton));
                            tvLike.setTextColor(getResources().getColor(R.color.backgroundButton));
                            tvLike.setText(likeCount + "");

                        }
                        else
                        {
                            imgLike.setColorFilter(getResources().getColor(R.color.white));
                            tvLike.setTextColor(getResources().getColor(R.color.white));
                            tvLike.setText(likeCount + "");
                        }
                        tvLike.setText(likeCount + "");
                        if (content.data.getImageName().getThumbnailLarge() == "")
                        {
                            largeImageClick = content.data.getCover();
                        }
                        else
                        {
                            largeImageClick = content.data.getImageName().getThumbnailLarge();
                        }

                        setImageBackground(ivPhoto, largeImageClick.replace("\\", ""));
                    }
                    else
                    {
                    }
                } catch (Exception e)
                {
                    Logger.e("-onReady-", "Error: " + e.getMessage());

                }
            }

            @Override
            public void onError(String message)
            {
                hideLoading();
                if (!Tools.isNetworkAvailable(AlbumDetailActivity.this))
                {
                    Logger.e("-OnError-", "Error: " + message);
                    showError(AlbumDetailActivity.this, "خطا در دریافت اطلاعات از سرور!");
                }
                else
                {

                    showAlert(AlbumDetailActivity.this, R.string.networkErrorMessage, R.string.networkError);
                }
            }
        });


    }

    @Override
    public void OnItemRelatedAlbumsClick(View view, Category content)
    {
        try
        {
            idAlbum = content.getId();
            sendRequestListPhotos(idAlbum);

          /*  titleAlbum.setText(content.getTitle() + "");

            tvCaption.setText(content.getCaption() + "");

            likeCount = content.getLikes();
            isBookmark = content.getIsBookmarked();
            isLike = content.getIsLiked();

            tvLike.setText(likeCount + "");
            // if (content.getImageName().getThumbnailLarge() == null)
            largeImageClick = content.getCover();
            // else
            //      largeImageClick = content.getImageName().getThumbnailLarge();

            setImageBackground(ivPhoto, largeImageClick.replace("\\", ""));*/
        } catch (Exception e)
        {
            e.getMessage();
        }
    }
}
