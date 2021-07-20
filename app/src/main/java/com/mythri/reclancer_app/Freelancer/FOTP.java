package com.mythri.reclancer_app.Freelancer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mythri.reclancer_app.R;

public class FOTP extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotp_reg);

        Intent myIntent = getIntent();
        final String mobile_num = myIntent.getStringExtra("phone");
        Toast.makeText(getApplicationContext(),mobile_num,Toast.LENGTH_SHORT).show();
        final String num = myIntent.getStringExtra("num");
        Toast.makeText(getApplicationContext(),num,Toast.LENGTH_SHORT).show();

        final EditText OTP = (EditText)findViewById(R.id.otp);
        Button s = (Button)findViewById(R.id.submit);

        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String otpnum = OTP.getText().toString().trim();
                Toast.makeText(getApplicationContext(),otpnum,Toast.LENGTH_SHORT).show();

                if(otpnum.equals(num))
                {
                    Toast.makeText(getApplicationContext(),"Matching",Toast.LENGTH_SHORT).show();
                    Intent login =  new Intent(FOTP.this,FLogin.class);
                    startActivity(login);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Not Matching.Please Enter valid OTP",Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

        @Override
        public void onResume() {
            LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
            super.onResume();
        }
        @Override
        public void onPause() {
            super.onPause();
            LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String message = intent.getStringExtra("message");

                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                // message is the fetching OTP


            }
        }
    };

    }

