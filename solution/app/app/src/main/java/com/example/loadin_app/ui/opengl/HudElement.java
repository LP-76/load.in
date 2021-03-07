package com.example.loadin_app.ui.opengl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.opengl.GLES20;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import com.example.loadin_app.ui.opengl.programs.IRotatable;
import com.example.loadin_app.ui.opengl.programs.IScalable;
import com.example.loadin_app.ui.opengl.programs.ITexturable;
import com.example.loadin_app.ui.opengl.programs.OpenGLProgram;
import com.example.loadin_app.ui.opengl.programs.OpenGLVariableHolder;
import com.example.loadin_app.ui.opengl.programs.TextureCoordinateProgram;

import java.util.Arrays;
import java.util.stream.Stream;

public class HudElement extends Shape implements IDrawable, ITexturable, IScalable, IRotatable {

    private TexturedTriangle[] triangles;
    private OpenGLVariableHolder positions;
    private OpenGLVariableHolder textureCoordinates;

    private Texture hudImage;

    private String message;

    private World myWorld;
    private Vector scale;
    private float width;
    private Layout.Alignment textAlign;
    private int textSize;

    float yaw;
    float pitch;


    public HudElement(World world, Hud parent){
        super(parent);

        myWorld = world;
        message = "HELLO WORLD!!";
        textSize = 48;
        scale = new Vector(1, 1, 1);
        textAlign = Layout.Alignment.ALIGN_CENTER;

        Color white = new Color(1f, 1f, 1f, 0f);
        Color red = new Color(1, 0f, 0f, 0f);
        Color green = new Color(0f, 1, 0f, 0f);
        Color blue = new Color(0f, 0f, 1f, 0f);
        yaw = 0;
        pitch = 0;


        Vector bottomLeft = new Vector(0, 0, 0f, white);
        Vector bottomRight = new Vector(1, 0, 0f, red);
        Vector topLeft = new Vector(0, 1, 0f, green);
        Vector topRight = new Vector(1, 1, 0f, blue);
        width = 1f;




        triangles=       new TexturedTriangle[]{
                new TexturedTriangle(bottomLeft, topLeft, topRight,
                        bottomLeft,
                        topLeft,
                        topRight
                ),
                new TexturedTriangle(bottomLeft, topRight, bottomRight,
                        bottomLeft,
                        topRight,
                        bottomRight)
        };
        refreshHud();
        refreshTextureCoordinates();
        refreshPositions();
    }

    private void refreshHud(){
        int desiredWidth = 512;

        //setup text paint
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(textSize);
        textPaint.setAntiAlias(true);
        textPaint.setARGB(255, 255, 255, 255);

        StaticLayout.Builder layoutBuilder = StaticLayout.Builder.obtain(message, 0, message.length(), textPaint,desiredWidth);
        layoutBuilder.setAlignment(textAlign);
        StaticLayout layout = layoutBuilder.build();


        Bitmap bitmap = Bitmap.createBitmap(layout.getWidth(), layout.getHeight(), Bitmap.Config.ARGB_4444);
// get a canvas to paint over the bitmap
        Canvas canvas = new Canvas(bitmap);
        bitmap.eraseColor(android.graphics.Color.argb(0,255,255,255));

        Paint fill = new Paint();
        fill.setARGB(128, 0, 0, 0 );

// Draw the text

// draw the text centered




        float centerX = canvas.getWidth() /2 ;
        float centerY = canvas.getHeight() /2;


        canvas.rotate(180,centerX, centerY );
        //canvas.drawText(message, 200,15, textPaint);
        canvas.drawRect(0,0, layout.getWidth(), layout.getHeight(), fill);
        layout.draw(canvas);



        GLES20.glUseProgram(myWorld.getHudProgram().getProgramHandle());

        if(hudImage != null)
            hudImage.delete();

        hudImage = new Texture(bitmap, myWorld.getHudProgram(), TextureCoordinateProgram.U_TEXTURE, false); //load the texture
        bitmap.recycle();
        float ratio = (float)canvas.getHeight() / (float)canvas.getWidth();
        scale = new Vector(width, ratio * width, 0f);
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

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
        refreshHud();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        refreshHud();
    }

    public Layout.Alignment getTextAlign() {
        return textAlign;
    }

    public void setTextAlign(Layout.Alignment textAlign) {
        this.textAlign = textAlign;
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

    public void scale(float scale){
        this.scale = new Vector(scale, scale, scale);
    }

    public Vector upperRight(){
        return getWorldOffset().add(scale);
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    @Override
    public Vector getScale() {
        return scale;
    }

    @Override
    public float getYaw() {
       return yaw;
    }

    @Override
    public float getPitch() {
        return pitch;
    }
}
