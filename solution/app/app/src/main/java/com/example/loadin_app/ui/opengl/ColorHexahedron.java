package com.example.loadin_app.ui.opengl;

import com.example.loadin_app.ui.opengl.programs.IColorable;
import com.example.loadin_app.ui.opengl.programs.IPlaceable;
import com.example.loadin_app.ui.opengl.programs.OpenGLVariableHolder;

import java.util.Arrays;
import java.util.stream.Stream;

public class ColorHexahedron extends Hexahedron implements IColorable {

    private Color color;
    private Triangle[] mTriangles;
    public Color getColor() {
        return color;
    }

    OpenGLVariableHolder positions;
    OpenGLVariableHolder colors;

    public ColorHexahedron(float width, float height, float length, Color defaultColor, IPlaceable parent){
        super(width, height, length, parent);

        color = defaultColor.adjust(1.50f);

        //IMPORTANT, the Z axis is considered horizontal and Y is considered the up direction

        float x = width;
        float z = length;
        float y = height;

        Vector p1 = new Vector(0, 0, 0, color);
        Vector p2 = new Vector(x, 0, 0, color.adjust(0.95f));
        Vector p3 = new Vector(x, 0, z, color.adjust(0.85f));
        Vector p4 = new Vector(0, 0, z,  color.adjust(0.75f));
        Vector p5 = new Vector(0, y, 0, color.adjust(0.65f));
        Vector p6 = new Vector(x, y, 0f, color.adjust(0.55f));
        Vector p7 = new Vector(x, y, z, color.adjust(0.45f));
        Vector p8 = new Vector(0, y, z,color.adjust(0.35f));


        //6 sides
        //2 triangles per side
        mTriangles = new Triangle[]{
                //base
                new Triangle(p1, p4, p3),
                new Triangle(p2, p1, p3),
//                //top
                new Triangle(p5, p7, p6),
                new Triangle(p5, p8, p7),
//                //front
                new Triangle(p1, p6, p2),
                new Triangle(p1, p5, p6),
//                //back
                new Triangle(p3, p8, p4),
                new Triangle(p3, p7, p8),
//                //right
                new Triangle(p2, p7, p3),
                new Triangle(p2, p6, p7),
//                //left
                new Triangle(p4, p5, p1),
                new Triangle(p4, p8, p5),

        };


        positions = new OpenGLVariableHolder(
               Arrays.stream(mTriangles).flatMap(i -> i.getCoordinates()), 3
        );

        colors = new OpenGLVariableHolder(
                Arrays.stream(mTriangles).flatMap(i -> i.getColors()), 4
        );

    }


    @Override
    public void move(Vector direction) {
        for(Triangle t : mTriangles){
            t.move(direction);
        }
    }





    @Override
    public OpenGLVariableHolder getPositions() {
       return positions;
    }

    @Override
    public void draw(World worldContext, float[] view, float[] projection) {
        worldContext.getLightViewProgram().render(this, view, projection);
    }

    @Override
    public OpenGLVariableHolder getColors() {
        return colors;
    }
}
