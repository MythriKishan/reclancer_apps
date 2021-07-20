package com.mythri.reclancer_app.Recruiter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mythri.reclancer_app.Freelancer.FOTP;
import com.mythri.reclancer_app.Freelancer.FReg;
import com.mythri.reclancer_app.HomePage;
import com.mythri.reclancer_app.Model.RecReg;
import com.mythri.reclancer_app.Network.APIRecReg;
import com.mythri.reclancer_app.Network.ApiUtil;
import com.mythri.reclancer_app.R;
import com.mythri.reclancer_app.Validation.RecRegVal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RReg extends AppCompatActivity {

    Toast tst;
    String mobile;

    private APIRecReg mAPIService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rreg);

        final EditText mail = (EditText) findViewById(R.id.email);
        //Validate Email Field

        if(mail.getText().toString().length() == 0 )
        {
            mail.setError( "required!" );
        }
        mail.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                RecRegVal.isEmailAddress(mail, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        //Mobile Number

        final EditText mob = (EditText) findViewById(R.id.mobile);
        //Validate Email Field

        if(mob.getText().toString().length() == 0 )
        {
            mob.setError( "required!" );
        }
        mob.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                RecRegVal.isMobileNum(mob, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });



        final EditText pwd = (EditText) findViewById(R.id.pass);
        //Validate Password Field
        if(pwd.getText().toString().length() == 0 )
        {
            pwd.setError( "required!" );
        }
        pwd.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                RecRegVal.isPassword(pwd, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });


        final EditText cpwd = (EditText) findViewById(R.id.cpass);
        //Validate Confirm Password Field

        if(cpwd.getText().toString().length() == 0 )
        {
            cpwd.setError( "required!" );
        }

        cpwd.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                RecRegVal.isCpass(cpwd, true);
                final EditText pwd = (EditText) findViewById(R.id.pass);
                String passwrd = pwd.getText().toString();

                if (!cpwd.equals(passwrd)) {
                    Toast tst = Toast.makeText(getApplicationContext(), "Password Mismatch", Toast.LENGTH_SHORT);
                    tst.show();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        Button submitBtn = (Button) findViewById(R.id.rreg);

        mAPIService = ApiUtil.getAPIReg();
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast tst;

                String email = mail.getText().toString().trim();
                String mobile = mob.getText().toString().trim();
                String pass = pwd.getText().toString().trim();
                String cpassword = cpwd.getText().toString().trim();


                if ((!TextUtils.isEmpty(email)) && (!TextUtils.isEmpty(mobile)) && (!TextUtils.isEmpty(pass)) && (!TextUtils.isEmpty(cpassword))) {                    //isFormValid();
                    sendPost(email, mobile,pass, cpassword);
                } else {
                    tst = Toast.makeText(getApplicationContext(), "Please enter Valid Data in the form", Toast.LENGTH_SHORT);
                    tst.show();
                }

            }


        });
    }

        public void sendPost(String mail,String mob,String password,String cpass) {
           mAPIService.savePost(mail,mob, password, cpass).enqueue(new Callback<RecReg>() {
                @Override
                public void onResponse(Call<RecReg> call, Response<RecReg> response) {


                     if (response.isSuccessful()) {
                        //showResponse(response.body().toString());
                        //Log.i(TAG, "post submitted to API." + response.body().toString());
                         String num = response.body().getRandnum();

                         Intent otp =  new Intent(RReg.this, ROTP.class);
                         otp.putExtra("phone",mobile);
                         otp.putExtra("num",num);
                         startActivity(otp);

                         Toast tst = Toast.makeText(getApplicationContext(), "Activation email has been sent to your emailId.Click on the link to activate the account", Toast.LENGTH_SHORT);
                         tst.show();

                     }

                }

                @Override
                public void onFailure(Call<RecReg> call, Throwable t) {
                    //Log.e(TAG, "Unable to submit post to API.");

                    Toast tst = Toast.makeText(getApplicationContext(), "Server Error Retry registering", Toast.LENGTH_SHORT);
                    tst.show();


                }
            });
        }
        public void Rclose(View view) {

            Intent rc = new Intent(RReg.this, HomePage.class);
            startActivity(rc);

        }

    }


