package com.mythri.reclancer_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mythri.reclancer_app.Freelancer.FLogin;
import com.mythri.reclancer_app.Freelancer.F_Reg;
import com.mythri.reclancer_app.Recruiter.RLogin;
import com.mythri.reclancer_app.Recruiter.RReg;

public class HomePage extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        final TextView r_login = (TextView) findViewById(R.id.rlogin);
        final TextView f_login = (TextView) findViewById(R.id.flogin);
        final TextView r_reg = (TextView) findViewById(R.id.rreg);
        final TextView f_reg = (TextView) findViewById(R.id.freg);

        String reclogin = r_login.getText().toString();
        String freelogin = f_login.getText().toString();
        String recreg = r_reg.getText().toString();
        String freereg = f_reg.getText().toString();




        View.OnClickListener listener = new View.OnClickListener()
        {
            public void onClick(View v)
            {
                switch (v.getId()) {

                    case R.id.freg:

                        //Do things
                        Intent fr = new Intent(HomePage.this, F_Reg.class);
                        startActivity(fr);
                        break;
                        //return true;

                    case R.id.flogin:
                        //Do things
                        Intent fl = new Intent(HomePage.this, FLogin.class);
                        startActivity(fl);
                        break;
                        //return true;

                    case R.id.rlogin:
                        //Do things
                        Intent rl = new Intent(HomePage.this, RLogin.class);
                        startActivity(rl);
                        //return true;
                        break;

                    case R.id.rreg:
                        //Do things
                        Intent rr = new Intent(HomePage.this, RReg.class);
                        startActivity(rr);
                        //return true;
                        break;
                }

            }
        };

        f_login.setOnClickListener(listener);
        f_reg.setOnClickListener(listener);
        r_login.setOnClickListener(listener);
        r_reg.setOnClickListener(listener);


    }


}
