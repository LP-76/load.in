package com.example.loadin_app.ui.opengl.programs;

import com.example.loadin_app.ui.opengl.CubeMap;

public interface ICubeMappable {
    OpenGLVariableHolder getCubeTextureDirections();
    CubeMap getMap();
}
