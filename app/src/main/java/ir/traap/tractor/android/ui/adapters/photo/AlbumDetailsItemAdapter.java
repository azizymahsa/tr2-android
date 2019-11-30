package ir.traap.tractor.android.ui.adapters.photo;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.model.allService.response.SubMenu;
import ir.traap.tractor.android.apiServices.model.photo.response.Content;
import ir.traap.tractor.android.models.otherModels.mainService.MainServiceModelItem;
import ir.traap.tractor.android.ui.activities.photo.AlbumDetailActivity;
import library.android.service.model.Hotel.confirm.subModel.Holder;

public class AlbumDetailsItemAdapter extends RecyclerView.Adapter<AlbumDetailsItemAdapter.ViewHolder>
{
    private AlbumDetailsItemAdapter.OnItemAllMenuClickListener mItemClickListener;
    private Context mContext;
    private  List<Content> list;
    private int row_index = 0;
    private View view;

    public AlbumDetailsItemAdapter(Context mContext, List<Content> list, AlbumDetailActivity mItemClickListener)
    {
        this.mItemClickListener = mItemClickListener;
        this.mContext = mContext;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_recycle_album_, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Content item = list.get(position);

      //  holder.tvTitle.setText(item.getTitle());
        holder.rootView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                row_index = position;
                mItemClickListener.OnItemAllMenuClick(view, item.getId(), item);
                notifyDataSetChanged();

            }
        });
        /*if (row_index == 0)
            mItemClickListener.OnItemAllMenuClick(view, list.get(0).getId(), item);*/
        if (row_index == position)
        {
            try
            {
                setImageBackground(holder.image,item.getImageName().getThumbnailSmall().replace("\\", ""));
                holder.progressBar.setVisibility(View.GONE);


            }
            catch (Exception e)
            {
                holder.progressBar.setVisibility(View.GONE);
                Picasso.with(mContext).load(R.drawable.ic_logo_red).into(holder.image);
            }
        } else
        {
            try
            {
                setImageBackground(holder.image,item.getImageName().getThumbnailSmall().replace("\\", ""));
                holder.progressBar.setVisibility(View.GONE);
            } catch (Exception e1)
            {
                holder.progressBar.setVisibility(View.GONE);
                Picasso.with(mContext).load(R.drawable.ic_logo_red).into(holder.image);
            }
        }
        //------------test--------------------
//        holder.progressBar.setVisibility(View.GONE);
//        Picasso.with(mContext).load(R.drawable.ic_logo_red).into(holder.image);
        //------------test--------------------


    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
       // public TextView tvTitle;
        public RoundedImageView image;
        public ProgressBar progressBar;
        public LinearLayout rootView;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            view = itemView;
            rootView = itemView.findViewById(R.id.rootView);
           // tvTitle = itemView.findViewById(R.id.tvTitle);
            image = itemView.findViewById(R.id.image);
            progressBar = itemView.findViewById(R.id.progress);


        }

      /*  @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {

            }
        }*/
    }

    private void setImageBackground(ImageView image, String link)
    {
        try
        {
            Glide.with(mContext).load(Uri.parse(link)).into(image);
        }
        catch (NullPointerException e)
        {
            Glide.with(mContext).load(R.drawable.img_failure).into(image);

            //Picasso.with(mContext).load(R.drawable.img_failure).into(image);
        }
    }
    public interface OnItemAllMenuClickListener
    {
        public void OnItemAllMenuClick(View view, Integer id,Content content);
    }

//    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener)
//    {
//        this.mItemClickListener = mItemClickListener;
//    }

}
