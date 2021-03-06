package com.firebase.pass.passadmin;

/**
 * Created by 1405214 on 09-02-2017.
 */

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
    Context context;
    // int flags[];
    String[] countryNames;
    LayoutInflater inflter;
    public static String PLACE_NAME="NO PLACE SELECTED";

    public CustomAdapter(Context applicationContext, String[] countryNames) {
        this.context = applicationContext;
        this.countryNames = countryNames;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return countryNames.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.test, null);
        TextView names = (TextView) view.findViewById(R.id.textView);
        if(i==0)
        {
            names.setText("Tap to select");
            names.setTextColor(Color.GRAY);
            PLACE_NAME="NO PLACE SELECTED";
        }
        else
        {

            names.setText(countryNames[i]);
            names.setTextColor(Color.WHITE);
            PLACE_NAME=countryNames[i];
        }
        return view;
    }
}