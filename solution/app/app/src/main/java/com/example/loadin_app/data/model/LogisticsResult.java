package com.example.loadin_app.data.model;

import com.example.loadin_app.LoadPlan;

import odu.edu.loadin.common.MovingTruck;

public class LogisticsResult {

    private MovingTruck movingTruck;
    private LoadPlan loadPlan;
    private float numOfMiles;

    LogisticsResult(MovingTruck movingTruck)
    {
        this.movingTruck = movingTruck;
    }

    public MovingTruck getMovingTruck() {
        return movingTruck;
    }

    public void setMovingTruck(MovingTruck movingTruck) {
        this.movingTruck = movingTruck;
    }

    public LoadPlan getLoadPlan() {
        return loadPlan;
    }

    public void setLoadPlan(LoadPlan loadPlan) {
        this.loadPlan = loadPlan;
    }

    public float getNumOfMiles() {
        return numOfMiles;
    }

    public void setNumOfMiles(float numOfMiles) {
        this.numOfMiles = numOfMiles;
    }
}
