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
import android.widget.TextView;
import android.widget.Toast;

import com.mythri.reclancer_app.Globals;
import com.mythri.reclancer_app.Model.TokenJsonObject;
import com.mythri.reclancer_app.Network.APIRLogout;
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

public class RHome extends AppCompatActivity  {

    TextView name_f,name_m,name_l, mobilenum, emailid, adr,state_name,city_name;
    Button btn;

    private APIRLogout mAPIService;

    String userid,f_name,l_name,name,phone,mail,adrs,st,ct;
    int stateid,cityid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rhome);

        Intent myIntent = getIntent();
        final String userId = myIntent.getStringExtra("Id");
        final String t = myIntent.getStringExtra("token");
        Toast.makeText(getApplicationContext(),t,Toast.LENGTH_SHORT).show();

        //Set Token
        ((Globals) this.getApplication()).setToken(t);

        JSONObject post_dict = new JSONObject();

        try {
            post_dict.put("userid", userId);
            post_dict.put("token",t);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (post_dict.length() > 0) {
            new RHome.SendDataToServer().execute(String.valueOf(post_dict));

        }

        name_f = (TextView) findViewById(R.id.fname);
        name_l = (TextView) findViewById(R.id.lname);
        mobilenum = (TextView) findViewById(R.id.mobile);
        emailid = (TextView) findViewById(R.id.email);
        adr = (TextView) findViewById(R.id.address);
        city_name = (TextView) findViewById(R.id.city);
        state_name = (TextView) findViewById(R.id.state);

        //btn = (Button) findViewById(R.id.editprofile);

    }

        class SendDataToServer extends AsyncTask<String, String, String> {

            @Override
            protected String doInBackground(String... params) {
                String JsonResponse = null;
                String JsonDATA = params[0];
                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("https://reclancer.com/apprec_profile.php");
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
                    }reader = new BufferedReader(new InputStreamReader(inputStream));

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


                }
                catch (UnsupportedEncodingException e) {
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
                    displayData(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

        private void displayData(String json) throws JSONException {
            JSONArray jsonarray = new JSONArray(json);

            for (int i = 0; i < jsonarray.length(); i++) {


                JSONObject c = jsonarray.getJSONObject(i);


                userid = c.getString("id");
                Toast.makeText(getBaseContext(), userid, Toast.LENGTH_SHORT).show();


                f_name = c.getString("fname");
                Toast.makeText(getBaseContext(), f_name, Toast.LENGTH_SHORT).show();

               l_name = c.getString("lname");
                Toast.makeText(getBaseContext(), l_name, Toast.LENGTH_SHORT).show();

                String name = f_name + " " + l_name;
                Toast.makeText(getBaseContext(), name, Toast.LENGTH_SHORT).show();
                name_f.setText(name);

                phone = c.getString("mobile");
                mobilenum.setText(phone);
                Toast.makeText(getBaseContext(), phone, Toast.LENGTH_SHORT).show();

                mail = c.getString("email");
                Toast.makeText(getBaseContext(), mail, Toast.LENGTH_SHORT).show();
                emailid.setText(mail);

                adrs = c.getString("address");
                Toast.makeText(getBaseContext(), adrs, Toast.LENGTH_SHORT).show();
                adr.setText(adrs);

                st = c.getString("state");
                Toast.makeText(getBaseContext(), st, Toast.LENGTH_SHORT).show();
                state_name.setText(st);

                ct = c.getString("city");
                Toast.makeText(getBaseContext(), ct, Toast.LENGTH_SHORT).show();
                city_name.setText(ct);

                stateid = c.getInt("state_id");
                cityid = c.getInt("city_id");

                //name_f.setText(name);
                // name_l.setText(l_name);
                //mobilenum.setText(phone);
                //emailid.setText(mail);
                //adr.setText(adrs);
               // state_name.setText(st);
                //city_name.setText(ct);


               /* btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent edit = new Intent(RHome.this, Edit_rprofile.class);
                        edit.putExtra("user_id",userid);
                        edit.putExtra("firstname",f_name);
                        edit.putExtra("lastname",l_name);
                        edit.putExtra("mobile",phone);
                        edit.putExtra("email",mail);
                        edit.putExtra("address",adrs);
                        startActivity(edit);

                    }
                });*/
            }

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
                        Intent home = new Intent(RHome.this, RHome.class);
                        home.putExtra("Id",Id);
                        startActivity(home);
                        return true;

                    case R.id.postad:
                        Intent post = new Intent(RHome.this, RPostAd.class);
                        post.putExtra("Id",Id);
                        post.putExtra("token",Token);
                        startActivity(post);
                        return true;

                    case R.id.mjobs:
                        Intent manage = new Intent(RHome.this, ManageRJobs.class);
                        manage.putExtra("Id",Id);
                        manage.putExtra("token",Token);
                        startActivity(manage);
                        return true;

                    case R.id.Rdash:
                        Intent rfdash = new Intent(RHome.this, R_Dash.class);
                        rfdash.putExtra("Id",Id);
                        rfdash.putExtra("token",Token);
                        startActivity(rfdash);
                        return true;

                    case R.id.Rfdash:
                        Intent rdash = new Intent(RHome.this, RFDash.class);
                        rdash.putExtra("Id",Id);
                        rdash.putExtra("token",Token);
                        startActivity(rdash);
                        return true;


                    case R.id.recsetting:
                        Intent pset = new Intent(RHome.this, R_PSettings.class);
                        pset.putExtra("Id",Id);
                        startActivity(pset);
                        return true;

                    case R.id.fsearch:
                        Intent search = new Intent(RHome.this, FSearch.class);
                        search.putExtra("Id",Id);
                        startActivity(search);
                        return true;

                    case R.id.rlogout:

                        mAPIService = ApiUtil.getAPI_Rlogout();

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

        Call<TokenJsonObject> logOut = mAPIService.logOutUser(((Globals) this.getApplication()).getToken(),userId);
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

        Intent intent = new Intent(RHome.this, RLogin.class);
        finish();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    public void edit_profile(View view) {

        final String token = ((Globals) RHome.this.getApplication()).getToken();
        Intent edit = new Intent(RHome.this, Edit_rprofile.class);
        edit.putExtra("user_id",userid);
        edit.putExtra("firstname",f_name);
        edit.putExtra("lastname",l_name);
        edit.putExtra("mobile",phone);
        edit.putExtra("email",mail);
        edit.putExtra("address",adrs);
        edit.putExtra("state_id",stateid);
        edit.putExtra("city_id",cityid);
        edit.putExtra("cityname",ct);
        edit.putExtra("token",token);
        startActivity(edit);
    }



}




