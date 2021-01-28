package com.example.welcomeactivity;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
<<<<<<< HEAD
=======

import com.github.mikephil.charting.data.PieEntry;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
>>>>>>> d60ec35235921d6429ca6cab61b2affdfc82dfea

import com.github.mikephil.charting.data.PieEntry;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

<<<<<<< HEAD
public class HomeFragment extends Fragment {
=======
    public static final String SPENDINGS_HOME_FILE = "HOME_SPENDINGS_FILE2.txt";
    public static final String SPENDINGS_FOOD_FILE = "FOOD_SPENDINGS_FILE2.txt";
    public static final String SPENDINGS_MEDICAL_FILE = "MEDICAL_SPENDINGS_FILE2.txt";
    public static final String SPENDINGS_OTHERS_FILE = "OTHERS_SPENDINGS_FILE2.txt";
>>>>>>> d60ec35235921d6429ca6cab61b2affdfc82dfea

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
<<<<<<< HEAD
        int valoareaTotala = getTotalSpentMoney(SpendingsGeneralActivity.SPENDINGS_HOME_FILE);
        valoareaTotala += getTotalSpentMoney(SpendingsGeneralActivity.SPENDINGS_FOOD_FILE);
        valoareaTotala += getTotalSpentMoney(SpendingsGeneralActivity.SPENDINGS_MEDICAL_FILE);
        valoareaTotala += getTotalSpentMoney(SpendingsGeneralActivity.SPENDINGS_OTHERS_FILE);
        valoareaTotala += getGoalsMoney();
=======
        Context context = view.getContext();

        int valoareaTotala = getTotalSpentMoney(SPENDINGS_HOME_FILE , context);
        valoareaTotala += getTotalSpentMoney(SPENDINGS_FOOD_FILE , context);
        valoareaTotala += getTotalSpentMoney(SPENDINGS_MEDICAL_FILE , context);
        valoareaTotala += getTotalSpentMoney(SPENDINGS_OTHERS_FILE , context);
>>>>>>> d60ec35235921d6429ca6cab61b2affdfc82dfea
        TextView dateText= view.findViewById(R.id.ammountTotalMoney);
        dateText.setText(String.valueOf(valoareaTotala));

        TextView homeTextView = view.findViewById(R.id.homeTextView);
        homeTextView.setBackgroundResource(R.drawable.rounded_corners_title);
        GradientDrawable drawable = (GradientDrawable) homeTextView.getBackground();
        drawable.setColor(ContextCompat.getColor(getActivity(), R.color.transparent));

        return view;
    }

<<<<<<< HEAD
    private String readFromFile(String filename) {
        String ret = "";
        try {
            InputStream inputStream = getActivity().openFileInput(filename);
=======
    private String readFromFile(Context context, String filename) {
        String ret = "";
        try {
            InputStream inputStream = context.openFileInput(filename);
>>>>>>> d60ec35235921d6429ca6cab61b2affdfc82dfea

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                }
                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (Exception e) {
            Log.e("ERROR", "Can not read file. Check Compatibility.");
        }
        return ret;
    }

<<<<<<< HEAD
    public int getTotalSpentMoney(String filename) {
        String data = readFromFile(filename);
=======
    public int getTotalSpentMoney(String filename, Context context) {
        String data = readFromFile(context, filename);
>>>>>>> d60ec35235921d6429ca6cab61b2affdfc82dfea
        if (data.isEmpty()) {
            return 0;
        }

        int totalSpentValue = 0;
        String [] splitLines = data.split("\n");
        for (String line : splitLines) {
            String [] currLine = line.split("_");
            if (currLine.length < 3) {
                continue;
            }

            totalSpentValue += Integer.parseInt(currLine[1]);
        }

        return totalSpentValue;
<<<<<<< HEAD
    }

    public float getGoalsMoney() {
        String read = readFromFile(GoalsFragment.GOALS_FILE);
        if (read.isEmpty()) {
            return 0.0f;
        }

        float val = 0.0f;
        String [] splitRead = read.split("\n");
        for (String line : splitRead) {
            if (line.length() == 0) {
                continue;
            }

            String [] split2 = line.split("_");
            val += Float.parseFloat(split2[4]);
        }

        return val;
=======
>>>>>>> d60ec35235921d6429ca6cab61b2affdfc82dfea
    }
}