package com.traap.traapapp.ui.fragments.Introducing_the_team.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.mainVideos.Category;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;


public class TeamPhotoAdapter extends RecyclerView.Adapter<TeamPhotoAdapter.ViewHolder>
{
    private Context context;


    public TeamPhotoAdapter()
    {

    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType)
    {
        this.context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.team_photo_adapter, parent, false));
    }


    @Override
    public void onBindViewHolder(final TeamPhotoAdapter.ViewHolder holder, final int position)
    {


    }




    @Override
    public int getItemCount()
    {

        return 10;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {


        public ViewHolder(View v)
        {
            super(v);

        }
    }


}
