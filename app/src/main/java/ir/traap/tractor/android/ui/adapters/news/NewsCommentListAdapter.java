package ir.traap.tractor.android.ui.adapters.news;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.model.news.details.getComment.response.GetNewsCommentResponse;

public class NewsCommentListAdapter extends RecyclerView.Adapter<NewsCommentListAdapter.ViewHolder>
{
    private OnItemClickListener mItemClickListener;
    private Context mContext;
    private List<GetNewsCommentResponse> list;

    public NewsCommentListAdapter(Context mContext, List<GetNewsCommentResponse> list, OnItemClickListener mItemClickListener)
    {
        this.mContext = mContext;
        this.list = list;
        this.mItemClickListener = mItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.adapter_news_comment_list, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        GetNewsCommentResponse item = list.get(position);

        holder.tvUser.setText(item.getUser().getFirstName() + " " +
                item.getUser().getLastName() + " | " +
                item.getCreateDate()
        );

        holder.tvComment.setText(item.getBody());

        holder.tvLikeCounter.setText(String.valueOf(item.getLikeCount()));
        holder.tvDislikeCounter.setText(String.valueOf(item.getDislikeCount()));

        holder.imgLike.setOnClickListener(v ->
        {
            switch (item.getRated())
            {
                case 0:
                {
                    int newLike = item.getLikeCount() + 1;
                    holder.tvLikeCounter.setText(String.valueOf(newLike));
                    mItemClickListener.onLikeItemClick(v, item.getId(), position);
                    break;
                }
//                case 1:
//                {
//                    holder.tvLikeCounter.setText("");
//                    holder.tvDislikeCounter.setText("");
//                    break;
//                }
                case -1:
                {
                    int newLike = item.getLikeCount() + 1;
                    int newDislike = item.getDislikeCount() - 1;
                    holder.tvLikeCounter.setText(String.valueOf(newLike));
                    holder.tvDislikeCounter.setText(String.valueOf(newDislike));
                    mItemClickListener.onLikeItemClick(v, item.getId(), position);
                    break;
                }
            }
            item.setRated(1);
        });

        holder.imgDislike.setOnClickListener(v ->
        {
            switch (item.getRated())
            {
                case 0:
                {
                    int newDislike = item.getDislikeCount() + 1;
                    holder.tvDislikeCounter.setText(String.valueOf(newDislike));
                    mItemClickListener.onDislikeItemClick(v, item.getId(), position);
                    break;
                }
//                case -1:
//                {
//                    holder.tvLikeCounter.setText("");
//                    holder.tvDislikeCounter.setText("");
//                    break;
//                }
                case 1:
                {
                    int newLike = item.getLikeCount() - 1;
                    int newDislike = item.getDislikeCount() + 1;
                    holder.tvLikeCounter.setText(String.valueOf(newLike));
                    holder.tvDislikeCounter.setText(String.valueOf(newDislike));
                    mItemClickListener.onDislikeItemClick(v, item.getId(), position);
                    break;
                }
            }
            item.setRated(-1);
        });

        switch (item.getRated())
        {
            case 0:
            {
                holder.imgLike.setColorFilter(mContext.getResources().getColor(R.color.textHint));
                holder.imgDislike.setColorFilter(mContext.getResources().getColor(R.color.textHint));
                break;
            }
            case 1:
            {
                holder.imgLike.setColorFilter(mContext.getResources().getColor(R.color.backgroundButton));
                holder.imgDislike.setColorFilter(mContext.getResources().getColor(R.color.textHint));
                break;
            }
            case -1:
            {
                holder.imgLike.setColorFilter(mContext.getResources().getColor(R.color.textHint));
                holder.imgDislike.setColorFilter(mContext.getResources().getColor(R.color.backgroundButton));
                break;
            }
            default:
            {
                holder.imgLike.setColorFilter(mContext.getResources().getColor(R.color.textHint));
                holder.imgDislike.setColorFilter(mContext.getResources().getColor(R.color.textHint));
                break;
            }
        }

    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener
    {
        private TextView tvUser, tvComment, tvLikeCounter, tvDislikeCounter;
        private ImageView imgLike, imgDislike, imgReplay;

        public ViewHolder(@NonNull View rootView)
        {
            super(rootView);

            imgLike = rootView.findViewById(R.id.imgLike);
            imgDislike = rootView.findViewById(R.id.imgDislike);
            imgReplay = rootView.findViewById(R.id.imgReplay);
            tvUser = rootView.findViewById(R.id.tvUser);
            tvComment = rootView.findViewById(R.id.tvComment);
            tvLikeCounter = rootView.findViewById(R.id.tvLikeCounter);
            tvDislikeCounter = rootView.findViewById(R.id.tvDislikeCounter);

            imgReplay.setOnClickListener(this);
//            imgLike.setOnClickListener(this);
//            imgDislike.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            if (mItemClickListener != null)
            {
                switch (view.getId())
                {
//                    case R.id.imgLike:
//                    {
//                        mItemClickListener.onLikeItemClick(view, list.get(getAdapterPosition()).getId(), getAdapterPosition());
//                        break;
//                    }
//                    case R.id.imgDislike:
//                    {
//                        mItemClickListener.onDislikeItemClick(view, list.get(getAdapterPosition()).getId(), getAdapterPosition());
//                        break;
//                    }
                    case R.id.imgReplay:
                    {
                        mItemClickListener.onReplayItemClick(view, list.get(getAdapterPosition()).getId(), getAdapterPosition());
                        break;
                    }
                }
            }
        }
    }


    public interface OnItemClickListener
    {
        public void onLikeItemClick(View view, Integer id, Integer position);

        public void onDislikeItemClick(View view, Integer id, Integer position);

        public void onReplayItemClick(View view, Integer id, Integer position);
    }

}
