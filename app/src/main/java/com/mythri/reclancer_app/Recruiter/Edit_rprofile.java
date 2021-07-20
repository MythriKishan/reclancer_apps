package com.mythri.reclancer_app.Recruiter;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mythri.reclancer_app.Freelancer.Edit_fprofile;
import com.mythri.reclancer_app.Freelancer.RSearch;
import com.mythri.reclancer_app.Globals;
import com.mythri.reclancer_app.Model.RProfileUpdate;
import com.mythri.reclancer_app.Model.TokenJsonObject;
import com.mythri.reclancer_app.Network.APIFLogout;
import com.mythri.reclancer_app.Network.APIRLogout;
import com.mythri.reclancer_app.Network.APIRProfile;
import com.mythri.reclancer_app.Network.ApiUtil;
import com.mythri.reclancer_app.R;
import com.mythri.reclancer_app.Session;
import com.mythri.reclancer_app.Validation.ProfileVal;

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

public class
Edit_rprofile extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    TextView f_name,l_name,phone,mail,ads;
    private APIRProfile mAPIService;

    Spinner statename,cityname;
    String cname;

    int st,ct,state_id,city_id;

    private APIRLogout APIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rprofile);

        final String token = ((Globals) this.getApplication()).getToken();
        Toast.makeText(getApplicationContext(),token,Toast.LENGTH_SHORT).show();


        statename = (Spinner) findViewById(R.id.state);
        cityname = (Spinner) findViewById(R.id.city);

        Intent myIntent = getIntent();
        final String userId = myIntent.getStringExtra("user_id");
        Toast.makeText(getApplicationContext(),userId,Toast.LENGTH_SHORT).show();

        final String first_name = myIntent.getStringExtra("firstname");
        Toast.makeText(getApplicationContext(),first_name,Toast.LENGTH_SHORT).show();

        final String last_name = myIntent.getStringExtra("lastname");
        Toast.makeText(getApplicationContext(),last_name,Toast.LENGTH_SHORT).show();

        final String mobilenum = myIntent.getStringExtra("mobile");
        Toast.makeText(getApplicationContext(),mobilenum,Toast.LENGTH_SHORT).show();

        final String emailid = myIntent.getStringExtra("email");
        Toast.makeText(getApplicationContext(),emailid,Toast.LENGTH_SHORT).show();

        final String adrs = myIntent.getStringExtra("address");
        Toast.makeText(getApplicationContext(),adrs,Toast.LENGTH_SHORT).show();

        f_name =  (TextView)findViewById(R.id.fname);
        f_name.setText(first_name);

        l_name =  (TextView)findViewById(R.id.lname);
        l_name.setText(last_name);

        phone =  (TextView)findViewById(R.id.mobile);
        phone.setText(mobilenum);

        mail = (TextView)findViewById(R.id.email);
        mail.setText(emailid);

        ads = (TextView)findViewById(R.id.address);
        ads.setText(adrs);


        state_id = myIntent.getIntExtra("state_id",0);
        //Toast.makeText(getApplicationContext(),state_id,Toast.LENGTH_SHORT).show();

        statename.setSelection(state_id);
        city_id =myIntent.getIntExtra("city_id",0);

       getJSON("https://reclancer.com/getstate");

       cname =  myIntent.getStringExtra("cityname");

        f_name =  (TextView)findViewById(R.id.fname);

        if(f_name.getText().toString().length() == 0 )
        {
            f_name.setError( "required!" );
        }
        f_name.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                ProfileVal.isFName(f_name, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        f_name.setText(first_name);

        l_name =  (TextView)findViewById(R.id.lname);
        if(l_name.getText().toString().length() == 0 )
        {
            l_name.setError( "required!" );
        }
        l_name.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                ProfileVal.isLName(l_name, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        l_name.setText(last_name);

        phone =  (TextView)findViewById(R.id.mobile);
        if(phone.getText().toString().length() == 0 )
        {
            phone.setError( "required!" );
        }
        phone.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                ProfileVal.isPhoneNumber(phone, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        phone.setText(mobilenum);

        mail = (TextView)findViewById(R.id.email);
        if(mail.getText().toString().length() == 0 )
        {
            mail.setError( "required!" );
        }
        mail.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                ProfileVal.isEmailAddress(mail, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        mail.setText(emailid);

        ads = (TextView)findViewById(R.id.address);
        if(ads.getText().toString().length() == 0 )
        {
            ads.setError( "required!" );
        }
        ads.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                ProfileVal.isAddress(ads, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        ads.setText(adrs);


        Button submitBtn = (Button) findViewById(R.id.submit);

        mAPIService = ApiUtil.getAPIRProfile();
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast tst;

                String first_name = f_name.getText().toString().trim();
                String last_name = l_name.getText().toString().trim();
                String mobilenum = phone.getText().toString().trim();
                String emailid = mail.getText().toString().trim();
                String adrs = ads.getText().toString().trim();

                st = statename.getSelectedItemPosition();
                if (statename.getSelectedItemPosition() > 0) {
                    // get selected item value
                    //String itemvalue = String.valueOf(spinner.getSelectedItem());
                    //int state = spinner.getSelectedItemPosition();

                } else {
                    // set error message on spinner
                    TextView errorTextview = (TextView)statename.getSelectedView();
                    errorTextview.setError("");
                    errorTextview.setTextColor(Color.RED);
                    errorTextview.setError("required");
                }
                //tst = Toast.makeText(getApplicationContext(),""+st,Toast.LENGTH_SHORT);
                // tst.show();



                if (cityname.getSelectedItemPosition() > 0) {
                    // get selected item value
                    //String itemvalue = String.valueOf(spinner.getSelectedItem());
                    //int state = spinner.getSelectedItemPosition();

                } else {
                    // set error message on spinner
                    TextView errorTextview = (TextView)cityname.getSelectedView();
                    errorTextview.setError("");
                    errorTextview.setTextColor(Color.RED);
                    errorTextview.setError("required");
                }

               String ci = cityname.getSelectedItem().toString();
                tst = Toast.makeText(getApplicationContext(), ci, Toast.LENGTH_SHORT);
                tst.show();



                if((!TextUtils.isEmpty(first_name)) && (!TextUtils.isEmpty(last_name)) && (!TextUtils.isEmpty(mobilenum)) && (!TextUtils.isEmpty(emailid)) && (!TextUtils.isEmpty(adrs)) && (st!=0) && (!TextUtils.isEmpty(ci)) )
                {                    //isFormValid();
                    sendPost(userId,token,first_name,last_name,mobilenum,emailid,adrs,st,ci);
                    //sendPost(userId,token,first_name,last_name,mobilenum,emailid,adrs,st);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please enter Valid Data in the form", Toast.LENGTH_SHORT).show();

                }



            }


        });



    }

    public void sendPost(final String userId,String token, String first_name, String last_name, String mobilenum, String emailid, String adrs,int st,String ci) {
        mAPIService.savePost(userId,token,first_name,last_name,mobilenum,emailid,adrs,st,ci).enqueue(new Callback<RProfileUpdate>() {
            @Override

            public void onResponse(Call<RProfileUpdate> call, Response<RProfileUpdate> response) {

                if (response.isSuccessful()) {

                    Intent i = new Intent(Edit_rprofile.this, RHome.class);
                    i.putExtra("Id",userId);
                    startActivity(i);
                    //showResponse(response.body().toString());
                    //Log.i(TAG, "post submitted to API." + response.body().toString());
                    //Toast.makeText(getApplicationContext(), "Success",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RProfileUpdate> call, Throwable t) {
                //Log.e(TAG, "Unable to submit post to API.");

                Toast tst = Toast.makeText(getApplicationContext(), "Server Error Retry registering", Toast.LENGTH_SHORT);
                tst.show();


            }
        });
    }

    private void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void loadIntoListView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);

        String[] recresults = new String[jsonArray.length()+1];
        recresults[0]="Select State"; //item at pos 0
        int j=1;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            recresults[j++] = obj.getString("name");
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, recresults);
        statename.setAdapter(arrayAdapter);
        statename.setSelection(state_id);
        statename.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String sp1 = String.valueOf(statename.getSelectedItemId());
        Toast.makeText(this, sp1, Toast.LENGTH_SHORT).show();
        JSONObject post_dict = new JSONObject();

        try {
            post_dict.put("stateid", sp1);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(!sp1.equals("0"))
        {
            //do something
            new Edit_rprofile.SendJsonDataToServer().execute(String.valueOf(post_dict));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    class SendJsonDataToServer extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String JsonResponse = null;
            String JsonDATA = params[0];
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL("https://reclancer.com/appcity_list.php");
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
                if(s != null) {
                    loadCityList(s);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"No City",Toast.LENGTH_SHORT).show();
                    // String n = "0";
                    // loadcityNull(n);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
    private void loadCityList(String json) throws JSONException {

        Intent myIntent = getIntent();
        cname =  myIntent.getStringExtra("cityname");

        JSONArray jsonArray = new JSONArray(json);

        String[] citylist = new String[jsonArray.length() + 1];
        citylist[0] = "Select City"; //item at pos 0
        int j = 1;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            citylist[j++] = obj.getString("city_name");
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, citylist);
        cityname.setAdapter(arrayAdapter);
        int selectionPosition= arrayAdapter.getPosition(cname);
       // Toast.makeText(getApplicationContext(), "" + selectionPosition, Toast.LENGTH_SHORT).show();
        cityname.setSelection(selectionPosition);

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
                        Intent home = new Intent(Edit_rprofile.this, RHome.class);
                        home.putExtra("Id",Id);
                        startActivity(home);
                        return true;

                    case R.id.postad:
                        Intent post = new Intent(Edit_rprofile.this, RPostAd.class);
                        post.putExtra("Id",Id);
                        post.putExtra("token",Token);
                        startActivity(post);
                        return true;

                    case R.id.mjobs:
                        Intent manage = new Intent(Edit_rprofile.this, ManageRJobs.class);
                        manage.putExtra("Id",Id);
                        manage.putExtra("token",Token);
                        startActivity(manage);
                        return true;

                    case R.id.Rdash:
                        Intent rfdash = new Intent(Edit_rprofile.this, R_Dash.class);
                        rfdash.putExtra("Id",Id);
                        rfdash.putExtra("token",Token);
                        startActivity(rfdash);
                        return true;

                    case R.id.Rfdash:
                        Intent rdash = new Intent(Edit_rprofile.this, RFDash.class);
                        rdash.putExtra("Id",Id);
                        rdash.putExtra("token",Token);
                        startActivity(rdash);
                        return true;


                    case R.id.recsetting:
                        Intent pset = new Intent(Edit_rprofile.this, R_PSettings.class);
                        pset.putExtra("Id",Id);
                        startActivity(pset);
                        return true;

                    case R.id.fsearch:
                        Intent search = new Intent(Edit_rprofile.this, FSearch.class);
                        search.putExtra("Id",Id);
                        startActivity(search);
                        return true;

                    case R.id.rlogout:

                        APIService = ApiUtil.getAPI_Rlogout();

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


            }
        });
    }


    private void logOutMethod() {

        Intent myIntent = getIntent();
        final String userId = myIntent.getStringExtra("Id");

        Call<TokenJsonObject> logOut = APIService.logOutUser(((Globals) this.getApplication()).getToken(),userId);
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

        Intent intent = new Intent(Edit_rprofile.this, RLogin.class);
        finish();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

}
