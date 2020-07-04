package com.traap.traapapp.ui.fragments.events;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.makeramen.roundedimageview.RoundedImageView;
import com.pixplicity.easyprefs.library.Prefs;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.event.getEventByid.GetEventByIdResponse;
import com.traap.traapapp.apiServices.model.event.getWorkshopById.GetWorkShopByIdResponse;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.main.MainActivity;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.Introducing_the_team.adapter.FragmentsPagerAdapter;
import com.traap.traapapp.ui.fragments.about.AboutFragment;
import com.traap.traapapp.ui.fragments.events.adapter.DetailEventAdapter;
import com.traap.traapapp.ui.fragments.events.subFragments.MyEventsFragment;
import com.traap.traapapp.ui.fragments.events.subFragments.NextEventFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.CustomViewPager;
import com.traap.traapapp.utilities.Tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

import static com.traap.traapapp.utilities.Utility.changeFontInViewGroup;

/**
 * Authors:
 * Reza Nejati <reza.n.j.t.i@gmail.com>
 * Copyright © 2017
 */
public class EventDetailFragment extends BaseFragment implements View.OnClickListener, DetailEventAdapter.DetailEventAdapterEvents
{
    private View view;
    private MainActionView mainView;
    private Toolbar mToolbar;
    private View imgBack, imgMenu, rlShirt;
    private TextView tvUserName, tvPopularPlayer;
    private RecyclerView rvDetail;
    private CircularProgressButton btnConfirm;
    private Integer totalCount=0;
    private HashMap<Integer,Integer> counts= new HashMap<>();
    private ArrayList<String> countPerson= new ArrayList<>();
    private int eventId=0;
    public TextView txtTeacher,txtTitle,txtDate;
    private RoundedImageView imgBackground;

    public static EventDetailFragment newInstance(MainActionView mainView,int eventId)
    {
        EventDetailFragment f = new EventDetailFragment();
        f.setMainView(mainView,eventId);
        return f;
    }

    private void setMainView(MainActionView mainView,int eventId)
    {
        this.mainView = mainView;
        this.eventId = eventId;
    }

    public EventDetailFragment()
    {
    }


    public static AboutFragment newInstance()
    {
        AboutFragment fragment = new AboutFragment();


        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_event_detail, container, false);
        initViews();

