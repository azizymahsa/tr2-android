package ir.traap.tractor.android.ui.adapters.video;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import java.util.List;
import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.model.mainVideos.Category;
import ir.traap.tractor.android.apiServices.model.mainVideos.Recent;
import ir.traap.tractor.android.ui.fragments.main.MainActionView;

/**
 * Created by MahtabAzizi on 11/23/2019.
 */
public class NewestVideosAdapter extends RecyclerView.Adapter<NewestVideosAdapter.ViewHolder>
{
    private final MainActionView mainView;
    private Context context;
    private List<Category> recent;
    private NewestVideoListener listener;





    public NewestVideosAdapter(List<Category> recent, MainActionView mainView,NewestVideoListener listener)
    {
        this.recent=recent;
        this.mainView=mainView;
        this.listener=listener;
    }


    @Override
    public NewestVideosAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType)
    {
        this.context = parent.getContext();
        return new NewestVideosAdapter.ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.list_item_newest_video, parent, false));
    }


    @Override
    public void onBindViewHolder(final NewestVideosAdapter.ViewHolder holder, final int position)
    {
        Category recentItem = recent.get(position);
        holder.tvTitleVideo.setText(recentItem.getTitle());
        holder.tvLike.setText(recentItem.getLikes().toString());
        setImageBackground(holder.ivNewestVideo,recentItem.getBigPoster().replace("\\", ""));
        holder.ivNewestVideo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener.onItemNewestVideoClick(position,recentItem);
            }
        });

    }

    private void setImageBackground(ImageView image, String link)
    {
        try
        {
            Glide.with(context).load(Uri.parse(link)).into(image);
       /*     Picasso.with(context).load(Uri.parse(link)).into(image, new Callback()
            {
                @Override
                public void onSuccess() { }

                @Override
                public void onError()
                {
                    Picasso.with(context).load(R.drawable.img_failure).into(image);
                }
            });*/
        }
        catch (NullPointerException e)
        {
            Picasso.with(context).load(R.drawable.img_failure).into(image);
        }
    }



    @Override
    public int getItemCount()
    {

        return recent.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public RoundedImageView ivNewestVideo;
        public TextView tvTitleVideo,tvLike;

        public ViewHolder(View v)
        {
            super(v);
            tvTitleVideo=v.findViewById(R.id.tvTitleVideo);
            ivNewestVideo=v.findViewById(R.id.ivNewestVideo);
            tvLike=v.findViewById(R.id.tvLike);

        }
    }

    public interface NewestVideoListener {
        void onItemNewestVideoClick(int position, Category category);
    }

}
