package ir.traap.tractor.android.ui.activities.photo;

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

import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.generator.SingletonService;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.categoryByIdVideo.CategoryByIdVideosRequest;
import ir.traap.tractor.android.apiServices.model.categoryByIdVideo.CategoryByIdVideosResponse;
import ir.traap.tractor.android.apiServices.model.likeVideo.LikeVideoRequest;
import ir.traap.tractor.android.apiServices.model.likeVideo.LikeVideoResponse;
import ir.traap.tractor.android.apiServices.model.mainVideos.Category;
import ir.traap.tractor.android.apiServices.model.photo.response.Content;
import ir.traap.tractor.android.apiServices.model.photo.response.PhotosByIdResponse;
import ir.traap.tractor.android.conf.TrapConfig;
import ir.traap.tractor.android.ui.adapters.photo.AlbumDetailsItemAdapter;
import ir.traap.tractor.android.ui.adapters.photo.NewestPhotosAdapter;
import ir.traap.tractor.android.ui.base.BaseActivity;
import ir.traap.tractor.android.utilities.Tools;
import ir.traap.tractor.android.utilities.Utility;

public class ShowBigPhotoActivity extends BaseActivity implements View.OnClickListener, AlbumDetailsItemAdapter.OnItemAllMenuClickListener
{
    private TextView  tvLike;
    private TextView imgBack;
    private RoundedImageView ivPhoto;
    private ImageView imgBookmark, imgLike;

    private RelativeLayout rlLike;

    private int likeCount = 0;

    private Integer idPhoto;
    private String largeImageClick = "";

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
                setImageBackground(ivPhoto, largeImageClick);
                tvLike.setText(likeCount + "");

            }
        }


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

        SingletonService.getInstance().getLikeVideoService().likePhotoService(idPhoto, request, new OnServiceStatus<WebServiceClass<LikeVideoResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<LikeVideoResponse> response)
            {

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
                Tools.showToast(getApplicationContext(), message, R.color.red);

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
            likeCount = likeCount - 1;
            tvLike.setText(likeCount + "");
        }

    }


    @Override
    public void OnItemAllMenuClick(View view, Integer id, Content content)
    {


    }
}
