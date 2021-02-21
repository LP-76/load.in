package com.example.loadin_app.ui.opengl;

import java.util.stream.Stream;

public class Truck extends WorldObject
{
    private float lengthMeters,widthMeters,heightMeters;
    private float costPerDayDollars, costPerMileDollars;

    public Truck()
    {
        lengthMeters = 5.1308f;
        widthMeters = 2.2098f;
        heightMeters = 2.1844f;
        costPerDayDollars = 39.95f;
        costPerMileDollars = 0.99f;

        //TODO: Make a real constructor that takes real data from somewhere and doesn't hardwire random data from uhaul.com
    }

    @Override
    public Stream<Shape> getShapes() {
        //TODO: BYROOOON FIX IIIIT
        return null;
    }

    public float GetAreaOfTruckMeters()
    {
        return lengthMeters * widthMeters * heightMeters;
    }
}

