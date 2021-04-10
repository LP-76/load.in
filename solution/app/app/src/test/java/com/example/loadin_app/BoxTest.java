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


        Box d = new Box(48, 24, 24);
        Box e = new Box(24, 24, 24);
        Box f = new Box(24, 24, 24);
        Box g = new Box(24,24, 24);

        e.setDestination(new Vector(0,0,0));
        f.setDestination(new Vector(24, 0, 0));
        d.setDestination(new Vector(0, 24, 0));;

        g.setDestination(new Vector(12, 0, 0));

        Assert.assertTrue(d.isAbove(e));
        Assert.assertTrue(d.isAbove(f));
        Assert.assertTrue(d.isAbove(g));


        Box h = new Box(24, 24, 24);
        Box i = new Box(24, 24, 24);
        h.setDestination(new Vector(0,0,0));
        i.setDestination(new Vector(24, 24, 0));

        Assert.assertTrue(!i.isAbove(h));


    }

    @Test
    public void testInFrontOf(){
        Box a = new Box(24, 24, 24);
        Box b = new Box(24, 24, 24);

        a.setDestination(new Vector(0, 0, 0));
        b.setDestination(new Vector(0, 0, 0));
        Assert.assertTrue(!a.isInFrontOf(b));
        Assert.assertTrue(!b.isInFrontOf(a));

        a.setDestination(new Vector(24, 24, 0));  //x and y do not affect the z
        Assert.assertTrue(!a.isInFrontOf(b));
        Assert.assertTrue(!b.isInFrontOf(a));

        a.setDestination(new Vector(0, 0, 16));  //we're still in the same row technically
        Assert.assertTrue(!a.isInFrontOf(b));
        Assert.assertTrue(!b.isInFrontOf(a));

        a.setDestination(new Vector(0, 0, 25));  //we're still in the same row technically
        Assert.assertTrue(!a.isInFrontOf(b));
        Assert.assertTrue(b.isInFrontOf(a));



    }

    @Test
    public void testInSameRow(){

        Box a = new Box(24, 24, 24);
        Box b = new Box(24, 24, 24);

        a.setDestination(new Vector(0, 0, 0));
        b.setDestination(new Vector(0, 0, 0));
        Assert.assertTrue(a.isInSameRowAs(b));
        Assert.assertTrue(b.isInSameRowAs(a));

        a.setDestination(new Vector(0, 24, 0));  //y does not affect
        Assert.assertTrue(a.isInSameRowAs(b));
        Assert.assertTrue(b.isInSameRowAs(a));

        a.setDestination(new Vector(45, 0, 16));  //we're still in the same row technically because x shouldn't matter
        Assert.assertTrue(a.isInSameRowAs(b));
        Assert.assertTrue(b.isInSameRowAs(a));

        a.setDestination(new Vector(0, 0, 25));  //now we've moved in front of
        Assert.assertTrue(b.isInFrontOf(a));
        Assert.assertTrue(!a.isInSameRowAs(b));
        Assert.assertTrue(!b.isInSameRowAs(a));
    }


    @Test
    public void comparator(){
        Box a = new Box(24, 24, 24);
        Box b = new Box(24, 24, 24);
        Box c = new Box(24, 24, 24);
        Box d = new Box(24, 24, 24);
        Box e = new Box(24, 24, 24);
        Box f = new Box(24, 24, 24);
        a.setDestination(new Vector(0,24, 0));
        b.setDestination(new Vector(0,0,0));
        c.setDestination(new Vector(0,48,0));
        d.setDestination(new Vector(24, 0, 0));
        e.setDestination(new Vector(24, 24, 0));
        f.setDestination(new Vector(24, 24, 24));

        Load l = new Load(new EmptySpace(24, 24, 24, new Vector(24, 24, 24)));

        int AFTER = 1;
        int BEFORE = -1;



        Assert.assertTrue(AFTER == l.compare(a, b));

        Assert.assertTrue(BEFORE == l.compare(b,a));

        Assert.assertTrue(BEFORE == l.compare(a, c));

        Assert.assertTrue(BEFORE == l.compare(b, c));

        Assert.assertTrue(AFTER == l.compare(a, d));
        Assert.assertTrue(AFTER == l.compare(b, d));
        Assert.assertTrue(AFTER == l.compare(c, d));

        Assert.assertTrue(AFTER == l.compare(a, e));
        Assert.assertTrue(AFTER == l.compare(b, e));
        Assert.assertTrue(AFTER == l.compare(c, e));

        Assert.assertTrue(BEFORE == l.compare(d, e));

        Assert.assertTrue(AFTER == l.compare(a, f));
        Assert.assertTrue(AFTER == l.compare(b, f));
        Assert.assertTrue(AFTER == l.compare(c, f));

        Assert.assertTrue(AFTER == l.compare(d, f));
        Assert.assertTrue(AFTER == l.compare(e, f));

        Assert.assertTrue(BEFORE == l.compare(f, a));
        Assert.assertTrue(BEFORE == l.compare(f, b));
        Assert.assertTrue(BEFORE == l.compare(f, c));

        Assert.assertTrue(BEFORE == l.compare(f, d));
        Assert.assertTrue(BEFORE == l.compare(f, e));

    }
}
