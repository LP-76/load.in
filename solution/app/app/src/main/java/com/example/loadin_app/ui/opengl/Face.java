package com.example.loadin_app.ui.opengl;

import android.opengl.GLES20;

import com.example.loadin_app.ui.opengl.programs.IColorable;
import com.example.loadin_app.ui.opengl.programs.IPlaceable;
import com.example.loadin_app.ui.opengl.programs.ITexturable;
import com.example.loadin_app.ui.opengl.programs.OpenGLProgram;
import com.example.loadin_app.ui.opengl.programs.OpenGLVariableHolder;

import java.util.Arrays;
import java.util.stream.Stream;

public class Face extends Shape implements IColorable, ITexturable {
    private TexturedTriangle[] triangles;
    private Texture texture;

    private OpenGLVariableHolder positions;
    private OpenGLVariableHolder colors;
    private OpenGLVariableHolder textureCoordinates;

    public Stream<TexturedTriangle> getTriangles() {
        return Arrays.stream(triangles);
    }

    public Texture getTexture() {
        return texture;
    }

    @Override
    public OpenGLVariableHolder getTextureCoordiantes() {
        return textureCoordinates;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }



    public Face(TexturedTriangle[] triangles, IPlaceable parent){
        super(parent);
        this.triangles = triangles;

        colors = new OpenGLVariableHolder(
                Arrays.stream(triangles).flatMap(i -> i.getColors()),
                4
        );

        refreshPositions();
        refreshTextureCoordinates();
    }

    public void refreshTextureCoordinates(){
        textureCoordinates = new OpenGLVariableHolder(
                Arrays.stream(triangles).flatMap(i -> i.getTextureCoordinates()),
                2
        );
    }
    public void refreshPositions(){
        positions = new OpenGLVariableHolder(
                Arrays.stream(triangles).flatMap(i -> i.getCoordinates()),
                3
        );
    }



    @Override
    public void draw(World worldContext, float[] view, float[] projection) {
        worldContext.getTextureViewProgram().render(this, view, projection);
    }

    @Override
    public OpenGLVariableHolder getColors() {
        return colors;
    }

    @Override
    public OpenGLVariableHolder getPositions() {
        return positions;
    }
}
