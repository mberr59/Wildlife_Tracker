package com.example.wildlifetracker.UI;

import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.location.Location;
import android.os.Bundle;

import com.example.wildlifetracker.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.wildlifetracker.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        // Add a marker in Guntersville State Park and move the camera
        LatLng park = new LatLng(34.391442, -86.202289);
        mMap.addMarker(new MarkerOptions().position(park).title("Park"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(park));
        mMap.setMinZoomPreference(12);
        LatLng deer1 = new LatLng(34.361663, -86.216750);
        mMap.addMarker(new MarkerOptions().position(deer1).title("Deer 1"));
        isTargetInRange(park, deer1);
    }

    public void isTargetInRange(LatLng center, LatLng target) {
        float[] distance = new float[1];
        Location.distanceBetween(center.latitude, center.longitude, target.latitude, target.longitude, distance);
        if (distance[0] > 3200.0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
            builder.setTitle("Target out of Range");
            builder.setMessage("Target has moved out of range.");
            builder.setPositiveButton("OK",(dialog, which) ->{
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}