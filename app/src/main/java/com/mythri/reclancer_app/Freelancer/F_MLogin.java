package com.mythri.reclancer_app.Freelancer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mythri.reclancer_app.Globals;
import com.mythri.reclancer_app.Model.FLoginRes;
import com.mythri.reclancer_app.Model.FMLogin;
import com.mythri.reclancer_app.Network.APIFMLogin;
import com.mythri.reclancer_app.Network.APIFreeLogin;
import com.mythri.reclancer_app.Network.ApiUtil;
import com.mythri.reclancer_app.R;
import com.mythri.reclancer_app.Recruiter.ROTP;
import com.mythri.reclancer_app.Recruiter.RReg;
import com.mythri.reclancer_app.Validation.FreeRegVal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class F_MLogin extends Activity {

    private TextView mResponseTv;
    private APIFMLogin mAPILogin;

    String email, id,mob;
    TextView userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_mlogin);

        final EditText phone = (EditText) findViewById(R.id.fmobile);

        //Validate Email Field
        if (phone.getText().toString().length() == 0) {
            phone.setError("Mobile is required!");
        }

        phone.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                FreeRegVal.isMobileNum(phone, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });


        Button submitBtn = (Button) findViewById(R.id.m_login);

        mAPILogin = ApiUtil.getAPI_FMLogin();
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast tst;

                String mob = phone.getText().toString().trim();

                if((!TextUtils.isEmpty(mob)))
                {
                    m_login(mob);
                }

                else
                {
                    //tst = Toast.makeText(getApplicationContext(),"Please enter Valid Data in the form",Toast.LENGTH_SHORT);
                    // tst.show();
                    Snackbar.make(view, "Please enter required fields with Valid Data in the form", Snackbar.LENGTH_LONG).show();
                }

            }
        });


    }
    public void m_login(String mobile) {
        mAPILogin.m_login(mobile).enqueue(new Callback<FMLogin>() {

            @Override
            public void onResponse(Call<FMLogin> call, Response<FMLogin> response) {
                String JSON_STRING = null;

                if(response.isSuccessful()) {
                    //String id = response.body().getID();
                    String mobile  = response.body().getMobile();
                    String num = response.body().getRandnum();

                        Intent otp =  new Intent(F_MLogin.this, FOTP_Login.class);
                        otp.putExtra("phone",mobile);
                        otp.putExtra("num",num);
                        startActivity(otp);
                    }
                else
                {
                    Toast.makeText(F_MLogin.this, "Incorrect Mobile Number", Toast.LENGTH_SHORT).show();

                }
            }



            @Override
            public void onFailure(Call<FMLogin> call, Throwable t) {
                //Log.e(TAG, "Unable to submit post to API.");

                Toast tst = Toast.makeText(getApplicationContext(),"Incorrect Mobile Number",Toast.LENGTH_SHORT);
                tst.show();

                //Snackbar.make(, "Please enter required fields with Valid Data in the form", Snackbar.LENGTH_LONG).show();
            }
        });
    }



   /* public void m_login(View view) {

        Intent reset = new Intent(F_MLogin.this, F_MobLogin.class);
        startActivity(reset);
    }*/
}
