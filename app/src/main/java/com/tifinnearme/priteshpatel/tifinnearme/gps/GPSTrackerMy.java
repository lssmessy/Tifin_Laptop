package com.tifinnearme.priteshpatel.tifinnearme.gps;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by pritesh.patel on 27-03-15.
 */
public class GPSTrackerMy extends Service implements LocationListener{

    private final LatLng MUMBAI=new LatLng(18.9750,72.8258);
    private LatLng myloc=null;
    private GoogleMap map;
    private Location loc;
    private  LocationManager locationManager;
    static double lattitude,longitude;
    private Context mycontext;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;
    boolean isGPSEnabled=false;

    public GPSTrackerMy(Context mycontext) {
        this.mycontext = mycontext;
        getLoc();
    }

    public Location getLoc() {
        try {
            locationManager = (LocationManager)mycontext.getSystemService(LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(locationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && isNetworkEnabled) {
                //Toast.makeText(this, "Enable network or GPS", Toast.LENGTH_LONG).show();
                showSetting();
            } else {
                this.canGetLocation=true;
                if (isNetworkEnabled)//If network is enabled
                {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10, 10, this);
                    Log.d("Network", " enabled");
                    if (locationManager != null)
                        loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (loc != null) {
                        lattitude = getLat();
                        longitude = getLong();
                    }
                }
                if (isGPSEnabled) {
                    if(loc==null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 10, this);
                        Log.d("GPS", " enabled");
                        if (locationManager != null)
                            loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (loc != null) {
                            lattitude = getLat();
                            longitude = getLong();
                        }
                    }
                }

            }
        }catch (Exception e){e.printStackTrace();}
        return loc;
    }
    public double getLat(){
        if(loc!=null)
            lattitude=loc.getLatitude();
        return lattitude;
    }
    public double getLong(){
        if(loc!=null)
            longitude=loc.getLongitude();
        return longitude;
    }
    public boolean canGetLocation(){
        return  this.canGetLocation;

    }
    public void showSetting(){
        AlertDialog.Builder alert=new AlertDialog.Builder(GPSTrackerMy.this);
        alert.setTitle("GPS Settings");
        alert.setMessage("GPS is not enabled. Do you want to Enable now!!");
        alert.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mycontext.startActivity(i);
            }
        });
        alert.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alert.show();
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
