package com.example.raphaeloliveira.testegooglemaps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import java.util.Calendar;

public class LocationGPS implements LocationListener {

    private Context context;
    private LocationManager mLocationManager;
    private Double[] coord;
    private CallBack callback;

    public LocationGPS(Context context, CallBack callback) {
        this.context = context;
        this.callback = callback;
        init();
    }

    public void init() {
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            return;
        }

        android.location.Location location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null && location.getTime() > Calendar.getInstance().getTimeInMillis() - 30 * 1000) {

            // caso faz 2 minutos que pegou o local, pega o último local...
            coord = new Double[2];
            coord[0] = location.getLatitude();
            coord[1] = location.getLongitude();

            mLocationManager.removeUpdates(this);

            callback.coordenadas(coord);

        } else {
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        }
    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        if (location != null) {
            coord = new Double[2];
            coord[0] = location.getLatitude();
            coord[1] = location.getLongitude();

            try {

                mLocationManager.removeUpdates(this);

                callback.coordenadas(coord);

            } catch (SecurityException e) {
                Log.e("LocationGPS-ERROR", e.getMessage());
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
}