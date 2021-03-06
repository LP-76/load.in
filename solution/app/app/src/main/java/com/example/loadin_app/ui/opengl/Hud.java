package com.example.loadin_app.ui.opengl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.opengl.GLES20;
import android.opengl.Matrix;

import androidx.lifecycle.MutableLiveData;

import java.util.Arrays;
import java.util.stream.Stream;

public class Hud implements IBaseDrawable{
    private TexturedTriangle[] triangles;

    private String message;

    private OpenGLVariableHolder positions;
    private OpenGLVariableHolder textureCoordinates;

    private Texture hudImage;
    private World myWorld;

    public Hud(World world) {
        myWorld = world;


        message = "HELLO WORLD!!";

        Color white = new Color(1f, 1f, 1f, 0f);
        Color red = new Color(1, 0f, 0f, 0f);
        Color green = new Color(0f, 1, 0f, 0f);
        Color blue = new Color(0f, 0f, 1f, 0f);



        Vector bottomLeft = new Vector(-1, -1, 0f, white);
        Vector bottomRight = new Vector(1, -1, 0f, red);
        Vector topLeft = new Vector(-1, 1, 0f, green);
        Vector topRight = new Vector(1, 1, 0f, blue);


        Vector textureOffset = new Vector(1f, 1f, 0f);


         triangles=       new TexturedTriangle[]{
                 new TexturedTriangle(bottomLeft, topLeft, topRight,
                         bottomLeft.add(textureOffset).multiply(0.5f),
                         topLeft.add(textureOffset).multiply(0.5f),
                         topRight.add(textureOffset).multiply(0.5f)
                 ),
                 new TexturedTriangle(bottomLeft, topRight, bottomRight,
                         bottomLeft.add(textureOffset).multiply(0.5f),
                         topRight.add(textureOffset).multiply(0.5f),
                         bottomRight.add(textureOffset).multiply(0.5f))
         };
        refreshHud();
        refreshTextureCoordinates();
        refreshPositions();
    }

    public void refreshTextureCoordinates(){
        textureCoordinates = new OpenGLVariableHolder(
                Arrays.stream(triangles).flatMap(i -> i.getTextureCoordinates()),
                2, World.TextureCoordinateProgram.A_TEX_COORD
        );
    }
    public void refreshPositions(){
        positions = new OpenGLVariableHolder(
                Arrays.stream(triangles).flatMap(i -> i.getCoordinates()),
                3, World.TextureCoordinateProgram.A_POSITION
        );
    }

    private void refreshHud(){
        Bitmap bitmap = Bitmap.createBitmap(512, 512, Bitmap.Config.ARGB_4444);
// get a canvas to paint over the bitmap
        Canvas canvas = new Canvas(bitmap);
        bitmap.eraseColor(android.graphics.Color.argb(0,255,255,255));

        Paint fill = new Paint();
        fill.setARGB(128, 0, 0, 0 );

// Draw the text
        Paint textPaint = new Paint();
        textPaint.setTextSize(12);
        textPaint.setAntiAlias(true);
        textPaint.setARGB(255, 255, 0, 0);
// draw the text centered



        float centerX = canvas.getWidth() /2 ;
        float centerY = canvas.getHeight() /2;


        canvas.rotate(180,centerX, centerY );
        canvas.drawText(message, 200,15, textPaint);
        canvas.drawRect(100,15, 200, 30, fill);


        GLES20.glUseProgram(getMyProgram().getProgramHandle());

        if(hudImage != null)
            hudImage.delete();

        hudImage = new Texture(bitmap, getMyProgram(), World.TextureCoordinateProgram.U_TEXTURE); //load the texture
        bitmap.recycle();
    }




    @Override
    public void draw(float[] view, float[] projection) {
        OpenGLProgram program = getMyProgram(); //hud program
        GLES20.glUseProgram(program.getProgramHandle()); //activate the program

        //calculate model

        float[] model = new float[16];
        Matrix.setIdentityM(model,0);

        //upload model
        program.setUniformMatrix4fv(model, World.TextureCoordinateProgram.U_MODEL); //we're just passing through all coordinates to the renderer
        //upload view
        program.setUniformMatrix4fv(view, World.TextureCoordinateProgram.U_VIEW);
        //upload projection
        program.setUniformMatrix4fv(projection, World.TextureCoordinateProgram.U_PROJECTION);



        program.setVertexAttributePointer(positions, 3*4);  //conduct upload

        //upload texture information
        program.setVertexAttributePointer(textureCoordinates, 2*4);

        //set the texture
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, hudImage.getHandle());  //bind to the handle

        //draw
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, positions.getCount());

        //cleanup
        program.disableVertexAttribute(World.TextureCoordinateProgram.A_TEX_COORD);
        program.disableVertexAttribute(World.TextureCoordinateProgram.A_POSITION);


    }


    public OpenGLProgram getMyProgram() {
        return myWorld.getHudProgram(); //this will use the texture view
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        refreshHud();
    }
}
