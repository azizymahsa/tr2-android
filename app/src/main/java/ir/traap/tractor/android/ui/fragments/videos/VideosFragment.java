package ir.traap.tractor.android.ui.fragments.videos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.SnapHelper;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.yarolegovich.discretescrollview.DiscreteScrollView;

import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.generator.SingletonService;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.mainVideos.MainVideoRequest;
import ir.traap.tractor.android.apiServices.model.mainVideos.MainVideosResponse;
import ir.traap.tractor.android.apiServices.part.GetMainVideosService;
import ir.traap.tractor.android.ui.adapters.video.NewestVideosAdapter;
import ir.traap.tractor.android.ui.base.BaseFragment;
import ir.traap.tractor.android.ui.fragments.main.MainActionView;
import ir.traap.tractor.android.utilities.Tools;
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

/**
 * Created by MahtabAzizi on 11/23/2019.
 */
public class VideosFragment extends BaseFragment
{
    private MainActionView mainView;
    private View rootView;
    private DiscreteScrollView scrollViewNewestVideo;
    private ScrollingPagerIndicator indicator;
    private LinearLayoutManager sliderLayoutManager;

    public VideosFragment()
    {

    }

    public static VideosFragment newInstance(MainActionView mainView)
    {
        VideosFragment fragment = new VideosFragment();
        fragment.setMainView(mainView);
        return fragment;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        YoYo.with(Techniques.FadeIn)
                .duration(700)
                .playOn(rootView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (rootView != null)
            return rootView;
        rootView = inflater.inflate(R.layout.fragment_videos, container, false);

        mainView.showLoading();
        //((TextView) rootView.findViewById(R.id.tvTitle));

        scrollViewNewestVideo = rootView.findViewById(R.id.dsNewestVideo);
        indicator = rootView.findViewById(R.id.indicator);

      //  scrollViewNewestVideo.setOrientation(DSVOrientation o); //Sets an orientation of the view
        scrollViewNewestVideo.setOffscreenItems(3); //Reserve extra space equal to (childSize * count) on each side of the view
        scrollViewNewestVideo.setOverScrollEnabled(true); //Can also be set using android:overScrollMode xml attribute

        scrollViewNewestVideo.getCurrentItem(); //returns adapter position of the currently selected item or -1 if adapter is empty.
      /*  scrollViewNewestVideo.scrollToPosition(int position); //position becomes selected
        scrollViewNewestVideo.smoothScrollToPosition(int position); //position becomes selected with animated scroll
     */   scrollViewNewestVideo.setItemTransitionTimeMillis(2000); //determines how much time it takes to change the item on fling,
        scrollViewNewestVideo.setSlideOnFling(true);

        requestMainVideos();
        
       /* scrollView.setOrientation(DSVOrientation o); //Sets an orientation of the view
        scrollView.setOffscreenItems(count); //Reserve extra space equal to (childSize * count) on each side of the view
        scrollView.setOverScrollEnabled(enabled);*/
        return rootView;
    }

    private void requestMainVideos()
    {
        MainVideoRequest request = new MainVideoRequest();

        SingletonService.getInstance().getMainVideosService().getMainVideos(new OnServiceStatus<WebServiceClass<MainVideosResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<MainVideosResponse> response)
            {
                mainView.hideLoading();
                try {

                    if (response.info.statusCode == 200) {

                        onGetMainVideosSuccess(response.data);

                    } else {
                        Tools.showToast(getContext(),response.info.message,R.color.red);
                    }
                } catch (Exception e) {
                    Tools.showToast(getContext(),e.getMessage(),R.color.red);

                }
            }

            @Override
            public void onError(String message)
            {
              mainView.hideLoading();
                Tools.showToast(getActivity(),message,R.color.red);
            }
        }, request);
    }

    private void onGetMainVideosSuccess(MainVideosResponse mainVideosResponse)
    {
        scrollViewNewestVideo.setAdapter(new NewestVideosAdapter(mainVideosResponse.getRecent(),mainView));
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(scrollViewNewestVideo);
        indicator.attachToRecyclerView(scrollViewNewestVideo);
    }
}
