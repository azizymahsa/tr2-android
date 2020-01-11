package com.traap.traapapp.ui.activities.photo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import it.sephiroth.android.library.widget.HListView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import com.traap.traapapp.utilities.HackyViewPager;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.ScreenShot;
import com.traap.traapapp.utilities.Tools;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ShowBigPhotoActivity extends BaseActivity implements View.OnClickListener, AlbumDetailsItemAdapter.OnItemAllMenuClickListener {
    private TextView tvLike;
    private TextView imgBack;
    private RoundedImageView ivPhoto, ivBigLike;
    private ImageView btnSharePic, imgLike, btnBookmark;

    private RelativeLayout rlLike, rlPic;

    private int likeCount = 0;

    private Boolean isBookmark = false;
    private Integer idPhoto;
    private String largeImageClick = "";
    private boolean isLike = false;

    private static final long DOUBLE_CLICK_TIME_DELTA = 300;
    long lastClickTime = 0;
    private boolean doubleClick = false;
    private boolean isMoving = false;


    HListView thumbnails_scroll_view;
    private HackyViewPager viewPager;
    IntroAdapter introAdapter;
    ImageListAdapter imageListAdapter;
    boolean listChange = true;
    int pos;
    private List<Content> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo);
        Bundle extras = getIntent().getExtras();


        Gson gson = new Gson();

        list = gson.fromJson(extras.getString("content", ""),
                new TypeToken<ArrayList<Content>>() {
                }.getType());
        initView();

     /*   if (savedInstanceState == null) {
            if (extras == null) {

            } else {

                idPhoto = extras.getInt("idPhoto", 0);
                largeImageClick = extras.getString("SRCImage", "");
                likeCount = extras.getInt("LikeCount", 0);
                isBookmark = extras.getBoolean("isBookmark", false);
                isLike = extras.getBoolean("isLike", false);
                setImageBackground(ivPhoto, largeImageClick);
                tvLike.setText(likeCount + "");
                if (isLike) {
                    imgLike.setColorFilter(getResources().getColor(R.color.backgroundButton));
                    tvLike.setTextColor(getResources().getColor(R.color.backgroundButton));
                } else {
                    imgLike.setColorFilter(getResources().getColor(R.color.white));
                    tvLike.setTextColor(getResources().getColor(R.color.white));
                }

            }


        }
        setColorBookmark();
*/

    }

    private void setColorBookmark() {
        if (isBookmark)
            btnBookmark.setColorFilter(getResources().getColor(R.color.backgroundButton));
        else
            btnBookmark.setColorFilter(getResources().getColor(R.color.white));
    }


    private void initView() {
        try {

            imgBack = findViewById(R.id.imgBack);
            imgBack.setOnClickListener(v ->
            {
                finish();
            });


        /*    ivPhoto = findViewById(R.id.ivPhoto);
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

*/
        } catch (Exception e) {
            e.getMessage();
        }


        try {
           pos = getIntent().getExtras().getInt("pic");
        } catch (Exception e) {
        }


        thumbnails_scroll_view = findViewById(R.id.thumbnails_scroll_view);
        viewPager = findViewById(R.id.intro_view_pager);



        introAdapter = new IntroAdapter();
        imageListAdapter = new ImageListAdapter();
        viewPager.setAdapter(new IntroAdapter());
        thumbnails_scroll_view.setAdapter(imageListAdapter);
        // viewPager.setCurrentItem(getIntent().getExtras().getInt("pic"));
        if (pos == 0 || pos == 1) {

            thumbnails_scroll_view.setSelection(pos);


        } else if (pos == list.size()) {
            thumbnails_scroll_view.setSelection(pos);


        } else if (pos == list.size() - 1 || pos == list.size() - 2) {
            thumbnails_scroll_view.setSelection(pos);

        } else {

            thumbnails_scroll_view.setSelection(pos - 1);


        }


        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                listChange = true;

                return false;
            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (listChange) {

                    thumbnails_scroll_view.smoothScrollToPosition(position);

                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(pos);
    }


    private void setImageBackground(ImageView image, String link) {
        try {
            Glide.with(this).load(Uri.parse(link)).into(image);

        } catch (NullPointerException e) {
            Picasso.with(this).load(R.drawable.img_failure).into(image);
        }
    }

    @Override
    public void onClick(View v) {
    /*    switch (v.getId()) {

            case R.id.rlLike:
                // imgLike.setColorFilter(getResources().getColor(R.color.backgroundButton));
                // tvLike.setTextColor(getResources().getColor(R.color.backgroundButton));
                ivBigLike.setVisibility(View.VISIBLE);
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
                if (!isMoving) {
                    long clickTime = System.currentTimeMillis();
                    if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
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
                                if (!doubleClick) {
                                    System.out.println("--------------singleClick");

                                } else
                                    doubleClick = false;
                            }
                        }, 350);
                    }
                    lastClickTime = clickTime;
                }

                break;
        }*/
    }

    private void requestBookMark() {
        BookMarkPhotoRequest request = new BookMarkPhotoRequest();

        SingletonService.getInstance().getLikeVideoService().bookMarkPhotoService(idPhoto, request, new OnServiceStatus<WebServiceClass<BookMarkPhotoResponse>>() {
            @Override
            public void onReady(WebServiceClass<BookMarkPhotoResponse> response) {

                try {

                    if (response.info.statusCode == 200) {

                        setBookMark(response.data);

                    } else {
                        Tools.showToast(getApplicationContext(), response.info.message, R.color.red);
                    }
                } catch (Exception e) {
                    Tools.showToast(getApplicationContext(), e.getMessage(), R.color.red);

                }
            }

            @Override
            public void onError(String message) {
                // Tools.showToast(getApplicationContext(), message, R.color.red);
                if (!Tools.isNetworkAvailable(ShowBigPhotoActivity.this)) {
                    Logger.e("-OnError-", "Error: " + message);
                    showError(getApplicationContext(), "خطا در دریافت اطلاعات از سرور!");
                } else {
                    // showError(getApplicationContext(),String.valueOf(R.string.networkErrorMessage));

                    showAlert(getApplicationContext(), R.string.networkErrorMessage, R.string.networkError);
                }
            }
        });
    }

    private void setBookMark(BookMarkPhotoResponse data) {
        if (data.getBookMarked()) {
            btnBookmark.setColorFilter(getResources().getColor(R.color.backgroundButton));


        } else {
            btnBookmark.setColorFilter(getResources().getColor(R.color.white));

        }
    }

    private void requestLike() {
        LikeVideoRequest request = new LikeVideoRequest();

        SingletonService.getInstance().getLikeVideoService().likePhotoService(idPhoto, request, new OnServiceStatus<WebServiceClass<LikeVideoResponse>>() {
            @Override
            public void onReady(WebServiceClass<LikeVideoResponse> response) {

                try {

                    if (response.info.statusCode == 200) {
                        animateHeart(ivBigLike);
                        setLiked(response.data);

                    } else {
                        Tools.showToast(getApplicationContext(), response.info.message, R.color.red);
                    }
                } catch (Exception e) {
                    Tools.showToast(getApplicationContext(), e.getMessage(), R.color.red);

                }
            }

            @Override
            public void onError(String message) {
                if (!Tools.isNetworkAvailable(ShowBigPhotoActivity.this)) {
                    Logger.e("-OnError-", "Error: " + message);
                    showError(getApplicationContext(), "خطا در دریافت اطلاعات از سرور!");
                } else {
                    // showError(getApplicationContext(),String.valueOf(R.string.networkErrorMessage));

                    showAlert(getApplicationContext(), R.string.networkErrorMessage, R.string.networkError);
                }
            }
        });
    }

    private void setLiked(LikeVideoResponse data) {
        if (data.getIsLiked()) {
            imgLike.setColorFilter(getResources().getColor(R.color.backgroundButton));
            tvLike.setTextColor(getResources().getColor(R.color.backgroundButton));
            likeCount = likeCount + 1;
            tvLike.setText(likeCount + "");

        } else {
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

    private Animation prepareAnimation(Animation animation) {
        animation.setRepeatCount(1);
        animation.setRepeatMode(Animation.REVERSE);
        return animation;
    }

    @Override
    public void OnItemAllMenuClick(View view, Integer id, Content content,Integer position) {



    }


    private class IntroAdapter extends PagerAdapter {
        public IntroAdapter() {

        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = View.inflate(container.getContext(), R.layout.list_image_item_gallary, null);
            PhotoView image = view.findViewById(R.id.photo_view);
            final int[] likeCount = {0};



            ImageView btnSharePic = view.findViewById(R.id.btnSharePics);
            ImageView imgLike = view.findViewById(R.id.imgLikes);
            ImageView btnBookmark = view.findViewById(R.id.btnBookmarks);
            RoundedImageView ivBigLike = view.findViewById(R.id.ivBigLikes);
            TextView tvLike = view.findViewById(R.id.tvLikes);
            RelativeLayout rlPic = view.findViewById(R.id.rlPic);

            tvLike.setText(list.get(position).getLikes()+"");


            if (list.get(position).getIsBookmarked()){
                btnBookmark.setColorFilter(getResources().getColor(R.color.backgroundButton));

            }
            else{
                btnBookmark.setColorFilter(getResources().getColor(R.color.white));

            }

            if (list.get(position).getIsLiked()) {
                imgLike.setColorFilter(getResources().getColor(R.color.backgroundButton));
                tvLike.setTextColor(getResources().getColor(R.color.backgroundButton));
            } else {
                imgLike.setColorFilter(getResources().getColor(R.color.white));
                tvLike.setTextColor(getResources().getColor(R.color.white));
            }
            likeCount[0] = list.get(position).getLikes();


            imgLike.setOnClickListener(v -> {
                ivBigLike.setVisibility(View.VISIBLE);
                LikeVideoRequest request = new LikeVideoRequest();
                animateHeart(ivBigLike);

                if ( list.get(position).getIsLiked()){
                    imgLike.setColorFilter(getResources().getColor(R.color.gray));
                    tvLike.setTextColor(getResources().getColor(R.color.gray));

                    if (likeCount[0] > 0)
                        likeCount[0] = likeCount[0] - 1;
                    tvLike.setText(likeCount[0] + "");
                    list.get(position).setIsLiked(false);
                    list.get(position).setLikes(likeCount[0]);

                }else{

                    imgLike.setColorFilter(getResources().getColor(R.color.backgroundButton));
                    tvLike.setTextColor(getResources().getColor(R.color.backgroundButton));
                    likeCount[0] = likeCount[0] + 1;
                    tvLike.setText(likeCount[0] + "");
                    list.get(position).setIsLiked(true);

                    list.get(position).setLikes(likeCount[0]);


                }

                SingletonService.getInstance().getLikeVideoService().likePhotoService(list.get(position).getId(), request, new OnServiceStatus<WebServiceClass<LikeVideoResponse>>() {
                    @Override
                    public void onReady(WebServiceClass<LikeVideoResponse> response) {

                        try {

                            if (response.info.statusCode == 200) {

                            } else {
                                Tools.showToast(getApplicationContext(), response.info.message, R.color.red);
                            }
                        } catch (Exception e) {
                            Tools.showToast(getApplicationContext(), e.getMessage(), R.color.red);

                        }
                    }

                    @Override
                    public void onError(String message) {
                        if (!Tools.isNetworkAvailable(ShowBigPhotoActivity.this)) {
                            Logger.e("-OnError-", "Error: " + message);
                            showError(getApplicationContext(), "خطا در دریافت اطلاعات از سرور!");
                        } else {
                            // showError(getApplicationContext(),String.valueOf(R.string.networkErrorMessage));

                            showAlert(getApplicationContext(), R.string.networkErrorMessage, R.string.networkError);
                        }
                    }
                });
            });


            btnBookmark.setOnClickListener(v -> {


                if (list.get(position).getIsBookmarked()){
                    btnBookmark.setColorFilter(getResources().getColor(R.color.white));

                    list.get(position).setIsBookmarked(false);

                }
                else{
                    btnBookmark.setColorFilter(getResources().getColor(R.color.backgroundButton));

                    list.get(position).setIsBookmarked(true);

                }

                BookMarkPhotoRequest request = new BookMarkPhotoRequest();

                SingletonService.getInstance().getLikeVideoService().bookMarkPhotoService(list.get(position).getId(), request, new OnServiceStatus<WebServiceClass<BookMarkPhotoResponse>>() {
                    @Override
                    public void onReady(WebServiceClass<BookMarkPhotoResponse> response) {

                        try {

                            if (response.info.statusCode == 200) {

                                //setBookMark(response.data);

                            } else {
                                Tools.showToast(getApplicationContext(), response.info.message, R.color.red);
                            }
                        } catch (Exception e) {
                            Tools.showToast(getApplicationContext(), e.getMessage(), R.color.red);

                        }
                    }

                    @Override
                    public void onError(String message) {
                        // Tools.showToast(getApplicationContext(), message, R.color.red);
                        if (!Tools.isNetworkAvailable(ShowBigPhotoActivity.this)) {
                            Logger.e("-OnError-", "Error: " + message);
                            showError(getApplicationContext(), "خطا در دریافت اطلاعات از سرور!");
                        } else {
                            // showError(getApplicationContext(),String.valueOf(R.string.networkErrorMessage));

                            showAlert(getApplicationContext(), R.string.networkErrorMessage, R.string.networkError);
                        }
                    }
                });

            });



            btnSharePic.setOnClickListener(v -> {
                new ScreenShot(rlPic, ShowBigPhotoActivity.this);


            });


            container.addView(view, 0);

            Glide.with(ShowBigPhotoActivity.this)
                    .load(list.get(position).getImageName().getThumbnailLarge())
                    //  .centerCrop()
                    // .error(R.drawable.not_found)
                    .into(image);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


    public class ImageListAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private ViewHolder holder;


        public ImageListAdapter() {
            inflater = LayoutInflater.from(ShowBigPhotoActivity.this);

        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.row_item_image, null);
                holder = new ViewHolder();
                holder.ivImage = convertView.findViewById(R.id.ivImage);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Glide.with(ShowBigPhotoActivity.this)
                    .load(list.get(position).getImageName().getThumbnailLarge())
                    //  .centerCrop()
                    // .error(R.drawable.not_found)
                    .into(holder.ivImage);

            holder.ivImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listChange = false;
                    viewPager.setCurrentItem(position);
                }
            });

            return convertView;
        }


        public class ViewHolder {
            ImageView ivImage;

        }
    }


}
