package com.example.welcomeactivity;


public class Goal {
    private String selectedGoalColor;
    private String newGoalName;
    private String numberOfDays;
    private float moneyToSave;
    private float moneySaved;
    private String id;

    public Goal() {
        selectedGoalColor = "Default";
        newGoalName = "None";
        numberOfDays = "0";
        moneyToSave = 0.0f;
        moneySaved = 0.0f;
        id = "none";
    }

    public Goal(String goalName, String color, String days, float moneyToSave1, float moneySaved1) {
        selectedGoalColor = color;
        newGoalName = goalName;
        numberOfDays = days;
        moneyToSave = moneyToSave1;
        moneySaved = moneySaved1;
        id = "none";
    }

    public String getSelectedGoalColor() {
        return selectedGoalColor;
    }

    public void setSelectedGoalColor(String selectedGoalColor) {
        this.selectedGoalColor = selectedGoalColor;
    }

    public String getNewGoalName() {
        return newGoalName;
    }

    public void setNewGoalName(String newGoalName) {
        this.newGoalName = newGoalName;
    }

    public String getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(String numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public float getMoneyToSave() {
        return moneyToSave;
    }

    public void setMoneyToSave(float moneyToSave) {
        this.moneyToSave = moneyToSave;
    }

    public float getMoneySaved() {
        return moneySaved;
    }

    public void setMoneySaved(float moneySaved) {
        this.moneySaved = moneySaved;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
