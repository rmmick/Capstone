package com.helper.budget.capstone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

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
        loadRecyclerView();
        populateData();
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

            case R.id.budget:
                Intent mIntent = new Intent(this, budgetGenerator.class);
                startActivity(mIntent);

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    protected void loadRecyclerView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new RecyclerAdapter(mEntries, url, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void populateData(){

        mEntries.addEntry(new Entry("Hello", "Enter something descriptive into this box.", 33.15, "12/12/2016", "Household"));
        mEntries.addEntry(new Entry("world", "Enter something descriptive into this box.", 47.91, "1/16/2016", "Vehicle"));
        mEntries.addEntry(new Entry("Fuck", "Enter something descriptive into this box.", 85730.56, "4/11/2016", "Household"));

        Entry e = new Entry("Venture", "Rent", 600.98, "2/12/1202", "home");
        e.setSeparateDate("12", "2", "2018");
        e.setUsername("bryan");

        mEntries.addEntry(e);
    }
}
