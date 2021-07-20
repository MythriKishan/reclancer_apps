package com.mythri.reclancer_app.Recruiter;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mythri.reclancer_app.Freelancer.RSearch;
import com.mythri.reclancer_app.Globals;
import com.mythri.reclancer_app.Model.RecPostAd;
import com.mythri.reclancer_app.Model.TokenJsonObject;
import com.mythri.reclancer_app.Network.APIRLogout;
import com.mythri.reclancer_app.Network.APIRecPost;
import com.mythri.reclancer_app.Network.ApiUtil;
import com.mythri.reclancer_app.R;
import com.mythri.reclancer_app.Session;
import com.mythri.reclancer_app.Validation.RecPostVal;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RPostAd extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner state,city,categry,work;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;

    int status;

    private APIRLogout LAPIService;

    Button select_sDate,select_eDate,select_cDate;

    int st,ct,gen,wtype;
    EditText new_cat;

    private APIRecPost mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpostad);

       final String token = ((Globals) this.getApplication()).getToken();
        Toast.makeText(getApplicationContext(),token,Toast.LENGTH_SHORT).show();

        select_sDate = findViewById(R.id.sdate);
        select_eDate = findViewById(R.id.edate);
        select_cDate =  findViewById(R.id.cdate);

        select_sDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(RPostAd.this, R.style.TimePickerTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                               // select_sDate.setText(day + "/" + (month + 1) + "/" + year);
                                select_sDate.setText(year + "_" + (month + 1) + "_" + day);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();


            }
        });



        select_eDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                    calendar = Calendar.getInstance();
                    year = calendar.get(Calendar.YEAR);
                    month = calendar.get(Calendar.MONTH);
                    dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                    datePickerDialog = new DatePickerDialog(RPostAd.this, R.style.TimePickerTheme,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                    //select_eDate.setText(day + "/" + (month + 1) + "/" + year)
                                    select_eDate.setText(year + "_" + (month + 1) + "_" + day);
                                }
                            }, year, month, dayOfMonth);

                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
               /* String s = select_sDate.getText().toString();
                SimpleDateFormat formatter = new SimpleDateFormat(year + "_" + (month + 1) + "_" + dayOfMonth);
                String onlyTimeStr = formatter.format(s);

                try {
                    datePickerDialog.getDatePicker().setMinDate(formatter.parse(onlyTimeStr));
                } catch (ParseException e) {
                    e.printStackTrace();
                }*/
                    datePickerDialog.show();




            }



        });


        select_cDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(RPostAd.this, R.style.TimePickerTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                //select_eDate.setText(day + "/" + (month + 1) + "/" + year)
                                select_cDate.setText(year + "_" + (month + 1) + "_" + day);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();


            }
        });






        final EditText j_title = (EditText) findViewById(R.id.jtitle);
        //Validate Title field
        if(j_title.getText().toString().length() == 0 )
        {
            j_title.setError( "required!" );
        }
        j_title.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                RecPostVal.isTitle(j_title, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        final EditText name = (EditText) findViewById(R.id.name);
        //Validate Name Field
        if(name.getText().toString().length() == 0 )
        {
            name.setError( "required!" );
        }
        name.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                RecPostVal.isName(name, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });


        final EditText phone = (EditText) findViewById(R.id.mobile);
        //Validate Mobile Field
        if(phone.getText().toString().length() == 0 )
        {
            phone.setError( "required!" );
        }
        phone.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                RecPostVal.isPhoneNumber(phone, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        final EditText mail = (EditText) findViewById(R.id.email);
        //Validate Email Field
        if(mail.getText().toString().length() == 0 )
        {
            mail.setError( "required!" );
        }
        mail.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                RecPostVal.isEmailAddress(mail, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });


        final EditText p_title = (EditText) findViewById(R.id.ptitle);

        state = findViewById(R.id.state);
        city = findViewById(R.id.city);
        categry = findViewById(R.id.category);
        new_cat = (EditText) findViewById(R.id.newcat);
        work = findViewById(R.id.worktype);

        getJSON("https://reclancer.com/getstate");
        getCat("https://reclancer.com/getcatgry");
        getWork("https://reclancer.com/getworktype");

        //state.setOnItemSelectedListener(this);


        final EditText subcategory = (EditText) findViewById(R.id.subcat);
        final EditText skills = (EditText) findViewById(R.id.pskills);
        //Validate Skills Field
        if(skills.getText().toString().length() == 0 )
        {
            skills.setError( "required!" );
        }
       skills.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                RecPostVal.isPskills(skills, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });


        final EditText adrs = (EditText) findViewById(R.id.address);
        //Validate Address Field
        if(adrs.getText().toString().length() == 0 )
        {
            adrs.setError( "required!" );
        }
        adrs.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                RecPostVal.isAddress(adrs, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        final EditText exps = (EditText) findViewById(R.id.exp);
        final EditText s_skills = (EditText) findViewById(R.id.sskills);
        final EditText p_period = (EditText) findViewById(R.id.pperiod);

        final EditText p_rates = (EditText)findViewById(R.id.prates);
        //validate rates Field
        if(p_rates.getText().toString().length() == 0 )
        {
            p_rates.setError( "required!" );
        }
        p_rates.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                RecPostVal.isPrjRts(p_rates, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        final EditText app_email = (EditText) findViewById(R.id.appemail);
        if(app_email.getText().toString().length() == 0 )
        {
            app_email.setError( "required!" );
        }
        app_email.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                RecPostVal.isEmailAddress(app_email, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        final EditText desc = (EditText) findViewById(R.id.des);
        if(desc.getText().toString().length() == 0 )
        {
            desc.setError( "required!" );
        }
        desc.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                RecPostVal.isDesp(desc, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });



        Button btn = (Button) findViewById(R.id.postad);

        mAPIService = ApiUtil.getAPIRecPost();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = getIntent();
                String userId = myIntent.getStringExtra("Id");
                Toast.makeText(getApplicationContext(),userId,Toast.LENGTH_SHORT).show();

                if (state.getSelectedItemPosition() > 0) {
                    // get selected item value
                    //String itemvalue = String.valueOf(spinner.getSelectedItem());
                    //int state = spinner.getSelectedItemPosition();

                } else {
                    // set error message on spinner
                    TextView errorTextview = (TextView)state.getSelectedView();
                    errorTextview.setError("");
                    errorTextview.setTextColor(Color.RED);
                    errorTextview.setError("required");
                }


                Toast tst;

                String jobtitle = j_title.getText().toString().trim();
                tst = Toast.makeText(getApplicationContext(), jobtitle, Toast.LENGTH_SHORT);
                tst.show();

                String fname = name.getText().toString().trim();
                tst = Toast.makeText(getApplicationContext(), fname, Toast.LENGTH_SHORT);
                tst.show();

                String mobile = phone.getText().toString().trim();
                tst = Toast.makeText(getApplicationContext(), mobile, Toast.LENGTH_SHORT);
                tst.show();

                String email = mail.getText().toString().trim();
                tst = Toast.makeText(getApplicationContext(), email, Toast.LENGTH_SHORT);
                tst.show();


                int st = state.getSelectedItemPosition();
                Toast.makeText(getApplicationContext(),""+st,Toast.LENGTH_SHORT).show();
                int ct = city.getSelectedItemPosition();

                String ci = city.getSelectedItem().toString();
                tst = Toast.makeText(getApplicationContext(), ci, Toast.LENGTH_SHORT);
                tst.show();


                int cat_item =  categry.getSelectedItemPosition();
                if (categry.getSelectedItemPosition() > 0) {
                    // get selected item value
                    // final String cat = String.valueOf(spin.getSelectedItem());
                } else {
                    // set error message on spinner
                    TextView errorTextview = (TextView)categry.getSelectedView();
                    errorTextview.setError("");
                    errorTextview.setTextColor(Color.RED);
                    errorTextview.setError("required");
                }

                final String cat = String.valueOf(categry.getSelectedItem());
                tst = Toast.makeText(getApplicationContext(), cat, Toast.LENGTH_SHORT);
                tst.show();

                String newcat = new_cat.getText().toString().trim();
                tst = Toast.makeText(getApplicationContext(), newcat, Toast.LENGTH_SHORT);
                tst.show();
                if(newcat.equals(""))
                {
                    newcat = "NA";
                    tst = Toast.makeText(getApplicationContext(), newcat, Toast.LENGTH_SHORT);
                    tst.show();
                }



                String subcat = subcategory.getText().toString().trim();
                tst = Toast.makeText(getApplicationContext(), subcat, Toast.LENGTH_SHORT);
                tst.show();

               /* String work = workloc.getText().toString().trim();
                tst = Toast.makeText(getApplicationContext(), work, Toast.LENGTH_SHORT);
                tst.show();*/

                int loc = work.getSelectedItemPosition();
                tst = Toast.makeText(getApplicationContext(), ""+loc, Toast.LENGTH_SHORT);
                tst.show();
                if (work.getSelectedItemPosition() > 0) {
                    // get selected item value
                    //String itemvalue = String.valueOf(spinner.getSelectedItem());
                } else {
                    // set error message on spinner
                    TextView errorTextview = (TextView)work.getSelectedView();
                    errorTextview.setError("");
                    errorTextview.setTextColor(Color.RED);
                    errorTextview.setError("required");
                }

                String pskills = skills.getText().toString().trim();
                tst = Toast.makeText(getApplicationContext(), pskills, Toast.LENGTH_SHORT);
                tst.show();

                String secskills = s_skills.getText().toString().trim();
                tst = Toast.makeText(getApplicationContext(), secskills, Toast.LENGTH_SHORT);
                tst.show();

                String exp = exps.getText().toString().trim();
                tst = Toast.makeText(getApplicationContext(), exp, Toast.LENGTH_SHORT);
                tst.show();

                String address = adrs.getText().toString().trim();
                tst = Toast.makeText(getApplicationContext(), address, Toast.LENGTH_SHORT);
                tst.show();

                String prjperiod = p_period.getText().toString().trim();
                tst = Toast.makeText(getApplicationContext(), prjperiod, Toast.LENGTH_SHORT);
                tst.show();

                String prjrates = p_rates.getText().toString().trim();
                tst = Toast.makeText(getApplicationContext(), prjrates, Toast.LENGTH_SHORT);
                tst.show();


                /*String startdate = sdate.getText().toString().trim();
                tst = Toast.makeText(getApplicationContext(), startdate, Toast.LENGTH_SHORT);
                tst.show();

                String enddate = edate.getText().toString().trim();
                tst = Toast.makeText(getApplicationContext(), enddate, Toast.LENGTH_SHORT);
                tst.show();*/

                String amail = app_email.getText().toString().trim();
                tst = Toast.makeText(getApplicationContext(), amail, Toast.LENGTH_SHORT);
                tst.show();

                String desp = desc.getText().toString().trim();
                tst = Toast.makeText(getApplicationContext(), desp, Toast.LENGTH_SHORT);
                tst.show();


                //String start_date = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH).format(select_sDate);

               /* SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
                String start_date = formatter.format(sdate);
                tst = Toast.makeText(getApplicationContext(), start_date, Toast.LENGTH_SHORT);
                tst.show();*/
                String start_date = select_sDate.getText().toString();
                tst = Toast.makeText(getApplicationContext(), start_date, Toast.LENGTH_SHORT);
                tst.show();



                String end_date = select_eDate.getText().toString();
                tst = Toast.makeText(getApplicationContext(), end_date, Toast.LENGTH_SHORT);
                tst.show();

                /*SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy_MM_dd");
                try {
                    Date date1=dateFormat.parse(start_date);
                    Date date2=dateFormat.parse(end_date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }*/

                tst = Toast.makeText(getApplicationContext(), start_date, Toast.LENGTH_SHORT);
                tst.show();

                if(end_date.compareTo(start_date)<0)
                {
                    status = 0;
                    Toast.makeText(getApplicationContext(),"End date should not be before start date",Toast.LENGTH_SHORT).show();
                    Log.i("Fail","End date should not be before start date");

                }
                else
                {
                  Toast.makeText(getApplicationContext(),"Inside else",Toast.LENGTH_SHORT).show();
                }


                String last_date = select_cDate.getText().toString();
                tst = Toast.makeText(getApplicationContext(), last_date, Toast.LENGTH_SHORT);
                tst.show();

                //if((!TextUtils.isEmpty(jobtitle)) && (!TextUtils.isEmpty(fname)) && (!TextUtils.isEmpty(mobile)) && (!TextUtils.isEmpty(email)) && (!TextUtils.isEmpty(address))  && (!TextUtils.isEmpty(desp)) && (!TextUtils.isEmpty(amail)) && (!TextUtils.isEmpty(start_date)) && (!TextUtils.isEmpty(end_date)) && (!TextUtils.isEmpty(last_date)) && (!TextUtils.isEmpty(prjrates)) && (status !=0)) {

                if((!TextUtils.isEmpty(jobtitle)) && (!TextUtils.isEmpty(fname)) && (!TextUtils.isEmpty(mobile)) && (!TextUtils.isEmpty(email)) && (!TextUtils.isEmpty(address))  && (!TextUtils.isEmpty(desp)) && (!TextUtils.isEmpty(prjrates)) && (!TextUtils.isEmpty(amail)) && (!TextUtils.isEmpty(start_date)) && (!TextUtils.isEmpty(end_date)) && (!TextUtils.isEmpty(last_date)) )  {
                RecPost(userId,token, jobtitle, fname, mobile, email, st, ci, cat, newcat,subcat, pskills, secskills, exp, address, prjrates, prjperiod,loc,desp,start_date,end_date,last_date,amail);
                }
                else
                {
                    Toast toast = Toast.makeText(getApplicationContext(),"Fill the required Fields",Toast.LENGTH_SHORT);
                    toast.show();
                }

            }

        });

    }

    public void  RecPost(String userId,String token,String jtitle, String name, String mobile, String email, int state, String city,String cat, String newcat, String subcat, String skills, String sskills, String exp, String address, String prjrates, String prjperiod, int work_type, String desp,String start_date,String end_date,String last_date,String appemail) {
        mAPIService.RecPostad(userId,token,name,email,mobile,jtitle,state,city,cat,newcat,subcat,skills,sskills,exp,address,prjrates,prjperiod,work_type,desp,start_date,end_date,last_date,appemail).enqueue(new Callback<RecPostAd>() {
            @Override
            public void onResponse(Call<RecPostAd> call, Response<RecPostAd> response) {

                if(response.isSuccessful()) {

                    Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_SHORT).show();

                    /*String c =  response.body().getCode();
                    Toast.makeText(getApplicationContext(),c,Toast.LENGTH_SHORT).show();

                    Intent myIntent = getIntent();
                    final String Id = myIntent.getStringExtra("Id");
                    final String token = myIntent.getStringExtra("token");
                    Intent su = new Intent(RPostAd.this, Rsuccess.class);
                    su.putExtra("Id",Id);
                    su.putExtra("token",token);
                    startActivity(su);*/
                }
            }

            @Override
            public void onFailure(Call<RecPostAd> call, Throwable t) {
                //Log.e(TAG, "Unable to submit post to API.");

                Toast tst = Toast.makeText(getApplicationContext(),"Server Error Retry registering",Toast.LENGTH_SHORT);
                tst.show();


            }
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        // TODO Auto-generated method stub
      /*  String sp1 = String.valueOf(state.getSelectedItemId());
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
            new RPostAd.SendJsonDataToServer().execute(String.valueOf(post_dict));
        }
        //  if (sp1 != "0") {


        //  }*/

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
                loadCityList(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            /*try {
                loadIntoListView(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }*/
        }

    }
    private void loadCityList(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);

        String[] citylist = new String[jsonArray.length() + 1];
        citylist[0] = "Select City"; //item at pos 0
        int j = 1;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            citylist[j++] = obj.getString("city_name");
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, citylist);
        city.setAdapter(arrayAdapter);
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

        String[] recresults = new String[jsonArray.length() + 1];
        recresults[0] = "Select State"; //item at pos 0
        int j = 1;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            recresults[j++] = obj.getString("name");
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, recresults);
        state.setAdapter(arrayAdapter);

        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {


// TODO Auto-generated method stub
                String sp1 = String.valueOf(state.getSelectedItemId());
                // int sp1 = statename.getSelectedItemPosition();
                Toast.makeText(getApplicationContext(), sp1, Toast.LENGTH_SHORT).show();
                JSONObject post_dict = new JSONObject();

                try {
                    post_dict.put("stateid", sp1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                if (!sp1.equals("0")) {
                    //do something
                    new RPostAd.SendJsonDataToServer().execute(String.valueOf(post_dict));
                } else {
                    Toast.makeText(getApplicationContext(), "Select State", Toast.LENGTH_SHORT).show();
                }


            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
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

        categry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                String cat_name =  categry.getItemAtPosition(position).toString();

                Toast.makeText(RPostAd.this, cat_name, Toast.LENGTH_LONG).show();

                if(cat_name.equals("Others"))
                {
                    Toast.makeText(RPostAd.this, "Inside Others", Toast.LENGTH_LONG).show();

                    new_cat.setHint("Enter New Category");
                    new_cat.setVisibility(View.VISIBLE);

                }
                else
                {
                    new_cat.setHint("");
                    new_cat.setVisibility(View.INVISIBLE);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

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
                        Intent home = new Intent(RPostAd.this, RHome.class);
                        home.putExtra("Id",Id);
                        startActivity(home);
                        return true;

                    case R.id.postad:
                        Intent post = new Intent(RPostAd.this, RPostAd.class);
                        post.putExtra("Id",Id);
                        post.putExtra("token",Token);
                        startActivity(post);
                        return true;

                    case R.id.mjobs:
                        Intent manage = new Intent(RPostAd.this, ManageRJobs.class);
                        manage.putExtra("Id",Id);
                        manage.putExtra("token",Token);
                        startActivity(manage);
                        return true;

                    case R.id.Rdash:
                        Intent rfdash = new Intent(RPostAd.this, R_Dash.class);
                        rfdash.putExtra("Id",Id);
                        rfdash.putExtra("token",Token);
                        startActivity(rfdash);
                        return true;

                    case R.id.Rfdash:
                        Intent rdash = new Intent(RPostAd.this, RFDash.class);
                        rdash.putExtra("Id",Id);
                        rdash.putExtra("token",Token);
                        startActivity(rdash);
                        return true;

                    case R.id.recsetting:
                        Intent pset = new Intent(RPostAd.this, R_PSettings.class);
                        pset.putExtra("Id",Id);
                        startActivity(pset);
                        return true;

                    case R.id.fsearch:
                        Intent search = new Intent(RPostAd.this, FSearch.class);
                        search.putExtra("Id",Id);
                        startActivity(search);
                        return true;

                    case R.id.rlogout:

                        LAPIService = ApiUtil.getAPI_Rlogout();

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

        Call<TokenJsonObject> logOut = LAPIService.logOutUser(((Globals) this.getApplication()).getToken(),userId);
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

        Intent intent = new Intent(RPostAd.this, RLogin.class);
        finish();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        overridePendingTransition(0, 0);
        startActivity(intent);
    }


    private void validate_date()
    {

        String start_date = select_sDate.getText().toString();
        String end_date = select_eDate.getText().toString();

        if(end_date.compareTo(start_date)>0)
        {
            Toast.makeText(getApplicationContext(),"End date is before start date",Toast.LENGTH_SHORT).show();
        }
    }




}
