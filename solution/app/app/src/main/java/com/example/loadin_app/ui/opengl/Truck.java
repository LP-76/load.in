package com.example.loadin_app.ui.opengl;

import java.util.stream.Stream;

public class Truck extends WorldObject
{
    private float lengthInches,widthInches,heightInches;
    private float costPerDayDollars, costPerMileDollars;

    public Truck()
    {
        super(null); //TODO: replace with an instance of world
        lengthInches = 202f;
        widthInches = 87f;
        heightInches = 86f;
        costPerDayDollars = 39.95f;
        costPerMileDollars = 0.99f;

        //TODO: Make a real constructor that takes real data from somewhere and doesn't hardwire random data from uhaul.com
    }

    @Override
    public Stream<Shape> getShapes()
    {
        //TODO: BYROOOON FIX IIIIT
        return null;
    }

    public float GetAreaOfTruckInches()
    {
        return lengthInches * widthInches * heightInches;
    }
}

