package com.example.loadin_app;

import com.example.loadin_app.ui.opengl.Box;
import com.example.loadin_app.ui.opengl.Vector;

import org.junit.Assert;
import org.junit.Test;

public class BoxTest {
    @Test
    public void testAbove(){
        Box a = new Box(24, 24, 24);
        Box b = new Box(24, 24, 24);

        a.setDestination(new Vector(0,24, 0));
        b.setDestination(new Vector(0,0,0));

        Assert.assertTrue(a.isAbove(b));

        Assert.assertFalse(b.isAbove(a));

        Box c = new Box(12, 12, 24);
        c.setDestination(new Vector(0,24, 0));

        Assert.assertTrue(c.isAbove(b));

        Assert.assertFalse(b.isAbove(c));

    }

    @Test
    public void comparator(){
        Box a = new Box(24, 24, 24);
        Box b = new Box(24, 24, 24);
        Box c = new Box(24, 24, 24);
        a.setDestination(new Vector(0,24, 0));
        b.setDestination(new Vector(0,0,0));
        c.setDestination(new Vector(0,48,0));

        Load l = new Load(new EmptySpace(24, 24, 24, new Vector(24, 24, 24)));

        int result = l.compare(a, b);

        Assert.assertTrue(1 == result);

        result = l.compare(b,a);
        Assert.assertTrue(-1 == result);

        result = l.compare(a, c);
        Assert.assertTrue(-1 == result);

        result = l.compare(b, c);
        Assert.assertTrue(-1 == result);
    }
}
