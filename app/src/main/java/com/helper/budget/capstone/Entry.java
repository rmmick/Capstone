package com.helper.budget.capstone;

/**
 * Created by Rachel on 2/20/2018.
 */

public class Entry {

    String Name;
    String Description;
    Double Cost;
    String Date;
    String Category;

    @Override
    public String toString() {
        String info = "Name: " + Name + "\n"
                + "Date: " + Date + "\n"
                + "Category: " + Category + "\n"
                + "Cost: " + Cost + "\n"
                + "Description: " + Description;
        return info;
    }

    public Entry(String n, String d, Double c, String date, String cat){
        Name = n;
        Description = d;
        Cost = c;
        Date = date;
        Category = cat;
    }
}
