package com.helper.budget.capstone.AsyncTasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.helper.budget.capstone.AsyncTasks.entrySelectTask;
import com.helper.budget.capstone.Entry;
import com.helper.budget.capstone.Main_Activity;
import com.helper.budget.capstone.R;
import com.helper.budget.capstone.entryDatabase;

import java.time.Month;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bryan on 3/27/2018.
 */

public class entryInsertTask extends AsyncTask<String, Void, String> {
    private Main_Activity main;
    private RequestQueue queue;
    private String fromWeb;
    private final String ServerURL = "http://default-environment.rfemrggswx.us-west-2.elasticbeanstalk.com/";
    private ProgressDialog myProgressDialog;
    private String NameHolder;
    private String CatHolder;
    private String YearHolder;
    private String MonthHolder;
    private String DayHolder;
    private String DescriptionHolder;
    private String AmountHolder;
    private String PlaceHolder;
    private entryDatabase mEntries;

    public entryInsertTask(Main_Activity activity, entryDatabase E) {
        attach(activity);
        mEntries = E;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog_start();
    }


    private void progressDialog_start() {
        myProgressDialog = new ProgressDialog(main);
        myProgressDialog.setTitle("Please wait");
        myProgressDialog.setMessage("Attempting to insert data...");
        myProgressDialog.setCancelable(false);
        myProgressDialog.show();
    }

    private void progressDialog_stop(){
        if(myProgressDialog != null) {
            myProgressDialog.dismiss();
        }
    }

    private void storeResponse(String response){
        if(response.equals("Data Inserted Successfully")){
            DescriptionHolder = DescriptionHolder.replaceAll("\\\\'", "'");
            PlaceHolder = PlaceHolder.replaceAll("\\\\'", "'");
            Entry e = new Entry(PlaceHolder,
                    DescriptionHolder,
                    Double.parseDouble(AmountHolder),
                    "",
                    CatHolder);
            if(Integer.parseInt(DayHolder) < 10){
                DayHolder = "0" + DayHolder.charAt(DayHolder.length()-1);
            }
            if(Integer.parseInt(MonthHolder) < 10){
                MonthHolder = "0" + MonthHolder.charAt(MonthHolder.length()-1);
            }
            e.setDate(DayHolder,MonthHolder,YearHolder);
            e.useDate();
            mEntries.addEntry(e);
            Button r = main.findViewById(R.id.mainRefresh);
            r.performClick();
            Toast.makeText(main, response, Toast.LENGTH_SHORT).show();
            progressDialog_stop();
            return;
        }
        Toast.makeText(main, response, Toast.LENGTH_SHORT).show();
        progressDialog_stop();
        Log.d("MAIN TASK", response);
        fromWeb = response;
        Toast.makeText(main, fromWeb, Toast.LENGTH_SHORT).show();
        Log.d("MAIN TASK", fromWeb);
    }
    void detach() {
        main = null;
    }

    void attach(Main_Activity activity) {
        main = activity;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected String doInBackground(String... strings) {
        fromWeb = "";

            try {
                NameHolder = strings[0];
                CatHolder = strings[1];
                YearHolder = strings[2];
                MonthHolder = strings[3];
                DayHolder = strings[4];
                DescriptionHolder = strings[5];
                AmountHolder = strings[6];
                PlaceHolder = strings[7];
            } catch (Exception e) {
                return "Not Enough Information Given";
            }

            StringRequest postRequest = new StringRequest(Request.Method.POST, ServerURL + "insertEntry.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // response
                            storeResponse(response);
                            Log.d("Response", response);
                            detach();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            storeResponse(error.toString()+" For Entry");
                            Log.d("Error.Response", error.toString());
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username", NameHolder);
                    params.put("category", CatHolder);
                    params.put("year", YearHolder);
                    params.put("month", MonthHolder);
                    params.put("day", DayHolder);
                    params.put("description", DescriptionHolder);
                    params.put("amount", AmountHolder);
                    params.put("place", PlaceHolder);
                    return params;
                }
            };
            if (main != null) {
                queue = Volley.newRequestQueue(main);
                queue.add(postRequest);
                return " Into Entry";
            }

        return "Problem Inserting Data";
    }
}
