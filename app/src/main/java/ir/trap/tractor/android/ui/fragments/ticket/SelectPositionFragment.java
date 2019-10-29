package ir.trap.tractor.android.ui.fragments.ticket;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import ir.trap.tractor.android.R;
import ir.trap.tractor.android.apiServices.generator.SingletonService;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.getAllBoxes.AllBoxesResult;
import ir.trap.tractor.android.apiServices.model.getAllBoxes.GetAllBoxesRequest;
import ir.trap.tractor.android.apiServices.model.getAllBoxes.GetAllBoxesResponse;
import ir.trap.tractor.android.utilities.Tools;

public class SelectPositionFragment
        extends Fragment implements View.OnClickListener
{

    ColorPickerView colorPickerView;
    ImageView ivDefault;
    RelativeLayout rlImageViews;
    private static final String KEY_MODEL = "KEY_MODEL";
    TextView tvTitle;
   // private SubMenuModel[] subMenuModels;
    private OnListFragmentInteractionListener interactionListener;
   /* @BindView(R.id.spinnerAllBoxes)
    Spinner spinnerAllBoxes;*/
    private ArrayList<String> allBoxes;
    private Spinner spinnerAllBoxes;
    private Integer selectPositionId;
    private View btnBackToDetail;
    private View btnPaymentConfirm;
    private OnClickContinueBuyTicket onClickContinueBuyTicketListener;
    private List<AllBoxesResult> allBoxesResponse;
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

        Log.d("",getArguments().getString(KEY_MODEL));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_position_fragment, container, false);
        // tvTitle=view.findViewById(R.id.tvTitle);
        Context context = view.getContext();
        spinnerAllBoxes=view.findViewById(R.id.spinnerAllBoxes);
        btnBackToDetail=view.findViewById(R.id.btnBackToDetail);
        btnPaymentConfirm=view.findViewById(R.id.btnPaymentConfirm);
        getAllBoxesRequest();
        btnBackToDetail.setOnClickListener(this);
        btnPaymentConfirm.setOnClickListener(this);

        colorPickerView = view.findViewById(R.id.colorPickerView);
        ivDefault = view.findViewById(R.id.ivDefault);
        rlImageViews = view.findViewById(R.id.rlImageViews);

        handleSetStadiumLayouts();

      // allBoxesResult.add(new AllBoxesResult(0,"test",4));
       // setDataSpinnerAllBoxes(allBoxesResult);
       /* RecyclerView recyclerView = (RecyclerView) view;

        recyclerView.setLayoutManager(new GridLayoutManager(context,3));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(new ItemRecyclerViewAdapter(subMenuModels, interactionListener));*/
        return view;
    }

    private void handleSetStadiumLayouts()
    {
        colorPickerView.setColorListener(new ColorEnvelopeListener() {
            @Override
            public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {

                Log.e("tessstt", envelope.getHexCode() + "");
              /*  if (allBoxesResponse.isEmpty()){
                        getAllBoxesRequest();
                    }else
                    {*/
                       /* if (envelope.getHexCode().equals(getString(R.string.full)))
                        {
                        Toast.makeText(getContext(), "این جایگاه پر شده است", Toast.LENGTH_SHORT).show();
                        return;
                    }*/
              /*      for (AllBoxesResult stadiomModel : allBoxesResponse)
                    {
                        if (envelope.getHexCode().equals(getString(R.string.full)))
                        {
                            for (Integer integer : fullFromServer)
                            {
                                if (integer.equals(Integer.valueOf(stadiomModel.getName())))
                                {
                                    Toast.makeText(getContext(), "این جایگاه پر شده است", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        }
                    }*//*
                }*/


                switch (envelope.getHexCode()) {

                    case "FF999900":
                        ivDefault.setImageResource(R.drawable.ic_test_stadium_full);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);

                        break;
                    case "FFFE0002":
                        ivDefault.setImageResource(R.drawable.ic_test_stadium_selected);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);

                        break;
                /*    case "FFDD78FF":
                        ivDefault.setImageResource(R.drawable.tes_207_selectt);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);

                        break;
                    case "FFF478FF":
                        ivDefault.setImageResource(R.drawable.tes_209_selectt);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);

                        break;
                    case "FFFF78DD":
                        ivDefault.setImageResource(R.drawable.tes_210_selectt);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);

                        break;
                    case "FFFF78C7":
                        ivDefault.setImageResource(R.drawable.tes_211_selectt);
                        ivDefault.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;*/
                }

                Log.e("tessstt", envelope.getHexCode() + "");
            }
        });
    }

    public void handleFullPositions(){
     /*   for (Integer integer : fullFromServer) {
            ImageView imgView = new ImageView(this.getContext());
            rlImageViews.addView(imgView);
            imgView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imgView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
            switch (integer) {
                case 205:
                    imgView.setImageResource(R.drawable.ic_test_stadium_full);
                    break;
                case 206:
                   // imgView.setImageResource(R.drawable.tes_206_fullt);
                    break;
                case 207:
                    break;
                case 209:
                    break;
                case 210:
                    break;
                case 211:
                    break;

            }


        }*/
    }
    private void getAllBoxesRequest()
    {
        Integer id=1;
        GetAllBoxesRequest request = new GetAllBoxesRequest();
        SingletonService.getInstance().getAllBoxesService().getAllBoxes(new OnServiceStatus<WebServiceClass<GetAllBoxesResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<GetAllBoxesResponse> response)
            {
                try {
                    if (response.info.statusCode==200){
                        setDataSpinnerAllBoxes(response.data.getResults());
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
        }, request,id);
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
            case R.id.btnBackToDetail:
                onClickContinueBuyTicketListener.onBackClicked();
                break;
        }
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

