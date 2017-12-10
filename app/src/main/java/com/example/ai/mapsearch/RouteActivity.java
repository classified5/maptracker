package com.example.ai.mapsearch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ai.mapsearch.API.ApiClient;
import com.example.ai.mapsearch.API.RetrofitMaps;
import com.example.ai.mapsearch.Background.LocationServices;
import com.example.ai.mapsearch.Model.Destination;
import com.example.ai.mapsearch.Model.Example;
import com.example.ai.mapsearch.Model.Participant;
import com.example.ai.mapsearch.Model.ResponseCoordinate;
import com.example.ai.mapsearch.Model.ResponseParticipant;
import com.example.ai.mapsearch.Utils.Constant;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class RouteActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button btnCurrent, btnRefresh;
    private LatLng origin;
    private LatLng dest;
    private ArrayList<LatLng> MarkerPoints;
    private TextView tvParticipant, tvUser;
    private Polyline line;
    private RetrofitMaps retrofitMaps;
    private double lonDest, latDest, lonOrg, latOrg;
    private Marker markerOrg = null, markerDest, markerParticipant;
    private List<Participant> participantList;
    private List<Marker> markerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        startService(new Intent(this, LocationServices.class));

        tvParticipant = (TextView) findViewById(R.id.tvParticipant);
        tvUser = (TextView) findViewById(R.id.tvUser);
        btnCurrent = (Button) findViewById(R.id.btnCurrent);
        btnRefresh = (Button) findViewById(R.id.btnDriving);

        retrofitMaps = ApiClient.createClient().create(RetrofitMaps.class);

        btnRefresh.setOnClickListener(buttonOperation);
        btnCurrent.setOnClickListener(buttonOperation);


//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            checkLocationPermission();
//        }

        MarkerPoints = new ArrayList<>();
        markerList = new ArrayList<Marker>();


        getDestination();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private View.OnClickListener buttonOperation = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.btnCurrent:
                    goToCurrentLocation();
                    break;
                case R.id.btnDriving:
                    getParticipant();
                    break;


            }
        }
    };

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        markerOrg = mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("You are here!"));
        markerParticipant = mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Participant!"));
//        mMap.setMyLocationEnabled(true);
        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

//        // Setting onclick event listener for the map
//        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//
//            @Override
//            public void onMapClick(LatLng point) {
//
//                // clearing map and generating new marker points if user clicks on map more than two times
//                if (MarkerPoints.size() > 1) {
////                    mMap.clear();
////                    MarkerPoints.clear();
//                    MarkerPoints = new ArrayList<>();
//                    tvParticipant.setText("");
//                }
//
//                // Adding new item to the ArrayList
//                MarkerPoints.add(point);
//
//                // Creating MarkerOptions
//                MarkerOptions options = new MarkerOptions();
//
//                // Setting the position of the marker
//                options.position(point);
//
//                /**
//                 * For the start location, the color of marker is GREEN and
//                 * for the end location, the color of marker is RED.
//                 */
//                if (MarkerPoints.size() == 1) {
//                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
//                    latOrg = point.latitude;
//                    lonOrg = point.longitude;
//
//                } else if (MarkerPoints.size() == 2) {
//                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//                    latDest = point.latitude;
//                    lonDest = point.longitude;
//                }
//
//
//                // Add new marker to the Google Map Android API V2
//                mMap.addMarker(options);
//
//                // Checks, whether start and end locations are captured
//                if (MarkerPoints.size() >= 2) {
//                    origin = MarkerPoints.get(0);
////                    latOrg = MarkerPoints.get(0).latitude;
////                    lonOrg = MarkerPoints.get(0).longitude;
////                    latDest = MarkerPoints.get(1).latitude;
////                    lonDest = MarkerPoints.get(1).longitude;
//                    dest = MarkerPoints.get(1);
//                }
//
//            }
//        });

