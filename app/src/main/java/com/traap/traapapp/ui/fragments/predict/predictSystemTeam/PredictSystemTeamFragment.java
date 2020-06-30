package com.traap.traapapp.ui.fragments.predict.predictSystemTeam;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.predict.predictSystem.getMainPredict.FormationItem;
import com.traap.traapapp.apiServices.model.predict.predictSystem.getMainPredict.GetMainPredictSystemResponse;
import com.traap.traapapp.apiServices.model.predict.predictSystem.getMainPredict.PlayerItem;
import com.traap.traapapp.apiServices.model.predict.predictSystem.getSystem.response.GetPredictSystemFromIdResponse;
import com.traap.traapapp.apiServices.model.predict.predictSystem.sendPredictPlayers.request.PlayerPositioning;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.adapters.predict.predictSystem.PredictPlayerItemListAdapter;
import com.traap.traapapp.ui.adapters.predict.predictSystem.PredictPlayerRowListAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.ui.dialogs.PlayerPredictListDialog;
import com.traap.traapapp.ui.fragments.predict.PredictActionView;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;
import com.wang.avi.AVLoadingIndicatorView;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import io.reactivex.disposables.CompositeDisposable;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;


@SuppressLint("ValidFragment")
public class PredictSystemTeamFragment extends BaseFragment implements PredictPlayerItemListAdapter.OnPositionItemClick, OnAnimationEndListener,
        OnServiceStatus<WebServiceClass<GetMainPredictSystemResponse>>, PredictSystemActionView, PredictGetFormationContentImpl.onGetFormationContentListener
{
    private CompositeDisposable disposable;
    private List<FormationItem> formationItemList;
    private List<PlayerItem> allPlayerList;
    private CircularProgressButton btnConfirm;

    private List<PlayerPositioning> playerPositioningList;
    private List<Integer> playerRequestList;

    private List<List<GetPredictSystemFromIdResponse>> formationContentNestedList;
    private int defaultFormationId;

    private View rootView;
    private PredictActionView mainView;
    private TextView tvAwayHeader, tvHomeHeader, tvMatchDate;
    private ImageView imgHomeHeader, imgAwayHeader, imgCupLogo, imgBackground;
    private LinearLayout llPredict;
    private FloatingActionButton fabSelectSystem;

    private RelativeLayout rlConfirm;

    private AVLoadingIndicatorView progressImageBackground;

    private Spinner spSystem;
    private ArrayList<String> formationStrList;

    private int rcSystemTeamWidth = 0;
    private int rcSystemTeamHeight = 0;

    private Integer matchId;
    private Boolean isPredictable;
    private Context context;

    private PredictPlayerRowListAdapter adapter;
    private RecyclerView rcSystemTeam;


    public PredictSystemTeamFragment()
    {

    }

    public static PredictSystemTeamFragment newInstance(PredictActionView mainView, Integer matchId, Boolean isPredictable)
    {
        PredictSystemTeamFragment f = new PredictSystemTeamFragment();
        f.setMainView(mainView);

        Bundle arg = new Bundle();
        arg.putInt("matchId", matchId);
        arg.putBoolean("isPredictable", isPredictable);

        f.setArguments(arg);

        return f;
    }

    private void setMainView(PredictActionView mainView)
    {
        this.mainView = mainView;
    }


    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            matchId = getArguments().getInt("matchId");
            isPredictable = getArguments().getBoolean("isPredictable");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_predict_system_team, container, false);

        disposable = new CompositeDisposable();

        initView();

        return rootView;
    }


    public void initView()
    {
        spSystem = rootView.findViewById(R.id.spSystem);
        rcSystemTeam = rootView.findViewById(R.id.rcSystemTeam);

        rlConfirm = rootView.findViewById(R.id.rlConfirm);
        btnConfirm = rootView.findViewById(R.id.btnConfirm);
        imgBackground = rootView.findViewById(R.id.imgBackground);

        tvMatchDate = rootView.findViewById(R.id.tvMatchDate);

        progressImageBackground = rootView.findViewById(R.id.progressImageProfile);

        tvAwayHeader = rootView.findViewById(R.id.tvAwayHeader);
        tvHomeHeader = rootView.findViewById(R.id.tvHomeHeader);

        imgAwayHeader = rootView.findViewById(R.id.imgAwayHeader);
        imgHomeHeader = rootView.findViewById(R.id.imgHomeHeader);

        imgCupLogo = rootView.findViewById(R.id.imgCupLogo);

        llPredict = rootView.findViewById(R.id.llPredict);
        fabSelectSystem = rootView.findViewById(R.id.fabSelectSystem);

        fabSelectSystem.setImageBitmap(textAsBitmap("ثبت", 14f, Color.WHITE));

        //-------------------------------get dimension------------------------------------
        ViewTreeObserver vto = rcSystemTeam.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {
                rcSystemTeam.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                rcSystemTeamWidth = rcSystemTeam.getMeasuredWidth();
                rcSystemTeamHeight = rcSystemTeam.getMeasuredHeight();

                Log.e("--dimension--", "width: " + rcSystemTeamWidth + ", height: " + rcSystemTeamHeight);
            }
        });
        //-------------------------------get dimension------------------------------------

        playerPositioningList = new ArrayList<>();
        playerRequestList = new ArrayList<>();

        rcSystemTeam.setLayoutManager(new LinearLayoutManager(context));
        rcSystemTeam.setNestedScrollingEnabled(false);

        SingletonService.getInstance().getPredictService().getMainPredictSystem(matchId, this);

        btnConfirm.setText("ثبت پیش\u200cبینی");

        btnConfirm.setOnClickListener(v ->
        {
            if (playerPositioningList.size() != 11)
            {
                Logger.e("== players size", "size: " + playerPositioningList.size());
                showAlertFailure(context, "برای ثبت پیش\u200cبینی لازم است 11 بازیکن انتخاب گردد. ", getString(R.string.error), false);
            }
            else
            {
                SendPlayersWithPosition();
            }
//            else
//            {
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
//                {
//                    if (playerRequestList.stream().distinct().collect(Collectors.toList()).size() == 11)
//                    {
                        //call API
//                        showDebugToast((Activity) context, "call API 1");
//                        SendPlayersWithPosition();
//                    }
//                    else
//                    {
//                        showAlertFailure(context, "برای ثبت پیش\u200cبینی انتخاب بازیکن تکراری مجاز نیست. ", getString(R.string.error), false);
//                    }
//                }
//                else
//                {
//                    if (new HashSet<>(playerRequestList).size() == 11)
//                    {
//                        //call API
////                        showDebugToast((Activity) context, "call API 2");
//                        SendPlayersWithPosition();
//                    }
//                    else
//                    {
//                        showAlertFailure(context, "برای ثبت پیش\u200cبینی انتخاب بازیکن تکراری مجاز نیست. ", getString(R.string.error), false);
//                    }
//                }

//            }
        });
    }

    private void SendPlayersWithPosition()
    {
        btnConfirm.startAnimation();
        btnConfirm.setClickable(false);

        PredictSendPlayersWithPositionImpl.SendPlayersWithPosition(
                matchId,
                formationItemList.get(spSystem.getSelectedItemPosition()).getFormationId(),
                playerPositioningList, new PredictSendPlayersWithPositionImpl.onSendPlayersWithPositionListener()
                {
                    @Override
                    public void onSendPlayersWithPositionCompleted(String message)
                    {
                        btnConfirm.revertAnimation();
                        btnConfirm.setClickable(true);
                        showAlertSuccess(context, message, "", true);
                    }

                    @Override
                    public void onSendPlayersWithPositionError(String message)
                    {
                        btnConfirm.revertAnimation();
                        btnConfirm.setClickable(true);
                        showAlertFailure(context, message, "", false);
                    }
                });
    }

    public static Bitmap textAsBitmap(String text, float textSize, int textColor)
    {
        Paint paint = new Paint(ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.0f); // round
        int height = (int) (baseline + paint.descent() + 0.0f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }


    @Override
    public void onPositionClick(int positionId, int playerId, int rowPosition, int columnPosition)
    {
        Logger.e("--- onPositionClick row:column ---", rowPosition + ":" + columnPosition);
        PlayerPredictListDialog dialog = new PlayerPredictListDialog((Activity) context, rowPosition, columnPosition, positionId, allPlayerList, this);
        dialog.show(((Activity) context).getFragmentManager(), "playerPredictListDialog");
    }

    @Override
    public void onSelectPlayerFromDialog(int positionId, PlayerItem playerItem, int rowPosition, int columnPosition)
    {
        Logger.e("--- onSelectPlayer row:column ---", rowPosition + ":" + columnPosition);
        formationContentNestedList.get(rowPosition).get(columnPosition).setPlayer(playerItem);

        adapter = new PredictPlayerRowListAdapter(context, matchId, formationContentNestedList, rcSystemTeamWidth / 5, this);
        rcSystemTeam.setAdapter(adapter);

        addPlayerRequest(positionId, playerItem.getPlayerId());
    }

    private void addPlayerRequest(int positionId, int playerId)
    {
        if (!playerPositioningList.isEmpty())
        {
            if (isPosIdAvailable(positionId) == -1)
            {
                Logger.e("--isPosIdAvailable--", "== -1");
                playerPositioningList.add(new PlayerPositioning(playerId, positionId));
                playerRequestList.add(playerId);
            }
            else
            {
                Logger.e("--isPosIdAvailable--", "founded");
                try
                {
                    PlayerPositioning playerItem = playerPositioningList.get(playerPositioningList.indexOf(searchPlayerPos(positionId)));
                    playerItem.setPlayerId(playerId);
                    playerItem.setPositionId(positionId);


//                    if(playerPositioningList.remove(searchPlayerPos(positionId)))
////                    if(playerPositioningList.remove(new PlayerPositioning(playerId, positionId)))
//                    {
//                        Logger.e("--remove item--", "successful");
//                        playerPositioningList.add(new PlayerPositioning(playerId, positionId));
//                    }
//                    else
//                    {
//                        Logger.e("--remove item--", "failure");
//                    }
                }
                catch (ArrayIndexOutOfBoundsException e)
                {
//                    playerPositioningList.add(new PlayerPositioning(playerId, positionId));
//                    playerRequestList.add(playerId);
                }
            }
        }
        else
        {
            playerPositioningList.add(new PlayerPositioning(playerId, positionId));
            playerRequestList.add(playerId);
        }
    }

    private int isPosIdAvailable(int positionId)
    {
        @Nullable
        PlayerPositioning findItem = searchPlayerPos(positionId);

        return findItem != null ? playerPositioningList.indexOf(findItem) : -1;
    }

    private PlayerPositioning searchPlayerPos(int positionId)
    {
        PlayerPositioning findItem;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            findItem = playerPositioningList.stream().filter(player -> player.getPositionId() == positionId).findAny().orElse(null);
        }
        else
        {
            findItem = Iterables.tryFind(playerPositioningList, player -> player.getPositionId() == positionId).orNull();
        }

        if (findItem == null)
        {
            Logger.e("== findItem ==", "null, index: -1");
        }
        else
        {
            Logger.e("== findItem ==", "index: " + playerPositioningList.indexOf(findItem));
        }
        return findItem;
    }

    @Override
    public void onReady(WebServiceClass<GetMainPredictSystemResponse> response)
    {
        mainView.hideLoading();
        if (response == null || response.data == null)
        {
            showErrorAndBack("خطا در دریافت اطلاعات از سرور!");
            Logger.e("-GetPredictResponse-", "null");
            return;
        }
        if (response.info.statusCode != 200)
        {
            showErrorAndBack(response.info.message);
        }
        else
        {
            int defaultFormationPos = 0;
            defaultFormationId = response.data.getDefaultFormationId();
            formationItemList = response.data.getFormationItemList();
            allPlayerList = response.data.getAllPlayers();

            tvMatchDate.setText(response.data.getMatch().getMatchDatetimeStr());
            tvAwayHeader.setText(response.data.getMatch().getAwayTeam().getTeamName());
            tvHomeHeader.setText(response.data.getMatch().getHomeTeam().getTeamName());

            setImageIntoIV(imgAwayHeader, response.data.getMatch().getAwayTeam().getTeamLogo());
            setImageIntoIV(imgHomeHeader, response.data.getMatch().getHomeTeam().getTeamLogo());
            setImageIntoIV(imgCupLogo, response.data.getMatch().getCup().getCupLogo());

            Picasso.with(context).load(response.data.getBackgroundImage()).into(imgBackground);

            formationStrList = new ArrayList<>();

            for (FormationItem formationItem: formationItemList)
            {
                formationStrList.add(formationItem.getTitle());
                if (defaultFormationId != 0 && defaultFormationId == formationItem.getFormationId())
                {
                    defaultFormationPos = formationItemList.indexOf(formationItem);
                }
            }

            Logger.e("--systemStrList--", "size: " + formationStrList.size());

            ArrayAdapter<String> adapterGenderStrList = new ArrayAdapter<String>(context, R.layout.my_spinner_item_system, formationStrList);
            adapterGenderStrList.setDropDownViewResource(R.layout.custom_spinner_dropdown_item_system);
            spSystem.setAdapter(adapterGenderStrList);
            spSystem.setSelection(defaultFormationPos);

            spSystem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {
                    Logger.e("== onItemSelected ==", "Pos: " + position + ", system: " + formationStrList.get(position));
                    getFormationContentSelected(formationItemList.get(position).getFormationId());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent)
                {

                }
            });
        }
    }

    @Override
    public void onError(String message)
    {
        mainView.hideLoading();
        Logger.e("-showErrorMessage-", "Error: " + message);

        if (Tools.isNetworkAvailable((Activity) context))
        {
            showErrorAndBack("خطا در دریافت اطلاعات از سرور!");
        }
        else
        {
            showErrorAndBack(getString(R.string.networkErrorMessage));
        }
    }

    private void getFormationContentSelected(int formationId)
    {
        progressImageBackground.setVisibility(View.VISIBLE);

        PredictGetFormationContentImpl.GetFormationContent(matchId, formationId, this);
    }

    private void setImageIntoIV(ImageView imageView, String link)
    {
        try
        {
            Picasso.with(context).load(link).into(imageView, new Callback()
            {
                @Override
                public void onSuccess() { }

                @Override
                public void onError()
                {
                    Picasso.with(context).load(R.drawable.img_failure).into(imageView);
                }
            });
        }
        catch (Exception e)
        {
            Logger.e("-Load Image-", "" + e.getMessage());
            e.printStackTrace();
            Picasso.with(context).load(R.drawable.img_failure).into(imageView);
        }
    }

    private void showErrorAndBack(String message)
    {
        MessageAlertDialog dialog = new MessageAlertDialog((Activity) context, getResources().getString(R.string.error),
                message, false, MessageAlertDialog.TYPE_ERROR, new MessageAlertDialog.OnConfirmListener()
        {
            @Override
            public void onConfirmClick()
            {
                getActivity().onBackPressed();
            }

            @Override
            public void onCancelClick()
            {

            }
        });
        dialog.show(((Activity) context).getFragmentManager(), "dialog");
    }

    @Override
    public void onGetFormationContentCompleted(List<List<GetPredictSystemFromIdResponse>> response)
    {
        progressImageBackground.setVisibility(View.GONE);

        formationContentNestedList = response;

        adapter = new PredictPlayerRowListAdapter(context, matchId, formationContentNestedList, rcSystemTeamWidth / 5, this);
        rcSystemTeam.setAdapter(adapter);
        adapter.GetAllItemHeight(heightItems ->
        {
            int rcHeight = 0;
            for (int height: heightItems)
            {
                rcHeight += height;
            }

            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, rcHeight);
            Logger.e("--rcSystemTeam height--", "height: " + rcHeight);
            rcSystemTeam.setLayoutParams(params);
        });
    }

    @Override
    public void onGetFormationContentError(String message)
    {
        progressImageBackground.setVisibility(View.GONE);
        showAlertFailure(context, message, getResources().getString(R.string.error), false);
    }

    @Override
    public void onAnimationEnd()
    {
        btnConfirm.setBackground(ContextCompat.getDrawable(SingletonContext.getInstance().getContext(), R.drawable.background_border_red));
    }

    @Override
    public void onDestroyView()
    {
        disposable.dispose();
        super.onDestroyView();
    }

}
