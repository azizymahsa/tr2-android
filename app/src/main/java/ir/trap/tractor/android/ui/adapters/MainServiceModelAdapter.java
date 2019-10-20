package ir.trap.tractor.android.ui.adapters;

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

import ir.trap.tractor.android.R;
import ir.trap.tractor.android.models.otherModels.MainServiceModelItem;
import ir.trap.tractor.android.ui.fragments.allMenu.AllMenuFragment;

public class MainServiceModelAdapter extends RecyclerView.Adapter<MainServiceModelAdapter.ViewHolder>
{
    private OnItemClickListener mItemClickListener;
    private Context mContext;
    private List<MainServiceModelItem> list;

    public MainServiceModelAdapter(Context mContext, List<MainServiceModelItem> list, OnItemClickListener mItemClickListener)
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
                R.layout.adapter_main_service_model, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        MainServiceModelItem item = list.get(position);

        holder.tvTitle.setText(item.getTitle());

        try
        {
            Picasso.with(mContext).load(item.getImageLink()).into(holder.image, new Callback()
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
                mItemClickListener.onChosenItemClick(view,  list.get(getAdapterPosition()).getId());
            }
        }
    }


    public interface OnItemClickListener
    {
        public void onChosenItemClick(View view, Integer id);
    }

//    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener)
//    {
//        this.mItemClickListener = mItemClickListener;
//    }

}
