package com.example.wper_smile.service_bind;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG="info";
    MyService myService=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ServiceConnection serviceConnection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.v(TAG, "onServiceConnected");
                myService = ((MyService.LocalBinder) service).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

                Log.v(TAG, "onServiceDisconnected");
            }
        };

        Button staBtn=(Button)findViewById(R.id.staBtn);
        Button endBtn=(Button)findViewById(R.id.endBtn);
        Button usingBtn=(Button)findViewById(R.id.usingBtn);

        staBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,MyService.class);
                bindService(intent,serviceConnection, Service.BIND_AUTO_CREATE);
                Toast.makeText(MainActivity.this, "Start Service", Toast.LENGTH_SHORT).show();
                Log.v(TAG,"Start Service");
            }
        });

        endBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unbindService(serviceConnection);
                Toast.makeText(MainActivity.this, "Close Service", Toast.LENGTH_SHORT).show();
                Log.v(TAG,"Close Service");
            }
        });
      usingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myService!=null){
                    Log.v(TAG,"Using Service:"+myService.add(1,2));
                    Toast.makeText(MainActivity.this, "Using Service", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

