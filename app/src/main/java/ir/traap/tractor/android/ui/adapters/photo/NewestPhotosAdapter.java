package ir.traap.tractor.android.ui.adapters.photo;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.model.mainVideos.Category;
import ir.traap.tractor.android.apiServices.model.mainVideos.Recent;
import ir.traap.tractor.android.ui.fragments.main.MainActionView;

/**
 * Created by MahsaAzizi on 11/23/2019.
 */
public class NewestPhotosAdapter extends RecyclerView.Adapter<NewestPhotosAdapter.ViewHolder>
{
    private  MainActionView mainView;
    private Context context;
    private ArrayList<Category> recent;




    public NewestPhotosAdapter(ArrayList<Category> recent, MainActionView mainView)
    {
        this.recent=recent;
        this.mainView=mainView;
    }

    public NewestPhotosAdapter(ArrayList<Category> recent)
    {
        this.recent=recent;
        // this.mainView=mainView;
    }


    @Override
    public NewestPhotosAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType)
    {
        this.context = parent.getContext();
        return new NewestPhotosAdapter.ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.list_item_newest_video, parent, false));
    }


    @Override
    public void onBindViewHolder(final NewestPhotosAdapter.ViewHolder holder, final int position)
    {
        Category recentItem = recent.get(position);
        holder.tvTitleVideo.setText(recentItem.getTitle());
     //   holder.tvLike.setText(recentItem.getLikes().toString());
        setImageBackground(holder.ivNewestVideo,recentItem.getCover().replace("\\", ""));

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
        public ImageView imgLike,imgIcon;

        public ViewHolder(View v)
        {
            super(v);
            tvTitleVideo=v.findViewById(R.id.tvTitleVideo);
            ivNewestVideo=v.findViewById(R.id.ivNewestVideo);
            tvLike=v.findViewById(R.id.tvLike);
            imgLike=v.findViewById(R.id.imgLike);
            imgIcon=v.findViewById(R.id.imgIcon);
            tvLike.setVisibility(View.GONE);
            imgLike.setVisibility(View.GONE);
            imgIcon.setVisibility(View.GONE);

        }
    }

}
