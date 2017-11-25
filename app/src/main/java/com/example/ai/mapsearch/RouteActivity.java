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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class RouteActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button btnCurrent;
    private LatLng origin;
    private LatLng dest;
    private ArrayList<LatLng> MarkerPoints;
    private TextView ShowDistanceDuration;
    private Polyline line;
    private RetrofitMaps retrofitMaps;
    private double lonDest, latDest, lonOrg, latOrg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        startService(new Intent(this, LocationServices.class));

        ShowDistanceDuration = (TextView) findViewById(R.id.show_distance_time);
        btnCurrent = (Button) findViewById(R.id.btnCurrent);

        retrofitMaps = ApiClient.createClient().create(RetrofitMaps.class);

        btnCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToCurrentLocation();
            }
        });
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            checkLocationPermission();
//        }

        MarkerPoints = new ArrayList<>();
        getDestination();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        // Setting onclick event listener for the map
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {

                // clearing map and generating new marker points if user clicks on map more than two times
                if (MarkerPoints.size() > 1) {
//                    mMap.clear();
                    MarkerPoints.clear();
                    MarkerPoints = new ArrayList<>();
                    ShowDistanceDuration.setText("");
                }

                // Adding new item to the ArrayList
                MarkerPoints.add(point);

                // Creating MarkerOptions
                MarkerOptions options = new MarkerOptions();

                // Setting the position of the marker
                options.position(point);

                /**
                 * For the start location, the color of marker is GREEN and
                 * for the end location, the color of marker is RED.
                 */
                if (MarkerPoints.size() == 1) {
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    latOrg = point.latitude;
                    lonOrg = point.longitude;

                } else if (MarkerPoints.size() == 2) {
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    latDest = point.latitude;
                    lonDest = point.longitude;
                }


                // Add new marker to the Google Map Android API V2
                mMap.addMarker(options);

                // Checks, whether start and end locations are captured
                if (MarkerPoints.size() >= 2) {
                    origin = MarkerPoints.get(0);
//                    latOrg = MarkerPoints.get(0).latitude;
//                    lonOrg = MarkerPoints.get(0).longitude;
//                    latDest = MarkerPoints.get(1).latitude;
//                    lonDest = MarkerPoints.get(1).longitude;
                    dest = MarkerPoints.get(1);
                }

            }
        });

//        Button btnDriving = (Button) findViewById(R.id.btnDriving);
//        btnDriving.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getRoute("driving");
//            }
//        });

    }

    private void getRoute(Double latOrg, Double lonOrg, Double latDest, Double lonDest) {

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
                try {
                    //Remove previous line from map
                    if (line != null) {
                        line.remove();
                    }
                    // This loop will go through all the results and add marker on each location.
                    for (int i = 0; i < response.body().getRoutes().size(); i++) {
                        String distance = response.body().getRoutes().get(i).getLegs().get(i).getDistance().getText();
                        String time = response.body().getRoutes().get(i).getLegs().get(i).getDuration().getText();
                        Log.d("log", "Distance:" + distance + ", Duration:" + time);
                        ShowDistanceDuration.setText("Distance:" + distance + ", Duration:" + time);
                        String encodedString = response.body().getRoutes().get(0).getOverviewPolyline().getPoints();
                        List<LatLng> list = decodePoly(encodedString);
                        line = mMap.addPolyline(new PolylineOptions()
                                .addAll(list)
                                .width(20)
                                .color(Color.RED)
                                .geodesic(true)
                        );
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
        // Enable MyLocation Layer of Google Map


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
        double latitude = myLocation.getLatitude();

        // Get longitude of the current location
        double longitude = myLocation.getLongitude();

        // Create a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        // Show the current location in Google Map
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in the Google Map
        mMap.animateCamera(CameraUpdateFactory.zoomTo(20));
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("You are here!"));
        getRoute(latitude, longitude, latDest, lonDest);
    }

    private void getDestination(){
        Call<com.example.ai.mapsearch.Model.Response> call = retrofitMaps.getDestination("1");
        call.enqueue(new Callback<com.example.ai.mapsearch.Model.Response>() {
            @Override
            public void onResponse(Response<com.example.ai.mapsearch.Model.Response> response, Retrofit retrofit) {
                Log.d("Debug", "Code " + response.raw());

                if(response.body().getCode().equals("200")){
                    List<Destination> destinationList = response.body().getData();
                    Log.d("Debug", "Destination " +response.body().getData().toString());
                    latDest = destinationList.get(0).getDestinationLatitude();
                    lonDest = destinationList.get(0).getDestinationLongitude();
                    String name = destinationList.get(0).getDestinationName();
                    mMap.addMarker(new MarkerOptions().position(new LatLng(latDest, lonDest)).title(name));

                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
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
