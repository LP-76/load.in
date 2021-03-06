package com.example.loadin_app;

public class LoadStatistics //this class represents our criteria for chosing where a box should go
{
    //the information necessary to describe how to find this space
    int loadIndex;
    int emptySpaceIndex;

    //the criteria we will use to determine if this space is suitable

    int numberOfDimensionsThatMatchBox = 0; //ideally we want to find a space that perfectly matches the box
    float emptyAreaOfLoad = 0; //ideally we want to choose a space that finishes an existing load

    public LoadStatistics(int input_LoadIndex, int input_EmptySpaceIndex, int input_NumberOfMatchingDimensions, float input_EmptyAreaOfLoad )
    {
        loadIndex = input_LoadIndex;
        emptySpaceIndex = input_EmptySpaceIndex;
        numberOfDimensionsThatMatchBox = input_NumberOfMatchingDimensions;
        emptyAreaOfLoad = input_EmptyAreaOfLoad;
    }

    public int GetNumberOfMatchingDimensions()
    {
        return numberOfDimensionsThatMatchBox;
    }

    public float GetEmptyArea()
    {
        return emptyAreaOfLoad;
    }

    public int GetLoadIndex()
    {
        return loadIndex;
    }

    public int GetEmptySpaceIndex()
    {
        return emptySpaceIndex;
    }

}
