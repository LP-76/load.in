package com.example.loadin_app.ui.opengl.programs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.example.loadin_app.R;
import com.example.loadin_app.ui.opengl.CubeMap;
import com.example.loadin_app.ui.opengl.Texture;

import org.w3c.dom.Text;

public class HudProgram extends OpenGLProgram{

    public static final String U_MODEL = "model";
    public static final String U_VIEW = "view";
    public static final String U_PROJECTION = "projection";

    public static final String U_TEXTURE = "u_Texture";


    public static final String A_POSITION = "a_Position";  //vector position handle
    public static final String A_TEX_COORD = "a_TexCoord";

    private Texture stainlessSteel;

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

        stainlessSteel = loadTexture(context, R.drawable.stainless_steel);

    }

    public Texture getStainlessSteel() {
        return stainlessSteel;
    }

    private Texture loadTexture(Context ctx, int resourceId){
        BitmapFactory.Options ops = new BitmapFactory.Options();
        ops.inScaled = false;
        Bitmap bitmap = BitmapFactory.decodeResource(ctx.getResources(), resourceId, ops);
        return new Texture(bitmap, this, U_TEXTURE, true);
    }

    public <T extends IPlaceable & ITexturable & IScalable & IRotatable > void render(T item, float[] view, float[] projection){
        GLES20.glUseProgram(getProgramHandle());  //activate this program

        float[] translation = processTranslation(item);  //model is translatable and scalable
        float[] scale = processScale(item.getScale());
        float[] rotation = processRotation(item);

        float[] modelTS = new float[16];
        Matrix.multiplyMM(modelTS, 0, translation, 0, scale, 0);
        float[] model = new float[16];
        Matrix.multiplyMM(model, 0, modelTS, 0, rotation, 0);



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