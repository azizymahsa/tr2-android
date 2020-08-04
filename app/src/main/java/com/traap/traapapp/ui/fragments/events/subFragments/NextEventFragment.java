package com.traap.traapapp.ui.fragments.events.subFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.event.GetAllEventResponse;
import com.traap.traapapp.apiServices.model.event.getEventByid.GetEventByIdResponse;
import com.traap.traapapp.apiServices.model.event.getWorkshopById.GetWorkShopByIdResponse;
import com.traap.traapapp.apiServices.model.event.participant.ParticipantEventIdResponse;
import com.traap.traapapp.ui.activities.main.MainActivity;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.about.AboutFragment;
import com.traap.traapapp.ui.fragments.events.EventDetailFragment;
import com.traap.traapapp.ui.fragments.events.adapter.NextEventAdapter;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.Tools;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

/**
 * Authors:
 * Reza Nejati <reza.n.j.t.i@gmail.com>
 * Copyright © 2017
 */
public class NextEventFragment extends BaseFragment implements View.OnClickListener, NextEventAdapter.NextEventAdapterEvent
{
    private View view;
    private MainActionView mainView;
    private RecyclerView rvEvents;


    public static NextEventFragment newInstance(MainActionView mainView)
    {
        NextEventFragment f = new NextEventFragment();
        f.setMainView(mainView);
        return f;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    public NextEventFragment()
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
        view = inflater.inflate(R.layout.fragment_next_event, container, false);
        initViews();
        getAllEvents(this);
        return view;
    }

    private void getAllEvents(NextEventFragment nextEventFragment)
    {
        mainView.showLoading();

        SingletonService.getInstance().AllEventsService().getAllEvents(new OnServiceStatus<WebServiceClass<GetAllEventResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<GetAllEventResponse> response)
            {

                try
                {
                    mainView.hideLoading();

                    if (response.info.statusCode == 200)
                    {
                        rvEvents.setAdapter(new NextEventAdapter(getActivity(), nextEventFragment, response.data.getResults()));

                       // showAlertSuccess(getContext(), response.info.message, "", false);
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

    private void initViews()
    {
        rvEvents = view.findViewById(R.id.rvEvents);

    }

    @Override
    public void onClick(View v)
    {

    }

    @Override
    public void onNextEventClick(Integer id,String title,String desc)
    {
        ((MainActivity) getActivity()).setFragment(EventDetailFragment.newInstance(mainView,id,title,desc));
        ((MainActivity) getActivity()).replaceFragment(((MainActivity) getActivity()).getFragment(), "EventDetailFragment");
       // getWorkshopsById(id);
       // getParticipantRetrieve(id);


    }





    private void getParticipantRetrieve(Integer idEvent)
    {
        mainView.showLoading();

        SingletonService.getInstance().AllEventsService().getParticipantRetrieve(idEvent,new OnServiceStatus<WebServiceClass<ParticipantEventIdResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<ParticipantEventIdResponse> response)
            {

                try
                {
                    mainView.hideLoading();

                    if (response.info.statusCode == 200)
                    {

                        //showAlertSuccess(getContext(), response.info.message, "", false);
                        //((MainActivity) getActivity()).setFragment(EventDetailFragment.newInstance(mainView));
                      //  ((MainActivity) getActivity()).replaceFragment(((MainActivity) getActivity()).getFragment(), "EventDetailFragment");
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
