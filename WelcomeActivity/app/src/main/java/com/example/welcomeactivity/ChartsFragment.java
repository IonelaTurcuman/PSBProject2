package com.example.welcomeactivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.textfield.TextInputLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

public class ChartsFragment extends Fragment {
    public static final String MONEY_INFO_FILE = "money_available_info_file15.txt";
    public static final String MONEY_HOME_INFO_FILE = "money_home_info_file15.txt";
    public static final String MONEY_FOOD_INFO_FILE = "money_food_info_file15.txt";
    public static final String MONEY_MEDICAL_INFO_FILE = "money_medical_info_file15.txt";
    public static final String MONEY_OTHERS_INFO_FILE = "money_others_info_file15.txt";

    private TextView currentDateTextView;
    private TextView totalMoneyTextView;
    private TextView totalMoneyLeftTextView;
    private TextView insertMoneyTextView;
    private TextView piechart1TitleTextView;
    private TextView piechart2TitleTextView;
    private PieChart moneySpentPieChart;
    private PieChart moneyEveryCategoryPieChart;
    private Button addMoneyButton;
    private EditText totalAmountEditText;

    private EditText homeMoneyEditText;
    private TextView homeMoneyTextView;
    private Button addHomeMoneyButton;

    private EditText foodMoneyEditText;
    private TextView foodMoneyTextView;
    private Button addFoodMoneyButton;

    private EditText medicalMoneyEditText;
    private TextView medicalMoneyTextView;
    private Button addMedicalMoneyButton;

    private EditText othersMoneyEditText;
    private TextView othersMoneyTextView;
    private Button addOthersMoneyButton;

