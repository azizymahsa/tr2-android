package ir.trap.tractor.android.ui.fragments.ticket;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

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
        extends Fragment
{

    private static final String KEY_MODEL = "KEY_MODEL";
    TextView tvTitle;
   // private SubMenuModel[] subMenuModels;
    private OnListFragmentInteractionListener interactionListener;
    @BindView(R.id.spinnerAllBoxes)
    Spinner spinnerAllBoxes;
    private ArrayList<String> allBoxes;
    private List<AllBoxesResult> allBoxesResult=new ArrayList<>();

    public SelectPositionFragment() {
    }

    /**
     * Receive the model list
     */
    public static SelectPositionFragment newInstance(String s) {
        SelectPositionFragment fragment = new SelectPositionFragment();
       Bundle args = new Bundle();
        args.putString(KEY_MODEL, s);
        fragment.setArguments(args);

        return fragment;
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

        getAllBoxesRequest();
      // allBoxesResult.add(new AllBoxesResult(0,"test",4));
       // setDataSpinnerAllBoxes(allBoxesResult);
       /* RecyclerView recyclerView = (RecyclerView) view;

        recyclerView.setLayoutManager(new GridLayoutManager(context,3));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(new ItemRecyclerViewAdapter(subMenuModels, interactionListener));*/
        return view;
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
                    }else {
                        Tools.showToast(getContext(),response.info.message,R.color.red);
                    }
                   /* btnMyBills.revertAnimation(BillFragment.this);
                    btnMyBills.setClickable(true);
                    if (response.info.statusCode == 200) {

                        onGetMyBillsServiceSuccess(response.data.getResults());

                    } else {
                        Tools.showToast(getContext(),response.info.message,R.color.red);
                    }*/
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
      //  billsTypePosition = new ArrayList<Integer>();

        for (int i = 0; i < result.size(); i++) {
            allBoxes.add(result.get(i).getName());
          //  billsTypePosition.add(billActiveVm.get(i).getId());
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
                   // simcardType = SIMCARD_TYPE_ETEBARI;
                } else
                {
                   // simcardType = SIMCARD_TYPE_DAEMI;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * <p/>
     */
    public interface OnListFragmentInteractionListener {
       // void onListFragmentInteraction(SubMenuModel item);
    }
}

