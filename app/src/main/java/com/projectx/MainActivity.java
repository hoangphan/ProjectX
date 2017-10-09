package com.projectx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startButtonClicked(View view)
    {
        //Create an intent to open connectoini activity
        Intent intent = new Intent(this, ConnectionActivity.class);
        //Start connection activity
        startActivity(intent);
    }
}
