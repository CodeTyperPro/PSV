package com.example.psv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.bluetooth.*;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.skyfishjy.library.RippleBackground;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    BluetoothAdapter bluetoothAdapter;
    Location location;
    static int REQUEST_ENABLE_BT;
    ListView listView;
    LocationListener locationListener;
    LocationManager locationManager;
    RippleBackground findDevice;
    Switch aSwitch;

    Animation devicesAnimation;
    ImageView img_device_1;
    ImageView img_device_2;
    ImageView img_device_3;


    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    private  List<ModelView> modelViewList;

    MediaPlayer mediaPlayer;
    Vibrator vibrator;
    AudioManager audioManager;

    Timer timer;
    Runnable runnable;
    Handler handler;
    int cont = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm);
        mediaPlayer.setLooping(true);

        aSwitch = (Switch) findViewById(R.id.btn_switch);
        findDevice = (RippleBackground) findViewById(R.id.content) ;

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        img_device_1 = (ImageView) findViewById(R.id.img_device_1);
        img_device_2 = (ImageView) findViewById(R.id.img_device_2);
        img_device_3 = (ImageView) findViewById(R.id.img_device_3);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        devicesAnimation = AnimationUtils.loadAnimation(this, R.anim.device);

        recyclerView.setVisibility(View.INVISIBLE);
        img_device_1.setVisibility(View.INVISIBLE);
        img_device_2.setVisibility(View.INVISIBLE);
        img_device_3.setVisibility(View.INVISIBLE);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    findDevice.startRippleAnimation();
                    bluetoothAdapter.startDiscovery();

                    recyclerView.setVisibility(View.VISIBLE);
                    img_device_1.setVisibility(View.VISIBLE);
                    img_device_2.setVisibility(View.VISIBLE);
                    img_device_3.setVisibility(View.VISIBLE);

                    img_device_1.startAnimation(devicesAnimation);
                    img_device_2.startAnimation(devicesAnimation);
                    img_device_3.startAnimation(devicesAnimation);
                    recyclerView.startAnimation(devicesAnimation);

                    if(!mediaPlayer.isPlaying())
                        mediaPlayer.start();
                    else mediaPlayer.reset();

                    vibrator.vibrate(900);

                } else{
                    //unregisterReceiver(broadcastReceiver);
                    findDevice.stopRippleAnimation();

                    img_device_1.clearAnimation();
                    img_device_2.clearAnimation();
                    img_device_3.clearAnimation();
                    recyclerView.clearAnimation();

                    recyclerView.setVisibility(View.INVISIBLE);
                    img_device_1.setVisibility(View.INVISIBLE);
                    img_device_2.setVisibility(View.INVISIBLE);
                    img_device_3.setVisibility(View.INVISIBLE);

                    mediaPlayer.pause();
                }
            }
        });

        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Dispositivo não suporta Bluetooth.", Toast.LENGTH_SHORT).show();
        }

        if (!bluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }

        // Register for broadcasts when a device is discovered.
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(broadcastReceiver, filter);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // add data to the list
        AddData();

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(modelViewList);
        recyclerView.setAdapter(mAdapter);
    }

    private  void  AddData(){
        modelViewList = new ArrayList<>();
        modelViewList.add(new ModelView("Stianeth João", "1"));
        modelViewList.add(new ModelView("Amadeu Jeremias", "3"));
        modelViewList.add(new ModelView("Alfredo Martins", "5"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(broadcastReceiver);
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                String deviceName = device.getName();
                String deviceAddress = device.getAddress();

                modelViewList.add(new ModelView(deviceName, deviceAddress));
            }
        }
    };
}