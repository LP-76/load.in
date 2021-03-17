package com.example.loadin_app;


import com.example.loadin_app.ui.opengl.Vector;

//this class represents an empty space, which will be used in the load plan algorithm to represent some or all of the empty space in the truck.
public class EmptySpace
{
    private float length, width, height, volume, lengthWidthArea, lengthHeightArea, widthHeightArea;

    private Vector offset;

    public EmptySpace(float input_length, float input_width, float input_height, Vector input_offset)
    {
        if(input_offset.getX() < 0 || input_offset.getY() < 0 ||input_offset.getZ() < 0)
        {
            System.out.println("WARNING: EmptySpace with negative offset being created: " + input_offset.toString());
        }

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

    //checks whether other space is of the same height and length and directly to the left or right of this space (no empty space in between)
    public boolean isNeighborInXandSameHeightAndLength(EmptySpace other)
    {
        return other.GetHeight() == height
                && other.GetLength() == length
                && ( (other.GetOffset().getX() - width == offset.getX() ) || (other.GetOffset().getX() + other.GetWidth() == offset.getX()) )
                && other.GetOffset().getZ() == offset.getZ()
                && other.GetOffset().getY() == offset.getY();
    }

    //checks whether other space is of the same width and length and directly above or below this space (no empty space in between)
    public boolean isNeighBorInYAboveAndSameWidthAndLength(EmptySpace other)
    {
        return other.GetWidth() == width
                && other.GetLength() == length
                && other.GetOffset().getX() == offset.getX()
                && other.GetOffset().getZ() == offset.getZ()
                && ( (other.GetOffset().getY() - height == offset.getY() ) || (other.GetOffset().getY() + other.GetHeight() == offset.getY()) );
    }

    //checks whether other space is of the same width and height and directly in front or behind this space (no empty space in between)
    public boolean isNeighborInZandSameHeightAndWidth(EmptySpace other)
    {
        return other.GetHeight() == height
                && other.GetWidth() == width
                && other.GetOffset().getX() == offset.getX()
                && ( (other.GetOffset().getZ() - length == offset.getX() ) || (other.GetOffset().getZ() + other.GetLength() == offset.getZ()) )
                && other.GetOffset().getY() == offset.getY();
    }
}
