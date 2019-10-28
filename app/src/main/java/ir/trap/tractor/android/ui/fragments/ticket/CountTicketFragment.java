package ir.trap.tractor.android.ui.fragments.ticket;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import ir.trap.tractor.android.R;

public class CountTicketFragment
        extends Fragment implements View.OnClickListener
{

    private static final String KEY_MODEL = "KEY_MODEL";
    private OnClickContinueBuyTicket onClickContinueBuyTicketListener;
    private View btnBackToDetail;
    private View btnPaymentConfirm;

    public CountTicketFragment() {
    }

    /**
     * Receive the model list
     */
    public static CountTicketFragment newInstance(String s,OnClickContinueBuyTicket onClickContinueBuyTicket) {
        CountTicketFragment fragment = new CountTicketFragment();
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

        Log.d("SSSSSSSSSSSSSSSSSS:",getArguments().getString(KEY_MODEL));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.count_ticket_fragment, container, false);
        // tvTitle=view.findViewById(R.id.tvTitle);
        Context context = view.getContext();
        btnBackToDetail=view.findViewById(R.id.btnBackToDetail);
        btnPaymentConfirm=view.findViewById(R.id.btnPaymentConfirm);
        btnBackToDetail.setOnClickListener(this);
        btnPaymentConfirm.setOnClickListener(this);
       /* RecyclerView recyclerView = (RecyclerView) view;

        recyclerView.setLayoutManager(new GridLayoutManager(context,3));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(new ItemRecyclerViewAdapter(subMenuModels, interactionListener));*/
        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // activity must implement OnListFragmentInteractionListener
      /*  if (context instanceof OnListFragmentInteractionListener) {
            interactionListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
      //  interactionListener = null;
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
}

