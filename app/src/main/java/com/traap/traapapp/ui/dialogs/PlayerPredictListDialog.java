package com.traap.traapapp.ui.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.rxbinding3.widget.RxTextView;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.predict.predictSystem.getMainPredict.PlayerItem;
import com.traap.traapapp.ui.adapters.predict.predictSystem.PredictPlayerListAdapter;
import com.traap.traapapp.ui.fragments.predict.predictSystemTeam.PredictSystemActionView;
import com.traap.traapapp.utilities.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


@SuppressLint("ValidFragment")
public class PlayerPredictListDialog extends DialogFragment
{
    private CompositeDisposable disposable;
    private final int DELAY_TIME_TEXT_CHANGE = 200;
    private Context context;

    private RecyclerView recyclerView;
    private EditText edtSearch;
    private PredictPlayerListAdapter adapter;

    private List<PlayerItem> playerItemList, filteredPlayerItemList;
    private int positionId, rowPosition, columnPosition;

    private Dialog dialog;
    private Activity activity;
    private PredictSystemActionView actionView;


    public PlayerPredictListDialog(Activity activity, int rowPosition, int columnPosition, int positionId, List<PlayerItem> playerItemList, PredictSystemActionView actionView)
    {
        this.activity = activity;
        this.actionView = actionView;
        this.playerItemList = playerItemList;
        this.positionId = positionId;
        this.rowPosition = rowPosition;
        this.columnPosition = columnPosition;
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
        dialog = new Dialog(activity, R.style.MyAlertDialogStyle);
        dialog.setContentView(R.layout.dialog_player_list);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        disposable = new CompositeDisposable();

        edtSearch = dialog.findViewById(R.id.edtSearch);
        recyclerView = dialog.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        adapter = new PredictPlayerListAdapter(context, playerItemList, playerItem ->
        {
            actionView.onSelectPlayerFromDialog(positionId, playerItem, rowPosition, columnPosition);
            dismiss();
        });
        recyclerView.setAdapter(adapter);

       disposable.add(RxTextView.textChanges(edtSearch)
               .skipInitialValue()
               .filter(charSequence ->
               {
                   Logger.e("-text-", charSequence.length() + ": " + charSequence);
                   if (charSequence.length() < 3)
                   {
                       adapter = new PredictPlayerListAdapter(context, playerItemList, playerItem -> actionView.onSelectPlayerFromDialog(positionId, playerItem, rowPosition, columnPosition));
                       recyclerView.setAdapter(adapter);
                   }

                   return charSequence.length() > 2;
               })
               .debounce(DELAY_TIME_TEXT_CHANGE, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io())
               //--------------------------
               .flatMap(new Function<CharSequence, ObservableSource<PlayerItem>>()
               {
                   @Override
                   public ObservableSource<PlayerItem> apply(CharSequence charSequence) throws Exception
                   {
                       return geFilteredPlayerListObservable(charSequence);
                   }
               })
               //--------------------------
               .observeOn(AndroidSchedulers.mainThread())
               .subscribeWith(getFilteredPlayerList())
       );

        return dialog;
    }

    private ObservableSource<PlayerItem> geFilteredPlayerListObservable(CharSequence charSequence)
    {
        filteredPlayerItemList = new ArrayList<>();
        Observable<PlayerItem> observable = Observable.fromIterable(playerItemList)
                .filter(playerItem -> playerItem.getFullName().contains(charSequence))
                .subscribeOn(Schedulers.io());

        if (observable.toList().blockingGet().size() == 0)
        {
            Logger.e("-Observable-", "List is Empty");
        }

        return observable;
    }

    private DisposableObserver<PlayerItem> getFilteredPlayerList()
    {
//        recyclerView = dialog.findViewById(R.id.recyclerView);
        filteredPlayerItemList = new ArrayList<>();

        return new DisposableObserver<PlayerItem>()
        {
            @Override
            public void onNext(PlayerItem playerItem)
            {
                filteredPlayerItemList.add(playerItem);
                Logger.e("--searchCategory--", "Search query: " + playerItem.getFullName());
                Logger.e("--searchCategory--", "Query size: " + filteredPlayerItemList.size());

                adapter = new PredictPlayerListAdapter(context, filteredPlayerItemList, filteredPlayerItem -> actionView.onSelectPlayerFromDialog(positionId, filteredPlayerItem, rowPosition, columnPosition));
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(Throwable e) { Logger.e("--search--", "showErrorMessage: " + e.getMessage()); }

            @Override
            public void onComplete() { Logger.e("--search--", "onComplete"); }
        };
    }

    @Override
    public void onDestroyView()
    {
        disposable.dispose();
        super.onDestroyView();
    }
}
