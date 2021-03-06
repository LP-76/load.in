package com.example.loadin_app.ui.opengl.programs;

import android.content.Context;
import android.opengl.GLES20;

import com.example.loadin_app.R;
import com.example.loadin_app.ui.opengl.CubeMap;

public class HudProgram extends OpenGLProgram{

    public static final String U_MODEL = "model";
    public static final String U_VIEW = "view";
    public static final String U_PROJECTION = "projection";

    public static final String U_TEXTURE = "u_Texture";


    public static final String A_POSITION = "a_Position";  //vector position handle
    public static final String A_TEX_COORD = "a_TexCoord";



    public HudProgram(){

    }


    @Override
    public void load(Context context) {
        String vertexShaderCode = loadShaderFile(context, R.raw.hud_vertex_shader);
        String fragmentShaderCode = loadShaderFile(context, R.raw.hud_fragment_shader);

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

    public <T extends IPlaceable & ITexturable > void render(T item, float[] view, float[] projection){
        GLES20.glUseProgram(getProgramHandle());  //activate this program

        float[] model = processTranslation(item);  //model is translatable but not scalable

        //upload model matrix
        setUniformMatrix4fv(model, U_MODEL);
        //upload view matrix
        setUniformMatrix4fv(view, U_VIEW);
        //upload projection matrix
        setUniformMatrix4fv(projection, U_PROJECTION);

        //upload positions
        OpenGLVariableHolder positions = item.getPositions();
        setVertexAttributePointer(positions, A_POSITION);

        //upload texture coordintes
        setVertexAttributePointer(item.getTextureCoordiantes(), A_TEX_COORD);


        //set the texture
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, item.getTexture().getHandle());  //bind to the handle


        //conduct render
        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, positions.getCount());

        //cleanup attributes
        disableVertexAttribute(A_POSITION);
        disableVertexAttribute(A_TEX_COORD);

    }

}