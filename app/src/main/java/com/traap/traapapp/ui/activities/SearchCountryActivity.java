package com.traap.traapapp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.traap.traapapp.R;
import com.traap.traapapp.models.CountryCodeModel;
import com.traap.traapapp.ui.adapters.CountryAdapter;
import com.traap.traapapp.ui.base.BaseActivity;
import com.traap.traapapp.utilities.Utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SearchCountryActivity extends BaseActivity
{
    private ImageView imgSearch,imgBack1;
    private RelativeLayout imgBack;
    private MaterialSearchView search_view;
    private RecyclerView rvCountry;
    private ArrayList<CountryCodeModel> countryCodeModels=new ArrayList<>();
    private ArrayList<CountryCodeModel> countryCodeModelsFilter=new ArrayList<>();
    private CountryAdapter countryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_country);
        initCountryCode();
        initView();
    }

    private void initView()
    {
        imgSearch=findViewById(R.id.imgSearch);
        search_view=findViewById(R.id.search_view);
        rvCountry=findViewById(R.id.rvCountry);
        imgBack1=findViewById(R.id.imgBack1);
        imgBack=findViewById(R.id.imgBack);
        imgBack.setOnClickListener(v ->
        {
           finish();
        });

        countryAdapter=new CountryAdapter(this,countryCodeModelsFilter, codeModel ->
        {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("name",codeModel.getName());
            returnIntent.putExtra("code",codeModel.getDialCode());
            setResult(Activity.RESULT_OK,returnIntent);
            finish();


        });
        rvCountry.setAdapter(countryAdapter);



        imgSearch.setOnClickListener(v ->
        {
            search_view.showSearch();
        });



        search_view.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                if (newText.equals("")){
                    countryCodeModelsFilter.clear();
                    countryCodeModelsFilter.addAll(countryCodeModels);
                    countryAdapter.notifyDataSetChanged();
                    return false;
                }


                Observable
                        .fromIterable(countryCodeModels)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.computation())
                        .filter(x -> {return x.getName().toLowerCase().contains(Utility.convertNumbersToEnglish(newText))||x.getDialCode().toLowerCase().contains(Utility.convertNumbersToEnglish(newText));})
                        .toList()
                        .subscribe(new SingleObserver<List<CountryCodeModel>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onSuccess(List<CountryCodeModel> codeModels) {
                                if (codeModels.size()>0){
                                    countryCodeModelsFilter.clear();
                                    countryCodeModelsFilter.addAll(codeModels);
                                    countryAdapter.notifyDataSetChanged();
                                }



                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("sdsdf", e.getMessage() );
                            }
                        });
                return false;
            }
        });

    }
    private void initCountryCode()
    {
        Gson gson = new Gson();
        String json = null;
        try {
            InputStream inputStream = getAssets().open("country.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }

        countryCodeModels= gson.fromJson(json,
                new TypeToken<ArrayList<CountryCodeModel>>() {
                }.getType());
        countryCodeModelsFilter.addAll(countryCodeModels);

    }

}
