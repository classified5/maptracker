package com.example.ai.mapsearch.Background;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.ai.mapsearch.API.ApiClient;
import com.example.ai.mapsearch.API.RetrofitMaps;
import com.example.ai.mapsearch.Model.Coordinate;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by A.I on 25/11/2017.
 */

public class LocationServices extends Service{

    PowerManager.WakeLock wakeLock;
    private LocationManager locationManager;
    private RetrofitMaps retrofitMaps;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();

        PowerManager pm = (PowerManager) getSystemService(this.POWER_SERVICE);

        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "DoNotSleep");
        retrofitMaps = ApiClient.createClient().create(RetrofitMaps.class);
        Log.d("Google", "Service Created");

    }

    @SuppressLint("MissingPermission")
    @Override
    @Deprecated
    public void onStart(Intent intent, int startId){
        super.onStart(intent, startId);

        Log.d("Google", "Service Started");

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000,5, listener);
    }

    private LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.d("Google", "Location Changed");

            if(location == null) return;

            if(isConnectingToInternet(getApplicationContext())){
                Coordinate coordinate = new Coordinate("1","1",location.getLongitude(), location.getLatitude());

                try {
                    retrofit.Call<Coordinate> call = retrofitMaps.setCoordinate(coordinate);

                    call.enqueue(new Callback<Coordinate>() {
                        @Override
                        public void onResponse(Response<Coordinate> response, Retrofit retrofit) {
                            int statusCode = response.code();
                            Log.d("Google", "Code " + statusCode);

                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.d("Google", "Failed " + t.getMessage());
                        }
                    });

                }catch (Exception e){
                    e.printStackTrace();
                }

            }



        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    @Override
    public void onDestroy(){
        super.onDestroy();

        wakeLock.release();
    }

    public static boolean isConnectingToInternet(Context _context) {
        ConnectivityManager connectivity = (ConnectivityManager) _context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }

}
