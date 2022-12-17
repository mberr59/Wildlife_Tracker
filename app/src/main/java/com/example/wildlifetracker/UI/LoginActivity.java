package com.example.wildlifetracker.UI;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wildlifetracker.R;
import com.example.wildlifetracker.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.SupportMapFragment;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
