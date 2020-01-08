package com.traap.traapapp.ui.fragments.photo.archive;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.mainVideos.Category;
import com.traap.traapapp.apiServices.model.mainVideos.ListCategory;
import com.traap.traapapp.apiServices.model.photo.archive.PhotoArchiveResponse;
import com.traap.traapapp.enums.MediaArchiveCategoryCall;
import com.traap.traapapp.models.otherModels.mediaModel.MediaDetailsPositionIdsModel;
import com.traap.traapapp.ui.activities.photo.AlbumDetailActivity;
import com.traap.traapapp.ui.activities.photo.ShowBigPhotoActivity;
import com.traap.traapapp.ui.adapters.photo.PhotosArchiveAdapter;
import com.traap.traapapp.ui.adapters.photo.PhotosCategoryTitleAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;

import java.util.ArrayList;
import java.util.List;

public class PhotosArchiveCategoryFragment extends BaseFragment implements OnServiceStatus<WebServiceClass<PhotoArchiveResponse>>,
        PhotosArchiveAdapter.ArchivePhotosListener ,PhotosCategoryTitleAdapter.TitleCategoryListener
{
    private View rootView;
    private String Ids, filterStartDate, filterEndDate, filterSearchText;

    private ProgressBar progressBar;
    private PhotosArchiveAdapter adapter;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private TextView tvEmpty;
    private MediaArchiveCategoryCall callFrom;


    private ArrayList<Category> photosContentList = new ArrayList<>();

    public PhotosArchiveCategoryFragment()
    {
    }

    public static PhotosArchiveCategoryFragment newInstance(String IDs,
                                                            MediaArchiveCategoryCall callFrom,
                                                            @Nullable String filterStartDate,
                                                            @Nullable String filterEndDate,
                                                            @Nullable String filterSearchText,
                                                            @Nullable List<Category> photosContentList)
    {
        PhotosArchiveCategoryFragment fragment = new PhotosArchiveCategoryFragment();
        fragment.setCallFrom(callFrom);

        Bundle arg = new Bundle();
        arg.putString("Ids", IDs);
        arg.putString("filterStartDate", filterStartDate != null ? filterStartDate : "");
        arg.putString("filterEndDate", filterEndDate != null ? filterEndDate : "");
        arg.putString("filterSearchText", filterSearchText != null ? filterSearchText : "");
        arg.putBoolean("pagerWithFilter", false);

        if (callFrom == MediaArchiveCategoryCall.FROM_SINGLE_CONTENT)
        {
            arg.putParcelableArrayList("photosContentList", (ArrayList<? extends Parcelable>) photosContentList);
        }

        Logger.e("-Ids 0-", IDs);

        fragment.setArguments(arg);
        return fragment;
    }

    private void setCallFrom(MediaArchiveCategoryCall callFrom)
    {
        this.callFrom = callFrom;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            Ids = getArguments().getString("Ids");

            filterStartDate = getArguments().getString("filterStartDate");
            filterEndDate = getArguments().getString("filterEndDate");
            filterSearchText = getArguments().getString("filterSearchText");

            photosContentList = getArguments().getParcelableArrayList("photosContentList");

//            Logger.e("-Ids 1-", Ids + " # " + pagerWithFilter);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if (rootView != null)
        {
            return rootView;
        }
        rootView = inflater.inflate(R.layout.fragment_photos_archive_category, container, false);

        progressBar = rootView.findViewById(R.id.progressbar);
        tvEmpty = rootView.findViewById(R.id.tvEmpty);

        recyclerView = rootView.findViewById(R.id.recyclerView);

        layoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(layoutManager);

        if (callFrom == MediaArchiveCategoryCall.FROM_ID)
        {
            Logger.e("-Ids 2-", Ids);
            SingletonService.getInstance().getPhotoArchiveService().getArchivePhoto(Ids, this);
        }
        if (callFrom == MediaArchiveCategoryCall.FROM_FILTER_IDs)
        {
            Logger.e("-Ids 2-", Ids);
            SingletonService.getInstance().getPhotoArchiveService().getArchivePhotoByIds(
                    Ids,
                    filterStartDate,
                    filterEndDate,
                    filterSearchText,
                    this
            );
        }
        else if (callFrom == MediaArchiveCategoryCall.FROM_FAVORITE)
        {
            SingletonService.getInstance().getPhotoArchiveService().getBookMarkPhoto(this);
        }
        else if (callFrom == MediaArchiveCategoryCall.FROM_SINGLE_CONTENT)
        {
            progressBar.setVisibility(View.GONE);
            Logger.e("--photosContentList size--", "Size: " + photosContentList.size());

            layoutManager = new GridLayoutManager(getActivity(), 1);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new PhotosArchiveAdapter(photosContentList, false, this);
            recyclerView.setAdapter(adapter);

            adapter.notifyDataSetChanged();

            if (photosContentList.isEmpty())
            {
                tvEmpty.setVisibility(View.VISIBLE);
            }
        }

        return rootView;
    }


    @Override
    public void onReady(WebServiceClass<PhotoArchiveResponse> response)
    {
        if (response.info.statusCode != 200)
        {
            showError(getActivity(), response.info.message);
        }
        else
        {
            photosContentList = response.data.getResults();

            Boolean FLAG_Favorite = false;
            if (callFrom == MediaArchiveCategoryCall.FROM_FAVORITE)
            {
                FLAG_Favorite = true;
            }
            adapter = new PhotosArchiveAdapter(photosContentList, FLAG_Favorite, this);
            recyclerView.setAdapter(adapter);

            adapter.notifyDataSetChanged();

            if (photosContentList.isEmpty())
            {
                tvEmpty.setVisibility(View.VISIBLE);
            }
        }

        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void onError(String message)
    {
        progressBar.setVisibility(View.GONE);

        if (Tools.isNetworkAvailable(getActivity()))
        {
            Logger.e("-OnError-", "Error: " + message);
            showError(getActivity(), "خطا در دریافت اطلاعات از سرور!");
        }
        else
        {
            showAlert(getActivity(), R.string.networkErrorMessage, R.string.networkError);
        }
    }


    @Override
    public void onItemTitleCategoryClick(ListCategory category)
    {

        List<MediaDetailsPositionIdsModel> positionIdsList = new ArrayList<>();
        for (int i = 0; i < photosContentList.size(); i++)
        {
            MediaDetailsPositionIdsModel model = new MediaDetailsPositionIdsModel();
            model.setId(photosContentList.get(i).getId());
            model.setPosition(i);

            positionIdsList.add(model);
        }

    }

    @Override
    public void onItemArchiveVideoClick(int position, Category category, ArrayList<Category> recent)
    {
        List<MediaDetailsPositionIdsModel> positionIdsList = new ArrayList<>();
        for (int i = 0; i < photosContentList.size(); i++)
        {
            MediaDetailsPositionIdsModel model = new MediaDetailsPositionIdsModel();
            model.setId(photosContentList.get(i).getId());
            model.setPosition(i);

            positionIdsList.add(model);
        }

        if (callFrom == MediaArchiveCategoryCall.FROM_FAVORITE)
        {
            Intent intent = new Intent(getActivity(), ShowBigPhotoActivity.class);

            intent.putExtra("SRCImage", category.getImageName().getThumbnailLarge());
            intent.putExtra("LikeCount", category.getLikes());
            intent.putExtra("idPhoto", category.getId());
            intent.putExtra("isLike", category.getIsLiked());
            intent.putExtra("isBookmark", category.getIsBookmarked());
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(getActivity(), AlbumDetailActivity.class);

            intent.putParcelableArrayListExtra("Photos", recent);
            intent.putExtra("IdPhotoCategory", category.getId());
            intent.putExtra("IdPhoto", category.getId());
            intent.putExtra("positionPhoto", position);

            startActivity(intent);
        }
    }

}
