package com.example.loadin_app;


import com.example.loadin_app.ui.opengl.Vector;

//this class represents an empty space, which will be used in the load plan algorithm to represent some or all of the empty space in the truck.
public class EmptySpace
{
    private float length, width, height, volume, lengthWidthArea, lengthHeightArea, widthHeightArea;

    private Vector offset;

    public EmptySpace(float input_length, float input_width, float input_height, Vector input_offset)
    {
        length = input_length;
        width = input_width;
        height = input_height;
        offset = input_offset;

        lengthWidthArea = length * width;
        lengthHeightArea = length * height;
        widthHeightArea = width * height;
        volume = length * width * height;
    }

    public float GetLength()
    {
        return length;
    }

    public float GetWidth()
    {
        return width;
    }

    public float GetHeight()
    {
        return height;
    }

    public float GetLengthWidthArea()
    {
        return lengthWidthArea;
    }

    public float GetLengthHeightArea()
    {
        return lengthHeightArea;
    }

    public float GetWidthHeightArea()
    {
        return widthHeightArea;
    }

    public Vector GetOffset()
    {
        return offset;
    }

    public void SetOffset(Vector input)
    {
        offset = input;
    }


}
