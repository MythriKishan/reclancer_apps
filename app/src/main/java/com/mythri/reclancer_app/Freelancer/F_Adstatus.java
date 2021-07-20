package com.mythri.reclancer_app.Freelancer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import com.mythri.reclancer_app.Globals;
import com.mythri.reclancer_app.Model.FreeAdStatus;
import com.mythri.reclancer_app.Network.APIFAdStatus;
import com.mythri.reclancer_app.Network.ApiUtil;
import com.mythri.reclancer_app.R;
import com.mythri.reclancer_app.Recruiter.ManageRJobs;
import com.mythri.reclancer_app.Recruiter.R_Adstatus;

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

public class F_Adstatus extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] status_ad = {"Select Status", "open", "closed", "cancelled", "resecheduled"};

    EditText adstatus;

    Spinner spin;

    String st;

    private APIFAdStatus mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fstatus);

        final String token = ((Globals) this.getApplication()).getToken();
        Toast.makeText(getApplicationContext(),token,Toast.LENGTH_SHORT).show();

        spin = (Spinner) findViewById(R.id.ad_status);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, status_ad);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        Intent myIntent = getIntent();
        String adid = myIntent.getStringExtra("ad_id");
        Toast.makeText(getApplicationContext(), adid, Toast.LENGTH_SHORT).show();

        JSONObject post_dict = new JSONObject();

        try {
            post_dict.put("adid", adid);
            post_dict.put("token", token);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (post_dict.length() > 0) {
            new F_Adstatus.SendDataToServer().execute(String.valueOf(post_dict));

        }

        adstatus = (EditText) findViewById(R.id.status);
        adstatus.setEnabled(false);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    class SendDataToServer extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String JsonResponse = null;
            String JsonDATA = params[0];
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL("https://reclancer.com/appFad_status.php");
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
            final String ad_id = c.getString("ad_id");
            Toast.makeText(getBaseContext(), ad_id, Toast.LENGTH_SHORT).show();

            final String status = c.getString("status");
            Toast.makeText(getBaseContext(), status, Toast.LENGTH_SHORT).show();

            adstatus.setText(status);

            Button btn = (Button) findViewById(R.id.change_status);

            mAPIService = ApiUtil.getAPIF_adstatus();

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    st = String.valueOf(spin.getSelectedItem());
                    Toast.makeText(getApplicationContext(), st, Toast.LENGTH_SHORT).show();

                    F_statusupdate(ad_id, st);


                }
            });
        }
    }
        public void F_statusupdate(String ad_id, String st){
            mAPIService.F_statusupdate(ad_id, st).enqueue(new Callback<FreeAdStatus>() {
                @Override
                public void onResponse(Call<FreeAdStatus> call, Response<FreeAdStatus> response) {

                    if (response.isSuccessful()) {
                        //showResponse(response.body().toString());
                        //Log.i(TAG, "post submitted to API." + response.body().toString());
                        Toast tst = Toast.makeText(getApplicationContext(), "Changed Status Successfully", Toast.LENGTH_SHORT);
                        tst.show();

                        Intent myIntent = getIntent();
                        final String Id = myIntent.getStringExtra("Id");
                        Toast t = Toast.makeText(getApplicationContext(), Id, Toast.LENGTH_SHORT);
                        t.show();
                        Intent su = new Intent(F_Adstatus.this, ManageFJobs.class);
                        su.putExtra("Id",Id);
                        startActivity(su);
                    }
                }

                @Override
                public void onFailure(Call<FreeAdStatus> call, Throwable t) {
                    //Log.e(TAG, "Unable to submit post to API.");

                    Toast tst = Toast.makeText(getApplicationContext(), "Server Error Retry registering", Toast.LENGTH_SHORT);
                    tst.show();


                }
            });
        }



    }

