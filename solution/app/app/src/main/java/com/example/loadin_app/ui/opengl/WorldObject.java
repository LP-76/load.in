package com.example.loadin_app.ui.opengl;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.SystemClock;

import java.util.Arrays;
import java.util.stream.Stream;

import static com.example.loadin_app.ui.opengl.World.StandardLightViewProgram.MVP_MATRIX;
import static com.example.loadin_app.ui.opengl.World.StandardLightViewProgram.U_MV_MATRIX;

/**
 * An object that can be displayed in the world
 */
public abstract class  WorldObject  {

    private Vector offset;
    private int vPMatrixHandle;
    private  int mMVMatrixHandle;

    private float[] mLightModelMatrix = new float[16];

    private final float[] mLightPosInModelSpace = new float[] {0.0f, 0.0f, 0.0f, 1.0f};
    private final float[] mLightPosInWorldSpace = new float[4];
    private final float[] mLightPosInEyeSpace = new float[4];

    private World myWorld;

    private OpenGLVariableLoader vertexBuffer;
    private OpenGLVariableLoader colorBuffer;
    private OpenGLVariableLoader orthogonalBuffer;
    private OpenGLUniformMatrix3fv lightBuffer;

    public  WorldObject(World world){
        offset = new Vector(0,0,0);
        myWorld = world;
    }





    public void place(Vector offset){
        this.offset  = offset;
    }

    public void move(Vector direction){
        this.offset = new Vector(offset.getX() + direction.getX(),
                offset.getY() + direction.getY(),
                offset.getZ() + direction.getZ()
        );
    }

    private void loadBuffer(float[] viewMatrix){
        //put the items into the buffers here
       vertexBuffer = new OpenGLVertexAttribute(getVectorCoordinates(), 3, World.StandardLightViewProgram.A_POSITION,3*4);
       colorBuffer = new OpenGLVertexAttribute(getVectorColors(), 4, World.StandardLightViewProgram.A_COLOR, 4*4);
       orthogonalBuffer = new OpenGLVertexAttribute(getOrthogonalVectors(), 3, World.StandardLightViewProgram.A_NORMAL, 3*4);


        long time = SystemClock.uptimeMillis() % 10000L;
        float angleInDegrees = (360.0f / 10000.0f) * ((int) time);

        Matrix.setIdentityM(mLightModelMatrix, 0);
        Matrix.translateM(mLightModelMatrix, 0, 0.0f, 0.0f, -4.0f);
        Matrix.rotateM(mLightModelMatrix, 0, angleInDegrees, 1.0f, 1.0f, 0.0f);
        Matrix.translateM(mLightModelMatrix, 0, 0.0f, 0.0f, 2.0f);
        Matrix.multiplyMV(mLightPosInWorldSpace, 0, mLightModelMatrix, 0, mLightPosInModelSpace, 0);
        Matrix.multiplyMV(mLightPosInEyeSpace, 0, viewMatrix, 0, mLightPosInWorldSpace, 0);

       lightBuffer = new OpenGLUniformMatrix3fv(mLightPosInEyeSpace, 4, World.StandardLightViewProgram.U_LIGHTPOS);


    }

    public abstract Stream<Shape> getShapes();

    public Stream<Float> getOrthogonalVectors(){
        return getShapes().flatMap(i -> i.getTriangles())
                .flatMap(i -> Arrays.stream(i.getOrthogonalVectorsForPlane()))
                .flatMap(i -> i.getCoordinates());

    }

    public Stream<Float> getVectorCoordinates(){
        return getShapes().flatMap(i -> i.getTriangles()).flatMap(i -> i.getCoordinates());
    }

    public Stream<Float> getVectorColors(){
        return getShapes().flatMap(i -> i.getTriangles()).flatMap(i -> i.getColors());
    }

    private float[] processTranslation(float[] mvpMatrix){

        //apply translations here

        float[] translationMatrix = new float[16];

        //set the identity matrix for translation
        Matrix.setIdentityM(translationMatrix, 0);

        //add the translation to the matrix
        Matrix.translateM(translationMatrix,0, offset.getX(), offset.getY(), offset.getZ() );

        float[] postTranslationMatrix = new float[16];
        //apply the translation to the objects matrix translation
        Matrix.multiplyMM(postTranslationMatrix, 0, mvpMatrix, 0, translationMatrix, 0);
        return postTranslationMatrix;
    }

    private void uploadLightInformation(float[] viewMatrix){


        lightBuffer.establishHandle(myWorld.getLightViewProgram().getProgramHandle());

        lightBuffer.uploadData();



    }



    private void uploadPositionInformation(){

        vertexBuffer.establishHandle(myWorld.getLightViewProgram().getProgramHandle());
        GLES20.glEnableVertexAttribArray(vertexBuffer.handle);
        vertexBuffer.uploadData();  //load into memory

    }

    private void uploadColorInformation(){
        colorBuffer.establishHandle(myWorld.getLightViewProgram().getProgramHandle());
        GLES20.glEnableVertexAttribArray(colorBuffer.handle);
        colorBuffer.uploadData();



    }
    private  void uploadNormalInformation(){
        orthogonalBuffer.establishHandle(myWorld.getLightViewProgram().getProgramHandle());
        GLES20.glEnableVertexAttribArray(orthogonalBuffer.handle);
        orthogonalBuffer.uploadData();

    }

    /*
    The mvp matrix is the matrix that has been configured as the view and the projection at a higher level
     */
    public void draw(float[] mvpMatrix, float[] viewMatrix) {
        GLES20.glUseProgram(myWorld.getLightViewProgram().getProgramHandle());

        loadBuffer(viewMatrix);

        float[] postTranslationMatrix = processTranslation(mvpMatrix);  //here we are applying the translation to the object
        //depending upon the world offset


        uploadPositionInformation(); //this uploads the verticies of the object to the engine
        uploadColorInformation();  //this uploads the necissary things for the color
        uploadNormalInformation();  //this is the orthogonal information for the lights
        uploadLightInformation(viewMatrix);

        //int colorHandle = GLES20.glGetUniformLocation(program, "vColor");

        // Set color for drawing the triangle
        //GLES20.glUniform4fv(colorHandle, 1, color, 0);

        // get handle to shape's transformation matrix
        vPMatrixHandle = GLES20.glGetUniformLocation(myWorld.getLightViewProgram().getProgramHandle(), MVP_MATRIX);
        mMVMatrixHandle = GLES20.glGetUniformLocation(myWorld.getLightViewProgram().getProgramHandle(), U_MV_MATRIX);

        // Pass the projection and view transformation to the shader
        GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, postTranslationMatrix, 0);

        GLES20.glUniformMatrix4fv(mMVMatrixHandle, 1, false, postTranslationMatrix, 0);

        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexBuffer.getCount());



    }

}
