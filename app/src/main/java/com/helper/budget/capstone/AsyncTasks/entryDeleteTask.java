package com.helper.budget.capstone.AsyncTasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.helper.budget.capstone.Main_Activity;
import com.helper.budget.capstone.R;
import com.helper.budget.capstone.RecyclerAdapter;
import com.helper.budget.capstone.entryDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bryan on 3/27/2018.
 */

public class entryDeleteTask extends AsyncTask<String, Void, String> {
    private final String ENTRY = "entry";
    private final String BUDGET = "budget";
    private final String USER = "user";
    private AppCompatActivity main;
    private RequestQueue queue;
    private String fromWeb;
    private final String ServerURL = "http://default-environment.rfemrggswx.us-west-2.elasticbeanstalk.com/";
    private ProgressDialog myProgressDialog;
    private entryDatabase EDB;
    private int pos;
    RecyclerAdapter recyclerAdapter;

    public entryDeleteTask(Main_Activity activity, entryDatabase e, int p, RecyclerAdapter recyclerAdapter) {

        attach(activity);
        EDB = e;
        pos = p;
        this.recyclerAdapter = recyclerAdapter;
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
            final String CatHolder;
            final String YearHolder;
            final String MonthHolder;
            final String DayHolder;
            final String DescriptionHolder;
            final String AmountHolder;
            final String PlaceHolder;
            try {
                NameHolder = strings[0];
                CatHolder = strings[1];
                YearHolder = strings[2];
                MonthHolder = strings[3];
                DayHolder = strings[4];
                DescriptionHolder = strings[5];
                AmountHolder = strings[6];
                PlaceHolder = strings[7];
                Log.d("Username", NameHolder);
                Log.d("Category", CatHolder);
                Log.d("Year", YearHolder);
                Log.d("Month", MonthHolder);
                Log.d("Day", DayHolder);
                Log.d("Description", DescriptionHolder);
                Log.d("Amount", AmountHolder);
                Log.d("Place", PlaceHolder);
            }  catch (Exception e) {
                return "Not Enough Information Given";
            }
            StringRequest postRequest = new StringRequest(Request.Method.POST, ServerURL + "deleteEntry.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // response
                            storeResponse(response+" From Entry");
                            Log.d("Response", response);
                            if(response.equals("Data Deleted Successfully")){
                                EDB.getEntriesList().remove(pos);
//                                RecyclerView rv = main.findViewById(R.id.recyclerView);
//                                RecyclerAdapter ra = (RecyclerAdapter) rv.getAdapter();
//                                ra.notifyItemRemoved(pos);
//                                ra.notifyItemRangeChanged(pos, EDB.getEntriesList().size());
                                Main_Activity m = (Main_Activity) main;
                                m.getAdapter().notifyDeletion(pos);
                                Button r = main.findViewById(R.id.mainRefresh);
                                r.performClick();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            error.printStackTrace();
                            storeResponse(error.toString()+" From Entry");
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
            }
        return "Problem Deleted Data";
    }
}
