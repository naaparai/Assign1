package com.dogpo.assign1;

/**
 * Created by Netforce on 8/27/2016.
 */
public class ExpandChildData {
    public String word;
    public int occurance;

    public ExpandChildData(String word, int occurance) {
        this.occurance = occurance;
        this.word = word;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ExpandChildData)) {
            return false;
        }

        ExpandChildData that = (ExpandChildData) obj;
        return this.word.equals(that.word);
    }
}
