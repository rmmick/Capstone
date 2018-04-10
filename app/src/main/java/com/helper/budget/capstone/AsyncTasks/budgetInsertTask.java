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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bryan on 4/4/2018.
 */

public class budgetInsertTask extends AsyncTask<String, Void, String> {
    private final String ENTRY = "entry";
    private final String BUDGET = "budget";
    private final String USER = "user";
    private Main_Activity main;
    private RequestQueue queue;
    private String fromWeb;
    private final String ServerURL = "http://default-environment.rfemrggswx.us-west-2.elasticbeanstalk.com/";
    private ProgressDialog myProgressDialog;
    private entryDatabase EDB;
    private double newAmount;
    private String CatHolder;

    public budgetInsertTask(Main_Activity activity, entryDatabase e) {

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
        if(response.equals("Data Inserted Successfully")){
            EDB.setBudgets(CatHolder,newAmount);
        }else{
            Toast.makeText(main, response, Toast.LENGTH_SHORT).show();
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
        final String YearHolder;
        final String DayHolder;
        final String MonthHolder;
        final String AmountHolder;
        final String userGenHolder;
        try {
            NameHolder = strings[0];
            CatHolder = strings[1];
            YearHolder = strings[2];
            MonthHolder = strings[3];
            DayHolder = strings[4];
            userGenHolder = strings[5];
            AmountHolder = strings[6];
            Log.d("Response", NameHolder);
            Log.d("Response", CatHolder);
            Log.d("Response", YearHolder);
            Log.d("Response", MonthHolder);
            Log.d("Response", DayHolder);
            Log.d("Response", userGenHolder);
            Log.d("Response", AmountHolder);


            newAmount = Double.parseDouble(AmountHolder);
        }  catch (Exception e) {
            return "Not Enough Information Given";
        }
        StringRequest postRequest = new StringRequest(Request.Method.POST, ServerURL + "insertBudget.php",
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
                params.put("category", CatHolder);
                params.put("year", YearHolder);
                params.put("month", MonthHolder);
                params.put("day", DayHolder);
                params.put("userGenerated", userGenHolder);
                params.put("amount", AmountHolder);
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
