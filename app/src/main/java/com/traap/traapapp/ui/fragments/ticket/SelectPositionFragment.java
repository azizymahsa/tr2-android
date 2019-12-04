package com.traap.traapapp.ui.fragments.ticket;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.TransactionTooLargeException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.models.otherModels.ticket.StadiumPositionModel;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getAllBoxes.AllBoxesResult;
import com.traap.traapapp.apiServices.model.getAllBoxes.GetAllBoxesRequest;
import com.traap.traapapp.apiServices.model.getAllBoxes.GetAllBoxesResponse;
import com.traap.traapapp.apiServices.model.matchList.MatchItem;
import com.traap.traapapp.apiServices.model.reservationmatch.ReservationResponse;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.ticket.selectposition.ReservationMatchImpl;
import com.traap.traapapp.ui.fragments.ticket.selectposition.ReservationMatchInteractor;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;
import library.android.eniac.utility.Utility;

public class SelectPositionFragment
        extends BaseFragment implements View.OnClickListener, ReservationMatchInteractor.OnFinishedReservationListener
{
    private List<AllBoxesResult> newResult;

    ColorPickerView colorPickerView;
    ImageView ivDefault, ivSelected;
    RelativeLayout rlImageViewsFull;
    private static final String KEY_MODEL = "KEY_MODEL";
    private TextView tvTitle, tvAmountStation, tvAmountForPay, tvStadiumName, tvDateTime, tvCount, tvM, tvP,tvHome,tvGuest;
    private OnListFragmentInteractionListener interactionListener;
    private ArrayList<String> allBoxes;
    private Spinner spinnerAllBoxes;
    private Integer selectPositionId = 1;
    private Integer selectedIndex = 0;
    private View btnBackToDetail;
    private View btnPaymentConfirm, llSliderItemMatch;
    private OnClickContinueBuyTicket onClickContinueBuyTicketListener;
    private List<AllBoxesResult> allBoxesResponse;
    ArrayList<StadiumPositionModel> stadiumPositionModels = new ArrayList<>();
    private int amountForPay;
    private ImageView imgView, imgHost, imgGuest,imgBackground;
    private ImageView imgViewSelected;
    private int count = 1;
    private Integer matchId = 1;
    private Integer amountOneTicket = 0;
    private ArrayList<Integer> positionIdAllBoxes;
    private MatchItem matchBuyable;
    private int positionId, positionIdFromServer;
    private String positionName;
    private ReservationMatchImpl reservationMatch;
    private Integer stadiumId=1;
    private String selectPosition;
    private Handler handler;
    private Integer timeForRequestGetData=5000;
    private Runnable stadiumInfoRunnable;


    public SelectPositionFragment()
    {
    }

    /**
     * Receive the model list
     */
    public static SelectPositionFragment newInstance(String s, OnClickContinueBuyTicket onClickContinueBuyTicket, MatchItem matchBuyable)
    {
        SelectPositionFragment fragment = new SelectPositionFragment();
        fragment.setOnClickContinueBuyTicket(onClickContinueBuyTicket);
        Bundle args = new Bundle();
        try
        {
            args.putParcelable("matchBuyable", matchBuyable);

        } catch (Exception e)
        {

        }

        fragment.setArguments(args);

        return fragment;
    }

    private void setOnClickContinueBuyTicket(OnClickContinueBuyTicket onClickContinueBuyTicket)
    {
        this.onClickContinueBuyTicketListener = onClickContinueBuyTicket;
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        matchBuyable = getArguments().getParcelable("matchBuyable");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.select_position_fragment, container, false);
        Context context = view.getContext();
        reservationMatch = new ReservationMatchImpl();
        handler = new Handler();
        stadiumInfoRunnable = new Runnable() {
            @Override
            public void run() {
                BuyTicketsFragment.buyTicketsFragment.showLoading();

                getAllBoxesRequest();
            }
        };
        tvCount = view.findViewById(R.id.tvCount);
        tvM = view.findViewById(R.id.tvM);
        tvP = view.findViewById(R.id.tvP);
        tvStadiumName = view.findViewById(R.id.tvStadiumName);
        tvDateTime = view.findViewById(R.id.tvDateTime);
        imgBackground=view.findViewById(R.id.imgBackground);
        imgHost = view.findViewById(R.id.imgHost);
        tvHome=view.findViewById(R.id.tvHome);
        tvGuest=view.findViewById(R.id.tvGuest);
        imgGuest = view.findViewById(R.id.imgGuest);
        llSliderItemMatch = view.findViewById(R.id.llSliderItemMatch);

        spinnerAllBoxes = view.findViewById(R.id.spinnerAllBoxes);
        // btnBackToDetail=view.findViewById(R.id.btnBackToDetail);
        btnPaymentConfirm = view.findViewById(R.id.btnPaymentConfirm);
        tvAmountStation = view.findViewById(R.id.tvAmountStation);
        tvAmountForPay = view.findViewById(R.id.tvAmountForPay);
        setDataMatch();
        //btnBackToDetail.setOnClickListener(this);
        tvP.setOnClickListener(this);
        tvM.setOnClickListener(this);
        btnPaymentConfirm.setOnClickListener(this);

        colorPickerView = view.findViewById(R.id.colorPickerView);
        ivDefault = view.findViewById(R.id.ivDefault);
        ivSelected = view.findViewById(R.id.ivSelected);
        rlImageViewsFull = view.findViewById(R.id.rlImageViewsFull);


        setDataStadiumPosition();
        getAllBoxesRequest();


        return view;
    }

    private void setDataMatch()
    {

        try
        {
            llSliderItemMatch.setVisibility(View.VISIBLE);
            matchId = matchBuyable.getId();
            stadiumId = matchBuyable.getStadium().getId();
            tvStadiumName.setText(matchBuyable.getStadium().getName());
            setImageColor(imgHost, matchBuyable.getTeamHome().getLogo());
            setImageColor(imgGuest, matchBuyable.getTeamAway().getLogo());
            setImageColor(imgBackground,matchBuyable.getCup().getImageName());

            tvHome.setText(matchBuyable.getTeamHome().getName());
            tvGuest.setText(matchBuyable.getTeamAway().getName());
            //  tvDateTime.setText(getDate(matchBuyable.getMatchDatetime()));
            tvDateTime.setText(matchBuyable.getMatchDatetimeStr());

        } catch (Exception e)
        {
            Tools.showToast(getContext(), e.getMessage(), R.color.red);
            llSliderItemMatch.setVisibility(View.GONE);

        }

    }


    private void setImageColor(ImageView imageView, String link)
    {
        try
        {
            Picasso.with(SingletonContext.getInstance().getContext()).load(link).into(imageView, new Callback()
            {
                @Override
                public void onSuccess()
                {
                    // cvContent.setBackgroundColor(Color.TRANSPARENT);
                }

                @Override
                public void onError()
                {
                    Picasso.with(SingletonContext.getInstance().getContext()).load(R.drawable.img_failure).into(imageView);
                }
            });
        } catch (Exception e)
        {

        }
    }

    private void setDataStadiumPosition()
    {
        stadiumPositionModels.add(new StadiumPositionModel("FF328DAA", "1", true));
        stadiumPositionModels.add(new StadiumPositionModel("FF953D3D", "2", true));
        stadiumPositionModels.add(new StadiumPositionModel("FFFE9000", "3", true));
        stadiumPositionModels.add(new StadiumPositionModel("FFFFFC9B", "4", true));
        stadiumPositionModels.add(new StadiumPositionModel("FF00AC62", "5", true));

        stadiumPositionModels.add(new StadiumPositionModel("FF8A3D7D", "6", true));
        stadiumPositionModels.add(new StadiumPositionModel("FF9AB260", "7", true));
        stadiumPositionModels.add(new StadiumPositionModel("FFFF8181", "8", true));
        stadiumPositionModels.add(new StadiumPositionModel("FF0F0060", "9", true));
        stadiumPositionModels.add(new StadiumPositionModel("FFFFC170", "10", true));

        stadiumPositionModels.add(new StadiumPositionModel("FF00EDFF", "11", true));
        stadiumPositionModels.add(new StadiumPositionModel("FF481337", "12", true));
        stadiumPositionModels.add(new StadiumPositionModel("FF009A8F", "13", true));
        stadiumPositionModels.add(new StadiumPositionModel("FFFE0002", "14", true));
        stadiumPositionModels.add(new StadiumPositionModel("FF00FF5D", "15", true));

        stadiumPositionModels.add(new StadiumPositionModel("FFA0F113", "16", true));
        stadiumPositionModels.add(new StadiumPositionModel("FF8A4000", "17", true));
        stadiumPositionModels.add(new StadiumPositionModel("FF0080FF", "18", true));
        stadiumPositionModels.add(new StadiumPositionModel("FFDC0DB3", "19", true));
        stadiumPositionModels.add(new StadiumPositionModel("FF52488A", "20", true));

        stadiumPositionModels.add(new StadiumPositionModel("FFCFD574", "21", true));
        stadiumPositionModels.add(new StadiumPositionModel("FFA8CAEC", "22", true));
        stadiumPositionModels.add(new StadiumPositionModel("FF575657", "23", true));
        stadiumPositionModels.add(new StadiumPositionModel("FF8FC549", "24", true));
        stadiumPositionModels.add(new StadiumPositionModel("FF9A1955", "25", true));

        stadiumPositionModels.add(new StadiumPositionModel("FF8DFFFB", "26", true));
        stadiumPositionModels.add(new StadiumPositionModel("FFA29C00", "27", true));
        stadiumPositionModels.add(new StadiumPositionModel("FF00E600", "28", true));
        stadiumPositionModels.add(new StadiumPositionModel("FFD8B506", "29", true));
        stadiumPositionModels.add(new StadiumPositionModel("FFCF0000", "30", true));

        stadiumPositionModels.add(new StadiumPositionModel("FF948DFF", "31", true));
        stadiumPositionModels.add(new StadiumPositionModel("FFE7EC44", "32", true));
        stadiumPositionModels.add(new StadiumPositionModel("FFD97B00", "33", true));
        stadiumPositionModels.add(new StadiumPositionModel("FFC500FF", "34", true));
        stadiumPositionModels.add(new StadiumPositionModel("FF74FFD0", "35", true));

        stadiumPositionModels.add(new StadiumPositionModel("FF8E7627", "36", true));
        stadiumPositionModels.add(new StadiumPositionModel("FFAC0000", "37", true));
        stadiumPositionModels.add(new StadiumPositionModel("FF828282", "38", true));
        stadiumPositionModels.add(new StadiumPositionModel("FF6E00FF", "39", true));
        stadiumPositionModels.add(new StadiumPositionModel("FF9CE27F", "40", true));

        stadiumPositionModels.add(new StadiumPositionModel("FFFFBAFA", "41", true));
        stadiumPositionModels.add(new StadiumPositionModel("FFF237FF", "42", true));
        stadiumPositionModels.add(new StadiumPositionModel("FF440000", "43", true));

        /////CIP Up Down
        stadiumPositionModels.add(new StadiumPositionModel("FFA11919", "مهمان", true));

        stadiumPositionModels.add(new StadiumPositionModel("FFFFCC00", "CIP", true));

        stadiumPositionModels.add(new StadiumPositionModel("", "46", true));
        stadiumPositionModels.add(new StadiumPositionModel("", "47", true));
        stadiumPositionModels.add(new StadiumPositionModel("FFA11919", "میهمان", true));



        //////

    }

    private void handleSetStadiumLayouts()
    {
        colorPickerView.setColorListener((ColorEnvelopeListener) (envelope, fromUser) ->
        {
            for (StadiumPositionModel stadiomModel : stadiumPositionModels)
            {
                if (envelope.getHexCode().equals(stadiomModel.getColor()) && stadiomModel.isFull())
                {
                    showToast(getActivity(), "ظرفیت این جایگاه پر شده است", R.color.red);
                    return;
                }
                if (envelope.getHexCode().equals(stadiomModel.getColor()))
                {
                    positionId = stadiomModel.getId();
                }
            }


            switch (envelope.getHexCode())
            {
                case "FF328DAA":
                    setOnePositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(0).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(0).getNumber().toString());
                    break;
                case "FF953D3D":
                    setTowPositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(1).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(1).getNumber().toString());
                    break;
                case "FFFE9000":
                    setThreePositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(2).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(2).getNumber().toString());
                    break;
                case "FFFFFC9B":
                    setFourPositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(3).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(3).getNumber().toString());
                    break;
                case "FF00AC62":
                    setFivePositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(4).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(4).getNumber().toString());
                    break;
                /////5
                case "FF8A3D7D":
                    setSixPositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(5).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(5).getNumber().toString());
                    break;
                case "FF9AB260":
                    setSevenPositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(6).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(6).getNumber().toString());
                    break;
                case "FFFF8181":
                    setEightPositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(7).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(7).getNumber().toString());
                    break;
                case "FF0F0060":
                    setNinePositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(8).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(8).getNumber().toString());
                    break;
                case "FFFFC170":
                    setTenPositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(9).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(9).getNumber().toString());
                    break;
                ////////10
                case "FF00EDFF":
                    setElevenPositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(10).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(10).getNumber().toString());
                    break;
                case "FF481337":
                    setTwelvePositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(11).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(11).getNumber().toString());
                    break;
                case "FF009A8F":
                    setThirteenPositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(12).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(12).getNumber().toString());
                    break;
                case "FFFE0002":
                    setFourteenPositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(13).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(13).getNumber().toString());
                    break;
                case "FF00FF5D":
                    setFiveteenPositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(14).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(14).getNumber().toString());
                    break;
                ////15
                case "FFA0F113":
                    setSixteenPositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(15).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(15).getNumber().toString());
                    break;
                case "FF8A4000":
                    setSeventeenPositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(16).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(16).getNumber().toString());
                    break;
                case "FF0080FF":
                    setEighteenPositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(17).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(17).getNumber().toString());
                    break;
                case "FFDC0DB3":
                    setNineteenPositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(18).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(18).getNumber().toString());
                    break;
                case "FF52488A":
                    setTwentyPositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(19).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(19).getNumber().toString());
                    break;
                ////20

                case "FFCFD574":
                    setTwentyonePositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(20).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(20).getNumber().toString());
                    break;
                case "FFA8CAEC":
                    setTwentytwoPositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(21).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(21).getNumber().toString());
                    break;
                case "FF575657":
                    setTwentythreePositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(22).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(22).getNumber().toString());
                    break;
                case "FF8FC549":
                    setTwentyfourPositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(23).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(23).getNumber().toString());
                    break;
                case "FF9A1955":
                    setTwentyfivePositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(24).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(24).getNumber().toString());
                    break;
                ///////25

                case "FF8DFFFB":
                    setTwentysixPositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(25).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(25).getNumber().toString());
                    break;
                case "FFA29C00":
                    setTwentysevenPositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(26).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(26).getNumber().toString());
                    break;
                case "FF00E600":
                    setTwentyeightPositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(27).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(27).getNumber().toString());
                    break;
                case "FFD8B506":
                    setTwentyninePositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(28).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(28).getNumber().toString());
                    break;
                case "FFCF0000":
                    setThirtyPositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(29).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(29).getNumber().toString());
                    break;
                //////30

                case "FF948DFF":
                    setThirtyonePositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(30).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(30).getNumber().toString());
                    break;
                case "FFE7EC44":
                    setThirtytwoPositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(31).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(31).getNumber().toString());
                    break;
                case "FFD97B00":
                    setThirtythreePositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(32).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(32).getNumber().toString());
                    break;
                case "FFC500FF":
                    setThirtyfourPositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(33).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(33).getNumber().toString());
                    break;
                case "FF74FFD0":
                    setThirtyfivePositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(34).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(34).getNumber().toString());
                    break;
                //////////35

                case "FF8E7627":
                    setThirtysixPositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(35).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(35).getNumber().toString());
                    break;
                case "FFAC0000":
                    setThirtysevenPositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(36).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(36).getNumber().toString());
                    break;
                case "FF828282":
                    setThirtyeightPositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(37).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(37).getNumber().toString());
                    break;
                case "FF6E00FF":
                    setThirtyninePositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(38).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(38).getNumber().toString());
                    break;
                case "FF9CE27F":
                    setfourtyPositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(39).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(39).getNumber().toString());
                    break;
                //////40

                case "FFFFBAFA":
                    setfourtyonePositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(40).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(40).getNumber().toString());
                    break;
                case "FFF237FF":
                    setfourtytwoPositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(41).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(41).getNumber().toString());
                    break;
                case "FF440000":
                    setfourtythreePositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(42).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(42).getNumber().toString());
                    break;

                case "FFA11919":
                    setGuestPositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(43).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(43).getNumber().toString());
                    break;

                case "FFFFCC00":
                    setCIPPositionSelected();
                    setSpinnerPositionSelected(stadiumPositionModels.get(44).getNumber());
                    selectedIndex = allBoxes.indexOf(stadiumPositionModels.get(44).getNumber().toString());
                    break;
               /* case "FF0F0060":
                    ivSelected.setImageResource(R.drawable.ic_fourteen_full);
                    ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    break;
                case "":
                    ivSelected.setImageResource(R.drawable.ic_fourteen_full);
                    ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    break;*/
                //////////45
            }

            Logger.e("tesstt", envelope.getHexCode() + "");
        });
    }

    private void setCIPPositionSelected()
    {
        selectPositionId = 45;
        ivSelected.setImageResource(R.drawable.ic_cip_selected);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setfourtythreePositionSelected()
    {
        selectPositionId = 43;
        ivSelected.setImageResource(R.drawable.ic_selected_fourty_three);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setGuestPositionSelected()
    {
        selectPositionId = 44;
        ivSelected.setImageResource(R.drawable.ic_guest_selected);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setfourtytwoPositionSelected()
    {
        selectPositionId = 42;
        ivSelected.setImageResource(R.drawable.ic_selected_fourty_two);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setfourtyonePositionSelected()
    {
        selectPositionId = 41;
        ivSelected.setImageResource(R.drawable.ic_selected_fourty_one);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setfourtyPositionSelected()
    {
        selectPositionId = 40;
        ivSelected.setImageResource(R.drawable.ic_selected_fourty);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setThirtyninePositionSelected()
    {
        selectPositionId = 39;
        ivSelected.setImageResource(R.drawable.ic_selected_thirty_nine);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setThirtyeightPositionSelected()
    {
        selectPositionId = 38;
        ivSelected.setImageResource(R.drawable.ic_selected_thirty_eight);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setThirtysevenPositionSelected()
    {
        selectPositionId = 37;
        ivSelected.setImageResource(R.drawable.ic_selected_thirty_seven);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setThirtysixPositionSelected()
    {
        selectPositionId = 36;
        ivSelected.setImageResource(R.drawable.ic_selected_thirty_six);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setThirtyfivePositionSelected()
    {
        selectPositionId = 35;
        ivSelected.setImageResource(R.drawable.ic_selected_thirty_five);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setThirtyfourPositionSelected()
    {
        selectPositionId = 34;
        ivSelected.setImageResource(R.drawable.ic_selected_thirty_four);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setThirtythreePositionSelected()
    {
        selectPositionId = 33;
        ivSelected.setImageResource(R.drawable.ic_selected_thirty_three);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setThirtytwoPositionSelected()
    {
        selectPositionId = 32;
        ivSelected.setImageResource(R.drawable.ic_selected_thirty_two);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setThirtyonePositionSelected()
    {
        selectPositionId = 31;
        ivSelected.setImageResource(R.drawable.ic_selected_thirty_one);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setThirtyPositionSelected()
    {
        selectPositionId = 30;
        ivSelected.setImageResource(R.drawable.ic_selected_thirty);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);
    }

    private void setTwentyninePositionSelected()
    {
        selectPositionId = 29;
        ivSelected.setImageResource(R.drawable.ic_selected_twenty_nine);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);
    }

    private void setTwentyeightPositionSelected()
    {
        selectPositionId = 28;
        ivSelected.setImageResource(R.drawable.ic_selected_twenty_eight);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setTwentysevenPositionSelected()
    {
        selectPositionId = 27;
        ivSelected.setImageResource(R.drawable.ic_selected_twenty_seven);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setTwentysixPositionSelected()
    {
        selectPositionId = 26;
        ivSelected.setImageResource(R.drawable.ic_selected_twenty_six);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setTwentyfivePositionSelected()
    {
        selectPositionId = 25;
        ivSelected.setImageResource(R.drawable.ic_selected_twenty_five);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setTwentyfourPositionSelected()
    {
        selectPositionId = 24;
        ivSelected.setImageResource(R.drawable.ic_selected_twenty_four);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setTwentythreePositionSelected()
    {
        selectPositionId = 23;
        ivSelected.setImageResource(R.drawable.ic_selected_twenty_three);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setTwentytwoPositionSelected()
    {
        selectPositionId = 22;
        ivSelected.setImageResource(R.drawable.ic_selected_twenty_two);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setTwentyonePositionSelected()
    {
        selectPositionId = 21;
        ivSelected.setImageResource(R.drawable.ic_selected_twenty_one);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setTwentyPositionSelected()
    {
        selectPositionId = 20;
        ivSelected.setImageResource(R.drawable.ic_selected_twenty);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setNineteenPositionSelected()
    {
        selectPositionId = 19;
        ivSelected.setImageResource(R.drawable.ic_selected_nineteen);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setEighteenPositionSelected()
    {
        selectPositionId = 18;
        ivSelected.setImageResource(R.drawable.ic_selected_eighteen);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setSeventeenPositionSelected()
    {
        selectPositionId = 17;
        ivSelected.setImageResource(R.drawable.ic_selected_seventeen);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setSixteenPositionSelected()
    {
        selectPositionId = 16;
        ivSelected.setImageResource(R.drawable.ic_selected_sixteen);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setFiveteenPositionSelected()
    {
        selectPositionId = 15;
        ivSelected.setImageResource(R.drawable.ic_selected_fifteen);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setFourteenPositionSelected()
    {
        selectPositionId = 14;
        ivSelected.setImageResource(R.drawable.ic_selected_fourteen);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setThirteenPositionSelected()
    {
        selectPositionId = 13;
        ivSelected.setImageResource(R.drawable.ic_selected_thirteen);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setTwelvePositionSelected()
    {
        selectPositionId = 12;
        ivSelected.setImageResource(R.drawable.ic_selected_twelve);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setElevenPositionSelected()
    {
        selectPositionId = 11;
        ivSelected.setImageResource(R.drawable.ic_selected_eleven);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setTenPositionSelected()
    {
        selectPositionId = 10;
        ivSelected.setImageResource(R.drawable.ic_selected_ten);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setNinePositionSelected()
    {
        selectPositionId = 9;
        ivSelected.setImageResource(R.drawable.ic_selected_nine);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setEightPositionSelected()
    {
        selectPositionId = 8;
        ivSelected.setImageResource(R.drawable.ic_selected_eight);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setSevenPositionSelected()
    {
        selectPositionId = 7;
        ivSelected.setImageResource(R.drawable.ic_selected_seven);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setSixPositionSelected()
    {
        selectPositionId = 6;
        ivSelected.setImageResource(R.drawable.ic_selected_six);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setFivePositionSelected()
    {
        selectPositionId = 5;
        ivSelected.setImageResource(R.drawable.ic_selected_five);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setFourPositionSelected()
    {
        selectPositionId = 4;
        ivSelected.setImageResource(R.drawable.ic_selected_four);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setThreePositionSelected()
    {
        selectPositionId = 3;
        ivSelected.setImageResource(R.drawable.ic_selected_three);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setTowPositionSelected()
    {
        selectPositionId = 2;
        ivSelected.setImageResource(R.drawable.ic_selected_two);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setOnePositionSelected()
    {
        selectPositionId = 1;
        ivSelected.setImageResource(R.drawable.ic_selected_one);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setAmounts(newResult);

    }

    private void setSpinnerPositionSelected(String numberPosition)
    {
        try
        {

            for (int i = 0; i < positionIdAllBoxes.size(); i++)
            {
                if (numberPosition.equals(allBoxes.get(i).replace("جایگاه ", "")))
                {
                    spinnerAllBoxes.setSelection(i);

                        if (numberPosition.equals(stadiumPositionModels.get(43).getNumber())){
                            selectPositionId=44;
                        }else if ( numberPosition.equals(stadiumPositionModels.get(44).getNumber())){
                            selectPositionId=45;

                        }else {
                            selectPositionId = Integer.valueOf(numberPosition);
                        }
                  setAmounts(newResult);
                }
            }
        } catch (Exception e)
        {

        }

    }

    private void getAllBoxesRequest()
    {
        GetAllBoxesRequest request = new GetAllBoxesRequest();
        request.setViewers(count);
        request.setMatchId(matchId);
        SingletonService.getInstance().getAllBoxesService().getAllBoxes(new OnServiceStatus<WebServiceClass<GetAllBoxesResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<GetAllBoxesResponse> response)
            {
                try
                {

                    if (response.info.statusCode == 200)
                    {
                        newResult = new ArrayList<>();

                        for (AllBoxesResult item: response.data.getResults())
                        {
                            if (item.getName().equals(stadiumPositionModels.get(43).getNumber())||
                                    item.getName().equals(stadiumPositionModels.get(44).getNumber())
                                    ||
                                    item.getName().equals(stadiumPositionModels.get(47).getNumber())){

                            }else if (Integer.parseInt(item.getName()) < 10)
                            {
                                newResult.add(item);
                            }
                        }

                        for (AllBoxesResult item: response.data.getResults())
                        {
                            if (item.getName().equals(stadiumPositionModels.get(43).getNumber())||
                                    item.getName().equals(stadiumPositionModels.get(44).getNumber())
                                    ||
                                    item.getName().equals(stadiumPositionModels.get(47).getNumber())){

                                newResult.add(item);

                            }else if (Integer.parseInt(item.getName()) >= 10)
                                {

                                    newResult.add(item);
                                }


                        }
                        Logger.e("--AllBoxesResult size --", "size: " + newResult.size());

                        setDataSpinnerAllBoxes(newResult);
                        allBoxesResponse = newResult;
                        setFullPositions(newResult);
                        setAmounts(newResult);
                    }
                    else
                    {
                        showToast(getContext(), response.info.message, R.color.red);
                    }
                }
                catch (Exception e)
                {
                    showToast(getContext(), e.getMessage(), R.color.red);

                }
            }

            @Override
            public void onError(String message)
            {
               /* btnMyBills.revertAnimation(BillFragment.this);
                btnMyBills.setClickable(true);*/
                Tools.showToast(getActivity(), message, R.color.red);
            }
        }, request);
    }

    private void setFullPositions(List<AllBoxesResult> results)
    {
        Observable.fromIterable(results)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AllBoxesResult>()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {
                    }

                    @Override
                    public void onComplete()
                    {
                        initFullPart();

                    }

                    @Override
                    public void onError(Throwable e)
                    {

                    }

                    @Override
                    public void onNext(AllBoxesResult result)
                    {
                        for (StadiumPositionModel stadiomModel : stadiumPositionModels)
                        {

                            if (stadiomModel.getNumber().equals(result.getName()))
                            {
                                stadiomModel.setId(result.getId());
                                stadiomModel.setAmount(result.getTicketAmount());
                                if (stadiomModel.isFull())
                                    stadiomModel.setFull(false);
                            }

                        }


                    }
                });
    }


    public void initFullPart()
    {

        Observable.fromIterable(stadiumPositionModels)
//                .subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StadiumPositionModel>()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {
                    }

                    @Override
                    public void onComplete()
                    {
                        BuyTicketsFragment.buyTicketsFragment.hideLoading();
                        handleSetStadiumLayouts();
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        BuyTicketsFragment.buyTicketsFragment.hideLoading();

                    }

                    @Override
                    public void onNext(StadiumPositionModel partStadiomModel)
                    {
                        if (partStadiomModel.isFull())
                        {
                            imgView = new ImageView(getContext());
                            rlImageViewsFull.addView(imgView);
                            imgView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                            imgView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT
                                    , RelativeLayout.LayoutParams.MATCH_PARENT));
                            switch (partStadiomModel.getNumber())
                            {
                                case "1":
                                    setImageIntoIV(imgView, R.drawable.ic_one_full);
//                                    imgView.setImageResource(R.drawable.ic_one_full);
                                    break;
                                case "2":
                                    setImageIntoIV(imgView, R.drawable.ic_tow_full);
//                                    imgView.setImageResource(R.drawable.ic_tow_full);
                                    break;
                                case "3":
                                    setImageIntoIV(imgView, R.drawable.ic_three_full);
//                                    imgView.setImageResource(R.drawable.ic_three_full);
                                    break;
                                case "4":
                                    setImageIntoIV(imgView, R.drawable.ic_four_full);
//                                    imgView.setImageResource(R.drawable.ic_four_full);
                                    break;
                                case "5":
                                    setImageIntoIV(imgView, R.drawable.ic_five_full);
//                                    imgView.setImageResource(R.drawable.ic_five_full);
                                    break;

                                case "6":
                                    setImageIntoIV(imgView, R.drawable.ic_six_full);
//                                    imgView.setImageResource(R.drawable.ic_six_full);
                                    break;
                                case "7":
                                    setImageIntoIV(imgView, R.drawable.ic_seven_full);
//                                    imgView.setImageResource(R.drawable.ic_seven_full);
                                    break;
                                case "8":
                                    setImageIntoIV(imgView, R.drawable.ic_eight_full);
//                                    imgView.setImageResource(R.drawable.ic_eight_full);
                                    break;
                                case "9":
                                    setImageIntoIV(imgView, R.drawable.ic_nine_full);
//                                    imgView.setImageResource(R.drawable.ic_nine_full);
                                    break;
                                case "10":
                                    setImageIntoIV(imgView, R.drawable.ic_ten_full);
//                                    imgView.setImageResource(R.drawable.ic_ten_full);
                                    break;

                                case "11":
                                    setImageIntoIV(imgView, R.drawable.ic_eleven_full);
//                                    imgView.setImageResource(R.drawable.ic_eleven_full);
                                    break;
                                case "12":
                                    setImageIntoIV(imgView, R.drawable.ic_twelve_full);
//                                    imgView.setImageResource(R.drawable.ic_twelve_full);
                                    break;
                                case "13":
                                    setImageIntoIV(imgView, R.drawable.ic_thirteen_full);
//                                    imgView.setImageResource(R.drawable.ic_thirteen_full);
                                    break;
                                case "14":
                                    setImageIntoIV(imgView, R.drawable.ic_fourteen_full);
//                                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                                    break;
                                case "15":
                                    setImageIntoIV(imgView, R.drawable.ic_fifteen_full);
//                                    imgView.setImageResource(R.drawable.ic_fifteen_full);
                                    break;

                                case "16":
                                    setImageIntoIV(imgView, R.drawable.ic_sixteen_full);
//                                    imgView.setImageResource(R.drawable.ic_sixteen_full);
                                    break;
                                case "17":
                                    setImageIntoIV(imgView, R.drawable.ic_seventeen_full);
//                                    imgView.setImageResource(R.drawable.ic_seventeen_full);
                                    break;
                                case "18":
                                    setImageIntoIV(imgView, R.drawable.ic_eighteen_full);
//                                    imgView.setImageResource(R.drawable.ic_eighteen_full);
                                    break;
                                case "19":
                                    setImageIntoIV(imgView, R.drawable.ic_nineteen_full);
//                                    imgView.setImageResource(R.drawable.ic_nineteen_full);
                                    break;
                                case "20":
                                    setImageIntoIV(imgView, R.drawable.ic_twenty_full);
//                                    imgView.setImageResource(R.drawable.ic_twenty_full);
                                    break;

                                case "21":
                                    setImageIntoIV(imgView, R.drawable.ic_twenty_one_full);
//                                    imgView.setImageResource(R.drawable.ic_twenty_one_full);
                                    break;
                                case "22":
                                    setImageIntoIV(imgView, R.drawable.ic_twenty_tow_full);
//                                    imgView.setImageResource(R.drawable.ic_twenty_tow_full);
                                    break;
                                case "23":
                                    setImageIntoIV(imgView, R.drawable.ic_twenty_three_full);
//                                    imgView.setImageResource(R.drawable.ic_twenty_three_full);
                                    break;
                                case "24":
                                    setImageIntoIV(imgView, R.drawable.ic_twenty_four_full);
//                                    imgView.setImageResource(R.drawable.ic_twenty_four_full);
                                    break;
                                case "25":
                                    setImageIntoIV(imgView, R.drawable.ic_twenty_five_full);
//                                    imgView.setImageResource(R.drawable.ic_twenty_five_full);
                                    break;

                                case "26":
                                    setImageIntoIV(imgView, R.drawable.ic_twenty_six_full);
//                                    imgView.setImageResource(R.drawable.ic_twenty_six_full);
                                    break;
                                case "27":
                                    setImageIntoIV(imgView, R.drawable.ic_twenty_seven_full);
//                                    imgView.setImageResource(R.drawable.ic_twenty_seven_full);
                                    break;
                                case "28":
                                    setImageIntoIV(imgView, R.drawable.ic_twenty_eight_full);
//                                    imgView.setImageResource(R.drawable.ic_twenty_eight_full);
                                    break;
                                case "29":
                                    setImageIntoIV(imgView, R.drawable.ic_twenty_nine_full);
//                                    imgView.setImageResource(R.drawable.ic_twenty_nine_full);
                                    break;
                                case "30":
                                    setImageIntoIV(imgView, R.drawable.ic_thirty_full);
//                                    imgView.setImageResource(R.drawable.ic_thirty_full);
                                    break;

                                case "31":
                                    setImageIntoIV(imgView, R.drawable.ic_thirty_one_full);
//                                    imgView.setImageResource(R.drawable.ic_thirty_one_full);
                                    break;
                                case "32":
                                    setImageIntoIV(imgView, R.drawable.ic_thirty_two_full);
//                                    imgView.setImageResource(R.drawable.ic_thirty_two_full);
                                    break;
                                case "33":
                                    setImageIntoIV(imgView, R.drawable.ic_thirty_three_full);
//                                    imgView.setImageResource(R.drawable.ic_thirty_three_full);
                                    break;
                                case "34":
                                    setImageIntoIV(imgView, R.drawable.ic_thirty_four_full);
//                                    imgView.setImageResource(R.drawable.ic_thirty_four_full);
                                    break;
                                case "35":
                                    setImageIntoIV(imgView, R.drawable.ic_thirty_five_full);
//                                    imgView.setImageResource(R.drawable.ic_thirty_five_full);
                                    break;

                                case "36":
                                    setImageIntoIV(imgView, R.drawable.ic_thirty_six_full);
//                                    imgView.setImageResource(R.drawable.ic_thirty_six_full);
                                    break;
                                case "37":
                                    setImageIntoIV(imgView, R.drawable.ic_thirty_seven_full);
//                                    imgView.setImageResource(R.drawable.ic_thirty_seven_full);
                                    break;
                                case "38":
                                    setImageIntoIV(imgView, R.drawable.ic_thirty_eight_full);
//                                    imgView.setImageResource(R.drawable.ic_thirty_eight_full);
                                    break;
                                case "39":
                                    setImageIntoIV(imgView, R.drawable.ic_thirty_nine_full);
//                                    imgView.setImageResource(R.drawable.ic_thirty_nine_full);
                                    break;
                                case "40":
                                    setImageIntoIV(imgView, R.drawable.ic_fourty_full);
//                                    imgView.setImageResource(R.drawable.ic_fourty_full);
                                    break;

                                case "41":
                                    setImageIntoIV(imgView, R.drawable.ic_fourty_one_full);
//                                    imgView.setImageResource(R.drawable.ic_fourty_one_full);
                                    break;
                                case "42":
                                    setImageIntoIV(imgView, R.drawable.ic_fourty_two_full);
//                                    imgView.setImageResource(R.drawable.ic_fourty_two_full);
                                    break;
                                case "43":
                                    setImageIntoIV(imgView, R.drawable.ic_fourty_three_full);
//                                    imgView.setImageResource(R.drawable.ic_fourty_three_full);
                                    break;
                                case "CIP":
                                    setImageIntoIV(imgView, R.drawable.ic_cip_full);
//                                    imgView.setImageResource(R.drawable.ic_cip_full);
                                    break;
                                case "مهمان":
                                    setImageIntoIV(imgView, R.drawable.ic_full_guest);
//                                    imgView.setImageResource(R.drawable.ic_bottom_full);
                                    break;
                                case "میهمان":
                                    setImageIntoIV(imgView, R.drawable.ic_full_guest);
//                                    imgView.setImageResource(R.drawable.ic_bottom_full);
                                    break;
                                case "46":
                                    setImageIntoIV(imgView, R.drawable.ic_up_stadium_full);
//                                    imgView.setImageResource(R.drawable.ic_up_stadium_full);
                                    break;
                                case "47":
                                    setImageIntoIV(imgView,R.drawable.ic_bottom_full);

                            }


                        }

                    }
                });

    }

    private void setImageIntoIV(ImageView imgView, int drawable)
    {
//        Bitmap myImg = null;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//        {
//            myImg = ((BitmapDrawable) ResourcesCompat.getDrawable(getActivity().getResources(), drawable, null)).getBitmap();
//        }
//        else
//        {
//        }


      /* BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inSampleSize =3;

        Bitmap mImageBitmap = BitmapFactory.decodeResource(getResources(), drawable, options);*/

        Picasso.with(SingletonContext.getInstance().getContext())
               .load(drawable)
                .into(imgView);
        //this code dont work for image larger
   /*     Glide.with(SingletonContext.getInstance().getContext())
                .load(mImageBitmap)
                .into(imgView)
                .clearOnDetach();*/

    }

    private void setAmounts(List<AllBoxesResult> results)
    {

     try
     {
         amountOneTicket = stadiumPositionModels.get(selectPositionId-1).getAmount();
         amountForPay = amountOneTicket * count;
//        tvAmountStation.setText("قیمت بلیت این جایگاه:" + Utility.priceFormat(results.get(0).getTicketAmount().toString()) + " ریال");
         tvAmountStation.setText("قیمت بلیت این جایگاه:" + Utility.priceFormat(String.valueOf(amountOneTicket))+ " ریال");
         tvAmountForPay.setText("مبلغ قابل پرداخت:" + Utility.priceFormat(String.valueOf(amountForPay)) + " ریال");

     }catch (Exception e){

     }

    }

    private void setDataSpinnerAllBoxes(List<AllBoxesResult> result)
    {
        allBoxes = new ArrayList<String>();
        positionIdAllBoxes = new ArrayList<Integer>();

        for (int i = 0; i < result.size(); i++)
        {
            allBoxes.add("جایگاه " + result.get(i).getName());
            //allBoxes.add(result.get(i).getName());

            positionIdAllBoxes.add(result.get(i).getId());
        }

        ArrayAdapter<String> adapterAllBoxes = new ArrayAdapter<String>(getActivity(),
                R.layout.my_spinner_item, allBoxes);
        adapterAllBoxes.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinnerAllBoxes.setAdapter(adapterAllBoxes);
        spinnerAllBoxes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedIndex = position;

                try
                {
                    switch (result.get(position).getName())
                    {
                        case "1":
                            setOnePositionSelected();
                            break;
                        case "2":
                            setTowPositionSelected();
                            break;
                        case "3":
                            setThreePositionSelected();
                            break;
                        case "4":
                            setFourPositionSelected();
                            break;
                        case "5":
                            setFivePositionSelected();
                            break;


                        case "6":
                            setSixPositionSelected();
                            break;
                        case "7":
                            setSevenPositionSelected();
                            break;
                        case "8":
                            setEightPositionSelected();
                            break;
                        case "9":
                            setNinePositionSelected();
                            break;
                        case "10":
                            setTenPositionSelected();
                            break;


                        case "11":
                            setElevenPositionSelected();
                            break;
                        case "12":
                            setTwelvePositionSelected();
                            break;
                        case "13":
                            setThirteenPositionSelected();
                            break;
                        case "14":
                            setFourteenPositionSelected();
                            break;
                        case "15":
                            setFiveteenPositionSelected();
                            break;


                        case "16":
                            setSixteenPositionSelected();
                            break;
                        case "17":
                            setSeventeenPositionSelected();
                            break;
                        case "18":
                            setEighteenPositionSelected();
                            break;
                        case "19":
                            setNineteenPositionSelected();
                            break;
                        case "20":
                            setTwentyPositionSelected();
                            break;


                        case "21":
                            setTwentyonePositionSelected();
                            break;
                        case "22":
                            setTwentytwoPositionSelected();
                            break;
                        case "23":
                            setTwentythreePositionSelected();
                            break;
                        case "24":
                            setTwentyfourPositionSelected();
                            break;
                        case "25":
                            setTwentyfivePositionSelected();
                            break;


                        case "26":
                            setTwentysixPositionSelected();
                            break;
                        case "27":
                            setTwentysevenPositionSelected();
                            break;
                        case "28":
                            setTwentyeightPositionSelected();
                            break;
                        case "29":
                            setTwentyninePositionSelected();
                            break;
                        case "30":
                            setThirtyPositionSelected();
                            break;


                        case "31":
                            setThirtyonePositionSelected();
                            break;
                        case "32":
                            setThirtytwoPositionSelected();
                            break;
                        case "33":
                            setThirtythreePositionSelected();
                            break;
                        case "34":
                            setThirtyfourPositionSelected();
                            break;
                        case "35":
                            setThirtyfivePositionSelected();
                            break;


                        case "36":
                            setThirtysixPositionSelected();
                            break;
                        case "37":
                            setThirtysevenPositionSelected();
                            break;
                        case "38":
                            setThirtyeightPositionSelected();
                            break;
                        case "39":
                            setThirtyninePositionSelected();
                            break;
                        case "40":
                            setfourtyPositionSelected();
                            break;


                        case "41":
                            setfourtyonePositionSelected();
                            break;
                        case "42":
                            setfourtytwoPositionSelected();
                            break;
                        case "43":
                            setfourtythreePositionSelected();
                            break;

                        case "مهمان":
                            setGuestPositionSelected();
                            break;
                        case "میهمان":
                            setGuestPositionSelected();
                            break;
                        case "CIP":
                            setCIPPositionSelected();
                            break;
                    }

                } catch (Exception e)
                {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });
    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        // activity must implement OnListFragmentInteractionListener
        if (context instanceof OnListFragmentInteractionListener)
        {
            interactionListener = (OnListFragmentInteractionListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        interactionListener = null;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnPaymentConfirm:
                BuyTicketsFragment.buyTicketsFragment.showLoading();
                callReservationRequest();
                break;
           /* case R.id.btnBackToDetail:
                onClickContinueBuyTicketListener.onBackClicked();
                break;*/
            case R.id.tvM:
                if (count > 1)
                {
                    count--;
                    setTimerForRequestGetStadiumData();
                }
                break;
            case R.id.tvP:
                if (count == 5)
                {
                    Tools.showToast(getContext(), "حداکثر تعداد بلیت قابل خرید 5 عدد میباشد.");
                }
                if (count < 5)
                {
                    count++;
                    setTimerForRequestGetStadiumData();
                }
                break;
        }
        setAmounts(count);
        tvCount.setText(String.valueOf(count));
    }

    public void setTimerForRequestGetStadiumData(){


        handler.removeCallbacks(stadiumInfoRunnable);
        handler.postDelayed(stadiumInfoRunnable, timeForRequestGetData);
    }

    private void callReservationRequest()
    {
        Logger.e("-stadiumPositionModels-", "size: " + stadiumPositionModels.size());
        Logger.e("-callReservationRequest-", "pos: " + selectPositionId);
        reservationMatch.reservationRequest(this,
                matchId,
                count,
                stadiumPositionModels.get(selectPositionId - 1).getId());
//                stadiumPositionModels.get(selectPositionId).getId() - 1);
    }

    private void setAmounts(int countTicket)
    {
        amountForPay = amountOneTicket * countTicket;
        tvAmountForPay.setText("مبلغ قابل پرداخت:" + Utility.priceFormat(String.valueOf(amountForPay)) + " ریال");
    }

    @Override
    public void onFinishedReservation(ReservationResponse response)
    {
//        BuyTicketsFragment.buyTicketsFragment.setData(selectPositionId, count, amountForPay,response.getResults());
        if (selectPositionId==44){
            selectPosition="مهمان";
        }else if (selectPositionId==45){
            selectPosition="CIP";
        }else {
            selectPosition=selectPositionId.toString();
        }
        BuyTicketsFragment.buyTicketsFragment.setData(selectPosition, count, response.getAmount(),amountOneTicket, response.getResults(),stadiumId);
        onClickContinueBuyTicketListener.onContinueClicked();
        BuyTicketsFragment.buyTicketsFragment.hideLoading();

    }

    @Override
    public void onErrorReservation(String error)
    {

        Tools.showToast(getContext(), error, R.color.red);
        BuyTicketsFragment.buyTicketsFragment.hideLoading();

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * <p/>
     */
    public interface OnListFragmentInteractionListener
    {
        // void onListFragmentInteraction(SubMenuModel item);
    }
}

