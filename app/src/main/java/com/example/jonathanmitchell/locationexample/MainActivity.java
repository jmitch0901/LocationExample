package com.example.jonathanmitchell.locationexample;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jonathanmitchell.locationexample.Factories.LocationFactory;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;

public class MainActivity extends AppCompatActivity {

    TextView locationText;
    boolean isGettingLocation = false;

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.i("fsdfs","YAY GOT A LOCATION!!!!");
            Log.i("fsfd", location.toString());

            if(locationText!=null){
                locationText.setText("Lat: "+location.getLatitude()+"\nLong: "+location.getLongitude());
            }

            LocationFactory.get(MainActivity.this).removeListener(this);
            LocationFactory.get(MainActivity.this).stopLocationServices();
            isGettingLocation = false;

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationText = (TextView)findViewById(R.id.text_view);
        Button grabLocationButton = (Button)findViewById(R.id.button);


        grabLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isGettingLocation){
                    return;
                }

                final LocationFactory locationFactory = LocationFactory.get(MainActivity.this);

                locationFactory.prepareGoogleConnection(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        isGettingLocation = true;
                        locationFactory.registerListener(locationListener);
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                    }
                }, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {
                        Log.e("fsdf", "UH OH ERROR CONNECTING TO GOOGLE!: " + connectionResult.getErrorMessage());
                    }
                });

                locationFactory.startLocationServices();

            }
        });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocationFactory.get(this).stopLocationServices();
    }
}
