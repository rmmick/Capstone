package com.helper.budget.capstone.AsyncTasks;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.helper.budget.capstone.Login;
import com.helper.budget.capstone.Main_Activity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bryan on 3/26/2018.
 */
public class userInsertTask extends AsyncTask<String, Void, String> {
    private Login main;
    private RequestQueue queue;
    private String fromWeb;
    private final String ServerURL = "http://default-environment.rfemrggswx.us-west-2.elasticbeanstalk.com/";
    private ProgressDialog myProgressDialog;
    private String password;

    public userInsertTask(Login activity) {
        attach(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog_start();
    }
    private void progressDialog_start() {
        myProgressDialog = new ProgressDialog(main);
        myProgressDialog.setTitle("Please wait");
        myProgressDialog.setMessage("Attempting to add user...");
        myProgressDialog.setCancelable(false);
        myProgressDialog.show();
    }

    private void progressDialog_stop(){
        if(myProgressDialog != null) {
            myProgressDialog.dismiss();
        }
    }

    private void storeResponse(String response){
        Log.d("MAIN TASK", response);
        fromWeb = response;
        //Toast.makeText(main, fromWeb, Toast.LENGTH_SHORT).show();
        Log.d("MAIN TASK", fromWeb);
        if(response.equals("Error Inserting Data")){
            Toast.makeText(main, response, Toast.LENGTH_SHORT).show();
            progressDialog_stop();
            return;
        }
        else{
            Toast.makeText(main, "User added successfully", Toast.LENGTH_SHORT).show();
            progressDialog_stop();
            return;
        }
    }
    void detach() {
        main = null;
    }

    void attach(Login activity) {
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
            password = strings[1];
        } catch (Exception e) {
            return "Not Enough Information Given";
        }
        StringRequest postRequest = new StringRequest(Request.Method.POST, ServerURL + "insertUser.php",
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
                        storeResponse(error.toString()+" From User");
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", NameHolder);
                params.put("password", password);
                return params;
            }
        };
        if (main != null) {
            queue = Volley.newRequestQueue(main);
            queue.add(postRequest);
            return fromWeb+" From User";
        }

        return "Problem Deleting Data";
    }
}