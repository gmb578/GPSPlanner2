package com.gmbtech.wg.gpsplanner;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import android.os.Bundle;

public class GPSMap extends SupportMapFragment{

    private GoogleMap mMap;
    private Context mContext;
    private FollowMeLocationSource followMeLocationSource;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        mMap = getMap();
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.setMyLocationEnabled(true);
        mContext = getActivity();
        followMeLocationSource = new FollowMeLocationSource();

        return v;
    }


    public void UpdateUI(double lat, double lng) {
        //get bounding box
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        LatLngBounds.Builder latlngBuilder = new LatLngBounds.Builder();
        latlngBuilder.include(new LatLng(lat, lng));
        LatLngBounds latLngBounds = latlngBuilder.build();
        CameraUpdate movement = CameraUpdateFactory.newLatLngBounds(latLngBounds,
                display.getWidth(), display.getHeight(), 15);
        mMap.moveCamera(movement);

    }

    /* Our custom LocationSource.
  * We register this class to receive location updates from the Location Manager
  * and for that reason we need to also implement the LocationListener interface. */
    private class FollowMeLocationSource implements LocationSource, LocationListener {

        private OnLocationChangedListener mListener;
        private LocationManager locationManager;
        private final Criteria criteria = new Criteria();
        private String bestAvailableProvider;
        /* Updates are restricted to one every 10 seconds, and only when
         * movement of more than 10 meters has been detected.*/
        private final int minTime = 10000;     // minimum time interval between location updates, in milliseconds
        private final int minDistance = 10;    // minimum distance between location updates, in meters

        private FollowMeLocationSource() {
            // Get reference to Location Manager
            locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

            // Specify Location Provider criteria
            criteria.setAccuracy(Criteria.ACCURACY_LOW);
            criteria.setPowerRequirement(Criteria.POWER_LOW);
            criteria.setAltitudeRequired(true);
            criteria.setBearingRequired(true);
            criteria.setSpeedRequired(true);
            criteria.setCostAllowed(true);
        }

        private void getBestAvailableProvider() {
            /* The preferred way of specifying the location provider (e.g. GPS, NETWORK) to use
             * is to ask the Location Manager for the one that best satisfies our criteria.
             * By passing the 'true' boolean we ask for the best available (enabled) provider. */
            bestAvailableProvider = locationManager.getBestProvider(criteria, true);
        }

        /* Activates this provider. This provider will notify the supplied listener
         * periodically, until you call deactivate().
         * This method is automatically invoked by enabling my-location layer. */
        @Override
        public void activate(OnLocationChangedListener listener) {
            // We need to keep a reference to my-location layer's listener so we can push forward
            // location updates to it when we receive them from Location Manager.
            mListener = listener;

            // Request location updates from Location Manager
            if (bestAvailableProvider != null) {
                locationManager.requestLocationUpdates(bestAvailableProvider, minTime, minDistance, (android.location.LocationListener) this);
            } else {
                // (Display a message/dialog) No Location Providers currently available.
            }
        }

        /* Deactivates this provider.
         * This method is automatically invoked by disabling my-location layer. */
        @Override
        public void deactivate() {
            // Remove location updates from Location Manager
            locationManager.removeUpdates((android.location.LocationListener) this);

            mListener = null;
        }

        @Override
        public void onLocationChanged(Location location) {
            /* Push location updates to the registered listener..
             * (this ensures that my-location layer will set the blue dot at the new/received location) */
            if (mListener != null) {
                mListener.onLocationChanged(location);
            }

        UpdateUI(location.getLatitude(), location.getLongitude());

        }


        public void onStatusChanged(String s, int i, Bundle bundle) {

        }


        public void onProviderEnabled(String s) {

        }


        public void onProviderDisabled(String s) {

        }
    }

}




