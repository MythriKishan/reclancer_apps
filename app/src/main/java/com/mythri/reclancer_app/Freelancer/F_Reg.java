package com.mythri.reclancer_app.Freelancer;

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

import com.mythri.reclancer_app.HomePage;
import com.mythri.reclancer_app.Model.FreelancerReq;
import com.mythri.reclancer_app.Network.APIfreelancer;
import com.mythri.reclancer_app.Network.ApiUtil;
import com.mythri.reclancer_app.PaymentActivity;
import com.mythri.reclancer_app.R;
import com.mythri.reclancer_app.Validation.FreeRegVal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class F_Reg extends AppCompatActivity {

    private TextView mResponseTv;
    private APIfreelancer mAPIService;

    String phone,n,e_mail;
    EditText contact,mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText mail = (EditText) findViewById(R.id.email);
        //Validate Email EditText

        if(mail.getText().toString().length() == 0 )
        {
            mail.setError( "required!" );
        }
        mail.addTextChangedListener(new TextWatcher() {

            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                FreeRegVal.isEmailAddress(mail, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        final EditText contact = (EditText) findViewById(R.id.mobile);

        if(contact.getText().toString().length() == 0 )
        {
            contact.setError( "required!" );
        }
        contact.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                FreeRegVal.isMobileNum(contact, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });


        final EditText pwd = (EditText) findViewById(R.id.pass);

        //Password Validation
        if(pwd.getText().toString().length() == 0 )
        {
            pwd.setError( "required!" );
        }
        pwd.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                FreeRegVal.isPassword(pwd, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        final EditText cpwd = (EditText) findViewById(R.id.cpass);

        //Confirm Password Validation
        if(cpwd.getText().toString().length() == 0 )
        {
            cpwd.setError( "required!" );
        }

        cpwd.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                FreeRegVal.isCpass(cpwd, true);
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

        Button payBtn = (Button) findViewById(R.id.pay);
        payBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                e_mail = mail.getText().toString().trim();
                Toast.makeText(getApplicationContext(),e_mail,Toast.LENGTH_SHORT).show();

                n = contact.getText().toString().trim();
                Toast.makeText(getApplicationContext(),n,Toast.LENGTH_SHORT).show();

                String pass = pwd.getText().toString().trim();
                Toast.makeText(getApplicationContext(),pass,Toast.LENGTH_SHORT).show();

                String cpassword = cpwd.getText().toString().trim();
                Toast.makeText(getApplicationContext(),cpassword,Toast.LENGTH_SHORT).show();

                Intent pay = new Intent(F_Reg.this, PaymentActivity.class);
                pay.putExtra("phone",n);
                pay.putExtra("mail",e_mail);
                pay.putExtra("pwd",pass);
                pay.putExtra("cpass",cpassword);
                startActivity(pay);


            }

        });

        Button submitBtn = (Button) findViewById(R.id.freg);

        mAPIService = ApiUtil.getAPIfrelancer();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast tst;
                String email = mail.getText().toString().trim();
                final String phone = contact.getText().toString().trim();
                Toast.makeText(getApplicationContext(),phone,Toast.LENGTH_SHORT).show();
                String pass = pwd.getText().toString().trim();
                String cpassword = cpwd.getText().toString().trim();

                //On Form Validation
                if((!TextUtils.isEmpty(email)) && (!TextUtils.isEmpty(phone)) && (!TextUtils.isEmpty(pass)) && (!TextUtils.isEmpty(cpassword)) )
                {
                    sendPost(email,phone, pass, cpassword);
                }
                else {
                    tst = Toast.makeText(getApplicationContext(), "Please enter Valid Data in the form", Toast.LENGTH_SHORT);
                    tst.show();
                }

            }

        });

    }

    public void sendPost(String mail, final String phone, String password, String cpass) {
        mAPIService.savePost(mail,phone,password, cpass).enqueue(new Callback<FreelancerReq>() {
            @Override
            public void onResponse(Call<FreelancerReq> call, Response<FreelancerReq> response) {



                if (response.isSuccessful()) {
                    //showResponse(response.body().toString());
                    //Log.i(TAG, "post submitted to API." + response.body().toString());

                    String num = response.body().getRandnum();

                    Intent otp =  new Intent(F_Reg.this,FOTP.class);
                    otp.putExtra("phone",phone);
                    otp.putExtra("num",num);
                    startActivity(otp);

                    Toast tst = Toast.makeText(getApplicationContext(), "Activation email has been sent to your emailId.Click on the link to activate the account", Toast.LENGTH_SHORT);
                    tst.show();
                }
                else if (response.code() == 400)
                {
                    Toast tst = Toast.makeText(getApplicationContext(), "Duplicate Email Id or Number",Toast.LENGTH_SHORT);
                    tst.show();
                }
                else
                {
                    Toast tst = Toast.makeText(getApplicationContext(), "Server error",Toast.LENGTH_SHORT);
                    tst.show();
                }



            }

            @Override
            public void onFailure(Call<FreelancerReq> call, Throwable t) {
                //Log.e(TAG, "Unable to submit post to API.");

                Toast tst = Toast.makeText(getApplicationContext(), "Server Error Retry registering", Toast.LENGTH_SHORT);
                tst.show();


            }
        });
    }
    public void Rclose(View view) {

        Intent rc = new Intent(F_Reg.this, HomePage.class);
        startActivity(rc);

    }

}
