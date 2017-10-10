package com.projectx;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ConnectionActivity extends AppCompatActivity {

    // constant for BLUETOOTH enable bit indication
    private static final byte REQUEST_ENABLE_BT = 1;

    // constant for LOCATION permission request
    // mandatory for Bluetooth application
    private static final byte PERMISSION_REQUEST_COARSE_LOCATION = 1;

    // declaration for device bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter;

    // declaration for list of query devices
    private ListView listView;
    private ArrayList<String> mDeviceList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        listView = (ListView) findViewById(R.id.listView);
        DiscoverBluetoothDevices();
    }

    // handler for CONNECT button reaction
    public void buttonConnectClicked(View view)
    {
        //TODO: implement conenction and go to next page
    }

    // handler for REFRESH button reaction
    public void buttonRefreshClicked(View view)
    {
        mDeviceList.clear();
        DiscoverBluetoothDevices();
    }

    protected void DiscoverBluetoothDevices()
    {
        mDeviceList.clear();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            NotifyToast("No adapter found");
        }
        else
        {
            if (!mBluetoothAdapter.isEnabled()) {
                // Request enable bluetooth adapter if it is still off
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

                // start the respective intent for enabling bluetooth
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

                // request to make device discoverable (optional)
                Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);

                // only make the device discoverable within 300 seconds - saving energy
                discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                startActivity(discoverableIntent);
            }

            // tricky - from MARSMALLOW, app must request permission to location
            // this is mandatory for bluetooth application
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (this.checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                            PERMISSION_REQUEST_COARSE_LOCATION);
                }
            }
            NotifyToast("Adapter OK");
            // asynchronous discovery - heavy activity takes 12 seconds of query (page scan, inquiry scan)
            mBluetoothAdapter.startDiscovery();
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mReceiver, filter);
        }
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mDeviceList.add(device.getName() + "\n" + device.getAddress());
                NotifyToast(device.getName() + "\n" + device.getAddress());
                listView.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, mDeviceList));
            }
        }
    };


    public void NotifyToast(String message)
    {
        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}

