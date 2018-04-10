package com.helper.budget.capstone;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rachel on 2/22/2018.
 */

public class entryDatabase {

    String username;

    private double [] totals;
    private double [] counts;
    private double [] averages;
    private double [] budgets;

    private List<Entry> mEntries = new ArrayList<>();

    public entryDatabase(){
        budgets = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
    }

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

    public double[] getTotalsForMonth(int month, int year){
        double [] monthTotals = new double[]{0.0,0.0,0.0,0.0,0.0,0.0};
        String m;
        if(month<10){
            m = "0" + Integer.toString(month);
        }else{
            m = Integer.toString(month);
        }
        String y = Integer.toString(year);
        for(int i = 0; i < mEntries.size(); i++) {
            switch (mEntries.get(i).Category) {
                case("Home"):
                    if (mEntries.get(i).month.equals(m) && mEntries.get(i).year.equals(y)) {
                        monthTotals[2]+= mEntries.get(i).Cost;
                    }
                case "Vehicle":
                    if (mEntries.get(i).month.equals(m) && mEntries.get(i).year.equals(y)) {
                        monthTotals[0]+= mEntries.get(i).Cost;
                    }
                    break;
                case "Pets":
                    if (mEntries.get(i).month.equals(m) && mEntries.get(i).year.equals(y)) {
                        monthTotals[1]+= mEntries.get(i).Cost;
                    }
                    break;
                case "Food":
                    if (mEntries.get(i).month.equals(m) && mEntries.get(i).year.equals(y)) {
                        monthTotals[3]+= mEntries.get(i).Cost;
                    }
                    break;
                case "Entertainment":
                    if (mEntries.get(i).month.equals(m) && mEntries.get(i).year.equals(y)) {
                        monthTotals[4]+= mEntries.get(i).Cost;
                    }
                    break;
                case "Other":
                    if (mEntries.get(i).month.equals(m) && mEntries.get(i).year.equals(y)) {
                        monthTotals[5]+= mEntries.get(i).Cost;
                    }
                    break;
                default:
                    break;
            }
        }
        return monthTotals;
    }

    public void setBudgets(String cat, double budget){
        switch (cat){
            case "Vehicle":
                budgets[0] = budget;
                break;
            case "Pets":
                budgets[1] = budget;
                break;
            case "Home":
                budgets[2] = budget;
                break;
            case "Food":
                budgets[3] = budget;
                break;
            case "Entertainment":
                budgets[4] = budget;
                break;
            case "Other":
                budgets[5] = budget;
                break;
            default:
                break;
        }
    }

    public double[] getBudgets(){
        return budgets;
    }

    public String getUsername(){return username;}
}
