package com.example.ai.mapsearch;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ai.mapsearch.API.ApiClient;
import com.example.ai.mapsearch.API.RetrofitMaps;
import com.example.ai.mapsearch.Data.PrefManager;
import com.example.ai.mapsearch.Utils.Constant;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EditText etSearch, etLonitude, etLatitude;
    private Button btnSearch, btnLatLng, btnSetDestination;
    private String location;
    private Double longitude, latitude;
    private RetrofitMaps retrofitMaps;
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        retrofitMaps = ApiClient.createClient().create(RetrofitMaps.class);
        prefManager = new PrefManager(getApplicationContext());

        etSearch = (EditText) findViewById(R.id.etSearch);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        etLatitude = (EditText) findViewById(R.id.etLatitude);
        etLonitude = (EditText) findViewById(R.id.etLongitude);
        btnLatLng = (Button) findViewById(R.id.btnLatLng);
        btnSetDestination = (Button) findViewById(R.id.btnSetDestination);

        btnSetDestination.setOnClickListener(buttonOperation);
        btnSearch.setOnClickListener(buttonOperation);
        btnLatLng.setOnClickListener(buttonOperation);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }

    public void onMapSearch(String type){
        mMap.clear();
        List<Address> addressList = null;
        Geocoder geocoder = new Geocoder(this);
        if(type.equals("location")){
            location = etSearch.getText().toString();
            try {
                addressList = geocoder.getFromLocationName(location, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            etLonitude.setText(String.valueOf(addressList.get(0).getLongitude()));
            etLatitude.setText(String.valueOf(addressList.get(0).getLatitude()));

        }else{
            longitude = Double.parseDouble(etLonitude.getText().toString());
            latitude = Double.parseDouble(etLatitude.getText().toString());
            try {
                addressList = geocoder.getFromLocation(latitude, longitude, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            etSearch.setText(addressList.get(0).getAddressLine(0));
        }

        Address address = addressList.get(0);
        LatLng latlng = new LatLng(address.getLatitude(), address.getLongitude());
        mMap.addMarker(new MarkerOptions().position(latlng).title(address.getAddressLine(0)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(20));



    }



    private View.OnClickListener buttonOperation = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btnSearch:
                    if(!etSearch.getText().toString().isEmpty()){
                        onMapSearch("location");
                    }

                    break;
                case R.id.btnLatLng:
                    if(!etLonitude.getText().toString().isEmpty() || etLonitude.getText().toString().isEmpty()){
                        onMapSearch("coordinate");
                    }
                    break;

                case R.id.btnSetDestination:
                    sendDestination();
                    break;

            }
        }
    };

    private void sendDestination(){
        Call<com.example.ai.mapsearch.Model.Response> call = retrofitMaps.setDestination(prefManager.getUserId(), etSearch.getText().toString(), etLonitude.getText().toString(), etLatitude.getText().toString());
        call.enqueue(new Callback<com.example.ai.mapsearch.Model.Response>() {
            @Override
            public void onResponse(Response<com.example.ai.mapsearch.Model.Response> response, Retrofit retrofit) {
                Log.d(Constant.TAG, "Set Destination " + response.raw());
                if(response.body().getCode().equals("200")){
                    Toast.makeText(MapsActivity.this, "Destination Set!", Toast.LENGTH_SHORT);
                    startActivity(new Intent(MapsActivity.this, DestinationActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(Constant.TAG, "Set Destination Error " + t.toString());
            }
        });
    }
}


