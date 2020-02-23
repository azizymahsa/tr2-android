package com.traap.traapapp.ui.adapters.spectatorList;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.spectatorInfo.SpectatorInfoResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MahtabAzizi on 2/22/2020.
 */
public class SpectatorListAdapter extends RecyclerView.Adapter<SpectatorListAdapter.ViewHolder> implements CompoundButton.OnCheckedChangeListener
{
    private SpectatorListAdapter.OnItemSpectatorListClickListener mItemClickListener;
    private Context context;
    private List<SpectatorInfoResponse> spectatorInfoResponse;
    private ArrayList<SpectatorInfoResponse> selectedInfo = new ArrayList<SpectatorInfoResponse>();


    public SpectatorListAdapter(List<SpectatorInfoResponse> spectatorInfoResponse, SpectatorListAdapter.OnItemSpectatorListClickListener mItemClickListener)
    {
        this.spectatorInfoResponse = spectatorInfoResponse;
        this.mItemClickListener = mItemClickListener;

        // this.mainView=mainView;
    }

 /*   public SpectatorListAdapter(List<SpectatorInfoResponse> spectatorInfoResponse)
    {
        this.spectatorInfoResponse = spectatorInfoResponse;

    }
*/

    @Override
    public SpectatorListAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType)
    {
        this.context = parent.getContext();
        return new SpectatorListAdapter.ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.list_item_spectator_info, parent, false));
    }


    @Override
    public void onBindViewHolder(final SpectatorListAdapter.ViewHolder holder, final int position)
    {
        SpectatorInfoResponse spectatorInfo = spectatorInfoResponse.get(position);
        holder.tvSpectatorInfo.setText(spectatorInfo.getFirstName() + " " + spectatorInfo.getLastName() + "-" + spectatorInfo.getNationalCode());
        holder.cbSelectSpectator.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    // selectedInfo=new ArrayList<SpectatorInfoResponse>();

                    selectedInfo.add(position, new SpectatorInfoResponse(spectatorInfo.getFirstName(), spectatorInfo.getLastName(), spectatorInfo.getNationalCode()));
                } else
                {
                    selectedInfo.remove(position);
                }

                mItemClickListener.OnItemSpectatorListClick(selectedInfo);

            }
        });
        //  mItemClickListener.OnItemRelatedAlbumsClick(view, recentItem);
    }


    @Override
    public int getItemCount()
    {

        return spectatorInfoResponse.size();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {

        if (isChecked)
        {


        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView tvSpectatorInfo;
        public CheckBox cbSelectSpectator;

        public ViewHolder(View v)
        {
            super(v);
            tvSpectatorInfo = v.findViewById(R.id.tvSpectatorInfo);
            cbSelectSpectator = v.findViewById(R.id.cbSelectSpectator);

        }
    }

    public interface OnItemSpectatorListClickListener
    {

        void OnItemSpectatorListClick(ArrayList<SpectatorInfoResponse> selectedInfo);
    }

}