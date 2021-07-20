package com.mythri.reclancer_app.Freelancer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mythri.reclancer_app.Model.FMLogin;
import com.mythri.reclancer_app.Model.FM_otpLogin;
import com.mythri.reclancer_app.Network.APIFMLogin;
import com.mythri.reclancer_app.Network.APIFMLoginOTP;
import com.mythri.reclancer_app.Network.ApiUtil;
import com.mythri.reclancer_app.R;
import com.mythri.reclancer_app.Recruiter.RLogin;
import com.mythri.reclancer_app.Recruiter.ROTP;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FOTP_Login extends Activity {

    private TextView mResponseTv;
    private APIFMLoginOTP mAPILogin;

    String email, id;
    TextView userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_otplogin);

        Intent myIntent = getIntent();
        final String mobile_num = myIntent.getStringExtra("phone");
        Toast.makeText(getApplicationContext(), mobile_num, Toast.LENGTH_SHORT).show();
        final String num = myIntent.getStringExtra("num");
        Toast.makeText(getApplicationContext(), num, Toast.LENGTH_SHORT).show();

        final EditText OTP = (EditText) findViewById(R.id.otp);


        Button s = (Button) findViewById(R.id.submit);
        mAPILogin = ApiUtil.getAPIotp_FMLogin();
       s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = getIntent();
                final String mobile_num = myIntent.getStringExtra("phone");
                Toast.makeText(getApplicationContext(), mobile_num, Toast.LENGTH_SHORT).show();

                String otpnum = OTP.getText().toString().trim();
                Toast.makeText(getApplicationContext(), otpnum, Toast.LENGTH_SHORT).show();

                if (otpnum.equals(num)) {
                    Toast.makeText(getApplicationContext(), "Matching", Toast.LENGTH_SHORT).show();

                    m_otplogin(mobile_num,otpnum);


                   // Intent login = new Intent(FOTP_Login.this, FLogin.class);
                   // startActivity(login);

                } else {
                    Toast.makeText(getApplicationContext(), "Not Matching.Please Enter valid OTP", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }

    public void m_otplogin(String mobile,String otp) {
        mAPILogin.m_otplogin(mobile,otp).enqueue(new Callback<FM_otpLogin>() {

            @Override
            public void onResponse(Call<FM_otpLogin> call, Response<FM_otpLogin> response) {
                String JSON_STRING = null;

                if(response.isSuccessful()) {
                    String id = response.body().getID();
                    String mobile  = response.body().getMobile();
                    String num = response.body().getRandnum();
                    String token  = response.body().getJwt();

                    Intent otp =  new Intent(FOTP_Login.this, FHome.class);
                    otp.putExtra("Id",id);
                    otp.putExtra("token",token);
                    startActivity(otp);
                }
                else
                {
                    Toast.makeText(FOTP_Login.this, "Incorrect Mobile Number", Toast.LENGTH_SHORT).show();

                }
            }



            @Override
            public void onFailure(Call<FM_otpLogin> call, Throwable t) {
                //Log.e(TAG, "Unable to submit post to API.");

                Toast tst = Toast.makeText(getApplicationContext(),"Incorrect Mobile Number",Toast.LENGTH_SHORT);
                tst.show();

                //Snackbar.make(, "Please enter required fields with Valid Data in the form", Snackbar.LENGTH_LONG).show();
            }
        });
    }
}

