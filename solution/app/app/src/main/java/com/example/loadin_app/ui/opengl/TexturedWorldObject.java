package com.example.loadin_app.ui.opengl;

import android.opengl.GLES20;

import java.util.stream.Stream;

public abstract class TexturedWorldObject extends  WorldObject {


    public TexturedWorldObject(World world) {
        super(world);
    }

    @Override
    public OpenGLProgram getMyProgram() {
        return myWorld.getTextureViewProgram(); //this will use the texture view
    }



    @Override
    public void draw(float[] view, float[] projection) {
        OpenGLProgram program = myWorld.getTextureViewProgram(); //textured view program
        GLES20.glUseProgram(program.getProgramHandle()); //activate the program

        //calculate model

        float[] postScaleMatrix = processScale();  //scale the object to size
        float[] postTranslationMatrix = processTranslation(postScaleMatrix);  //move the object in the world

        //upload model
        program.setUniformMatrix4fv(postTranslationMatrix, World.TextureCoordinateProgram.U_MODEL); //this is the model scale and transpose info
        //upload view
        program.setUniformMatrix4fv(view, World.TextureCoordinateProgram.U_VIEW);
        //upload projection
        program.setUniformMatrix4fv(projection, World.TextureCoordinateProgram.U_PROJECTION);

        //foreach shape, draw the shapes
        for(IDrawable item: getDrawableShapes().toArray(IDrawable[]::new)){
            item.draw(myWorld);
        }


    }

    public abstract Stream<IDrawable> getDrawableShapes();


}
