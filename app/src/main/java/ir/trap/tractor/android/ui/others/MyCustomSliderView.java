package ir.trap.tractor.android.ui.others;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import ir.trap.tractor.android.R;
import lombok.Getter;
import lombok.Setter;

public class MyCustomSliderView extends BaseSliderView
{
    private TextView tvHeaderText, tvHeaderSubText, tvStadiumName, tvDateTime;
    private ImageView imgGuest, imgHost, imgBackground;
    private RelativeLayout rlRoot;

    @Getter @Setter
    private String colorStadiumName , colorDateTime;

    @Getter @Setter
    @Nullable
    private String imgAwayLink, imgHomeLink, imgBackgroundLink, headerDesc, stadiumName, dateTime;

    private ProgressBar progress;

    public MyCustomSliderView(Context context)
    {
        super(context);
    }

    @Override
    public View getView()
    {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.slider_item_view,null);

        rlRoot = rootView.findViewById(R.id.rlRoot);
        imgBackground = rootView.findViewById(R.id.imgBackground);
        imgHost = rootView.findViewById(R.id.imgHost);
        imgGuest = rootView.findViewById(R.id.imgGuest);
        tvHeaderText = rootView.findViewById(R.id.tvHeaderText);
        tvHeaderSubText = rootView.findViewById(R.id.tvHeaderSubText);
        tvStadiumName = rootView.findViewById(R.id.tvStadiumName);
        tvDateTime = rootView.findViewById(R.id.tvDateTime);
        progress = rootView.findViewById(R.id.progress);

        tvHeaderText.setTypeface(Typeface.SANS_SERIF);
        tvHeaderSubText.setTypeface(Typeface.SANS_SERIF);

        progress.setVisibility(View.VISIBLE);
        bindAndShow();
//        bindEventAndShow(rootView, imgBackground);
        return rootView;
    }

    protected void bindAndShow()
    {
        final BaseSliderView me = this;

        rlRoot.setOnClickListener(v ->
        {
            if(mOnSliderClickListener != null)
            {
                mOnSliderClickListener.onSliderClick(me);
            }
        });

        if (getHeaderDesc() != null)
        {
            if (getHeaderDesc().contains("/"))
            {
                String split[] = getHeaderDesc().split("/");
                tvHeaderText.setText(split[0]);
                tvHeaderSubText.setText("/" + split[1]);
                tvHeaderSubText.setVisibility(View.VISIBLE);
            }
            else
            {
                tvHeaderText.setText(getHeaderDesc());
                tvHeaderSubText.setVisibility(View.GONE);
            }
        }

        if (getStadiumName() != null)
        {
            tvStadiumName.setText(getStadiumName());
        }

        if (getColorStadiumName() != null)
        {
            tvStadiumName.setTextColor(Color.parseColor(getColorStadiumName()));
        }

        if (getDateTime() != null)
        {
            tvDateTime.setText(getDateTime());
        }

        if (getColorDateTime() != null)
        {
            tvDateTime.setTextColor(Color.parseColor(getColorDateTime()));
        }

        if (getImgBackgroundLink() != null)
        {
            setImageBackground(imgBackground, getImgBackgroundLink());
        }

        if (getImgHomeLink() != null)
        {
            setImageBackground(imgHost, getImgHomeLink());
        }

        if (getImgAwayLink() != null)
        {
            setImageBackground(imgGuest, getImgAwayLink());
        }

        progress.setVisibility(View.GONE);
        rlRoot.setVisibility(View.VISIBLE);
    }

    private void setImageBackground(ImageView imageView, String link)
    {
        try
        {
            Picasso.with(mContext).load(Uri.parse(link)).into(imageView, new Callback()
            {
                @Override
                public void onSuccess() { }

                @Override
                public void onError()
                {
                    Picasso.with(mContext).load(R.drawable.img_failure).into(imageView);
                }
            });
        }
        catch (NullPointerException e)
        {
            Picasso.with(mContext).load(R.drawable.img_failure).into(imageView);
        }
    }
}
