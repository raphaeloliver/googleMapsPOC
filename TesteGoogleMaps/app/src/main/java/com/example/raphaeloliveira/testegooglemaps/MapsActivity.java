package com.example.raphaeloliveira.testegooglemaps;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

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


        new LocationGPS(this, new CallBack() {
            @Override
            public void coordenadas(Double[] resultado) {
                LAT = resultado[0];
                LONG = resultado[1];
                mapFragment.getMapAsync(MapsActivity.this);
            }
        });
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
}
