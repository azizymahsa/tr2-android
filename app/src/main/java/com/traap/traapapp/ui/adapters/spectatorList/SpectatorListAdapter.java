package com.traap.traapapp.ui.adapters.spectatorList;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.spectatorInfo.SpectatorInfoResponse;
import com.traap.traapapp.models.otherModels.ticket.SpectatorInfoModel;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MahtabAzizi on 2/22/2020.
 */
public class SpectatorListAdapter extends RecyclerView.Adapter<SpectatorListAdapter.ViewHolder>
{
    private SpectatorListAdapter.OnItemSpectatorListClickListener mItemClickListener;
    private Context context;
    private List<SpectatorInfoResponse> spectatorInfoResponse;
   private ArrayList<SpectatorInfoModel> selectedInfo = new ArrayList<>();
    private int countChecked=0;
    private int countTicket;
    private int positionSelectedInfos=0;
    Activity activity;


    public SpectatorListAdapter(Activity activity,List<SpectatorInfoResponse> spectatorInfoResponse, SpectatorListAdapter.OnItemSpectatorListClickListener mItemClickListener,Integer countTicket)
    {
        this.spectatorInfoResponse = spectatorInfoResponse;
        this.mItemClickListener = mItemClickListener;
        this.countTicket=countTicket;
        this.activity=activity;

        // this.mainView=mainView;
    }

 /*   public SpectatorListAdapter(List<SpectatorInfoResponse> spectatorInfoResponse)
    {
        this.spectatorInfoResponse = spectatorInfoResponse;

    }
*/

    @Override
    public SpectatorListAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType)
    {
        this.context = parent.getContext();
        return new SpectatorListAdapter.ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.list_item_spectator_info, parent, false));
    }


    @Override
    public void onBindViewHolder(final SpectatorListAdapter.ViewHolder holder, final int position)
    {
        SpectatorInfoResponse spectatorInfo = spectatorInfoResponse.get(position);
        holder.tvSpectatorInfo.setText(spectatorInfo.getFirstName() + " " + spectatorInfo.getLastName() + "-" + spectatorInfo.getNationalCode());
      holder.cbSelectSpectator.setChecked(spectatorInfo.getChecked());
        holder.cbSelectSpectator.setEnabled(false);


      holder.llItemMyBill.setOnClickListener(v ->
      {

          if (!spectatorInfo.getChecked()){

              if (countChecked>=countTicket){
                  showDialogError();
                  holder.cbSelectSpectator.setChecked(false);
              }else {
                  selectedInfo.add(countChecked,new SpectatorInfoModel(spectatorInfo.getFirstName(), spectatorInfo.getLastName(), spectatorInfo.getNationalCode()));
                  countChecked = countChecked + 1;
                  spectatorInfo.setChecked(true);

                  mItemClickListener.OnItemSpectatorListClick(selectedInfo,position);

              }
          }else{
              for (int i = 0; i <selectedInfo.size() ; i++) {
                  if (selectedInfo.get(i).getFirstName().equals(spectatorInfo.getFirstName())){
                      selectedInfo.remove(i);
                      countChecked=countChecked-1;
                      break;
                  }

              }
                spectatorInfo.setChecked(false);
              holder.cbSelectSpectator.setChecked(false);


          }


          mItemClickListener.OnItemSpectatorListClick(selectedInfo,position);

      });





/*        holder.cbSelectSpectator.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {

                if (isChecked){

                    if (countChecked>=countTicket){
                        showDialogError();
                        holder.cbSelectSpectator.setChecked(false);
                    }else {
                        selectedInfo.add(countChecked,new SpectatorInfoModel(spectatorInfo.getFirstName(), spectatorInfo.getLastName(), spectatorInfo.getNationalCode()));
                        countChecked = countChecked + 1;
                        spectatorInfo.setChecked(true);

                        mItemClickListener.OnItemSpectatorListClick(selectedInfo,position);

                    }
                }else{
                    for (int i = 0; i <selectedInfo.size() ; i++) {
                        if (selectedInfo.get(i).getFirstName().equals(spectatorInfo.getFirstName())){
                            selectedInfo.remove(i);
                            countChecked=countChecked-1;
                            break;
                        }

                    }
                 //   spectatorInfo.setChecked(false);


                }


                mItemClickListener.OnItemSpectatorListClick(selectedInfo,position);


                *//*
                if (isChecked)
                {

                    if (countChecked>=countTicket){
                        showDialogError();
                        holder.cbSelectSpectator.setChecked(false);
                    }else {
                        selectedInfo.add(position, new SpectatorInfoModel(spectatorInfo.getFirstName(), spectatorInfo.getLastName(), spectatorInfo.getNationalCode()));
                        countChecked = countChecked + 1;
                    }

                } else
                {
                    selectedInfo.remove(position);
                    countChecked=countChecked-1;
*//*
            //    }



            }
        });*/
        //  mItemClickListener.OnItemRelatedAlbumsClick(view, recentItem);
    }

    private void showDialogError() {

        String txtError = "تعداد تماشاگر انتخاب شده ی شما بیش از تعداد " + countTicket + " بلیت میباشد.";

        MessageAlertDialog dialog = new MessageAlertDialog(((Activity) context), "",txtError, false,
                MessageAlertDialog.TYPE_ERROR, new MessageAlertDialog.OnConfirmListener()
        {
            @Override
            public void onConfirmClick()
            {


            }

            @Override
            public void onCancelClick()
            {

            }
        });

        dialog.setCancelable(false);
        dialog.show(((Activity) context).getFragmentManager(), "messageDialog");
    }


    @Override
    public int getItemCount()
    {

        return spectatorInfoResponse.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView tvSpectatorInfo;
        public CheckBox cbSelectSpectator;
        public FrameLayout llItemMyBill;

        public ViewHolder(View v)
        {
            super(v);
            tvSpectatorInfo = v.findViewById(R.id.tvSpectatorInfo);
            cbSelectSpectator = v.findViewById(R.id.cbSelectSpectator);
            llItemMyBill = v.findViewById(R.id.llItemMyBill);

        }
    }

    public interface OnItemSpectatorListClickListener
    {

        void OnItemSpectatorListClick(ArrayList<SpectatorInfoModel> selectedInfo,Integer position);
    }

}