package com.example.loadin_app.ui.opengl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.opengl.GLES20;

import com.example.loadin_app.R;

import java.util.Arrays;
import java.util.stream.Stream;

public class Sign extends  TexturedWorldObject{
    private String message;
    private Bitmap renderedMessage;
    private OpenGLVariableHolder textureCoordinates;
    private TexturedHexahedron board;
    private Texture tex;

    public Sign(World w, float width, float height){
        super(w);
        message = "Hello cruel world";
        renderMessage();

        board = new TexturedHexahedron(width, height, 1f);


    }

    public void setMessage(String message){
        this.message = message;
        renderMessage();
    }

    public void testBitmap(Bitmap source){

        renderedMessage = source;

    }

    private void renderMessage(){

        Bitmap bitmap = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_4444);
// get a canvas to paint over the bitmap
        Canvas canvas = new Canvas(bitmap);
        bitmap.eraseColor(Color.rgb(255,255,255));



//// get a background image from resources
//// note the image format must match the bitmap format
//        Drawable background = context.getDrawable(R.drawable.box_input_logo);
//        background.setBounds(0, 0, 256, 256);
//        background.draw(canvas); // draw the background to our bitmap

// Draw the text
        Paint textPaint = new Paint();
        textPaint.setTextSize(32);
        textPaint.setAntiAlias(true);
        textPaint.setARGB(255, 255, 0, 0x00);
// draw the text centered



        float centerX = canvas.getWidth() /2 ;
        float centerY = canvas.getHeight() /2;


        canvas.rotate(180,centerX, centerY );
        canvas.drawText(message, 16,112, textPaint);



        //great we have text, but how the heck do we get it into something??
        if(renderedMessage!= null)
            renderedMessage.recycle();
        renderedMessage = bitmap;

        GLES20.glUseProgram(getMyProgram().getProgramHandle());
        tex = new Texture(renderedMessage, getMyProgram(), World.TextureCoordinateProgram.U_TEXTURE); //load the texture
        board.setFrontTexture(tex);
        board.setBottomTexture(tex);
        board.setTopTexture(tex);
        board.setLeftTexture(tex);
        board.setRightTexture(tex);
        board.setBackTexture(tex);
    }

//    @Override
//    public void uploadDataForShader(OpenGLProgram program) {
//
//        uploadPositionInformation(program);  //load position data in
//        uploadColorInformation(program);
//        //we also need to upload the information for the texture positions
//        textureCoordinates = new OpenGLVariableHolder(
//                getTextureCoordinates(),
//                2, World.TextureCoordinateProgram.A_TEX_COORD
//        );
////
//        program.setVertexAttributePointer(textureCoordinates, 2*4);  //2 coordinates per vertex
//        //setup for texture
//
//        GLES20.glActiveTexture(GLES20.GL_TEXTURE0); //activate texture 0
//        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, tex.getHandle()); //tell it where the data is
//        getMyProgram().setUniform1i(World.TextureCoordinateProgram.U_TEXTURE, 0);  //bind the variable to the shader so it can see it
//
//
//
//    }


    @Override
    public OpenGLProgram getMyProgram() {
        return myWorld.getTextureViewProgram(); //this will use the texture view
    }

    @Override
    public Stream<IDrawable> getDrawableShapes() {
        return Arrays.stream(new IDrawable[]{ board  });
    }
}
