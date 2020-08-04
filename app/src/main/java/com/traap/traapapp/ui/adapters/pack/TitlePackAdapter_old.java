package com.traap.traapapp.ui.adapters.pack;

import android.animation.ObjectAnimator;
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

import androidx.recyclerview.widget.RecyclerView;

import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;

import java.util.ArrayList;
import java.util.List;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.getRightelPack.response.Detail;
import com.traap.traapapp.models.otherModels.pack.RightelPackModel;
import com.traap.traapapp.utilities.SimpleDividerItemDecoration;

/**
 * Created by Javad.Abadi on 8/13/2018.
 */
public class TitlePackAdapter_old extends RecyclerView.Adapter<TitlePackAdapter_old.ViewHolder>
{

    private final List<RightelPackModel> data;

    private Context context;
    private DetailPackAdapter_old detailPackAdapterOld;
    private SparseBooleanArray expandState = new SparseBooleanArray();
    private DetailPackAdapter_old.GetPackInAdapter getPackInAdapter;
    private String type;
    private Integer operatorType;

    public TitlePackAdapter_old(final List<RightelPackModel> data, DetailPackAdapter_old.GetPackInAdapter getPackInAdapter, String type, Integer operatorType)
    {
        this.data = data;
        this.getPackInAdapter = getPackInAdapter;
        this.type = type;
        this.operatorType = operatorType;
        for (int i = 0; i < data.size(); i++)
        {
            expandState.append(i, false);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType)
    {
        this.context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.recycler_view_rightel_item, parent, false));
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {

        final RightelPackModel item = data.get(position);

        holder.tvTitle.setText(item.getTitle());
        List<Detail> details = new ArrayList<>();

        if (!TextUtils.isEmpty(type))
        {
           /* if (type.equals("all"))
            {
                detailPackAdapter = new DetailPackAdapter(item.getDetail(), getPackInAdapter);
                holder.tvTitle.setText(item.getTitle() + " (" + item.getDetail().size() + ")");
            } else
            {*/
                for (int i = 0; i < item.getDetail().size(); i++)
                {
                    if (item.getDetail().get(i).getPackageType().equals(type))
                        details.add(item.getDetail().get(i));

                }
                detailPackAdapterOld = new DetailPackAdapter_old(details, getPackInAdapter,operatorType);
                holder.tvTitle.setText(item.getTitle() + " (" + details.size() + ")");


        } else
        {
            detailPackAdapterOld = new DetailPackAdapter_old(item.getDetail(), getPackInAdapter,operatorType);
            holder.tvTitle.setText(item.getTitle() + " (" + item.getDetail().size() + ")");


        }


        holder.expandableLayout.setInRecyclerView(true);
        holder.detailRecycler.setAdapter(detailPackAdapterOld);


        holder.detailRecycler.addItemDecoration(new SimpleDividerItemDecoration(context));

        holder.expandableLayout.setExpanded(expandState.get(position));
        holder.expandableLayout.setListener(new ExpandableLayoutListenerAdapter()
        {
            @Override
            public void onPreOpen()
            {
                createRotateAnimator(holder.imgArrow, 0f, 180f).start();
                expandState.put(position, true);
            }

            @Override
            public void onPreClose()
            {
                createRotateAnimator(holder.imgArrow, 180f, 0f).start();
                expandState.put(position, false);
            }
        });

        holder.imgArrow.setRotation(expandState.get(position) ? 180f : 0f);
        holder.buttonLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View v)
            {
                onClickButton(holder.expandableLayout);
            }
        });




        if (operatorType==1){
            holder.llImage.setBackground(context.getResources().getDrawable(R.drawable.circle_background_1));


        }else if (operatorType==2){
            holder.llImage.setBackground(context.getResources().getDrawable(R.drawable.circle_background_2));

        }else{
            holder.llImage.setBackground(context.getResources().getDrawable(R.drawable.circle_background_3));

        }


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
        public ImageView imgArrow;
        public TextView tvTitle;
        public RecyclerView detailRecycler;
        public LinearLayout llImage;

        public ExpandableLinearLayout expandableLayout;
        //  public RelativeLayout tvArrow;
        RelativeLayout buttonLayout;

        public ViewHolder(View v)
        {
            super(v);
            detailRecycler = v.findViewById(R.id.detailRecycler);
            expandableLayout = v.findViewById(R.id.expandableLayout);
            imgArrow = v.findViewById(R.id.imgArrow);
            tvTitle = v.findViewById(R.id.tvTitle);
            buttonLayout = v.findViewById(R.id.buttonLayout);
            llImage = v.findViewById(R.id.llImage);
        }
    }


}
