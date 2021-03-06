package com.example.loadin_app.ui.opengl;

//this is the world that anything can be rendered in

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;

import com.example.loadin_app.R;

import org.w3c.dom.Text;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Stream;

public class World {

    public static final float INCHES_TO_WORLD_SCALE = 1f/12f;  //feet essentially

    private OpenGLProgram lightViewProgram;
    private TextureCoordinateProgram textureProgram;
    private CubeMapProgram cubeMapProgram;


    private ArrayList<Animation> animations;
    private ArrayList<WorldObject> worldObjects;
    private Duration tick;
    private LocalDateTime lastDraw;

    public World(Context context){
        lightViewProgram = new AlternateLightViewProgram();
        lightViewProgram.load(context);
        textureProgram = new TextureCoordinateProgram();
        textureProgram.load(context);
        cubeMapProgram = new CubeMapProgram();
        cubeMapProgram.load(context);

        worldObjects = new ArrayList<WorldObject>();
        animations = new ArrayList<Animation>();
    }
    public OpenGLProgram getLightViewProgram(){
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

    public Duration getTick(){
        return tick;
    }


    public class TextureCoordinateProgram extends OpenGLProgram{

        public static final String U_MODEL = "model";
        public static final String U_VIEW = "view";
        public static final String U_PROJECTION = "projection";

        public static final String U_TEXTURE = "u_Texture";

        public static final String U_LIGHTPOS = "u_LightPos";

        public static final String A_POSITION = "a_Position";  //vector position handle
        public static final String A_COLOR = "a_Color";
        public static final String A_NORMAL = "a_Normal";
        public static final String A_TEX_COORD = "a_TexCoord";

        private Texture cardboard;

        public TextureCoordinateProgram(){

        }


        @Override
        public void load(Context context) {
            String vertexShaderCode = loadShaderFile(context, R.raw.texture_vertex_shader);
            String fragmentShaderCode = loadShaderFile(context, R.raw.texture_fragment_shader);

            int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER,
                    vertexShaderCode);
            int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER,
                    fragmentShaderCode);

            // add the vertex shader to program
            GLES20.glAttachShader(getProgramHandle(), vertexShader);

            // add the fragment shader to program
            GLES20.glAttachShader(getProgramHandle(), fragmentShader);

            // creates OpenGL ES program executables
            GLES20.glLinkProgram(getProgramHandle());


            loadCardboard(context);
        }

        private void loadCardboard(Context ctx){
            BitmapFactory.Options ops = new BitmapFactory.Options();
            ops.inScaled = false;
            Bitmap bitmap = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.cardboard, ops);
            cardboard = new Texture(bitmap, this, U_TEXTURE);

        }

        public Texture getCardboard() {
            return cardboard;
        }
    }


    public class AlternateLightViewProgram extends OpenGLProgram{

        public static final String U_MODEL = "model";
        public static final String U_VIEW = "view";
        public static final String U_PROJECTION = "projection";

        public static final String U_LIGHTPOS = "u_LightPos";

        public static final String A_POSITION = "a_Position";  //vector position handle
        public static final String A_COLOR = "a_Color";
        public static final String A_NORMAL = "a_Normal";



        @Override
        public void load(Context context) {
            String vertexShaderCode = loadShaderFile(context, R.raw.light_color_shader);
            String fragmentShaderCode = loadShaderFile(context, R.raw.fragment_shader);

            int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER,
                    vertexShaderCode);
            int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER,
                    fragmentShaderCode);

            // add the vertex shader to program
            GLES20.glAttachShader(getProgramHandle(), vertexShader);

            // add the fragment shader to program
            GLES20.glAttachShader(getProgramHandle(), fragmentShader);

            // creates OpenGL ES program executables
            GLES20.glLinkProgram(getProgramHandle());
        }
    }

    public class CubeMapProgram extends OpenGLProgram{

        //required uniforms
        public static final String U_MODEL = "model";
        public static final String U_VIEW = "view";
        public static final String U_PROJECTION = "projection";

        public static final String U_CUBE = "u_cube"; //uniform handle to the cubemap

        //to be supplied for calculations
        public static final String A_POSITION = "a_Position";  //vector position handle
        public static final String A_COLOR = "a_Color";
        public static final String A_TEX_DIRECTION = "a_tex_direction";

        private CubeMap box;


        @Override
        public void load(Context context) {
            String vertexShaderCode = loadShaderFile(context, R.raw.cubemap_vertex_shader);
            String fragmentShaderCode = loadShaderFile(context, R.raw.cubemap_frag_shader);

            int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER,
                    vertexShaderCode);
            int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER,
                    fragmentShaderCode);

            // add the vertex shader to program
            GLES20.glAttachShader(getProgramHandle(), vertexShader);

            // add the fragment shader to program
            GLES20.glAttachShader(getProgramHandle(), fragmentShader);

            // creates OpenGL ES program executables
            GLES20.glLinkProgram(getProgramHandle());

            loadBoxCube(context);

        }

        public CubeMap getBox() {
            return box;
        }

        public void loadBoxCube(Context context){
             box = new CubeMap(this);

            int[] images = {
                    R.drawable.right,
                    R.drawable.left,
                    R.drawable.top,
                    R.drawable.bottom,
                    R.drawable.back,
                    R.drawable.front
            };
            BitmapFactory.Options ops = new BitmapFactory.Options();
            ops.inScaled = false;
            for(int x = 0; x < images.length;x++){
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), images[x], ops);
                box.addTexture(bitmap, CubeMap.RIGHT + x);
                bitmap.recycle();
            }

            box.applyParamaters();

        }



    }

}
