package com.helper.budget.capstone.AsyncTasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.helper.budget.capstone.Main_Activity;
import com.helper.budget.capstone.entryDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bryan on 4/3/2018.
 */

public class budgetSelectTask extends AsyncTask<String, Void, String> {
    private final String ENTRY = "entry";
    private final String BUDGET = "budget";
    private final String USER = "user";
    private Main_Activity main;
    private RequestQueue queue;
    private String fromWeb;
    private final String ServerURL = "http://default-environment.rfemrggswx.us-west-2.elasticbeanstalk.com/";
    private ProgressDialog myProgressDialog;
    private entryDatabase EDB;

    public budgetSelectTask(Main_Activity activity, entryDatabase e) {

        attach(activity);
        EDB = e;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog_start();
    }
    private void progressDialog_start() {
        myProgressDialog = new ProgressDialog(main);
        /*myProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //dialog.dismiss();
                        //this.cancel(true);

                        //myProgressDialog = null;
                    }
                });*/
        myProgressDialog.setTitle("Please wait");
        myProgressDialog.setMessage("Attempting to delete data...");
        myProgressDialog.setCancelable(false);
        myProgressDialog.show();
    }

    private void progressDialog_stop(){
        if(myProgressDialog != null) {
            myProgressDialog.dismiss();
        }
    }

    void storeResponse(String response){
        if(response.charAt(0) != '['){
            Toast.makeText(main, response, Toast.LENGTH_SHORT).show();
            progressDialog_stop();
            return;
        }try {
            JSONArray jA = new JSONArray(response);
            JSONObject jO;
            for(int i = 0; i<jA.length();i++){
                jO = jA.getJSONObject(i);
                String cost = jO.getString("maxValue");
                String category = jO.getString("category");
                Double amount = Double.parseDouble(cost);
                EDB.setBudgets(category, amount);
            }
            progressDialog_stop();
            return;
        }catch(Exception e){
            Toast.makeText(main, "error parsing data", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        progressDialog_stop();
        Log.d("MAIN TASK", response);
        fromWeb = response;
        //Toast.makeText(main, fromWeb, Toast.LENGTH_SHORT).show();
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
        /*if (main != null) {
            Toast.makeText(main, s, Toast.LENGTH_SHORT).show();
        }
        detach();*/
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected String doInBackground(String... strings) {
        final String NameHolder;
        try {
            NameHolder = strings[0];
        }  catch (Exception e) {
            return "Not Enough Information Given";
        }
        StringRequest postRequest = new StringRequest(Request.Method.POST, ServerURL + "selectBudget.php",
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
                        error.printStackTrace();
                        storeResponse(error.toString()+" From Budget");
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
        }
        return "Problem Deleted Data";
    }
}
