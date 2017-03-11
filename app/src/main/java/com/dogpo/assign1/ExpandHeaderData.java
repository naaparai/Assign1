package com.dogpo.assign1;

/**
 * Created by Netforce on 8/27/2016.
 */
public class ExpandHeaderData {
    public String occurrance;
    public int id;

    public ExpandHeaderData(int id, String occurrance) {
        this.id = id;
        this.occurrance = occurrance;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ExpandHeaderData)) {
            return false;
        }

        ExpandHeaderData that = (ExpandHeaderData) obj;
        return this.id==that.id;
    }
}
