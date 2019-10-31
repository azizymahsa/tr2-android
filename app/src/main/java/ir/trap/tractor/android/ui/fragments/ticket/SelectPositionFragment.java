package ir.trap.tractor.android.ui.fragments.ticket;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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

import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ir.trap.tractor.android.R;
import ir.trap.tractor.android.apiServices.generator.SingletonService;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.StadiumPositionModel;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.getAllBoxes.AllBoxesResult;
import ir.trap.tractor.android.apiServices.model.getAllBoxes.GetAllBoxesRequest;
import ir.trap.tractor.android.apiServices.model.getAllBoxes.GetAllBoxesResponse;
import ir.trap.tractor.android.apiServices.model.match.ResponseMatch;
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


    // private List<AllBoxesResult> allBoxesResult=new ArrayList<>();

    public SelectPositionFragment() {
    }

    /**
     * Receive the model list
     */
    public static SelectPositionFragment newInstance(String s,OnClickContinueBuyTicket onClickContinueBuyTicket) {
        SelectPositionFragment fragment = new SelectPositionFragment();
        fragment.setOnClickContinueBuyTicket(onClickContinueBuyTicket);
       Bundle args = new Bundle();
        args.putString(KEY_MODEL, s);
        fragment.setArguments(args);

        return fragment;
    }

    private void setOnClickContinueBuyTicket(OnClickContinueBuyTicket onClickContinueBuyTicket)
    {
        this.onClickContinueBuyTicketListener=onClickContinueBuyTicket;
    }
