package com.example.loadin_app.ui.opengl;

import android.opengl.GLES20;

import java.util.Arrays;
import java.util.stream.Stream;

public class Box extends WorldObject{
   //private final Color BOX_COLOR =  new Color(102f/255f, 84f/255f, 74f/255f, 1f);



    private CubeMappedHexahedron hexahedron;

   private Vector destination;

    public Box(float width, float height, float length, World world) {
        super(world);


        hexahedron = new CubeMappedHexahedron(
               width ,
               height ,
               length );
        hexahedron.setMap(world.getCubeMapProgram().getBox());//the global box map

        destination = new Vector(0f, 0f, 0f);
     }

     public void setDestination(Vector destination){
        this.destination = destination;
     }
     public Vector getDestination(){
        return destination;
     }


     public float getWidth(){
        return hexahedron.getWidth();
     }

     public float getHeight(){
        return hexahedron.getHeight();
     }
     public float getLength(){
        return hexahedron.getLength();
     }

     public float getArea()
     {
         return hexahedron.getWidth() * hexahedron.getLength() * hexahedron.getHeight();
     }


     public Vector getCenter(){
        float x = getWidth() / 2f;
        float z = getLength() / 2f;
        float y = getHeight() / 2f;
        return getOffset().add( new Vector(x,y,z));

     }


    public void rotateLeftBy90Degrees(){
        hexahedron = new CubeMappedHexahedron(hexahedron.getLength(), hexahedron.getHeight(), hexahedron.getWidth() );
    }

    public boolean intersects(Box otherBox){
        //TODO: implement
        return false;
    }
    public Vector getOffsetToLeftOf(){
        return null;
    }




    @Override
    public OpenGLProgram getMyProgram() {
        return myWorld.getCubeMapProgram();
    }

    @Override
    public void draw(float[] view, float[] projection) {
        OpenGLProgram program = myWorld.getCubeMapProgram(); //textured view program
        GLES20.glUseProgram(program.getProgramHandle()); //activate the program

        //calculate model
        float[] postScaleMatrix = processScale();  //scale the object to size
        float[] postTranslationMatrix = processTranslation(postScaleMatrix);  //move the object in the world

        //upload model
        program.setUniformMatrix4fv(postTranslationMatrix, World.TextureCoordinateProgram.U_MODEL); //this is the model scale and transpose info
        //upload view
        program.setUniformMatrix4fv(view, World.TextureCoordinateProgram.U_VIEW);
        //upload projection
        program.setUniformMatrix4fv(projection, World.TextureCoordinateProgram.U_PROJECTION);

        hexahedron.draw(myWorld); //pass off the responsibility to one layer down
    }
}
