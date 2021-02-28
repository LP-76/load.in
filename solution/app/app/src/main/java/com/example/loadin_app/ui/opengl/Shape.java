package com.example.loadin_app.ui.opengl;

import java.util.stream.Stream;

public abstract class Shape {


    public Shape(){


    }

    public abstract void move(Vector direction);

    public abstract Stream<Triangle> getTriangles();

}
