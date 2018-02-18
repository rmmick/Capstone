package com.helper.budget.capstone;

/**
 * Created by Rachel on 2/18/2018.
 */

public class Entry {
    public final String NAME;
    public final String  DESCRIPTION;
    public final Double  COST;
    public final String  DATE;


    @Override
    public String toString() {

        String info =
                "Date: " + DATE + "\n"
                        + "Name: " + NAME + "\n"
                        + "Description: " + DESCRIPTION + "\n"
                        + "Cost: " + COST + "\n";
        return info;
    }

    private Entry(Builder b) {
        NAME = b.Name;
        COST = b.Cost;
        DESCRIPTION = b.Description;
        DATE = b.Date;
    }

    public static class Builder {
        String Name;
        Double Cost;
        String Description;
        String Date;

        // Model and price required
        Builder(String Name, Double Cost, String Description, String Date) {
            this.Name = Name;
            this.Cost = Cost;
            this.Description = Description;
            this.Date = Date;
        }

        Builder setName(String Name) {
            this.Name = Name;
            return this;
        }

        Builder setCost(Double Cost) {
            this.Cost = Cost;
            return this;
        }

        Builder setDescription(String Description) {
            this.Description = Description;
            return this;
        }

        Builder setDate(String Date) {
            this.Date = Date;
            return this;
        }

        public Entry build() {
            return new Entry(this);
        }
    }
}