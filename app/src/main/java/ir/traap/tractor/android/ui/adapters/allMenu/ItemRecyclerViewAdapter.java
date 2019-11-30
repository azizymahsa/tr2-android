package ir.traap.tractor.android.ui.adapters.allMenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.model.allService.response.SubMenu;


public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder> {

    private ItemRecyclerViewAdapter.OnItemClickListenerItem mItemClickListener;
    private Context mContext;
    private List<SubMenu> list;

    public ItemRecyclerViewAdapter(Context mContext, List<SubMenu> list, OnItemClickListenerItem mItemClickListener)
    {
        this.mItemClickListener = mItemClickListener;
        this.mContext = mContext;
        this.list = list;
    }


    @NonNull
    @Override
    public ItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.adapter_main_service_model_item, null);

        ItemRecyclerViewAdapter.ViewHolder viewHolder = new ItemRecyclerViewAdapter.ViewHolder(itemLayoutView);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

/*    }

    @Override
    public void onBindViewHolder(@NonNull MainServiceModelAdapter.ViewHolder holder, int position)
    {*/
        SubMenu item = list.get(position);

        holder.tvTitle.setText(item.getTitle());

        try
        {
            Picasso.with(mContext).load(item.getImageName()).into(holder.image, new Callback()
            {
                @Override
                public void onSuccess()
                {
                    holder.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError()
                {
                    holder.progressBar.setVisibility(View.GONE);
                    Picasso.with(mContext).load(R.drawable.ic_logo_red).into(holder.image);
                }
            });
        }
        catch (Exception e)
        {
            holder.progressBar.setVisibility(View.GONE);
            Picasso.with(mContext).load(R.drawable.ic_logo_red).into(holder.image);
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

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener
    {
        public TextView tvTitle;
        public ImageView image;
        public ProgressBar progressBar;
        public LinearLayout rootView;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            rootView = itemView.findViewById(R.id.rootView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            image = itemView.findViewById(R.id.image);
            progressBar = itemView.findViewById(R.id.progress);

            rootView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            if (mItemClickListener != null)
            {
                mItemClickListener.onChosenItemClickk(view,  list.get(getAdapterPosition()).getKeyId());
            }
        }
    }


    public interface OnItemClickListenerItem
    {
        public void onChosenItemClickk(View view, Integer id);
    }

//    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener)
//    {
//        this.mItemClickListener = mItemClickListener;
//    }

}
