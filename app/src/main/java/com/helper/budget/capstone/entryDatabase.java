package com.helper.budget.capstone;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rachel on 2/22/2018.
 */

public class entryDatabase {

    String ServerURL = "";
    String username;

    private double [] totals;
    private double [] counts;
    private double [] averages;

    private List<Entry> mEntries = new ArrayList<>();

    public entryDatabase(){}

    public void addEntry(Entry e){

        mEntries.add(e);
    }

    public List<Entry> getEntriesList(){
        return mEntries;
    }

    public void setUsername(String u){
        username = u;
    }

    public void calcSysBudget(){

        totals = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        counts = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        averages = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};

        for(int i = 0; i < mEntries.size(); i++){

            switch(mEntries.get(i).Category){
                case "Vehicle":
                    totals[0] += mEntries.get(i).Cost;
                    counts[0]++;
                    break;
                case "Pets":
                    totals[1] += mEntries.get(i).Cost;
                    counts[1]++;
                    break;
                case "Home":
                    totals[2] += mEntries.get(i).Cost;
                    counts[2]++;
                    break;
                case "Food":
                    totals[3] += mEntries.get(i).Cost;
                    counts[3]++;
                    break;
                case "Entertainment":
                    totals[4] += mEntries.get(i).Cost;
                    counts[4]++;
                    break;
                case "Other":
                    totals[5] += mEntries.get(i).Cost;
                    counts[5]++;
                    break;
            }
        }

        for(int j = 0; j < 6; j++){
            if(counts[j] != 0)
                averages[j] = totals[j] / counts[j];
        }
    }

    public double[] getAverages(){
        return averages;
    }

    public double[] getTotals(){
        return totals;
    }

}
