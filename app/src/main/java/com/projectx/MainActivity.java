package com.projectx;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.projectx.connect.ConnectionActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startButtonClicked(View view)
    {

        Intent mControllerService = new Intent (this, Controller.class);
        startService(mControllerService);
        //Create an intent to open connection activity
        Intent map_activity = new Intent(this, ConnectionActivity.class);
        //Start connection activity
        startActivity(map_activity);

    }

}
