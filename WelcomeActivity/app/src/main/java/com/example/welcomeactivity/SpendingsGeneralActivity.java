package com.example.welcomeactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SpendingsGeneralActivity extends AppCompatActivity {

    public static final String SPENDINGS_HOME_FILE = "HOME_SPENDINGS_FILE16.txt";
    public static final String SPENDINGS_FOOD_FILE = "FOOD_SPENDINGS_FILE16.txt";
    public static final String SPENDINGS_MEDICAL_FILE = "MEDICAL_SPENDINGS_FILE16.txt";
    public static final String SPENDINGS_OTHERS_FILE = "OTHERS_SPENDINGS_FILE16.txt";

    private String currentSpendingType = "None";
    private TextView spendingsTypeTextView;
    private TextView allocatedMoneyTextView;
    private TextView spentMoneyTextView;
    private PieChart moneySpentPieChart;
    private LinearLayout mainLinearLayout;
    private Button addNewSpendingButton;
    private float totalAllocatedSum = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spendings_general);

        spendingsTypeTextView = findViewById(R.id.spendingsTypeTextView);
        moneySpentPieChart = findViewById(R.id.moneySpentPieChart);
        addNewSpendingButton = findViewById(R.id.addProductGeneralButton);
        mainLinearLayout = findViewById(R.id.mainLinearLayout);
        allocatedMoneyTextView = findViewById(R.id.allocatedMoneyTextView);
        spentMoneyTextView = findViewById(R.id.spentMoneyTextView);

        if (getIntent().hasExtra(Spendings.SPENDINGS_TYPE)) {
            currentSpendingType = getIntent().getStringExtra(Spendings.SPENDINGS_TYPE);
        }

        if (currentSpendingType.equals("None")) {
            Log.d("SPENDINGS Error", "No spendings type received.");
            return;
        }

        setTotalAllocatedSum(currentSpendingType);

        spendingsTypeTextView.setBackgroundResource(R.drawable.rounded_corners_title);
        GradientDrawable drawable = (GradientDrawable) spendingsTypeTextView.getBackground();
        drawable.setColor(ContextCompat.getColor(this, R.color.transparent));

        allocatedMoneyTextView.setBackgroundResource(R.drawable.rounded_corners_title);
        drawable = (GradientDrawable) allocatedMoneyTextView.getBackground();
        drawable.setColor(ContextCompat.getColor(this, R.color.transparent));
        allocatedMoneyTextView.setText("Allocated money:" + "\n" + totalAllocatedSum + " lei");

        spentMoneyTextView.setBackgroundResource(R.drawable.rounded_corners_title);
        drawable = (GradientDrawable) spentMoneyTextView.getBackground();
        drawable.setColor(ContextCompat.getColor(this, R.color.transparent));

        int totalSpentValue = 0;
        if (currentSpendingType.equals(Spendings.SPENDINGS_HOME)) {
            totalSpentValue = getTotalSpentMoney(SPENDINGS_HOME_FILE);
        } else if (currentSpendingType.equals(Spendings.SPENDINGS_FOOD)) {
            totalSpentValue = getTotalSpentMoney(SPENDINGS_FOOD_FILE);
        } else if (currentSpendingType.equals(Spendings.SPENDINGS_MEDICAL)) {
            totalSpentValue = getTotalSpentMoney(SPENDINGS_MEDICAL_FILE);
        } else {
            totalSpentValue = getTotalSpentMoney(SPENDINGS_OTHERS_FILE);
        }
        spentMoneyTextView.setText("Spent money:" + "\n" + totalSpentValue + " lei");

        if (currentSpendingType.equals(Spendings.SPENDINGS_HOME)) {
            spendingsTypeTextView.setText("HOME");
            createPieChart();
            setExistingSpendings(SPENDINGS_HOME_FILE);
        } else if (currentSpendingType.equals(Spendings.SPENDINGS_FOOD)) {
            spendingsTypeTextView.setText("FOOD");
            createPieChart();
            setExistingSpendings(SPENDINGS_FOOD_FILE);
        } else if (currentSpendingType.equals(Spendings.SPENDINGS_MEDICAL)) {
            spendingsTypeTextView.setText("MEDICAL");
            createPieChart();
            setExistingSpendings(SPENDINGS_MEDICAL_FILE);
        } else {
            spendingsTypeTextView.setText("OTHERS");
            createPieChart();
            setExistingSpendings(SPENDINGS_OTHERS_FILE);
        }

        addNewSpendingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProductPopup();
            }
        });
    }

    public void createPieChart() {
        moneySpentPieChart.setUsePercentValues(true);
        moneySpentPieChart.getDescription().setText("");
        moneySpentPieChart.setHoleColor(Color.WHITE);
        moneySpentPieChart.setHoleRadius(40);
        moneySpentPieChart.setTransparentCircleRadius(50);
        moneySpentPieChart.setTransparentCircleAlpha(40);
        moneySpentPieChart.animateY(800);
        moneySpentPieChart.setHoleColor(ContextCompat.getColor(this, R.color.piechartHole));
        moneySpentPieChart.setDrawEntryLabels(false);
        moneySpentPieChart.setDrawMarkers(false);
        moneySpentPieChart.setExtraOffsets(0, 0, 0, -7);

        Legend legend = moneySpentPieChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        ArrayList<PieEntry> percs = getPiechartValues();

        PieDataSet set = new PieDataSet(percs, "");
        int[] MY_COLORS_HOME =  {Color.rgb(213, 42, 42), Color.rgb(255, 112, 112)};
        int[] MY_COLORS_FOOD =  {Color.rgb(233, 185, 22), Color.rgb(244, 211, 98)};
        int[] MY_COLORS_MEDICAL =  {Color.rgb(20, 63, 163), Color.rgb(98, 136, 226)};
        int[] MY_COLORS_OTHERS =  {Color.rgb(22, 80, 17), Color.rgb(63, 164, 54)};

        int[] MY_COLORS = null;
        if (currentSpendingType.equals(Spendings.SPENDINGS_HOME)) {
            MY_COLORS = MY_COLORS_HOME;
        } else if (currentSpendingType.equals(Spendings.SPENDINGS_FOOD)) {
            MY_COLORS = MY_COLORS_FOOD;
        } else if (currentSpendingType.equals(Spendings.SPENDINGS_MEDICAL)) {
            MY_COLORS = MY_COLORS_MEDICAL;
        } else {
            MY_COLORS = MY_COLORS_OTHERS;
        }

        ArrayList<Integer> colors = new ArrayList<>();
        for (int c: MY_COLORS) {
            colors.add(c);
        }
        set.setColors(colors);

        PieData data = new PieData(set);
        data.setValueTextSize(10f);
        moneySpentPieChart.setData(data);
    }

    public void setTotalAllocatedSum(String currentSpendingType) {
        String filename = "";
        if (currentSpendingType.equals(Spendings.SPENDINGS_HOME)) {
            filename = ChartsFragment.MONEY_HOME_INFO_FILE;
        } else if (currentSpendingType.equals(Spendings.SPENDINGS_FOOD)) {
            filename = ChartsFragment.MONEY_FOOD_INFO_FILE;
        } else if (currentSpendingType.equals(Spendings.SPENDINGS_MEDICAL)) {
            filename = ChartsFragment.MONEY_MEDICAL_INFO_FILE;
        } else {
            filename = ChartsFragment.MONEY_OTHERS_INFO_FILE;
        }

        String readData = readFromFile(filename);
        if (readData.isEmpty()) {
            return;
        }

        String [] splitData = readData.split(" ");
        totalAllocatedSum = Float.parseFloat(splitData[1]);
    }

    public void setExistingSpendings(String filename) {
        String text = readFromFile(filename);
        if (text.isEmpty()) {
            Log.d("WARNING", "No products in home spendings.");
            return;
        }

        Log.d("READ MEDICAL", text);
        int index = 0;
        String [] linesSpendings = text.split("\n");
        for (String line : linesSpendings) {
            String [] splitLine = line.split("_");
            if (splitLine.length < 3) {
                continue;
            }

            addProduct(splitLine, index++);
        }
    }

    public void addProductPopup() {
        final View popupView = getLayoutInflater().inflate(R.layout.add_product, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(popupView);

        final AlertDialog dialogPopup = dialogBuilder.create();

        dialogPopup.show();
        dialogPopup.setCanceledOnTouchOutside(true);
        dialogPopup.getWindow().setLayout(900, 800);
        dialogPopup.getWindow().setBackgroundDrawableResource(R.drawable.rounded_corners_popup);

        final Button addButton = popupView.findViewById(R.id.addProductButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText productName = popupView.findViewById(R.id.productNameEditText);
                EditText price = popupView.findViewById(R.id.priceEditText);

                String data = "";
                if (currentSpendingType.equals(Spendings.SPENDINGS_HOME)) {
                    //String d = "lampa_10_Jan 21, 2021\npat_30_Jan 23, 2021\n";
                    //writeToFile(SPENDINGS_HOME_FILE, d);
                    data = productName.getText().toString() + "_" + price.getText().toString() +
                            "_" + java.text.DateFormat.getDateInstance().format(new Date()) + "\n";
                    writeToFile(SPENDINGS_HOME_FILE, data);
                } else if (currentSpendingType.equals(Spendings.SPENDINGS_FOOD)) {
                    //String d = "mazare_1_Jan 21, 2021\ncartofi_2_Jan 23, 2021\n";
                    //writeToFile(SPENDINGS_FOOD_FILE, d);
                    data = productName.getText().toString() + "_" + price.getText().toString() +
                            "_" + java.text.DateFormat.getDateInstance().format(new Date()) + "\n";
                    writeToFile(SPENDINGS_FOOD_FILE, data);
                } else if (currentSpendingType.equals(Spendings.SPENDINGS_MEDICAL)) {
                    //String d = "paracetamol_2_Jan 21, 2021\nalgocalmin_10_Jan 23, 2021\n";
                    //writeToFile(SPENDINGS_MEDICAL_FILE, d);
                    data = productName.getText().toString() + "_" + price.getText().toString() +
                            "_" + java.text.DateFormat.getDateInstance().format(new Date()) + "\n";
                    writeToFile(SPENDINGS_MEDICAL_FILE, data);
                } else {
                    //String d = "bocanci_10_Jan 21, 2021\ntelefon_10_Jan 23, 2021\n";
                   // writeToFile(SPENDINGS_OTHERS_FILE, d);
                    data = productName.getText().toString() + "_" + price.getText().toString() +
                            "_" + java.text.DateFormat.getDateInstance().format(new Date()) + "\n";
                    writeToFile(SPENDINGS_OTHERS_FILE, data);
                }

                addProduct(data.split("_"), 1000);

                int totalSpentValue = 0;
                if (currentSpendingType.equals(Spendings.SPENDINGS_HOME)) {
                    totalSpentValue = getTotalSpentMoney(SPENDINGS_HOME_FILE);
                } else if (currentSpendingType.equals(Spendings.SPENDINGS_FOOD)) {
                    totalSpentValue = getTotalSpentMoney(SPENDINGS_FOOD_FILE);
                } else if (currentSpendingType.equals(Spendings.SPENDINGS_MEDICAL)) {
                    totalSpentValue = getTotalSpentMoney(SPENDINGS_MEDICAL_FILE);
                } else {
                    totalSpentValue = getTotalSpentMoney(SPENDINGS_OTHERS_FILE);
                }
                spentMoneyTextView.setText("Spent money:" + "\n" + totalSpentValue  + " lei");

                createPieChart();
                dialogPopup.dismiss();
            }
        });
    }

    public void addProduct(String[] splitLine, int index) {
        TextView txt = new TextView(this);
        String currText = splitLine[0] + ": " + splitLine[1] + " lei" +
                ",                                   " + splitLine[2];
        txt.setText(currText);
        txt.setPadding(10, 10, 10, 10);
        txt.setMinHeight(40);
        txt.setTextSize(18);
        txt.setTextColor(Color.BLUE);
        txt.setBackgroundResource(R.drawable.rounded_corners_title);
        GradientDrawable drawable = (GradientDrawable) txt.getBackground();
        drawable.setColor(ContextCompat.getColor(this, R.color.transparent));

        TextView emptyUp = new TextView(this);
        emptyUp.setWidth(0);
        emptyUp.setHeight(10);
        emptyUp.setId(2222 + index);

        TextView emptyDown = new TextView(this);
        emptyDown.setWidth(0);
        emptyDown.setHeight(10);
        emptyDown.setId(2222 + index + 1);

        mainLinearLayout.addView(emptyUp);
        mainLinearLayout.addView(txt);
        mainLinearLayout.addView(emptyDown);
    }

    public ArrayList<PieEntry> getPiechartValues() {
        ArrayList<PieEntry> percs = new ArrayList<>();

        int totalSpentValue = 0;
        if (currentSpendingType.equals(Spendings.SPENDINGS_HOME)) {
            totalSpentValue = getTotalSpentMoney(SPENDINGS_HOME_FILE);
        } else if (currentSpendingType.equals(Spendings.SPENDINGS_FOOD)) {
            totalSpentValue = getTotalSpentMoney(SPENDINGS_FOOD_FILE);
        } else if (currentSpendingType.equals(Spendings.SPENDINGS_MEDICAL)) {
            totalSpentValue = getTotalSpentMoney(SPENDINGS_MEDICAL_FILE);
        } else {
            totalSpentValue = getTotalSpentMoney(SPENDINGS_OTHERS_FILE);
        }

        if (totalSpentValue == 0) {
            Log.d("WARNING", "No products in home spendings.");
            percs.add(new PieEntry(0.0f, "Spent"));
            percs.add(new PieEntry(1.0f, "Left"));
            return percs;
        }

        float spentVal = (float)totalSpentValue / totalAllocatedSum * 100;
        float remainedVal = (float)100 - spentVal;

        percs.add(new PieEntry(spentVal, "Spent"));
        percs.add(new PieEntry(remainedVal, "Left"));
        return percs;
    }

    public int getTotalSpentMoney(String filename) {
        String data = readFromFile(filename);
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
    }

    private void writeToFile(String filename, String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(filename, Context.MODE_APPEND));
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
            InputStream inputStream = openFileInput(filename);

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
}