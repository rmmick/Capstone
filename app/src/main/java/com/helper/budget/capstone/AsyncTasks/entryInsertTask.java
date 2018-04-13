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

    /**
     * attach the activity for context and database to alter
     *
     * @param activity the context of the task
     * @param E local database to update
     */
    public entryInsertTask(Main_Activity activity, entryDatabase E) {
        attach(activity);
        mEntries = E;
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
        myProgressDialog.setMessage("Attempting to insert data...");
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
    private void storeResponse(String response){
        //if successful, add the entry locally
        if(response.equals("Data Inserted Successfully")){
            //replaces the '\'' with ''' to revert it back from SQL syntax
            DescriptionHolder = DescriptionHolder.replaceAll("\\\\'", "'");
            PlaceHolder = PlaceHolder.replaceAll("\\\\'", "'");
            //create a new entry from the data
            Entry e = new Entry(PlaceHolder,
                    DescriptionHolder,
                    Double.parseDouble(AmountHolder),
                    "",
                    CatHolder);
            //add 0 to the end of the month and day for formatting
            if(Integer.parseInt(DayHolder) < 10){
                DayHolder = "0" + DayHolder.charAt(DayHolder.length()-1);
            }
            if(Integer.parseInt(MonthHolder) < 10){
                MonthHolder = "0" + MonthHolder.charAt(MonthHolder.length()-1);
            }

            //sets the date, both fully and month,day,year
            e.setDate(DayHolder,MonthHolder,YearHolder);
            e.useDate();

            //adds entry and refreshes the view
            mEntries.addEntry(e);
            Button r = main.findViewById(R.id.mainRefresh);
            r.performClick();

            //informs the user of the success, stops the dialog to allow interaction and returns to main
            Toast.makeText(main, response, Toast.LENGTH_SHORT).show();
            progressDialog_stop();
            return;
        }
        //otherwise reports an error
        Toast.makeText(main, response, Toast.LENGTH_SHORT).show();
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
                YearHolder = strings[2];
                MonthHolder = strings[3];
                DayHolder = strings[4];
                DescriptionHolder = strings[5];
                AmountHolder = strings[6];
                PlaceHolder = strings[7];
            } catch (Exception e) {
                return "Not Enough Information Given";
            }
            //create a new web request for inserting the new budget
            StringRequest postRequest = new StringRequest(Request.Method.POST, ServerURL + "insertEntry.php",
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
                            storeResponse("Problem accessing the web server");
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    //send the parameters to the webserver for the SQL
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
            //add the request to the queue to be picked up by the next network thread
            if (main != null) {
                queue = Volley.newRequestQueue(main);
                queue.add(postRequest);
            }

        return "Success";
    }
}
