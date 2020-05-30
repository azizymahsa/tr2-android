package com.traap.traapapp.ui.fragments.headCoach.profileHeadCoach;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.cup.CupResponse;
import com.traap.traapapp.apiServices.model.techs.GetTechsIdResponse;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.Introducing_the_team.adapter.PositionInLeaguesAdapter;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;

/**
 * Created by MahtabAzizi on 5/26/2020.
 */
public class ProfileHeadCoahFragment extends BaseFragment
{
    private GetTechsIdResponse headProfileData;
    private View rootView;
    private NestedScrollView nested;
    public static Integer height;
    private TextView tvNameFa,tvNameEn,tvBirthday,tvJoin,tvLeave,tvNumberTshirt,tvStartDate,tvFeet,tvNationalGoals,tvClubGoals,tvEducation;


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

        tvNameFa.setText(headProfileData.getPersianFirstName()+headProfileData.getPersianLastName());
        tvNameEn.setText(headProfileData.getEnglishFirstName()+headProfileData.getEnglishLastName());
        tvBirthday.setText(headProfileData.getBirthday());
        tvClubGoals.setText(headProfileData.getClubGoals().toString());
       /* if (headProfileData.getEducation()!=null)
            tvEducation.setText(headProfileData.getEducation());*/
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
        tvNumberTshirt.setText("");
        tvStartDate.setText("");
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

        ViewCompat.setNestedScrollingEnabled(nested,false);
    }



}


