package com.traap.traapapp.ui.fragments.headCoach.profileHeadCoach;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import com.traap.traapapp.R;

import com.traap.traapapp.apiServices.model.techs.GetTechsIdResponse;
import com.traap.traapapp.ui.base.BaseFragment;

import com.traap.traapapp.utilities.Utility;

/**
 * Created by MahtabAzizi on 5/26/2020.
 */
public class ProfileHeadCoahFragment extends BaseFragment implements View.OnClickListener
{
    private GetTechsIdResponse headProfileData;
    private View rootView;
    private NestedScrollView nested;
    public static Integer height;
    private TextView tvNameFa,tvNameEn,tvBirthday,tvJoin,tvLeave,tvNumberTshirt
            ,tvStartDate,tvFeet,tvNationalGoals,tvClubGoals,tvEducation,tvDesc,tvLinkAddress;


    public ProfileHeadCoahFragment()
    {

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (rootView != null)
        {
            return rootView;

        }
        rootView = inflater.inflate(R.layout.fragment_profile_head_coach, container, false);
        return rootView;
    }

    public void setData(GetTechsIdResponse headProfileData)
    {

        this.headProfileData=headProfileData;
        tvNameFa.setText(headProfileData.getPersianFirstName()+headProfileData.getPersianLastName());
        tvNameEn.setText(headProfileData.getEnglishFirstName()+headProfileData.getEnglishLastName());
        tvBirthday.setText(headProfileData.getBirthday());
        tvClubGoals.setText(headProfileData.getClubGoals().toString());
        if (headProfileData.getEducation()!=null)
            tvEducation.setText(headProfileData.getEducation());
        if (headProfileData.getFeet()==1){

            tvFeet.setText("راست");

        }else if (headProfileData.getFeet()==2){

            tvFeet.setText("چپ");

        }else if (headProfileData.getFeet()==3){

            tvFeet.setText("دوپا");

        }

        tvJoin.setText(headProfileData.getJoinedDate());
        tvLeave.setText(headProfileData.getLeavedDate());
        tvNationalGoals.setText(headProfileData.getNationalGoals().toString());
        tvNumberTshirt.setText(headProfileData.getNumber().toString());
        tvStartDate.setText("");
        tvDesc.setText(headProfileData.getDescription());
        tvLinkAddress.setText("ادامه مطلب: "+headProfileData.getWiki());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView(){
        nested=rootView.findViewById(R.id.nested);
        tvNameFa=rootView.findViewById(R.id.tvNameFa);
        tvNameEn=rootView.findViewById(R.id.tvNameEn);
        tvBirthday=rootView.findViewById(R.id.tvBirthDay);
        tvJoin=rootView.findViewById(R.id.tvJoin);
        tvLeave=rootView.findViewById(R.id.tvLeave);
        tvNumberTshirt=rootView.findViewById(R.id.tvNumberTshirt);
        tvStartDate=rootView.findViewById(R.id.tvStartDate);
        tvFeet=rootView.findViewById(R.id.tvFeet);
        tvNationalGoals=rootView.findViewById(R.id.tvNationalGoals);
        tvClubGoals=rootView.findViewById(R.id.tvClubGoals);
        tvEducation=rootView.findViewById(R.id.tvEducation);
        tvLinkAddress=rootView.findViewById(R.id.tvLinkAddress);
        tvLinkAddress.setOnClickListener(this);
        tvDesc=rootView.findViewById(R.id.tvDesc);


        ViewCompat.setNestedScrollingEnabled(nested,false);
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.tvLinkAddress:

                Utility.openUrlCustomTab(this.getActivity(), headProfileData.getWiki());

                break;
        }
    }
}


