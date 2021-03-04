package com.example.loadin_app.ui.opengl;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.time.Duration;
import java.util.Arrays;
import java.util.stream.Stream;



/**
 * An object that can be displayed in the world
 */
public abstract class  WorldObject  {

    private Vector offset;


    public Vector getOffset() {
        return offset;
    }
//private float[] mLightModelMatrix = new float[16];

    private final float[] mLightPosInModelSpace = new float[] { 8.0f, 8.0f, 8.0f, 1.0f};
   // private final float[] mLightPosInWorldSpace = new float[4];
   // private final float[] mLightPosInEyeSpace = new float[4];

    protected World myWorld;
    private boolean visible;

    private OpenGLVariableHolder vertexBuffer;
    private OpenGLVariableHolder colorBuffer;
    private OpenGLVariableHolder orthogonalBuffer;
    private OpenGLVariableHolder lightBuffer;

    public  WorldObject(World world){
        offset = new Vector(0,0,0);
        myWorld = world;
        myWorld.addObject(this);
        visible = true;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void place(Vector offset){
        this.offset  = offset;
    }

//    public void move(Vector direction){
//        this.offset = new Vector(offset.getX() + direction.getX(),
//                offset.getY() + direction.getY(),
//                offset.getZ() + direction.getZ()
//        );
//    }


    public void moveToLocationOverDuration(Duration timeframe,  Vector newLocation){
        myWorld.addAnimation( new TransposeAnimation(this, timeframe, myWorld.getTick(), newLocation));
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

    private float[] processScale(){
        float[] scaleMatrix = new float[16];

        //set the identity matrix for translation
        Matrix.setIdentityM(scaleMatrix, 0);

        //add the translation to the matrix
        Matrix.scaleM(scaleMatrix,0, World.INCHES_TO_WORLD_SCALE,  World.INCHES_TO_WORLD_SCALE,  World.INCHES_TO_WORLD_SCALE );

        return scaleMatrix;
    }

    public abstract OpenGLProgram getMyProgram();

    public  void uploadDataForShader(OpenGLProgram program){
        uploadLightInformation(program);
        uploadPositionInformation(program);
        uploadColorInformation(program);
    }

    public void cleanupAfterDraw(OpenGLProgram program){
       program.disableVertexAttribute(vertexBuffer.getVariableName());
       program.disableVertexAttribute(colorBuffer.getVariableName());
    }


protected void uploadLightInformation(OpenGLProgram program){
   // lightBuffer = new OpenGLVariableHolder(mLightPosInModelSpace, 4, World.StandardLightViewProgram.U_LIGHTPOS);
    //program.setUniformMatrix3fv(lightBuffer);  //upload the light information
}

    protected void uploadPositionInformation(OpenGLProgram program){
        vertexBuffer = new OpenGLVariableHolder(getVectorCoordinates(), 3, World.AlternateLightViewProgram.A_POSITION);
        program.setVertexAttributePointer(vertexBuffer, 3*4);

    }

    protected void uploadColorInformation(OpenGLProgram program){
        colorBuffer = new OpenGLVariableHolder(getVectorColors(), 4, World.AlternateLightViewProgram.A_COLOR);
        program.setVertexAttributePointer(colorBuffer, 4*4);


    }
    private  void uploadNormalInformation(){
//        orthogonalBuffer.establishHandle(myWorld.getLightViewProgram().getProgramHandle());
//        GLES20.glEnableVertexAttribArray(orthogonalBuffer.handle);
//        orthogonalBuffer.uploadData();
        //orthogonalBuffer = new OpenGLVertexAttribute(getOrthogonalVectors(), 3, World.StandardLightViewProgram.A_NORMAL, 3*4);
    }

    /*
    The mvp matrix is the matrix that has been configured as the view and the projection at a higher level
     */
    public void draw(float[] view, float[] projection) {
        OpenGLProgram program = getMyProgram();
        GLES20.glUseProgram(program.getProgramHandle());


        float[] postScaleMatrix = processScale();  //scale the object to size
        float[] postTranslationMatrix = processTranslation(postScaleMatrix);  //move the object in the world

        uploadDataForShader(program);  //establish variables for programs


        //we need to establish the project, the view and the model matrixes
        program.setUniformMatrix4fv(postTranslationMatrix, World.AlternateLightViewProgram.U_MODEL); //model matrix is the scale and the translation
        program.setUniformMatrix4fv(projection,World.AlternateLightViewProgram.U_PROJECTION);
        program.setUniformMatrix4fv( view,World.AlternateLightViewProgram.U_VIEW);


        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexBuffer.getCount());


        cleanupAfterDraw(program);

        //GLES20.glDisableVertexAttribArray(orthogonalBuffer.getHandle());
    }

}
