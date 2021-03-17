package com.example.loadin_app;

import com.example.loadin_app.ui.opengl.Vector;

import org.junit.Assert;
import org.junit.Test;

public class EmptySpaceNeighborTester
{
    //"isNeighborInX" Tests
    @Test
    public void TestSpaceLeftOfOther()
    {
        EmptySpace space = new EmptySpace(1,1,1, new Vector(0,0,0 ));
        EmptySpace other = new EmptySpace(1,333,1, new Vector(1,0,0 ));

        Assert.assertTrue(space.isNeighborInXandSameHeightAndLength(other));
    }

    @Test
    public void TestSpaceRightOfOther()
    {
        EmptySpace space = new EmptySpace(1,333,1, new Vector(0,0,0 ));
        EmptySpace other = new EmptySpace(1,1,1, new Vector(333,0,0 ));

        Assert.assertTrue(space.isNeighborInXandSameHeightAndLength(other));
    }

    @Test
    public void TestSpaceNotDirectlyToLeftOfOther()
    {
        EmptySpace space = new EmptySpace(1,1,1, new Vector(0,0,0 ));
        EmptySpace other = new EmptySpace(1,333,1, new Vector(1,13,13 ));

        Assert.assertFalse(space.isNeighborInXandSameHeightAndLength(other));
    }

    @Test
    public void TestSpaceNotDirectlyToRightOfOther()
    {
        EmptySpace space = new EmptySpace(1,333,1, new Vector(0,0,0 ));
        EmptySpace other = new EmptySpace(1,1,1, new Vector(333,13,13 ));

        Assert.assertFalse(space.isNeighborInXandSameHeightAndLength(other));
    }

    @Test
    public void TestSpaceToLeftOfOtherButDifferentSize()
    {
        EmptySpace space = new EmptySpace(1,1,1, new Vector(0,0,0 ));
        EmptySpace other = new EmptySpace(2,333,2, new Vector(1,0,0 ));

        Assert.assertFalse(space.isNeighborInXandSameHeightAndLength(other));
    }

    @Test
    public void TestSpaceToRightOfOtherButDifferentSize()
    {
        EmptySpace space = new EmptySpace(1,333,1, new Vector(0,0,0 ));
        EmptySpace other = new EmptySpace(2,1,2, new Vector(333,0,0 ));

        Assert.assertFalse(space.isNeighborInXandSameHeightAndLength(other));
    }

    @Test
    public void TestSpaceToLeftOfOtherButSpaceInBetween()
    {
        EmptySpace space = new EmptySpace(1,1,1, new Vector(0,0,0 ));
        EmptySpace other = new EmptySpace(1,333,1, new Vector(2,0,0 ));

        Assert.assertFalse(space.isNeighborInXandSameHeightAndLength(other));
    }

    @Test
    public void TestSpaceToRightOfOtherButSpaceInBetween()
    {
        EmptySpace space = new EmptySpace(1,333,1, new Vector(0,0,0 ));
        EmptySpace other = new EmptySpace(1,1,1, new Vector(340,0,0 ));

        Assert.assertFalse(space.isNeighborInXandSameHeightAndLength(other));
    }

    @Test
    public void TestSpaceToRightOfOtherButWrongInEveryWay()
    {
        EmptySpace space = new EmptySpace(1,1,1, new Vector(0,0,0 ));
        EmptySpace other = new EmptySpace(2,333,2, new Vector(4,2,20 ));

        Assert.assertFalse(space.isNeighborInXandSameHeightAndLength(other));
    }

    //"isNeighborInY" Tests
    @Test
    public void TestSpaceAboveOther()
    {
        EmptySpace space = new EmptySpace(1,1,1, new Vector(0,0,0 ));
        EmptySpace other = new EmptySpace(1,1,333, new Vector(0,1,0 ));

        Assert.assertTrue(space.isNeighBorInYAboveAndSameWidthAndLength(other));
    }

    @Test
    public void TestSpaceBelowOther()
    {
        EmptySpace space = new EmptySpace(1,1,333, new Vector(0,0,0 ));
        EmptySpace other = new EmptySpace(1,1,1, new Vector(0,333,0 ));

        Assert.assertTrue(space.isNeighBorInYAboveAndSameWidthAndLength(other));
    }

    @Test
    public void TestSpaceNotDirectlyAboveOther()
    {
        EmptySpace space = new EmptySpace(1,1,1, new Vector(0,0,0 ));
        EmptySpace other = new EmptySpace(1,1,333, new Vector(13,1,13 ));

        Assert.assertFalse(space.isNeighBorInYAboveAndSameWidthAndLength(other));
    }

    @Test
    public void TestSpaceNotDirectlyBelowOther()
    {
        EmptySpace space = new EmptySpace(1,1,333, new Vector(90,0,47 ));
        EmptySpace other = new EmptySpace(1,1,1, new Vector(0,333,0 ));

        Assert.assertFalse(space.isNeighBorInYAboveAndSameWidthAndLength(other));
    }

