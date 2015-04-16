package com.gmbtech.wg.gpsplanner;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import java.util.ArrayList;


public class GPSMap extends SupportMapFragment {

    private GoogleMap mMap;
    private Context mContext;
    public int i;
    protected LocationManager locationManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        mMap = getMap();
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setMyLocationEnabled(true);
        mContext = getActivity();
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 50, new android.location.LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(location.getLatitude(), location.getLongitude()), 16));
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                            .zoom(16)                   // Sets the zoom
               /*     .tilt(20)                   // Sets the tilt of the camera to 30 degrees
                    .bearing(90)                // Sets the direction of the camera to east  */
                            .build();                   // Creates a CameraPosition from the builder
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
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
        });


        if (location != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude()), 16));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(16)                   // Sets the zoom
               /*     .tilt(20)                   // Sets the tilt of the camera to 30 degrees
                    .bearing(90)                // Sets the direction of the camera to east  */
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }


        MarkerOptions markerOptions = new MarkerOptions();
        // Setting the position for the marker
        ArrayList<Task> data = TaskSaver.getInstance(getActivity()).getTasks();
        for (i = 0; i < data.size(); i++) {
            if (data.get(i) != null) {
                final Task task = data.get(i);
                Location pinlocation = task.getLocation();

                markerOptions.position(new LatLng(pinlocation.getLatitude(), pinlocation.getLongitude()));
                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title(pinlocation.getLatitude() + " : " + pinlocation.getLongitude());

                mMap.addMarker(markerOptions);
            }
        }


        // Setting a click event handler for the map
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);


                // Animating to the touched position
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                mMap.addMarker(markerOptions);

                openDialog(latLng);


            }


            public void openDialog(LatLng latLng) {
                Location l = new Location("");
                l.setLatitude(latLng.latitude);
                l.setLongitude(latLng.longitude);
                DialogFragment newFragment = new TaskDialog(l);
                newFragment.show(getActivity().getFragmentManager(), "Task");

            }
        });

        return v;
    }
}






    class TaskHolder {
        ImageView imgIcon;
        TextView txtTitle;
    }


























