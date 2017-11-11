package com.projectx;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.projectx.maps.MapsActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startButtonClicked(View view)
    {
        //Create an intent to open connection activity
        Intent map_activity = new Intent(this, MapsActivity.class);
        //Start connection activity
        startActivity(map_activity);

    }

}
