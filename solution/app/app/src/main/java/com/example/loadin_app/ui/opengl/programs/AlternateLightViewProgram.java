package com.example.loadin_app.ui.opengl.programs;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.example.loadin_app.R;
import com.example.loadin_app.ui.opengl.World;

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
        String fragmentShaderCode = loadShaderFile(context, R.raw.light_fragment_shader);

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

    public <T extends IPlaceable & IColorable > void render(T item, float[] view, float[] projection){
        GLES20.glUseProgram(getProgramHandle());  //activate this program

        float[] transpose = processTranslation(item);
        float[] scale = processScale(World.WORLD_SCALE_VECTOR);

        float[] model = new float[16];
        Matrix.multiplyMM(model, 0, scale, 0, transpose, 0);  //model is the mix of the two

        //upload model matrix
        setUniformMatrix4fv(model, U_MODEL);
        //upload view matrix
        setUniformMatrix4fv(view, U_VIEW);
        //upload projection matrix
        setUniformMatrix4fv(projection, U_PROJECTION);

        //upload positions
        OpenGLVariableHolder positions = item.getPositions();

        setVertexAttributePointer(positions, A_POSITION);

        //upload color
        setVertexAttributePointer(item.getColors(), A_COLOR);

        //conduct render
        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, positions.getCount());

        //cleanup attributes

        disableVertexAttribute(A_COLOR);
        disableVertexAttribute(A_POSITION);


    }
}
