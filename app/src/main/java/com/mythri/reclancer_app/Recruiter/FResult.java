package com.mythri.reclancer_app.Recruiter;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;

import android.widget.TextView;
import android.widget.Toast;

import com.mythri.reclancer_app.Globals;
import com.mythri.reclancer_app.Model.FResultAds;
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
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FResult extends AppCompatActivity {

    private FResultAdapter retrofitAdapter;
    private RecyclerView recyclerView;

    //private APIFFilter mAPIService;
    private APIRLogout mAPIService;

    FResultAdapter adapter;
    EditText skill;
    String res;
    List<FResultAds> resAds = new ArrayList<>();

    LinearLayoutManager llm;

    Spinner statename,categry,work;

    int st,location;
    String cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freeresults);

        String token = ((Globals) this.getApplication()).getToken();
        Toast.makeText(getApplicationContext(),token,Toast.LENGTH_SHORT).show();

        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        Intent myIntent = getIntent();
        res = myIntent.getStringExtra("resp");
        Toast.makeText(getApplicationContext(),res,Toast.LENGTH_SHORT).show();

        try {
            displayData(res);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ImageView img = (ImageView) findViewById(R.id.myImageId);
        img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                View cv = (View) findViewById(R.id.layout_to_hide) ;

                if (cv.getVisibility() == View.VISIBLE)
                    cv.setVisibility(View.GONE);
                else
                    cv.setVisibility(View.VISIBLE);
            }
        });

        skill = findViewById(R.id.pskills);
        statename = findViewById(R.id.state);
        categry = findViewById(R.id.category);
        work = findViewById(R.id.worktype);

        getJSON("https://reclancer.com/getstate");
        getCat("https://reclancer.com/getcatgry");
        getWork("https://reclancer.com/getworktype");

        Button btn = (Button) findViewById(R.id.filter);

        //mAPIService = ApiUtil.getAPIR_filter();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast tst;

                st = statename.getSelectedItemPosition();
                tst = Toast.makeText(getApplicationContext(),""+st,Toast.LENGTH_SHORT);
                tst.show();

                location = work.getSelectedItemPosition();
                tst = Toast.makeText(getApplicationContext(), ""+location, Toast.LENGTH_SHORT);
                tst.show();

                cat = String.valueOf(categry.getSelectedItem());
                tst = Toast.makeText(getApplicationContext(), cat, Toast.LENGTH_SHORT);
                tst.show();

                String pskills = skill.getText().toString().trim();
                tst = Toast.makeText(getApplicationContext(), pskills, Toast.LENGTH_SHORT);
                tst.show();

                // R_Filter(cat,pskills,st,location);

                JSONObject post_dict = new JSONObject();

                try {
                    post_dict.put("cat", cat);
                    post_dict.put("pskills", pskills);
                    post_dict.put("state", st);
                    post_dict.put("worktype", location);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (post_dict.length() > 0) {
                    new FResult.SendJsonDataToServer().execute(String.valueOf(post_dict));

                }


            }

        });

    }



    class SendJsonDataToServer extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String JsonResponse = null;
            String JsonDATA = params[0];
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL("https://reclancer.com/appfree_filter.php");
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

            //loadData(s);
            try {
                displayData(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }




    private void displayData(String json) throws JSONException {

        ArrayList<FResultAds> modelRecyclerArrayList = new ArrayList<>();
        JSONArray dataArray = new JSONArray(json);

        for (int i = 0; i < dataArray.length(); i++) {

            FResultAds resultads = new FResultAds();
            JSONObject dataobj = dataArray.getJSONObject(i);


            resultads.setName(dataobj.getString("name"));
            resultads.setAd_id(dataobj.getString("ad_id"));
            resultads.setSkills(dataobj.getString("skills"));
            resultads.setExp(dataobj.getString("exp"));


            modelRecyclerArrayList.add(resultads);

        }

        retrofitAdapter = new FResultAdapter(this,modelRecyclerArrayList);
        recyclerView.setAdapter(retrofitAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

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
                        Intent home = new Intent(FResult.this, RHome.class);
                        home.putExtra("Id",Id);
                        startActivity(home);
                        return true;

                    case R.id.postad:
                        Intent post = new Intent(FResult.this, RPostAd.class);
                        post.putExtra("Id",Id);
                        post.putExtra("token",Token);
                        startActivity(post);
                        return true;

                    case R.id.mjobs:
                        Intent manage = new Intent(FResult.this, ManageRJobs.class);
                        manage.putExtra("Id",Id);
                        manage.putExtra("token",Token);
                        startActivity(manage);
                        return true;

                    case R.id.Rdash:
                        Intent rfdash = new Intent(FResult.this, R_Dash.class);
                        rfdash.putExtra("Id",Id);
                        rfdash.putExtra("token",Token);
                        startActivity(rfdash);
                        return true;

                    case R.id.Rfdash:
                        Intent rdash = new Intent(FResult.this, RFDash.class);
                        rdash.putExtra("Id",Id);
                        rdash.putExtra("token",Token);
                        startActivity(rdash);
                        return true;


                    case R.id.recsetting:
                        Intent pset = new Intent(FResult.this, R_PSettings.class);
                        pset.putExtra("Id",Id);
                        startActivity(pset);
                        return true;

                    case R.id.fsearch:
                        Intent search = new Intent(FResult.this, FSearch.class);
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



    public void details(View view) {

        TextView adid = (TextView) findViewById(R.id.ad_id);
        String ad_id = adid.getText().toString().trim();

        Intent myIntent = getIntent();
        String user_id = myIntent.getStringExtra("id");

        Intent i = new Intent(FResult.this, FDetails.class);
        i.putExtra("adId",ad_id);
        i.putExtra("Id",user_id);
        startActivity(i);

    }


    private void getCat(final String urlWebService) {

        class GetCat extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadCategory(s);
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
        GetCat getCaty = new GetCat();
        getCaty.execute();
    }

    private void loadCategory(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);

        String[] recresults = new String[jsonArray.length()+1];
        recresults[0]="Select Category"; //item at pos 0
        int j=1;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            recresults[j++] = obj.getString("name");
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, recresults);
        categry.setAdapter(arrayAdapter);
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
    }


    private void getWork(final String urlWebService) {

        class GetWork extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadWork(s);
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

        GetWork getWork = new GetWork();
        getWork.execute();
    }

    private void loadWork(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);

        String[] workresults = new String[jsonArray.length()+1];
        workresults[0]="Select Work Type"; //item at pos 0
        int j=1;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            workresults[j++] = obj.getString("type");
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, workresults);
        work.setAdapter(arrayAdapter);
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

        Intent intent = new Intent(FResult.this, RLogin.class);
        finish();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

}
