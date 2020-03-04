package com.traap.traapapp.ui.fragments.moneyTransfer;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.traap.traapapp.R;
import com.traap.traapapp.ui.adapters.monyTransfer.AddCartAdapter;
import com.traap.traapapp.ui.adapters.monyTransfer.AddCartItem;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Authors:
 * Reza Nejati <reza.n.j.t.i@gmail.com>
 * Copyright © 2017
 */
public class OriginCardFragment extends BaseFragment {
    private View v;

    private MainActionView mainView;
    private FragmentTransfer fragmentTransfer;

    public OriginCardFragment() {
    }

    public static OriginCardFragment newInstance(MainActionView mainView, FragmentTransfer fragmentTransfer) {
        OriginCardFragment f = new OriginCardFragment();
        f.setMainView(mainView);
        f.setFragmentTransfer(fragmentTransfer);
        return f;
    }

    private void setFragmentTransfer(FragmentTransfer fragmentTransfer) {
        this.fragmentTransfer = fragmentTransfer;
    }

    private void setMainView(MainActionView mainView) {
        this.mainView = mainView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        v = inflater.inflate(R.layout.fragment_origin_card, container, false);

        initView();

        // Initializing list view with the custom adapter
        ArrayList<AddCartItem> itemList = new ArrayList<AddCartItem>();

        AddCartAdapter itemArrayAdapter = new AddCartAdapter( itemList);
        RecyclerView  recyclerView = (RecyclerView) v.findViewById(R.id.rcAddCartList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(itemArrayAdapter);

        // Populating list items
        for(int i=0; i<4; i++) {
            itemList.add(new AddCartItem("Item " + i,"مهسا عزیزی","1234_5666_4656_1222"));
        }

        return v;
    }

    private void initView() {
    }

    @Override
    public void onResume() {
        super.onResume();
        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    FragmentManager fm = getFragmentManager();
                    if (fm.getBackStackEntryCount() > 0) {
                        fm.popBackStack();
                    }


                    return true;
                }
                return false;
            }
        });
    }
}
