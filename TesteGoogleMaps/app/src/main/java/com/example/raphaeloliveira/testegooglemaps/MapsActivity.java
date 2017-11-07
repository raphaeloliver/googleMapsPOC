package com.example.raphaeloliveira.testegooglemaps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 123;
    private double LAT = 0;
    private double LONG = 0;
    private GoogleMap mMap;
    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            verifyLocation();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            }
        } else {

            verifyLocation();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLngBounds zoom = new LatLngBounds(new LatLng(LAT, LONG), new LatLng(LAT, LONG));

        LatLng position = new LatLng(LAT, LONG);
        //Adiciona o marcador
        mMap.addMarker(new MarkerOptions().position(position));
        //Move a camera ate a posição
        mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
        //zoom na posição
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(zoom.getCenter(), 17));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    verifyLocation();

                } else {
                    // permission denied
                    // Disable the functionality that depends on this permission.
                }
                return;
            }
            // other 'case' statements for other permssions
        }
    }

    public void verifyLocation() {
        new LocationGPS(this, new CallBack() {
            @Override
            public void coordenadas(Double[] resultado) {
                LAT = resultado[0];
                LONG = resultado[1];
                mapFragment.getMapAsync(MapsActivity.this);
            }
        });
    }
}