    @Test
    public void TestSpaceAboveOtherButDifferentSize()
    {
        EmptySpace space = new EmptySpace(1,1,1, new Vector(0,0,0 ));
        EmptySpace other = new EmptySpace(2,2,333, new Vector(0,1,0 ));

        Assert.assertFalse(space.isNeighBorInYAboveAndSameWidthAndLength(other));
    }

    @Test
    public void TestSpaceBelowOtherButDifferentSize()
    {
        EmptySpace space = new EmptySpace(1,1,333, new Vector(0,0,0 ));
        EmptySpace other = new EmptySpace(2,2,1, new Vector(0,333,0 ));

        Assert.assertFalse(space.isNeighBorInYAboveAndSameWidthAndLength(other));
    }

    @Test
    public void TestSpaceAboveOtherButSpaceInBetween()
    {
        EmptySpace space = new EmptySpace(1,1,1, new Vector(0,0,0 ));
        EmptySpace other = new EmptySpace(1,1,333, new Vector(0,2,0 ));

        Assert.assertFalse(space.isNeighBorInYAboveAndSameWidthAndLength(other));
    }

    @Test
    public void TestSpaceBelowOtherButSpaceInBetween()
    {
        EmptySpace space = new EmptySpace(1,1,333, new Vector(0,0,0 ));
        EmptySpace other = new EmptySpace(1,1,1, new Vector(0,339,0 ));

        Assert.assertFalse(space.isNeighBorInYAboveAndSameWidthAndLength(other));
    }

    @Test
    public void TestSpaceAboveOtherButWrongInEveryWay()
    {
        EmptySpace space = new EmptySpace(1,1,1, new Vector(0,0,0 ));
        EmptySpace other = new EmptySpace(2,2,333, new Vector(33,2,99 ));

        Assert.assertFalse(space.isNeighBorInYAboveAndSameWidthAndLength(other));
    }

    //"isNeighborInZ" Tests
    @Test
    public void TestSpaceBehindOther()
    {
        EmptySpace space = new EmptySpace(1,1,1, new Vector(0,0,0 ));
        EmptySpace other = new EmptySpace(333,1,1, new Vector(0,0,1 ));

        Assert.assertTrue(space.isNeighborInZandSameHeightAndWidth(other));
    }

    @Test
    public void TestSpaceInFrontOfOther()
    {
        EmptySpace space = new EmptySpace(333,1,1, new Vector(0,0,0 ));
        EmptySpace other = new EmptySpace(1,1,1, new Vector(0,0,333 ));

        Assert.assertTrue(space.isNeighborInZandSameHeightAndWidth(other));
    }

    @Test
    public void TestSpaceNotDirectlyBehindOther()
    {
        EmptySpace space = new EmptySpace(1,1,1, new Vector(0,0,0 ));
        EmptySpace other = new EmptySpace(333,1,1, new Vector(769 ,40,1 ));

        Assert.assertFalse(space.isNeighborInZandSameHeightAndWidth(other));
    }

    @Test
    public void TestSpaceNotDirectlyInFrontOfOther()
    {
        EmptySpace space = new EmptySpace(333,1,1, new Vector(0,0,0 ));
        EmptySpace other = new EmptySpace(1,1,1, new Vector(443,443,333 ));

        Assert.assertFalse(space.isNeighborInZandSameHeightAndWidth(other));
    }

    @Test
    public void TestSpaceBehindOtherButDifferentSize()
    {
        EmptySpace space = new EmptySpace(1,1,1, new Vector(0,0,0 ));
        EmptySpace other = new EmptySpace(333,2,2, new Vector(0,0,1 ));

        Assert.assertFalse(space.isNeighborInZandSameHeightAndWidth(other));
    }

    @Test
    public void TestSpaceInFrontOfOtherButDifferentSize()
    {
        EmptySpace space = new EmptySpace(333,1,1, new Vector(0,0,0 ));
        EmptySpace other = new EmptySpace(1,2,2, new Vector(0,0,333 ));

        Assert.assertFalse(space.isNeighborInZandSameHeightAndWidth(other));
    }

    @Test
    public void TestSpaceBehindOtherButSpaceInBetween()
    {
        EmptySpace space = new EmptySpace(1,1,1, new Vector(0,0,0 ));
        EmptySpace other = new EmptySpace(333,1,1, new Vector(0,0,2 ));

        Assert.assertFalse(space.isNeighborInZandSameHeightAndWidth(other));
    }

    @Test
    public void TestSpaceInFrontOfOtherButSpaceInBetween()
    {
        EmptySpace space = new EmptySpace(333,1,1, new Vector(0,0,0 ));
        EmptySpace other = new EmptySpace(1,1,1, new Vector(0,0,9999 ));

        Assert.assertFalse(space.isNeighborInZandSameHeightAndWidth(other));
    }

    @Test
    public void TestSpaceBehindOtherButWrongInEveryWay()
    {
        EmptySpace space = new EmptySpace(1,1,1, new Vector(0,0,0 ));
        EmptySpace other = new EmptySpace(333,333,44448, new Vector(27,-411,99 ));

        Assert.assertFalse(space.isNeighborInZandSameHeightAndWidth(other));
    }

    //never let it be said that i wrote no tests
    //-jason
}
