package ir.trap.tractor.android.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ir.trap.tractor.android.R;
import ir.trap.tractor.android.singleton.SingletonContext;


/**
 * Created by Mahsa.Azizi
 */
public class ImagePagerAdapter extends RecyclerView.Adapter<ImagePagerAdapter.MyViewHolder> {

    private List<Integer> pic=new ArrayList<>();
    //private MainView mainView;
    private Context mainView;
    private Context context;


   public ImagePagerAdapter(Context mainView) {
        pic.add(R.drawable.img_failure);
        pic.add(R.drawable.img_failure);
        pic.add(R.drawable.img_failure);
        this.mainView = mainView;
        context = SingletonContext.getInstance().getContext();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.slider_item, parent, false);


        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        holder.ivImage.setImageDrawable(context.getResources().getDrawable(pic.get(position)));

        }


    @Override
    public int getItemCount() {
        return pic.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivImage;


        private MyViewHolder(View convertView) {
            super(convertView);
            ivImage = convertView.findViewById(R.id.ivImage);

    }}

}
