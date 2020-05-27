package com.traap.traapapp.ui.fragments.headCoach.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.topPlayers.Result;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>
{
    private Context context;


    public CommentAdapter()
    {

    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType)
    {
        this.context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.comment_item, parent, false));
    }


    @Override
    public void onBindViewHolder(final CommentAdapter.ViewHolder holder, final int position)
    {
        if (position==2){
            holder.llReply.setVisibility(View.VISIBLE);
        }else{
            holder.llReply.setVisibility(View.GONE);

        }
        if (position==0){
            holder.llEdit.setVisibility(View.VISIBLE);
        }else{
            holder.llEdit.setVisibility(View.GONE);

        }


    }





    @Override
    public int getItemCount()
    {

        return 10;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private LinearLayout llReply,llEdit;


        public ViewHolder(View v)
        {
            super(v);
            llReply=v.findViewById(R.id.llReply);
            llEdit=v.findViewById(R.id.llEdit);

        }
    }


}
