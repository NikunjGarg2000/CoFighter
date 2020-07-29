package com.wordpress.myselfnikunj.cofighter.Model;

import android.content.Context;

public class IndianStatesNamesModel {

    private Context context;
    private String name, cases, active, recovered;

    public IndianStatesNamesModel() {
    }

    public IndianStatesNamesModel(Context context, String name, String cases, String active, String recovered) {
        this.context = context;
        this.name = name;
        this.cases = cases;
        this.active = active;
        this.recovered = recovered;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
