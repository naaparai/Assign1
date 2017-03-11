package com.dogpo.assign1;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.LoginFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements ExpandableListAdapter.clickListner {

    private static final int PICKFILE_RESULT_CODE = 101;
    TextView textView;
    Button button;
    ExpandableListView expandableListView;
    private File source;
    ExpandableListAdapter expandableListAdapter;
    Context context;
    ArrayList<ExpandHeaderData> expandHeaderDatas = new ArrayList<>();
    HashMap<ExpandHeaderData, List<TeamListData>> expandChildDatas = new HashMap<>();
    private HashMap<String, Integer> countByWords;
    private ArrayList<TeamListData> teamListDatas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPermission();
        context=this;
        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        setupExpandable();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
                chooseFile.setType("text/plain");
                startActivityForResult(
                        Intent.createChooser(chooseFile, "Choose a file"),
                        PICKFILE_RESULT_CODE
                );
            }
        });

    }

    private void setupExpandable() {
        //prepareListData();
        expandableListAdapter = new ExpandableListAdapter(context, expandHeaderDatas, expandChildDatas);
        // setting list adapter
        expandableListView.setAdapter(expandableListAdapter);
        expandableListAdapter.setClickListner(this);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICKFILE_RESULT_CODE && resultCode == Activity.RESULT_OK) {
            Uri content_describer = data.getData();
            String src = content_describer.getPath();
            source = new File(src);
            Log.d("src is ", source.toString());
            String filename = content_describer.getLastPathSegment();
            textView.setText(filename);
            Log.d("FileName is ", filename);
            countByWords = new HashMap<String, Integer>();
            Scanner s = null;
            try {
                s = new Scanner(source);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            while (s.hasNext()) {
                String next = s.next();
                Integer count = countByWords.get(next);
                if (count != null) {
                    countByWords.put(next, count + 1);
                } else {
                    countByWords.put(next, 1);
                }
            }
            s.close();
            Map.Entry<String, Integer> maxEntry = null;
            for (Map.Entry<String, Integer> entry : countByWords.entrySet()) {
                if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                    maxEntry = entry;
                }
            }
            Log.i("hashapmax", maxEntry.getValue() + " " + Math.round(maxEntry.getValue() / 10) * 10);
            int maxBount = Math.round(maxEntry.getValue() / 10) * 10 + 10;
            for (int i = 0; i < maxBount; i += 10) {
                int nextbount = i + 10;
                String bound = i + "-" + nextbount;
                Log.i("hashmapbound", bound);
                ExpandHeaderData competition = new ExpandHeaderData(nextbount, bound);
                if (!expandHeaderDatas.contains(competition)) {

                    expandHeaderDatas.add(competition);
                }
            }
            Iterator it = countByWords.entrySet().iterator();

            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                Log.i("hashmap", pair.getKey() + " = " + pair.getValue());
                teamListDatas.add(new TeamListData((String) pair.getKey(), (int) pair.getValue()));


            }
            Collections.sort(teamListDatas);
            setupExpandableData();

        }
    }

    private void setupExpandableData() {

        int counter = 0;
        ArrayList<TeamListData> expandChildData = new ArrayList<>();
        for (TeamListData teamData : teamListDatas) {
            if (teamData.occurance <=expandHeaderDatas.get(counter).id) {
                expandChildData.add(teamData);

            } else {
                expandChildDatas.put(expandHeaderDatas.get(counter), expandChildData);
                counter++;
                expandChildData = new ArrayList<>();
                expandChildData.add(teamData);


            }
        }
        expandChildDatas.put(expandHeaderDatas.get(counter), expandChildData);

        expandableListAdapter.notifyDataSetChanged();
    }

    private void getPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            String[] permission = {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE

            };

            ActivityCompat.requestPermissions(this,
                    permission, 1);


        }
    }

    @Override
    public void itemClicked(View view, int groupview, int childview) {

    }
}
