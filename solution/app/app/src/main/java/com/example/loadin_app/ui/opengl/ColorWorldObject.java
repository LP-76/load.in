package com.example.loadin_app.ui.opengl;

import android.opengl.GLES20;

import java.util.Arrays;
import java.util.stream.Stream;


public abstract class ColorWorldObject extends WorldObject {
    private OpenGLVariableHolder vertexBuffer;
    private OpenGLVariableHolder colorBuffer;
    private OpenGLVariableHolder orthogonalBuffer;
    private OpenGLVariableHolder lightBuffer;
    private final float[] mLightPosInModelSpace = new float[] { 8.0f, 8.0f, 8.0f, 1.0f};

    public ColorWorldObject(World world) {
        super(world);
    }

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

    @Override
    public OpenGLProgram getMyProgram(){
        return myWorld.getLightViewProgram();
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

}
