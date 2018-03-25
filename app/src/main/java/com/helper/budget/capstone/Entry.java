package com.helper.budget.capstone;

/**
 * Created by Rachel on 2/20/2018.
 */

public class Entry {

    String username;
    String password;

    Double Cost;
    String Date;
    String day, Category, month, year, Description, Name;

    @Override
    public String toString() {
        String info = "Name: " + Name + "\n"
                + "Date: " + day + "\n"
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