        return view;
    }
    private void initViews()
    {
        mToolbar = view.findViewById(R.id.toolbar);
        tvUserName = mToolbar.findViewById(R.id.tvUserName);

        txtTeacher = view.findViewById(R.id.txtTeacher);
        txtTitle = view.findViewById(R.id.txtTitle);
        txtDate = view.findViewById(R.id.txtDate);
        imgBackground = view.findViewById(R.id.imgBackground);

        rvDetail = view.findViewById(R.id.rvDetail);
        btnConfirm = view.findViewById(R.id.btnConfirm);
        countPerson.add("1");
        countPerson.add("2");
        countPerson.add("3");
        countPerson.add("4");
        counts.put(0,0);
        counts.put(1,0);


        TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvTitle.setText("جزئیات رویداد");
        FrameLayout flLogoToolbar = mToolbar.findViewById(R.id.flLogoToolbar);
        flLogoToolbar.setOnClickListener(v ->
        {
            mainView.backToMainFragment();

        });

        mToolbar.findViewById(R.id.imgBack).setOnClickListener(v -> mainView.backToMainFragment());

        tvUserName.setText(TrapConfig.HEADER_USER_NAME);

        mToolbar.findViewById(R.id.imgMenu).setOnClickListener(v -> mainView.openDrawer());

        imgBack = view.findViewById(R.id.imgBack);
        imgBack.setOnClickListener(v ->
        {
            getActivity().onBackPressed();
        });

        tvPopularPlayer = mToolbar.findViewById(R.id.tvPopularPlayer);
        tvPopularPlayer.setText(String.valueOf(Prefs.getInt("popularPlayer", 12)));
        rlShirt = mToolbar.findViewById(R.id.rlShirt);
        rlShirt.setOnClickListener(v -> startActivityForResult(new Intent(SingletonContext.getInstance().getContext(), MyProfileActivity.class), 100));

        btnConfirm.setOnClickListener(v ->
        {
            totalCount=0;
            Iterator myVeryOwnIterator = counts.keySet().iterator();
            while(myVeryOwnIterator.hasNext()) {
                Integer key=(Integer)myVeryOwnIterator.next();
                Integer value= counts.get(key);
                totalCount+=value;


            }
            ((MainActivity)getActivity()).setFragment(RegisterEventFragment.newInstance(mainView,totalCount));
            ((MainActivity)getActivity()).replaceFragment(((MainActivity)getActivity()).getFragment(), "RegisterEventFragment");
        });
        getEventById(eventId);
        getWorkshopsById(eventId,this);
    }
    private void getWorkshopsById(Integer idEvent, DetailEventAdapter.DetailEventAdapterEvents events)
    {
        {
            mainView.showLoading();

            SingletonService.getInstance().AllEventsService().getWorkshopsById(idEvent,new OnServiceStatus<WebServiceClass<GetWorkShopByIdResponse>>()
            {
                @Override
                public void onReady(WebServiceClass<GetWorkShopByIdResponse> response)
                {

                    try
                    {
                        mainView.hideLoading();

                        if (response.info.statusCode == 200)
                        {

                            rvDetail.setAdapter(new DetailEventAdapter(getActivity(),response.data.getResults(),events,countPerson));

                        } else
                        {
                            showAlertFailure(getContext(), response.info.message, "", false);


                        }
                    } catch (Exception e)
                    {
                        mainView.hideLoading();
                        showAlertFailure(getContext(), e.getMessage(), "", false);


                    }


                }

                @Override
                public void onError(String message)
                {

                    mainView.hideLoading();

                    if (Tools.isNetworkAvailable(Objects.requireNonNull(getActivity())))
                    {
                        showAlertFailure(getContext(), message, "", false);


                    } else
                    {
                        showAlertFailure(getContext(), getString(R.string.networkErrorMessage), "", false);


                    }

                }
            });
        }
    }
    private void getEventById(Integer idEvent)
    {
        {
            mainView.showLoading();

            SingletonService.getInstance().AllEventsService().getEventById(idEvent,new OnServiceStatus<WebServiceClass<GetEventByIdResponse>>()
            {

                @Override
                public void onReady(WebServiceClass<GetEventByIdResponse> response)
                {

                    try
                    {
                        mainView.hideLoading();

                        if (response.info.statusCode == 200)
                        {
                            txtTeacher.setText(response.data.getTeacher()+"");
                            txtTitle.setText(" ◄ "+response.data.getTitle()+"");
                            txtDate.setText(response.data.getCreateDate().toString());

                            imgBackground.setImageDrawable(getContext().getResources().getDrawable(R.drawable.test_event_2));
                            try
                            {
                                Picasso.with(getContext()).load(response.data.getImageUrl()).into(imgBackground);

                             //   progressBar.setVisibility(View.GONE);
                            } catch (Exception e1)
                            {
                              //  holder.progressBar.setVisibility(View.GONE);
                                Picasso.with(getContext()).load(R.drawable.ic_logo_red).into(imgBackground);
                            }
                        } else
                        {
                            showAlertFailure(getContext(), response.info.message, "", false);


                        }
                    } catch (Exception e)
                    {
                        mainView.hideLoading();
                        showAlertFailure(getContext(), e.getMessage(), "", false);


                    }


                }

                @Override
                public void onError(String message)
                {

                    mainView.hideLoading();

                    if (Tools.isNetworkAvailable(Objects.requireNonNull(getActivity())))
                    {
                        showAlertFailure(getContext(), message, "", false);


                    } else
                    {
                        showAlertFailure(getContext(), getString(R.string.networkErrorMessage), "", false);


                    }

                }
            });
        }
    }


    @Override
    public void onClick(View v)
    {

    }

    @Override
    public void onItemCountSelected(String count, Integer position)
    {
        Iterator myVeryOwnIterator = counts.keySet().iterator();
        while(myVeryOwnIterator.hasNext()) {
            Integer key=(Integer)myVeryOwnIterator.next();
            Integer value= counts.get(key);
            if (position.equals(key)){
                counts.put(position,Integer.valueOf(count));
            }

        }
    }
}

