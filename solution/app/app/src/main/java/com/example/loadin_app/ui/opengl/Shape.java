package com.example.loadin_app.ui.opengl;

import com.example.loadin_app.ui.opengl.programs.IPlaceable;

import java.util.stream.Stream;

public abstract class Shape implements IDrawable, IPlaceable {

    protected IPlaceable parent;

    protected Vector parentOffset;


    public Shape(IPlaceable parent){
        this.parent =parent;
        parentOffset = new Vector(0,0,0f); //by default, we are right there with parent coordinates

    }

   public void move(Vector direction){
        parentOffset = direction;
   }

    @Override
    public Vector getWorldOffset() {
        return parent.getWorldOffset().add(parentOffset);
    }

}
