package com.traap.traapapp.ui.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.lottery.GetLotteryWinnerListResponse;
import com.traap.traapapp.apiServices.model.lottery.Winner;
import com.traap.traapapp.ui.adapters.lotteryWinnerList.LotteryPredictGeneralAdapter;
import com.traap.traapapp.ui.fragments.predict.predictResult.PredictResultActionView;
import com.traap.traapapp.utilities.Logger;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import io.reactivex.Observable;

/**
 * Created by Javad.Abadi on 6/24/2019.
 */
@SuppressLint("ValidFragment")
public class LotteryWinnerListDialog extends DialogFragment
{
    private View rootView;
    private Context context;

    private List<Winner> winnerList;

    private Dialog dialog;
    private Integer matchId;

    private ImageView imageView;
    private AVLoadingIndicatorView progress;
    private RelativeLayout rlProgress;
    private LinearLayout llContent;
    private CircularProgressButton btnBack, btnDetails;

    private RecyclerView recyclerView;
    private PredictResultActionView actionView;

    public LotteryWinnerListDialog(Integer matchId, PredictResultActionView actionView)
    {
        this.matchId = matchId;
        this.actionView = actionView;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        dialog = new Dialog(context, R.style.MyAlertDialogStyle);
        dialog.setContentView(R.layout.dialog_predict_win_list);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        imageView = dialog.findViewById(R.id.image);
        rlProgress = dialog.findViewById(R.id.rlProgress);
        llContent = dialog.findViewById(R.id.llContent);
        progress = dialog.findViewById(R.id.progress);
        btnBack = dialog.findViewById(R.id.btnBack);
        btnDetails = dialog.findViewById(R.id.btnDetails);
        recyclerView = dialog.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

//        progress.show();
        rlProgress.setVisibility(View.VISIBLE);
        llContent.setVisibility(View.GONE);

        getData();

        btnBack.setOnClickListener(v ->
        {
            dismiss();
        });

        btnDetails.setOnClickListener(v ->
        {
            actionView.onShowDetailWinnerList(winnerList);
            dismiss();
        });

        return dialog;
    }

    private void getData()
    {
        //getData
        //hide progress
        //show image
        //show List with adapter
        SingletonService.getInstance().getLotteryWinnerListService().getLotteryWinnerListService(matchId, new OnServiceStatus<WebServiceClass<GetLotteryWinnerListResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<GetLotteryWinnerListResponse> response)
            {
//                progress.hide();
                rlProgress.setVisibility(View.GONE);
//                llContent.setVisibility(View.VISIBLE);
                if (response.data.equals(null))
                {
                    //showAlert and dismiss
                    dismiss();
                    actionView.showAlertFailure("خطا در دریافت اطلاعات از سرور!");
                }
                else if (response.info.statusCode != 200)
                {
                    //showAlert and dismiss
                    dismiss();
                    actionView.showAlertFailure(response.info.message);
                }
                else
                {
                    setImageBackground(imageView, response.data.getImageLink());

                    if (response.data.getIsFinal())
                    {
                        dialog.findViewById(R.id.space).setVisibility(View.VISIBLE);
                        btnDetails.setVisibility(View.VISIBLE);
                    }

                    winnerList = response.data.getWinnerList();

                    List<Winner> generalWinnerList= new ArrayList<>();
                    Observable.fromIterable(winnerList)
                            .filter(winner -> winnerList.indexOf(winner) < 3)
                            .toList()
                            .doOnSuccess(winners ->
                            {
                                generalWinnerList.addAll(winners);
                                Logger.e("-generalWinnerList-", "Size: " + generalWinnerList.size());
                                recyclerView.setAdapter(new LotteryPredictGeneralAdapter(context, generalWinnerList, response.data.getIsFinal()));
                            })
                            .subscribe();
                }

            }

            @Override
            public void onError(String message)
            {
//                progress.hide();
                rlProgress.setVisibility(View.GONE);
                dismiss();
                actionView.showAlertFailure("خطا در دریافت اطلاعات از سرور!");

            }
        });
    }

    private void setImageBackground(ImageView imageView, String link)
    {
        rlProgress.setVisibility(View.VISIBLE);

        try
        {
            Picasso.with(context).load(Uri.parse(link)).into(imageView, new Callback()
            {
                @Override
                public void onSuccess()
                {
                    rlProgress.setVisibility(View.GONE);
                    llContent.setVisibility(View.VISIBLE);
                }

                @Override
                public void onError()
                {
                    Picasso.with(context).load(R.drawable.img_failure).into(imageView);
                    rlProgress.setVisibility(View.GONE);
                    llContent.setVisibility(View.VISIBLE);
                }
            });
        }
        catch (NullPointerException e)
        {
            Picasso.with(context).load(R.drawable.img_failure).into(imageView);
            rlProgress.setVisibility(View.GONE);
            llContent.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog)
    {
        super.onDismiss(dialog);
    }


}
