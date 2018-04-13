package com.helper.budget.capstone;

/**
 * Created by Rachel on 2/20/2018.
 */

public class Entry {

    //String username;
    String id;
    Double Cost;
    String Date; //mm/dd/yyyy
    String day, Category, month, year, Description, Name;

    @Override
    public String toString() {
        //creates the display for when an entry is selected from the recycler view
        String info = "Name: " + Name + "\n"
                + "Date: " + Date + "\n"
                + "Category: " + Category + "\n"
                + "Cost: " + Cost + "\n"
                + "Description: " + Description;
        return info;
    }

    /**
     * passes all the information that is retrieved from the database
     * @param n place spent
     * @param d description of purchase
     * @param c amount spent
     * @param date date spent
     * @param cat category of purchase
     */
    public Entry(String n, String d, Double c, String date, String cat){
        Name = n;
        Description = d;
        Cost = c;
        Date = date;
        Category = cat;
    }

    /*
     * sets the username of the entry
     * @param u username of the user
     *
    public void setUsername(String u){
        username = u;
    }*/

    /**
     * sets the date based on given values
     * @param d day of month
     * @param m month of year
     * @param y year
     */
    public void setDate(String d, String m, String y){

        Date = m + "/" + d + "/" + y;
    }

    /*public void setSeparateDate(String d, String m, String y){

        day = d;
        month = m;
        year = y;
    }*/

    /**
     * sets individual month, day, and year based on the current date
     */
    public void useDate(){
        String[] dates = Date.split("/");
        day = dates[1];
        month = dates[0];
        year = dates[2];
    }
}
