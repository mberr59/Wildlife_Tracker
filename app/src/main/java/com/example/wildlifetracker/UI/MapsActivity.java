package com.example.wildlifetracker.UI;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.wildlifetracker.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.wildlifetracker.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private double[] trackerCoordinates = new double[2];
    private String trackedName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trackerCoordinates = getIntent().getDoubleArrayExtra("coordinates");
        trackedName = getIntent().getStringExtra("trackedName");

        com.example.wildlifetracker.databinding.ActivityMapsBinding binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
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

        // Add a marker in Guntersville State Park and move the camera to the location.
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(AnimalDetail.park));
        googleMap.setMaxZoomPreference(16);
        googleMap.setMinZoomPreference(10);
        LatLng trackedAnimal = new LatLng(trackerCoordinates[0], trackerCoordinates[1]);
        googleMap.addMarker(new MarkerOptions().position(trackedAnimal).title(trackedName));
    }

    public boolean onCreateMapMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_animal_list, menu);
        return true;
    }

    public boolean onMapItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}