package com.example.loadin_app;

import com.example.loadin_app.ui.opengl.Vector;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class EmptySpaceDefragmenterTester
{
    @Test
    public void TestDefragmentX()
    {
        ArrayList<EmptySpace> spaces = new ArrayList<EmptySpace>();

        spaces.add(new EmptySpace(1,1,1, new Vector(0,0,0)));
        spaces.add(new EmptySpace(1,1,1, new Vector(1,0,0)));

        ArrayList<EmptySpace> answer = EmptySpaceDefragmenter.Defragment(spaces);
        Assert.assertTrue(answer.size() == 1);
        Assert.assertTrue(answer.get(0).equals(new EmptySpace(1,2,1,new Vector(0,0,0))));
    }

    @Test
    public void TestDefragmentY()
    {
        ArrayList<EmptySpace> spaces = new ArrayList<EmptySpace>();

        spaces.add(new EmptySpace(1,1,1, new Vector(0,0,0)));
        spaces.add(new EmptySpace(1,1,1, new Vector(0,1,0)));

        ArrayList<EmptySpace> answer = EmptySpaceDefragmenter.Defragment(spaces);
        Assert.assertTrue(answer.size() == 1);
        Assert.assertTrue(answer.get(0).equals(new EmptySpace(1,1,2,new Vector(0,0,0))));
    }

    @Test
    public void TestDefragmentZ()
    {
        ArrayList<EmptySpace> spaces = new ArrayList<EmptySpace>();

        spaces.add(new EmptySpace(1,1,1, new Vector(0,0,0)));
        spaces.add(new EmptySpace(1,1,1, new Vector(0,0,1)));

        ArrayList<EmptySpace> answer = EmptySpaceDefragmenter.Defragment(spaces);
        Assert.assertTrue(answer.size() == 1);
        Assert.assertTrue(answer.get(0).equals(new EmptySpace(2,1,1,new Vector(0,0,0))));
    }

    @Test
    public void TestDefragmentXDifferentWidths()
    {
        ArrayList<EmptySpace> spaces = new ArrayList<EmptySpace>();

        spaces.add(new EmptySpace(1,33,1, new Vector(0,0,0)));
        spaces.add(new EmptySpace(1,1,1, new Vector(33,0,0)));

        ArrayList<EmptySpace> answer = EmptySpaceDefragmenter.Defragment(spaces);
        Assert.assertTrue(answer.size() == 1);
        Assert.assertTrue(answer.get(0).equals(new EmptySpace(1,34,1,new Vector(0,0,0))));
    }

    @Test
    public void TestDefragmentYDifferentHeights()
    {
        ArrayList<EmptySpace> spaces = new ArrayList<EmptySpace>();

        spaces.add(new EmptySpace(1,1,33, new Vector(0,0,0)));
        spaces.add(new EmptySpace(1,1,1, new Vector(0,33,0)));

        ArrayList<EmptySpace> answer = EmptySpaceDefragmenter.Defragment(spaces);
        Assert.assertTrue(answer.size() == 1);
        Assert.assertTrue(answer.get(0).equals(new EmptySpace(1,1,34,new Vector(0,0,0))));
    }

    @Test
    public void TestDefragmentZDifferentLengths()
    {
        ArrayList<EmptySpace> spaces = new ArrayList<EmptySpace>();

        spaces.add(new EmptySpace(33,1,1, new Vector(0,0,0)));
        spaces.add(new EmptySpace(1,1,1, new Vector(0,0,33)));

        ArrayList<EmptySpace> answer = EmptySpaceDefragmenter.Defragment(spaces);
        Assert.assertTrue(answer.size() == 1);
        Assert.assertTrue(answer.get(0).equals(new EmptySpace(34,1,1,new Vector(0,0,0))));
    }

    @Test
    public void TestDefragmentXNonZeroOffsets()
    {
        ArrayList<EmptySpace> spaces = new ArrayList<EmptySpace>();

        spaces.add(new EmptySpace(1,1,1, new Vector(130,130,130)));
        spaces.add(new EmptySpace(1,1,1, new Vector(131,130,130)));

        ArrayList<EmptySpace> answer = EmptySpaceDefragmenter.Defragment(spaces);
        Assert.assertTrue(answer.size() == 1);
        Assert.assertTrue(answer.get(0).equals(new EmptySpace(1,2,1,new Vector(130,130,130))));
    }

    @Test
    public void TestDefragmentYNonZeroOffsets()
    {
        ArrayList<EmptySpace> spaces = new ArrayList<EmptySpace>();

        spaces.add(new EmptySpace(1,1,1, new Vector(130,130,130)));
        spaces.add(new EmptySpace(1,1,1, new Vector(130,131,130)));

        ArrayList<EmptySpace> answer = EmptySpaceDefragmenter.Defragment(spaces);
        Assert.assertTrue(answer.size() == 1);
        Assert.assertTrue(answer.get(0).equals(new EmptySpace(1,1,2,new Vector(130,130,130))));
    }

    @Test
    public void TestDefragmentZNonZeroOffsets()
    {
        ArrayList<EmptySpace> spaces = new ArrayList<EmptySpace>();

        spaces.add(new EmptySpace(1,1,1, new Vector(130,130,130)));
        spaces.add(new EmptySpace(1,1,1, new Vector(130,130,131)));

        ArrayList<EmptySpace> answer = EmptySpaceDefragmenter.Defragment(spaces);
        Assert.assertTrue(answer.size() == 1);
        Assert.assertTrue(answer.get(0).equals(new EmptySpace(2,1,1,new Vector(130,130,130))));
    }

    @Test
    public void TestDefragmentX3Boxes()
    {
        ArrayList<EmptySpace> spaces = new ArrayList<EmptySpace>();

        spaces.add(new EmptySpace(1,1,1, new Vector(0,0,0)));
        spaces.add(new EmptySpace(1,1,1, new Vector(1,0,0)));
        spaces.add(new EmptySpace(1,1,1, new Vector(2,0,0)));

        ArrayList<EmptySpace> answer = EmptySpaceDefragmenter.Defragment(spaces);
        Assert.assertTrue(answer.size() == 1);
        Assert.assertTrue(answer.get(0).equals(new EmptySpace(1,3,1,new Vector(0,0,0))));
    }
    @Test
    public void TestDefragmentY3Boxes()
    {
        ArrayList<EmptySpace> spaces = new ArrayList<EmptySpace>();

        spaces.add(new EmptySpace(1,1,1, new Vector(0,0,0)));
        spaces.add(new EmptySpace(1,1,1, new Vector(0,1,0)));
        spaces.add(new EmptySpace(1,1,1, new Vector(0,2,0)));

        ArrayList<EmptySpace> answer = EmptySpaceDefragmenter.Defragment(spaces);

        Assert.assertTrue(answer.size() == 1);
        Assert.assertTrue(answer.get(0).equals(new EmptySpace(1,1,3,new Vector(0,0,0))));
    }

    @Test
    public void TestDefragmentZ3Boxes()
    {
        ArrayList<EmptySpace> spaces = new ArrayList<EmptySpace>();

        spaces.add(new EmptySpace(1,1,1, new Vector(0,0,0)));
        spaces.add(new EmptySpace(1,1,1, new Vector(0,0,1)));
        spaces.add(new EmptySpace(1,1,1, new Vector(0,0,2)));

        ArrayList<EmptySpace> answer = EmptySpaceDefragmenter.Defragment(spaces);
        Assert.assertTrue(answer.size() == 1);
        Assert.assertTrue(answer.get(0).equals(new EmptySpace(3,1,1,new Vector(0,0,0))));
    }

    @Test
    public void TestDefragmentX1BoxNotNeighboring()
    {
        ArrayList<EmptySpace> spaces = new ArrayList<EmptySpace>();

        spaces.add(new EmptySpace(1,1,1, new Vector(0,0,0)));
        spaces.add(new EmptySpace(1,1,1, new Vector(1,0,0)));
        spaces.add(new EmptySpace(1,1,1, new Vector(33,33,33)));

        ArrayList<EmptySpace> answer = EmptySpaceDefragmenter.Defragment(spaces);
        Assert.assertTrue(answer.size() == 2);
        Assert.assertTrue(answer.contains(new EmptySpace(1,2,1,new Vector(0,0,0))));
        Assert.assertTrue(answer.contains(new EmptySpace(1,1,1, new Vector(33,33,33))));
    }

    @Test
    public void TestDefragmentY1BoxNotNeighboring()
    {
        ArrayList<EmptySpace> spaces = new ArrayList<EmptySpace>();

        spaces.add(new EmptySpace(1,1,1, new Vector(0,0,0)));
        spaces.add(new EmptySpace(1,1,1, new Vector(0,1,0)));
        spaces.add(new EmptySpace(1,1,1, new Vector(33,33,33)));

        ArrayList<EmptySpace> answer = EmptySpaceDefragmenter.Defragment(spaces);
        Assert.assertTrue(answer.size() == 2);
        Assert.assertTrue(answer.contains(new EmptySpace(1,1,1, new Vector(33,33,33))));
        Assert.assertTrue(answer.contains(new EmptySpace(1,1,2,new Vector(0,0,0))));
    }

    @Test
    public void TestDefragmentZ1BoxNotNeighboring()
    {
        ArrayList<EmptySpace> spaces = new ArrayList<EmptySpace>();

        spaces.add(new EmptySpace(1,1,1, new Vector(0,0,0)));
        spaces.add(new EmptySpace(1,1,1, new Vector(0,0,1)));
        spaces.add(new EmptySpace(1,1,1, new Vector(33,33,33)));

        ArrayList<EmptySpace> answer = EmptySpaceDefragmenter.Defragment(spaces);
        Assert.assertTrue(answer.size() == 2);
        Assert.assertTrue(answer.contains(new EmptySpace(1,1,1, new Vector(33,33,33))));
        Assert.assertTrue(answer.contains(new EmptySpace(2,1,1,new Vector(0,0,0))));
    }

    @Test
    public void TestDefragmentComplex()
    {
        ArrayList<EmptySpace> spaces = new ArrayList<EmptySpace>();

        spaces.add(new EmptySpace(1,1,1, new Vector(0,0,0)));
        spaces.add(new EmptySpace(1,1,1, new Vector(0,1,0)));
        spaces.add(new EmptySpace(1,1,1, new Vector(0,0,1)));
        spaces.add(new EmptySpace(1,1,1, new Vector(0,1,1)));
        spaces.add(new EmptySpace(2,1,2, new Vector(1,0,0)));

        ArrayList<EmptySpace> answer = EmptySpaceDefragmenter.Defragment(spaces);
        Assert.assertTrue(answer.size() == 1);
        Assert.assertTrue(answer.get(0).equals(new EmptySpace(2,2,2,new Vector(0,0,0))));
    }
}
