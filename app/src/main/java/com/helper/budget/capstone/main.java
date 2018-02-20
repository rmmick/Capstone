package com.helper.budget.capstone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class main extends AppCompatActivity {

    String url = "";

    List<Entry> mEntries = new ArrayList<>();
    Spinner spinner;
    private RecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loadSpinner();
        addDataTester();
        loadRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    protected void loadRecyclerView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new RecyclerAdapter(mEntries, url);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void loadSpinner() {

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(null);

        ArrayList<String> s = new ArrayList<>();
        s.add("Cost");
        s.add("Entry Name");
        s.add("Date");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, s);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setEnabled(true);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (mEntries != null) {
                    //mAdapter.sortList(spinner.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void addDataTester(){

        Entry e = new Entry("Wal-Mart", "Bought some stuff", 35.00, "2/11/2018");
        mEntries.add(e);
        e = new Entry("Target", "Bought some stuff", 105.00, "2/10/2018");
        mEntries.add(e);
        e = new Entry("Five Below", "Bought some stuff", 5.30, "2/07/2018");
        mEntries.add(e);
        e = new Entry("ScrubLand", "Bought some stuff", 10000.00, "2/20/2018");
        mEntries.add(e);
        e = new Entry("BryanOrBill", "Bought some stuff", 69.00, "2/14/2018");
        mEntries.add(e);
        e = new Entry("Wal-Mart", "Bought some stuff", 35.00, "2/11/2018");
        mEntries.add(e);
        e = new Entry("Target", "Bought some stuff", 105.00, "2/10/2018");
        mEntries.add(e);
        e = new Entry("Five Below", "Bought some stuff", 5.30, "2/07/2018");
        mEntries.add(e);
        e = new Entry("ScrubLand", "Bought some stuff", 10000.00, "2/20/2018");
        mEntries.add(e);
        e = new Entry("BryanOrBill", "Bought some stuff", 69.00, "2/14/2018");
        mEntries.add(e);
        e = new Entry("Wal-Mart", "Bought some stuff", 35.00, "2/11/2018");
        mEntries.add(e);
        e = new Entry("Target", "Bought some stuff", 105.00, "2/10/2018");
        mEntries.add(e);
        e = new Entry("Five Below", "Bought some stuff", 5.30, "2/07/2018");
        mEntries.add(e);
        e = new Entry("ScrubLand", "Bought some stuff", 10000.00, "2/20/2018");
        mEntries.add(e);
        e = new Entry("BryanOrBill", "Bought some stuff", 69.00, "2/14/2018");
        mEntries.add(e);
        e = new Entry("Wal-Mart", "Bought some stuff", 35.00, "2/11/2018");
        mEntries.add(e);
        e = new Entry("Target", "Bought some stuff", 105.00, "2/10/2018");
        mEntries.add(e);
        e = new Entry("Five Below", "Bought some stuff", 5.30, "2/07/2018");
        mEntries.add(e);
        e = new Entry("ScrubLand", "Bought some stuff", 10000.00, "2/20/2018");
        mEntries.add(e);
        e = new Entry("BryanOrBill", "Bought some stuff", 69.00, "2/14/2018");
        mEntries.add(e);

    }

}
