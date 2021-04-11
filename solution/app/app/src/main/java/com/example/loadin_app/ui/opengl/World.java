package com.example.loadin_app.ui.opengl;

//this is the world that anything can be rendered in

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;

import com.example.loadin_app.R;
import com.example.loadin_app.ui.opengl.programs.*;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Stream;

public class World {

    public static final float INCHES_TO_WORLD_SCALE = 1f/12f;  //feet essentially
    public static final Vector WORLD_SCALE_VECTOR = new Vector(INCHES_TO_WORLD_SCALE, INCHES_TO_WORLD_SCALE, INCHES_TO_WORLD_SCALE);

    private AlternateLightViewProgram lightViewProgram;
    private TextureCoordinateProgram textureProgram;
    private CubeMapProgram cubeMapProgram;
    private HudProgram hudProgram;


    private Context context;

    private ArrayList<Animation> animations;
    private ArrayList<WorldObject> worldObjects;
    private Duration tick;
    private LocalDateTime lastDraw;
    private boolean colorCodeBoxes;

    public World(Context context){
       this(context, false);
    }

    public World(Context context, boolean colorCodeBoxes){
        this.context = context;
        this.colorCodeBoxes = colorCodeBoxes;
        lightViewProgram = new AlternateLightViewProgram();
        lightViewProgram.load(context);
        textureProgram = new TextureCoordinateProgram();
        textureProgram.load(context);
        cubeMapProgram = new CubeMapProgram();
        cubeMapProgram.load(context);

        hudProgram = new HudProgram();
        hudProgram.load(context);

        worldObjects = new ArrayList<WorldObject>();
        animations = new ArrayList<Animation>();
    }

    public boolean isColorCodeBoxes() {
        return colorCodeBoxes;
    }

    public void setColorCodeBoxes(boolean colorCodeBoxes) {
        this.colorCodeBoxes = colorCodeBoxes;
    }

    public Context getContext() {
        return context;
    }

    public AlternateLightViewProgram getLightViewProgram(){
        return lightViewProgram;
    }
    public TextureCoordinateProgram getTextureViewProgram(){
        return textureProgram;
    }

    public Stream<Animation> getAnimiations(){
        return animations.stream();
    }

    public void addAnimation(Animation a){
        animations.add(a);
    }

    public void removeAnimation(Animation a){
        animations.remove(a);
    }

    public void addObject(WorldObject anObject){
        worldObjects.add(anObject);
    }
    public final ArrayList<WorldObject> getWorldObjects(){
        return worldObjects;
    }

    public CubeMapProgram getCubeMapProgram() {
        return cubeMapProgram;
    }

    public void updateTicks(){
        if(lastDraw != null)
            tick = Duration.between(lastDraw, LocalDateTime.now() );
        lastDraw = LocalDateTime.now();
    }

    public HudProgram getHudProgram() {
        return hudProgram;
    }

    public Duration getTick(){
        return tick;
    }





}
