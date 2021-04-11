package com.example.loadin_app;


import com.example.loadin_app.ui.opengl.Box;
import com.example.loadin_app.ui.opengl.Vector;

import java.util.ArrayList;

//this class represents an empty space, which will be used in the load plan algorithm to represent some or all of the empty space in the truck.
public class EmptySpace {
    private float length, width, height, volume, lengthWidthArea, lengthHeightArea, widthHeightArea;

    private Vector offset;

    public EmptySpace(float input_length, float input_width, float input_height, Vector input_offset) {
        if (input_offset.getX() < 0 || input_offset.getY() < 0 || input_offset.getZ() < 0) {
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

    public EmptySpace(EmptySpace other)
    {
        //    private float length, width, height, volume, lengthWidthArea, lengthHeightArea, widthHeightArea;
        //
        //    private Vector offset;

        this.length = other.length;
        this.width = other.width;
        this.height = other.height;

        lengthWidthArea = length * width;
        lengthHeightArea = length * height;
        widthHeightArea = width * height;
        volume = length * width * height;

        this.offset = new Vector(other.offset.getX(), other.offset.getY(), other.offset.getZ());


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

    public float GetVolume()
    {
        return length * width * height;
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

    public boolean isNeighborInAnyWay(EmptySpace other)
    {
        return isNeighborInXandSameHeightAndLength(other) || isNeighBorInYAboveAndSameWidthAndLength(other) || isNeighborInZandSameHeightAndWidth(other);
    }


    public void merge(EmptySpace other)
    {
        if(this.isNeighborInXandSameHeightAndLength(other))
        {
            this.width += other.GetWidth();
            this.offset = new Vector(Math.min(this.GetOffset().getX(),other.GetOffset().getX()), this.GetOffset().getY(), this.GetOffset().getZ());
        }
        else if(this.isNeighBorInYAboveAndSameWidthAndLength(other))
        {
            this.height += + other.GetHeight();
            this.offset = new Vector(this.GetOffset().getX(),Math.min(this.GetOffset().getY(),other.GetOffset().getY()), this.GetOffset().getZ());
        }
        else if(this.isNeighborInZandSameHeightAndWidth(other))
        {
            this.length += other.GetLength();
            this.offset = new Vector(this.GetOffset().getX(),this.GetOffset().getY(), Math.min(this.GetOffset().getZ(),other.GetOffset().getZ()));
        }
    }

    public ArrayList<EmptySpace> split(Box box)
    {
        ArrayList<EmptySpace> splitSpaces = new ArrayList<EmptySpace>();

        if(this.length - box.getLength() > 0 && this.width > 0 && this.height > 0 )
            splitSpaces.add(new EmptySpace(this.length - box.getLength(), this.width, this.height, this.offset ));

        if(box.getLength() > 0 && this.width - box.getWidth() > 0 && this.height > 0)
            splitSpaces.add(new EmptySpace(box.getLength(), this.width - box.getWidth(), this.height, new Vector(this.offset.getX(),this.offset.getY(),this.offset.getZ() + (this.length-box.getLength()))));

        if(box.getLength() > 0 && box.getWidth() > 0 && (this.height - box.getHeight()) > 0 )
            splitSpaces.add(new EmptySpace(box.getLength(), box.getWidth(), this.height - box.getHeight(), new Vector(this.offset.getX() + (this.width - box.getWidth()), this.offset.getY() + box.getHeight(), this.offset.getZ() + (this.length - box.getLength()))));

        return splitSpaces;
    }

    public void setBoxInSpace(Box b){
        b.setDestination(new Vector(GetOffset().getX() + (GetWidth() - b.getWidth()), GetOffset().getY(), GetOffset().getZ() + (GetLength() - b.getLength())));
    }

    public boolean canFit(Box box)
    {
        return (this.width >= box.getWidth() ) &&(this.length >= box.getLength() ) && (this.height >= box.getHeight() );
    }

    public int rankFit(Box box){

        int numberOfMatchingDimensions = 0;

        if(box.getWidth() == GetWidth())
            numberOfMatchingDimensions++;

        if(box.getHeight() == GetHeight())
            numberOfMatchingDimensions++;

        if(box.getLength() == GetLength())
            numberOfMatchingDimensions++;

        return numberOfMatchingDimensions;

    }


    @Override
    public boolean equals(Object other)
    {
        if(other.getClass() == getClass())
        {
            EmptySpace otherSpace = (EmptySpace)other;

            return otherSpace.GetHeight() == height
                    && otherSpace.GetWidth() == width
                    && otherSpace.GetLength() == length
                    && otherSpace.GetOffset().getX() == offset.getX()
                    && otherSpace.GetOffset().getY() == offset.getY()
                    && otherSpace.GetOffset().getZ() == offset.getZ();
        }
        else
        {
            return false;
        }
    }
}
