package ir.traap.tractor.android.ui.activities.photo;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moeidbannerlibrary.banner.BannerLayout;
import com.makeramen.roundedimageview.RoundedImageView;
import com.pixplicity.easyprefs.library.Prefs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.generator.SingletonService;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.categoryByIdVideo.CategoryByIdVideosRequest;
import ir.traap.tractor.android.apiServices.model.categoryByIdVideo.CategoryByIdVideosResponse;
import ir.traap.tractor.android.apiServices.model.likeVideo.LikeVideoRequest;
import ir.traap.tractor.android.apiServices.model.likeVideo.LikeVideoResponse;
import ir.traap.tractor.android.apiServices.model.mainVideos.Category;
import ir.traap.tractor.android.conf.TrapConfig;
import ir.traap.tractor.android.ui.adapters.photo.NewestPhotosAdapter;
import ir.traap.tractor.android.ui.base.BaseActivity;
import ir.traap.tractor.android.utilities.Tools;
import ir.traap.tractor.android.utilities.Utility;

public class AlbumDetailActivity extends BaseActivity implements View.OnClickListener
{
    private TextView tvTitle, tvUserName,tvPopularPlayer,tvLike;
    private View imgBack, imgMenu;
    private RoundedImageView ivPhoto;
    private ImageView imgBookmark,imgLike;
    private int positionVideo,idVideoCategory;
    private ArrayList<Category> videosList;
    private Category videoItem;
    private RelativeLayout rlLike;
    private String urlVideo;
    private int idVideo;
    private Integer likeCount=0;
    private BannerLayout bRelatedAlbums;

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
                idVideoCategory=extras.getInt("IdVideoCategory",0);
                idVideo=extras.getInt("IdPhoto",0);
                positionVideo=extras.getInt("positionPhoto",0);
            }
        }

        initView();
    }

    private void initView()
    {
        try
        {
            bRelatedAlbums = findViewById(R.id.bRelatedAlbums);

            tvTitle = findViewById(R.id.tvTitle);
            tvTitle.setText("محتوای یک عکس");

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

        ivPhoto=findViewById(R.id.ivPhoto);
        imgBookmark=findViewById(R.id.imgBookmark);
        imgLike=findViewById(R.id.imgLike);
        tvLike=findViewById(R.id.tvLike);
      //  rlVideo=findViewById(R.id.rlVideo);
        rlLike=findViewById(R.id.rlLike);

        setDataItems();

        requestGetRelatedVideos(idVideoCategory);
        rlLike.setOnClickListener(this);

    }

    private void requestGetRelatedVideos(int idVideoCategory)
    {
        CategoryByIdVideosRequest request = new CategoryByIdVideosRequest();

        SingletonService.getInstance().categoryByIdVideosService().categoryByIdPhotosService(idVideoCategory,request,new    OnServiceStatus<WebServiceClass<CategoryByIdVideosResponse>>()
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

    private void setRelatedVideoData(CategoryByIdVideosResponse data)
    {
        if (!data.getResults().isEmpty())
        {
            bRelatedAlbums.setAdapter(new NewestPhotosAdapter(data.getResults()));
            List<Category> relatedList = data.getResults();

            for (int i = 0; i <relatedList.size(); i++)
            {
               // setImageBackground(ivRelated1, relatedList.get(i).getCover().replace("\\", ""));
               /* setImageBackground(ivRelated2, relatedList.get(1).getCover().replace("\\", ""));
                setImageBackground(ivRelated3, relatedList.get(2).getCover().replace("\\", ""));
                setImageBackground(ivRelated4, relatedList.get(3).getCover().replace("\\", ""));*/
            }


        }
    }

    private void setDataItems()
    {
        videoItem=videosList.get(positionVideo);

       /* urlVideo=videoItem.getFrame().replace("\\", "");
       tvLike.setText(videoItem.getLikes().toString());
        likeCount=videoItem.getLikes();
        if (videoItem.getIsLiked()){
            imgLike.setColorFilter(getResources().getColor(R.color.backgroundButton));
            tvLike.setTextColor(getResources().getColor(R.color.backgroundButton));

        }else {
            imgLike.setColorFilter(getResources().getColor(R.color.gray));
            tvLike.setTextColor(getResources().getColor(R.color.gray));

        }
        if (videoItem.getIsBookmarked()){
            imgBookmark.setColorFilter(getResources().getColor(R.color.backgroundButton));
        }else {
          imgBookmark.setColorFilter(getResources().getColor(R.color.gray));
        }*/
      //  tvDate.setText(videoItem.getCreateDateFormatted()+"");
      //  tvTitleVideo.setText(videoItem.getTitle()+"");
      //  tvDesc.setText(videoItem.getCaption()+"");
        setImageBackground(ivPhoto,videoItem.getCover().replace("\\", ""));
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
        }
        catch (NullPointerException e)
        {
            Picasso.with(this).load(R.drawable.img_failure).into(image);
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
           /* case R.id.rlVideo:
                playVideo(urlVideo);
                break;*/
            case R.id.rlLike:
                imgLike.setColorFilter(getResources().getColor(R.color.backgroundButton));
                tvLike.setTextColor(getResources().getColor(R.color.backgroundButton));
                requestLikeVideo();
                break;
        }
    }

    private void requestLikeVideo()
    {
        //rlLike.setClickable(false);
        LikeVideoRequest request = new LikeVideoRequest();

        SingletonService.getInstance().getLikeVideoService().likePhotoService(idVideo,request,new    OnServiceStatus<WebServiceClass<LikeVideoResponse>>()
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
            likeCount=likeCount+1;
            tvLike.setText(likeCount+"");

        }else {
            imgLike.setColorFilter(getResources().getColor(R.color.gray));
            tvLike.setTextColor(getResources().getColor(R.color.gray));
            likeCount=likeCount-1;
            tvLike.setText(likeCount+"");
        }
        //tvLike.setText();

    }

    private void playVideo(String urlVideo)
    {
        Utility.openUrlCustomTab(this,urlVideo);
    }
}
