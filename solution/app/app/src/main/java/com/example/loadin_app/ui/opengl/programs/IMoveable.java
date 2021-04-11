package com.example.loadin_app.ui.opengl.programs;

import com.example.loadin_app.ui.opengl.Vector;

public interface IMoveable {
    Vector getWorldOffset();
    void place(Vector v);
}
