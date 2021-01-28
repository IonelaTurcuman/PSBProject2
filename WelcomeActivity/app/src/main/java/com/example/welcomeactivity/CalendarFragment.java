package com.example.welcomeactivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CalendarFragment extends Fragment {

    Dialog myDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        CalendarView calendarView = view.findViewById(R.id.calendarView);

        myDialog = new Dialog(calendarView.getContext());
        String homeData = readFromFile(calendarView.getContext(), SpendingsGeneralActivity.SPENDINGS_HOME_FILE);
        String foodData = readFromFile(calendarView.getContext(), SpendingsGeneralActivity.SPENDINGS_FOOD_FILE);
        String medicalData = readFromFile(calendarView.getContext(), SpendingsGeneralActivity.SPENDINGS_MEDICAL_FILE);
        String othersData = readFromFile(calendarView.getContext(), SpendingsGeneralActivity.SPENDINGS_OTHERS_FILE);
        final List<String> homeCategory = new ArrayList<>(Arrays.asList(homeData.split("\\r?\\n")));
        final List<String> foodCategory = new ArrayList<>(Arrays.asList(foodData.split("\\r?\\n")));
        final List<String> medicalCategory = new ArrayList<>(Arrays.asList(medicalData.split("\\r?\\n")));
        final List<String> otherCategory = new ArrayList<>(Arrays.asList(othersData.split("\\r?\\n")));

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                myDialog.setContentView(R.layout.calendar_popup);
                myDialog.show();
                List<String> homeItems = getDateInfo(homeCategory, year, month, dayOfMonth);
                List<String> foodItems = getDateInfo(foodCategory, year, month, dayOfMonth);
                List<String> medicalItems = getDateInfo(medicalCategory, year, month, dayOfMonth);
                List<String> otherItems = getDateInfo(otherCategory, year, month, dayOfMonth);
                TextView dateText= myDialog.findViewById(R.id.calendarDate);
                dateText.setText("Selected day: " + dayOfMonth +"/"+ (month+1) +"/"+ year);
                List<String> boughtItems = getItems(homeItems,  foodItems,  medicalItems,  otherItems, 0);
                Log.d("BOUGHTITEMS", String.valueOf(boughtItems));
                TextView itemsText= myDialog.findViewById(R.id.tabelulMeuItems);
                String totalItems="";
                for (int i=0; i<boughtItems.size(); i++) {
                    totalItems += boughtItems.get(i) + "\n";
                }
                itemsText.setText(totalItems);
                TextView categoryText= myDialog.findViewById(R.id.tabelulMeuCategory);
                List<String> categoryItems = getCategory(homeItems,  foodItems,  medicalItems,  otherItems);
                Log.d("CATEGORYITEMS", String.valueOf(categoryItems));
                String totalCategories="";
                for (int i=0; i<categoryItems.size(); i++) {
                    totalCategories += categoryItems.get(i) + "\n";
                }
                categoryText.setText(totalCategories);
                TextView priceText= myDialog.findViewById(R.id.tabelulMeuPrice);
                List<String> priceItems = getItems(homeItems,  foodItems,  medicalItems,  otherItems, 1);
                Log.d("PRICEITEMS", String.valueOf(priceItems));
                String totalPrices="";
                for (int i=0; i<priceItems.size(); i++) {
                    totalPrices += priceItems.get(i) + "\n";
                }
                priceText.setText(totalPrices);


            }
        });
        return view;
    }

    private String readFromFile(Context context, String filename) {
        String ret = "";
        try {
            InputStream inputStream = context.openFileInput(filename);

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

    private List<String> getDateInfo(List<String> information, int year, int month, int dayOfMonth) {
        List<String> boughtItem = new ArrayList<>();
        for (int i=1; i < information.size(); i++){
            List<String> splitedArray = new ArrayList<>(Arrays.asList(String.valueOf(information.get(i)).split("_")));
            String currentDate = getMonth(month+1).substring(0,3) + " " + dayOfMonth + ", " + year;
            if (splitedArray.get(2).equals(currentDate)){
                boughtItem.add(information.get(i));
            }
        }
        return boughtItem;
    }

    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month-1];
    }

    public List<String> getItems(List<String> homeItems, List<String> foodItems, List<String> medicalItems, List<String> otherItems, int value) {
        List<String> boughtItem = new ArrayList<>();
        for (int i=0; i < homeItems.size(); i++){
            String [] item = String.valueOf(homeItems.get(i)).split("_");
            boughtItem.add(item[value]);
        }

        for (int i=0; i < foodItems.size(); i++){
            String [] item = String.valueOf(foodItems.get(i)).split("_");
            boughtItem.add(item[value]);
        }

        for (int i=0; i < medicalItems.size(); i++){
            String [] item = String.valueOf(medicalItems.get(i)).split("_");
            boughtItem.add(item[value]);
        }

        for (int i=0; i < otherItems.size(); i++){
            String [] item = String.valueOf(otherItems.get(i)).split("_");
            boughtItem.add(item[value]);
        }

        return boughtItem;
    }

    public List<String> getCategory(List<String> homeItems, List<String> foodItems, List<String> medicalItems, List<String> otherItems) {
        List<String> boughtCategory = new ArrayList<>();
        for (int i=0; i < homeItems.size(); i++){
            boughtCategory.add("Home");
        }

        for (int i=0; i < foodItems.size(); i++){
            boughtCategory.add("Food");
        }

        for (int i=0; i < medicalItems.size(); i++){
            boughtCategory.add("Medical");
        }

        for (int i=0; i < otherItems.size(); i++){
            boughtCategory.add("Other");
        }

        return boughtCategory;
    }
}