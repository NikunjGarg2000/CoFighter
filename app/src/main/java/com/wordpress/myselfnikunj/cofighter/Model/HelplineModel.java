package com.wordpress.myselfnikunj.cofighter.Model;

import android.content.Context;

public class HelplineModel {

    private Context context;
    private String stateName, number;

    public HelplineModel() {
    }

    public HelplineModel(Context context, String stateName, String number) {
        this.context = context;
        this.stateName = stateName;
        this.number = number;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}
