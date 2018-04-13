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

    /**
     * initializes the user's budgets array
     */
    public entryDatabase(){
        budgets = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
    }

    /**
     * adds an entry to the list for the recycler view
     * @param e entry to be added
     */
    public void addEntry(Entry e){
        mEntries.add(e);
    }

    /**
     * returns the list of entries currently used by the recycler view
     * @return list of entries
     */
    public List<Entry> getEntriesList(){
        return mEntries;
    }

    /**
     * sets the username of the current user logged in
     * @param u user logged in
     */
    public void setUsername(String u){
        username = u;
    }

    /**
     * calculates the average of all of the entries in the database
     * this value is used for the system generated budget
     */
    public void calcSysBudget(){
        //initializes the arrays, with the indexs representing categories
        //0 = vehicle, 1 = pets, 2 = home, 3 = food, 4 = entertainment, 5 = other
        totals = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        counts = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        averages = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};

        //for all of the entries, increase the count and total of the appropriate category
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
        //then calcs average for each category and stores them in the average, following the same pattern
        for(int j = 0; j < 6; j++){
            if(counts[j] != 0)
                averages[j] = totals[j] / counts[j];
        }
    }

    /**
     * returns the current averages of the entries
     * @return averages of the entries
     */
    public double[] getAverages(){
        return averages;
    }

    /*public double[] getTotals(){
        return totals;
    }*/

    /**
     * gets the totals for the current date given
     * @param month month of year
     * @param year year
     * @return totals of each entry for the date
     */
    public double[] getTotalsForMonth(int month, int year){
        //creates the array to return with the indexs
        //0 = vehicle, 1 = pets, 2 = home, 3 = food, 4 = entertainment, 5 = other
        double [] monthTotals = new double[]{0.0,0.0,0.0,0.0,0.0,0.0};
        String m;
        //adjust the month to match the syntax of the entries in the list
        if(month<10){
            m = "0" + Integer.toString(month);
        }else{
            m = Integer.toString(month);
        }
        //convert the year to a string
        String y = Integer.toString(year);

        //for each entry, add to the totals if the month and year match
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

        //return the results
        return monthTotals;
    }

    /**
     * sets the budget for the category give
     * @param cat category of the budget
     * @param budget amount of budget
     */
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

    /**
     * returns the current budgets for the user logged in
     * @return current budgets
     */
    public double[] getBudgets(){
        return budgets;
    }

    /**
     * returns the current user that is logged in
     * @return user logged in
     */
    public String getUsername(){return username;}
}
