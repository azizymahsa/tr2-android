package ir.trap.tractor.android.ui.fragments.ticket;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.pixplicity.easyprefs.library.Prefs;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ir.trap.tractor.android.R;
import ir.trap.tractor.android.apiServices.generator.SingletonService;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.StadiumPositionModel;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.getAllBoxes.AllBoxesResult;
import ir.trap.tractor.android.apiServices.model.getAllBoxes.GetAllBoxesRequest;
import ir.trap.tractor.android.apiServices.model.getAllBoxes.GetAllBoxesResponse;
import ir.trap.tractor.android.apiServices.model.matchList.MachListResponse;
import ir.trap.tractor.android.apiServices.model.matchList.MatchItem;
import ir.trap.tractor.android.utilities.Logger;
import ir.trap.tractor.android.utilities.Tools;
import library.android.calendar.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import library.android.eniac.utility.Utility;
import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class SelectPositionFragment
        extends Fragment implements View.OnClickListener
{

    ColorPickerView colorPickerView;
    ImageView ivDefault,ivSelected;
    RelativeLayout rlImageViewsFull;
    private static final String KEY_MODEL = "KEY_MODEL";
    private TextView tvTitle,tvAmountStation,tvAmountForPay,tvStadiumName, tvDateTime, tvCount, tvM, tvP;
   // private SubMenuModel[] subMenuModels;
    private OnListFragmentInteractionListener interactionListener;
   /* @BindView(R.id.spinnerAllBoxes)
    Spinner spinnerAllBoxes;*/
    private ArrayList<String> allBoxes;
    private Spinner spinnerAllBoxes;
    private Integer selectPositionId;
    private View btnBackToDetail;
    private View btnPaymentConfirm,llSliderItemMatch;
    private OnClickContinueBuyTicket onClickContinueBuyTicketListener;
    private List<AllBoxesResult> allBoxesResponse;
    ArrayList<StadiumPositionModel> stadiumPositionModels = new ArrayList<>();
    ArrayList<Integer> fullFromServer = new ArrayList<>();
    private int amountForPay;
    private ImageView imgView,imgHost, imgGuest;
    private ImageView imgViewSelected;
    private int count = 1;
    private Integer matchId=1;
    private Integer amountOneTicket=0;
    private ArrayList<Integer> positionIdAllBoxes;
    private MatchItem matchBuyable;
    private int positionId;
    private String positionName;


    // private List<AllBoxesResult> allBoxesResult=new ArrayList<>();

    public SelectPositionFragment() {

    }

    /**
     * Receive the model list
     */
    public static SelectPositionFragment newInstance(String s, OnClickContinueBuyTicket onClickContinueBuyTicket, MatchItem matchBuyable) {
        SelectPositionFragment fragment = new SelectPositionFragment();
        fragment.setOnClickContinueBuyTicket(onClickContinueBuyTicket);
        Bundle args = new Bundle();
        args.putParcelable("matchBuyable", matchBuyable);

        fragment.setArguments(args);

        return fragment;
    }

    private void setOnClickContinueBuyTicket(OnClickContinueBuyTicket onClickContinueBuyTicket)
    {
        this.onClickContinueBuyTicketListener=onClickContinueBuyTicket;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        matchBuyable = getArguments().getParcelable("matchBuyable");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_position_fragment, container, false);
        // tvTitle=view.findViewById(R.id.tvTitle);
        Context context = view.getContext();


        tvCount = view.findViewById(R.id.tvCount);
        tvM = view.findViewById(R.id.tvM);
        tvP = view.findViewById(R.id.tvP);
        tvStadiumName = view.findViewById(R.id.tvStadiumName);
        tvDateTime = view.findViewById(R.id.tvDateTime);
        imgHost = view.findViewById(R.id.imgHost);
        imgGuest = view.findViewById(R.id.imgGuest);
        llSliderItemMatch=view.findViewById(R.id.llSliderItemMatch);

        spinnerAllBoxes=view.findViewById(R.id.spinnerAllBoxes);
       // btnBackToDetail=view.findViewById(R.id.btnBackToDetail);
        btnPaymentConfirm=view.findViewById(R.id.btnPaymentConfirm);
        tvAmountStation=view.findViewById(R.id.tvAmountStation);
        tvAmountForPay=view.findViewById(R.id.tvAmountForPay);
        setDataMatch();
        getAllBoxesRequest();
        //btnBackToDetail.setOnClickListener(this);
        tvP.setOnClickListener(this);
        tvM.setOnClickListener(this);
        btnPaymentConfirm.setOnClickListener(this);

        colorPickerView = view.findViewById(R.id.colorPickerView);
        ivDefault = view.findViewById(R.id.ivDefault);
        ivSelected=view.findViewById(R.id.ivSelected);
        rlImageViewsFull = view.findViewById(R.id.rlImageViewsFull);


        setDataStadiumPosition();
       // setFullPositions();
        setLayoutFullPosition();
        handleSetStadiumLayouts();

      // allBoxesResult.add(new AllBoxesResult(0,"test",4));
       // setDataSpinnerAllBoxes(allBoxesResult);
       /* RecyclerView recyclerView = (RecyclerView) view;

        recyclerView.setLayoutManager(new GridLayoutManager(context,3));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(new ItemRecyclerViewAdapter(subMenuModels, interactionListener));*/
        return view;
    }

    private void setDataMatch()
    {

                    try
                    {
                        llSliderItemMatch.setVisibility(View.VISIBLE);
                        matchId=matchBuyable.getId();
                        tvStadiumName.setText(matchBuyable.getStadium().getName());
                        setImageColor(imgHost, matchBuyable.getTeamHome().getLogo());
                        setImageColor(imgGuest,matchBuyable.getTeamAway().getLogo());
                        tvDateTime.setText(getDate(matchBuyable.getMatchDatetime()));

                    }catch (Exception e){
                        Tools.showToast(getContext(),e.getMessage(),R.color.red);
                        llSliderItemMatch.setVisibility(View.GONE);

                    }

    }

    private String getDate(Double matchDatetime)
    {
        String shamsi = "";
        Date d = new Date((new Double(matchDatetime)).longValue());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd " + " " + "hh:mm:ss");
        String date = dateFormat.format(d);  // formatted date in string

        PersianCalendar persianCalendar = new PersianCalendar();
        PersianDate pdate = new PersianDate();
        PersianDateFormat pdformater1 = new PersianDateFormat("Y/m/d");
      //  pdformater1.format(pdate);//1396/05/20

        PersianDateFormat pdformater2 = new PersianDateFormat("l j F y ");
        date =String.valueOf(pdformater2.format(pdate));//۱۹ تیر ۹۶



        return date;
    }

    private void setImageColor(ImageView imageView, String link)
    {
        try
        {
            Picasso.with(getContext()).load(link).into(imageView, new Callback()
            {
                @Override
                public void onSuccess()
                {
                    // cvContent.setBackgroundColor(Color.TRANSPARENT);
                }

                @Override
                public void onError()
                {
                    Picasso.with(getContext()).load(R.drawable.img_failure).into(imageView);
                }
            });
        }catch (Exception e){

        }
    }


    private void setLayoutFullPosition()
    {
        for (Integer integer : fullFromServer) {
            imgView = new ImageView(this.getContext());
            rlImageViewsFull.addView(imgView);
            imgView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imgView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
            switch (integer) {
                case 1:
                    imgView.setImageResource(R.drawable.ic_one_full);
                    break;
                case 2:
                    imgView.setImageResource(R.drawable.ic_tow_full);
                    break;
                case 3:
                    imgView.setImageResource(R.drawable.ic_three_full);
                    break;
                case 4:
                    imgView.setImageResource(R.drawable.ic_four_full);
                    break;
                case 5:
                    imgView.setImageResource(R.drawable.ic_five_full);
                    break;

                case 6:
                    imgView.setImageResource(R.drawable.ic_six_full);
                    break;
                case 7:
                    imgView.setImageResource(R.drawable.ic_seven_full);
                    break;
                case 8:
                    imgView.setImageResource(R.drawable.ic_eight_full);
                    break;
                case 9:
                    imgView.setImageResource(R.drawable.ic_nine_full);
                    break;
                case 10:
                    imgView.setImageResource(R.drawable.ic_ten_full);
                    break;

                case 11:
                    imgView.setImageResource(R.drawable.ic_eleven_full);
                    break;
                case 12:
                    imgView.setImageResource(R.drawable.ic_twelve_full);
                    break;
                case 13:
                    imgView.setImageResource(R.drawable.ic_thirteen_full);
                    break;
                case 14:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;
                case 15:
                    imgView.setImageResource(R.drawable.ic_fifteen_full);
                    break;

                case 16:
                    imgView.setImageResource(R.drawable.ic_sixteen_full);
                    break;
                case 17:
                    imgView.setImageResource(R.drawable.ic_seventeen_full);
                    break;
                case 18:
                    imgView.setImageResource(R.drawable.ic_eighteen_full);
                    break;
                case 19:
                    imgView.setImageResource(R.drawable.ic_nineteen_full);
                    break;
                case 20:
                    imgView.setImageResource(R.drawable.ic_twenty_full);
                    break;

                case 21:
                    imgView.setImageResource(R.drawable.ic_twenty_one_full);
                    break;
                case 22:
                    imgView.setImageResource(R.drawable.ic_twenty_tow_full);
                    break;
                case 23:
                    imgView.setImageResource(R.drawable.ic_twenty_three_full);
                    break;
                case 24:
                    imgView.setImageResource(R.drawable.ic_twenty_four_full);
                    break;
                case 25:
                    imgView.setImageResource(R.drawable.ic_twenty_five_full);
                    break;

                case 26:
                    imgView.setImageResource(R.drawable.ic_twenty_six_full);
                    break;
                case 27:
                    imgView.setImageResource(R.drawable.ic_twenty_seven_full);
                    break;
                case 28:
                    imgView.setImageResource(R.drawable.ic_twenty_eight_full);
                    break;
                case 29:
                    imgView.setImageResource(R.drawable.ic_twenty_nine_full);
                    break;
                case 30:
                    imgView.setImageResource(R.drawable.ic_thirty_full);
                    break;

                case 31:
                    imgView.setImageResource(R.drawable.ic_thirty_one_full);
                    break;
                case 32:
                    imgView.setImageResource(R.drawable.ic_thirty_two_full);
                    break;
                case 33:
                    imgView.setImageResource(R.drawable.ic_thirty_three_full);
                    break;
                case 34:
                    imgView.setImageResource(R.drawable.ic_thirty_four_full);
                    break;
                case 35:
                    imgView.setImageResource(R.drawable.ic_thirty_five_full);
                    break;

                case 36:
                    imgView.setImageResource(R.drawable.ic_thirty_six_full);
                    break;
                case 37:
                    imgView.setImageResource(R.drawable.ic_thirty_seven_full);
                    break;
                case 38:
                    imgView.setImageResource(R.drawable.ic_thirty_eight_full);
                    break;
                case 39:
                    imgView.setImageResource(R.drawable.ic_thirty_nine_full);
                    break;
                case 40:
                    imgView.setImageResource(R.drawable.ic_fourty_full);
                    break;

                case 41:
                    imgView.setImageResource(R.drawable.ic_fourty_one_full);
                    break;
                case 42:
                    imgView.setImageResource(R.drawable.ic_fourty_two_full);
                    break;
                case 43:
                    imgView.setImageResource(R.drawable.ic_fourty_three_full);
                    break;
                case 44:
                    break;
                case 45:
                    break;

            }


        }
    }

    private void setFullPositions()
    {
        fullFromServer.add(5);
        fullFromServer.add(20);
        fullFromServer.add(25);
        fullFromServer.add(30);

    }

    private void setDataStadiumPosition()
    {
        stadiumPositionModels.add(new StadiumPositionModel("FF328DAA", 1));
        stadiumPositionModels.add(new StadiumPositionModel("FF953D3D", 2));
        stadiumPositionModels.add(new StadiumPositionModel("FFFE9000", 3));
        stadiumPositionModels.add(new StadiumPositionModel("FFFFFC9B", 4));
        stadiumPositionModels.add(new StadiumPositionModel("FF00AC62", 5));

        stadiumPositionModels.add(new StadiumPositionModel("FF8A3D7D", 6));
        stadiumPositionModels.add(new StadiumPositionModel("FF9AB260", 7));
        stadiumPositionModels.add(new StadiumPositionModel("FFFF8181", 8));
        stadiumPositionModels.add(new StadiumPositionModel("FF0F0060", 9));
        stadiumPositionModels.add(new StadiumPositionModel("FFFFC170", 10));

        stadiumPositionModels.add(new StadiumPositionModel("FF00EDFF", 11));
        stadiumPositionModels.add(new StadiumPositionModel("FF481337", 12));
        stadiumPositionModels.add(new StadiumPositionModel("FF009A8F", 13));
        stadiumPositionModels.add(new StadiumPositionModel("FFFE0002", 14));
        stadiumPositionModels.add(new StadiumPositionModel("FF00FF5D", 15));

        stadiumPositionModels.add(new StadiumPositionModel("FFA0F113", 16));
        stadiumPositionModels.add(new StadiumPositionModel("FF8A4000", 17));
        stadiumPositionModels.add(new StadiumPositionModel("FF0080FF", 18));
        stadiumPositionModels.add(new StadiumPositionModel("FFDC0DB3", 19));
        stadiumPositionModels.add(new StadiumPositionModel("FF52488A", 20));

        stadiumPositionModels.add(new StadiumPositionModel("FFCFD574", 21));
        stadiumPositionModels.add(new StadiumPositionModel("FFA8CAEC", 22));
        stadiumPositionModels.add(new StadiumPositionModel("FF575657", 23));
        stadiumPositionModels.add(new StadiumPositionModel("FF8FC549", 24));
        stadiumPositionModels.add(new StadiumPositionModel("FF9A1955", 25));

        stadiumPositionModels.add(new StadiumPositionModel("FF8DFFFB", 26));
        stadiumPositionModels.add(new StadiumPositionModel("FFA29C00", 27));
        stadiumPositionModels.add(new StadiumPositionModel("FF00E600", 28));
        stadiumPositionModels.add(new StadiumPositionModel("FFD8B506", 29));
        stadiumPositionModels.add(new StadiumPositionModel("FFCF0000", 30));

        stadiumPositionModels.add(new StadiumPositionModel("FF948DFF", 31));
        stadiumPositionModels.add(new StadiumPositionModel("FFE7EC44", 32));
        stadiumPositionModels.add(new StadiumPositionModel("FFD97B00", 33));
        stadiumPositionModels.add(new StadiumPositionModel("FFC500FF", 34));
        stadiumPositionModels.add(new StadiumPositionModel("FF74FFD0", 35));

        stadiumPositionModels.add(new StadiumPositionModel("FF8E7627", 36));
        stadiumPositionModels.add(new StadiumPositionModel("FFAC0000", 37));
        stadiumPositionModels.add(new StadiumPositionModel("FF828282", 38));
        stadiumPositionModels.add(new StadiumPositionModel("FF6E00FF", 39));
        stadiumPositionModels.add(new StadiumPositionModel("FF9CE27F", 40));

        stadiumPositionModels.add(new StadiumPositionModel("FFFFBAFA", 41));
        stadiumPositionModels.add(new StadiumPositionModel("FFF237FF", 42));
        stadiumPositionModels.add(new StadiumPositionModel("FF440000", 43));

        /////
        stadiumPositionModels.add(new StadiumPositionModel("", 44));
        stadiumPositionModels.add(new StadiumPositionModel("", 45));
        //////

    }

    private void handleSetStadiumLayouts()
    {
        colorPickerView.setColorListener(new ColorEnvelopeListener() {
            @Override
            public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {

                Logger.e("test", envelope.getHexCode() + "");

                for (StadiumPositionModel stadiumPosition : stadiumPositionModels) {
                    if (envelope.getHexCode().equals(stadiumPosition.getColor())) {
                        for (Integer integer : fullFromServer) {
                            if (integer.equals(stadiumPosition.getNumber())) {
                                Toast.makeText(getContext(), "این جایگاه پر شده است", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }
                }

                switch (envelope.getHexCode()) {
                    case "FF328DAA":
                        setOnePositionSelected();
                        setSpinnerPositionSelected(stadiumPositionModels.get(0).getNumber());
                        break;
                    case "FF953D3D":
                        setTowPositionSelected();
                        setSpinnerPositionSelected(stadiumPositionModels.get(1).getNumber());
                        break;
                    case "FFFE9000":
                        setThreePositionSelected();
                        setSpinnerPositionSelected(stadiumPositionModels.get(2).getNumber());
                        break;
                    case "FFFFFC9B":
                        setFourPositionSelected();
                        setSpinnerPositionSelected(stadiumPositionModels.get(3).getNumber());
                        break;
                    case "FF00AC62":
                        setFivePositionSelected();
                        setSpinnerPositionSelected(stadiumPositionModels.get(4).getNumber());
                        break;
                        /////5
                    case "FF8A3D7D":
                        setSixPositionSelected();
                        setSpinnerPositionSelected(stadiumPositionModels.get(5).getNumber());
                        break;
                    case "FF9AB260":
                        setSevenPositionSelected();
                        setSpinnerPositionSelected(stadiumPositionModels.get(6).getNumber());
                        break;
                    case "FFFF8181":
                        setEightPositionSelected();
                        setSpinnerPositionSelected(stadiumPositionModels.get(7).getNumber());
                        break;
                    case "FF0F0060":
                        setNinePositionSelected();
                        setSpinnerPositionSelected(stadiumPositionModels.get(8).getNumber());
                        break;
                    case "FFFFC170":
                        setTenPositionSelected();
                        setSpinnerPositionSelected(stadiumPositionModels.get(9).getNumber());
                        break;
                        ////////10
                    case "FF00EDFF":
                        setElevenPositionSelected();
                        setSpinnerPositionSelected(stadiumPositionModels.get(10).getNumber());
                        break;
                    case "FF481337":
                        setTwelvePositionSelected();
                        setSpinnerPositionSelected(stadiumPositionModels.get(11).getNumber());
                        break;
                    case "FF009A8F":
                        setThirteenPositionSelected();
                        setSpinnerPositionSelected(stadiumPositionModels.get(12).getNumber());
                        break;
                    case "FFFE0002":
                        setFourteenPositionSelected();
                        setSpinnerPositionSelected(stadiumPositionModels.get(13).getNumber());
                        break;
                    case "FF00FF5D":
                        setFiveteenPositionSelected();
                        setSpinnerPositionSelected(stadiumPositionModels.get(14).getNumber());
                        break;
                        ////15
                     case "FFA0F113":
                         setSixteenPositionSelected();
                         setSpinnerPositionSelected(stadiumPositionModels.get(15).getNumber());
                        break;
                    case "FF8A4000":
                        setSeventeenPositionSelected();
                        setSpinnerPositionSelected(stadiumPositionModels.get(16).getNumber());
                        break;
                    case "FF0080FF":
                        setEighteenPositionSelected();
                        setSpinnerPositionSelected(stadiumPositionModels.get(17).getNumber());
                        break;
                    case "FFDC0DB3":
                        setNineteenPositionSelected();
                        setSpinnerPositionSelected(stadiumPositionModels.get(18).getNumber());
                        break;
                    case "FF52488A":
                        setTwentyPositionSelected();
                        setSpinnerPositionSelected(stadiumPositionModels.get(19).getNumber());
                        break;
                        ////20

                    case "FFCFD574":
                        setTwentyonePositionSelected();
                        setSpinnerPositionSelected(stadiumPositionModels.get(20).getNumber());
                        break;
                    case "FFA8CAEC":
                        setTwentytwoPositionSelected();
                        setSpinnerPositionSelected(stadiumPositionModels.get(21).getNumber());
                        break;
                    case "FF575657":
                        setTwentythreePositionSelected();
                        setSpinnerPositionSelected(stadiumPositionModels.get(22).getNumber());
                        break;
                    case "FF8FC549":
                        setTwentyfourPositionSelected();
                        setSpinnerPositionSelected(stadiumPositionModels.get(23).getNumber());
                        break;
                    case "FF9A1955":
                        setTwentyfivePositionSelected();
                        setSpinnerPositionSelected(stadiumPositionModels.get(24).getNumber());
                        break;
                        ///////25

                    case "FF8DFFFB":
                        setTwentysixPositionSelected();
                        setSpinnerPositionSelected(stadiumPositionModels.get(25).getNumber());
                        break;
                    case "FFA29C00":
                        setTwentysevenPositionSelected();
                        setSpinnerPositionSelected(stadiumPositionModels.get(26).getNumber());
                        break;
                    case "FF00E600":
                        setTwentyeightPositionSelected();
                        setSpinnerPositionSelected(stadiumPositionModels.get(27).getNumber());
                        break;
                    case "FFD8B506":
                        setTwentyninePositionSelected();
                        setSpinnerPositionSelected(stadiumPositionModels.get(28).getNumber());
                        break;
                    case "FFCF0000":
                        setThirtyPositionSelected();
                        setSpinnerPositionSelected(stadiumPositionModels.get(29).getNumber());
                        break;
                        //////30

                      case "FF948DFF":
                          setThirtyonePositionSelected();
                          setSpinnerPositionSelected(stadiumPositionModels.get(30).getNumber());
                        break;
                    case "FFE7EC44":
                        setThirtytwoPositionSelected();
                       setSpinnerPositionSelected(stadiumPositionModels.get(31).getNumber());
                        break;
                    case "FFD97B00":
                        setThirtythreePositionSelected();
                        setSpinnerPositionSelected(stadiumPositionModels.get(32).getNumber());
                        break;
                    case "FFC500FF":
                        setThirtyfourPositionSelected();
                        setSpinnerPositionSelected(stadiumPositionModels.get(33).getNumber());
                        break;
                    case "FF74FFD0":
                        setThirtyfivePositionSelected();
                        setSpinnerPositionSelected(stadiumPositionModels.get(34).getNumber());
                        break;
                        //////////35

                   case "FF8E7627":
                       setThirtysixPositionSelected();
                       setSpinnerPositionSelected(stadiumPositionModels.get(35).getNumber());
                        break;
                    case "FFAC0000":
                        setThirtysevenPositionSelected();
                        setSpinnerPositionSelected(stadiumPositionModels.get(36).getNumber());
                        break;
                    case "FF828282":
                        setThirtyeightPositionSelected();
                        setSpinnerPositionSelected(stadiumPositionModels.get(37).getNumber());
                        break;
                    case "FF6E00FF":
                        setThirtyninePositionSelected();
                        setSpinnerPositionSelected(stadiumPositionModels.get(38).getNumber());
                        break;
                    case "FF9CE27F":
                        setfourtyPositionSelected();
                        setSpinnerPositionSelected(stadiumPositionModels.get(39).getNumber());
                        break;
                        //////40

                    case "FFFFBAFA":
                        setfourtyonePositionSelected();
                        setSpinnerPositionSelected(stadiumPositionModels.get(40).getNumber());
                        break;
                    case "FFF237FF":
                        setfourtytwoPositionSelected();
                        setSpinnerPositionSelected(stadiumPositionModels.get(41).getNumber());
                        break;
                    case "FF440000":
                        setfourtythreePositionSelected();
                        setSpinnerPositionSelected(stadiumPositionModels.get(42).getNumber());
                        break;
                   /* case "":
                        ivSelected.setImageResource(R.drawable.ic_fourteen_full);
                        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case "":
                        ivSelected.setImageResource(R.drawable.ic_fourteen_full);
                        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;*/
                        //////////45
                }

                Log.e("tessstt", envelope.getHexCode() + "");
            }
        });
    }

    private void setfourtythreePositionSelected()
    {
        selectPositionId = 43;
        ivSelected.setImageResource(R.drawable.ic_selected_fourty_three);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);

    }

    private void setfourtytwoPositionSelected()
    {
        selectPositionId = 42;
        ivSelected.setImageResource(R.drawable.ic_selected_fourty_two);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);

    }

    private void setfourtyonePositionSelected()
    {
        selectPositionId = 41;
        ivSelected.setImageResource(R.drawable.ic_selected_fourty_one);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);

    }

    private void setfourtyPositionSelected()
    {
        selectPositionId = 40;
        ivSelected.setImageResource(R.drawable.ic_selected_fourty);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);

    }

    private void setThirtyninePositionSelected()
    {
        selectPositionId = 39;
        ivSelected.setImageResource(R.drawable.ic_selected_thirty_nine);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);

    }

    private void setThirtyeightPositionSelected()
    {
        selectPositionId = 38;
        ivSelected.setImageResource(R.drawable.ic_selected_thirty_eight);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);

    }

    private void setThirtysevenPositionSelected()
    {
        selectPositionId = 37;
        ivSelected.setImageResource(R.drawable.ic_selected_thirty_seven);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);

    }

    private void setThirtysixPositionSelected()
    {
        selectPositionId = 36;
        ivSelected.setImageResource(R.drawable.ic_selected_thirty_six);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);

    }

    private void setThirtyfivePositionSelected()
    {
        selectPositionId = 35;
        ivSelected.setImageResource(R.drawable.ic_selected_thirty_five);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);

    }

    private void setThirtyfourPositionSelected()
    {
        selectPositionId = 34;
        ivSelected.setImageResource(R.drawable.ic_selected_thirty_four);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);

    }

    private void setThirtythreePositionSelected()
    {
        selectPositionId = 33;
        ivSelected.setImageResource(R.drawable.ic_selected_thirty_three);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);

    }

    private void setThirtytwoPositionSelected()
    {
        selectPositionId = 32;
        ivSelected.setImageResource(R.drawable.ic_selected_thirty_two);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);

    }

    private void setThirtyonePositionSelected()
    {
        selectPositionId = 31;
        ivSelected.setImageResource(R.drawable.ic_selected_thirty_one);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);

    }

    private void setThirtyPositionSelected()
    {
        selectPositionId = 30;
        ivSelected.setImageResource(R.drawable.ic_selected_thirty);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);


    }

    private void setTwentyninePositionSelected()
    {
        selectPositionId = 29;
        ivSelected.setImageResource(R.drawable.ic_selected_twenty_nine);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);

    }

    private void setTwentyeightPositionSelected()
    {
        selectPositionId = 28;
        ivSelected.setImageResource(R.drawable.ic_selected_twenty_eight);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);

    }

    private void setTwentysevenPositionSelected()
    {
        selectPositionId = 27;
        ivSelected.setImageResource(R.drawable.ic_selected_twenty_seven);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);

    }

    private void setTwentysixPositionSelected()
    {
        selectPositionId = 26;
        ivSelected.setImageResource(R.drawable.ic_selected_twenty_six);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);

    }

    private void setTwentyfivePositionSelected()
    {
        selectPositionId = 25;
        ivSelected.setImageResource(R.drawable.ic_selected_twenty_five);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);

    }

    private void setTwentyfourPositionSelected()
    {
        selectPositionId = 24;
        ivSelected.setImageResource(R.drawable.ic_selected_twenty_four);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);

    }

    private void setTwentythreePositionSelected()
    {
        selectPositionId = 23;
        ivSelected.setImageResource(R.drawable.ic_selected_twenty_three);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);

    }

    private void setTwentytwoPositionSelected()
    {
        selectPositionId = 22;
        ivSelected.setImageResource(R.drawable.ic_selected_twenty_two);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
    }

    private void setTwentyonePositionSelected()
    {
        selectPositionId = 21;
        ivSelected.setImageResource(R.drawable.ic_selected_twenty_one);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);

    }

    private void setTwentyPositionSelected()
    {
        selectPositionId = 20;
        ivSelected.setImageResource(R.drawable.ic_selected_twenty);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);

    }

    private void setNineteenPositionSelected()
    {
        selectPositionId = 19;
        ivSelected.setImageResource(R.drawable.ic_selected_nineteen);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);

    }

    private void setEighteenPositionSelected()
    {
        selectPositionId = 18;
        ivSelected.setImageResource(R.drawable.ic_selected_eighteen);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);

    }

    private void setSeventeenPositionSelected()
    {
        selectPositionId = 17;
        ivSelected.setImageResource(R.drawable.ic_selected_seventeen);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);

    }

    private void setSixteenPositionSelected()
    {
        selectPositionId = 16;
        ivSelected.setImageResource(R.drawable.ic_selected_sixteen);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);

    }

    private void setFiveteenPositionSelected()
    {
        selectPositionId = 15;
        ivSelected.setImageResource(R.drawable.ic_selected_fifteen);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);

    }

    private void setFourteenPositionSelected()
    {
        selectPositionId = 14;
        ivSelected.setImageResource(R.drawable.ic_selected_fourteen);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);

    }

    private void setThirteenPositionSelected()
    {
        selectPositionId = 13;
        ivSelected.setImageResource(R.drawable.ic_selected_thirteen);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);

    }

    private void setTwelvePositionSelected()
    {
        selectPositionId = 12;
        ivSelected.setImageResource(R.drawable.ic_selected_twelve);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);

    }

    private void setElevenPositionSelected()
    {
        selectPositionId = 11;
        ivSelected.setImageResource(R.drawable.ic_selected_eleven);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);

    }

    private void setTenPositionSelected()
    {
        selectPositionId = 10;
        ivSelected.setImageResource(R.drawable.ic_selected_ten);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);

    }

    private void setNinePositionSelected()
    {
        selectPositionId = 9;
        ivSelected.setImageResource(R.drawable.ic_selected_nine);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);

    }

    private void setEightPositionSelected()
    {
        selectPositionId = 8;
        ivSelected.setImageResource(R.drawable.ic_selected_eight);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);

    }

    private void setSevenPositionSelected()
    {
        selectPositionId = 7;
        ivSelected.setImageResource(R.drawable.ic_selected_seven);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);

    }

    private void setSixPositionSelected()
    {
        selectPositionId = 6;
        ivSelected.setImageResource(R.drawable.ic_selected_six);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);

    }

    private void setFivePositionSelected()
    {
        selectPositionId = 5;
        ivSelected.setImageResource(R.drawable.ic_selected_five);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);

    }

    private void setFourPositionSelected()
    {
        selectPositionId = 4;
        ivSelected.setImageResource(R.drawable.ic_selected_four);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);

    }

    private void setThreePositionSelected()
    {
        selectPositionId = 3;
        ivSelected.setImageResource(R.drawable.ic_selected_three);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
    }

    private void setTowPositionSelected()
    {
        selectPositionId = 2;
        ivSelected.setImageResource(R.drawable.ic_selected_two);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
    }

    private void setOnePositionSelected()
    {
        selectPositionId = 1;
        ivSelected.setImageResource(R.drawable.ic_selected_one);
        ivSelected.setScaleType(ImageView.ScaleType.FIT_CENTER);
    }

    private void setSpinnerPositionSelected(Integer numberPosition)
    {
        try
        {
            for (int i = 0; i < positionIdAllBoxes.size(); i++) {
                if (numberPosition == Integer.valueOf(allBoxes.get(i))) {
                    spinnerAllBoxes.setSelection(i);
                }
            }
        }catch (Exception e){

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
                try {
                    if (response.info.statusCode==200){
                        setDataSpinnerAllBoxes(response.data.getResults());
                        setAmounts(response.data.getResults());
                        allBoxesResponse=response.data.getResults();
                        setFullPositions(response.data.getResults());
                    }else {
                        Tools.showToast(getContext(),response.info.message,R.color.red);
                    }
                } catch (Exception e) {
                    Tools.showToast(getContext(),e.getMessage(),R.color.red);

                }
            }

            @Override
            public void onError(String message)
            {
               /* btnMyBills.revertAnimation(BillFragment.this);
                btnMyBills.setClickable(true);*/
                Tools.showToast(getActivity(),message,R.color.red);
            }
        }, request);
    }

    private void setFullPositions(List<AllBoxesResult> results)
    {
        Observable.fromIterable(results)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AllBoxesResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                      //  initFullPart();

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(AllBoxesResult result) {
                        for (StadiumPositionModel stadiomModel : stadiumPositionModels) {

                           /* if (stadiomModel.getNumber().equals(result.getName()) && stadiomModel.isFull) {
                                stadiomModel.setFull(false);
                            }*/

                        }


                    }
                });
    }

    private void setAmounts(List<AllBoxesResult> results)
    {
        amountForPay = results.get(0).getTicketAmount() * count;
        amountOneTicket=results.get(0).getTicketAmount();
        tvAmountStation.setText("قیمت بلیت این جایگاه :"+ Utility.priceFormat(results.get(0).getTicketAmount().toString()) +" ریال");
        tvAmountForPay.setText("مبلغ قابل پرداخت :"+Utility.priceFormat(String.valueOf(amountForPay)) + " ریال");
    }

    private void setDataSpinnerAllBoxes(List<AllBoxesResult> result)
    {
        allBoxes = new ArrayList<String>();
        positionIdAllBoxes = new ArrayList<Integer>();

        for (int i = 0; i < result.size(); i++) {
            allBoxes.add(result.get(i).getName());
            positionIdAllBoxes.add(result.get(i).getId());
        }
        ArrayAdapter<String> adapterAllBoxes = new ArrayAdapter<String>(getActivity(),
                R.layout.simple_spinner_item, allBoxes);
        adapterAllBoxes.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinnerAllBoxes.setAdapter(adapterAllBoxes);
        spinnerAllBoxes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

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

                    }

                }catch (Exception e){

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // activity must implement OnListFragmentInteractionListener
        if (context instanceof OnListFragmentInteractionListener) {
            interactionListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interactionListener = null;
    }

    public void setDataToCompleteInfoFragment(Integer id)
    {
        this.selectPositionId=id;
    }
    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.btnPaymentConfirm:
               // checkIdFromEachPosition();
                Prefs.putInt("PositionId",selectPositionId);
                Prefs.putInt("CountTicket",count);
                onClickContinueBuyTicketListener.onContinueClicked();
              //  callReservationRequest();
              //  onClickContinueBuyTicketListener.onContinueSelectPosiotionClicked(count,selectPositionId);
                break;
           /* case R.id.btnBackToDetail:
                onClickContinueBuyTicketListener.onBackClicked();
                break;*/
            case R.id.tvM:
                if (count > 1)
                    count--;
                break;
            case R.id.tvP:
                if (count < 5)
                    count++;
                break;
        }
        setAmounts(count);
        tvCount.setText(String.valueOf(count));
    }

    private void callReservationRequest()
    {

    }

    private void setAmounts(int countTicket)
    {
        amountForPay = amountOneTicket * countTicket;
        tvAmountForPay.setText("مبلغ قابل پرداخت "+Utility.priceFormat(String.valueOf(amountForPay)) + " ریال");
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * <p/>
     */
    public interface OnListFragmentInteractionListener {
       // void onListFragmentInteraction(SubMenuModel item);
    }
}

