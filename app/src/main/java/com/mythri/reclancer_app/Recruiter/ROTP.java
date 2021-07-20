package com.mythri.reclancer_app.Recruiter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mythri.reclancer_app.Freelancer.RResult;
import com.mythri.reclancer_app.Freelancer.RSearch;
import com.mythri.reclancer_app.Model.RecReg;
import com.mythri.reclancer_app.Model.Rec_mobval;
import com.mythri.reclancer_app.Network.APIRecReg;
import com.mythri.reclancer_app.Network.APIRmobval;
import com.mythri.reclancer_app.R;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ROTP extends AppCompatActivity {
    private APIRmobval mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotp_reg);

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

                Intent myIntent = getIntent();
                final String mobile_num = myIntent.getStringExtra("phone");
                Toast.makeText(getApplicationContext(),mobile_num,Toast.LENGTH_SHORT).show();

                String otpnum = OTP.getText().toString().trim();
                Toast.makeText(getApplicationContext(),otpnum,Toast.LENGTH_SHORT).show();

                if(otpnum.equals(num))
                {
                    //sendData(mobile_num,otpnum);

                    Toast.makeText(getApplicationContext(),"Matching",Toast.LENGTH_SHORT).show();
                    Intent login =  new Intent(ROTP.this, RLogin.class);
                    startActivity(login);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Not Matching.Please Enter valid OTP",Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    private void sendData(String mobile_num, String otpnum) {
        mAPIService.sendData(mobile_num,otpnum).enqueue(new Callback<Rec_mobval>() {
            @Override
            public void onResponse(Call<Rec_mobval> call, Response<Rec_mobval> response) {


                if (response.isSuccessful()) {
                    //showResponse(response.body().toString());
                    //Log.i(TAG, "post submitted to API." + response.body().toString());
                    //String num = response.body().getRandnum();


                    Toast tst = Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT);
                    tst.show();

                }

            }

            @Override
            public void onFailure(Call<Rec_mobval> call, Throwable t) {
                //Log.e(TAG, "Unable to submit post to API.");

                Toast tst = Toast.makeText(getApplicationContext(), "Server Error Retry registering", Toast.LENGTH_SHORT);
                tst.show();


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
