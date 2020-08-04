package com.traap.traapapp.ui.adapters.pack;

import android.animation.ObjectAnimator;
import android.content.Context;
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
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.getSimPackageList.response.SimContentItem;
import com.traap.traapapp.apiServices.model.getSimPackageList.response.SimPackage;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.ui.fragments.simcardPack.imp.GetSimContentListByTypeImp;
import com.traap.traapapp.utilities.SimpleDividerItemDecoration;

import java.util.List;

/**
 * Created by Javad.Abadi on 8/13/2018.
 */
public class TitlePackAdapter extends RecyclerView.Adapter<TitlePackAdapter.ViewHolder>
{
    private Context context;
    private DetailPackAdapter adapter;
    private SparseBooleanArray expandState = new SparseBooleanArray();
    private DetailPackAdapter.GetPackFromAdapterListener listener;
    private String type;
    private int operatorType, simType;
    private List<SimPackage> packageList;

    public TitlePackAdapter(int operatorType, int simType, List<SimPackage> packageList, DetailPackAdapter.GetPackFromAdapterListener listener)
    {
        this.packageList = packageList;
        this.operatorType = operatorType;
        this.simType = simType;
        this.listener = listener;

        for (int i = 0; i < packageList.size(); i++)
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
        SimPackage simPackage = packageList.get(position);

        List<SimContentItem> contentList = GetSimContentListByTypeImp.getSimContentListByType(simPackage.getContentList(), simType);

        adapter = new DetailPackAdapter(operatorType, contentList, listener);
        holder.expandableLayout.setInRecyclerView(true);
        holder.detailRecycler.setAdapter(adapter);

        holder.tvTitle.setText(new StringBuilder(
                simPackage.getDurationPackage())
                .append(" (")
//                .append(simPackage.getContentList().size())
                .append(contentList.size())
                .append(")")
        );

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
        holder.buttonLayout.setOnClickListener(v -> onClickButton(holder.expandableLayout));

        switch (operatorType)
        {
            case TrapConfig.OPERATOR_TYPE_MTN:
            {
                holder.llImage.setBackground(context.getResources().getDrawable(R.drawable.circle_background_1));
                break;
            }
            case TrapConfig.OPERATOR_TYPE_MCI:
            {
                holder.llImage.setBackground(context.getResources().getDrawable(R.drawable.circle_background_2));
                break;
            }
            case TrapConfig.OPERATOR_TYPE_RIGHTELL:
            {
                holder.llImage.setBackground(context.getResources().getDrawable(R.drawable.circle_background_3));
                break;
            }
        }
    }

    private void onClickButton(final ExpandableLayout expandableLayout)
    {
        expandableLayout.toggle();
    }

    @Override
    public int getItemCount()
    {
        return packageList.size();
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
        private RelativeLayout buttonLayout;

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
