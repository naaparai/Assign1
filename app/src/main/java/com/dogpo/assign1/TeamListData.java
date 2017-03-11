package com.dogpo.assign1;

/**
 * Created by Gowtham Chandrasekar on 31-07-2015.
 */
public class TeamListData implements Comparable<TeamListData> {
    public String word;
    public int occurance;

    public TeamListData(String word, int occurance) {
        this.occurance = occurance;
        this.word=word;

    }



    @Override
    public int compareTo(TeamListData eventsData) {
        return Double.compare(occurance, eventsData.occurance);

    }
}
