package com.mythri.reclancer_app.Recruiter;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mythri.reclancer_app.Globals;
import com.mythri.reclancer_app.Model.RecPSet;
import com.mythri.reclancer_app.Model.TokenJsonObject;
import com.mythri.reclancer_app.Network.APIRLogout;
import com.mythri.reclancer_app.Network.APIRP_settings;
import com.mythri.reclancer_app.Network.ApiUtil;
import com.mythri.reclancer_app.R;
import com.mythri.reclancer_app.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

public class R_PSettings extends AppCompatActivity {

    //implements RadioGroup.OnCheckedChangeListener{

    private RadioGroup radioemail, radiomobile, radiophoto;
    private RadioButton mailyes, phoneyes, snapyes,mailno,phoneno,snapno;
    private Button btnDisplay;
    private TextView p_mail,p_phone,p_pic;

    private APIRP_settings mAPIService;
    private APIRLogout lAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpsettings);

        String token = ((Globals) this.getApplication()).getToken();
        Toast.makeText(getApplicationContext(),token,Toast.LENGTH_SHORT).show();

        mAPIService = ApiUtil.getAPIRPSet();

        Intent myIntent = getIntent();
        final String userId = myIntent.getStringExtra("Id");
        Toast.makeText(getApplicationContext(),userId,Toast.LENGTH_SHORT).show();

        addListenerOnButton();

        JSONObject post_dict = new JSONObject();
        try {
            post_dict.put("userid",userId);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (post_dict.length() > 0) {

            //do something
            new R_PSettings.SendData().execute(String.valueOf(post_dict));

        }

      /* radioemail = (RadioGroup) findViewById(R.id.email);
        mailyes = (RadioButton) findViewById(R.id.emailyes);
        mailno = (RadioButton) findViewById(R.id.emailno);


        radiomobile = (RadioGroup) findViewById(R.id.mobile);
        phoneyes = (RadioButton) findViewById(R.id.mobileyes);
        phoneno = (RadioButton) findViewById(R.id.mobileno);

        radiophoto = (RadioGroup) findViewById(R.id.photo);
        snapyes = (RadioButton) findViewById(R.id.photyes);
        snapno = (RadioButton)findViewById(R.id.photono);*/

       /* p_mail = (TextView) findViewById(R.id.pmail);
        p_phone = (TextView) findViewById(R.id.pphone);
        p_pic = (TextView) findViewById(R.id.ppic);*/

    }

    class SendData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String JsonResponse = null;
            String JsonDATA = params[0];
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL("https://reclancer.com/app_r_privacy.php");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                // is output buffer writter
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                //set headers and method
                Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                writer.write(JsonDATA);
                // json data
                writer.close();


                InputStream inputStream = urlConnection.getInputStream();
                //input stream
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String inputLine;
                while ((inputLine = reader.readLine()) != null)
                    buffer.append(inputLine + "\n");
                if (buffer.length() == 0) {
                    // Stream was empty. No point in parsing.
                    return null;
                }
                JsonResponse = buffer.toString();
                //response data

                // Log.i(TAG,JsonResponse);

                //Toast.makeText(getApplicationContext(),JsonResponse,Toast.LENGTH_SHORT).show();

                //send to post execute
                return JsonResponse;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        //Log.e(TAG, "Error closing stream", e);
                    }
                }
            }
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

            try {
                loadDetails(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    public void loadDetails(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject c = jsonArray.getJSONObject(i);

            final String e_mail = c.getString("email");
            Toast.makeText(getApplicationContext(),e_mail,Toast.LENGTH_SHORT).show();
            final String pic = c.getString("photo");
            Toast.makeText(getApplicationContext(),pic,Toast.LENGTH_SHORT).show();
            final String phone = c.getString("mobile");
            Toast.makeText(getApplicationContext(),phone,Toast.LENGTH_SHORT).show();

           /* p_mail.setText(e_mail);
            p_phone.setText(phone);
            p_pic.setText(pic);*/


            if (e_mail.equals("yes"))
            {
                mailyes.setChecked(true);;
            }
            else
            {
                mailno.setChecked(true);
            }

           if(phone.equals("yes"))
            {
                phoneyes.setChecked(true);
            }
            else
            {
                phoneno.setChecked(true);
            }

           if(pic.equals("yes"))
            {
                snapyes.setChecked(true);
            }
            else
            {
                snapno.setChecked(true);
            }

           // addListenerOnButton(e_mail,phone,pic);
            addListenerOnButton();

        }




    }



   public void addListenerOnButton()
    {

        radioemail = (RadioGroup) findViewById(R.id.email);
        radiomobile = (RadioGroup) findViewById(R.id.mobile);
        radiophoto = (RadioGroup) findViewById(R.id.photo);

        mailyes = (RadioButton) findViewById(R.id.emailyes);
        mailno = (RadioButton) findViewById(R.id.emailno);
        phoneyes = (RadioButton) findViewById(R.id.mobileyes);
        phoneno = (RadioButton) findViewById(R.id.mobileno);
        snapyes = (RadioButton) findViewById(R.id.photyes);
        snapno = (RadioButton)findViewById(R.id.photono);



      /*  int buttonChecked = radioemail.getCheckedRadioButtonId();
        Toast.makeText(getApplicationContext(),+buttonChecked,Toast.LENGTH_SHORT).show();

        if(buttonChecked == -1)
        {
            mailyes.setChecked(true);
        }*/


        /*radioemail.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.emailyes:
                        mailyes.setChecked(true);
                        break;
                    case R.id.emailno:
                        mailno.setChecked(true);
                        break;
                }
            }
        });

        radiomobile.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.mobileyes:
                        phoneyes.setChecked(true);
                        break;
                    case R.id.mobileno:
                        phoneno.setChecked(true);
                        break;
                }
            }
        });

        radiophoto.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.photyes:
                        snapyes.setChecked(true);
                        break;
                    case R.id.photono:
                        snapno.setChecked(true);
                        break;
                }
            }
        });*/


        btnDisplay = (Button) findViewById(R.id.psettings);

        btnDisplay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent myIntent = getIntent();
                String userId = myIntent.getStringExtra("Id");
                Toast.makeText(getApplicationContext(), userId, Toast.LENGTH_SHORT).show();

                RadioButton mail = (RadioButton) radioemail.findViewById(radioemail.getCheckedRadioButtonId());
                String email_id = mail.getText().toString();
                if (email_id.equals("Yes")) {
                    email_id = "yes";

                } else {
                    email_id = "no";
                }
                Toast.makeText(R_PSettings.this, email_id, Toast.LENGTH_SHORT).show();

                RadioButton phone = (RadioButton) radiomobile.findViewById(radiomobile.getCheckedRadioButtonId());
                String mobile_num = phone.getText().toString();
                if (mobile_num.equals("Yes")) {
                    mobile_num = "yes";

                } else {
                    mobile_num = "no";
                }
                Toast.makeText(R_PSettings.this, mobile_num, Toast.LENGTH_SHORT).show();

                RadioButton snap = (RadioButton) radiophoto.findViewById(radiophoto.getCheckedRadioButtonId());
                String pics = snap.getText().toString();
                if (pics.equals("Yes")) {
                    pics = "yes";

                } else {
                    pics = "no";
                }

                Toast.makeText(R_PSettings.this, pics, Toast.LENGTH_SHORT).show();

                RPPost(userId, email_id, mobile_num, pics);

            }
        });

    }

    public void RPPost(String userId, String email, String mobile, String photo) {
        mAPIService.RPPost(userId, email, mobile, photo).enqueue(new Callback<RecPSet>() {
            @Override
            public void onResponse(Call<RecPSet> call, Response<RecPSet> response) {

                if (response.isSuccessful()) {
                    //showResponse(response.body().toString());
                    //Log.i(TAG, "post submitted to API." + response.body().toString());
                    Toast tst = Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT);
                    tst.show();
                }
            }

            @Override
            public void onFailure(Call<RecPSet> call, Throwable t) {
                //Log.e(TAG, "Unable to submit post to API.");

                Toast tst = Toast.makeText(getApplicationContext(), "Server Error Retry registering", Toast.LENGTH_SHORT);
                tst.show();


            }
        });


    }

    public void ShowMenu(View view) {

        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.main, popup.getMenu());
        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent myIntent = getIntent();
                final String Id = myIntent.getStringExtra("Id");
                final String Token = myIntent.getStringExtra("token");

                switch (item.getItemId()) {
                    case R.id.home:
                        Intent home = new Intent(R_PSettings.this, RHome.class);
                        home.putExtra("Id",Id);
                        startActivity(home);
                        return true;

                    case R.id.postad:
                        Intent post = new Intent(R_PSettings.this, RPostAd.class);
                        post.putExtra("Id",Id);
                        post.putExtra("token",Token);
                        startActivity(post);
                        return true;

                    case R.id.mjobs:
                        Intent manage = new Intent(R_PSettings.this, ManageRJobs.class);
                        manage.putExtra("Id",Id);
                        manage.putExtra("token",Token);
                        startActivity(manage);
                        return true;

                    case R.id.Rdash:
                        Intent rfdash = new Intent(R_PSettings.this, R_Dash.class);
                        rfdash.putExtra("Id",Id);
                        rfdash.putExtra("token",Token);
                        startActivity(rfdash);
                        return true;

                    case R.id.Rfdash:
                        Intent rdash = new Intent(R_PSettings.this, RFDash.class);
                        rdash.putExtra("Id",Id);
                        rdash.putExtra("token",Token);
                        startActivity(rdash);
                        return true;


                    case R.id.recsetting:
                        Intent pset = new Intent(R_PSettings.this, R_PSettings.class);
                        pset.putExtra("Id",Id);
                        startActivity(pset);
                        return true;

                    case R.id.fsearch:
                        Intent search = new Intent(R_PSettings.this, FSearch.class);
                        search.putExtra("Id",Id);
                        startActivity(search);
                        return true;

                    case R.id.rlogout:

                        lAPIService = ApiUtil.getAPI_Rlogout();

                        logOutMethod();

                       /*getLogout("http://reclancer.com/appflogout.php");
                       Session session= new Session(getApplicationContext());
                       session.destroySession();
                       Intent logout = new Intent(FHome.this,FLogin.class);
                       startActivity(logout);
                       return true;*/

                    default:
                        return false;
                }

                //return(super.onMenuItemSelected(1,item));

            }
        });
    }
    private void logOutMethod() {

        Intent myIntent = getIntent();
        final String userId = myIntent.getStringExtra("Id");

        Call<TokenJsonObject> logOut =  lAPIService.logOutUser(((Globals) this.getApplication()).getToken(),userId);
        logOut.enqueue(new Callback<TokenJsonObject>() {
            @Override
            public void onResponse(Call<TokenJsonObject> call, Response<TokenJsonObject> response) {

                if (response.isSuccessful()) {

                    Toast tst = Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT);
                    tst.show();

                    //unset( $GLOBALS['Stoken'] );

                    goToLogInActivity();
                }
                  /*  TokenJsonObject result = response.body();
                    if (result.getData() != null) {
                        Snackbar.make(findViewById(android.R.id.content), result.getMsg(), Snackbar.LENGTH_LONG).show();
                    } else {
                        goToLogInActivity();
                        Snackbar.make(findViewById(android.R.id.content), result.getMsg(), Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(findViewById(android.R.id.content), response.message(), Snackbar.LENGTH_LONG).show();
                }*/
            }

            @Override
            public void onFailure(Call<TokenJsonObject> call, Throwable t) {
                //Log.e(TAG, "Unable to submit post to API.");

                Toast tst = Toast.makeText(getApplicationContext(), "Server Error Retry registering", Toast.LENGTH_SHORT);
                tst.show();

                goToLogInActivity();


            }
        });
    }

    private void goToLogInActivity() {
        //getPreference().removeLoginPreferences();

        ((Globals) this.getApplication()).setToken("");

        String tk = ((Globals)this.getApplicationContext()).Stoken;

        Toast.makeText(getApplicationContext(),tk,Toast.LENGTH_SHORT).show();


        Session session= new Session(getApplicationContext());
        session.destroySession();

        Intent intent = new Intent(R_PSettings.this, RLogin.class);
        finish();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

}
