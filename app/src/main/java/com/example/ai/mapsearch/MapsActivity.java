package com.example.ai.mapsearch;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EditText etSearch, etLonitude, etLatitude;
    private Button btnSearch, btnLatLng;
    private String location;
    private Double longitude, latitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        etSearch = (EditText) findViewById(R.id.etSearch);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        etLatitude = (EditText) findViewById(R.id.etLatitude);
        etLonitude = (EditText) findViewById(R.id.etLongitude);
        btnLatLng = (Button) findViewById(R.id.btnLatLng);

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
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }

    public void onMapSearch(String type){

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
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latlng));



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

            }
        }
    };

}


