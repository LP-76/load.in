package com.example.loadin_app.ui.opengl;

//this is the world that anything can be rendered in

import android.content.Context;
import android.opengl.GLES20;

import com.example.loadin_app.R;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Stream;

public class World {

    public static final float INCHES_TO_WORLD_SCALE = 1f/12f;  //feet essentially

    private OpenGLProgram lightViewProgram;
    private OpenGLProgram textureProgram;

    private ArrayList<Animation> animations;
    private ArrayList<WorldObject> worldObjects;
    private Duration tick;
    private LocalDateTime lastDraw;

    public World(Context context){
        lightViewProgram = new AlternateLightViewProgram();
        lightViewProgram.load(context);
        textureProgram = new TextureCoordinateProgram();
        textureProgram.load(context);
        worldObjects = new ArrayList<WorldObject>();
        animations = new ArrayList<Animation>();
    }
    public OpenGLProgram getLightViewProgram(){
        return lightViewProgram;
    }
    public OpenGLProgram getTextureViewProgram(){
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



    public void updateTicks(){
        if(lastDraw != null)
            tick = Duration.between(lastDraw, LocalDateTime.now() );
        lastDraw = LocalDateTime.now();
    }

    public Duration getTick(){
        return tick;
    }


    public class TextureCoordinateProgram extends OpenGLProgram{

        public static final String U_MV_MATRIX = "u_MVMatrix";
        public static final String U_LIGHTPOS = "u_LightPos";

        public static final String A_POSITION = "a_Position";  //vector position handle
        public static final String MVP_MATRIX = "u_MVPMatrix";  //handle to the projection matrix

        public static final String V_COLOR = "v_Color";
        public static final String A_COLOR = "a_Color";
        public static final String A_TEX_COORD = "a_TexCoord";
        public static final String OUT_TEX_COORD = "TexCoord";
        public static final String A_NORMAL = "a_Normal";
        public static final String U_TEXTURE = "u_Texture";

        private final String fragmentShaderCode =
                "precision mediump float;       \n"		// Set the default precision to medium. We don't need as high of a
                        // precision in the fragment shader.
                        + "uniform sampler2D u_Texture;          \n"		// the texture reference
                        + "varying vec4 v_Color;          \n"		// This is the color from the vertex shader interpolated across the
                        + "varying vec2 TexCoord;          \n"		// the coordinate from the other shader
                        // triangle per fragment.
                        + "void main()                    \n"		// The entry point for our fragment shader.
                        + "{                              \n"
                        + "   gl_FragColor = v_Color * texture2D(u_Texture,TexCoord);     \n"		// Pass the texture to the color mapper

                        + "} ";
        private final String vertexShaderCode =
                "uniform mat4 u_MVPMatrix;      \n"		// A constant representing the combined model/view/projection matrix.
                        + "uniform mat4 u_MVMatrix;       \n"		// A constant representing the combined model/view matrix.

                        + "attribute vec4 a_Position;     \n"		// Per-vertex position information we will pass in.
                        + "attribute vec4 a_Color;        \n"		// Per-vertex color information we will pass in.
                        + "attribute vec2 a_TexCoord;      \n"       //per-vertex x-y coordinate of the texture
                        + "varying vec4 v_Color;          \n"		// This will be passed into the fragment shader.
                        + "varying vec2 TexCoord;"
                        + "void main()                    \n" 	// The entry point for our vertex shader.
                        + "{                              \n"
                        + "   v_Color = a_Color;                              \n"
                        // gl_Position is a special variable used to store the final position.
                        // Multiply the vertex by the matrix to get the final point in normalized screen coordinates.
                        + "   gl_Position = u_MVPMatrix * a_Position;                            \n"
                        + "   TexCoord = a_TexCoord;"
                        + "}                                                   ";
        @Override
        public void load(Context context) {
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


//
//    public class StandardLightViewProgram extends OpenGLProgram{
//
//        public static final String U_MV_MATRIX = "u_MVMatrix";
//        public static final String U_LIGHTPOS = "u_LightPos";
//
//        public static final String A_POSITION = "a_Position";  //vector position handle
//        public static final String MVP_MATRIX = "u_MVPMatrix";  //handle to the projection matrix
//
//        public static final String V_COLOR = "v_Color";
//        public static final String A_COLOR = "a_Color";
//
//        public static final String A_NORMAL = "a_Normal";
//
//        private final String fragmentShaderCode =
//                "precision mediump float;       \n"		// Set the default precision to medium. We don't need as high of a
//                        // precision in the fragment shader.
//                        + "varying vec4 v_Color;          \n"		// This is the color from the vertex shader interpolated across the
//                        // triangle per fragment.
//                        + "void main()                    \n"		// The entry point for our fragment shader.
//                        + "{                              \n"
//                        + "   gl_FragColor = v_Color;     \n"		// Pass the color directly through the pipeline.
//                        + "} ";
//        private final String vertexShaderCode =
//                        "uniform mat4 u_MVPMatrix;      \n"		// A constant representing the combined model/view/projection matrix.
//                        + "uniform mat4 u_MVMatrix;       \n"		// A constant representing the combined model/view matrix.
//                        + "uniform vec3 u_LightPos;       \n"	    // The position of the light in eye space.
//
//                        + "attribute vec4 a_Position;     \n"		// Per-vertex position information we will pass in.
//                        + "attribute vec4 a_Color;        \n"		// Per-vertex color information we will pass in.
//                        + "attribute vec3 a_Normal;       \n"		// Per-vertex normal information we will pass in.
//
//                        + "varying vec4 v_Color;          \n"		// This will be passed into the fragment shader.
//
//                        + "void main()                    \n" 	// The entry point for our vertex shader.
//                        + "{                              \n"
//                        // Transform the vertex into eye space.
//                        + "   vec3 modelViewVertex = vec3(u_MVMatrix * a_Position);              \n"
//                        // Transform the normal's orientation into eye space.
//                        + "   vec3 modelViewNormal = vec3(u_MVMatrix * vec4(a_Normal, 0.0));     \n"
//                        // Will be used for attenuation.
//                        + "   float distance = length(u_LightPos - modelViewVertex);             \n"
//                        // Get a lighting direction vector from the light to the vertex.
//                        + "   vec3 lightVector = normalize(u_LightPos - modelViewVertex);        \n"
//                        // Calculate the dot product of the light vector and vertex normal. If the normal and light vector are
//                        // pointing in the same direction then it will get max illumination.
//                        + "   float diffuse = max(dot(modelViewNormal, lightVector), 0.1);       \n"
//                        // Attenuate the light based on distance.
//                        + "   diffuse = diffuse * (1.0 / (1.0 + (0.25 * distance * distance)));  \n"
//                        // Multiply the color by the illumination level. It will be interpolated across the triangle.
//                      //  + "   v_Color = a_Color * diffuse;                                       \n"
//                        + "   v_Color = a_Color;                              \n"
//                        // gl_Position is a special variable used to store the final position.
//                        // Multiply the vertex by the matrix to get the final point in normalized screen coordinates.
//                        + "   gl_Position = u_MVPMatrix * a_Position;                            \n"
//                        + "}                                                   ";
//        @Override
//        public void load() {
//
//
//
//            int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER,
//                    vertexShaderCode);
//            int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER,
//                    fragmentShaderCode);
//
//            // add the vertex shader to program
//            GLES20.glAttachShader(getProgramHandle(), vertexShader);
//
//            // add the fragment shader to program
//            GLES20.glAttachShader(getProgramHandle(), fragmentShader);
//
//            // creates OpenGL ES program executables
//            GLES20.glLinkProgram(getProgramHandle());
//        }
//    }


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

}