//        Button btnDriving = (Button) findViewById(R.id.btnDriving);
//        btnDriving.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getRoute("driving");
//            }
//        });

    }

    private void getRoute(Double latOrg, Double lonOrg, Double latDest, Double lonDest, final String type) {

        String url = "https://maps.googleapis.com/maps/api/directions/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitMaps service = retrofit.create(RetrofitMaps.class);

        Call<Example> call = service.getDistanceDuration("metric", latOrg + "," + lonOrg, latDest + "," + lonDest, "driving");

//        Log.d("log", "variable " + origin.latitude + " " + origin.longitude);

        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Response<Example> response, Retrofit retrofit) {
                Log.d("log", "Status " + response.raw() + "header " + response.headers() + "error " + response.errorBody());
                Log.d("log", "Response " + new Gson().toJson(response.body()));
                Log.d("log", "Length " + response.body().getRoutes().size());
                int finalTime = 0;
                try {
                    //Remove previous line from map
//                    if (line != null) {
//                        line.remove();
//                    }
                    // This loop will go through all the results and add marker on each location.
                    for (int i = 0; i < response.body().getRoutes().size(); i++) {
                        String distance = response.body().getRoutes().get(i).getLegs().get(i).getDistance().getText();
                        String time = response.body().getRoutes().get(i).getLegs().get(i).getDuration().getText();
                        String tempTime = time;
                        tempTime.trim();

                        String[] realTime = tempTime.split(" ");

                        Log.d("time", "real time " + realTime[0]);
                        finalTime += Integer.parseInt(realTime[0]);
                        Log.d("time", "final time " + finalTime);
                        Log.d("log", "Distance:" + distance + ", Duration:" + time);

                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                        String currentTime = dateFormat.format(calendar.getTime());
                        Date date = dateFormat.parse(currentTime);
                        calendar.setTime(date);
                        calendar.add(Calendar.MINUTE, finalTime);
                        String eta = dateFormat.format(calendar.getTime());

                        Log.d("time", "current time " + currentTime + " " + eta);


                        String encodedString = response.body().getRoutes().get(0).getOverviewPolyline().getPoints();
                        List<LatLng> list = decodePoly(encodedString);


                        if (type.equals("participant")){
                            tvParticipant.setText("Participant Data \nDistance: " + distance + "\nDuration: " + time + "\nETA: " + eta);
                            line = mMap.addPolyline(new PolylineOptions()
                                    .addAll(list)
                                    .width(20)
                                    .color(Color.GREEN)
                                    .geodesic(true)
                            );
                        }else{
                            tvUser.setText("User Data \nDistance: " + distance + "\nDuration: " + time +"\nETA: " + eta);
                            line = mMap.addPolyline(new PolylineOptions()
                                    .addAll(list)
                                    .width(20)
                                    .color(Color.RED)
                                    .geodesic(true)
                            );
                        }
                    }
                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });

    }

    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng( (((double) lat / 1E5)),
                    (((double) lng / 1E5) ));
            poly.add(p);
        }

        return poly;
    }

    @SuppressLint("MissingPermission")
    private void goToCurrentLocation() {

//        if(!markerOrg.equals(null)) markerOrg.remove();
        // Enable MyLocation Layer of Google Map

        markerOrg.remove();

        // Get LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Get the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Get Current Location
        @SuppressLint("MissingPermission") Location myLocation = locationManager.getLastKnownLocation(provider);

        //set map type
//        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        // Get latitude of the current location
        latOrg = myLocation.getLatitude();

        // Get longitude of the current location
        lonOrg = myLocation.getLongitude();

        // Create a LatLng object for the current location
        LatLng latLng = new LatLng(latOrg, lonOrg);

        // Show the current location in Google Map
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in the Google Map
        mMap.animateCamera(CameraUpdateFactory.zoomTo(20));

        markerOrg = mMap.addMarker(new MarkerOptions().position(new LatLng(latOrg, lonOrg)).title("You are here!"));
        getRoute(latOrg, lonOrg, latDest, lonDest, "user");
    }

    private void getDestination(){
//        markerDest.remove();
        Call<com.example.ai.mapsearch.Model.ResponseCoordinate> call = retrofitMaps.getDestination("1");
        call.enqueue(new Callback<ResponseCoordinate>() {
            @Override
            public void onResponse(Response<com.example.ai.mapsearch.Model.ResponseCoordinate> response, Retrofit retrofit) {
                Log.d(Constant.TAG, "Destination " + response.raw());

                if(response.body().getCode().equals("200")){
                    List<Destination> destinationList = response.body().getData();
                    Log.d("Debug", "Destination " +response.body().getData().toString());
                    latDest = destinationList.get(0).getDestinationLatitude();
                    lonDest = destinationList.get(0).getDestinationLongitude();
                    String name = destinationList.get(0).getDestinationName();
                    markerDest = mMap.addMarker(new MarkerOptions().position(new LatLng(latDest, lonDest))
                            .title(name)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(Constant.TAG, "Error " + t.getMessage());
            }
        });
    }

    private void getParticipant(){
        Call<ResponseParticipant> call = retrofitMaps.getParticipant("1", "1");
        call.enqueue(new Callback<ResponseParticipant>() {
            @Override
            public void onResponse(Response<ResponseParticipant> response, Retrofit retrofit) {
                Log.d(Constant.TAG, "Participant " + response.raw());

                if(response.body().getCode().equals("200")){
                    participantList = response.body().getData();
                    displayParticipant();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(Constant.TAG, "Error " + t.getMessage());
            }
        });
    }

    private void displayParticipant(){
        markerParticipant.remove();
        markerList.clear();
        for(int i = 0; i < participantList.size(); i++){
            markerParticipant = mMap.addMarker(new MarkerOptions().position(new LatLng(participantList.get(i).getParticipantLatitude(), participantList.get(i).getParticipantLongitude()))
                    .title(participantList.get(i).getParticipantName())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

            markerList.add(markerParticipant);

            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(participantList.get(i).getParticipantLatitude(), participantList.get(i).getParticipantLongitude())));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(20));

            getRoute(participantList.get(i).getParticipantLatitude(), participantList.get(i).getParticipantLongitude(), latDest,lonDest, "participant");

        }
    }


    // Checking if Google Play Services Available or not
//    private boolean isGooglePlayServicesAvailable() {
//        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
//        int result = googleAPI.isGooglePlayServicesAvailable(this);
//        if(result != ConnectionResult.SUCCESS) {
//            if(googleAPI.isUserResolvableError(result)) {
//                googleAPI.getErrorDialog(this, result,
//                        0).show();
//            }
//            return false;
//        }
//        return true;
//    }

//    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
//    public boolean checkLocationPermission(){
//        if (ContextCompat.checkSelfPermission(this,
//                android.Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            // Asking user if explanation is needed
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
//
//                // Show an explanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//
//                //Prompt the user once explanation has been shown
//                ActivityCompat.requestPermissions(this,
//                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
//                        MY_PERMISSIONS_REQUEST_LOCATION);
//
//
//            } else {
//                // No explanation needed, we can request the permission.
//                ActivityCompat.requestPermissions(this,
//                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
//                        MY_PERMISSIONS_REQUEST_LOCATION);
//            }
//            return false;
//        } else {
//            return true;
//        }
//    }

}
