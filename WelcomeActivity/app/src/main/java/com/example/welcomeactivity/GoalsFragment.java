package com.example.welcomeactivity;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;

public class GoalsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public static final String GOALS_FILE = "goals_info_file22.txt";

    View rootView = null;

    ArrayList<Goal> goals = new ArrayList<>();

    private LinearLayout linearLayout;
    private Button addGoalButton;
    private String currentAddedColor;

    static int global_index = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goals, container, false);
        rootView = view;

        linearLayout = view.findViewById(R.id.goalsLinearLayout);
        addGoalButton = view.findViewById(R.id.addGoalButton);

        addGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAddGoalPopup();
            }
        });

        loadExistingGoals();
        setGlobalIndex();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinnerTextSize) {
            currentAddedColor = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void createAddGoalPopup() {
        final View popupView = getLayoutInflater().inflate(R.layout.add_new_goal_popup, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(popupView);

        final AlertDialog dialogPopup = dialogBuilder.create();

        dialogPopup.show();
        dialogPopup.setCanceledOnTouchOutside(true);
        dialogPopup.getWindow().setLayout(900, 1200);
        dialogPopup.getWindow().setBackgroundDrawableResource(R.drawable.rounded_corners_popup);

        Spinner goalColorSpinner = popupView.findViewById(R.id.spinnerTextSize);
        final EditText goalNameEditText = popupView.findViewById(R.id.goalNameEditText);
        Button addGoalButton = popupView.findViewById(R.id.addNewGoalButton);
        final EditText sumToSave = popupView.findViewById(R.id.sumToSaveEditText);
        final EditText daysEditText = popupView.findViewById(R.id.daysEditText);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.goal_colors, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        goalColorSpinner.setAdapter(adapter);

        goalColorSpinner.setOnItemSelectedListener(this);

        addGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newGoalName = goalNameEditText.getText().toString();
                String numDays = daysEditText.getText().toString();
                float moneyToSave = Float.parseFloat(sumToSave.getText().toString());
                float moneySaved = 0.0f;

                ImageButton button = addGoal(newGoalName, numDays, moneyToSave, moneySaved, "Default", "Default");

                Goal goal = new Goal(newGoalName, currentAddedColor, numDays, moneyToSave, moneySaved);
                goal.setId(button.getId() + "");

                goals.add(goal);
                saveCurrentGoals();
                dialogPopup.dismiss();
            }
        });
    }

    public void createAddSavedMoneyPopup(final ImageButton currentButton) {
        final View popupView = getLayoutInflater().inflate(R.layout.add_goal_sum, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(popupView);

        final AlertDialog dialogPopup = dialogBuilder.create();

        dialogPopup.show();
        dialogPopup.setCanceledOnTouchOutside(true);
        dialogPopup.getWindow().setLayout(700, 600);
        dialogPopup.getWindow().setBackgroundDrawableResource(R.drawable.rounded_corners_popup);

        final EditText sumToAddEditText = popupView.findViewById(R.id.sumSavedEditText);

        Button addButton = popupView.findViewById(R.id.addSavedMoneyButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Goal currentGoal = null;
                int currId = currentButton.getId();
                Log.d("CUR1", "CUR1: " + currId);
                for (Goal goal : goals) {
                    Log.d("CUR1", "CUR2: " + goal.getId());
                    if (Integer.parseInt(goal.getId()) == (currId / 2) ||
                            Integer.parseInt(goal.getId()) == currId) {
                        currentGoal = goal;
                    }
                }

                Float sumToAdd = Float.parseFloat(sumToAddEditText.getText().toString());
                currentGoal.setMoneySaved(currentGoal.getMoneySaved() + sumToAdd);

                TextView txt = rootView.findViewById(currId * 2);
                txt.setText("GOAL: " + currentGoal.getMoneyToSave() + " lei                              " +
                        "PROGRESS: " + currentGoal.getMoneySaved() + " lei");

                ProgressBar pb = rootView.findViewById(currId * 3);
                pb.setProgress((int)Math.floor(getProgress(currentGoal.getMoneyToSave(), currentGoal.getMoneySaved())));

                saveCurrentGoals();
                dialogPopup.dismiss();
            }
        });
    }

    public ImageButton addGoal(final String goalName, String numDays, float moneyToSave,
                               float moneySaved, String color, String id) {
        int index = 0;
        ProgressBar pb = new ProgressBar(getActivity(), null, android.R.attr.progressBarStyleHorizontal);
        pb.setProgress((int)Math.floor(getProgress(moneyToSave, moneySaved)));
        pb.setLayoutParams(new RelativeLayout.LayoutParams(1100, 80));

        if (!color.equals("Default")) {
            currentAddedColor = color;
        }

        if (!id.equals("Default")) {
            global_index = Integer.parseInt(id);
        }

        pb.setId((9999 + global_index) * 3);

        if (currentAddedColor.equals("Red")) {
            pb.setBackgroundColor(Color.rgb(233, 61, 56));
        } else if (currentAddedColor.equals("Yellow")) {
            pb.setBackgroundColor(Color.rgb(224, 233, 45));
        } else if (currentAddedColor.equals("Blue")) {
            pb.setBackgroundColor(Color.rgb(19, 71, 118));
        } else if (currentAddedColor.equals("Purple")) {
            pb.setBackgroundColor(Color.rgb(119, 31, 187));
        } else if (currentAddedColor.equals("Green")){
            pb.setBackgroundColor(Color.rgb(44, 125, 21));
        } else {
            pb.setBackgroundColor(Color.rgb(210, 210, 210));
        }

        LinearLayout layout = new LinearLayout(getActivity());
        layout.setId((9999 + global_index) * 6);
        /*LinearLayout layout2 = new LinearLayout(getActivity());
        layout.setId((9999 + global_index) * 7);*/

        final ImageButton addSumButton = new ImageButton(getActivity());
        addSumButton.setBackgroundResource(R.drawable.ic_add_goal);
        addSumButton.setId(9999 + global_index);
        addSumButton.setPadding(10, 10, 10, 10);

        addSumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAddSavedMoneyPopup(addSumButton);
                saveCurrentGoals();
            }
        });

       /* final ImageButton deleteGoalButton = new ImageButton(getActivity());
        deleteGoalButton.setBackgroundResource(R.drawable.ic_delete);
        deleteGoalButton.setId((9999 + global_index) * 4);
        deleteGoalButton.setPadding(10, 10, 10, 10);

        deleteGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteGoal(addSumButton);
                saveCurrentGoals();
            }
        });*/

        TextView txt = new TextView(getActivity());
        txt.setText(goalName.toUpperCase() + "                                           " +
                numDays + " DAYS");
        txt.setPadding(10, 10, 10, 10);
        txt.setMinHeight(60);
        txt.setTextSize(18);
        txt.setTextColor(Color.BLUE);
        txt.setBackgroundResource(R.drawable.rounded_corners_pb);
        GradientDrawable drawable = (GradientDrawable) txt.getBackground();
        drawable.setColor(ContextCompat.getColor(getActivity(), R.color.transparent));

        TextView txt2 = new TextView(getActivity());
        txt2.setText("GOAL: " + moneyToSave + " lei                  " +
                "PROGRESS: " + moneySaved + " lei");
        txt2.setPadding(10, 10, 10, 10);
        txt2.setMinHeight(60);
        txt2.setTextSize(18);
        txt2.setTextColor(Color.BLUE);
        txt2.setId((9999 + global_index) * 2);
        txt2.setBackgroundResource(R.drawable.rounded_corners_pb);
        drawable = (GradientDrawable) txt2.getBackground();
        drawable.setColor(ContextCompat.getColor(getActivity(), R.color.transparent));

        TextView emptyUp = new TextView(getActivity());
        emptyUp.setWidth(0);
        emptyUp.setHeight(30);
        emptyUp.setId(2222 + index);

        TextView emptyMiddle = new TextView(getActivity());
        emptyMiddle.setWidth(40);
        emptyMiddle.setHeight(0);
        emptyMiddle.setId(2222 + index + 2);

        TextView emptyMiddle2 = new TextView(getActivity());
        emptyMiddle2.setWidth(40);
        emptyMiddle2.setHeight(0);
        emptyMiddle2.setId(2222 + index + 3);

        TextView emptyDown = new TextView(getActivity());
        emptyDown.setWidth(0);
        emptyDown.setHeight(30);
        emptyDown.setId(2222 + index + 1);

        layout.addView(txt);
        layout.addView(emptyMiddle);
        layout.addView(addSumButton);

        /*layout2.addView(txt2);
        layout2.addView(emptyMiddle2);
        layout2.addView(deleteGoalButton);*/

        linearLayout.addView(emptyUp);
        linearLayout.addView(layout);
        linearLayout.addView(pb);
        linearLayout.addView(txt2);
        linearLayout.addView(emptyDown);

        global_index++;
        return addSumButton;
    }

    public float getProgress(Float totalAllocatedMoney, float moneySaved) {
        return moneySaved / totalAllocatedMoney * 100;
    }


    public void saveCurrentGoals() {
        String data = "";
        for (Goal goal : goals) {
            String line = goal.getNewGoalName() + "_" + goal.getSelectedGoalColor() + "_" +
                    goal.getNumberOfDays() + "_" + goal.getMoneyToSave() + "_"
                    + goal.getMoneySaved() + "_" + goal.getId() + "\n";
            data += line;
        }

        writeToFile(GOALS_FILE, data);
    }

   /* public void deleteGoal(ImageButton button) {
        int index = -1;

        Log.d("CUR", "ID1: " + button.getId());
        for (int i = 0; i < goals.size(); i++) {
            Goal goal = goals.get(i);
            Log.d("CUR", "ID2: " + goal.getId());
            if (goal.getId().equals(button.getId())) {
                index = i;
                break;
            }
        }

        goals.remove(index);
    }*/

    private void loadExistingGoals() {
        String readData = readFromFile(GOALS_FILE);
        if (readData.isEmpty()) {
            Log.d("EMPTY", "EMPTY GOALS");
            return;
        }

        String [] readLines = readData.split("\n");
        for (String line : readLines) {
            if (line.length() == 0) {
                continue;
            }

            String [] splitLine = line.split("_");
            Goal goal = new Goal(splitLine[0], splitLine[1], splitLine[2],
                    Float.parseFloat(splitLine[3]), Float.parseFloat(splitLine[4]));
            goal.setId(splitLine[5]);

            goals.add(goal);
            addGoal(splitLine[0], splitLine[2], Float.parseFloat(splitLine[3]),
                    Float.parseFloat(splitLine[4]), goal.getSelectedGoalColor(),
                    splitLine[5]);
        }
    }

    public void setGlobalIndex() {
        if (goals.size() == 0) {
            global_index = 0;
            return;
        }

        Goal lastGoal = goals.get(goals.size()-1);
        global_index = Integer.parseInt(lastGoal.getId());
        global_index += 1;
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