package com.example.loadin_app.ui.opengl.programs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.example.loadin_app.R;
import com.example.loadin_app.ui.opengl.Texture;
import com.example.loadin_app.ui.opengl.World;

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
    private Texture floor;
    private Texture wall;

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


        cardboard = loadTexture(context, R.drawable.cardboard);
        floor = loadTexture(context, R.drawable.wood_floor);
        wall = loadTexture(context, R.drawable.corrugated_metal_texture_7);
    }

    private Texture loadTexture(Context ctx, int resourceId){
        BitmapFactory.Options ops = new BitmapFactory.Options();
        ops.inScaled = false;
        Bitmap bitmap = BitmapFactory.decodeResource(ctx.getResources(), resourceId, ops);
        return new Texture(bitmap, this, U_TEXTURE, true);
    }

    public Texture getFloor() {
        return floor;
    }

    public Texture getWall() {
        return wall;
    }

    public Texture getCardboard() {
        return cardboard;
    }


    public <T extends IPlaceable & IColorable & ITexturable> void render(T item, float[] view, float[] projection){
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

        //upload texture coordintes
        setVertexAttributePointer(item.getTextureCoordiantes(), A_TEX_COORD);

        Texture tex = item.getTexture();

        //set the texture
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, tex.getHandle());  //bind to the handle


        //conduct render
        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, positions.getCount());

        //cleanup attributes

        disableVertexAttribute(A_COLOR);
        disableVertexAttribute(A_POSITION);
        disableVertexAttribute(A_TEX_COORD);

    }

}
