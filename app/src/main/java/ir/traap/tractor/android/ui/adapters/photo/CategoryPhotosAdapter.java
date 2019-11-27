package ir.traap.tractor.android.ui.adapters.photo;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.model.mainVideos.Category;
import ir.traap.tractor.android.ui.fragments.main.MainActionView;

/**
 * Created by MahsaAzizi on 11/27/2019.
 */
public class CategoryPhotosAdapter extends RecyclerView.Adapter<CategoryPhotosAdapter.ViewHolder>
{
    private final MainActionView mainView;
    private Context context;
    private List<Category> categories;
    private TitleCategoryListener listener;

    public CategoryPhotosAdapter(List<Category> categories, MainActionView mainView)
    {
        this.categories=categories;
        this.mainView=mainView;
    }


    @Override
    public CategoryPhotosAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType)
    {
        this.context = parent.getContext();
        return new CategoryPhotosAdapter.ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.list_item_category_photo, parent, false));
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {
        Category category = categories.get(position);

        setImageBackground(holder.ivCategory,category.getCover().replace("\\", ""));

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

        return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public com.makeramen.roundedimageview.RoundedImageView ivCategory;

        public ViewHolder(View v)
        {
            super(v);
            ivCategory=v.findViewById(R.id.ivCategory);

        }
    }
    public interface TitleCategoryListener {
        void onItemTitleCategoryClick(Category category);
    }
}
