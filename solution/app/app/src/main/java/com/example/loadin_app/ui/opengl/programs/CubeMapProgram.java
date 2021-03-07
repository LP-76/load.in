package com.example.loadin_app.ui.opengl.programs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.example.loadin_app.R;
import com.example.loadin_app.ui.opengl.CubeMap;
import com.example.loadin_app.ui.opengl.Texture;
import com.example.loadin_app.ui.opengl.World;

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

    public <T extends IPlaceable & IColorable & ICubeMappable> void render(T item, float[] view, float[] projection){
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
        setVertexAttributePointer(item.getCubeTextureDirections(), A_TEX_DIRECTION);

        CubeMap map = item.getMap();

        //bind the cubemap

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);//I think this tells the program to bind the the variable for the cube
        GLES20.glBindTexture(GLES20.GL_TEXTURE_CUBE_MAP, map.getHandle());
        setUniform1i(U_CUBE, 0);


        //conduct render
        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, positions.getCount());

        //cleanup attributes
        disableVertexAttribute(A_COLOR);
        disableVertexAttribute(A_POSITION);
        disableVertexAttribute(A_TEX_DIRECTION);

    }

}
