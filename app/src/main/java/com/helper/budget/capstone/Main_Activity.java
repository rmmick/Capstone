package com.helper.budget.capstone;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.helper.budget.capstone.AsyncTasks.entrySelectTask;

import java.util.ArrayList;
import java.util.Collections;

public class Main_Activity extends AppCompatActivity {

    String url = "";

    entryDatabase mEntries;
    Spinner spinner;
    private Button r;
    private RecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mEntries = new entryDatabase();
        Intent intent = getIntent();
        mEntries.username = intent.getStringExtra("username");
        //Toast.makeText(this, user, Toast.LENGTH_SHORT).show();
        populateData();
        loadRecyclerView();
        setTitle("");

        r = (Button) findViewById(R.id.mainRefresh);
        r.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mRecyclerView.getAdapter().notifyDataSetChanged();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem item = menu.findItem(R.id.spinner2);
        final Spinner spinner = (Spinner) item.getActionView();

        ArrayList<String> s = new ArrayList<>();
        s.add("Name");
        s.add("Cost");
        s.add("Date");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, s);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setEnabled(true);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (mEntries != null) {
                    mAdapter.sortList(spinner.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.DIALOG_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data.getExtras().containsKey(Constants.ADD)) {
                    String myValue = data.getExtras().getString(Constants.ADD);

                    // Use the returned value
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent setIntent = new Intent(this, preferenceActivity.class);
                startActivity(setIntent);
                return true;

            case R.id.addEntry:
                //addDialog cdd=new addDialog(this, mEntries);
                //cdd.show();
                mAdapter.addEntry(this, mEntries);
            return true;

            case R.id.refresh:
                mRecyclerView.getAdapter().notifyDataSetChanged();
                return true;

            case R.id.createBudget:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("How would you like to generate a new budget?")
                        .setCancelable(true)
                        .setNegativeButton("System", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //TODO Have the System generate a new budget
                            }
                        })
                        .setPositiveButton("Manual", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //TODO Allow User to enter in their own values
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                return true;

            case R.id.viewBudget:

                budgetDialog bd = new budgetDialog(this, mEntries);

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(bd.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                bd.show();
                bd.getWindow().setAttributes(lp);
                bd.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void loadRecyclerView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new RecyclerAdapter(mEntries, url, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void populateData(){
        entrySelectTask task = new entrySelectTask(this,mEntries);
        task.execute(mEntries.username);
    }
}
