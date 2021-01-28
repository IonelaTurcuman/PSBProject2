package com.example.welcomeactivity;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class Spendings extends Fragment {

    public static final String SPENDINGS_TYPE = "SPENDINGS_TYPE";
    public static final String SPENDINGS_HOME = "SPENDINGS_HOME";
    public static final String SPENDINGS_FOOD = "SPENDINGS_FOOD";
    public static final String SPENDINGS_MEDICAL = "SPENDINGS_MEDICAL";
    public static final String SPENDINGS_OTHERS = "SPENDINGS_OTHERS";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spendings, container, false);

        ImageButton homeImageButton = view.findViewById(R.id.homeImageButton);
        homeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToSpendings = new Intent(getActivity().getApplicationContext(), SpendingsGeneralActivity.class);
                goToSpendings.putExtra(SPENDINGS_TYPE, SPENDINGS_HOME);
                startActivity(goToSpendings);
            }
            });

        ImageButton foodImageButton = view.findViewById(R.id.foodImageButton);
        foodImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Start new activity
                Intent goToSpendings = new Intent(getActivity().getApplicationContext(), SpendingsGeneralActivity.class);
                goToSpendings.putExtra(SPENDINGS_TYPE, SPENDINGS_FOOD);
                startActivity(goToSpendings);
            }
        });

        ImageButton medicalImageButton = view.findViewById(R.id.medicalImageButton);
        medicalImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToSpendings = new Intent(getActivity().getApplicationContext(), SpendingsGeneralActivity.class);
                goToSpendings.putExtra(SPENDINGS_TYPE, SPENDINGS_MEDICAL);
                startActivity(goToSpendings);
            }
        });

        ImageButton othersImageButton = view.findViewById(R.id.othersImageButton);
        othersImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToSpendings = new Intent(getActivity().getApplicationContext(), SpendingsGeneralActivity.class);
                goToSpendings.putExtra(SPENDINGS_TYPE, SPENDINGS_OTHERS);
                startActivity(goToSpendings);
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}