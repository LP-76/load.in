package com.example.loadin_app;

import com.example.loadin_app.extensions.IExtendedIterator;
import com.example.loadin_app.ui.opengl.Box;
import com.example.loadin_app.ui.opengl.LoadPlanRenderer;
import com.example.loadin_app.ui.opengl.LoadPlanStep;
import com.example.loadin_app.ui.opengl.LoadPlanStepIterator;
import com.example.loadin_app.ui.opengl.World;

import org.junit.Assert;
import org.junit.Test;

import java.util.Stack;

public class LoadPlanDisplayerTests {

    @Test
    public void testLoadPlanStepIterator(){


        LoadPlan lp = TestingLoadPlanGenerator.GenerateBasicSampleLoadPlan();
        LoadPlanStepIterator it = new LoadPlanStepIterator(lp);

        int boxCount = 0;
        int otherCount = 0;




        for(IExtendedIterator<Load> lpit = lp.iterator(); lpit.hasNext();){
            Load l = lpit.next();

            Stack<Box> boxes = new Stack<Box>();

            for(IExtendedIterator<Box> bxit = l.iterator(); bxit.hasNext();){
                Box b = bxit.next();
                boxes.push(b);
                Assert.assertTrue(it.hasNext());
                LoadPlanStep s = it.next();

                Assert.assertTrue(s.currentBox.equals(b));

            }

            Stack<Box> reverseTest = new Stack<Box>();

            while(boxes.size() > 0){
                Box b = boxes.pop();
                reverseTest.push(b);
                Assert.assertTrue(it.hasNext());
                LoadPlanStep s = it.next();

                Assert.assertTrue(b.equals(s.currentBox));
                Assert.assertTrue(!s.loading);
            }
            Assert.assertTrue(!it.hasNext());
            Assert.assertTrue(it.hasPrevious());

            while(reverseTest.size() > 0){

                Box b = reverseTest.pop();

                LoadPlanStep s = it.current();

                Assert.assertTrue(!s.loading);
                Assert.assertTrue(s != null);


                Assert.assertTrue(it.hasPrevious());
                s = it.previous();

            }



        }





    }

}
