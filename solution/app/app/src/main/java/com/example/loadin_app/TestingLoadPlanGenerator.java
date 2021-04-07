package com.example.loadin_app;

import com.example.loadin_app.ui.opengl.Box;
import com.example.loadin_app.ui.opengl.Truck;
import com.example.loadin_app.ui.opengl.Vector;
import com.example.loadin_app.ui.opengl.World;

public class TestingLoadPlanGenerator
{
    public static LoadPlan GenerateBasicSampleLoadPlan()
    {
        //World sampleWorld = new World();
        Truck t = new Truck();

        t.place(new Vector(2f*12f, 0f, 2f*12f));


        LoadPlan sampleLoadPlan = new LoadPlan(t);

        float boxSize = 24f;

        for(int lengthIndex = (int) (sampleLoadPlan.GetTruck().getLengthInches()/boxSize ); lengthIndex >= 0 ; lengthIndex--)
        {
            for(int heightIndex = 0 ; heightIndex < sampleLoadPlan.GetTruck().getHeightInches()/boxSize ; heightIndex++)
            {
                for(int widthIndex = (int) (sampleLoadPlan.GetTruck().getWidthInches()/boxSize) ; widthIndex >= 0  ; widthIndex--)
                {
                    Box newBox = new Box(boxSize,boxSize,boxSize);

                    newBox.setDestination(    new Vector(widthIndex*boxSize - boxSize, heightIndex*boxSize, lengthIndex*boxSize - boxSize));
                    sampleLoadPlan.AddBox(newBox);
                    newBox.setVisible(false);
                }
            }
        }

        return sampleLoadPlan;
    }

    public static LoadPlan GenerateOneBigBox(World theWorld)
    {
        Truck t = new Truck();
        t.setMyWorld(theWorld);
        t.place(new Vector(2f*12f, 0f, 2f*12f));

        LoadPlan sampleLoadPlan = new LoadPlan(t);

        Box newBox = new Box(t.getWidthInches(),t.getHeightInches(),t.getLengthInches());
        newBox.setMyWorld(theWorld);
        newBox.setDestination(   new Vector(0f, 0, 0f));
        newBox.setVisible(false);
        sampleLoadPlan.AddBox(newBox);

        return sampleLoadPlan;
    }
}
