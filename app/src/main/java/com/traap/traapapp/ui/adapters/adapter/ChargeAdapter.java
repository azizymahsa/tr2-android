package com.traap.traapapp.ui.adapters.adapter;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.availableAmount.Result;
import com.traap.traapapp.apiServices.model.getRightelPack.response.Detail;
import com.traap.traapapp.models.otherModels.pack.RightelPackModel;
import com.traap.traapapp.ui.adapters.pack.DetailPackAdapter;
import com.traap.traapapp.utilities.SimpleDividerItemDecoration;
import com.traap.traapapp.utilities.Utility;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Javad.Abadi on 8/13/2018.
 */
public class ChargeAdapter extends RecyclerView.Adapter<ChargeAdapter.ViewHolder>
{

    private final List<Result> data;

    private Activity activity;
    public ChargeAdapterEvent event;

    public ChargeAdapter(final List<Result> data, Activity activity,ChargeAdapterEvent event)
    {
        this.data = data;
        this.activity = activity;
        this.event = event;

    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_charge_item, parent, false));
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {

        final Result item = data.get(position);
        holder.tvTitle.setText(Utility.priceFormat(item.getAmount())+" ریال");
        if (item.getOperatorType()==1){
            holder.llImage.setBackground(activity.getResources().getDrawable(R.drawable.circle_background_1));


        }else if (item.getOperatorType()==2){
            holder.llImage.setBackground(activity.getResources().getDrawable(R.drawable.circle_background_2));

        }else{
            holder.llImage.setBackground(activity.getResources().getDrawable(R.drawable.circle_background_3));

        }

        holder.rlRoot.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                event.onClickChargeAmount(item);

            }
        });






    }

    private void onClickButton(final ExpandableLayout expandableLayout)
    {
        expandableLayout.toggle();
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }

    public ObjectAnimator createRotateAnimator(final View target, final float from, final float to)
    {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView tvTitle;
        public LinearLayout llImage;
        public RelativeLayout rlRoot;

        //  public RelativeLayout tvArrow;
        RelativeLayout buttonLayout;

        public ViewHolder(View v)
        {
            super(v);
            tvTitle = v.findViewById(R.id.tvTitle);
            buttonLayout = v.findViewById(R.id.buttonLayout);
            llImage = v.findViewById(R.id.llImage);
            rlRoot = v.findViewById(R.id.rlRoot);
        }
    }

    public interface ChargeAdapterEvent{
        void onClickChargeAmount(Result result);


}

}
