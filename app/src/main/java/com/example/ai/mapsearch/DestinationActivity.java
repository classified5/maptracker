package com.example.ai.mapsearch;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.ai.mapsearch.API.ApiClient;
import com.example.ai.mapsearch.API.RetrofitMaps;
import com.example.ai.mapsearch.Adapter.DestinationAdapter;
import com.example.ai.mapsearch.Model.Destination;
import com.example.ai.mapsearch.Model.ResponseDestination;
import com.example.ai.mapsearch.Utils.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class DestinationActivity extends AppCompatActivity {

    private RecyclerView rvDestination;
    private DestinationAdapter destinationAdapter;
    private RetrofitMaps retrofitMaps;
    private List<Destination> destinationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvDestination = (RecyclerView) findViewById(R.id.rvDestination);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvDestination.setLayoutManager(layoutManager);
        rvDestination.setItemAnimator(new DefaultItemAnimator());
        rvDestination.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        retrofitMaps = ApiClient.createClient().create(RetrofitMaps.class);

        getDestination();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void getDestination(){
        Call<ResponseDestination> call = retrofitMaps.getAllDestination();
        call.enqueue(new Callback<ResponseDestination>() {
            @Override
            public void onResponse(Response<ResponseDestination> response, Retrofit retrofit) {
                Log.d(Constant.TAG, "Destination " + response.raw());

                if(response.body().getCode().equals("200")){
                    destinationList = response.body().getData();
                    Log.d(Constant.TAG, "Destination data " + destinationList.get(0).getDestinationName());

                    destinationAdapter = new DestinationAdapter(destinationList);
                    rvDestination.setAdapter(destinationAdapter);

                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(Constant.TAG, "Error " + t.toString());
            }
        });
    }
}
