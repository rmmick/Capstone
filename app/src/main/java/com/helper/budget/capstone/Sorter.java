package com.helper.budget.capstone;
import java.util.Comparator;
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
    public int compare(Entry t1, Entry t2) {
        return (t1.Date.compareTo(t2.Date));
    }
}
