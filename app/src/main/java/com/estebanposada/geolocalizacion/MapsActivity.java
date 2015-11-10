package com.estebanposada.geolocalizacion;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    LocationManager locationManager = null;

    private GoogleMap mMap;
    private CameraUpdate micam;

    private CameraUpdate mCam;
    public double lat;
    public double lon;
    public TextView data;
    public EditText longitud;
    public EditText latitud;
    public Button btactualiza;
    public Button btactual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        data = (TextView) findViewById(R.id.textview);
        longitud = (EditText) findViewById(R.id.edi1);
        latitud = (EditText) findViewById(R.id.edi2);
        btactualiza = (Button) findViewById(R.id.btnActualiza);
        btactual = (Button) findViewById(R.id.btnActual);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updatePosition(location);
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
        };

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

/*
        mMap.addMarker(new MarkerOptions().position(new LatLng(6,-75)).title("Holi").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)).snippet("This is the snippet"));
        micam = CameraUpdateFactory.newLatLngZoom(new LatLng(6,-74),14);
        mMap.animateCamera(micam);*/

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
       mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker in nowhere"));
        //micam = CameraUpdateFactory.newLatLngZoom(new LatLng(0,0),0);
        //mMap.animateCamera(micam);
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private void   setMarker(LatLng position,String title,String info,float opacity,float dimension1,float dimension2){
        mMap.addMarker(new MarkerOptions()
                        .position(position)
                        .title(title)
                        .snippet(info)
                        .alpha(opacity)
                        .anchor(dimension1, dimension2)
                //.icon(BitmapDescriptorFactory.fromResource(icon))

        );
    }

    public void click (View v){
        Toast.makeText(getApplicationContext(), "Actualizado", Toast.LENGTH_SHORT).show();
        updatePosition();
    }

    public void ir (View v){
        Toast.makeText(getApplicationContext(), ((Button) v).getText(), Toast.LENGTH_SHORT).show();
        lon = Double.parseDouble(longitud.getText().toString());
        lat = Double.parseDouble(latitud.getText().toString());
        setMarker(new LatLng(lat, lon),"UdeA","Alma Mater",0.9F,0.1F,0.1F);
        mCam= CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lon), 15);
        mMap.animateCamera(mCam);

    }

    public void updatePosition(){
        Location loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        updatePosition(loc);
    }

    public void updatePosition (Location location){
        if (location !=null){
            lat=location.getLatitude();
            lon=location.getLongitude();
            //TextView txt = (TextView) findViewById(R.id.textview);
            data.setText(lat+"/"+lon+"/"+new java.util.Date(System.currentTimeMillis()).toString());
        }
    }


}
