package com.example.loadin_app.data.model;

import com.example.loadin_app.LoadPlan;
import com.example.loadin_app.LoadPlanGenerator;

import odu.edu.loadin.common.MovingTruck;

public class LogisticsResult {

    private MovingTruck movingTruck;
    private LoadPlan loadPlan;
    private float numOfMiles;
    private LoadPlanGenerator lpg;

    public LogisticsResult(MovingTruck movingTruck)
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

    public LoadPlanGenerator getLpg() {
        return lpg;
    }

    public void setLpg(LoadPlanGenerator lpg) {
        this.lpg = lpg;
    }
    public float calculateTotalCost(float baseRentalFee, float totalCostOfDistance)
    {
        return baseRentalFee + totalCostOfDistance;
    }
    public float calculateTotalCostOfDistance(float totalDistance, float costPerMile)
    {
        return totalDistance * costPerMile;
    }
    public float calculateTotalDistance(float numOfMiles, Integer numOfTrips)
    {
        return numOfMiles * numOfTrips;
    }

}
