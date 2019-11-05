package ir.traap.tractor.android.ui.adapters.Leaguse;

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

import com.app.feng.fixtablelayout.inter.IDataAdapter;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import ir.traap.tractor.android.R;


public class FixTableAdapter  extends RecyclerView.Adapter<FixTableAdapter.ViewHolder>{
    private final Context mContext;
    // private List<String> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private  List<DataBean> mData;


    public FixTableAdapter(String[] title, List<DataBean> mData, Context mContext) {
        this.mContext=mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.mData = mData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_calculate_credit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        DataBean item = mData.get(position);

        holder.teamTitle.setText(item.teamTitle);
        holder.matches.setText(item.matches);
        holder.won.setText(item.won);

        holder.drawn.setText(item.drawn);
        holder.lost.setText(item.lost);
        holder.goals_score.setText(item.goals_score);

        holder.goals_canceded.setText(item.goals_canceded);
        holder.goals_diff.setText(item.goals_diff);
        holder.point.setText(item.point);

        holder.ivTeam.setVisibility(View.VISIBLE);


        try {
            Picasso.with(mContext).load(item.imageLogo).into(holder.ivTeam, new Callback() {
                @Override
                public void onSuccess() {
                   // holder.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError() {
                    //holder.progressBar.setVisibility(View.GONE);
                    Picasso.with(mContext).load(R.drawable.ic_logo_red).into(holder.ivTeam);
                }
            });
        } catch (Exception e1) {
          //  holder.progressBar.setVisibility(View.GONE);
            Picasso.with(mContext).load(R.drawable.ic_logo_red).into(holder.ivTeam);
        }


    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
      //  TextView myTextView;
        TextView teamTitle;
        TextView matches;
        TextView won;

        TextView drawn;
        TextView lost;
        TextView goals_score;

        TextView goals_canceded;
        TextView goals_diff;
        TextView point;

        ImageView ivTeam;
        ViewHolder(View itemView) {
            super(itemView);
            teamTitle = itemView.findViewById(R.id.tvTeam);
            matches = itemView.findViewById(R.id.tvmatch);
            won = itemView.findViewById(R.id.tvWon);

            drawn = itemView.findViewById(R.id.tvdrawn);
            lost = itemView.findViewById(R.id.tvLost);
            goals_score = itemView.findViewById(R.id.tvGoulScore);

            goals_canceded = itemView.findViewById(R.id.tvGoalConceded);
            goals_diff = itemView.findViewById(R.id.tvGoalDiff);
            point = itemView.findViewById(R.id.tvPoint);

            ivTeam = itemView.findViewById(R.id.ivTeam);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
   /* String getItem(int id) {
        return mData.get(id);
    }*/

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

