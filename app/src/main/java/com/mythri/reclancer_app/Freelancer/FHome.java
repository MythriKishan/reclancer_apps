package com.mythri.reclancer_app.Freelancer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.mythri.reclancer_app.Network.APIFLogout;
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

public class FHome extends AppCompatActivity implements android.support.v7.widget.PopupMenu.OnMenuItemClickListener {

    TextView name_f,name_m,name_l, mobilenum, emailid, adr,state_name,city_name;
    String userid,f_name,l_name,name,phone,mail,adrs,st,ct;
    int stateid,cityid;


    SharedPreferences prefs;
    SharedPreferences.Editor edit;
    private APIFLogout mAPIService;

Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fhome);

        Intent myIntent = getIntent();
        final String userId = myIntent.getStringExtra("Id");
        Toast.makeText(getApplicationContext(),userId,Toast.LENGTH_SHORT).show();
        final String t = myIntent.getStringExtra("token");
        Toast.makeText(getApplicationContext(),t,Toast.LENGTH_SHORT).show();

        final String token = ((Globals) this.getApplication()).getToken();

      /*  prefs=getApplicationContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        edit=prefs.edit();

            edit.putString("token",t);
            Log.i("Login",t);
            edit.commit();*/



      //Set Token
      //  ((Globals) this.getApplication()).setToken(t);

        //Get Token

        /*Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();*/

        JSONObject post_dict = new JSONObject();

        try {
            post_dict.put("userid",userId);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (post_dict.length() > 0) {
            new FHome.SendDataToServer().execute(String.valueOf(post_dict));

        }

        name_f = (TextView) findViewById(R.id.fname);
        name_l = (TextView) findViewById(R.id.lname);
        mobilenum = (TextView)findViewById(R.id.mobile);
        emailid = (TextView)findViewById(R.id.email);
        adr = (TextView)findViewById(R.id.address);
        city_name = (TextView) findViewById(R.id.city);
        state_name = (TextView)findViewById(R.id.state);
       // btn =  (Button) findViewById(R.id.editprofile);

    }

    class SendDataToServer extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String JsonResponse = null;
            String JsonDATA = params[0];
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL("https://reclancer.com/appfree_profile.php");
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
            //Toast.makeText(getBaseContext(), userID, Toast.LENGTH_SHORT).show();

            f_name = c.getString("fname");
            Toast.makeText(getBaseContext(), f_name, Toast.LENGTH_SHORT).show();

            l_name = c.getString("lname");
            Toast.makeText(getBaseContext(), l_name, Toast.LENGTH_SHORT).show();

            //name = f_name + " " + l_name;
            String name = f_name + " " + l_name;
            Toast.makeText(getBaseContext(), name, Toast.LENGTH_SHORT).show();
            name_f.setText(name);


            phone = c.getString("mobile");
           mobilenum.setText(phone);
            Toast.makeText(getBaseContext(), phone, Toast.LENGTH_SHORT).show();


           mail = c.getString("email");
            emailid.setText(mail);
            Toast.makeText(getBaseContext(), mail, Toast.LENGTH_SHORT).show();

            adrs = c.getString("address");
            adr.setText(adrs);
            Toast.makeText(getBaseContext(), l_name, Toast.LENGTH_SHORT).show();

            st = c.getString("state");
            state_name.setText(st);

            ct = c.getString("city");
            Toast.makeText(getBaseContext(), ct ,Toast.LENGTH_SHORT).show();
            city_name.setText(ct);

           stateid = c.getInt("state_id");
           cityid = c.getInt("city_id");



           /* btn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                  final String token = ((Globals) FHome.this.getApplication()).getToken();
                  Intent edit = new Intent(FHome.this, Edit_fprofile.class);
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
          });*/
        }

    }





   public void ShowMenu(View view) {

        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.main_free, popup.getMenu());
        popup.show();

      popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
           @Override
           public boolean onMenuItemClick(MenuItem item) {

               Intent myIntent = getIntent();
               final String Id = myIntent.getStringExtra("Id");
               final String Token = myIntent.getStringExtra("token");

               switch (item.getItemId()) {
                   case R.id.fhome:
                       Intent home = new Intent(FHome.this, FHome.class);
                       home.putExtra("Id",Id);
                       home.putExtra("token",Token);
                       startActivity(home);
                       return true;

                   case R.id.postad:
                       Intent post = new Intent(FHome.this, FPostAd.class);
                       post.putExtra("Id",Id);
                       post.putExtra("token",Token);
                       startActivity(post);
                       return true;

                   case R.id.mfjobs:
                       Intent manage = new Intent(FHome.this, ManageFJobs.class);
                       manage.putExtra("Id",Id);
                       manage.putExtra("token",Token);
                       startActivity(manage);
                       return true;

                   case R.id.fdash:
                       Intent dash = new Intent(FHome.this, F_Dash.class);
                       dash.putExtra("Id",Id);
                       dash.putExtra("token",Token);
                       startActivity(dash);
                       return true;

                   case R.id.fRdash:
                       Intent Rdash = new Intent(FHome.this, FRDash.class);
                       Rdash.putExtra("Id",Id);
                       Rdash.putExtra("token",Token);
                       startActivity(Rdash);
                       return true;

                   case R.id.psettings:
                       Intent pset = new Intent(FHome.this, F_PSettigs.class);
                       pset.putExtra("Id",Id);
                       startActivity(pset);
                       return true;

                   case R.id.rsearch:
                       Intent search = new Intent(FHome.this, RSearch.class);
                       search.putExtra("Id",Id);
                       startActivity(search);
                       return true;

                   case R.id.flogout:

                       mAPIService = ApiUtil.getAPI_Flogout();

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

        Intent intent = new Intent(FHome.this, FLogin.class);
        finish();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        overridePendingTransition(0, 0);
        startActivity(intent);
    }



    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_free, menu);
        return true;
    }*/

   /* @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        final int id = item.getItemId();
        switch (item.getItemId()) {

            case R.id.fhome:
                Intent home = new Intent(FHome.this, FHome.class);
                startActivity(home);
                return true;

            case R.id.postad:
                Intent post = new Intent(FHome.this, FHome.class);
                startActivity(post);
                return true;

            case R.id.rsearch:

                Intent search = new Intent(FHome.this, FResult.class);
                startActivity(search);
                return true;


        }

        return super.onOptionsItemSelected(item);
    }*/



    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return true;
    }


    public void edit_profile(View view) {


        final String token = ((Globals) FHome.this.getApplication()).getToken();
        Intent edit = new Intent(FHome.this,Edit_fprofile.class);
        edit.putExtra("Id",userid);
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


    private void getToken() {
        prefs=this.getApplicationContext().getSharedPreferences("myPrefs",Context.MODE_PRIVATE);
        String token = prefs.getString("token","");
    }
}

