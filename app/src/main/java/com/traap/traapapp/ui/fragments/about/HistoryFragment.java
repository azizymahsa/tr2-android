package com.traap.traapapp.ui.fragments.about;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.pixplicity.easyprefs.library.Prefs;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getHistory.ResponseHistory;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.getHistory.PlayersCurrent;

import com.traap.traapapp.ui.adapters.aboutUs.ExpandableListPlayerHistoryAdapter;
import com.traap.traapapp.ui.adapters.aboutUs.NoScrollExListView;
import com.traap.traapapp.ui.adapters.aboutUs.ExpandableListHistoryAdapter;
import com.traap.traapapp.ui.fragments.main.MainActionView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class HistoryFragment
        extends BaseFragment implements View.OnClickListener
{
    public static HistoryFragment historyFragment;

    private Toolbar mToolbar;
    private TextView tvTitle,tvUserName,tvPopularPlayer;
    private View imgBack,imgMenu;
    private View view;
    private com.daimajia.slider.library.SliderLayout mDemoSlider;
    private com.daimajia.slider.library.SliderLayout mDemoSlider2;
    private MainActionView mainView;

    //Expanding
    private NoScrollExListView expandableListView;
    private NoScrollExListView expandableListView2;

    private ExpandableListHistoryAdapter expandableListViewAdapter;
    private ExpandableListPlayerHistoryAdapter expandableListViewAdapter2;

    private List<String> listDataGroup;
    private List<String> listDataGroup2;

    private HashMap<String, String> listDataChild;
    private HashMap<String, List<PlayersCurrent>> listPlayerCurrent;
    private WebServiceClass<ResponseHistory> responseHistory = new WebServiceClass<>();


    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    public HistoryFragment(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    public static HistoryFragment newInstance(MainActionView mainView)
    {
        HistoryFragment f = new HistoryFragment(mainView);
        Bundle args = new Bundle();
//        args.putParcelableArrayList("chosenServiceList", chosenServiceList);
//        args.putParcelableArrayList("footballServiceList", footballServiceList);

        f.setArguments(args);
        f.setMainView(mainView);
        return f;
    }

    private void setSlider(WebServiceClass<ResponseHistory> response)
    {


        for (int i = 1; i < response.data.getImages().size(); i++)
        {
            if (response.data.getImages().get(i).getRowNumber() == 1)
            {
                ImageSliderView textSliderView = new ImageSliderView(getActivity());

                textSliderView.setImgBackgroundLink(response.data.getImages().get(i).getImageName());
                textSliderView.setText("gone");
                textSliderView.setHeaderWeekNo("gone");
                textSliderView.setCenterView("gone");

                mDemoSlider.addSlider(textSliderView);

            }
        }


//            mDemoSlider.setPresetTransformer(SliderLayout.Transformer.RotateDown);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        PagerIndicator pagerIndicator = new PagerIndicator(getActivity());
        pagerIndicator.setDefaultIndicatorColor(R.color.currentColor, R.color.grayColor);
        mDemoSlider.setCustomIndicator(pagerIndicator);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());

        // mDemoSlider.setCurrentPosition(response.indexOf(matchCurrent));
        mDemoSlider.stopAutoCycle();

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

        EventBus.getDefault().register(this);
    }

    /**
     * method to initialize the listeners
     */
    private void initListeners()
    {

        // ExpandableListView on child click listener
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
        {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id)
            {
               /* Toast.makeText(
                        getContext(),
                        listDataGroup.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataGroup.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();*/
                return false;
            }
        });

        // ExpandableListView Group expanded listener
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener()
        {

            @Override
            public void onGroupExpand(int groupPosition)
            {
               /* Toast.makeText(getContext(),
                        listDataGroup.get(groupPosition) + " " + "text_collapsed",
                        Toast.LENGTH_SHORT).show();*/
            }
        });

        // ExpandableListView Group collapsed listener
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener()
        {

            @Override
            public void onGroupCollapse(int groupPosition)
            {
               /* Toast.makeText(getContext(),
                        listDataGroup.get(groupPosition) + " " + "text_collapsed",
                        Toast.LENGTH_SHORT).show();*/

            }
        });

    }

    /**
     * method to initialize the objects
     *
     * @param
     */
    private void initObjects()
    {

        // initializing the list of groups
        listDataGroup = new ArrayList<>();
        listDataGroup2 = new ArrayList<>();

        // initializing the list of child
        listDataChild = new HashMap<>();
        listPlayerCurrent = new HashMap<String, List<PlayersCurrent>>();

        // initializing the adapter object
        expandableListViewAdapter = new ExpandableListHistoryAdapter(getContext(), listDataGroup, listDataChild,getActivity());
        expandableListView.setAdapter(expandableListViewAdapter);

        expandableListViewAdapter2 = new ExpandableListPlayerHistoryAdapter(getContext(), listDataGroup2, listPlayerCurrent);
        expandableListView2.setAdapter(expandableListViewAdapter2);
    }

    /*
     * Preparing the list data
     *
     * Dummy Items
     */
    private void initListData(WebServiceClass<ResponseHistory> response)
    {


        // Adding group data
        for (int i = 0; i < response.data.getHistory().size(); i++)
        {
            listDataGroup.add(response.data.getHistory().get(i).getTitle());
            listDataChild.put(listDataGroup.get(i), response.data.getHistory().get(i).getBody());
        }

        if (response.data != null)
        {
            listDataGroup2.add("بازیکنان کنونی");
            listDataGroup2.add("بازیکنان برجسته پیشین");
        }
        listPlayerCurrent.put(listDataGroup2.get(0), response.data.getPlayersCurrent());
        listPlayerCurrent.put(listDataGroup2.get(1), response.data.getPlayers());


        // notify the adapter
        expandableListViewAdapter.notifyDataSetChanged();
        expandableListViewAdapter2.notifyDataSetChanged();
    }

    private void sendRequest()
    {
        mainView.showLoading();
        SingletonService.getInstance().getMenuService().getHistory(new OnServiceStatus<WebServiceClass<ResponseHistory>>()
        {
            @Override
            public void onReady(WebServiceClass<ResponseHistory> response)
            {
                try
                {
                    mainView.hideLoading();

                    if (response.info.statusCode == 200)
                    {
                        if (response.data.getImages().size() > 0)
                        {
                            // responseHistory=response;
                            setSlider(response);
                            setExpanding1(response);
                            setExpanding2(response);
                            setSlider2(response);
                        }
                    } else
                    {
                        mainView.hideLoading();
                        mainView.showError(response.info.message);
                    }
                } catch (Exception e)
                {
                    mainView.hideLoading();

                    mainView.showError(e.getMessage());
                }
            }

            @Override
            public void onError(String message)
            {
                mainView.hideLoading();

                mainView.showError(message);

            }
        });

    }

    private void setSlider2(WebServiceClass<ResponseHistory> response)
    {
        for (int i = 1; i < response.data.getImages().size(); i++)
        {
            if (response.data.getImages().get(i).getRowNumber() == 2)
            {
                ImageSliderView textSliderView = new ImageSliderView(getActivity());

                textSliderView.setImgBackgroundLink(response.data.getImages().get(i).getImageName());
                textSliderView.setText("gone");
                textSliderView.setHeaderWeekNo("gone");
                textSliderView.setCenterView("gone");

                mDemoSlider2.addSlider(textSliderView);

            }
        }
//            mDemoSlider2.setPresetTransformer(SliderLayout.Transformer.RotateDown);
        mDemoSlider2.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        PagerIndicator pagerIndicator = new PagerIndicator(getActivity());
        pagerIndicator.setDefaultIndicatorColor(R.color.currentColor, R.color.grayColor);
        mDemoSlider2.setCustomIndicator(pagerIndicator);
        mDemoSlider2.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider2.setDuration(10000);
//            mDemoSlider.addOnPageChangeListener(this);
    }

    private void setExpanding2(WebServiceClass<ResponseHistory> response)
    {
        // initializing the listeners
        initListeners();

        // initializing the objects
        initObjects();

        // preparing list data
        initListData(response);
    }

    private void setExpanding1(WebServiceClass<ResponseHistory> response)
    {
        // initializing the listeners
        initListeners();

        // initializing the objects
        initObjects();

        // preparing list data
        initListData(response);
    }


    private void initViews()
    {
        try
        {
            mDemoSlider = view.findViewById(R.id.slider);
            mDemoSlider2 = view.findViewById(R.id.slider2);
            expandableListView = view.findViewById(R.id.expandableListView);
            expandableListView2 = view.findViewById(R.id.expandableListView2);
            //toolbar
            /*Toolbars*/
            mToolbar = view.findViewById(R.id.toolbar);
            tvUserName = view.findViewById(R.id.tvUserName);

            tvUserName.setText(TrapConfig.HEADER_USER_NAME);

            mToolbar.findViewById(R.id.imgMenu).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mainView.openDrawer();
                }
            });

            tvTitle = view.findViewById(R.id.tvTitle);
            imgMenu=view.findViewById(R.id.imgMenu);

            imgMenu.setOnClickListener(v -> mainView.openDrawer());
            imgBack = view.findViewById(R.id.imgBack);
            imgBack.setOnClickListener(v ->
            {
                getActivity().onBackPressed();
            });

            tvTitle.setText("تاریخچه");
            tvPopularPlayer = mToolbar.findViewById(R.id.tvPopularPlayer);
            tvPopularPlayer.setText(String.valueOf(Prefs.getInt("popularPlayer", 12)));
        } catch (Exception e)
        {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.history_fragment, container, false);
        sendRequest();
        // initializing the views
        initViews();



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

    @Subscribe
    public void getHeaderContent(HeaderModel headerModel)
    {
        if (headerModel.getPopularNo() != 0)
        {
            tvPopularPlayer.setText(String.valueOf(headerModel.getPopularNo()));
        }
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }
}

