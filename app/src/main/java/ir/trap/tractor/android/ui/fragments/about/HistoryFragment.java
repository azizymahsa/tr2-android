package ir.trap.tractor.android.ui.fragments.about;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ir.trap.tractor.android.R;
import ir.trap.tractor.android.apiServices.generator.SingletonService;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.getHistory.PlayersCurrent;
import ir.trap.tractor.android.apiServices.model.getHistory.ResponseHistory;
import ir.trap.tractor.android.ui.adapters.aboutUs.ExpandableListPlayerHistoryAdapter;
import ir.trap.tractor.android.ui.adapters.aboutUs.NoScrollExListView;
import ir.trap.tractor.android.ui.adapters.aboutUs.ExpandableListHistoryAdapter;
import ir.trap.tractor.android.ui.fragments.main.MainActionView;


public class HistoryFragment
        extends Fragment implements View.OnClickListener
{


    private View view;
    private com.daimajia.slider.library.SliderLayout mDemoSlider;
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
                Toast.makeText(getContext(),
                        listDataGroup.get(groupPosition) + " " + "text_collapsed",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // ExpandableListView Group collapsed listener
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener()
        {

            @Override
            public void onGroupCollapse(int groupPosition)
            {
                Toast.makeText(getContext(),
                        listDataGroup.get(groupPosition) + " " + "text_collapsed",
                        Toast.LENGTH_SHORT).show();

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
        expandableListViewAdapter = new ExpandableListHistoryAdapter(getContext(), listDataGroup, listDataChild);
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



        // Adding child data
       /* listDataChild.put(listDataGroup.get(0), alcoholList);
        listDataChild.put(listDataGroup.get(1), coffeeList);
        listDataChild.put(listDataGroup.get(2), pastaList);
        listDataChild.put(listDataGroup.get(3), coldDrinkList);*/

        // notify the adapter
        expandableListViewAdapter.notifyDataSetChanged();
        expandableListViewAdapter2.notifyDataSetChanged();
    }

    private void sendRequest()
    {
        SingletonService.getInstance().getMenuService().getHistory(new OnServiceStatus<WebServiceClass<ResponseHistory>>()
        {
            @Override
            public void onReady(WebServiceClass<ResponseHistory> response)
            {
                try
                {
                    if (response.info.statusCode == 200)
                    {
                        if (response.data.getImages().size() > 0)
                        {
                            // responseHistory=response;
                            setSlider(response);
                            setExpanding1(response);
                            setExpanding2(response);
                        }
                    } else
                    {
                        mainView.showError(response.info.message);
                    }
                } catch (Exception e)
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
        mDemoSlider = view.findViewById(R.id.slider);
        expandableListView = view.findViewById(R.id.expandableListView);
        expandableListView2 = view.findViewById(R.id.expandableListView2);

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
}

