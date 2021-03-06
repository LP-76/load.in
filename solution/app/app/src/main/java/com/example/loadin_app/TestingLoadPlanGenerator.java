package com.example.loadin_app;

import com.example.loadin_app.ui.opengl.Box;
import com.example.loadin_app.ui.opengl.Truck;
import com.example.loadin_app.ui.opengl.Vector;
import com.example.loadin_app.ui.opengl.World;

public class TestingLoadPlanGenerator
{
    public static LoadPlan GenerateBasicSampleLoadPlan(World theWorld)
    {
        //World sampleWorld = new World();
        Truck t = new Truck(theWorld);
        t.place(new Vector(2f*12f, 0f, 2f*12f));


        LoadPlan sampleLoadPlan = new LoadPlan(t);


        for(int lengthIndex = (int) sampleLoadPlan.GetTruck().getLengthInches()/12 ; lengthIndex >= 0 ; lengthIndex--)
        {
            for(int heightIndex = 0 ; heightIndex < sampleLoadPlan.GetTruck().getHeightInches()/12 ; heightIndex++)
            {
                for(int widthIndex = (int) sampleLoadPlan.GetTruck().getWidthInches()/12 ; widthIndex >= 0  ; widthIndex--)
                {
                    Box newBox = new Box(12,12,12,theWorld);
                    newBox.setDestination( t.getWorldOffset().add(   new Vector(widthIndex*12 - 12f, heightIndex*12, lengthIndex*12 - 12f)));
                    sampleLoadPlan.AddBox(newBox);
                    newBox.setVisible(false);
                }
            }
        }

        return sampleLoadPlan;
    }

    public static LoadPlan GenerateOneBigBox(World theWorld)
    {
        Truck t = new Truck(theWorld);
        t.place(new Vector(2f*12f, 0f, 2f*12f));

        LoadPlan sampleLoadPlan = new LoadPlan(t);

        Box newBox = new Box(t.getWidthInches(),t.getHeightInches(),t.getLengthInches(),theWorld);
        newBox.setDestination( t.getWorldOffset().add(   new Vector(0f, 0, 0f)));
        newBox.setVisible(false);
        sampleLoadPlan.AddBox(newBox);

        return sampleLoadPlan;
    }
}
