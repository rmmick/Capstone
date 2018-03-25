package com.helper.budget.capstone;

/**
 * Created by Rachel on 2/20/2018.
 */

public class Entry {

    String username;
    String id;
    Double Cost;
    String Date; //mm/dd/yyyy
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

    public void setUsername(String u){

        username = u;
    }

    public void setDate(String d, String m, String y){

        Date = d + "/" + m + "/" + y;
    }

    public void setSeparateDate(String d, String m, String y){

        day = d;
        month = m;
        year = y;
    }
}
