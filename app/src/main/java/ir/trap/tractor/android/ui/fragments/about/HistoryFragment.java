package ir.trap.tractor.android.ui.fragments.about;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import ir.trap.tractor.android.R;
import ir.trap.tractor.android.apiServices.generator.SingletonService;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.getHistory.ResponseHistory;
import ir.trap.tractor.android.ui.fragments.main.MainActionView;

public class HistoryFragment
        extends Fragment implements View.OnClickListener
{


    private View view;
    private SliderLayout mDemoSlider;
    private MainActionView mainView;


    public static HistoryFragment newInstance(MainActionView mainView)
    {
        HistoryFragment f = new HistoryFragment();
        f.setMainView(mainView);
        return f;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }
    public HistoryFragment()
    {
    }


    public static HistoryFragment newInstance()
    {
        HistoryFragment fragment = new HistoryFragment();


        return fragment;
    }

    private void setSlider(WebServiceClass<ResponseHistory> response)
    {
        for (int i = 1; i < response.data.getImages().size(); i++)
        {//HeaderWeekNo,CenterView,Text;
            ImageSliderView textSliderView = new ImageSliderView(getActivity());
//            textSliderView.setStadiumName("ورزشگاه یادگار امام تبریز");
//            textSliderView.setDateTime("یکشنبه 12 آبان 1398 - 18:00");
//            textSliderView.setColorDateTime("#000");
//            textSliderView.setColorStadiumName("#aaa");
            //  textSliderView.setHeaderText(String.valueOf(i));
            textSliderView.setImgBackgroundLink(response.data.getImages().get(i).getImageName());
            textSliderView.setText("gone");
            textSliderView.setHeaderWeekNo("gone");
            textSliderView.setCenterView("gone");
//            textSliderView.setImgGuestLink();;
//            textSliderView.setImgHostLink();;
            //  textSliderView.setOnSliderClickListener(getContext());

            mDemoSlider.addSlider(textSliderView);


        }
//            mDemoSlider.setPresetTransformer(SliderLayout.Transformer.RotateDown);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        PagerIndicator pagerIndicator = new PagerIndicator(getActivity());
        pagerIndicator.setDefaultIndicatorColor(R.color.currentColor, R.color.grayColor);
        mDemoSlider.setCustomIndicator(pagerIndicator);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(10000);
//            mDemoSlider.addOnPageChangeListener(this);
    }

    private void setImageColor(ImageView imageView, String link)
    {
        try
        {
            Picasso.with(getContext()).load(Uri.parse(link)).centerCrop().resize(imageView.getMeasuredWidth(), imageView.getMeasuredHeight()).into(imageView, new Callback()
            {
                @Override
                public void onSuccess()
                {
                }

                @Override
                public void onError()
                {
                    Picasso.with(getContext()).load(R.drawable.img_failure).into(imageView);
                }
            });
        } catch (NullPointerException e)
        {
            Picasso.with(getContext()).load(R.drawable.img_failure).into(imageView);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        SingletonService.getInstance().getMenuService().getHistory(new OnServiceStatus<WebServiceClass<ResponseHistory>>()
        {
            @Override
            public void onReady(WebServiceClass<ResponseHistory> response)
            {
                try
                {
                    if (response.info.statusCode == 200){
                        if (response.data.getImages().size() > 0)
                            setSlider(response);
                    }else{
                        mainView.showError(response.info.message);
                    }
                }catch (Exception e)
                {
                    mainView.showError(e.getMessage());
                }
            }

            @Override
            public void onError(String message)
            {
                mainView.showError(message);

            }
        });
    }


    private void initView()
    {
        mDemoSlider = view.findViewById(R.id.slider);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.history_fragment, container, false);
        initView();

        return view;
    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }

    @Override
    public void onClick(View view)
    {
        /*switch (view.getId())
        {
            case R.id.btnPaymentConfirm:

                break;

        }*/
    }
}

