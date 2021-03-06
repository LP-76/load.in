package com.example.loadin_app.ui.opengl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.opengl.GLES20;

import com.example.loadin_app.ui.opengl.programs.ITexturable;
import com.example.loadin_app.ui.opengl.programs.OpenGLProgram;
import com.example.loadin_app.ui.opengl.programs.OpenGLVariableHolder;
import com.example.loadin_app.ui.opengl.programs.TextureCoordinateProgram;

import java.util.Arrays;
import java.util.stream.Stream;

public class HudElement extends Shape implements IDrawable, ITexturable {

    private TexturedTriangle[] triangles;
    private OpenGLVariableHolder positions;
    private OpenGLVariableHolder textureCoordinates;

    private Texture hudImage;

    private String message;

    private World myWorld;

    public HudElement(World world, Hud parent){
        super(parent);

        myWorld = world;
        message = "HELLO WORLD!!";

        Color white = new Color(1f, 1f, 1f, 0f);
        Color red = new Color(1, 0f, 0f, 0f);
        Color green = new Color(0f, 1, 0f, 0f);
        Color blue = new Color(0f, 0f, 1f, 0f);



        Vector bottomLeft = new Vector(0, 0, 0f, white);
        Vector bottomRight = new Vector(2, 0, 0f, red);
        Vector topLeft = new Vector(0, 2, 0f, green);
        Vector topRight = new Vector(2, 2, 0f, blue);





        triangles=       new TexturedTriangle[]{
                new TexturedTriangle(bottomLeft, topLeft, topRight,
                        bottomLeft.multiply(0.5f),
                        topLeft.multiply(0.5f),
                        topRight.multiply(0.5f)
                ),
                new TexturedTriangle(bottomLeft, topRight, bottomRight,
                        bottomLeft.multiply(0.5f),
                        topRight.multiply(0.5f),
                        bottomRight.multiply(0.5f))
        };
        refreshHud();
        refreshTextureCoordinates();
        refreshPositions();
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


        GLES20.glUseProgram(myWorld.getHudProgram().getProgramHandle());

        if(hudImage != null)
            hudImage.delete();

        hudImage = new Texture(bitmap, myWorld.getHudProgram(), TextureCoordinateProgram.U_TEXTURE); //load the texture
        bitmap.recycle();
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
        worldContext.getHudProgram().render(this, view, projection);
    }

    @Override
    public OpenGLVariableHolder getPositions() {
        return positions;
    }

    @Override
    public Texture getTexture() {
        return hudImage;
    }

    @Override
    public OpenGLVariableHolder getTextureCoordiantes() {
        return textureCoordinates;
    }
}
