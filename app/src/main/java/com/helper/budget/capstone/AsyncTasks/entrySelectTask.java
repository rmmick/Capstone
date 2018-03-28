package com.helper.budget.capstone.AsyncTasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.helper.budget.capstone.Entry;
import com.helper.budget.capstone.Main_Activity;
import com.helper.budget.capstone.entryDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bryan on 3/26/2018.
 */

public class entrySelectTask extends AsyncTask<String,Void,String>{
    private Main_Activity main;
    private RequestQueue queue;
    private String fromWeb;
    private final String ServerURL = "http://default-environment.rfemrggswx.us-west-2.elasticbeanstalk.com/";
    private ProgressDialog myProgressDialog;
    private entryDatabase eDB;

    public entrySelectTask(Main_Activity activity, entryDatabase e) {
        attach(activity);
        eDB = e;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog_start();
    }
    private void progressDialog_start() {
        myProgressDialog = new ProgressDialog(main);
        myProgressDialog.setTitle("Please wait");
        myProgressDialog.setMessage("Attempting to select data...");
        myProgressDialog.setCancelable(false);
        myProgressDialog.show();
    }

    private void progressDialog_stop(){
        if(myProgressDialog != null) {
            myProgressDialog.dismiss();
        }
    }

    private void storeResponse(String response){
        if(response.charAt(0) != '['){
            Toast.makeText(main, response, Toast.LENGTH_SHORT).show();
            progressDialog_stop();
            return;
        }try {
            JSONArray jA = new JSONArray(response);
            JSONObject jO;
            Entry e;
            for(int i = 0; i<jA.length();i++){
                jO = jA.getJSONObject(i);
                String placeSpent = jO.getString("placeSpent");
                String category = jO.getString("category_Name");
                String date = jO.getString("dateEntered");
                String description = jO.getString("description");
                String cost = jO.getString("cost");
                Double amount = Double.parseDouble(cost);
                String[] dates = date.split("-");
                if(dates.length == 3){
                    e = new Entry(placeSpent,description,amount, dates[1]+"/"+dates[2]+"/"+dates[0], category);
                    e.useDate();
                    eDB.addEntry(e);
                }
            }
            main.loadRecyclerView();
            progressDialog_stop();
            return;
        }catch(Exception e){
            Toast.makeText(main, "error parsing data", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        Log.d("MAIN TASK", response);
        fromWeb = response;
        Log.d("MAIN TASK", fromWeb);
        progressDialog_stop();
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
            final String NameHolder;
            try {
                NameHolder = strings[0];
            } catch (Exception e) {
                return "Not Enough Information Given";
            }
            StringRequest postRequest = new StringRequest(Request.Method.POST, ServerURL + "selectEntry.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // response
                            storeResponse(response);
                            Log.d("Response", response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            storeResponse(error.toString()+" From Entry");
                            Log.d("Error.Response", error.toString());
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username", NameHolder);
                    return params;
                }
            };
            if (main != null) {
                queue = Volley.newRequestQueue(main);
                queue.add(postRequest);
                return fromWeb+"From Entry";
            }
        return "Problem Deleting Data";
    }
}
