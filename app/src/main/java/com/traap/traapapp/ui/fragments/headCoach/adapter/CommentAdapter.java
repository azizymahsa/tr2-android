package com.traap.traapapp.ui.fragments.headCoach.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.traap.traapapp.R;
import com.traap.traapapp.ui.fragments.headCoach.model.CoachCommentModel;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>
{
    private Context context;
    private ArrayList<CoachCommentModel> coachCommentModel;
    private CommentAdapterEvents adapterEvents;


    public CommentAdapter(ArrayList<CoachCommentModel> coachCommentModel,CommentAdapterEvents adapterEvents)
    {
        this.coachCommentModel = coachCommentModel;
        this.adapterEvents = adapterEvents;

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
        if (position == 2)
        {
            holder.llReply.setVisibility(View.VISIBLE);
        } else
        {
            holder.llReply.setVisibility(View.GONE);

        }

        if (coachCommentModel.get(position).getUser())
        {
            holder.tvComment.setText(coachCommentModel.get(position).getComment());
            holder.llEdit.setVisibility(View.VISIBLE);

        } else
        {
            holder.llEdit.setVisibility(View.GONE);

        }
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500);
        holder.llRoot.startAnimation(anim);
        holder.ivDelete.setOnClickListener(v ->
        {
            adapterEvents.onDeleteCommentAdapter(position);

        });
        holder.ivEdit.setOnClickListener(v ->
        {
            adapterEvents.onEditCommentAdapter(position);

        });
    }


    @Override
    public int getItemCount()
    {

        return coachCommentModel.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private LinearLayout llReply, llEdit, llUserComment, llRoot;
        private TextView tvComment;
        private ImageView ivDelete, ivEdit;


        public ViewHolder(View v)
        {
            super(v);
            llReply = v.findViewById(R.id.llReply);
            llRoot = v.findViewById(R.id.llRoot);
            llEdit = v.findViewById(R.id.llEdit);
            llUserComment = v.findViewById(R.id.llUserComment);
            tvComment = v.findViewById(R.id.tvComment);
            ivDelete = v.findViewById(R.id.ivDelete);
            ivEdit = v.findViewById(R.id.ivEdit);

        }
    }

    public interface CommentAdapterEvents
    {
        void onDeleteCommentAdapter(Integer position);

        void onEditCommentAdapter(Integer position);
    }

}
