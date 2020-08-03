package com.traap.traapapp.ui.fragments.events;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.pixplicity.easyprefs.library.Prefs;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.billPayment.BillPaymentResponse;
import com.traap.traapapp.apiServices.model.event.payment.EventParticipant;
import com.traap.traapapp.apiServices.model.event.payment.RequestEventPay;
import com.traap.traapapp.apiServices.model.payBillCar.RequestPayBillCar;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.about.AboutFragment;
import com.traap.traapapp.ui.fragments.events.adapter.RegisterEventAdapter;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.Tools;
import com.traap.traapapp.utilities.Utility;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

import static com.traap.traapapp.conf.TrapConfig.PAYMENT_STATUS_EVENT;

/**
 * Authors:
 * Reza Nejati <reza.n.j.t.i@gmail.com>
 * Copyright © 2017
 */
public class RegisterEventFragment extends BaseFragment implements View.OnClickListener, RegisterEventAdapter.RegisterEventsAdapter
{
    private View view;
    private MainActionView mainView;
    private Integer count;
    private Toolbar mToolbar;
    private View imgBack, imgMenu, rlShirt;
    private TextView tvUserName, tvPopularPlayer;
    private RecyclerView rvRegister;
    private ArrayList<PersonEvent> personEvents = new ArrayList<>();
    private RegisterEventAdapter registerEventAdapter;
    private CircularProgressButton btnBuy;
    private Object PersonEvent;

    public static RegisterEventFragment newInstance(MainActionView mainView, Integer count, ArrayList<PersonEvent> personEvents)
    {
        RegisterEventFragment f = new RegisterEventFragment();
        f.setMainView(mainView, personEvents);
        f.setCount(count);
        return f;
    }

    private void setMainView(MainActionView mainView, ArrayList<PersonEvent> personEvents)
    {
        this.mainView = mainView;
        this.personEvents = personEvents;
    }

    public void setCount(Integer count)
    {
        this.count = count;
    }

    public RegisterEventFragment()
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
        view = inflater.inflate(R.layout.fragment_register_event, container, false);
        initViews();
        return view;
    }

    private void initViews()
    {
        mToolbar = view.findViewById(R.id.toolbar);
        tvUserName = mToolbar.findViewById(R.id.tvUserName);
        rvRegister = view.findViewById(R.id.rvRegister);
        registerEventAdapter = new RegisterEventAdapter(getActivity(), count, personEvents, this, false);
        rvRegister.setAdapter(registerEventAdapter);
        TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvTitle.setText("رویدادها");
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
        ViewCompat.setNestedScrollingEnabled(rvRegister, true);
        btnBuy = view.findViewById(R.id.btnBuy);
        btnBuy.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {

            case R.id.btnBuy:
                mainView.showLoading();
                registerEventAdapter = new RegisterEventAdapter(getActivity(), count, personEvents, this, true);
                rvRegister.setAdapter(registerEventAdapter);
                registerEventAdapter.notifyDataSetChanged();
                mainView.hideLoading();

                requestEventPayment(personEvents);

                break;
        }
    }

    private void requestEventPayment(ArrayList<com.traap.traapapp.ui.fragments.events.PersonEvent> personEvents)
    {

        mainView.showLoading();
        List<EventParticipant> eventParticipants = new ArrayList<>();
        for (int i = 0; i < personEvents.size(); i++)
        {
            EventParticipant eventParticipant = new EventParticipant();
            eventParticipant.setEmail(personEvents.get(i).getEmail());
            eventParticipant.setFirstName(personEvents.get(i).getFirst_name());
            eventParticipant.setLastName(personEvents.get(i).getLast_name());
            eventParticipant.setMobile(personEvents.get(i).getMobile());
            eventParticipant.setNationalCode(personEvents.get(i).getNational_code());
            eventParticipant.setWorkshop(personEvents.get(i).getWorkshopId());
            eventParticipants.add(eventParticipant);
        }
        RequestEventPay requestEventPay = new RequestEventPay();
        requestEventPay.setEventParticipants(eventParticipants);
        SingletonService.getInstance().AllEventsService().postWorkshopPay(requestEventPay, new OnServiceStatus<WebServiceClass<BillPaymentResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<BillPaymentResponse> response)
            {

                try
                {
                    mainView.hideLoading();

                    if (response.info.statusCode == 200)
                    {
                        //
                        if (response.data.getUrl() != null)
                        {
                            if (response.data.getUrl().length() > 0)
                            {
                                onPaymentEventSuccess(response.data, getSum(personEvents));

                            }
                        } else if (response.data.getMessage() != null)
                        {

                                showAlertSuccess(getContext(), response.data.getMessage(), "", true);
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

    //bayad az detail dorost beshe gheymat har workshop ro az id workshop gereft
    private Integer getSum(ArrayList<com.traap.traapapp.ui.fragments.events.PersonEvent> personEvents)
    {
        Integer sum = 0;
        for (int i = 0; i < personEvents.size(); i++)
        {
            sum = sum + ((personEvents.get(i).getPrice().equals("")) ? 0 : Integer.parseInt(personEvents.get(i).getPrice()));// Integer.parseInt(personEvents.get(i).getPrice());
        }
        return sum;
    }

    private void onPaymentEventSuccess(BillPaymentResponse data, Integer totalAmount)
    {

        String textBillPayment = "با انجام این پرداخت، مبلغ " + Utility.priceFormat(totalAmount + "") + " ریال بابت ' خلافی موتور ' " //+ termText + typeBill + numberText
                + "، از حساب شما کسر خواهد شد.";

        mainView.openEventPaymentFragment(data.getUrl(), textBillPayment, count, personEvents, totalAmount + "", PAYMENT_STATUS_EVENT);

    }

    @Override
    public void onItemClickDelete(Integer position, Integer workshopId, ArrayList<com.traap.traapapp.ui.fragments.events.PersonEvent> personEvents)
    {
        if (count > 1)
        {
            mainView.showLoading();
            PersonEvent obj;
            boolean flagEnd = false;
            Iterator<PersonEvent> iterator = personEvents.iterator();
            while (iterator.hasNext())
            {
                obj = iterator.next();
                if (!flagEnd)
                {
                    if (obj.getWorkshopId() == workshopId)
                    {
                        // Remove the current element from the iterator and the list.
                        iterator.remove();

                        count = count - 1;

                        flagEnd = true;

                    }
                }
            }

            registerEventAdapter = new RegisterEventAdapter(getActivity(), count, personEvents, this, false);
            rvRegister.setAdapter(registerEventAdapter);
            registerEventAdapter.notifyDataSetChanged();
            mainView.hideLoading();
        } else
        {
            showAlertFailure(getContext(), "تعداد شرکت کننده ها نباید کمتر از یک باشد.", "", false);
        }

    }

    @Override
    public void onItemAlertValid(String message, ArrayList<com.traap.traapapp.ui.fragments.events.PersonEvent> personEvents)
    {
        mainView.showError(message);
        this.personEvents = personEvents;

    }
}



