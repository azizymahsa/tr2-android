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


    public CommentAdapter(ArrayList<CoachCommentModel> coachCommentModel, CommentAdapterEvents adapterEvents)
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

        holder.llReply.setVisibility(View.GONE);


        /*private TextView commentUser,commentDate,commentLike,commentDisLike,tvComment;
        private TextView replyUser,replyDate,replyText,replyLike,replyDisLike;*/
        if (coachCommentModel.get(position).getUser())
        {
            //holder.tvComment.setText(coachCommentModel.get(position).getResults().getBody());
            holder.tvComment.setText(coachCommentModel.get(position).getComment());
            holder.llEdit.setVisibility(View.VISIBLE);

        } else
        {
            holder.llEdit.setVisibility(View.GONE);
            holder.commentUser.setText(coachCommentModel.get(position).getResults().getUser());
            holder.commentDate.setText(coachCommentModel.get(position).getResults().getCreateDate());
            holder.commentLike.setText(coachCommentModel.get(position).getResults().getLikesCount() + "");
            holder.commentDisLike.setText(coachCommentModel.get(position).getResults().getDislikesCount() + "");
            holder.tvComment.setText(coachCommentModel.get(position).getResults().getBody());

            if (coachCommentModel.get(position).getResults().getRated() == 1)
            {
                holder.rvCLike.setColorFilter(context.getResources().getColor(R.color.imageDislikedTintColor));

            } else if (coachCommentModel.get(position).getResults().getRated() == 0)
            {
                holder.rvCDisLike.setColorFilter(context.getResources().getColor(R.color.textHint));
            }

            if (coachCommentModel.get(position).getResults().getReplies().size() > 0)
            {
                holder.llReply.setVisibility(View.VISIBLE);
                holder.replyUser.setText(coachCommentModel.get(position).getResults().getReplies().get(0).getUser());
                holder.replyDate.setText(coachCommentModel.get(position).getResults().getReplies().get(0).getCreateDate());
                holder.replyText.setText(coachCommentModel.get(position).getResults().getReplies().get(0).getBody());
                holder.replyLike.setText(coachCommentModel.get(position).getResults().getReplies().get(0).getLikesCount() + "");
                holder.replyDisLike.setText(coachCommentModel.get(position).getResults().getReplies().get(0).getDislikesCount() + "");
                if (coachCommentModel.get(position).getResults().getRated() == 1)
                {
                    holder.rvRLike.setColorFilter(context.getResources().getColor(R.color.imageDislikedTintColor));

                } else if (coachCommentModel.get(position).getResults().getRated() == 0)
                {
                    holder.rvRDisLike.setColorFilter(context.getResources().getColor(R.color.textHint));
                }

            }

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
        holder.rvCLike.setOnClickListener(v ->
        {
            adapterEvents.onLikedCommentAdapter(position, coachCommentModel.get(position).getResults().getId(),true);
         /*   if (coachCommentModel.get(position).getResults().getRated() == 0)
            {
                holder.rvCLike.setColorFilter(context.getResources().getColor(R.color.imageDislikedTintColor));

            } else if (coachCommentModel.get(position).getResults().getRated() == 1)
            {
                holder.rvCDisLike.setColorFilter(context.getResources().getColor(R.color.textHint));
            }*/
        });
        holder.rvCDisLike.setOnClickListener(v ->
        {
            adapterEvents.onLikedCommentAdapter(position,coachCommentModel.get(position).getResults().getId(),false);
           /* if (coachCommentModel.get(position).getResults().getRated() == 0)
            {
                holder.rvCLike.setColorFilter(context.getResources().getColor(R.color.imageDislikedTintColor));

            } else if (coachCommentModel.get(position).getResults().getRated() == 1)
            {
                holder.rvCDisLike.setColorFilter(context.getResources().getColor(R.color.textHint));
            }*/
        });
        //Reply
        holder.rvRLike.setOnClickListener(v ->
        {
            adapterEvents.onLikedCommentAdapter(position, coachCommentModel.get(position).getResults().getReplies().get(0).getId(), true);
            /*if (coachCommentModel.get(position).getResults().getReplies().get(0).getRated() == 0)
            {
                holder.rvRLike.setColorFilter(context.getResources().getColor(R.color.imageDislikedTintColor));

            } else if (coachCommentModel.get(position).getResults().getReplies().get(0).getRated() == 1)
            {
                holder.rvRDisLike.setColorFilter(context.getResources().getColor(R.color.textHint));
            }*/
        });
        holder.rvRDisLike.setOnClickListener(v ->
        {
            adapterEvents.onLikedCommentAdapter(position, coachCommentModel.get(position).getResults().getReplies().get(0).getId(), false);
          /*  if (coachCommentModel.get(position).getResults().getReplies().get(0).getRated() == 0)
            {
                holder.rvRLike.setColorFilter(context.getResources().getColor(R.color.imageDislikedTintColor));

            } else if (coachCommentModel.get(position).getResults().getReplies().get(0).getRated() == 1)
            {
                holder.rvRDisLike.setColorFilter(context.getResources().getColor(R.color.textHint));
            }*/
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
        private TextView commentUser, commentDate, commentLike, commentDisLike, tvComment;
        private TextView replyUser, replyDate, replyText, replyLike, replyDisLike;
        private ImageView ivDelete, ivEdit;
        private ImageView rvCDisLike, rvCLike, rvRDisLike, rvRLike;


        public ViewHolder(View v)
        {
            super(v);
            replyDisLike = v.findViewById(R.id.replyDisLike);
            replyLike = v.findViewById(R.id.replyLike);
            replyText = v.findViewById(R.id.replyText);
            replyDate = v.findViewById(R.id.replyDate);
            replyUser = v.findViewById(R.id.replyUser);


            rvRDisLike = v.findViewById(R.id.rvRDisLike);
            rvRLike = v.findViewById(R.id.rvRLike);
            commentDisLike = v.findViewById(R.id.commentDisLike);
            commentLike = v.findViewById(R.id.commentLike);
            commentUser = v.findViewById(R.id.commentUser);
            commentDate = v.findViewById(R.id.commentDate);
            llReply = v.findViewById(R.id.llReply);
            llRoot = v.findViewById(R.id.llRoot);
            llEdit = v.findViewById(R.id.llEdit);
            llUserComment = v.findViewById(R.id.llUserComment);
            tvComment = v.findViewById(R.id.tvComment);
            ivDelete = v.findViewById(R.id.ivDelete);
            ivEdit = v.findViewById(R.id.ivEdit);
            rvCLike = v.findViewById(R.id.rvCLike);
            rvCDisLike = v.findViewById(R.id.rvCDisLike);
            //  rvCDisLike.setOnClickListener(this);
            // rvCLike.setOnClickListener(this);

        }
    }

    public interface CommentAdapterEvents
    {
        void onDeleteCommentAdapter(Integer position);

        void onEditCommentAdapter(Integer position);

        void onLikedCommentAdapter(Integer position, Integer id,Boolean flagLike);
    }

}
