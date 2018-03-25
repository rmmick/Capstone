package com.helper.budget.capstone;
import java.text.ParseException;
import java.util.Comparator;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Created by Rachel on 3/18/2018.
 */

class CompareCost implements Comparator<Entry> {

    @Override
    public int compare(Entry t1, Entry t2) {
       return (t1.Cost.compareTo(t2.Cost));
    }
}

class CompareName implements Comparator<Entry> {
    @Override
    public int compare(Entry t1, Entry t2) {
        return (t1.Name.compareTo(t2.Name));
    }
}

class CompareDate implements Comparator<Entry> {
    @Override
    public int compare(Entry t1, Entry t2)
    {
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = new SimpleDateFormat("MM/dd/yyyy").parse(t1.Date);
            date2 = new SimpleDateFormat("MM/dd/yyyy").parse(t2.Date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (date1.compareTo(date2));
    }
}
