package com.mythri.reclancer_app.Recruiter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mythri.reclancer_app.Freelancer.FLogin;
import com.mythri.reclancer_app.Freelancer.F_MLogin;
import com.mythri.reclancer_app.HomePage;
import com.mythri.reclancer_app.Model.RecLoginRes;
import com.mythri.reclancer_app.Network.APIRecLogin;
import com.mythri.reclancer_app.Network.ApiUtil;
import com.mythri.reclancer_app.R;
import com.mythri.reclancer_app.Validation.RecRegVal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RLogin extends AppCompatActivity {

    private APIRecLogin mAPILogin;

    String email,id;
    TextView userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rlogin);

        final EditText mail = (EditText) findViewById(R.id.remail);

        //Validate Email Field
        if( mail.getText().toString().length() == 0 )
        {
            mail.setError( "Email is required!" );
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

        final EditText pwd = (EditText) findViewById(R.id.rpass);
        //Validate Password Field
        if( pwd.getText().toString().length() == 0 )
            pwd.setError( "Password is required!" );
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

        Button submitBtn = (Button) findViewById(R.id.login);

        mAPILogin = ApiUtil.getAPIRecLogin();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast tst;

                String email = mail.getText().toString().trim();
                tst = Toast.makeText(getApplicationContext(),email,Toast.LENGTH_SHORT);
                tst.show();
                String pass = pwd.getText().toString().trim();
                tst = Toast.makeText(getApplicationContext(),pass,Toast.LENGTH_SHORT);
                tst.show();

                if((!TextUtils.isEmpty(email)) && (!TextUtils.isEmpty(pass)))
                {
                    loginPost(email,pass);
                }

                else
                {
                    tst = Toast.makeText(getApplicationContext(),"Please enter Valid Data in the form",Toast.LENGTH_SHORT);
                    tst.show();
                }

            }
        });


    }

    public void loginPost(String mail, String password) {
        mAPILogin.LoginPost(mail, password).enqueue(new Callback<RecLoginRes>() {

            @Override
            public void onResponse(Call<RecLoginRes> call, Response<RecLoginRes> response) {
                //String JSON_STRING = null;

                if(response.isSuccessful()) {
                    String id = response.body().getID();
                    String token  = response.body().getJwt();

                    Toast.makeText(getApplicationContext(),token,Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RLogin.this, RHome.class);
                    intent.putExtra("Id",id);
                    intent.putExtra("token",token);
                    startActivity(intent);

                    // showResponse(response.body().toString());
                }
            }



            @Override
            public void onFailure(Call<RecLoginRes> call, Throwable t) {
                //Log.e(TAG, "Unable to submit post to API.");

                Toast tst = Toast.makeText(getApplicationContext(),"Incorrect EMail ID or Password",Toast.LENGTH_SHORT);
                tst.show();


            }
        });
    }


    /*public void login(View view) {

        Intent i = new Intent(FLogin.this,FHome.class);

        startActivity(i);

    }*/

    public void Rclose(View view) {

        Intent c = new Intent(RLogin.this, HomePage.class);
        startActivity(c);
    }

    public void Resetpassword(View view) {

        Intent reset = new Intent(RLogin.this, R_Reset_Pass.class);
        reset.putExtra("Id",id);
        startActivity(reset);
    }

    public void m_login(View view) {
        Intent reset = new Intent(RLogin.this, R_MLogin.class);
        startActivity(reset);

    }
}
