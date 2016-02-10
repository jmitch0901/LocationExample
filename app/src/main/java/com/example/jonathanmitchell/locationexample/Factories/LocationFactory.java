package com.example.jonathanmitchell.locationexample.Factories;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by jonathanmitchell on 2/9/2016.
 */
public class LocationFactory{
    private static final String TAG = "LocationFactory";
    private static LocationFactory myFactory;
    private Context context;

    private GoogleApiClient googleApiClient;

    private LocationFactory(Context context){
        this.context = context;
    }
    public static LocationFactory get(Context c){

        if(myFactory == null){
            myFactory = new LocationFactory(c);
        }
        return myFactory;
    }

    public void prepareGoogleConnection(GoogleApiClient.ConnectionCallbacks onConnect, GoogleApiClient.OnConnectionFailedListener onError){
        googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(onConnect)
                .addOnConnectionFailedListener(onError)
                .build();
    }

    public void startLocationServices(){
        googleApiClient.connect();

    }

    public void stopLocationServices(){

        googleApiClient.disconnect();
    }

    public void registerListener(LocationListener listener){
        LocationRequest request = new LocationRequest();
        request.setInterval(3000);
        request.setFastestInterval(1000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,request,listener);
    }

    public void removeListener(LocationListener listener){
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, listener);
    }

//
//    @Override
//    public void onConnected(Bundle bundle) {
//        Log.i(TAG, "We are connected to Google!");
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {}
//
//    @Override
//    public void onConnectionFailed(ConnectionResult connectionResult) {
//        Log.e(TAG,"Uh Oh, Couldn't connect to google!");
//    }
}
