package ir.trap.tractor.android.ui.fragments.favoriteCard;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Objects;

import ir.trap.tractor.android.R;
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

public class FavoriteCardFragment extends Fragment implements FavoriteCardActionView
{
    private ImageView ivLeft, ivRight;
    private ScrollingPagerIndicator indicator;

    private RecyclerView rvListCard;
    private LinearLayoutManager linearLayoutManager;


    public FavoriteCardFragment()
    {
        // Required empty public constructor
    }

    public static FavoriteCardFragment newInstance()
    {
        FavoriteCardFragment fragment = new FavoriteCardFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_favorite_card, container, false);

        rvListCard = rootView.findViewById(R.id.rvListCard);
        ivRight = rootView.findViewById(R.id.ivRight);
        ivLeft = rootView.findViewById(R.id.ivLeft);
        indicator = rootView.findViewById(R.id.indicator);

        ivLeft.setOnClickListener(clickListener);
        ivRight.setOnClickListener(clickListener);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvListCard.setLayoutManager(linearLayoutManager);


        return rootView;
    }

    View.OnClickListener clickListener = view ->
    {
        if (view.getId() == R.id.ivLeft)
        {

        }
        else if (view.getId() == R.id.ivRight)
        {

        }
    };

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener)
//        {
//            mListener = (OnFragmentInteractionListener) context;
//        } else
//        {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
//        mListener = null;
    }

    @Override
    public void onSlideRight()
    {
        if (linearLayoutManager.findFirstCompletelyVisibleItemPosition() == Objects.requireNonNull(rvListCard.getAdapter()).getItemCount())
            return;
        int newPos = linearLayoutManager.findFirstCompletelyVisibleItemPosition() + 1;

        rvListCard.smoothScrollToPosition(newPos);

        if (newPos == Objects.requireNonNull(rvListCard.getAdapter()).getItemCount())
        {
            ivRight.setVisibility(View.INVISIBLE);
        }
        else
        {
            ivRight.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSlideLeft()
    {
        if (linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0)
            return;

        int newPos = linearLayoutManager.findFirstCompletelyVisibleItemPosition() - 1;

        rvListCard.smoothScrollToPosition(newPos);

        if (newPos == 0)
        {
            ivLeft.setVisibility(View.INVISIBLE);
        }
        else
        {
            ivLeft.setVisibility(View.VISIBLE);
        }
    }

//    public interface OnFragmentInteractionListener
//    {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
