package com.helper.budget.capstone;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rachel on 2/22/2018.
 */

public class entryDatabase {

    String ServerURL = "";
    String username;

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

}