    private double totalAllocatedMoney = 0.0;
    private double totalHomeAllocatedMoney = 0.0;
    private double totalFoodAllocatedMoney = 0.0;
    private double totalMedicalAllocatedMoney = 0.0;
    private double totalOthersAllocatedMoney = 0.0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_charts, container, false);

        currentDateTextView = view.findViewById(R.id.currentDateTextView);
        totalMoneyTextView = view.findViewById(R.id.totalSumTextView);
        totalMoneyLeftTextView = view.findViewById(R.id.moneyLeftTextView);
        moneySpentPieChart = view.findViewById(R.id.moneySpentPieChart);
        insertMoneyTextView = view.findViewById(R.id.addTotalSumTextView);
        addMoneyButton = view.findViewById(R.id.addMoneyButton);
        totalAmountEditText = view.findViewById(R.id.totalSumEditText);
        moneyEveryCategoryPieChart = view.findViewById(R.id.moneyEveryCategoryPieChart);
        piechart1TitleTextView = view.findViewById(R.id.piechart1TitleTextView);
        piechart2TitleTextView = view.findViewById(R.id.piechart2TitleTextView);

        homeMoneyEditText = view.findViewById(R.id.homeMoneyEditText);
        homeMoneyTextView = view.findViewById(R.id.homeMoneyTextView);
        addHomeMoneyButton = view.findViewById(R.id.addHomeMoneyButton);

        foodMoneyEditText = view.findViewById(R.id.foodMoneyEditText);
        foodMoneyTextView = view.findViewById(R.id.foodMoneyTextView);
        addFoodMoneyButton = view.findViewById(R.id.addFoodMoneyButton);

        medicalMoneyEditText = view.findViewById(R.id.medicalMoneyEditText);
        medicalMoneyTextView = view.findViewById(R.id.medicalMoneyTextView);
        addMedicalMoneyButton = view.findViewById(R.id.addMedicalMoneyButton);

        othersMoneyEditText = view.findViewById(R.id.othersMoneyEditText);
        othersMoneyTextView = view.findViewById(R.id.othersMoneyTextView);
        addOthersMoneyButton = view.findViewById(R.id.addOthersMoneyButton);

        // TOP PAGE
        totalMoneyTextView.setBackgroundResource(R.drawable.rounded_corners_title);
        GradientDrawable  drawable = (GradientDrawable) totalMoneyTextView.getBackground();
        drawable.setColor(ContextCompat.getColor(getActivity(), R.color.transparent));

        totalMoneyLeftTextView.setBackgroundResource(R.drawable.rounded_corners_title);
        drawable = (GradientDrawable) totalMoneyLeftTextView.getBackground();
        drawable.setColor(ContextCompat.getColor(getActivity(), R.color.transparent));

        //HOME
        homeMoneyEditText.setBackgroundResource(R.drawable.rounded_corners_title);
        drawable = (GradientDrawable) homeMoneyEditText.getBackground();
        drawable.setColor(ContextCompat.getColor(getActivity(), R.color.transparent));

        homeMoneyTextView.setBackgroundResource(R.drawable.rounded_corners_title);
        drawable = (GradientDrawable) homeMoneyTextView.getBackground();
        drawable.setColor(ContextCompat.getColor(getActivity(), R.color.transparent));

        //FOOD
        foodMoneyEditText.setBackgroundResource(R.drawable.rounded_corners_title);
        drawable = (GradientDrawable) foodMoneyEditText.getBackground();
        drawable.setColor(ContextCompat.getColor(getActivity(), R.color.transparent));

        foodMoneyTextView.setBackgroundResource(R.drawable.rounded_corners_title);
        drawable = (GradientDrawable) foodMoneyTextView.getBackground();
        drawable.setColor(ContextCompat.getColor(getActivity(), R.color.transparent));

        //MEDICAL
        medicalMoneyEditText.setBackgroundResource(R.drawable.rounded_corners_title);
        drawable = (GradientDrawable) medicalMoneyEditText.getBackground();
        drawable.setColor(ContextCompat.getColor(getActivity(), R.color.transparent));

        medicalMoneyTextView.setBackgroundResource(R.drawable.rounded_corners_title);
        drawable = (GradientDrawable) medicalMoneyTextView.getBackground();
        drawable.setColor(ContextCompat.getColor(getActivity(), R.color.transparent));

        //OTHERS
        othersMoneyEditText.setBackgroundResource(R.drawable.rounded_corners_title);
        drawable = (GradientDrawable) othersMoneyEditText.getBackground();
        drawable.setColor(ContextCompat.getColor(getActivity(), R.color.transparent));

        othersMoneyTextView.setBackgroundResource(R.drawable.rounded_corners_title);
        drawable = (GradientDrawable) othersMoneyTextView.getBackground();
        drawable.setColor(ContextCompat.getColor(getActivity(), R.color.transparent));

        currentDateTextView.setText(java.text.DateFormat.getDateInstance().format(new Date()));
        currentDateTextView.setBackgroundResource(R.drawable.rounded_corners_title);
        drawable = (GradientDrawable) currentDateTextView.getBackground();
        drawable.setColor(ContextCompat.getColor(getActivity(), R.color.transparent));

        piechart1TitleTextView.setBackgroundResource(R.drawable.rounded_corners_title);
        drawable = (GradientDrawable) piechart1TitleTextView.getBackground();
        drawable.setColor(ContextCompat.getColor(getActivity(), R.color.transparent));

        piechart2TitleTextView.setBackgroundResource(R.drawable.rounded_corners_title);
        drawable = (GradientDrawable) piechart2TitleTextView.getBackground();
        drawable.setColor(ContextCompat.getColor(getActivity(), R.color.transparent));

        // ALL
        String readData = readFromFile(MONEY_INFO_FILE);
        if (!readData.isEmpty()) {
            addMoneyButton.setVisibility(View.INVISIBLE);
            totalAmountEditText.setVisibility(View.INVISIBLE);
            insertMoneyTextView.setVisibility(View.INVISIBLE);
            totalMoneyTextView.setVisibility(View.VISIBLE);
            totalMoneyLeftTextView.setVisibility(View.VISIBLE);

            String [] currLine = readData.split(" ");
            if (currLine[0].contains("TOTAL_MONEY")) {
                totalAllocatedMoney = Double.parseDouble(currLine[1]);
                totalMoneyTextView.setText("Total money available for this month: \n" +
                        currLine[1] + " lei");
            }

            totalMoneyLeftTextView.setText("Total amount of money left:" + "\n" +
                    (totalAllocatedMoney - getTotalMoneySpent()) + " lei");
            createMoneyLeftPieChart();
        }

        addMoneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMoneyButton.setVisibility(View.INVISIBLE);
                totalAmountEditText.setVisibility(View.INVISIBLE);
                insertMoneyTextView.setVisibility(View.INVISIBLE);
                totalMoneyTextView.setVisibility(View.VISIBLE);
                totalMoneyLeftTextView.setVisibility(View.VISIBLE);

                totalAllocatedMoney = Double.parseDouble(totalAmountEditText.getText().toString());
                totalMoneyTextView.setText("Total money available for this month: \n" +
                            totalAmountEditText.getText().toString() + " lei");

                String data = "TOTAL_MONEY " + totalAmountEditText.getText().toString();
                writeToFile(MONEY_INFO_FILE, data);

                totalMoneyLeftTextView.setText("Total amount of money left:" + "\n" +
                        totalAllocatedMoney + " lei");

                createMoneyLeftPieChart();
            }
        });

        // HOME
        readData = readFromFile(MONEY_HOME_INFO_FILE);
        if (!readData.isEmpty()) {
            homeMoneyEditText.setVisibility(View.INVISIBLE);
            addHomeMoneyButton.setVisibility(View.INVISIBLE);
            homeMoneyTextView.setVisibility(View.VISIBLE);

            String [] currLine = readData.split(" ");
            if (currLine[0].contains("SPENT_MONEY_HOME")) {
                totalHomeAllocatedMoney = Double.parseDouble(currLine[1]);
                homeMoneyTextView.setText("Total money saved for HOME: " + currLine[1] + " lei");
            }
        }

        addHomeMoneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeMoneyEditText.setVisibility(View.INVISIBLE);
                addHomeMoneyButton.setVisibility(View.INVISIBLE);
                homeMoneyTextView.setVisibility(View.VISIBLE);

                totalHomeAllocatedMoney = Double.parseDouble(homeMoneyEditText.getText().toString());
                homeMoneyTextView.setText("Total money saved for HOME: " +
                        homeMoneyEditText.getText().toString() + " lei");
                String data = "SPENT_MONEY_HOME " + homeMoneyEditText.getText().toString() + "\n";
                writeToFile(MONEY_HOME_INFO_FILE, data);

                createMoneyCategoriesPieChart();
            }
        });

        // FOOD
        readData = readFromFile(MONEY_FOOD_INFO_FILE);
        if (!readData.isEmpty()) {
            foodMoneyEditText.setVisibility(View.INVISIBLE);
            addFoodMoneyButton.setVisibility(View.INVISIBLE);
            foodMoneyTextView.setVisibility(View.VISIBLE);

            String [] currLine = readData.split(" ");
            Log.d("AA", currLine[0]);
            if (currLine[0].contains("SPENT_MONEY_FOOD")) {
                totalFoodAllocatedMoney = Double.parseDouble(currLine[1]);
                foodMoneyTextView.setText("Total money saved for FOOD: " + currLine[1] + " lei");
            }
        }

        addFoodMoneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodMoneyEditText.setVisibility(View.INVISIBLE);
                addFoodMoneyButton.setVisibility(View.INVISIBLE);
                foodMoneyTextView.setVisibility(View.VISIBLE);

                totalFoodAllocatedMoney = Double.parseDouble(foodMoneyEditText.getText().toString());
                foodMoneyTextView.setText("Total money saved for FOOD: " +
                        foodMoneyEditText.getText().toString() + " lei");
                String data = "SPENT_MONEY_FOOD " + foodMoneyEditText.getText().toString() + "\n";
                writeToFile(MONEY_FOOD_INFO_FILE, data);

                createMoneyCategoriesPieChart();
            }
        });

        // MEDICAL
        readData = readFromFile(MONEY_MEDICAL_INFO_FILE);
        if (!readData.isEmpty()) {
            medicalMoneyEditText.setVisibility(View.INVISIBLE);
            addMedicalMoneyButton.setVisibility(View.INVISIBLE);
            medicalMoneyTextView.setVisibility(View.VISIBLE);

            String [] currLine = readData.split(" ");
            if (currLine[0].contains("SPENT_MONEY_MEDICAL")) {
                totalMedicalAllocatedMoney = Double.parseDouble(currLine[1]);
                medicalMoneyTextView.setText("Total money saved for MEDICAL: " + currLine[1] + " lei");
            }
        }

        addMedicalMoneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                medicalMoneyEditText.setVisibility(View.INVISIBLE);
                addMedicalMoneyButton.setVisibility(View.INVISIBLE);
                medicalMoneyTextView.setVisibility(View.VISIBLE);

                totalMedicalAllocatedMoney = Double.parseDouble(medicalMoneyEditText.getText().toString());
                medicalMoneyTextView.setText("Total money saved for MEDICAL: " +
                        medicalMoneyEditText.getText().toString() + " lei");
                String data = "SPENT_MONEY_MEDICAL " + medicalMoneyEditText.getText().toString() + "\n";
                writeToFile(MONEY_MEDICAL_INFO_FILE, data);

                createMoneyCategoriesPieChart();
            }
        });

        // OTHERS
        readData = readFromFile(MONEY_OTHERS_INFO_FILE);
        if (!readData.isEmpty()) {
            othersMoneyEditText.setVisibility(View.INVISIBLE);
            addOthersMoneyButton.setVisibility(View.INVISIBLE);
            othersMoneyTextView.setVisibility(View.VISIBLE);

            String [] currLine = readData.split(" ");
            if (currLine[0].contains("SPENT_MONEY_OTHERS")) {
                totalOthersAllocatedMoney = Double.parseDouble(currLine[1]);
                othersMoneyTextView.setText("Total money saved for OTHERS: " + currLine[1] + " lei");
            }
        }

        addOthersMoneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                othersMoneyEditText.setVisibility(View.INVISIBLE);
                addOthersMoneyButton.setVisibility(View.INVISIBLE);
                othersMoneyTextView.setVisibility(View.VISIBLE);

                totalOthersAllocatedMoney = Double.parseDouble(othersMoneyEditText.getText().toString());
                othersMoneyTextView.setText("Total money saved for OTHERS: " +
                        othersMoneyEditText.getText().toString() + " lei");
                String data = "SPENT_MONEY_OTHERS " + othersMoneyEditText.getText().toString() + "\n";
                writeToFile(MONEY_OTHERS_INFO_FILE, data);

                createMoneyCategoriesPieChart();
            }
        });

        createMoneyLeftPieChart();
        createMoneyCategoriesPieChart();

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void createMoneyLeftPieChart() {
        double totalSpentValue = getTotalMoneySpent();

        moneySpentPieChart.setUsePercentValues(true);
        moneySpentPieChart.getDescription().setText("");
        moneySpentPieChart.setHoleColor(Color.WHITE);
        moneySpentPieChart.setHoleRadius(40);
        moneySpentPieChart.setTransparentCircleRadius(50);
        moneySpentPieChart.setTransparentCircleAlpha(40);
        moneySpentPieChart.animateY(800);
        moneySpentPieChart.setHoleColor(ContextCompat.getColor(getActivity(), R.color.piechartHole));
        moneySpentPieChart.setDrawEntryLabels(false);
        moneySpentPieChart.setDrawMarkers(false);
        moneySpentPieChart.setExtraOffsets(0, 0, 0, -7);

        Legend legend = moneySpentPieChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        ArrayList<PieEntry> percs = getPiechartValues(totalSpentValue);

        PieDataSet set = new PieDataSet(percs, "");
        int[] MY_COLORS =  {Color.rgb(213, 42, 42), Color.rgb(22, 80, 17)};

        ArrayList<Integer> colors = new ArrayList<>();
        for (int c: MY_COLORS) {
            colors.add(c);
        }
        set.setColors(colors);

        PieData data = new PieData(set);
        data.setValueTextSize(10f);
        moneySpentPieChart.setData(data);
    }

    public void createMoneyCategoriesPieChart() {
        moneyEveryCategoryPieChart.setUsePercentValues(true);
        moneyEveryCategoryPieChart.getDescription().setText("");
        moneyEveryCategoryPieChart.setHoleColor(Color.WHITE);
        moneyEveryCategoryPieChart.setHoleRadius(40);
        moneyEveryCategoryPieChart.setTransparentCircleRadius(50);
        moneyEveryCategoryPieChart.setTransparentCircleAlpha(40);
        moneyEveryCategoryPieChart.animateY(800);
        moneyEveryCategoryPieChart.setHoleColor(ContextCompat.getColor(getActivity(), R.color.piechartHole));
        moneyEveryCategoryPieChart.setDrawEntryLabels(false);
        moneyEveryCategoryPieChart.setDrawMarkers(false);
        moneyEveryCategoryPieChart.setExtraOffsets(0, 0, 0, -7);

        Legend legend = moneyEveryCategoryPieChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        ArrayList<PieEntry> percs = getPiechartValuesCategories();

        PieDataSet set = new PieDataSet(percs, "");
        int[] MY_COLORS =  {Color.rgb(255, 255, 255),
                            Color.rgb(255, 112, 112), Color.rgb(244, 211, 98),
                            Color.rgb(98, 136, 226), Color.rgb(63, 164, 54)};

        ArrayList<Integer> colors = new ArrayList<>();
        for (int c: MY_COLORS) {
            colors.add(c);
        }
        set.setColors(colors);

        PieData data = new PieData(set);
        data.setValueTextSize(10f);
        moneyEveryCategoryPieChart.setData(data);
    }

    public ArrayList<PieEntry> getPiechartValues(double totalSpentValue) {
        ArrayList<PieEntry> percs = new ArrayList<>();

        if (totalSpentValue == 0) {
            percs.add(new PieEntry(0.0f, "Spent"));
            percs.add(new PieEntry(1.0f, "Left"));
            return percs;
        }

        double spentVal = totalSpentValue / totalAllocatedMoney * 100;
        double remainedVal = (double)100 - spentVal;

        percs.add(new PieEntry((float)spentVal, "Spent"));
        percs.add(new PieEntry((float)remainedVal, "Left"));
        return percs;
    }

    public ArrayList<PieEntry> getPiechartValuesCategories() {
        ArrayList<PieEntry> percs = new ArrayList<>();

        if (totalAllocatedMoney == 0 ||
                (totalHomeAllocatedMoney == 0 && totalFoodAllocatedMoney == 0 &&
                        totalMedicalAllocatedMoney == 0 && totalOthersAllocatedMoney == 0)) {
            percs.add(new PieEntry(1.0f, "Unused"));
            percs.add(new PieEntry(0.0f, "Home"));
            percs.add(new PieEntry(0.0f, "Food"));
            percs.add(new PieEntry(0.0f, "Medical"));
            percs.add(new PieEntry(0.0f, "Others"));
            return percs;
        }

        double spentValHome = totalHomeAllocatedMoney / totalAllocatedMoney * 100;
        double spentValFood = totalFoodAllocatedMoney / totalAllocatedMoney * 100;
        double spentValMedical = totalMedicalAllocatedMoney / totalAllocatedMoney * 100;
        double spentValOthers = totalOthersAllocatedMoney / totalAllocatedMoney * 100;
        double unused = (totalAllocatedMoney - (totalHomeAllocatedMoney + totalFoodAllocatedMoney +
                totalMedicalAllocatedMoney + totalOthersAllocatedMoney)) / totalAllocatedMoney * 100;

        percs.add(new PieEntry((float)unused, "Unused"));
        percs.add(new PieEntry((float)spentValHome, "Home"));
        percs.add(new PieEntry((float)spentValFood, "Food"));
        percs.add(new PieEntry((float)spentValMedical, "Medical"));
        percs.add(new PieEntry((float)spentValOthers, "Others"));
        return percs;
    }

    public double getTotalMoneySpent() {
        return getSpentMoneyCategory(SpendingsGeneralActivity.SPENDINGS_HOME_FILE) +
                getSpentMoneyCategory(SpendingsGeneralActivity.SPENDINGS_FOOD_FILE) +
                getSpentMoneyCategory(SpendingsGeneralActivity.SPENDINGS_MEDICAL_FILE) +
                getSpentMoneyCategory(SpendingsGeneralActivity.SPENDINGS_OTHERS_FILE) +
                getGoalsMoney();
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
    }

    public double getSpentMoneyCategory(String filename) {
        String data = readFromFile(filename);
        if (data.isEmpty()) {
            return 0;
        }

        double totalSpentValue = 0;
        String [] splitLines = data.split("\n");
        for (String line : splitLines) {
            String [] currLine = line.split("_");
            if (currLine.length < 3) {
                continue;
            }

            totalSpentValue += Double.parseDouble(currLine[1]);
        }

        return totalSpentValue;
    }

    private void writeToFile(String filename, String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getActivity().openFileOutput(filename, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("ERROR", "File write failed: " + e.toString());
        }
    }

    private String readFromFile(String filename) {
        String ret = "";
        try {
            InputStream inputStream = getActivity().openFileInput(filename);

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
            Log.e("ERROR", "Can not read file. Check " + filename);
        }
        return ret;
    }
}