/*
public static SelectPositionFragment newInstance(SubMenuModel[] subMenuModels) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putParcelableArray(KEY_MODEL, subMenuModels);
        fragment.setArguments(args);
        return fragment;
    }
*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null) {
            throw new RuntimeException("You must to send a subMenuModels ");
        }

        Logger.d("",getArguments().getString(KEY_MODEL));
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
        getAllBoxesRequest();
        //btnBackToDetail.setOnClickListener(this);
        tvP.setOnClickListener(this);
        tvM.setOnClickListener(this);
        btnPaymentConfirm.setOnClickListener(this);

        colorPickerView = view.findViewById(R.id.colorPickerView);
        ivDefault = view.findViewById(R.id.ivDefault);
        ivSelected=view.findViewById(R.id.ivSelected);
        rlImageViewsFull = view.findViewById(R.id.rlImageViewsFull);

        getMatchRequest();
        setDataStadiumPosition();
        setFullPositions();
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

        PersianDateFormat pdformater2 = new PersianDateFormat("y F j l");
        date =String.valueOf(pdformater2.format(pdate));//۱۹ تیر ۹۶



        return date;
    }

    private void setImageColor(ImageView imageView, String link)
    {
        try
        {
            Picasso.with(getContext()).load(Uri.parse(link)).centerCrop().resize(imageView.getMeasuredWidth(), imageView.getMeasuredHeight()).into(imageView, new Callback()
            {
                @Override
                public void onSuccess()
                {
                }

                @Override
                public void onError()
                {
                    Picasso.with(getContext()).load(R.drawable.img_failure).into(imageView);
                }
            });
        } catch (NullPointerException e)
        {
            Picasso.with(getContext()).load(R.drawable.img_failure).into(imageView);
        }
    }
    private void getMatchRequest()
    {
        SingletonService.getInstance().getTicketServices().getMatch(new OnServiceStatus<WebServiceClass<ResponseMatch>>()
        {
            @Override
            public void onReady(WebServiceClass<ResponseMatch> response)
            {
                try
                {
                    if (!response.data.getResults().isEmpty()){
                        matchId=response.data.getResults().get(0).getId();
                        llSliderItemMatch.setVisibility(View.VISIBLE);
                        tvStadiumName.setText(response.data.getResults().get(0).getStadium().getName());
                        setImageColor(imgHost, response.data.getResults().get(0).getTeamHome().getLogo());
                        setImageColor(imgGuest, response.data.getResults().get(0).getTeamAway().getLogo());
                        tvDateTime.setText(getDate(response.data.getResults().get(0).getMatchDatetime()));
                    }
                        // progress.setVisibility(View.GONE);
                }catch (Exception e){
                    Tools.showToast(getContext(),e.getMessage(),R.color.red);
                    llSliderItemMatch.setVisibility(View.GONE);

                }
            }

            @Override
            public void onError(String message)
            {
                Tools.showToast(getContext(),message,R.color.red);
                llSliderItemMatch.setVisibility(View.GONE);
                // progress.setVisibility(View.GONE);
            }
        });
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
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;
                case 2:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;
                case 3:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;
                case 4:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;
                case 5:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;

                case 6:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;
                case 7:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;
                case 8:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;
                case 9:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;
                case 10:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;

                case 11:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;
                case 12:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;
                case 13:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;
                case 14:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;
                case 15:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;

                case 16:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;
                case 17:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;
                case 18:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;
                case 19:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;
                case 20:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;

                case 21:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;
                case 22:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;
                case 23:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;
                case 24:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;
                case 25:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;

                case 26:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;
                case 27:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;
                case 28:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;
                case 29:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;
                case 30:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;

                case 31:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;
                case 32:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;
                case 33:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;
                case 34:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;
                case 35:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;

                case 36:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;
                case 37:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;
                case 38:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;
                case 39:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;
                case 40:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;

                case 41:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;
                case 42:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;
                case 43:
                    imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;
                case 44:
                     imgView.setImageResource(R.drawable.ic_fourteen_full);
                    break;
                case 45:
                    break;

            }


        }
    }

    private void setFullPositions()
    {
        fullFromServer.add(6);
        //fullFromServer.add(14);
    }

    private void setDataStadiumPosition()
    {
        stadiumPositionModels.add(new StadiumPositionModel("FF328DAA", 1));
        stadiumPositionModels.add(new StadiumPositionModel("FF953d3d", 2));
        stadiumPositionModels.add(new StadiumPositionModel("FFfe9000", 3));
        stadiumPositionModels.add(new StadiumPositionModel("FFfffc9b", 4));
        stadiumPositionModels.add(new StadiumPositionModel("FF00ac62", 5));

        stadiumPositionModels.add(new StadiumPositionModel("FF8A3D7D", 6));
        stadiumPositionModels.add(new StadiumPositionModel("FF9ab260", 7));
        stadiumPositionModels.add(new StadiumPositionModel("FFff8181", 8));
        stadiumPositionModels.add(new StadiumPositionModel("FF0f0060", 9));
        stadiumPositionModels.add(new StadiumPositionModel("FFffc170", 10));

        stadiumPositionModels.add(new StadiumPositionModel("FFC778FF", 11));
        stadiumPositionModels.add(new StadiumPositionModel("FFC778FF", 12));
        stadiumPositionModels.add(new StadiumPositionModel("FFC778FF", 13));
        stadiumPositionModels.add(new StadiumPositionModel("FFFE0002", 14));
        stadiumPositionModels.add(new StadiumPositionModel("FFC778FF", 15));

        stadiumPositionModels.add(new StadiumPositionModel("FFC778FF", 16));
        stadiumPositionModels.add(new StadiumPositionModel("FFC778FF", 17));
        stadiumPositionModels.add(new StadiumPositionModel("FFC778FF", 18));
        stadiumPositionModels.add(new StadiumPositionModel("FFC778FF", 19));
        stadiumPositionModels.add(new StadiumPositionModel("FFC778FF", 20));

        stadiumPositionModels.add(new StadiumPositionModel("FFC778FF", 21));
        stadiumPositionModels.add(new StadiumPositionModel("FFC778FF", 22));
        stadiumPositionModels.add(new StadiumPositionModel("FFC778FF", 23));
        stadiumPositionModels.add(new StadiumPositionModel("FFC778FF", 24));
        stadiumPositionModels.add(new StadiumPositionModel("FFC778FF", 25));

        stadiumPositionModels.add(new StadiumPositionModel("FFC778FF", 26));
        stadiumPositionModels.add(new StadiumPositionModel("FFC778FF", 27));
        stadiumPositionModels.add(new StadiumPositionModel("FFC778FF", 28));
        stadiumPositionModels.add(new StadiumPositionModel("FFC778FF", 29));
        stadiumPositionModels.add(new StadiumPositionModel("FFC778FF", 30));

        stadiumPositionModels.add(new StadiumPositionModel("FFC778FF", 31));
        stadiumPositionModels.add(new StadiumPositionModel("FFC778FF", 32));
        stadiumPositionModels.add(new StadiumPositionModel("FFC778FF", 33));
        stadiumPositionModels.add(new StadiumPositionModel("FFC778FF", 34));
        stadiumPositionModels.add(new StadiumPositionModel("FFC778FF", 35));

        stadiumPositionModels.add(new StadiumPositionModel("FFC778FF", 36));
        stadiumPositionModels.add(new StadiumPositionModel("FFC778FF", 37));
        stadiumPositionModels.add(new StadiumPositionModel("FFC778FF", 38));
        stadiumPositionModels.add(new StadiumPositionModel("FFC778FF", 39));
        stadiumPositionModels.add(new StadiumPositionModel("FFC778FF", 40));

        stadiumPositionModels.add(new StadiumPositionModel("FFC778FF", 41));
        stadiumPositionModels.add(new StadiumPositionModel("FFC778FF", 42));
        stadiumPositionModels.add(new StadiumPositionModel("FFC778FF", 43));
        stadiumPositionModels.add(new StadiumPositionModel("FFC778FF", 44));
        stadiumPositionModels.add(new StadiumPositionModel("FFC778FF", 45));


    }

    private void handleSetStadiumLayouts()
    {
        colorPickerView.setColorListener(new ColorEnvelopeListener() {
            @Override
            public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {

                Logger.e("tessstt", envelope.getHexCode() + "");

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
                       // ivDefault.setImageResource(R.drawable.ic_test_stadium_selected);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case "FF953d3d":
                       // ivDefault.setImageResource(R.drawable.ic_test_stadium_selected);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case "FFFE0002":
                        ivSelected.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                   /* case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                        /////5
                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                        ////////10

                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                        ////15

                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                        ////20

                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                        ///////25

                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                        //////30

                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                        //////////35

                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                        //////40

                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_fourteen_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                        //////////45*/
                }

                Logger.e("tessstt", envelope.getHexCode() + "");
            }
        });
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

    private void setAmounts(List<AllBoxesResult> results)
    {
        amountForPay = results.get(0).getTicketAmount() * count;
        amountOneTicket=results.get(0).getTicketAmount();
        tvAmountStation.setText("قیمت بلیط این جایگاه :"+ Utility.priceFormat(results.get(0).getTicketAmount().toString()) +" ریال");
        tvAmountForPay.setText("مبلغ قابل پرداخت "+Utility.priceFormat(String.valueOf(amountForPay)) + " ریال");
    }

    private void setDataSpinnerAllBoxes(List<AllBoxesResult> result)
    {
        allBoxes = new ArrayList<String>();
       // selectPositionId = new ArrayList<Integer>();

        for (int i = 0; i < result.size(); i++) {
            allBoxes.add(result.get(i).getName());
           // selectPositionId.add(result.get(i).getId());
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
                if (spinnerAllBoxes.getSelectedItemPosition() == 0)
                {
                } else
                {
                    //TODO if seat count 0 deleted check id
                    AllBoxesResult item = result.get(position);
                    selectPositionId = item.getId();
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

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.btnPaymentConfirm:
                onClickContinueBuyTicketListener.onContinueClicked();
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

