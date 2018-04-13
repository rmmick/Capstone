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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bryan on 4/4/2018.
 */

public class budgetDeleteTask extends AsyncTask<String, Void, String> {
    private Main_Activity main;
    private RequestQueue queue;
    private final String ServerURL = "http://default-environment.rfemrggswx.us-west-2.elasticbeanstalk.com/";
    private ProgressDialog myProgressDialog;
    private entryDatabase EDB;
    private double newAmount;
    private String userGen;
    private String NameHolder;
    private String CatHolder;

    /**
     * attach the activity for context and database to alter
     *
     * @param activity the context of the task
     * @param e local database to update
     */
    public budgetDeleteTask(Main_Activity activity, entryDatabase e) {
        attach(activity);
        EDB = e;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //start a progress dialog to stop to user from interacting with the UI
        progressDialog_start();
    }

    /**
     * Starts the  progress dialog
     */
    private void progressDialog_start() {
        myProgressDialog = new ProgressDialog(main);
        myProgressDialog.setTitle("Please wait");
        myProgressDialog.setMessage("Attempting to delete data...");
        myProgressDialog.setCancelable(false);
        myProgressDialog.show();
    }

    /**
     * dismisses the dialog
     */
    private void progressDialog_stop(){
        if(myProgressDialog != null) {
            myProgressDialog.dismiss();
        }
    }

    /**
     * processes the response received from the webserver
     *
     * @param response message received from the webserver
     */
    void storeResponse(String response){
        //if the budget was deleted, update the local budget
        if(response.equals("Data Deleted Successfully")){
            budgetInsertTask task = new budgetInsertTask(main,EDB);
            EDB.setBudgets(CatHolder, 0.0);
            //then execute an insert task to update the new budget the new value isn't 0
            if(newAmount!=0.0){
                task.execute(NameHolder,
                        CatHolder,
                        Integer.toString(Calendar.getInstance().get(Calendar.YEAR)),
                        Integer.toString(Calendar.getInstance().get(Calendar.MONTH) + 1),
                        Integer.toString(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)),
                        userGen,
                        Double.toString(newAmount)
                );
            }

        }else{
            //otherwise report the error
            Toast.makeText(main, response, Toast.LENGTH_SHORT).show();
        }
        //stop the progress dialog to allow UI interaction
        progressDialog_stop();
    }

    /**
     * sets the main activity context
     *
     * @param activity activity context given
     */
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
        try {//try to get the require values
            NameHolder = strings[0];
            CatHolder = strings[1];
            newAmount = Double.parseDouble(strings[2]);
            userGen = strings[3];
        }  catch (Exception e) {
            return "Not Enough Information Given";
        }
        //create a new web request for deleting the budget
        StringRequest postRequest = new StringRequest(Request.Method.POST, ServerURL + "deleteBudget.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //on response from webserver, handle it
                        storeResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //if error, inform the user
                        storeResponse("Problem accessing The web server");
                    }
                }

        ) {
            @Override
            protected Map<String, String> getParams() {
                //send the parameters to the webserver for the SQL
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", NameHolder);
                params.put("category", CatHolder);
                return params;
            }
        };
        //add the request to the queue to be picked up by the next network thread
        if (main != null) {
            queue = Volley.newRequestQueue(main);
            queue.add(postRequest);
        }
        return "Success";
    }
}
